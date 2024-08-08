package com.developerstring.jetco_ui.charts

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup

/**
 * Default shape for the bars in the column bar chart.
 * Rounded corners at the top (6.dp) and bottom (2.dp) give a visually appealing bar shape.
 */
private val defaultBarShape = RoundedCornerShape(
    topStart = 6.dp, topEnd = 6.dp, bottomStart = 2.dp, bottomEnd = 2.dp
)

/**
 * Default shape for the Y-axis line, rounded at the top (3.dp).
 */
private val defaultYAxisShape = RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp)

/**
 * Default shape for the X-axis line, fully rounded (3.dp) for a smooth appearance.
 */
private val defaultXAxisShape = RoundedCornerShape(3.dp)

/**
 * Default enter transition for animating the bars in the column bar chart.
 *
 * This transition expands the bars vertically over 1000 milliseconds when they
 * first appear on the screen. It provides a smooth and visually appealing
 * entrance for the bars in the chart.
 *
 * @see EnterTransition
 */
private val defaultEnterTransition = expandVertically(animationSpec = tween(durationMillis = 1000))

/**
 * A customizable Column Bar Chart component built with Jetpack Compose.
 *
 * This composable displays a bar chart with optional X and Y axes, allowing users
 * to visualize data in a vertical bar format. The chart supports features such as
 * animation, user scroll, pop-up text on click, and customizable appearance using [ColumnBarChart].
 *
 * @param modifier The [Modifier] to be applied to the chart.
 * @param barData The data to be displayed in the chart, where the keys are labels for the X-axis and the values are the corresponding bar heights.
 * @param barColor The color of the bars in the chart. Default is a light pink color (0xFFEFB8C8).
 * @param barHeight The height of the bars in the chart. Default is 200.dp.
 * @param barWidth The width of the bars in the chart. Default is 20.dp.
 * @param maxBarValue The maximum value for the bars. If null, it will be computed based on the data. Default is null.
 * @param userScrollEnable Whether horizontal scrolling is enabled for the bars. Default is true.
 * @param yAxisScaleEnable Whether the Y-axis scale should be displayed. Default is true.
 * @param yAxisLineEnable Whether the Y-axis line should be displayed. Default is true.
 * @param xAxisScaleEnable Whether the X-axis labels should be displayed. Default is true.
 * @param xAxisLineEnable Whether the X-axis line should be displayed. Default is true.
 * @param barShape The shape of the bars. Default is [defaultBarShape], which is a rounded rectangle.
 * @param axisLineWidth The width of the axis lines. Default is 6.dp.
 * @param yAxisLineShape The shape of the Y-axis line. Default is [defaultYAxisShape], which is rounded at the top.
 * @param xAxisLineShape The shape of the X-axis line. Default is [defaultXAxisShape], which is fully rounded.
 * @param axisLineColor The color of the axis lines. Default is LightGray.
 * @param enterAnimation The enter animation for the bars when they appear. Default is a vertical expansion animation.
 * @param exitAnimation The exit animation for the bars when they disappear. Default is a vertical shrink animation.
 * @param enableXAxisPopUp Whether to enable pop-up text on clicking the X-axis labels. Default is true.
 * @param enableBarPopUp Whether to enable pop-up text on clicking the bars. Default is true.
 * @param popUpColor The background color of the pop-up. Default is a light purple color (0xFFCCC2DC).
 * @param popUpTextColor The color of the text inside the pop-up. Default is Black.
 * @param fontSize The font size for the text labels on the X and Y axes. Default is 14.sp.
 * @param fontFamily The font family for the text labels on the X and Y axes. Default is [FontFamily.Default].
 * @param fontWeight The font weight for the text labels on the X and Y axes. Default is [FontWeight.Normal].
 * @param fontColor The color of the text labels on the X and Y axes. Default is Black.
 * @param enableAnimation Whether to enable animations for the bars. Default is true.
 * @param maxTextLinesXAxis The maximum number of characters to display for each X-axis label before truncating with an ellipsis. Default is 6.
 * @param yAxisScaleCount The number of divisions on the Y-axis scale. Default is 4.
 */
