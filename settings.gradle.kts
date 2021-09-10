rootProject.name = "appcha"

include("server")
include("server-test")

rootProject.children.forEach { project ->
    project.projectDir = file("modules/${project.name}")
}
