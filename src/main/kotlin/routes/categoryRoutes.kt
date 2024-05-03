package routes

// src/main/kotlin/routes/CategoryRoutes.kt
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import services.CategoryService

fun Route.categoryRoutes() {
    val categoryService = CategoryService()

    route("/categories") {
        get {
            val allCategories = categoryService.getAllCategories()
            call.respond(allCategories)
        }
    }
}