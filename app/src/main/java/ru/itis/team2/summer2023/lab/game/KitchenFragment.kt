package ru.itis.team2.summer2023.lab.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.team2.summer2023.lab.databinding.FragmentKitchenBinding

class KitchenFragment : Fragment() {
    private var binding: FragmentKitchenBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKitchenBinding.bind(view)
    }
}
