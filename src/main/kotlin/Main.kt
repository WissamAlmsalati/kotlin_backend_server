package org.example

import UserService
import services.ProductService
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.CORS
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.example.routes.productRoutes
import userRoutes


fun Application.module() {
    install(ContentNegotiation) {
        jackson {

        }
    }

    install(CORS) {
    method(HttpMethod.Options)
    method(HttpMethod.Put)
    method(HttpMethod.Delete)
    method(HttpMethod.Patch)
    header(HttpHeaders.Authorization)
    allowCredentials = true
    host("localhost:8080") // replace with your host
}

    val userService = UserService()
    val productService = ProductService()

    install(Routing) {
        userRoutes( userService)
        productRoutes(productService)
    }
}

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}