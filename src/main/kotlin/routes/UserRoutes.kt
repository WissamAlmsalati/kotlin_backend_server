package org.example.routes

import io.ktor.application.*
import io.ktor.http.HttpStatusCode
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*
import org.example.models.LoginRequest
import org.example.models.UpdateRequest
import org.example.models.User
import services.UserService

fun Route.userRoutes(userService: UserService) {
    route("/users") {
        post {
            val user = call.receive<User>()
            userService.addUser(user)
            call.respond(HttpStatusCode.Created)
        }

        get {
            call.respond(userService.viewUsers())
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val user = call.receive<User>()
            if (id != null) {
                userService.updateUser(id, user)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                userService.deleteUser(id)
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
            }
        }

        post("/signup") {
            val user = call.receive<User>()
            val result = userService.signup(user)
            call.respond(result)
        }

        post("/login") {
            val loginRequest = call.receive<LoginRequest>()
            val result = userService.login(loginRequest.email, null, loginRequest.password)
            call.respond(result)
        }

        put("/{id}/update") {
            val id = call.parameters["id"]?.toIntOrNull()
            val updateRequest = call.receive<UpdateRequest>()
            if (id != null) {
                val result = userService.updateUser(id, updateRequest.username, updateRequest.password)
                call.respond(result)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
            }
        }
    }
}
