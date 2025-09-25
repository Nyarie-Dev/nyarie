val javaVersion by extra(25)

subprojects {
    apply(plugin = "java")

    plugins.withType<JavaPlugin>() {
        configure<JavaPluginExtension>() {
            toolchain {
                languageVersion = JavaLanguageVersion.of(javaVersion)
            }
        }
    }
}

tasks.register("publishToMavenLocal") {
    group = "publishing"
    description = "Executes the 'publishToMavenLocal' task in all subprojects."

    dependsOn(
        ":nyarie-api:publishToMavenLocal",
        ":nyarie-core:publishToMavenLocal"
    )

    doLast {
        logger.info("Published all subprojects to Maven local repository")
    }
}