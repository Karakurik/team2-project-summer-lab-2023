package ru.itis.team2.summer2023.lab.start

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson

import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.CatRepository
import ru.itis.team2.summer2023.lab.Constants
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.ActivityStartBinding
import ru.itis.team2.summer2023.lab.game.GameActivity
import ru.itis.team2.summer2023.lab.game.KitchenFragment
import ru.itis.team2.summer2023.lab.game.RecycleView.KitchenRepository
import ru.itis.team2.summer2023.lab.game.RecycleView.PlayRepository

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = (supportFragmentManager.findFragmentById(R.id.start_container) as NavHostFragment).navController

        //getSharedPreferences("", MODE_PRIVATE).edit().clear().apply(); // это временно чтобы удалять предыдущие записанные значения потом уберем
        initSharedPreference()
    }

    private fun initSharedPreference() {
        val sharedPreferences = getSharedPreferences("", MODE_PRIVATE)

        if (sharedPreferences.all.isEmpty()) {
            val gson = Gson()

            var index: Int = 1
            for (cat in CatRepository.list) {
                sharedPreferences.edit {
                    putString("$index cat", gson.toJson(cat))
                }
                index++
            }
            // инициализация начального количества очков и айди уличного кота
            sharedPreferences.edit {
                putInt("last_cat_id", Constants.LAST_CAT_ID_DEF)
                putInt("care_points", Constants.START_CARE_POINTS)
                putInt("number_of_cats", Constants.START_CAT_AMOUNT)
            }

            index = 1
            for (product in KitchenRepository.list) {
                sharedPreferences.edit {
                    putString("$index product", gson.toJson(product))
                    index++
                }
            }

            index = 1
            for (product in PlayRepository.list) {
                sharedPreferences.edit {
                    putString("$index game", gson.toJson(product))
                    index++
                }
            }
        }
    }
}
