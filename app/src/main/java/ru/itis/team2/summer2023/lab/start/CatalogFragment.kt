package ru.itis.team2.summer2023.lab.start

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.itis.team2.summer2023.lab.CatAdapter
import ru.itis.team2.summer2023.lab.CatRepository
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentCatalogBinding
import ru.itis.team2.summer2023.lab.game.GameActivity

class CatalogFragment : Fragment(R.layout.fragment_catalog) {
    private var binding: FragmentCatalogBinding? = null
    private var adapter: CatAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCatalogBinding.bind(view)

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
                        // if (очков заботы хватает) {
                        // списание очков заботы
                        // переход к данному коту
                        val intent = Intent(requireContext(), GameActivity::class.java)
                        startActivity(intent)
                        // иначе сообщение об ошибке можно сделать snackbar
                        // или можно сделать ещё один alertdialog
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
