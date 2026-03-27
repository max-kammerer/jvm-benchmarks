plugins {
    id("java")
    id("com.google.protobuf") version "0.9.4"
    id("me.champeau.jmh") version "0.7.2"
    id ("com.github.johnrengelman.shadow") version "8.1.1"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

sourceSets {
    main {
        proto {
            srcDir("src/main/resources/proto")
        }
    }
}

jmh {
    includes = listOf(".*VarHandleBenchmark.*")
}

dependencies {
    implementation("at.yawk.lz4:lz4-java:1.10.2")
    implementation("ru.odnoklassniki:one-nio:2.2.0")
    implementation("com.google.protobuf:protobuf-java:4.28.2")

    implementation("org.openjdk.jmh:jmh-core:1.37")
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.28.2"
    }
}