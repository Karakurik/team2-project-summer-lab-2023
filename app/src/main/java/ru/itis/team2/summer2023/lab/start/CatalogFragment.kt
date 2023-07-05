package ru.itis.team2.summer2023.lab.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentCatalogBinding

class CatalogFragment : Fragment(R.layout.fragment_catalog) {
    private var binding: FragmentCatalogBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCatalogBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
