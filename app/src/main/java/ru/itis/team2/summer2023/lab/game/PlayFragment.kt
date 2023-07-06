package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentPlayBinding
import ru.itis.team2.summer2023.lab.game.RecycleView.KitchenRepository
import ru.itis.team2.summer2023.lab.game.RecycleView.PlayRepository
import ru.itis.team2.summer2023.lab.game.RecycleView.ProductAdapter

class PlayFragment : Fragment(R.layout.fragment_play) {
    private var binding: FragmentPlayBinding? = null
    private var adapter: ProductAdapter? = null
    private val advice: String = "play advice"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayBinding.bind(view)

        initAdapter()

        binding?.run {
            binding?.btnAdvice?.setOnClickListener {
                val dialog = AlertDialog.Builder(activity, R.style.MyAlertDialogTheme).setMessage(advice).create()
                dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                dialog.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initAdapter() {
        adapter = ProductAdapter(
            list = PlayRepository.list,
            onItemClick = {product ->
                //animation and delete product ?
            }
        )
        binding?.rvPlay?.adapter = adapter
        binding?.rvPlay?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
}
