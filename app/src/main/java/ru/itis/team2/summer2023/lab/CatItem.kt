package ru.itis.team2.summer2023.lab

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import ru.itis.team2.summer2023.lab.databinding.ItemCatBinding
import ru.itis.team2.summer2023.lab.start.StartActivity
import ru.itis.team2.summer2023.lab.start.StartFragment
import java.io.File


class CatItem(
    private val binding: ItemCatBinding,
    private val glide: RequestManager,
    private val onItemClick: (Cat) -> Unit
): ViewHolder(binding.root) {

    fun onBind(cat: Cat) {
        binding.run {
            tvBreed.text = "Порода: ${binding.root.context.getString(cat.breed)}"

            if (cat.open) {
                glide.load(cat.urlImage)
                    .into(ivImage)
                tvCarePoints.visibility = GONE
            }
            else {
                tvCarePoints.text = "Очки заботы: ${cat.carePoints}"
            }

            root.setOnClickListener {
                onItemClick(cat)
            }
        }
    }
}
