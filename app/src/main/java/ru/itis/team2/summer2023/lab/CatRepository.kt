package ru.itis.team2.summer2023.lab

import android.content.res.Resources
import ru.itis.team2.summer2023.lab.start.StartActivity

object CatRepository {
    val list: List<Cat> = listOf(
        Cat(1, R.string.breed1, 1, R.drawable.cat_idle1, R.drawable.cat_idle_animation,
            CatAnimation(
                R.drawable.cat_idle_animation,
                R.drawable.cat_eat_animation,
                R.drawable.cat_wash_animation,
                R.drawable.cat_meow_animation,
                R.drawable.cat_sad_animation,
                R.drawable.cat_to_sleep_animation,
                R.drawable.cat_sleep_animation,
                R.drawable.cat_from_sleep_animation),
            true, 100, 100, 100, 100, R.string.breed1_info,0, false),
        Cat(2, R.string.breed2, 1, R.drawable.cat_idle1, R.drawable.cat_idle_animation,
            CatAnimation(
                R.drawable.cat_idle_animation,
                R.drawable.cat_eat_animation,
                R.drawable.cat_wash_animation,
                R.drawable.cat_meow_animation,
                R.drawable.cat_sad_animation,
                R.drawable.cat_to_sleep_animation,
                R.drawable.cat_sleep_animation,
                R.drawable.cat_from_sleep_animation),
            false, 100, 100, 100, 100, R.string.breed2_info, 0, false),
        Cat(3, R.string.breed3, 5, R.drawable.cat_idle1, R.drawable.cat_idle_animation,
            CatAnimation(
                R.drawable.cat_idle_animation,
                R.drawable.cat_eat_animation,
                R.drawable.cat_wash_animation,
                R.drawable.cat_meow_animation,
                R.drawable.cat_sad_animation,
                R.drawable.cat_to_sleep_animation,
                R.drawable.cat_sleep_animation,
                R.drawable.cat_from_sleep_animation),
            false, 100, 100, 100, 100, R.string.breed3_info, 0, false)
    )
}
