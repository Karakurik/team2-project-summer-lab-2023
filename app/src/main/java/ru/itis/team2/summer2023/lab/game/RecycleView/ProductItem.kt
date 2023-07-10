package ru.itis.team2.summer2023.lab.game.RecycleView

import android.view.View.INVISIBLE
import androidx.recyclerview.widget.RecyclerView
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.RvItemBinding

class ProductItem (
    private val binding: RvItemBinding,
    private val onItemClick: (Product) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(product: Product){
            binding.run{
                tvName.text = product.name

                tvPoints.text = "+ ${product.restoring}"

                if (product.open) {
                    ivImage.setImageResource(product.picture)
                    tvCarePoints.visibility = INVISIBLE
                }
                else {
                    tvCarePoints.text = "${binding.root.context.getString(R.string.cp)} ${product.carePoints}"
                }

                root.setOnClickListener {
                    onItemClick(product)
                }
            }
        }
}
