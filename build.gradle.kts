plugins {
    id("java")
}

group = "eu.nyarie"
version = "1.0-SNAPSHOT"
val javaVersion = 23

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
}

tasks.test {
    useJUnitPlatform()
}