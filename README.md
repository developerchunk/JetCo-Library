[![](https://jitpack.io/v/developerchunk/JetCo-Library.svg)](https://jitpack.io/#developerchunk/JetCo-Library)

### Elevate your Compose projects with JetCo-Library
JetCo-Library offers ready-to-use UI components to make your Jetpack Compose apps look amazing. We've started with a Pie Chart and are working on a Modern Bottom Navigation. More cool components are on the way!

Want to help build the future of JetCo-Library? We're an **open-source** library, so your contributions are invaluable and always welcome.

Check us out and see what you think!

***Let's get started with Implementation!***

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

***Check out the Pie Chart***

**JetCo-UI PieChart: It provides a customizable Pie Chart component for your Jetpack Compose applications.**

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
	data = mapOf(
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

Default Preview:<br>

![Screenshot_1673695455 2](https://github.com/user-attachments/assets/010b7a28-a816-4235-8e00-0466dfb8fb11)


<br>
