import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "eu.nyarie"
version = "1.0-SNAPSHOT"
val lombokVersion by extra("1.18.38")
val jacksonVersion by extra("2.19.0")

repositories {
    mavenCentral()
}

dependencies {
    implementation("eu.luktronic:logblock:1.0.0-rc.1")

    implementation("org.tomlj:tomlj:1.1.1")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
    implementation("org.jspecify:jspecify:1.0.0")
    //Just for testing purposes, can be removed tbh
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.3")
    implementation(project(":nyarie-api"))

    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.27.2")

    testImplementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.3")
    testImplementation("org.mockito:mockito-core:2.1.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.18.0")

    //Lombok
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    testCompileOnly("org.projectlombok:lombok:${lombokVersion}")
    testAnnotationProcessor("org.projectlombok:lombok:${lombokVersion}")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "eu.nyarie.core.Main"
    }
    // If you have external dependencies and want a "fat jar"
//     from { configurations.runtimeClasspath. { it.isDirectory() ? it : zipTree(it) } }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<Test> {
    jvmArgs("--enable-preview")
}

tasks.withType<JavaExec> {
    jvmArgs("--enable-preview")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        showStandardStreams = true
    }
}