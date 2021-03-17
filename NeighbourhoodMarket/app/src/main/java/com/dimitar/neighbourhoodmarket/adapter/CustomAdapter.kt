package com.dimitar.neighbourhoodmarket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dimitar.neighbourhoodmarket.model.Item
import com.dimitar.neighbourhoodmarket.R

class CustomAdapter(private val dataSet: ArrayList<Item>, private val clickListener: (Item) -> Unit) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(item: Item, clickListener: (Item) -> Unit){
            // Define click listener for the ViewHolder's View.
            val textViewItem = itemView.findViewById(R.id.textViewItem) as TextView
            val textViewPrice = itemView.findViewById(R.id.textViewPrice) as TextView
            textViewItem.text = item.item
            textViewPrice.text = item.price.toString()
            itemView.setOnClickListener { clickListener(item) }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.bindItems(dataSet[position], clickListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
