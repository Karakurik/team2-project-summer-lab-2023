package ru.itis.team2.summer2023.lab.game

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import ru.itis.team2.summer2023.lab.R
import ru.itis.team2.summer2023.lab.databinding.FragmentPlayBinding

class PlayFragment : Fragment(R.layout.fragment_play) {
    private var binding: FragmentPlayBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayBinding.bind(view)

        var sharedPreferences = this.activity?.getSharedPreferences("", Context.MODE_PRIVATE)

        val music: MediaPlayer = MediaPlayer.create(this.context, R.raw.cat)

        binding?.run {
            tvScore.text = "Пойманных мышей: 0"
            motion.transitionToEnd()

            var score = 0
            ivMouse.setOnClickListener {
                if (score == 0) {
                    music.playbackParams = music.playbackParams.setSpeed(3f)
                }

                music.start()

                score++
                tvScore.text = "Пойманных мышей: $score"
                if (score % 15 == 0) {
                    var carePoints = sharedPreferences?.getInt("care_points", 0)
                    sharedPreferences?.edit {
                        putInt("care_points", carePoints!! + 1)
                    }
                    // здесь ещё нужно поменять текст у текст вью который показывает количество очков заботы
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
