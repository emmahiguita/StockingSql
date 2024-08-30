package com.example.stockingsql

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

@Suppress("unused")
class WarehouseAdapter(
    private val context: Context,
    private val warehouses: MutableList<Warehouse>
) : BaseAdapter() {

    override fun getCount(): Int {
        return warehouses.size
    }

    override fun getItem(position: Int): Any {
        return warehouses[position]
    }

    override fun getItemId(position: Int): Long {
        return warehouses[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.item_warehouse, parent, false)
        } else {
            view = convertView
        }

        val warehouse = warehouses[position]
        val textViewName = view.findViewById<TextView>(R.id.textViewWarehouseName)
        val textViewLocation = view.findViewById<TextView>(R.id.textViewWarehouseLocation)

        textViewName.text = warehouse.name
        textViewLocation.text = warehouse.location

        return view
    }

    fun updateWarehouses(newWarehouses: List<Warehouse>) {
        warehouses.clear()
        warehouses.addAll(newWarehouses)
        notifyDataSetChanged()
    }
}
