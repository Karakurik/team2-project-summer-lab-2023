package ru.itis.team2.summer2023.lab.game

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.edit
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import ru.itis.team2.summer2023.lab.Cat
import ru.itis.team2.summer2023.lab.CatRepository
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.ActivityGameBinding
import ru.itis.team2.summer2023.lab.start.StartActivity


class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = this.getSharedPreferences("", Context.MODE_PRIVATE);

        val int = sharedPreferences.getInt("last_cat_id", 1)
        val string = sharedPreferences.getString("$int cat", "")
        val cat = Gson().fromJson(string, Cat::class.java)
//        if (cat != null && cat.age == 0L) {
//            cat.age = System.currentTimeMillis()
//            this.getSharedPreferences("", Context.MODE_PRIVATE).edit {
//                putString("${cat.id} cat", Gson().toJson(cat))
//            }
//        }
        // cat?.urlImage?.let { binding.ivCat.setImageResource(it) }
        binding.ivCat.foreground = AppCompatResources.getDrawable(applicationContext, cat!!.animations.idle)
        val idleAnim = binding.ivCat.foreground as AnimationDrawable
        idleAnim.start()


        val fragment = supportFragmentManager.findFragmentById(R.id.game_container) as? NavHostFragment
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
}
