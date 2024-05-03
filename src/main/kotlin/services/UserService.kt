import org.example.models.LoginRequest
import org.example.models.UserModel
import java.sql.DriverManager

class UserService() {

    val url = "jdbc:mysql://localhost:3306/kotlin_app" // replace with your database URL
    val dbUsername = "root" // replace with your database username
    val dbPassword = "11111111" // replace with your database password

    val connection = DriverManager.getConnection(url, dbUsername, dbPassword)

    fun signUp(user: UserModel): UserModel {
        val query = "INSERT INTO users (id, username, email, password, phone) VALUES (?, ?, ?, ?, ?)"
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, user.id) // changed from setString to setInt
        preparedStatement.setString(2, user.username)
        preparedStatement.setString(3, user.email)
        preparedStatement.setString(4, user.password)
        preparedStatement.setString(5, user.phone)
        preparedStatement.executeUpdate()
        return user
    }

    fun login(loginRequest: LoginRequest): UserModel? {
    val query = "SELECT * FROM users WHERE email = ? AND password = ?"
    val preparedStatement = connection.prepareStatement(query)
    preparedStatement.setString(1, loginRequest.email)
    preparedStatement.setString(2, loginRequest.password)
    val resultSet = preparedStatement.executeQuery()
    return if (resultSet.next()) {
        UserModel(
            id = resultSet.getInt("id"),
            username = resultSet.getString("username"),
            email = resultSet.getString("email"),
            password = resultSet.getString("password"),
            phone = resultSet.getString("phone")
        )
    } else {
        null
    }
}

    fun getAllUsers(): List<UserModel> {
        val query = "SELECT * FROM users"
        val preparedStatement = connection.prepareStatement(query)
        val resultSet = preparedStatement.executeQuery()
        val users = mutableListOf<UserModel>()
        while (resultSet.next()) {
            val user = UserModel(
                id = resultSet.getInt("id"), // changed from getString to getInt
                username = resultSet.getString("username") ?: "",
                email = resultSet.getString("email") ?: "",
                password = resultSet.getString("password") ?: "",
                phone = resultSet.getString("phone") ?: ""
            )
            users.add(user)
        }
        return users
    }

    fun getUserById(userId: Int): UserModel? { // changed from String to Int
        val query = "SELECT * FROM users WHERE id = ?"
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, userId) // changed from setString to setInt
        val resultSet = preparedStatement.executeQuery()
        return if (resultSet.next()) {
            UserModel(
                id = resultSet.getInt("id"), // changed from getString to getInt
                username = resultSet.getString("username"),
                email = resultSet.getString("email"),
                password = resultSet.getString("password"),
                phone = resultSet.getString("phone")
            )
        } else {
            null
        }
    }


    fun deleteUser(userId: Int): UserModel? { // changed from String to Int
        val user = getUserById(userId)
        if (user != null) {
            val query = "DELETE FROM users WHERE id = ?"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, userId) // changed from setString to setInt
            preparedStatement.executeUpdate()
        }
        return user
    }
}
