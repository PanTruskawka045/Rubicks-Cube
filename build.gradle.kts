plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.18"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.3.0"
}

group = "me.pan_truskawka045"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
}

bukkitPluginYaml {
    main = "me.pan_truskawka045.RubicksCube.RubicksCubePlugin"
    authors.add("PanTruskawka045")
    apiVersion = "1.21.5"
    version = "1.0.0"
    name = "RubicksCube"
}

tasks.test {
    useJUnitPlatform()
}