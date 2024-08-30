package com.example.stockingsql

data class Product(
    val id: Int,
    val mp: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val categoryId: Int,
    val supplierId: Int
)
