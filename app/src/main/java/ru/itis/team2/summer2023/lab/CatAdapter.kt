package ru.itis.team2.summer2023.lab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.itis.team2.summer2023.lab.databinding.ItemCatBinding

class CatAdapter(
    private var list: List<Cat>,
    private val glide: RequestManager,
    private val onItemClick: (Cat) -> Unit
): RecyclerView.Adapter<CatItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
        CatItem = CatItem(
            ItemCatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide,
            onItemClick
        )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CatItem, position: Int) {
        holder.onBind(list[position])
    }
}
