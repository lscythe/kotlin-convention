gradlePlugin {
    plugins {
        register("pluginModule") {
            id = "convention.plugin-module"
            implementationClass = "PluginModuleConventionPlugin"
        }
        register("publishing") {
            id = "convention.publishing"
            implementationClass = "PublishingConventionPlugin"
        }
    }
}
