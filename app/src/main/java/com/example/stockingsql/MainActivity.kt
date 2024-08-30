@file:Suppress("UNUSED_PARAMETER")

package com.example.stockingsql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

@Suppress("unused")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openManageWarehouses(view: View) {
        val intent = Intent(this, ManageWarehousesActivity::class.java)
        startActivity(intent)
    }

    fun openManageSuppliers(view: View) {
        val intent = Intent(this, ManageSuppliersActivity::class.java)
        startActivity(intent)
    }

    fun openManageProducts(view: View) {
        val intent = Intent(this, ManageProductsActivity::class.java)
        startActivity(intent)
    }

    fun openManageInventoryMovements(view: View) {
        val intent = Intent(this, ManageInventoryMovementsActivity::class.java)
        startActivity(intent)
    }

    fun openManageCategories(view: View) {
        val intent = Intent(this, ManageCategoriesActivity::class.java)
        startActivity(intent)
    }

    fun exitApp(view: View) {}
}
