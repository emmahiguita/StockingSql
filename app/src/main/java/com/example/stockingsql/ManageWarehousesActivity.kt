package com.example.stockingsql

import android.content.ContentValues
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ManageWarehousesActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var listViewWarehouses: ListView
    private lateinit var editTextWarehouseName: EditText
    private lateinit var editTextWarehouseLocation: EditText
    private lateinit var buttonAddWarehouse: Button
    private lateinit var buttonEditWarehouse: Button
    private lateinit var buttonDeleteWarehouse: Button
    private var warehouses: MutableList<Warehouse> = mutableListOf()
    private var selectedWarehouseId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_warehouse)

        dbHelper = DatabaseHelper(this)
        listViewWarehouses = findViewById(R.id.listViewWarehouses)
        editTextWarehouseName = findViewById(R.id.editTextWarehouseName)
        editTextWarehouseLocation = findViewById(R.id.editTextWarehouseLocation)
        buttonAddWarehouse = findViewById(R.id.buttonAddWarehouse)
        buttonEditWarehouse = findViewById(R.id.buttonEditWarehouse)
        buttonDeleteWarehouse = findViewById(R.id.buttonDeleteWarehouse)

        buttonAddWarehouse.setOnClickListener {
            addWarehouse()
        }

        buttonEditWarehouse.setOnClickListener {
            editWarehouse()
        }

        buttonDeleteWarehouse.setOnClickListener {
            deleteWarehouse()
        }

        listViewWarehouses.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedWarehouse = warehouses[position]
            editTextWarehouseName.setText(selectedWarehouse.name)
            editTextWarehouseLocation.setText(selectedWarehouse.location)
            selectedWarehouseId = selectedWarehouse.id.toLong()
        }

        loadWarehouses()
    }

    private fun loadWarehouses() {
        warehouses.clear()
        warehouses.addAll(getWarehousesFromDb())

        val adapter = WarehouseAdapter(this, warehouses)
        listViewWarehouses.adapter = adapter
    }

    private fun addWarehouse() {
        val name = editTextWarehouseName.text.toString().trim()
        val location = editTextWarehouseLocation.text.toString().trim()

        if (name.isNotEmpty()) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("Nombre", name)
                put("Ubicacion", location)
            }
            db.insert("Almacenes", null, values)
            loadWarehouses()
            clearFields()
        } else {
            showToast("Por favor ingrese un nombre de almacén")
        }
    }

    private fun editWarehouse() {
        val name = editTextWarehouseName.text.toString().trim()
        val location = editTextWarehouseLocation.text.toString().trim()

        if (selectedWarehouseId != -1L && name.isNotEmpty()) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("Nombre", name)
                put("Ubicacion", location)
            }
            db.update("Almacenes", values, "AlmacenID = ?", arrayOf(selectedWarehouseId.toString()))
            loadWarehouses()
            clearFields()
        } else {
            showToast("Seleccione un almacén para editar")
        }
    }

    private fun deleteWarehouse() {
        if (selectedWarehouseId != -1L) {
            val db = dbHelper.writableDatabase
            db.delete("Almacenes", "AlmacenID = ?", arrayOf(selectedWarehouseId.toString()))
            loadWarehouses()
            clearFields()
        } else {
            showToast("Seleccione un almacén para eliminar")
        }
    }

    private fun clearFields() {
        editTextWarehouseName.text.clear()
        editTextWarehouseLocation.text.clear()
        selectedWarehouseId = -1
    }

    private fun getWarehousesFromDb(): List<Warehouse> {
        val db = dbHelper.readableDatabase
        val cursor = db.query("Almacenes", null, null, null, null, null, null)
        val warehouses = mutableListOf<Warehouse>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("AlmacenID"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"))
            val location = cursor.getString(cursor.getColumnIndexOrThrow("Ubicacion"))
            warehouses.add(Warehouse(id, name, location))
        }
        cursor.close()
        return warehouses
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
