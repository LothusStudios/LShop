plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
    id("com.palantir.git-version") version "0.12.3"
}

group = "studio.lothus"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}


dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    compileOnly(files("F:/API/carbonspigot.jar"))
    implementation ("io.socket:socket.io-client:2.1.0")
    implementation("com.squareup.okhttp3:okhttp:3.12.0")
    implementation("org.bstats:bstats-bungeecord:3.0.2")
    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    compileOnly("org.projectlombok:lombok:1.18.32")
    compileOnly("com.google.code.gson:gson:2.10.1")

    implementation("org.jetbrains:annotations:24.1.0")
}

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
val details = versionDetails()

bukkit {
    main = "studio.lothus.App"
    name = "LothusApp"
    version = "0.1-${details.gitHash.substring(0, 6)}"
    description = "Plugin feito para gerenciar a loja lothus.shop"
    website = "https://lothus.shop"
    load = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.STARTUP
}

tasks {
    shadowJar {
        dependencies {
            exclude("org.projectlombok:lombok:1.18.32")
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}