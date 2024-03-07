plugins {
    alias(libs.plugins.algomate)
    alias(libs.plugins.jagr)
    alias(libs.plugins.javafxplugin)
    application
}

exercise {
    assignmentId.set("projekt")
}

submission {
    // ACHTUNG!
    // Setzen Sie im folgenden Bereich Ihre TU-ID (NICHT Ihre Matrikelnummer!), Ihren Nachnamen und Ihren Vornamen
    // in Anführungszeichen (z.B. "ab12cdef" für Ihre TU-ID) ein!
    studentId = "ns70seky"
    firstName = "Nicolas"
    lastName = "Schmidt"

    // Optionally require own tests for mainBuildSubmission task. Default is false
    requireTests = false
}

configurations.all {
    resolutionStrategy {
        configurations.all {
            resolutionStrategy {
                force(
                    libs.algoutils.student,
                    libs.algoutils.tutor,
                    libs.junit.pioneer,
                )
            }
        }
    }
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml", "javafx.swing")
}

jagr {
    graders {
        val graderPublic by getting {
            graderName.set("Projekt-Public")
            rubricProviderName.set("projekt.Projekt_RubricProviderPublic")
        }
    }
}

tasks {
    javadoc {
        options.jFlags?.add("-Duser.language=en")
        options.optionFiles = mutableListOf(project.file("src/main/javadoc.options"))
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<Javadoc> {
        options.encoding = "UTF-8"
    }
}

application {
    mainClass.set("projekt.Main")
}
