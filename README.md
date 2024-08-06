[![](https://jitpack.io/v/developerchunk/JetCo-Library.svg)](https://jitpack.io/#developerchunk/JetCo-Library)

### Elevate your Compose projects with JetCo-UI

  **JetCo-UI PieChart: It provides a customizable Pie Chart component for your Jetpack Compose applications.**

Key Features:
  * Customizable radius, thickness, and animation duration
  * Flexible data input through a Map of categories and values
  * Support for custom content within chart slices using chartItem lambda
  * Option to display or hide chart items
  * Easy integration into your Jetpack Compose projects
<br>
Preview:<be>

![screenshot](https://github.com/user-attachments/assets/cd6aa475-6ae3-4d66-81ba-cc5ef272d533)


<br>

Let's get started!

Step 1: Add it to your settings.build.kts / root build.gradle at the end of repositories:
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
