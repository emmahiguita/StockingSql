package com.example.stockingsql

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SupplierDetailsActivity : AppCompatActivity() {

    private lateinit var txtSupplierName: TextView
    private lateinit var txtSupplierContact: TextView
    private lateinit var txtSupplierPhone: TextView
    private lateinit var txtSupplierEmail: TextView

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_supplier)

        // Inicializar componentes de la interfaz
        txtSupplierName = findViewById(R.id.txtSupplierName)
        txtSupplierContact = findViewById(R.id.txtSupplierContact)
        txtSupplierPhone = findViewById(R.id.txtSupplierPhone)
        txtSupplierEmail = findViewById(R.id.txtSupplierEmail)

        // Obtener datos del proveedor seleccionado desde Intent
        val supplierName = intent.getStringExtra("supplier_name") ?: ""
        val supplierContact = intent.getStringExtra("supplier_contact") ?: "No especificado"
        val supplierPhone = intent.getStringExtra("supplier_phone") ?: "No especificado"
        val supplierEmail = intent.getStringExtra("supplier_email") ?: "No especificado"

        // Mostrar los datos en los TextView correspondientes
        txtSupplierName.text = "Nombre: $supplierName"
        txtSupplierContact.text = "Contacto: $supplierContact"
        txtSupplierPhone.text = "Teléfono: $supplierPhone"
        txtSupplierEmail.text = "Email: $supplierEmail"
    }
}
