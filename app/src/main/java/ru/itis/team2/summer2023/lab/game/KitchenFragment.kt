package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentKitchenBinding
import ru.itis.team2.summer2023.lab.databinding.RvItemBinding
import ru.itis.team2.summer2023.lab.game.RecycleView.ProductAdapter
import ru.itis.team2.summer2023.lab.game.RecycleView.KitchenRepository
import ru.itis.team2.summer2023.lab.game.RecycleView.Product
import ru.itis.team2.summer2023.lab.game.RecycleView.ProductItem

class KitchenFragment : Fragment(R.layout.fragment_kitchen) {
    private var binding: FragmentKitchenBinding? = null
    private var adapter: ProductAdapter? = null
    private val advice: String = "kitchen advice"
    private var sharedPreferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKitchenBinding.bind(view)

        sharedPreferences = this.activity?.getSharedPreferences("", Context.MODE_PRIVATE)

        initAdapter()

        binding?.btnAdvice?.setOnClickListener {
            val dialog = AlertDialog.Builder(activity, R.style.MyAlertDialogTheme).setMessage(advice).create()
            dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            dialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initAdapter() {
        updateRepository()
        adapter = ProductAdapter(
            list = KitchenRepository.list,
            onItemClick = {product ->
                if (product.open) {
                    //делай здесь анимацию поедания продукта
                    //насчитай очки которые дает продукт к коту по последнему ай ди
                }
                else {
                    AlertDialog.Builder(activity)
                        .setTitle("Хотите купить продукт?")
                        .setNegativeButton("Нет") {dialog, which ->}
                        .setPositiveButton("Да") {dialog, which ->
                            val points = sharedPreferences?.getInt("care_points", 0)

                            if (points!! >= product.carePoints) {
                                sharedPreferences?.edit {
                                    putInt("care_points", points - product.carePoints)
                                }
                                replaceProductOpen(product.id)
                                // здесь или не здесь но надо как то обновить изображение списка
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
        binding?.rvKitchen?.adapter = adapter
        binding?.rvKitchen?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun replaceProductOpen(id: Int) {
        val shp = this.activity?.getSharedPreferences("products", Context.MODE_PRIVATE)
        val string = shp?.getString("$id product", "")
        val product = Gson().fromJson(string, Product::class.java)
        product.open = true
        shp?.edit {
            putString("$id cat", Gson().toJson(product))
        }
    }

    private fun updateRepository() {
        var index = 1
        var newProduct: Product
        var string: String?

        for (product in KitchenRepository.list) {
            string = this.activity?.getSharedPreferences("products", Context.MODE_PRIVATE)
                ?.getString("$index product", "")
            newProduct = Gson().fromJson(string, Product::class.java)
            product.open = newProduct.open
            index++
        }
    }
}
