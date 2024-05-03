package services

import org.example.models.User
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement

class UserService {
    private fun connectToDatabase(): Connection? {
        val url = "jdbc:mysql://localhost:3306/kotlin_app"
        val username = "root"
        val password = "11111111"

        return try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            DriverManager.getConnection(url, username, password)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun addUser(user: User) {
        val connection = connectToDatabase()
        connection?.let {
            val preparedStatement = it.prepareStatement("INSERT INTO users (email, username, password) VALUES (?, ?, ?)")
            preparedStatement.setString(1, user.email)
            preparedStatement.setString(2, user.username)
            preparedStatement.setString(3, user.password)
            val affectedRows = preparedStatement.executeUpdate()
            if (affectedRows > 0) {
                println("User added successfully.")
            } else {
                println("Failed to add user.")
            }
        }
    }

    fun viewUsers(): List<User> {
        val users = mutableListOf<User>()
        val connection = connectToDatabase()
        connection?.let {
            val statement = it.createStatement()
            val resultSet = statement.executeQuery("SELECT * FROM users")

            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val email = resultSet.getString("email")
                val username = resultSet.getString("username")
                val password = resultSet.getString("password")

                users.add(User(id, email, username, password))
            }
        }
        return users
    }

    fun updateUser(id: Int, user: User) {
        val connection = connectToDatabase()
        connection?.let {
            val preparedStatement: PreparedStatement = it.prepareStatement("UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?")
            preparedStatement.setString(1, user.email)
            preparedStatement.setString(2, user.username)
            preparedStatement.setString(3, user.password)
            preparedStatement.setInt(4, id)
            val affectedRows = preparedStatement.executeUpdate()
            if (affectedRows > 0) {
                println("User updated successfully.")
            } else {
                println("Failed to update user.")
            }
        }
    }

    fun deleteUser(id: Int) {
        val connection = connectToDatabase()
        connection?.let {
            val preparedStatement: PreparedStatement = it.prepareStatement("DELETE FROM users WHERE id = ?")
            preparedStatement.setInt(1, id)
            val affectedRows = preparedStatement.executeUpdate()
            if (affectedRows > 0) {
                println("User deleted successfully.")
            } else {
                println("Failed to delete user.")
            }
        }
    }


    fun signup(user: User): String {
        val connection = connectToDatabase()
        connection?.let {
            val checkUserStatement = it.prepareStatement("SELECT * FROM users WHERE email = ? OR username = ?")
            checkUserStatement.setString(1, user.email)
            checkUserStatement.setString(2, user.username)
            val resultSet = checkUserStatement.executeQuery()
            if (resultSet.next()) {
                return "User with this email or username already exists."
            } else {
                val preparedStatement = it.prepareStatement("INSERT INTO users (email, username, password) VALUES (?, ?, ?)")
                preparedStatement.setString(1, user.email)
                preparedStatement.setString(2, user.username)
                preparedStatement.setString(3, user.password)
                val affectedRows = preparedStatement.executeUpdate()
                if (affectedRows > 0) {
                    return "User signed up successfully."
                }
            }
        }
        return "Failed to sign up user."
    }

   fun login(email: String?, username: String?, password: String): String {
    val connection = connectToDatabase()
    connection?.let {
        val preparedStatement = it.prepareStatement("SELECT * FROM users WHERE (email = ? OR username = ?) AND password = ?")
        preparedStatement.setString(1, email)
        preparedStatement.setString(2, username)
        preparedStatement.setString(3, password)
        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            return "User logged in successfully."
        }
    }
    return "Invalid email/username or password."
}

    fun updateUser(id: Int, username: String?, password: String?): String {
    val connection = connectToDatabase()
    connection?.let {
        val preparedStatement = it.prepareStatement("UPDATE users SET username = COALESCE(?, username), password = COALESCE(?, password) WHERE id = ?")
        preparedStatement.setString(1, username)
        preparedStatement.setString(2, password)
        preparedStatement.setInt(3, id)
        val affectedRows = preparedStatement.executeUpdate()
        if (affectedRows > 0) {
            return "User updated successfully."
        }
    }
    return "Failed to update user."
}


}