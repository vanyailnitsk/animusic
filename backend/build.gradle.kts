
plugins {
    id("java")
    id("idea")
    id("io.freefair.lombok") version "8.2.0"
    id("org.springframework.boot") version "3.2.6"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

apply {
    from("dependencies.gradle")
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

group = "com.animusic"
version = "1.0"
description = "backend"

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnitPlatform()

    jvmArgs = listOf("-Xmx512m", "-Xms256m")

    environment("spring.profiles.active", "testing")

    filter {
        includeTestsMatching("*Test")
        isFailOnNoMatchingTests = false
    }

    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams = true
    }

    maxParallelForks = Runtime.getRuntime().availableProcessors()
}

tasks.register<JavaExec>("runDev") {
    group = "application"
    description = "Run the application with the 'dev' profile"

    mainClass.set("animusic.api.AnimusicApplication")

    classpath = sourceSets["main"].runtimeClasspath
    val s3Secret = System.getenv("S3_SECRET")
    environment("SPRING_PROFILES_ACTIVE", "dev")
    jvmArgs = listOf("-Dspring.profiles.active=dev","-Dtimeweb.s3.secret-key=$s3Secret")
    workingDir = file(".")
}


tasks.withType<JavaCompile> {
    options.forkOptions.jvmArgs!!.addAll(listOf(
        "--add-opens", "java.base/java.lang=ALL-UNNAMED",
        "--add-opens", "java.base/java.util=ALL-UNNAMED",
        "--add-opens", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED"
    ))
}


tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

