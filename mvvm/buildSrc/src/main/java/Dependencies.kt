import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Sdk {
    const val compile = 30
    const val target = 30
    const val min = 24

    const val buildTool = "30.0.2"
}

object Dep {
    fun Project.addDependencies(configName: String = "implementation") {
        dependencies {
            allLibs().forEach { (type, lib) -> if(type == "impl") add(configName, lib) else kapt(lib) }

            add("testImplementation", Test.junit)
            add("androidTestImplementation", Test.extJunit)
            add("androidTestImplementation", Test.espressoCore)
        }
    }

    private val libs = mutableMapOf<String, MutableMap<String, MutableList<String>>>()
    fun plugin(group:String):String = (libs[group] ?: error(""))[plugin]!!.first()

    private fun allLibs(excludeGroup: List<String> = emptyList()): MutableList<Pair<String, String>> {
        val allLibs = mutableListOf<Pair<String, String>>()

        libs.filter { !excludeGroup.contains(it.key) }.forEach { d ->
            d.value.filter { it.key != "plugin" }.forEach { (type, libs) ->
                libs.forEach { lib ->
                    allLibs.add(Pair(type, lib))
                    println("${if(type == "impl") "implementation" else "kapt"}(\"${lib}\")")
                }
            }
        }

        return allLibs
    }

    private const val plugin = "plugin"
    private const val impl = "impl"
    private const val kapt = "kapt"

    const val kotlin = "1.4.30"

    init {
        libs["Kotlin"] = hashMapOf(
            plugin to mutableListOf(
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin}"),

            impl to mutableListOf(
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${kotlin}",
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")
        )

        val gradle = "4.1.2"
        libs["Gradle"] = hashMapOf(
            plugin to mutableListOf(
            "com.android.tools.build:gradle:${gradle}")
        )

        libs["Basic"] = hashMapOf(
            impl to mutableListOf(
            "com.google.code.gson:gson:2.8.6",
            "com.google.android.material:material:1.3.0",

            // AndroidX
            "androidx.core:core-ktx:1.3.2",
            "androidx.appcompat:appcompat:1.2.0",
            "androidx.constraintlayout:constraintlayout:2.0.4",
            "androidx.legacy:legacy-support-v4:1.0.0",
            "androidx.fragment:fragment-ktx:1.2.5")
        )

        val navigation = "2.3.2"
        libs["Navigation"] = hashMapOf(
            plugin to mutableListOf(
            "androidx.navigation:navigation-safe-args-gradle-plugin:${navigation}"),

            impl to mutableListOf(
            "androidx.navigation:navigation-fragment-ktx:${navigation}",
            "androidx.navigation:navigation-ui-ktx:${navigation}")
        )

        val retrofit = "2.9.0"
        libs["Retrofit"] = hashMapOf(
            impl to mutableListOf(
            "com.squareup.retrofit2:retrofit:${retrofit}",
            "com.squareup.retrofit2:converter-gson:${retrofit}",
            "com.squareup.okhttp3:logging-interceptor:4.9.0")
        )

        val lifecycle = "2.2.0"
        libs["Lifecycle"] = hashMapOf(
            impl to mutableListOf(
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycle}",
                "androidx.lifecycle:lifecycle-livedata-ktx:${lifecycle}")
        )

        val glide = "4.11.0"
        libs["Glide"] = hashMapOf(
            impl to mutableListOf(
                "com.github.bumptech.glide:glide:${glide}",
                "com.github.bumptech.glide:okhttp3-integration:${glide}"),

            kapt to mutableListOf(
                "com.github.bumptech.glide:compiler:${glide}")
        )
    }

    object Test {
        const val junit = "junit:junit:4.13.1"
        const val extJunit = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }
}