import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.8.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "studio.lothus.delivery"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp")
    }
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("me.devnatan:inventory-framework-platform-bukkit:3.2.0")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    compileOnly(files("F:/API/carbonspigot.jar"))
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

val shadowJarTask = tasks.getByName<ShadowJar>("shadowJar")

tasks.register<Copy>("copyToPluginsDir") {
        dependsOn(shadowJarTask)
        from(shadowJarTask.outputs.files)
}
