package ru.itis.team2.summer2023.lab.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentKitchenBinding
import ru.itis.team2.summer2023.lab.game.RecycleView.ProductAdapter
import ru.itis.team2.summer2023.lab.game.RecycleView.KitchenRepository

class KitchenFragment : Fragment(R.layout.fragment_kitchen) {
    private var binding: FragmentKitchenBinding? = null
    private var adapter: ProductAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKitchenBinding.bind(view)

        initAdapter()

        /*binding?.tvName?.setOnClickListener {
            adapter?.updateDataset(
                KitchenRepository.list.subList(0, Random.nextInt(8))
            )
        }
         */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initAdapter() {
        adapter = ProductAdapter(
            list = KitchenRepository.list,
            onItemClick = {product ->
                //animation and delete product ?
            }
        )
        binding?.rvKitchen?.adapter = adapter
        binding?.rvKitchen?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
}
