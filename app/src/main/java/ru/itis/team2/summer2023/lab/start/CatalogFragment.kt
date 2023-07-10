package ru.itis.team2.summer2023.lab.start

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.CatAdapter
import ru.itis.team2.summer2023.lab.CatAnimation
import ru.itis.team2.summer2023.lab.CatRepository
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentCatalogBinding
import ru.itis.team2.summer2023.lab.game.GameActivity

class CatalogFragment : Fragment(R.layout.fragment_catalog) {
    private var binding: FragmentCatalogBinding? = null
    private var adapter: CatAdapter? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCatalogBinding.bind(view)

        sharedPreferences = this.activity?.getSharedPreferences("", Context.MODE_PRIVATE)

        sharedPreferences?.getInt(Constants.BACKGROUND_COLOR, 0)
            ?.let { binding?.catalogFragment?.setBackgroundColor(it) }

        binding!!.tvCarePoints.text = "${getString(R.string.care_points)} ${sharedPreferences?.getInt("care_points", 0)}"
        binding!!.tvStatistic.text = "${getString(R.string.number_of_cats)} ${sharedPreferences?.getInt("number_of_cats", 1)}"
        initAdapter()
    }

    private fun initAdapter() {
        updateRepository()
        adapter = CatAdapter(CatRepository.list, Glide.with(this)
        ) { cat ->
            if (cat.open) {
                sharedPreferences?.edit {
                    putInt("last_cat_id", cat.id)
                }

                val intent = Intent(requireContext(), GameActivity::class.java)
                startActivity(intent)
            }
            else {
                AlertDialog.Builder(activity)
                    .setTitle(getString(R.string.title1))
                    .setNegativeButton(getString(R.string.no)) { dialog, which -> }
                    .setPositiveButton(R.string.yes) { dialog, which ->

                        val points = sharedPreferences?.getInt("care_points", 0)
                        val number = sharedPreferences?.getInt("number_of_cats", 1)

                        if (points!! >= cat.carePoints) {
                            sharedPreferences?.edit {
                                putInt("care_points", points - cat.carePoints)
                                putInt("last_cat_id", cat.id)
                                putString("${cat.id} cat", replaceCatOpen(cat.id))
                                putInt("number_of_cats", number!! + 1)
                            }
                            binding!!.tvCarePoints.text = "${getString(R.string.care_points)} ${sharedPreferences?.getInt("care_points", 0)}"
                            binding!!.tvStatistic.text = "${getString(R.string.number_of_cats)} ${sharedPreferences?.getInt("number_of_cats", 1)}"

                            findNavController().navigate(R.id.action_catalogFragment_self)
                            val intent = Intent(requireContext(), GameActivity::class.java)
                            startActivity(intent)
                        }
                        else {
                            AlertDialog.Builder(activity)
                                .setTitle(getString(R.string.title2))
                                .setPositiveButton(getString(R.string.ok)) {dialog, which ->}
                                .show()
                        }
                    }
                    .show()
            }
        }
        binding?.rvCat?.adapter = adapter
    }

    private fun replaceCatOpen(id: Int): String? {
        val string = sharedPreferences?.getString("$id cat", "")
        val cat = Gson().fromJson(string, Cat::class.java)
        cat.open = true
        return Gson().toJson(cat)
    }

    private fun updateRepository() {
        var index = 1
        var newCat: Cat
        var string: String?

        for (cat in CatRepository.list) {
            string = sharedPreferences?.getString("$index cat", "")
            newCat = Gson().fromJson(string, Cat::class.java)
            cat.open = newCat.open
            cat.happy = newCat.happy
            cat.hunger = newCat.hunger
            cat.purity = newCat.purity
            cat.sleep = newCat.sleep
            index++
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
