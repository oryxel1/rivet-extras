plugins {
    id("java")
}

group = "oxy.rivet.extras"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.lenni0451.net/snapshots")
}

dependencies {
    compileOnly("com.github.Lenni0451.rivet:backend-thingl:6529dcec5e")
    compileOnly("com.github.Lenni0451.rivet:core:6529dcec5e")
    compileOnly("org.projectlombok:lombok:1.18.46")
    annotationProcessor("org.projectlombok:lombok:1.18.46")

    testImplementation("com.github.Lenni0451.rivet:core:6529dcec5e")
    testImplementation("com.github.Lenni0451.rivet:backend-thingl-glfw:6529dcec5e")
    testImplementation("org.lwjgl:lwjgl-glfw:3.4.1")
    listOf("natives-windows", "natives-windows-arm64", "natives-linux", "natives-linux-arm64").forEach {
        testImplementation("org.lwjgl:lwjgl:3.4.1:$it")
        testImplementation("org.lwjgl:lwjgl-glfw:3.4.1:$it")
        testImplementation("org.lwjgl:lwjgl-opengl:3.4.1:$it")
        testImplementation("org.lwjgl:lwjgl-stb:3.4.1:$it")
        testImplementation("org.lwjgl:lwjgl-freetype:3.4.1:$it")
    }
}