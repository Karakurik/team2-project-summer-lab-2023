package ru.itis.team2.summer2023.lab.game

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.itis.team2.summer2023.lab.R


class BreedInfoFragment(id: Int) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
        builder.setView(activity?.layoutInflater?.inflate(R.layout.fragment_breed_info, null))
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.breed_info_background)
        return dialog
    }
}
