package ru.itis.team2.summer2023.lab.game

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentInfoBinding

class InfoFragment : Fragment(R.layout.fragment_info) {
    private var binding: FragmentInfoBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        val sharedPreferences = this.requireActivity().getSharedPreferences("", Context.MODE_PRIVATE);
        val cat = Cat.getCat(sharedPreferences.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF))
        binding?.run {
            tvAgeValue.text = ((System.currentTimeMillis() - cat.age)/ 1000L).toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
