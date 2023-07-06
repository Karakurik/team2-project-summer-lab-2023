package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentBathBinding

class BathFragment : Fragment(R.layout.fragment_bath) {
    private var binding: FragmentBathBinding? = null
    private val advice: String = "bath advice"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBathBinding.bind(view)

        binding?.run {
            binding?.btnAdvice?.setOnClickListener {
                val dialog = AlertDialog.Builder(activity, R.style.MyAlertDialogTheme).setMessage(advice).create()
                dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                dialog.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
