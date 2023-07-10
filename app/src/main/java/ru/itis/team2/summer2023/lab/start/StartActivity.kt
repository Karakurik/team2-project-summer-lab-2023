package ru.itis.team2.summer2023.lab.start

import android.content.SharedPreferences
import android.graphics.Color.WHITE
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.gson.Gson
import ru.itis.team2.summer2023.lab.CatRepository
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.Constants.Companion.BACKGROUND_COLOR
import ru.itis.team2.summer2023.lab.Constants.Companion.MUSIC
import ru.itis.team2.summer2023.lab.Constants.Companion.SOUND
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.ActivityStartBinding
import ru.itis.team2.summer2023.lab.game.RecycleView.KitchenRepository


class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private var mp: MediaPlayer? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("", MODE_PRIVATE)
        mp = MediaPlayer.create(this, R.raw.music_start)
        mp?.setLooping(true)

        //getSharedPreferences("", MODE_PRIVATE).edit().clear().apply(); // это временно чтобы удалять предыдущие записанные значения потом уберем
        initSharedPreference()
    }


    override fun onResume(){
        super.onResume()
        if (sharedPreferences?.getBoolean(MUSIC, SOUND)!!) mp?.start()
    }

    fun musicChange(){
        var on = true
        if (mp?.isPlaying == true){
            mp?.pause()
            on = false
        } else {
            mp?.start()
        }
        sharedPreferences?.edit{
            putBoolean(MUSIC, on)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mp?.stop()
        mp?.release()
    }

    override fun onPause() {
        super.onPause()
        mp?.pause()
    }

    private fun initSharedPreference() {
        if (sharedPreferences?.all?.isEmpty()!!) {
            val gson = Gson()

            var index: Int = 1
            for (cat in CatRepository.list) {
                sharedPreferences?.edit {
                    putString("$index cat", gson.toJson(cat))
                }
                index++
            }

            sharedPreferences?.edit {
                putInt("last_cat_id", Constants.LAST_CAT_ID_DEF)
                putInt("care_points", Constants.START_CARE_POINTS)
                putInt("number_of_cats", Constants.START_CAT_AMOUNT)
                putBoolean(MUSIC, SOUND)
                putInt(BACKGROUND_COLOR, WHITE)
            }

            index = 1
            for (product in KitchenRepository.list) {
                sharedPreferences?.edit {
                    putString("$index product", gson.toJson(product))
                    index++
                }
            }
        }
    }
}
