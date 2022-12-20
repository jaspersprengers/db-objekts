plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka") version "1.7.20"
}


dependencies {

    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.h2database:h2:2.1.214")
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("commons-io:commons-io:2.11.0")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("ch.qos.logback:logback-core:1.4.5")
    implementation("org.slf4j:slf4j-api:2.0.5")

    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.20")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

}


tasks.test {
    useJUnitPlatform()
}


