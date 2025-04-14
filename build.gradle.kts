plugins {
    id("java")
    id("io.freefair.lombok") version "8.13.1"
}

group = "eu.nyarie"
version = "1.0-SNAPSHOT"
val javaVersion = 23

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.tomlj:tomlj:1.1.1")
    implementation("org.slf4j:slf4j-api:2.0.17")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.2")

    //This isn't even needed since Gradle's logger is being used during test runtime lmao
//    testImplementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.3")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
}

tasks.test {
    useJUnitPlatform()
}