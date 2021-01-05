package br.com.example.guiabolsoapptest.view.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.example.guiabolsoapptest.R
import kotlinx.android.synthetic.main.item_adapter_category.view.*
import java.util.*

class CategoryAdapter(private val categories: ArrayList<String>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_adapter_category,
            parent, false)
    )

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val categoryName = view.tvCategoryName

        fun bind(mCategoryName: String) {
            categoryName.text = mCategoryName.toUpperCase(Locale.ROOT)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }

    override fun getItemCount(): Int = categories.size

    fun update(mCategories: List<String>) {
        categories.clear()
        categories.addAll(mCategories)
        notifyDataSetChanged()
    }
}