package ru.itis.team2.summer2023.lab.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentHelpBinding

class HelpFragment : Fragment(R.layout.fragment_help) {
    private var binding: FragmentHelpBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHelpBinding.bind(view)
    }
}
