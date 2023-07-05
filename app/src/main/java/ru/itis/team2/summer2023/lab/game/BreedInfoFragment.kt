package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.DialogFragment
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentBreedInfoBinding


class BreedInfoFragment(id: Int) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder
            .setView(activity?.layoutInflater?.inflate(R.layout.fragment_breed_info, null))
        val dialog = builder.create()
        dialog.getWindow()?.setBackgroundDrawableResource(R.drawable.breed_info_background)
        return dialog
    }
}
