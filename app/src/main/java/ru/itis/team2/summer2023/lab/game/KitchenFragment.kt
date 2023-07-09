package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.opengl.Visibility
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.edit
import androidx.loader.content.AsyncTaskLoader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.Constants
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
                    val id = sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF)
                    val cat = Cat.getCat(id, sharedPreferences!!)
                    Cat.setHunger(cat.hunger + product.restoring, cat, sharedPreferences!!)
                    if (Cat.getCat(id, sharedPreferences!!).hunger < 100){
                        sharedPreferences?.edit {
                            putInt("care_points", sharedPreferences!!.getInt("care_points", Constants.START_CARE_POINTS) + product.carePoints)
                        }
                    }
                    requireActivity().findViewById<TextView>(R.id.tv_care_points_value).text =
                        "Очки заботы: ${sharedPreferences!!.getInt("care_points", Constants.START_CARE_POINTS)}"
                }
                else {
                    AlertDialog.Builder(activity)
                        .setTitle("Хотите купить продукт?")
                        .setNegativeButton("Нет") {dialog, which ->}
                        .setPositiveButton("Да") {dialog, which ->
                            val points = sharedPreferences?.getInt("care_points", Constants.START_CARE_POINTS)

                            if (points!! >= product.carePoints) {
                                sharedPreferences?.edit {
                                    putInt("care_points", points - product.carePoints)
                                    putString("${product.id} product", replaceProductOpen(product.id))
                                }
                                findNavController().navigate(R.id.action_kitchenFragment_self)
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

    private fun replaceProductOpen(id: Int): String? {
        val string = sharedPreferences?.getString("$id product", "")
        val product = Gson().fromJson(string, Product::class.java)
        product.open = true
        return Gson().toJson(product)
    }

    private fun updateRepository() {
        var index = 1
        var newProduct: Product
        var string: String?

        for (product in KitchenRepository.list) {
            string = sharedPreferences?.getString("$index product", "")
            newProduct = Gson().fromJson(string, Product::class.java)
            product.open = newProduct.open
            index++
        }
    }
    
}
