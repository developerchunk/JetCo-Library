[![](https://jitpack.io/v/developerchunk/JetCo-Library.svg)](https://jitpack.io/#developerchunk/JetCo-Library)

# Elevate your Compose projects with JetCo-Library
JetCo-Library offers ready-to-use UI components to make your Jetpack Compose apps look amazing. We've started with a ***Pie Chart*** and a customizable ***Column Bar Chart***, with more cool components on the way!

Want to help build the future of JetCo-Library? We're an **open-source** library, so your contributions are invaluable and always welcome.

Check us out and see what you think!

---

### Let's get started with Implementation!

Step 1: Add JitPack to your settings.build.kts / root build.gradle at the end of repositories:
```gradle
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency
```gradle
dependencies {
    implementation("com.github.developerchunk:JetCo-Library:1.0.1")
}
```

---

### *Column Bar Chart*

**JetCo-UI ColumnBarChart: A modern and customizable Column Bar Chart component for your Jetpack Compose applications.**

***Key Features:***
* Customizable bar config: height, width, and color
* Configurable X and Y axes with optional scale and grid lines
* Pop-up text on the bar and X-axis label clicks
* Animation support for bar entry and exit
* Option to rotate X-axis labels for better readability
* Easy integration into your Jetpack Compose projects
* And many more customizable parameters are available

Basic Usage
````kotlin
ColumnBarChart(
    chartData = mapOf(
        "Test-1" to 25,
        "Test-2" to 50,
        "Test-3" to 10,
        "Test-4" to 75,
        "Test-5" to 20,
    )
)
````
***Parameters that you can use to customize your Column Bar Chart:***

* **modifier**: Modifier to apply to the chart component.
* **chartData**: Map<String, Int> - The data to be displayed, with keys as labels for the X-axis and values as corresponding bar heights.
* **barColor**: Color - The color of the bars. Default is a light pink (0xFFEFB8C8).
* **barHeight**: Dp - The height of the bars. Default is 200.dp.
* **barWidth**: Dp - The width of the bars. Default is 20.dp.
* **maxBarValue**: Int? - Maximum value for the bars. Default is calculated based on the data if null.
* **userScrollEnable**: Boolean - Enables horizontal scrolling. Default is true.
* **yAxisScaleEnable**: Boolean - Displays the Y-axis scale. Default is true.
* **yAxisLineEnable**: Boolean - Displays the Y-axis line. Default is true.
* **xAxisScaleEnable**: Boolean - Displays the X-axis labels. Default is true.
* **xAxisLineEnable**: Boolean - Displays the X-axis line. Default is true.
* **barShape**: Shape - The shape of the bars. Default is rounded rectangle.
* **axisLineWidth**: Dp - The width of the axis lines. Default is 6.dp.
* **yAxisLineShape**: Shape - The shape of the Y-axis line. Default is rounded at the top.
* **xAxisLineShape**: Shape - The shape of the X-axis line. Default is fully rounded.
* **axisLineColor**: Color - The color of the axis lines. Default is LightGray.
* **enterAnimation**: EnterTransition - The animation for bar entry. Default is a vertical expansion.
* **exitAnimation**: ExitTransition - The animation for bar exit. Default is a vertical shrink.
* **enableXAxisPopUp**: Boolean - Enables pop-up text on X-axis label clicks. Default is true.
* **enableBarPopUp**: Boolean - Enables pop-up text on bar clicks. Default is true.
* **popUpColor**: Color - The background color of the pop-up. Default is a light purple (0xFFCCC2DC).
* **popUpTextColor**: Color - The color of the text inside the pop-up. Default is Black.
* **fontSize**: TextUnit - The font size for the axis labels. Default is 14.sp.
* **fontFamily**: FontFamily - The font family for the axis labels. Default is FontFamily.Default.
* **fontWeight**: FontWeight - The font weight for the axis labels. Default is FontWeight.Normal.
* **fontColor**: Color - The color of the axis labels. Default is Black.
* **enableAnimation**: Boolean - Enables animations for the bars. Default is true.
* **maxTextLinesXAxis**: Int - The maximum number of characters for X-axis labels before truncation. Default is 6.
* **yAxisScaleCount**: Int - The number of divisions on the Y-axis scale. Default is 4.
* **enableTextRotate**: Boolean - Rotates X-axis labels. Default is true.
* **textRotateAngle**: Float - The angle for X-axis label rotation. Default is -60f.
* **enableGridLines**: Boolean - Enables grid lines behind the bars. Default is true.
* **gridLineStyle**: GridLineStyle - The style for grid lines including color, stroke width, dash length, and gap length.

**Preview: **

<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/6a971513-0088-4711-b333-40c8e777743d" alt="Description 1" width="300"/></td>
    <td><img src="https://github.com/user-attachments/assets/692aca80-c06f-45e8-8007-61652779bd92" alt="Description 2" width="300"/></td>
    <td><img src="https://github.com/user-attachments/assets/c4c0d033-f977-4aa3-bdf1-ae43321ed23a" alt="Description 3" width="300"/></td>
  </tr>
</table>

### *Pie Chart*

**JetCo-UI PieChart: It provides a modern and customizable Pie Chart component for your Jetpack Compose applications.**

***Key Features:***
  * Customizable radius, thickness, and animation duration
  * Flexible data input through a Map of categories and values
  * Support for custom content within chart items using chartItem lambda
  * Option to display or hide chart items
  * Easy integration into your Jetpack Compose projects
<br>

Basic Usage
````kotlin
PieChart(
	chartData = mapOf(
		Pair("Sample-1", 110),
		Pair("Sample-2", 120),
		Pair("Sample-3", 130),
		Pair("Sample-4", 140),
		Pair("Sample-5", 150),
	)
)
````
***Parameters that you use to customize your Pie Chart:***
* **modifier**: Modifier to apply to the pie chart component.
* **data**: A Map containing data for the pie chart, where keys represent categories and values represent corresponding values.
* **radius**: Dp - The radius of the pie chart.
* **thickness**: Dp - The thickness of the pie chart slices.
* **animationDuration**: Int - The duration of the pie chart animation in milliseconds.
* **colorsList**: List<Color> - An optional list of colors to be used for the pie chart slices.
* **displayChartItems**: Boolean - A flag indicating whether to display chart items within the Pie Chart.
* **chartItems**: (List<Triple<String, Int, Color>>) -> Unit - A composable lambda that receives a list of the chart items (text, value, and color), allowing for customizing the content design for chart items.

**Preview:**

![Screenshot_1673695455 2](https://github.com/user-attachments/assets/010b7a28-a816-4235-8e00-0466dfb8fb11)

