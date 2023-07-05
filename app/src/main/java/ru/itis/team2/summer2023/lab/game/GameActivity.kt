package ru.itis.team2.summer2023.lab.game

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.ActivityGameBinding
import ru.itis.team2.summer2023.lab.start.StartActivity


class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            val breedInfoFragment = BreedInfoFragment(15)
            val manager = supportFragmentManager
            breedInfoFragment.show(manager, "myDialog")
        }
    }
}
