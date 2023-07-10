package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog.Builder
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentSleepBinding
import java.util.Timer
import java.util.TimerTask

class SleepFragment : Fragment(R.layout.fragment_sleep) {
    private var binding: FragmentSleepBinding? = null
    var light: Boolean = true
    private val advice: Int = R.string.sleep_advice
    private var toSleepTimer: Timer? = null
    private var toSleepTimerTask: ToSleepTimerTask? = null
    private var sleepTimer: Timer? = null
    private var fromSleepTimer: Timer? = null
    private var fromSleepTimerTask: FromSleepTimerTask? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepBinding.bind(view)

        sharedPreferences = this.activity?.getSharedPreferences("", Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF)
        light = sharedPreferences!!.getBoolean("LIGHT_$id", true)

        binding?.run {
            btnLight.setOnClickListener {
                var cat = Cat.getCat(id)

                if (!cat.isBusy || !light) {
                    val color = getNegativeColour(sharedPreferences!!.getInt(Constants.BACKGROUND_COLOR, Color.WHITE))
                    activity?.findViewById<View>(R.id.cl_game)?.setBackgroundColor(color)
                    sharedPreferences!!.edit {
                        putInt(Constants.BACKGROUND_COLOR, color)
                    }
                    if (light){
                        // засыпает, очки заботы зачисляются один раз
                        cat = Cat.setBusy(true,id)
                        if (cat.sleep < 100) {
                            sharedPreferences!!.edit {
                                putInt(
                                    "care_points",
                                    sharedPreferences!!.getInt(
                                        "care_points",
                                        Constants.START_CARE_POINTS
                                    ) + Constants.STANDART_INCREASE_CARE_POINTS
                                )
                            }
                        }
                        requireActivity().findViewById<TextView>(R.id.tv_care_points_value).text =
                            "${getString(R.string.care_points)} ${sharedPreferences!!.getInt("care_points", Constants.START_CARE_POINTS)}"
                        toSleepTimer?.cancel()
                        toSleepTimer = Timer()
                        val activity = requireActivity() as GameActivity
                        val num = activity.animations[cat.animations.toSleep]?.numberOfFrames
                        var sum = 0
                        for (i in 0 until num!!){
                            sum += activity.animations[cat.animations.toSleep]?.getDuration(i)!!
                        }
                        if (activity.animations[cat.currentAnimation]?.isRunning == true){
                            activity.animations[cat.currentAnimation]?.stop()
                        }
                        activity.animations[cat.animations.toSleep]?.alpha = 255
                        activity.animations[cat.currentAnimation]?.alpha = 0
                        activity.animations[cat.animations.toSleep]?.start()
                        cat = Cat.setCurrentAnimation(cat.animations.toSleep, id)
                        toSleepTimerTask = ToSleepTimerTask(activity, cat, sleepTimer, this@SleepFragment)
                        toSleepTimer!!.schedule(toSleepTimerTask, sum.toLong() - 100L)
                    } else {
                         //просыпается
                        light = true
                        sharedPreferences!!.edit{
                            putBoolean("LIGHT_$id", light)
                        }
                        fromSleepTimer?.cancel()
                        fromSleepTimer = Timer()
                        val activity = requireActivity() as GameActivity
                        val num = activity.animations[cat.animations.fromSleep]?.numberOfFrames
                        var sum = 0
                        for (i in 0 until num!!){
                            sum += activity.animations[cat.animations.fromSleep]?.getDuration(i)!!
                        }
                        if (activity.animations[cat.currentAnimation]?.isRunning == true){
                            activity.animations[cat.currentAnimation]?.stop()
                        }
                        activity.animations[cat.animations.fromSleep]?.alpha = 255
                        activity.animations[cat.currentAnimation]?.alpha = 0
                        activity.animations[cat.animations.fromSleep]?.start()
                        cat = Cat.setCurrentAnimation(cat.animations.fromSleep, id)
                        fromSleepTimerTask = FromSleepTimerTask(activity, cat)
                        fromSleepTimer!!.schedule(fromSleepTimerTask, sum.toLong() - 100L)
                    }
                } else {
                    binding?.let { Snackbar.make(it.root, "ваш котик уже занят прямо сейчас", Snackbar.LENGTH_SHORT).show() }
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
    class FromSleepTimerTask(
        private val activity: GameActivity,
        var cat: Cat
        ): TimerTask(){
        override fun run() {
            activity.runOnUiThread (Runnable {
                activity.animations[cat.animations.fromSleep]?.alpha = 0
                activity.animations[cat.animations.fromSleep]?.stop()
                cat = activity.setDefaultAnimation(cat)
                Cat.setBusy(false, cat.id)
            })
        }
    }
    class ToSleepTimerTask(
        private val activity: GameActivity,
        var cat: Cat,
        var sleepTimer: Timer?,
        private val sleepFragment: SleepFragment
        ): TimerTask(){

        private var sleepTimerTask: SleepTimerTask? = null
        override fun run() {
            activity.runOnUiThread (Runnable {
                activity.animations[cat.animations.toSleep]?.alpha = 0
                activity.animations[cat.animations.toSleep]?.stop()
                activity.animations[cat.animations.sleep]?.alpha = 255
                activity.animations[cat.animations.sleep]?.start()
                cat = Cat.setCurrentAnimation(cat.animations.sleep, cat.id)

                sleepFragment.light = !sleepFragment.light
                sleepFragment.sharedPreferences!!.edit{
                    putBoolean("LIGHT_${cat.id}", sleepFragment.light)
                }

                sleepTimer?.cancel()
                sleepTimer = Timer()
                sleepTimerTask = SleepTimerTask(activity, cat, sleepFragment)
                sleepTimer!!.schedule(sleepTimerTask, 0, Constants.CAT_UPDATE_VALUES/4)
            })
        }
        class SleepTimerTask(
            private val activity: GameActivity,
            var cat: Cat,
            private val sleepFragment: SleepFragment
            ): TimerTask(){
            override fun run() {
                activity.runOnUiThread {
                    cat = Cat.getCat(cat.id)
                    if (!sleepFragment.sharedPreferences!!.getBoolean("LIGHT_${cat.id}", true)){
                        cat = if (cat.hunger - Constants.LOW_FACTOR/2 > 0) {
                            Cat.setHunger(
                                cat.hunger - Constants.LOW_FACTOR/2,
                                cat.id
                            )
                        } else Cat.setHunger(0, cat.id)
                        cat = Cat.setSleep(
                            cat.sleep + Constants.STANDART_INCREASE_CAT_VALUES,
                            cat.id
                        )
                        cat = if (cat.purity - Constants.LOW_FACTOR/2 > 0) {
                            Cat.setPurity(
                                cat.purity - Constants.LOW_FACTOR/2,
                                cat.id
                            )
                        } else Cat.setPurity(0, cat.id)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Cat.updateSharedPrefs(sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF), sharedPreferences!!)
        binding = null
    }
}
