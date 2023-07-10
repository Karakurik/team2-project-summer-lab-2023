package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.ActivityGameBinding
import ru.itis.team2.summer2023.lab.start.StartActivity
import java.util.Timer
import java.util.TimerTask
import ru.itis.team2.summer2023.lab.Constants.Companion.BACKGROUND_COLOR


class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    var sharedPreferences: SharedPreferences? = null
    var animations: HashMap<Int, AnimationDrawable> = HashMap()
    private var catUpdateTimer: Timer? = null
    private var catUpdateTask: CatUpdateTask? = null
    private var pbUpdateTimer: Timer? = null
    private var pbUpdateTask: PBUpdateTask? = null
    var mp: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this.getSharedPreferences("", Context.MODE_PRIVATE)

        mp = MediaPlayer.create(this, R.raw.music_game)
        mp?.setLooping(true)

        sharedPreferences?.getInt(BACKGROUND_COLOR, 0)
            ?.let { binding.clGame.setBackgroundColor(it) }

        val id = sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF)

        Cat.updateRepo(id, sharedPreferences!!)

        var cat = Cat.getCat(id)
        if (cat.age == 0L) {
            cat = Cat.setAge(System.currentTimeMillis(), id)
        }
        initAnimations(cat)
        binding.tvCarePointsValue.text = "${getString(R.string.care_points)} ${sharedPreferences!!.getInt("care_points", Constants.START_CARE_POINTS)}"
        cat = Cat.setBusy(false, id)
        cat = setDefaultAnimation(cat)

        catUpdateTimer?.cancel()
        catUpdateTimer = Timer()
        catUpdateTask = CatUpdateTask(this)
        catUpdateTimer!!.schedule(catUpdateTask, Constants.CAT_UPDATE_VALUES, Constants.CAT_UPDATE_VALUES)

        pbUpdateTimer?.cancel()
        pbUpdateTimer = Timer()
        pbUpdateTask = PBUpdateTask(this)
        pbUpdateTimer!!.schedule(pbUpdateTask, 0, Constants.PB_UPDATE_VALUES)

        val fragment =
            supportFragmentManager.findFragmentById(R.id.game_container) as? NavHostFragment
        val controller = fragment?.navController

        findViewById<BottomNavigationView>(R.id.bnv_main).apply {
            if (controller != null) {
                setupWithNavController(controller)
            }
        }

        binding.btnToStart.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }

        binding.btnBreedInfo.setOnClickListener {
            val dialog = cat.breed.let { it1 ->
                cat.breed_info.let { it2 ->
                    AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
                        .setTitle(it1)
                        .setMessage(it2).create()
                }
            }
            dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            dialog?.show()
        }
    }
    class PBUpdateTask(private val activity: GameActivity): TimerTask(){
        override fun run() {
            activity.runOnUiThread {
                val cat = Cat.getCat(activity.sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF))
                activity.binding.pbHappy.progress = cat.happy
                activity.binding.pbHunger.progress = cat.hunger
                activity.binding.pbPurity.progress = cat.purity
                activity.binding.pbSleep.progress = cat.sleep
            }
        }

    }
    class CatUpdateTask(private val activity: GameActivity): TimerTask(){
        override fun run() {
            activity.runOnUiThread {
                val id = activity.sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF)
                var cat = Cat.getCat(id)
                if (!cat.isBusy) {
                    cat = Cat.setBusy(true, id)
                    cat = activity.setDefaultAnimation(cat)
                    cat = if (cat.hunger - Constants.LOW_FACTOR > 0) {
                        Cat.setHunger(
                            cat.hunger - Constants.LOW_FACTOR,
                            id
                        )
                    } else Cat.setHunger(0, id)
                    cat = if (cat.sleep - Constants.LOW_FACTOR > 0) {
                        Cat.setSleep(
                            cat.sleep - Constants.LOW_FACTOR,
                            id
                        )
                    } else Cat.setSleep(0, id)
                    cat = if (cat.happy - Constants.LOW_FACTOR > 0) {
                        Cat.setHappy(
                            cat.happy - Constants.LOW_FACTOR,
                            id
                        )
                    } else Cat.setHappy(0, id)
                    cat = if (cat.purity - Constants.LOW_FACTOR > 0) {
                        Cat.setPurity(
                            cat.purity - Constants.LOW_FACTOR,
                            id
                        )
                    } else Cat.setPurity(0, id)
                    Cat.setBusy(false, id)
                }
            }
        }
    }
    fun setDefaultAnimation(cat:Cat): Cat{
        val score = getTotalScore(cat)
        if (score < 50 && animations[cat.animations.sad]?.alpha != 255){
            if (animations[cat.currentAnimation]?.isRunning == true){
                animations[cat.currentAnimation]?.alpha = 0
                animations[cat.currentAnimation]?.stop()
            }
            animations[cat.animations.sad]?.alpha = 255
            animations[cat.animations.sad]?.start()
            Cat.setCurrentAnimation(cat.animations.sad, cat.id)
        } else if (score >= 50 && animations[cat.animations.idle]?.alpha != 255){
            if (animations[cat.currentAnimation]?.isRunning == true){
                animations[cat.currentAnimation]?.alpha = 0
                animations[cat.currentAnimation]?.stop()
            }
            animations[cat.animations.idle]?.alpha = 255
            animations[cat.animations.idle]?.start()
            Cat.setCurrentAnimation(cat.animations.idle, cat.id)
        }
        return Cat.getCat(cat.id)
    }
    private fun getTotalScore(cat: Cat): Int{
        return ((cat.hunger+cat.happy+cat.purity+cat.sleep)*100/400)
    }

    override fun onResume(){
        super.onResume()
        val sharedPreferences = getSharedPreferences("", MODE_PRIVATE)
        if (sharedPreferences.getBoolean(Constants.MUSIC, Constants.SOUND)) mp?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mp?.stop()
        mp?.release()
        Cat.updateSharedPrefs(sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF), sharedPreferences!!)
    }

    override fun onPause() {
        super.onPause()
        mp?.pause()
    }

    private fun initAnimations(cat: Cat){
        binding.ivCatIdle.setBackgroundResource(cat.animations.idle)
        binding.ivCatSad.setBackgroundResource(cat.animations.sad)
        binding.ivCatEat.setBackgroundResource(cat.animations.eat)
        binding.ivCatMeow.setBackgroundResource(cat.animations.meow)
        binding.ivCatWash.setBackgroundResource(cat.animations.wash)
        binding.ivCatToSleep.setBackgroundResource(cat.animations.toSleep)
        binding.ivCatSleeping.setBackgroundResource(cat.animations.sleep)
        binding.ivCatFromSleep.setBackgroundResource(cat.animations.fromSleep)
        animations[cat.animations.idle] = binding.ivCatIdle.background as AnimationDrawable
        animations[cat.animations.sad] = binding.ivCatSad.background as AnimationDrawable
        animations[cat.animations.eat] = binding.ivCatEat.background as AnimationDrawable
        animations[cat.animations.meow] = binding.ivCatMeow.background as AnimationDrawable
        animations[cat.animations.wash] = binding.ivCatWash.background as AnimationDrawable
        animations[cat.animations.toSleep] = binding.ivCatToSleep.background as AnimationDrawable
        animations[cat.animations.sleep] = binding.ivCatSleeping.background as AnimationDrawable
        animations[cat.animations.fromSleep] = binding.ivCatFromSleep.background as AnimationDrawable
        animations.forEach{(_: Int, animation: AnimationDrawable) -> animation.alpha = 0}
    }
}
