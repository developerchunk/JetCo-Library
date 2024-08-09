package com.developerstring.jetco_ui.charts

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A default list of colors used for the [PieChart] slices when no custom colors are provided.
 */
private val defaultColorList = listOf(
    Color(0xFFBB86FC),
    Color(0xFF6200EE),
    Color(0xFF03DAC5),
    Color(0xFF3700B3),
    Color(0xFF007BFF)
)

/**
 * A customizable PieChart composable that renders a pie chart based on the provided data.
 * The chart supports animation, color customization, and optional display of chart items.
 *
 * @param modifier The [Modifier] to be applied to the PieChart composable.
 * @param chartData A [Map] where each key represents a category, and the corresponding value represents the size of the pie slice.
 * @param radius The radius of the pie chart. Default is 90.dp.
 * @param thickness The thickness of the pie slices. Default is 25.dp.
 * @param animationDuration The duration of the pie chart's animation in milliseconds. Default is 1000ms.
 * @param colorsList An optional list of [Color]s to be used for the pie slices. If not provided, a default color list is used.
 * @param enableChartItems A boolean flag indicating whether to display a list of chart items (categories and values) below the pie chart. Default is true.
 * @param animationRotations The number of complete 360-degree rotations to apply to the pie chart during the animation. Default is 11 rotations.
 * @param chartItems An optional lambda function that receives a list of chart items ([PieChartEntry]) and allows for custom content to be displayed for each item. If null, a default item layout is used.
 */
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    chartData: Map<String, Int>,
    radius: Dp = 90.dp,
    thickness: Dp = 25.dp,
    animationDuration: Int = 1000,
    colorsList: List<Color>? = null,
    enableChartItems: Boolean = true,
    animationRotations: Int = 11,
    /** This compose function returns list of [PieChartEntry] - [text, value, and the color] of each item*/
    chartItems: @Composable ((List<PieChartEntry>) -> Unit)? = null
) {

    val totalSum = chartData.values.sum()
    val floatValue = mutableListOf<Float>()

    /* To set the value of each Arc according to
    the value given in the data, we have used a simple formula.
    Calculate the sweep angle for each slice of the pie chart based on its proportion
    relative to the total sum of all values. The `floatValue` list stores these angles
    in degrees (0-360), where each angle represents the portion of the pie chart
    corresponding to the value at the given index in the `data` map. */
    chartData.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    val colors: List<Color> = colorsList ?: defaultColorList

    var animationPlayed by rememberSaveable { mutableStateOf(false) }

    var lastValue = 0f

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radius.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = "animateFloatAsState"
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // 90f is used to complete 1/4 of the rotation
    // the 11f represents the number of rotations
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * animationRotations else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = "animateFloatAsState"
    )

    val list: MutableList<PieChartEntry> = dataToMutableList(
        data = chartData,
        colors = colors
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Pie Chart using Canvas Arc
        Box(
            /* The vertical padding is calculated using a formula to ensure that the pie chart's arcs
            do not get cut off when rotated. As the pie chart is drawn using multiple arcs, rotation
            may cause parts of the arcs to extend beyond the designated area, leading to visual clipping
            at the edges (top, bottom, right, and left). This formula adjusts the padding dynamically
            based on the radius of the chart to provide enough space around the pie chart, preventing
            any part of the arcs from being cut off during rotation. */
            modifier = Modifier
                .padding(all = (radius * 2) / (radius / 8.dp))
                .size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .offset { IntOffset.Zero }
                    .size(radius * 2f)
                    .rotate(animateRotation)
            ) {
                // draw each Arc for each data entry in Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = list[index].color,
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(thickness.toPx(), cap = StrokeCap.Butt),
                    )
                    lastValue += value
                }
            }
        }

        // To see the data in more structured way
        // Compose Function in which Items are showing data
        if (enableChartItems) { // Determines whether to display a list of chart items below the pie chart.
            if (chartItems !== null) {
                chartItems(list)
            } else {
                PieChartItems(
                    data = list,
                )
            }
        }

    }

}

/**
 * Displays a list of items representing the data used in the pie chart. Each item shows the category name, value, and associated color.
 *
 * @param data A list of [PieChartEntry]:
 */
@Composable
private fun PieChartItems(
    data: List<PieChartEntry>,
) {
    Column(
        modifier = Modifier
            .padding(top = 80.dp)
            .fillMaxWidth()
    ) {
        // create the data items
        LazyColumn {
            items(data) { value ->
                PieChartItem(
                    data = value,
                )
            }
        }

    }
}

@Composable
private fun PieChartItem(
    data: PieChartEntry,
    boxSize: Dp = 35.dp,
) {

    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 40.dp),
        color = Color.Transparent
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = data.color,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(boxSize)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.value.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color.Gray
                )
            }

        }

    }

}

/**
 * Converts a map of data into a mutable list of [PieChartEntry], where each [PieChartEntry] contains a category name, its corresponding value, and an associated color.
 * The function ensures that if there are more data entries than colors, the colors will be repeated with reduced opacity.
 *
 * @param data A [Map] where the key is a [String] representing the category name, and the value is an [Int] representing the numerical value.
 * @param colors A [List] of [Color] objects used to color the pie chart slices. If the number of data entries exceeds the number of colors, colors are reused with reduced opacity.
 * @return A [MutableList] of [PieChartEntry] objects:
 */
private fun dataToMutableList(
    data: Map<String, Int>,
    colors: List<Color>
): MutableList<PieChartEntry> {
    val list: MutableList<PieChartEntry> = mutableListOf()

    // Adjusting the list of colors to ensure all entries have a color, even if there are more entries than colors.
    data.entries.forEachIndexed { index, entry ->
        list.add(
            PieChartEntry(
                name = entry.key,
                value = entry.value,
                color = if (index >= colors.size) colors[index - colors.size].copy(alpha = 0.5f) else colors[index]
            )
        )

    }

    return list
}

/**
 * A data class representing an entry in a pie chart.
 *
 * @property name The name or label associated with this entry, typically representing a category.
 * @property value The numerical value associated with this entry, determining the size of the pie slice.
 * @property color The color used to represent this entry in the pie chart.
 *
 * The [PieChartEntry] class encapsulates the information for each slice of the pie chart,
 * making it easier to manage and pass around chart data. It is particularly useful when
 * rendering the pie chart and displaying the corresponding chart items.
 */
data class PieChartEntry(
    val name: String,
    val value: Int,
    val color: Color
)

