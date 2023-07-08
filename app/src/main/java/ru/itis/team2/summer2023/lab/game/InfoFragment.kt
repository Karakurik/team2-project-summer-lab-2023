package ru.itis.team2.summer2023.lab.game

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.CatRepository
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentInfoBinding

class InfoFragment : Fragment(R.layout.fragment_info) {
    private var binding: FragmentInfoBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        //временная дичь
//        val cat = Cat.findCat(requireActivity().getSharedPreferences("", Context.MODE_PRIVATE).getInt("last_cat_id", 1))
//        binding?.run {
//            tvAgeValue.text = ((System.currentTimeMillis() - cat!!.age)/ 1000L).toString()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
