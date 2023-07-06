package ru.itis.team2.summer2023.lab.start

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import androidx.navigation.fragment.NavHostFragment
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.CatRepository
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.ActivityStartBinding
import ru.itis.team2.summer2023.lab.game.GameActivity

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = (supportFragmentManager.findFragmentById(R.id.start_container) as NavHostFragment).navController

        getSharedPreferences("", MODE_PRIVATE).edit().clear().apply(); // это временно чтобы удалять предыдущие записанные значения потом уберем
        initSharedPreference()
    }

    private fun initSharedPreference() {
        val sharedPreferences = getSharedPreferences("", MODE_PRIVATE)

        if (sharedPreferences.all.isEmpty()) {
            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<Cat> = moshi.adapter(Cat::class.java)

            var index: Int = 1
            for (cat in CatRepository.list) {
                sharedPreferences.edit {
                    putString("$index cat", adapter.toJson(cat))
                }
                index++
            }
            // инициализация начального количества очков и айди уличного кота
            sharedPreferences.edit {
                putInt("last_cat_id", 1)
                putInt("care_points", 1)
            }
        }
    }
}
