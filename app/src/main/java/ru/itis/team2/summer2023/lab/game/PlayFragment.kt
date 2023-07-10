package ru.itis.team2.summer2023.lab.game

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.Constants.Companion.MUSIC
import ru.itis.team2.summer2023.lab.Constants.Companion.SOUND
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentPlayBinding
import java.util.Timer
import java.util.TimerTask

class PlayFragment : Fragment(R.layout.fragment_play) {
    private var binding: FragmentPlayBinding? = null
    private var meowTimer: Timer? = null
    private var meowTimerTask: MeowTimerTask? = null
    private var sharedPreferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayBinding.bind(view)

        sharedPreferences = this.activity?.getSharedPreferences("", Context.MODE_PRIVATE)

        val music: MediaPlayer = MediaPlayer.create(this.context, R.raw.cat)

        binding?.run {
            tvScore.text = "${getString(R.string.mouse)} 0"
            motion.transitionToEnd()

            var score = 0
            ivMouse.setOnClickListener {
                val id = sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF)
                var cat = Cat.getCat(id)
                if (!sharedPreferences!!.getBoolean("LIGHT_$id", true)){
                    binding?.let { Snackbar.make(it.root, "дайте вашему котику отдохнуть", Snackbar.LENGTH_SHORT).show() }
                }
                if (!cat.isBusy) {
                    cat = Cat.setBusy(true,id)

                    if (sharedPreferences!!.getBoolean(MUSIC, SOUND)) music.start()
                    score++
                    tvScore.text = getString(R.string.mouse) + " $score"
                    if (score % Constants.MOUSES == 0) {
                        val carePoints = sharedPreferences!!.getInt("care_points", Constants.START_CARE_POINTS)
                        sharedPreferences!!.edit {
                            putInt("care_points", carePoints + Constants.STANDART_INCREASE_CARE_POINTS)
                        }
                        requireActivity().findViewById<TextView>(R.id.tv_care_points_value).text =
                            "${getString(R.string.care_points)} ${sharedPreferences!!.getInt("care_points", Constants.START_CARE_POINTS)}"
                    }
                    cat = Cat.setHappy(cat.happy + Constants.STANDART_INCREASE_CAT_VALUES, id)
                    meowTimer?.cancel()
                    meowTimer = Timer()
                    val activity = requireActivity() as GameActivity
                    val num = activity.animations[cat.animations.meow]?.numberOfFrames
                    var sum = 0
                    for (i in 0 until num!!){
                        sum += activity.animations[cat.animations.meow]?.getDuration(i)!!
                    }
                    if (activity.animations[cat.currentAnimation]?.isRunning == true){
                        activity.animations[cat.currentAnimation]?.stop()
                    }
                    activity.animations[cat.animations.meow]?.alpha = 255
                    activity.animations[cat.currentAnimation]?.alpha = 0
                    activity.animations[cat.animations.meow]?.start()
                    cat = Cat.setCurrentAnimation(cat.animations.meow, id)
                    meowTimerTask = MeowTimerTask(activity, cat)
                    meowTimer!!.schedule(meowTimerTask, sum.toLong())
                }
            }
        }
    }
    class MeowTimerTask(private val activity: GameActivity, var cat: Cat): TimerTask() {
        override fun run() {
            activity.runOnUiThread(Runnable {
                activity.animations[cat.animations.meow]?.alpha = 0
                activity.animations[cat.animations.meow]?.stop()
                cat = activity.setDefaultAnimation(cat)
                Cat.setBusy(false, cat.id)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Cat.updateSharedPrefs(sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF), sharedPreferences!!)
        binding = null
    }
}
