package com.example.stockingsql

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@Suppress("DEPRECATION", "unused")
class ManageProductsActivity : AppCompatActivity() {

    private lateinit var searchInputLayout: TextInputLayout
    private lateinit var searchEditText: TextInputEditText
    private lateinit var filterChipGroup: ChipGroup
    private lateinit var productsRecyclerView: RecyclerView
    private lateinit var addProductFab: FloatingActionButton

    private lateinit var productAdapter: ProductAdapter
    private var selectedProduct: Product? = null

    private val databaseHelper by lazy { DatabaseHelper(this) }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_products) // Asegúrate de que el archivo de diseño esté correctamente nombrado

        // Inicializar vistas
        searchInputLayout = findViewById(R.id.searchInputLayout)
        searchEditText = findViewById(R.id.searchEditText)
        filterChipGroup = findViewById(R.id.filterChipGroup)
        productsRecyclerView = findViewById(R.id.productsRecyclerView)
        addProductFab = findViewById(R.id.addProductFab)

        setupRecyclerView()
        setupFilters()
        setupFloatingActionButton()
        setupSearch()
    }

    private fun setupRecyclerView() {
        productsRecyclerView.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductAdapter { product ->
            selectedProduct = product
            showProductDetails(product)
        }
        productsRecyclerView.adapter = productAdapter
        loadProducts() // Cargar productos al inicio
    }

    private fun setupFilters() {
        filterChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            when (chip?.id) {
                R.id.filterAllChip -> loadProducts()
                R.id.filterLowStockChip -> loadProducts(lowStock = true)
                R.id.filterHighValueChip -> loadProducts(highValue = true)
            }
        }
    }

    private fun setupFloatingActionButton() {
        addProductFab.setOnClickListener {
            showProductDialog(null)
        }
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener { text ->
            val query = text.toString()
            val products = databaseHelper.searchProducts(query)
            productAdapter.updateProducts(products)
        }
    }

    private fun loadProducts(lowStock: Boolean = false, highValue: Boolean = false) {
        val products = databaseHelper.getFilteredProducts(lowStock, highValue)
        productAdapter.updateProducts(products)
    }

    private fun showProductDetails(product: Product) {
        Toast.makeText(this, "Producto seleccionado: ${product.mp}", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("InflateParams")
    private fun showProductDialog(product: Product?) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_product, null)
        val editTextMP = dialogView.findViewById<TextInputEditText>(R.id.editTextMP)
        val editTextDescription = dialogView.findViewById<TextInputEditText>(R.id.editTextDescription)
        val editTextPrice = dialogView.findViewById<TextInputEditText>(R.id.editTextPrice)
        val editTextStock = dialogView.findViewById<TextInputEditText>(R.id.editTextStock)

        if (product != null) {
            editTextMP.setText(product.mp)
            editTextDescription.setText(product.description)
            editTextPrice.setText(product.price.toString())
            editTextStock.setText(product.stock.toString())
        }

        AlertDialog.Builder(this)
            .setTitle(if (product == null) "Nuevo Producto" else "Editar Producto")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val mp = editTextMP.text.toString()
                val description = editTextDescription.text.toString()
                val price = editTextPrice.text.toString().toDoubleOrNull() ?: 0.0
                val stock = editTextStock.text.toString().toIntOrNull() ?: 0

                if (product == null) {
                    databaseHelper.addProduct(mp, description, price, stock, categoryId = 0, supplierId = 0)
                    Toast.makeText(this, "Producto agregado", Toast.LENGTH_SHORT).show()
                } else {
                    databaseHelper.updateProduct(product.id, mp, description, price, stock, categoryId = 0, supplierId = 0)
                    Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show()
                }
                loadProducts() // Recarga los productos después de añadir/editar
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun confirmDeleteProduct(product: Product) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de que deseas eliminar este producto?")
            .setPositiveButton("Eliminar") { _, _ ->
                databaseHelper.deleteProduct(product.id)
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
                loadProducts() // Recarga los productos después de eliminar
                selectedProduct = null
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
