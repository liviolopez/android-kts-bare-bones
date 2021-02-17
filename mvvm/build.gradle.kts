buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Dep.plugin("Kotlin"))
        classpath(Dep.plugin("Gradle"))
        classpath(Dep.plugin("Navigation"))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}