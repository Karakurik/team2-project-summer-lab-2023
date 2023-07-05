package ru.itis.team2.summer2023.lab.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentSleepBinding

class SleepFragment : Fragment(R.layout.fragment_sleep) {
    private var binding: FragmentSleepBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
