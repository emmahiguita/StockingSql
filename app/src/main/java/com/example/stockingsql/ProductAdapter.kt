@file:Suppress("UNUSED_PARAMETER", "KotlinRedundantDiagnosticSuppress")

package com.example.stockingsql

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

// Adaptador para la lista de productos
class ProductAdapter(
    private val onItemClick: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    // ViewHolder para productos
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtProductId: TextView = itemView.findViewById(R.id.txtProductId)
        private val txtMp: TextView = itemView.findViewById(R.id.txtMp)
        private val txtDescription: TextView = itemView.findViewById(R.id.txtDescription)
        private val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        private val txtStock: TextView = itemView.findViewById(R.id.txtStock)

        // Asigna los datos del producto a las vistas
        fun bind(product: Product) {
            txtProductId.text = product.id.toString()
            txtMp.text = product.mp
            txtDescription.text = product.description
            txtPrice.text = product.price.toString()
            txtStock.text = product.stock.toString()

            itemView.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // Actualiza la lista de productos
    fun updateProducts(products: List<Product>) {
        submitList(products)
    }
}
