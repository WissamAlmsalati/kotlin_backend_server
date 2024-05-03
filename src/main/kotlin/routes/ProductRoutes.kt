package org.example.routes

import Product
import ProductDTO
import services.ProductService
import io.ktor.application.*
import io.ktor.http.HttpStatusCode
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*


fun Route.productRoutes(productService: ProductService) {
    route("/products") {
        post {
            val productDTO = call.receive<ProductDTO>()
            productService.addProduct( productDTO)
            call.respond(HttpStatusCode.Created)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val productDTO = call.receive<Product>()
            if (id != null) {
                productService.updateProduct(id, productDTO)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                productService.deleteProduct(id)
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
            }
        }

        get {
            call.respond(productService.viewProducts())
        }
    }
}
