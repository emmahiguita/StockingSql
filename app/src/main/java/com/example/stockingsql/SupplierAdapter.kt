package com.example.stockingsql

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SupplierAdapter(
    private val context: Context,
    private val suppliers: List<Proveedor>
) : RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder>() {

    private var onItemClickListener: ((Proveedor) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_supplier, parent, false)
        return SupplierViewHolder(view)
    }

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        val supplier = suppliers[position]
        holder.bind(supplier)
        holder.itemView.setOnClickListener { onItemClickListener?.invoke(supplier) }
    }

    override fun getItemCount(): Int = suppliers.size

    fun setOnItemClickListener(listener: (Proveedor) -> Unit) {
        onItemClickListener = listener
    }

    inner class SupplierViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewContact: TextView = itemView.findViewById(R.id.textViewContact)

        fun bind(supplier: Proveedor) {
            textViewName.text = supplier.nombre
            textViewContact.text = supplier.contact
        }
    }
}
