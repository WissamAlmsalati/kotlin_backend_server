package services

import Product
import ProductDTO

class ProductService {
    fun addProduct(productDTO: ProductDTO) {
        // Code to add a product
    }

    fun updateProduct(id: Int, productDTO: Product) {
        // Code to update a product
    }

    fun deleteProduct(id: Int) {
        // Code to delete a product
    }

    fun viewProducts(): List<Product> {
        // Code to view all products
        return emptyList()
    }
}