package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentPlayBinding
import ru.itis.team2.summer2023.lab.game.RecycleView.KitchenRepository
import ru.itis.team2.summer2023.lab.game.RecycleView.PlayRepository
import ru.itis.team2.summer2023.lab.game.RecycleView.Product
import ru.itis.team2.summer2023.lab.game.RecycleView.ProductAdapter

class PlayFragment : Fragment(R.layout.fragment_play) {
    private var binding: FragmentPlayBinding? = null
    private var adapter: ProductAdapter? = null
    private val advice: String = "play advice"
    private var sharedPreferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayBinding.bind(view)

        sharedPreferences = this.activity?.getSharedPreferences("", Context.MODE_PRIVATE)

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
        updateRepository()
        adapter = ProductAdapter(
            list = PlayRepository.list,
            onItemClick = {product ->
                if (product.open) {
                    //делай здесь анимацию игры кота
                    //насчитай очки которые дает игра к коту по последнему ай ди
                }
                else {
                    AlertDialog.Builder(activity)
                        .setTitle("Хотите разблокировать игру?")
                        .setNegativeButton("Нет") {dialog, which ->}
                        .setPositiveButton("Да") {dialog, which ->
                            val points = sharedPreferences?.getInt("care_points", 0)

                            if (points!! >= product.carePoints) {
                                sharedPreferences?.edit {
                                    putInt("care_points", points - product.carePoints)
                                    putString("${product.id} game", replaceProductOpen(product.id))
                                }
                                findNavController().navigate(R.id.action_playFragment_self)
                            }
                            else {
                                AlertDialog.Builder(activity)
                                    .setTitle("У вас недостаточно очков заботы.")
                                    .setPositiveButton("Ок") {dialog, which ->}
                                    .show()
                            }
                        }
                        .show()
                }
            }
        )
        binding?.rvPlay?.adapter = adapter
        binding?.rvPlay?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }


    private fun replaceProductOpen(id: Int): String? {
        val string = sharedPreferences?.getString("$id game", "")
        val product = Gson().fromJson(string, Product::class.java)
        product.open = true
        return Gson().toJson(product)
    }
    private fun updateRepository() {
        var index = 1
        var newGame: Product
        var string: String?

        for (game in PlayRepository.list) {
            string = sharedPreferences?.getString("$index game", "")
            newGame = Gson().fromJson(string, Product::class.java)
            game.open = newGame.open
            index++
        }
    }
}
