plugins {
    id("java")
    id("maven-publish")
}

group = "eu.nyarie"
version = "1.0-SNAPSHOT"
val javaVersion = 24
val lombokVersion = "org.projectlombok:lombok:1.18.38"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.tomlj:tomlj:1.1.1")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation(project(":nyarie-api"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.2")

    testImplementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.3")

    //Lombok
    compileOnly(lombokVersion)
    annotationProcessor(lombokVersion)
    testCompileOnly(lombokVersion)
    testAnnotationProcessor(lombokVersion)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
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
}