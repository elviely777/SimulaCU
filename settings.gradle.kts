pluginManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }
}

rootProject.name = "SimulaCU"
include(":app")
includeBuild("ssl-agent")
