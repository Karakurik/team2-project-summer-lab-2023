package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.edit
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.CatRepository
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.ActivityGameBinding
import ru.itis.team2.summer2023.lab.start.StartActivity
import kotlin.coroutines.CoroutineContext


class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this.getSharedPreferences("", Context.MODE_PRIVATE)

        val id = sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF)
        val cat = Cat.getCat(id, sharedPreferences!!)
        if (cat.age == 0L) {
            Cat.setAge(System.currentTimeMillis(), cat, sharedPreferences!!)
        }
        initAnimations(cat)

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
        GlobalScope.launch {
            lowCatStats()
        }
    }
    fun initAnimations(cat: Cat){
        binding.ivCatIdle.setBackgroundResource(cat.animations.idle)
        binding.ivCatSad.setBackgroundResource(cat.animations.sad)
        binding.ivCatEat.setBackgroundResource(cat.animations.eat)
        binding.ivCatMeow.setBackgroundResource(cat.animations.meow)
        binding.ivCatWash.setBackgroundResource(cat.animations.wash)
        binding.ivCatToSleep.setBackgroundResource(cat.animations.toSleep)
        binding.ivCatSleeping.setBackgroundResource(cat.animations.sleep)
        binding.ivCatFromSleep.setBackgroundResource(cat.animations.fromSleep)
    }

    suspend fun lowCatStats() = coroutineScope{
        launch {
            while (true){
                delay(Constants.WAIT)

                val cat = Cat.getCat(sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF), sharedPreferences!!)
                if (cat.hunger > 0){
                    Cat.setHunger(cat.hunger - Constants.LOW_FACTOR, cat, sharedPreferences!!)
                }
                if (cat.sleep > 0){
                    Cat.setSleep(cat.sleep - Constants.LOW_FACTOR, cat, sharedPreferences!!)
                }
                if (cat.happy > 0){
                    Cat.setHappy(cat.happy - Constants.LOW_FACTOR, cat, sharedPreferences!!)
                }
                if (cat.purity > 0){
                    Cat.setPurity(cat.purity - Constants.LOW_FACTOR, cat, sharedPreferences!!)
                }
            }
        }
        launch{
            while (true) {
                val cat = Cat.getCat(
                    sharedPreferences!!.getInt("last_cat_id", Constants.LAST_CAT_ID_DEF),
                    sharedPreferences!!
                )
                binding.pbHappy.progress = cat.happy
                binding.pbHunger.progress = cat.hunger
                binding.pbPurity.progress = cat.purity
                binding.pbSleep.progress = cat.sleep
            }
        }
    }
}
