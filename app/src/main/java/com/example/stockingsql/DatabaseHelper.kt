@file:Suppress("UNREACHABLE_CODE", "KotlinRedundantDiagnosticSuppress", "VARIABLE_WITH_REDUNDANT_INITIALIZER")

package com.example.stockingsql

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

@Suppress("UNUSED_PARAMETER", "KotlinRedundantDiagnosticSuppress")
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla Proveedores
        db.execSQL("CREATE TABLE IF NOT EXISTS Proveedores (" +
                "ProveedorID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre TEXT, " +
                "Contacto TEXT, " +
                "Telefono TEXT, " +
                "Email TEXT)")

        // Crear tabla Productos
        db.execSQL("CREATE TABLE IF NOT EXISTS Productos (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MP TEXT, " + // Asegúrate de que esta columna exista
                "Descripcion TEXT, " +
                "Precio REAL, " +
                "Stock INTEGER, " +
                "CategoriaID INTEGER, " +
                "ProveedorID INTEGER, " +
                "FOREIGN KEY(ProveedorID) REFERENCES Proveedores(ProveedorID))")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Eliminar tablas existentes
        db.execSQL("DROP TABLE IF EXISTS Productos")
        db.execSQL("DROP TABLE IF EXISTS Proveedores")
        onCreate(db)
    }

    // Métodos para Proveedores
    @SuppressLint("Range")
    fun getAllSuppliersWithDetails(): List<Proveedor> {
        val suppliers = mutableListOf<Proveedor>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Proveedores", null)

        try {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex("ProveedorID"))
                    val name = cursor.getString(cursor.getColumnIndex("Nombre"))
                    val contact = cursor.getString(cursor.getColumnIndex("Contacto"))
                    val phone = cursor.getString(cursor.getColumnIndex("Telefono"))
                    val email = cursor.getString(cursor.getColumnIndex("Email"))
                    suppliers.add(Proveedor(id, name, contact, phone, email))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error fetching suppliers", e)
        } finally {
            cursor.close()
        }

        return suppliers
    }

    fun deleteSupplier(id: String) {
        val db = this.writableDatabase
        val rowsAffected = db.delete("Proveedores", "ProveedorID = ?", arrayOf(id))
        Log.i("DatabaseHelper", "Supplier deleted, rows affected: $rowsAffected")
    }

    fun updateSupplier(supplierId: String, updatedName: String, updatedContact: String, updatedPhone: String, updatedEmail: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("Nombre", updatedName)
            put("Contacto", updatedContact)
            put("Telefono", updatedPhone)
            put("Email", updatedEmail)
        }
        val rowsAffected = db.update("Proveedores", values, "ProveedorID = ?", arrayOf(supplierId))
        Log.i("DatabaseHelper", "Supplier updated, rows affected: $rowsAffected")
        return rowsAffected
    }

    @SuppressLint("Range")
    fun getAllProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Productos", null)

        try {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("Id"))
                    val mp = cursor.getString(cursor.getColumnIndex("MP"))
                    val description = cursor.getString(cursor.getColumnIndex("Descripcion"))
                    val price = cursor.getDouble(cursor.getColumnIndex("Precio"))
                    val stock = cursor.getInt(cursor.getColumnIndex("Stock"))
                    val categoryId = cursor.getInt(cursor.getColumnIndex("CategoriaID"))
                    val supplierId = cursor.getInt(cursor.getColumnIndex("ProveedorID"))
                    products.add(Product(id, mp, description, price, stock, categoryId, supplierId))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error fetching products", e)
        } finally {
            cursor.close()
        }

        return products
    }

    fun addProduct(mp: String, description: String, price: Double, stock: Int, categoryId: Int, supplierId: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("MP", mp)
            put("Descripcion", description)
            put("Precio", price)
            put("Stock", stock)
            put("CategoriaID", categoryId)
            put("ProveedorID", supplierId)
        }
        try {
            val result = db.insert("Productos", null, values)
            if (result == -1L) {
                Log.e("DatabaseHelper", "Error inserting product: $mp")
            } else {
                Log.i("DatabaseHelper", "Product added successfully: $mp")
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting product", e)
        }
    }

    fun insertProveedor(name: String, contact: String, phone: String, email: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("Nombre", name)
            put("Contacto", contact)
            put("Telefono", phone)
            put("Email", email)
        }
        try {
            val result = db.insert("Proveedores", null, values)
            if (result == -1L) {
                Log.e("DatabaseHelper", "Error inserting supplier: $name")
            } else {
                Log.i("DatabaseHelper", "Supplier added successfully: $name")
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting supplier", e)
        }
    }

    fun deleteProduct(id: Int) {
        val db = this.writableDatabase
        val rowsAffected = db.delete("Productos", "Id = ?", arrayOf(id.toString()))
        Log.i("DatabaseHelper", "Product deleted, rows affected: $rowsAffected")
    }

    fun updateProduct(id: Int, mp: String, description: String, price: Double, stock: Int, categoryId: Int, supplierId: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("MP", mp)
            put("Descripcion", description)
            put("Precio", price)
            put("Stock", stock)
            put("CategoriaID", categoryId)
            put("ProveedorID", supplierId)
        }
        val rowsAffected = db.update("Productos", values, "Id = ?", arrayOf(id.toString()))
        Log.i("DatabaseHelper", "Product updated, rows affected: $rowsAffected")
    }

    @SuppressLint("Range")
    fun searchProducts(query: String): List<Product> {
        val products = mutableListOf<Product>()
        val db = this.readableDatabase
        val queryString = "SELECT * FROM Productos WHERE MP LIKE ? OR Descripcion LIKE ?"
        val cursor = db.rawQuery(queryString, arrayOf("%$query%", "%$query%"))

        try {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("Id"))
                    val mp = cursor.getString(cursor.getColumnIndex("MP"))
                    val description = cursor.getString(cursor.getColumnIndex("Descripcion"))
                    val price = cursor.getDouble(cursor.getColumnIndex("Precio"))
                    val stock = cursor.getInt(cursor.getColumnIndex("Stock"))
                    val categoryId = cursor.getInt(cursor.getColumnIndex("CategoriaID"))
                    val supplierId = cursor.getInt(cursor.getColumnIndex("ProveedorID"))
                    products.add(Product(id, mp, description, price, stock, categoryId, supplierId))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error searching products", e)
        } finally {
            cursor.close()
        }

        return products
    }

    @SuppressLint("Range")
    fun getFilteredProducts(lowStock: Boolean, highValue: Boolean): List<Product> {
        val products = mutableListOf<Product>()
        val db = this.readableDatabase
        val queryBuilder = StringBuilder("SELECT * FROM Productos WHERE 1=1")

        if (lowStock) {
            queryBuilder.append(" AND Stock < 10")
        }
        if (highValue) {
            queryBuilder.append(" AND Precio > 100")
        }

        val cursor = db.rawQuery(queryBuilder.toString(), null)

        try {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("Id"))
                    val mp = cursor.getString(cursor.getColumnIndex("MP"))
                    val description = cursor.getString(cursor.getColumnIndex("Descripcion"))
                    val price = cursor.getDouble(cursor.getColumnIndex("Precio"))
                    val stock = cursor.getInt(cursor.getColumnIndex("Stock"))
                    val categoryId = cursor.getInt(cursor.getColumnIndex("CategoriaID"))
                    val supplierId = cursor.getInt(cursor.getColumnIndex("ProveedorID"))
                    products.add(Product(id, mp, description, price, stock, categoryId, supplierId))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error filtering products", e)
        } finally {
            cursor.close()
        }

        return products
    }

    companion object {
        const val DATABASE_NAME = "StockingSql.db"
        const val DATABASE_VERSION = 1
    }
}
