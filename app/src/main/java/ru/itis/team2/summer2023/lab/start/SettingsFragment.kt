package ru.itis.team2.summer2023.lab.start

import android.content.Context.MODE_PRIVATE
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


class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var binding: FragmentSettingsBinding? = null
    private val on: String = "включить звук"
    private val off: String = "выключить звук"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        val sp = activity?.getSharedPreferences("", MODE_PRIVATE)

        checkText()
        binding?.run{
            sp?.getInt(BACKGROUND_COLOR, 0)?.let { color.setBackgroundColor(it) }
            sbRed.progress = sp?.getInt(COLOR_RED, 0)!!
            sbGreen.progress = sp?.getInt(COLOR_GREEN, 0)!!
            sbBlue.progress = sp?.getInt(COLOR_BLUE, 0)!!

            btnSound.setOnClickListener{
                activity?.let {
                    (it as StartActivity).musicChange()
                }
                checkText()
            }

            sbRed.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    setColor(sbRed.progress, sbGreen.progress, sbBlue.progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    sp?.edit {
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
                    sp?.edit {
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
                    sp?.edit {
                        putInt(COLOR_BLUE, sbBlue.progress)
                    }
                }
            })
        }
    }

    fun checkText(){
        binding?.run {
            if (activity?.getSharedPreferences("", MODE_PRIVATE)?.getBoolean(MUSIC, SOUND) == true) {
                btnSound.text = off
            } else btnSound.text = on
        }
    }

    fun setColor(red:Int, green:Int, blue:Int){
        val back_color = parseColor(toHexColor(red, green, blue))
        activity?.getSharedPreferences("", MODE_PRIVATE)?.edit {
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
