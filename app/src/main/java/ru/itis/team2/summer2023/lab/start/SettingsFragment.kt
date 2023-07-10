package ru.itis.team2.summer2023.lab.start

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color.parseColor
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import ru.itis.team2.summer2023.lab.Constants.Companion.BACKGROUND_COLOR
import ru.itis.team2.summer2023.lab.Constants.Companion.COLOR_BLUE
import ru.itis.team2.summer2023.lab.Constants.Companion.COLOR_GREEN
import ru.itis.team2.summer2023.lab.Constants.Companion.COLOR_RED
import ru.itis.team2.summer2023.lab.Constants.Companion.MUSIC
import ru.itis.team2.summer2023.lab.Constants.Companion.SOUND
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentSettingsBinding
import java.util.Locale


class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var binding: FragmentSettingsBinding? = null
    private var sharedPreferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        sharedPreferences = activity?.getSharedPreferences("", MODE_PRIVATE)

        checkText()

        binding?.run{
            sharedPreferences?.getInt(BACKGROUND_COLOR, 0)?.let {
                color.setBackgroundColor(it)
            }
            sbRed.progress = sharedPreferences?.getInt(COLOR_RED, 255)!!
            sbGreen.progress = sharedPreferences?.getInt(COLOR_GREEN, 255)!!
            sbBlue.progress = sharedPreferences?.getInt(COLOR_BLUE, 255)!!

            btnSound.setOnClickListener{
                activity?.let {
                    (it as StartActivity).musicChange()
                }
                checkText()
            }

            btnLanguage.setOnClickListener{

            }

            sbRed.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    setColor(sbRed.progress, sbGreen.progress, sbBlue.progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    sharedPreferences?.edit {
                        putInt(COLOR_RED, sbRed.progress)
                    }
                }
            })

            sbGreen.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    setColor(sbRed.progress, sbGreen.progress, sbBlue.progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    sharedPreferences?.edit {
                        putInt(COLOR_GREEN, sbGreen.progress)
                    }
                }
            })

            sbBlue.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    setColor(sbRed.progress, sbGreen.progress, sbBlue.progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    sharedPreferences?.edit {
                        putInt(COLOR_BLUE, sbBlue.progress)
                    }
                }
            })
        }
    }

    fun checkText(){
        binding?.run {
            if (sharedPreferences?.getBoolean(MUSIC, SOUND) == true) {
                btnSound.text = getString(R.string.off)
            } else btnSound.text = getString(R.string.on)
        }
    }

    fun setColor(red:Int, green:Int, blue:Int){
        val back_color = parseColor(toHexColor(red, green, blue))
        sharedPreferences?.edit {
            putInt(BACKGROUND_COLOR, back_color)
        }
        binding?.color?.setBackgroundColor(back_color)
    }

    fun toHexColor(red:Int, green:Int, blue:Int): String {
        val redStr = red.toString(16)
        val redPart = if (redStr.length < 2) "0$redStr" else redStr
        val greenStr = green.toString(16)
        val greenPart = if (greenStr.length < 2) "0$greenStr" else greenStr
        val blueStr = blue.toString(16)
        val bluePart = if (blueStr.length < 2) "0$blueStr" else blueStr
        return "#ff$redPart$greenPart$bluePart"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