@Composable
fun ColumnBarChart(
    modifier: Modifier = Modifier,
    barData: Map<String, Int>,
    barColor: Color = Color(0xFFEFB8C8),
    barHeight: Dp = 200.dp,
    barWidth: Dp = 20.dp,
    maxBarValue: Int? = null,
    userScrollEnable: Boolean = true,
    yAxisScaleEnable: Boolean = true,
    yAxisLineEnable: Boolean = true,
    xAxisScaleEnable: Boolean = true,
    xAxisLineEnable: Boolean = true,
    barShape: Shape = defaultBarShape,
    axisLineWidth: Dp = 6.dp,
    yAxisLineShape: Shape = defaultYAxisShape,
    xAxisLineShape: Shape = defaultXAxisShape,
    axisLineColor: Color = Color.LightGray,
    enterAnimation: EnterTransition = defaultEnterTransition,
    exitAnimation: ExitTransition = shrinkVertically(),
    enableXAxisPopUp: Boolean = true,
    enableBarPopUp: Boolean = true,
    popUpColor: Color = Color(0xFFCCC2DC),
    popUpTextColor: Color = Color.Black,
    fontSize: TextUnit = 14.sp,
    fontFamily: FontFamily = FontFamily.Default,
    fontWeight: FontWeight = FontWeight.Normal,
    fontColor: Color = Color.Black,
    enableAnimation: Boolean = true,
    maxTextLinesXAxis: Int = 6,
    yAxisScaleCount: Int = 4
) {

    // State for managing the popup
    var isYAxisPopupVisible by remember { mutableStateOf(false) }
    var yAxisPopupText by remember { mutableStateOf("") }

    // Determine the maximum value in the data set, or use the provided max value
    val maxValue: Int = maxBarValue ?: try {
        barData.values.max()
    } catch (_: Exception) {
        1
    }

    // Transform the data into a list of BarChartItems with normalized / float heights
    val barList = barData.mapToBarChartItems(maxValue = maxValue)

    // State for managing whether the animation should be displayed
    var animationDisplay by remember {
        mutableStateOf(!enableAnimation)
    }

    // Trigger the animation when the composable is first launched
    if (enableAnimation) {
        LaunchedEffect(key1 = Unit) {
            animationDisplay = true
        }
    }

    // Calculate the Y-axis scale step based on the maximum value and the number of steps
    val yAxisScaleStep = maxValue.toFloat() / yAxisScaleCount
    val yAxisStepHeight = barHeight / yAxisScaleCount

    Column(modifier = modifier) {

        Box(contentAlignment = Alignment.TopStart) {

            // Y-axis and bars container
            Row(horizontalArrangement = Arrangement.Start) {

                // Y-axis scale and line
                if (yAxisScaleEnable) {
                    Row {
                        // LazyColumn for the Y-axis scale labels
                        LazyColumn(horizontalAlignment = Alignment.End) {
                            items(yAxisScaleCount + 1) { index ->
                                val barScale = (yAxisScaleCount) - index
                                Row(
                                    modifier = Modifier.height(height = yAxisStepHeight),
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Text(
                                        text = (yAxisScaleStep * barScale).toString(),
                                        textAlign = TextAlign.End,
                                        fontSize = fontSize,
                                        fontFamily = fontFamily,
                                        fontWeight = fontWeight,
                                        color = fontColor
                                    )
                                }
                            }
                        }

                        // Y-axis line
                        if (yAxisLineEnable) {
                            Spacer(modifier = Modifier.width(10.dp))

                            Box(
                                modifier = Modifier
                                    .padding(top = yAxisStepHeight)
                                    .clip(shape = yAxisLineShape)
                                    .width(axisLineWidth)
                                    .height(barHeight)
                                    .background(axisLineColor)
                            )
                        }
                    }
                }

                // Bars and X-axis labels container
                LazyRow(
                    modifier = Modifier
                        .padding(top = yAxisStepHeight)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    userScrollEnabled = userScrollEnable
                ) {
                    items(barList, key = { keyItem ->
                        keyItem.name
                    }) { barItem ->

                        // State for managing the popup for each bar
                        var isBarPopupVisible by remember { mutableStateOf(false) }
                        var barPopupText by remember { mutableStateOf("") }

                        // Bar UI
                        Column(
                            modifier = Modifier
                                .wrapContentSize(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            // Display the popup when a bar is clicked
                            if (isBarPopupVisible&&enableBarPopUp) {
                                Popup(
                                    alignment = Alignment.Center,
                                    onDismissRequest = { isBarPopupVisible = false },
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(popUpColor, shape = RoundedCornerShape(8.dp))
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = barPopupText,
                                            fontSize = fontSize,
                                            fontFamily = fontFamily,
                                            fontWeight = fontWeight,
                                            color = popUpTextColor
                                        )
                                    }
                                }
                            }

                            // Bar Box
                            Box(
                                modifier = Modifier
                                    .height(barHeight)
                                    .width(barWidth),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                // Lambda to create the bar with the specified shape, color, and click handling
                                val barBox: @Composable () -> Unit = {
                                    Box(
                                        modifier = Modifier
                                            .clip(shape = barShape)
                                            .fillMaxHeight(barItem.floatValue)
                                            .fillMaxWidth()
                                            .background(
                                                barColor
                                            )
                                            .clickable {
                                                if(enableBarPopUp) {
                                                    barPopupText = barItem.value.toString()
                                                    isBarPopupVisible = true
                                                }
                                            }
                                    )
                                }

                                // Animate the bar if animations are enabled
                                if (enableAnimation) {
                                    androidx.compose.animation.AnimatedVisibility(
                                        visible = animationDisplay,
                                        enter = enterAnimation,
                                        exit = exitAnimation
                                    ) {
                                        barBox()
                                    }
                                } else {
                                    barBox()
                                }
                            }
                            // X-axis labels below each bar
                            if (xAxisScaleEnable) {
                                Spacer(modifier = Modifier.height(axisLineWidth + 5.dp))
                                val itemName = barItem.name
                                Text(
                                    text = if (itemName.length>maxTextLinesXAxis) "${itemName.take(maxTextLinesXAxis-2)}..." else itemName,
                                    fontSize = fontSize,
                                    fontFamily = fontFamily,
                                    fontWeight = fontWeight,
                                    color = fontColor,
                                    modifier = Modifier.clickable {
                                        if(enableXAxisPopUp) {
                                            yAxisPopupText = barItem.name
                                            isYAxisPopupVisible = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Popup to show full text for X-axis labels
            if (isYAxisPopupVisible && enableXAxisPopUp) {
                Popup(
                    alignment = Alignment.Center,
                    onDismissRequest = { isYAxisPopupVisible = false },
                ) {
                    Box(
                        modifier = Modifier
                            .background(popUpColor, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = yAxisPopupText,
                            fontSize = fontSize,
                            fontFamily = fontFamily,
                            fontWeight = fontWeight,
                            color = popUpTextColor
                        )
                    }
                }
            }

            // X-axis line at the bottom of the chart
            if (xAxisLineEnable) {
                Box(
                    modifier = Modifier
                        .padding(top = barHeight + yAxisStepHeight)
                        .fillMaxWidth()
                        .height(axisLineWidth)
                        .clip(shape = xAxisLineShape)
                        .background(axisLineColor)
                )
            }


        }
    }
}

/**
 * Extension function to map a [Map] of bar data to a list of [BarChartItems].
 *
 * This function takes a map where the keys represent the labels of the bars (X-axis labels)
 * and the values represent the corresponding bar heights. It then normalizes these values
 * based on the provided maximum value and returns a list of [BarChartItems] that can be used
 * to render the bars in the chart.
 *
 * @param maxValue The maximum value among all bars, used to normalize the bar heights.
 * @return A list of [BarChartItems], each containing the original name, value, and a normalized float value.
 */
private fun Map<String, Int>.mapToBarChartItems(maxValue: Int): List<BarChartItems> =
    mapValues { (key, value) ->
        BarChartItems(
            name = key,
            value = value,
            floatValue = (value.toFloat() / maxValue.toFloat())
        )
    }.values.toList()

/**
 * Data class representing an individual bar in the column bar chart.
 *
 * @property name The label for the bar (used on the X-axis).
 * @property value The actual value of the bar.
 * @property floatValue The normalized value of the bar, computed as a fraction of the maximum value.
 */
@Stable
private data class BarChartItems(
    val name: String,
    val value: Int,
    val floatValue: Float
)