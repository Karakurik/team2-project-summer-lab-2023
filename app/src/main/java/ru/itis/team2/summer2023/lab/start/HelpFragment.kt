package ru.itis.team2.summer2023.lab.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentHelpBinding

class HelpFragment : Fragment(R.layout.fragment_help) {
    private var binding: FragmentHelpBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHelpBinding.bind(view)

        val sharedPreferences = activity?.getSharedPreferences("", AppCompatActivity.MODE_PRIVATE)
        sharedPreferences?.getInt(Constants.BACKGROUND_COLOR, 0)
            ?.let { binding?.helpFragment?.setBackgroundColor(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
