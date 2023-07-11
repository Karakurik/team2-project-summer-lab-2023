package ru.itis.team2.summer2023.lab.game.RecycleView

import android.content.Context
import android.graphics.Color
import android.view.View.INVISIBLE
import androidx.recyclerview.widget.RecyclerView
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.RvItemBinding

class ProductItem (
    private val binding: RvItemBinding,
    private val onItemClick: (Product) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(product: Product){
            binding.run{
                val sharedPreferences = root.context.getSharedPreferences("", Context.MODE_PRIVATE)
                cvProduct.setCardBackgroundColor(sharedPreferences.getInt(Constants.BACKGROUND_COLOR, Color.WHITE))
                tvName.text = binding.root.context.getString(product.name)

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
