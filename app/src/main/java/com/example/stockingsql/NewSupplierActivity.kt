package com.example.stockingsql

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NewSupplierActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextContact: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonSave: Button
    private lateinit var databaseHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_supplier)

        // Initialize DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName)
        editTextContact = findViewById(R.id.editTextContact)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonSave = findViewById(R.id.buttonSave)

        buttonSave.setOnClickListener {
            addNewSupplier()
        }
    }

    private fun addNewSupplier() {
        val name = editTextName.text.toString().trim()
        val contact = editTextContact.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()
        val email = editTextEmail.text.toString().trim()

        if (name.isNotEmpty()) {
            databaseHelper.insertProveedor(name, contact, phone, email)
            finish() // Close activity and return to previous screen
        } else {
            // Show an error message if name is empty
            editTextName.error = "Por favor ingrese el nombre del proveedor"
        }
    }
}
