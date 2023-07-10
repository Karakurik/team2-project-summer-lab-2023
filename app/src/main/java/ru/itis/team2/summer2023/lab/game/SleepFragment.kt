package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog.Builder
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.edit
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentSleepBinding

class SleepFragment : Fragment(R.layout.fragment_sleep) {
    private var binding: FragmentSleepBinding? = null
    private var light: Boolean = true
    private val advice: Int = R.string.sleep_advice
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepBinding.bind(view)

        var sharedPreferences = this.activity?.getSharedPreferences("", Context.MODE_PRIVATE)

        binding?.run {
            btnLight.setOnClickListener {
//                val img = activity?.findViewById<View>(R.id.cl_game)
//                var back = 0
//                if (light) back = sharedPreferences!!.getInt(Constants.BACKGROUND_COLOR, 0)
//                else back = R.drawable.dialog_background
//                img?.setBackgroundColor(back)
//
//                light = !light
                val color = getNegativeColour(sharedPreferences!!.getInt(Constants.BACKGROUND_COLOR, 0))
                activity?.findViewById<View>(R.id.cl_game)?.setBackgroundColor(color)
                sharedPreferences.edit {
                    putInt(Constants.BACKGROUND_COLOR, color)
                }
            }

            btnAdvice.setOnClickListener {
                val dialog = Builder(activity, R.style.MyAlertDialogTheme).setMessage(advice).create()
                dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                dialog.show()
            }
        }
    }
    fun getNegativeColour(colour: Int) : Int{
        val red = 255 - Color.red(colour)
        val green = 255 - Color.green(colour)
        val blue = 255 - Color.blue(colour)
        return Color.rgb(red, green, blue)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
