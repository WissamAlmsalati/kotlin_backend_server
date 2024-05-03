import java.io.File
import java.io.FileInputStream
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.Statement

fun main() {
    println("Enter the name:")
    val name = readLine() ?: return
    println("Enter the price:")
    val price = readLine()?.toDouble() ?: return
    println("Enter the quantity:")
    val quantity = readLine()?.toInt() ?: return
    println("Enter the size:")
    val size = readLine() ?: return
    println("Enter the color:")
    val color = readLine() ?: return
    println("Enter the description:")
    val description = readLine() ?: return
    println("Enter the discount:")
    val discount = readLine()?.toDouble() ?: return
    println("Enter the category id:")
    val categoryId = readLine()?.toInt() ?: return
    println("Enter the image path:")
    val imagePath = readLine() ?: return

    insertClothes(name, price, quantity, size, color, description, discount, categoryId, imagePath)
}

fun insertClothes(name: String, price: Double, quantity: Int, size: String, color: String, description: String, discount: Double, categoryId: Int, imagePath: String) {
    val url = "jdbc:mysql://localhost:3306/kotlin_app" // replace with your database URL
    val dbUsername = "root" // replace with your database username
    val dbPassword = "11111111" // replace with your database password

    val connection = DriverManager.getConnection(url, dbUsername, dbPassword)

    // Insert the image into the pictures table
    val file = File(imagePath)
    val fis = FileInputStream(file)
    val insertPictureQuery = "INSERT INTO pictures (image) VALUES (?)"
    val picturePreparedStatement: PreparedStatement = connection.prepareStatement(insertPictureQuery, Statement.RETURN_GENERATED_KEYS)

    picturePreparedStatement.setBinaryStream(1, fis, file.length().toInt())
    picturePreparedStatement.executeUpdate()

    // Get the generated picture id
    val generatedKeys = picturePreparedStatement.generatedKeys
    generatedKeys.next()
    val pictureId = generatedKeys.getInt(1)

    fis.close()
    picturePreparedStatement.close()

    // Insert the clothes item
    val insertClothesQuery = "INSERT INTO clothes (name, price, quantity, size, color, description, discount, category_id, picture_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
    val clothesPreparedStatement: PreparedStatement = connection.prepareStatement(insertClothesQuery)

    clothesPreparedStatement.setString(1, name)
    clothesPreparedStatement.setDouble(2, price)
    clothesPreparedStatement.setInt(3, quantity)
    clothesPreparedStatement.setString(4, size)
    clothesPreparedStatement.setString(5, color)
    clothesPreparedStatement.setString(6, description)
    clothesPreparedStatement.setDouble(7, discount)
    clothesPreparedStatement.setInt(8, categoryId)
    clothesPreparedStatement.setInt(9, pictureId)
    clothesPreparedStatement.executeUpdate()

    clothesPreparedStatement.close()
    connection.close()
}