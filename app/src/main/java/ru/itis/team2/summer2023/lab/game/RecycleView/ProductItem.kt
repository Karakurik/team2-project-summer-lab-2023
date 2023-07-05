package ru.itis.team2.summer2023.lab.game.RecycleView

import androidx.recyclerview.widget.RecyclerView
import ru.itis.team2.summer2023.lab.databinding.RvItemBinding

class ProductItem (
    private val binding: RvItemBinding,
    private val onItemClick: (Product) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(product: Product){
            binding.run{
                tvName.text = product.name

                ivImage.setImageResource(product.picture)

                root.setOnClickListener {
                    onItemClick(product) //тут будет какая-то анимация при выборе продукта
                }
            }
        }
}
