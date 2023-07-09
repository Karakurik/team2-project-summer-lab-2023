package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog.Builder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentSleepBinding

class SleepFragment : Fragment(R.layout.fragment_sleep) {
    private var binding: FragmentSleepBinding? = null
    private var light: Boolean = true
    private val advice: Int = R.string.sleep_advice
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepBinding.bind(view)

        binding?.run {
            btnLight.setOnClickListener {
                val img = activity?.findViewById<View>(R.id.cl_game)
                var back = 0
                if (light) back = R.drawable.ic_launcher_background
                else back = R.drawable.dialog_background
                img?.setBackgroundResource(back)
                light = !light
                //ну тут меняется фон типо включили выключили свет
                // мб котик должен глаза закрывать
            }

            btnAdvice.setOnClickListener {
                val dialog = Builder(activity, R.style.MyAlertDialogTheme).setMessage(advice).create()
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
