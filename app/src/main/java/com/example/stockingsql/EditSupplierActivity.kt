@file:Suppress("UNREACHABLE_CODE")

package com.example.stockingsql

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditSupplierActivity : AppCompatActivity() {

    private lateinit var edtSupplierName: EditText
    private lateinit var edtSupplierContact: EditText
    private lateinit var edtSupplierPhone: EditText
    private lateinit var edtSupplierEmail: EditText
    private lateinit var btnSaveChanges: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_supplier)

        // Inicializar DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Inicializar componentes de la interfaz
        edtSupplierName = findViewById(R.id.edtEditSupplierName)
        edtSupplierContact = findViewById(R.id.edtEditSupplierContact)
        edtSupplierPhone = findViewById(R.id.edtEditSupplierPhone)
        edtSupplierEmail = findViewById(R.id.edtEditSupplierEmail)
        btnSaveChanges = findViewById(R.id.btnSaveSupplierChanges)

        // Obtener datos del proveedor seleccionado desde el Intent
        val supplierId = intent.getStringExtra("supplier_id") ?: ""
        val supplierName = intent.getStringExtra("supplier_name") ?: ""
        val supplierContact = intent.getStringExtra("supplier_contact") ?: ""
        val supplierPhone = intent.getStringExtra("supplier_phone") ?: ""
        val supplierEmail = intent.getStringExtra("supplier_email") ?: ""

        // Mostrar datos del proveedor en los EditText
        edtSupplierName.setText(supplierName)
        edtSupplierContact.setText(supplierContact)
        edtSupplierPhone.setText(supplierPhone)
        edtSupplierEmail.setText(supplierEmail)

        // Manejar clic en el botón de guardar cambios
        btnSaveChanges.setOnClickListener {
            val updatedName = edtSupplierName.text.toString().trim()
            val updatedContact = edtSupplierContact.text.toString().trim()
            val updatedPhone = edtSupplierPhone.text.toString().trim()
            val updatedEmail = edtSupplierEmail.text.toString().trim()

            // Verificar si ha habido cambios antes de actualizar
            if (updatedName != supplierName ||
                updatedContact != supplierContact ||
                updatedPhone != supplierPhone ||
                updatedEmail != supplierEmail) {

                // Actualizar proveedor en la base de datos
                val result = databaseHelper.updateSupplier(supplierId, updatedName, updatedContact, updatedPhone, updatedEmail)

                if (result > 0) {
                    Toast.makeText(this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al guardar los cambios", Toast.LENGTH_SHORT).show()
                }
            }

            // Finalizar la actividad actual y volver a la anterior
            finish()
        }
    }

    // Método onDestroy para cerrar el helper de base de datos
    override fun onDestroy() {
        databaseHelper.close()
        super.onDestroy()
    }
}

private operator fun Any.compareTo(@Suppress("UNUSED_PARAMETER") i: Int): Int {

    return TODO("Provide the return value")
}
