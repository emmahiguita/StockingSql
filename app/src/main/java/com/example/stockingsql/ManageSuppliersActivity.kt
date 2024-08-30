package com.example.stockingsql

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNREACHABLE_CODE", "KotlinRedundantDiagnosticSuppress")
class ManageSuppliersActivity : AppCompatActivity() {

    private lateinit var recyclerViewSuppliers: RecyclerView
    private lateinit var btnNuevoProveedor: Button
    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var suppliersList: ArrayList<Proveedor>
    private var selectedSupplier: Proveedor? = null
    private lateinit var adapter: SupplierAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_suppliers)

        // Initialize DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Initialize UI components
        recyclerViewSuppliers = findViewById(R.id.recyclerViewSuppliers)
        btnNuevoProveedor = findViewById(R.id.btnNuevoProveedor)
        btnEditar = findViewById(R.id.btnEditar)
        btnEliminar = findViewById(R.id.btnEliminar)

        // Configure RecyclerView
        recyclerViewSuppliers.layoutManager = LinearLayoutManager(this)
        suppliersList = ArrayList()
        adapter = SupplierAdapter(this, suppliersList)
        recyclerViewSuppliers.adapter = adapter

        // Fetch suppliers list from database and update RecyclerView
        updateSuppliersList()

        // Handle click events on RecyclerView items
        adapter.setOnItemClickListener { supplier ->
            selectedSupplier = supplier
            showSupplierDetails(supplier)
        }

        // Handle click event for adding a new supplier
        btnNuevoProveedor.setOnClickListener {
            val intent = Intent(this, NewSupplierActivity::class.java)
            startActivity(intent)
        }

        // Handle click event for editing a supplier
        btnEditar.setOnClickListener {
            selectedSupplier?.let {
                val intent = Intent(this, EditSupplierActivity::class.java)
                intent.putExtra("supplier_id", it.id)  // Pass the ID instead of the name
                startActivity(intent)
            }
        }

        // Handle click event for deleting a supplier
        btnEliminar.setOnClickListener {
            selectedSupplier?.let {
                showDeleteConfirmationDialog(it.id)  // Use ID for deletion
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateSuppliersList() {
        suppliersList.clear()
        suppliersList.addAll(databaseHelper.getAllSuppliersWithDetails())
        adapter.notifyDataSetChanged()
    }

    private fun showSupplierDetails(supplier: Proveedor) {
        AlertDialog.Builder(this).apply {
            setTitle("Detalles del Proveedor")
            setMessage("Nombre: ${supplier.nombre}\nContacto: ${supplier.contact ?: "-"}\nTeléfono: ${supplier.telefono ?: "-"}\nEmail: ${supplier.email ?: "-"}")
            setPositiveButton("Cerrar", null)
        }.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showDeleteConfirmationDialog(supplierId: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Eliminar Proveedor")
            setMessage("¿Estás seguro de que deseas eliminar al proveedor con ID $supplierId?")
            setPositiveButton("Sí") { _, _ ->
                // Delete supplier from database and update the list
                databaseHelper.deleteSupplier(supplierId)
                suppliersList.removeAll { it.id == supplierId }
                adapter.notifyDataSetChanged()
            }
            setNegativeButton("No", null)
        }.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        updateSuppliersList()
    }

    override fun onDestroy() {
        databaseHelper.close()
        super.onDestroy()
    }
}
