package ru.itis.team2.summer2023.lab.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.itis.team2.summer2023.lab.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
