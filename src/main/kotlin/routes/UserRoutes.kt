import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import org.example.models.LoginRequest
import org.example.models.SignUpRequest
import org.example.models.UserModel

fun Route.userRoutes(connection: UserService) {
    val userService = UserService()

    route("/users") {

        post("/signup") {
    val signUpRequest = call.receive<SignUpRequest>()
    val user = UserModel(
        id = signUpRequest.id,
        email = signUpRequest.email,
        password = signUpRequest.password,
        phone = signUpRequest.phone,
        username = signUpRequest.username
    )
    val createdUser = userService.signUp(user)
            System.out.println(createdUser)
    call.respond(HttpStatusCode.Created, createdUser)
}

post("/login") {
    val loginRequest = call.receive<LoginRequest>()
    val user = userService.login(loginRequest)
    if (user != null) {
        call.respond(user)
    } else {
        call.respond(HttpStatusCode.Unauthorized, "Invalid email or password.")
    }
}
        get("/{id}") {
            val userId = call.parameters["id"]?.toInt() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val user = userService.getUserById(userId) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(user)
        }
        delete("/{id}") {
            val userId = call.parameters["id"]?.toInt() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            val deletedUser = userService.deleteUser(userId)
            if (deletedUser != null) {
                call.respond(deletedUser)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        get {
            val allUsers = userService.getAllUsers()
            call.respond(allUsers)
        }
    }
}
