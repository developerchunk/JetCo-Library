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
 * The [PieChart] composable function renders a pie chart based on the provided data and customization options.
- [modifier]: [Modifier] to apply to the pie chart component.
- [data]: A [Map] containing data for the pie chart, where keys represent categories and values represent corresponding values.
- [radius]: The radius of the pie chart.
- [thickness]: The thickness of the pie chart slices.
- [animationDuration]: The duration of the pie chart animation.
- [colorsList]: An optional list of colors to be used for the pie chart slices.
- [displayChartItems]: A boolean flag indicating whether to display chart items within the Pie Chart.
- [animationRotations]: The number of complete rotations (360 degrees) to be applied to the pie chart during the animation.
 */
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: Map<String, Int>,
    radius: Dp = 90.dp,
    thickness: Dp = 25.dp,
    animationDuration: Int = 1000,
    colorsList: List<Color>? = null,
    displayChartItems: Boolean = true,
    animationRotations: Int = 11
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // Calculate the sweep angle for each slice of the pie chart based on its proportion
    // relative to the total sum of all values. The `floatValue` list stores these angles
    // in degrees (0-360), where each angle represents the portion of the pie chart
    // corresponding to the value at the given index in the `data` map.
    // For a detailed explanation check out the Medium Article. [https://medium.com/@developerchunk/create-custom-pie-chart-with-animations-in-jetpack-compose-android-studio-kotlin-49cf95ef321e]
    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    val colors: List<Color> = colorsList ?: listOf(
        Color(0xFFBB86FC),
        Color(0xFF6200EE),
        Color(0xFF03DAC5),
        Color(0xFF3700B3),
        Color(0xFF007BFF)
    )

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

    val list: MutableList<Triple<String, Int, Color>> = dataToMutableList(
        data = data,
        colors = colors
    )


    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(animateSize.dp),
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
                        color = list[index].third,
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(thickness.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }

        // To see the data in more structured way
        // Compose Function in which Items are showing data
        if (displayChartItems) {
            PieChartItems(
                data = list,
            )
        }

    }

}

private fun dataToMutableList(
    data: Map<String, Int>,
    colors: List<Color>
): MutableList<Triple<String, Int, Color>> {
    val list: MutableList<Triple<String, Int, Color>> = mutableListOf()

    data.entries.forEachIndexed { index, entry ->
        list.add(
            Triple(
                entry.key,
                entry.value,
                if (index >= colors.size) colors[index - colors.size].copy(alpha = 0.5f) else colors[index]
            )
        )

    }

    return list
}

/**
 * The [PieChart] composable function renders a pie chart based on the provided data and customization options, allowing for custom content display of each chart item.
- [modifier]: [Modifier] to apply to the pie chart component.
- [data]: A [Map] containing data for the pie chart, where keys represent categories and values represent corresponding values.
- [radius]: The radius of the pie chart.
- [thickness]: The thickness of the pie chart slices.
- [animationDuration]: The duration of the pie chart animation.
- [colorsList]: An optional list of colors to be used for the pie chart slices.
- [chartItems]: A composable lambda that receives list of the the chart items Triple<String, Int, Color> - text, value, and color, allowing for customizing the content / design for chart items.
- [displayChartItems]: A boolean flag indicating whether to display chart items within the Pie Chart.
- [animationRotations]: The number of complete rotations (360 degrees) to be applied to the pie chart during the animation.
 */
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: Map<String, Int>,
    radius: Dp = 90.dp,
    thickness: Dp = 25.dp,
    animationDuration: Int = 1000,
    colorsList: List<Color>? = null,
    displayChartItems: Boolean = true,
    animationRotations: Int = 11,
    /** This compose function returns list of Triple<String, Int, Color> - [text, value, and the color] of each item*/
    chartItems: @Composable (List<Triple<String, Int, Color>>) -> Unit
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // Calculate the sweep angle for each slice of the pie chart based on its proportion
    // relative to the total sum of all values. The `floatValue` list stores these angles
    // in degrees (0-360), where each angle represents the portion of the pie chart
    // corresponding to the value at the given index in the `data` map.
    // For a detailed explanation check out the Medium Article. [https://medium.com/@developerchunk/create-custom-pie-chart-with-animations-in-jetpack-compose-android-studio-kotlin-49cf95ef321e]
    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    val colors: List<Color> = colorsList ?: listOf(
        Color(0xFFBB86FC),
        Color(0xFF6200EE),
        Color(0xFF03DAC5),
        Color(0xFF3700B3),
        Color(0xFF007BFF)
    )

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

    val list: MutableList<Triple<String, Int, Color>> = dataToMutableList(
        data = data,
        colors = colors
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true

        data.entries.forEachIndexed { index, entry ->
            list[index] = (
                    Triple(
                        entry.key,
                        entry.value,
                        if (index >= colors.size) colors[index - colors.size].copy(alpha = 0.5f) else colors[index]
                    )
                    )
        }

    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(animateSize.dp),
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
                        color = list[index].third,
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(thickness.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }

        // To see the data in more structured way
        // Compose Function in which Items are showing data
        if (displayChartItems) {
            if (chartItems !== {}) {
                chartItems(list)
            } else {
                PieChartItems(
                    data = list,
                )
            }
        }

    }

}

@Composable
fun PieChartItems(
    data: MutableList<Triple<String, Int, Color>>,
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
fun PieChartItem(
    data: Triple<String, Int, Color>,
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
                        color = data.third,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(boxSize)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.second.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color.Gray
                )
            }

        }

    }

}
