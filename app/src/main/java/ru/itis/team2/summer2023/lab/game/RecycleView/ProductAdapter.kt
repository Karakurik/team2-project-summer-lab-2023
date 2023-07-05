package ru.itis.team2.summer2023.lab.game.RecycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.team2.summer2023.lab.databinding.RvItemBinding

class ProductAdapter (
    private var list: List<Product>,
    private val onItemClick: (Product) -> Unit) : RecyclerView.Adapter<ProductItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProductItem = ProductItem(binding = RvItemBinding.inflate(LayoutInflater.from(parent.context)),
        onItemClick = onItemClick)

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductItem, position: Int) {
        holder.onBind(list[position])
    }

    fun updateDataset(newList: List<Product>) {
        list = newList
        notifyDataSetChanged()
    }
}
