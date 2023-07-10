package ru.itis.team2.summer2023.lab.start

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentStartBinding
import ru.itis.team2.summer2023.lab.game.GameActivity

class StartFragment : Fragment(R.layout.fragment_start) {
    private var binding: FragmentStartBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStartBinding.bind(view)

        binding?.run{
            val sharedPreferences = activity?.getSharedPreferences("", AppCompatActivity.MODE_PRIVATE)

            sharedPreferences?.getInt(Constants.BACKGROUND_COLOR, 0)
                ?.let { startFragment.setBackgroundColor(it) }

            btnToGame.setOnClickListener {
                val intent = Intent(requireContext(), GameActivity::class.java)
                startActivity(intent)
            }
            
            btnCatalog.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_catalogFragment)
            }
            
            btnHelp.setOnClickListener {
                val dialog = AlertDialog.Builder(activity, R.style.MyAlertDialogTheme)
                    .setMessage(getString(R.string.learning_text)).create()
                dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                dialog.show()
            }

            btnSettings.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_settingsFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
