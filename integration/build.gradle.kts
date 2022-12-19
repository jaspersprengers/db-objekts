plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":core"))


    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.h2database:h2:2.1.214")
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("commons-io:commons-io:2.11.0")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("ch.qos.logback:logback-core:1.4.5")
    implementation("org.slf4j:slf4j-api:2.0.5")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

    testImplementation("org.mariadb.jdbc:mariadb-java-client:3.0.8")
    testImplementation("org.testcontainers:junit-jupiter:1.16.2")
    testImplementation("org.testcontainers:mariadb:1.17.6")
}

tasks.test {
    useJUnitPlatform()
}


kotlin.sourceSets["test"].kotlin {
    srcDir("src/generated-sources/kotlin")
}
