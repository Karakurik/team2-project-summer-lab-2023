package ru.itis.team2.summer2023.lab.start

import android.app.AlertDialog
import android.annotation.SuppressLint
import android.content.Context

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.edit
import com.bumptech.glide.Glide
import ru.itis.team2.summer2023.lab.CatAdapter
import ru.itis.team2.summer2023.lab.CatRepository
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
        // binding!!.tvCarePoints.text = "${binding!!.tvCarePoints.text} ${sharedPreferences?.getInt("care_points", 0)}"
        initAdapter()

    }

    private fun initAdapter() {
        adapter = CatAdapter(CatRepository.list, Glide.with(this)
        ) { cat ->
            if (cat.open) {
                val intent = Intent(requireContext(), GameActivity::class.java)
                startActivity(intent)
            }
            else {
                AlertDialog.Builder(activity)
                    .setTitle("Хотите забрать кота к себе?")
                    .setNegativeButton("Нет") { dialog, which -> }
                    .setPositiveButton("Да") { dialog, which ->

                        val points = sharedPreferences?.getInt("care_points", 0)

                        if (points!! >= cat.carePoints) {
                            sharedPreferences?.edit {
                                putInt("care_points", points - cat.carePoints)
                            }

                            val intent = Intent(requireContext(), GameActivity::class.java)
                            startActivity(intent)
                        }
                        else {
                            AlertDialog.Builder(activity)
                                .setTitle("У вас недостаточно очков счастья.")
                                .setPositiveButton("Ок") {dialog, which ->}
                                .show()
                        }
                    }
                    .show()
            }
        }
        binding?.rvCat?.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
