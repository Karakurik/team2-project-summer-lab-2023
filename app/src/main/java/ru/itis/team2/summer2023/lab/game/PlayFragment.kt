package ru.itis.team2.summer2023.lab.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentPlayBinding

class PlayFragment : Fragment(R.layout.fragment_play) {
    private var binding: FragmentPlayBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
