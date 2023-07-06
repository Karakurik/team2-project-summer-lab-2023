package ru.itis.team2.summer2023.lab.game

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentBreedInfoBinding
import ru.itis.team2.summer2023.lab.databinding.FragmentStartBinding


class BreedInfoFragment : DialogFragment() {
    private var binding: FragmentBreedInfoBinding ?= null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
        builder.setView(activity?.layoutInflater?.inflate(R.layout.fragment_breed_info, binding?.root))
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.breed_info_background)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreedInfoBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
