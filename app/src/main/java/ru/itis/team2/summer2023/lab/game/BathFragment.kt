package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentBathBinding
import java.util.Timer
import java.util.TimerTask

class BathFragment : Fragment(R.layout.fragment_bath) {
    private var binding: FragmentBathBinding? = null
    private val advice: Int = R.string.bath_advice
    private var bathTimer: Timer? = null
    private var bathTimerTask: BathTimerTask? = null
    private var sharedPreferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBathBinding.bind(view)

        sharedPreferences = this.activity?.getSharedPreferences("", Context.MODE_PRIVATE)

        binding?.btnWash?.setOnClickListener {
            val id = sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF)
            var cat = Cat.getCat(id)
            if (!cat.isBusy) {
                cat = Cat.setBusy(true,id)
                if (cat.purity < 100) {
                    sharedPreferences?.edit {
                        putInt(
                            "care_points",
                            sharedPreferences!!.getInt(
                                "care_points",
                                Constants.START_CARE_POINTS
                            ) + Constants.STANDART_INCREASE_CARE_POINTS
                        )
                    }
                }
                cat = Cat.setPurity(cat.purity + Constants.STANDART_INCREASE_CAT_VALUES, id)
                requireActivity().findViewById<TextView>(R.id.tv_care_points_value).text =
                    "Очки заботы: ${sharedPreferences!!.getInt("care_points", Constants.START_CARE_POINTS)}"
                bathTimer?.cancel()
                bathTimer = Timer()
                val activity = requireActivity() as GameActivity
                val num = activity.animations[cat.animations.wash]?.numberOfFrames
                var sum = 0
                for (i in 0 until num!!){
                    sum += activity.animations[cat.animations.wash]?.getDuration(i)!!
                }
                if (activity.animations[cat.currentAnimation]?.isRunning == true){
                    activity.animations[cat.currentAnimation]?.stop()
                }
                activity.animations[cat.animations.wash]?.alpha = 255
                activity.animations[cat.currentAnimation]?.alpha = 0
                activity.animations[cat.animations.wash]?.start()
                cat = Cat.setCurrentAnimation(cat.animations.wash, id)
                bathTimerTask = BathTimerTask(activity, cat)
                bathTimer!!.schedule(bathTimerTask, sum.toLong())
            } else {
                binding?.let { Snackbar.make(it.root, "ваш котик уже занят прямо сейчас", Snackbar.LENGTH_SHORT).show() }
            }
        }

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
        Cat.updateSharedPrefs(sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF), sharedPreferences!!)
        binding = null
    }
    class BathTimerTask(private val activity: GameActivity, var cat: Cat): TimerTask(){
        override fun run() {
            activity.runOnUiThread (Runnable {
                activity.animations[cat.currentAnimation]?.alpha = 0
                activity.animations[cat.currentAnimation]?.stop()
                cat = activity.setDefaultAnimation(cat)
                Cat.setBusy(false, cat.id)
            })
        }

    }
}
