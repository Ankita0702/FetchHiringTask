plugins {
    id("com.android.application") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
