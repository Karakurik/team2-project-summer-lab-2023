package ru.itis.team2.summer2023.lab.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.team2.summer2023.lab.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private var binding: FragmentStartBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStartBinding.bind(view)
    }
}
