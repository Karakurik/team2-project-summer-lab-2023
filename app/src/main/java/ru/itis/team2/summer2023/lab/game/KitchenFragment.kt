package ru.itis.team2.summer2023.lab.game

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentKitchenBinding
import ru.itis.team2.summer2023.lab.game.RecycleView.KitchenRepository
import ru.itis.team2.summer2023.lab.game.RecycleView.Product
import ru.itis.team2.summer2023.lab.game.RecycleView.ProductAdapter
import java.util.Timer
import java.util.TimerTask


class KitchenFragment : Fragment(R.layout.fragment_kitchen) {
    private var binding: FragmentKitchenBinding? = null
    private var adapter: ProductAdapter? = null
    private val advice: Int = R.string.kitchen_advice
    private var sharedPreferences: SharedPreferences? = null
    private var kitchenTimer: Timer? = null
    private var kitchenTimerTask: KitchenTimerTask? = null

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
                    var cat = Cat.getCat(id)
                    if (!cat.isBusy) {
                        cat = Cat.setBusy(true,id)
                        if (cat.hunger < 100) {
                            sharedPreferences?.edit {
                                putInt(
                                    "care_points",
                                    sharedPreferences!!.getInt(
                                        "care_points",
                                        Constants.START_CARE_POINTS
                                    ) + Constants.STANDART_INCREASE_CARE_POINTS
                                )
                            }
                        }
                        cat = Cat.setHunger(cat.hunger + product.restoring, id)
                        requireActivity().findViewById<TextView>(R.id.tv_care_points_value).text =
                            "${getString(R.string.care_points)} ${sharedPreferences!!.getInt("care_points", Constants.START_CARE_POINTS)}"
                        kitchenTimer?.cancel()
                        kitchenTimer = Timer()
                        val activity = requireActivity() as GameActivity
                        val num = activity.animations[cat.animations.eat]?.numberOfFrames
                        var sum = 0
                        for (i in 0 until num!!){
                            sum += activity.animations[cat.animations.eat]?.getDuration(i)!!
                        }
                        if (activity.animations[cat.currentAnimation]?.isRunning == true){
                            activity.animations[cat.currentAnimation]?.stop()
                        }
                        activity.animations[cat.animations.eat]?.alpha = 255
                        activity.animations[cat.currentAnimation]?.alpha = 0
                        activity.animations[cat.animations.eat]?.start()
                        cat = Cat.setCurrentAnimation(cat.animations.eat, id)
                        kitchenTimerTask = KitchenTimerTask(activity, cat)
                        kitchenTimer!!.schedule(kitchenTimerTask, sum.toLong())
                    } else {
                        binding?.let { Snackbar.make(it.root, getString(R.string.busy), Snackbar.LENGTH_SHORT).show() }
                    }
                }
                else {
                    AlertDialog.Builder(activity)
                        .setTitle(getString(R.string.buy))
                        .setNegativeButton(getString(R.string.no)) {dialog, which ->}
                        .setPositiveButton(getString(R.string.yes)) {dialog, which ->
                            val points = sharedPreferences?.getInt("care_points", Constants.START_CARE_POINTS)

                            if (points!! >= product.carePoints) {
                                sharedPreferences?.edit {
                                    putInt("care_points", points - product.carePoints)
                                    putString("${product.id} product", replaceProductOpen(product.id))
                                }
                                requireActivity().findViewById<TextView>(R.id.tv_care_points_value).text =
                                     "${getString(R.string.care_points)} ${sharedPreferences!!.getInt("care_points", Constants.START_CARE_POINTS)}"
                              
                                findNavController().navigate(R.id.action_kitchenFragment_self)
                            }
                            else {
                                AlertDialog.Builder(activity)
                                    .setTitle(getString(R.string.title2))
                                    .setPositiveButton(R.string.ok) {dialog, which ->}
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
    class KitchenTimerTask(private val activity: GameActivity, var cat: Cat): TimerTask(){
        override fun run() {
            activity.runOnUiThread (Runnable {
                activity.animations[cat.animations.eat]?.alpha = 0
                activity.animations[cat.animations.eat]?.stop()
                cat = activity.setDefaultAnimation(cat)
                activity.sharedPreferences?.let { Cat.setBusy(false, cat.id) }
            })
        }
    }
}
