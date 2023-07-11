package ru.itis.team2.summer2023.lab

import android.content.res.Resources
import ru.itis.team2.summer2023.lab.start.StartActivity

object CatRepository {
    val list: List<Cat> = listOf(
        Cat(1, R.string.breed1, 0, R.drawable.cat_idle1, R.drawable.cat_idle_animation,
            CatAnimation(
                R.drawable.cat_idle_animation,
                R.drawable.cat_eat_animation,
                R.drawable.cat_wash_animation,
                R.drawable.cat_meow_animation,
                R.drawable.cat_sad_animation,
                R.drawable.cat_to_sleep_animation,
                R.drawable.cat_sleep_animation,
                R.drawable.cat_from_sleep_animation),
            true, 100, 100, 100, 100, R.string.breed1_info, false),
        Cat(2, R.string.breed2, 100, R.drawable.blue_idle1, R.drawable.blue_idle_animation,
            CatAnimation(
                R.drawable.blue_idle_animation,
                R.drawable.blue_eat_animation,
                R.drawable.blue_wash_animation,
                R.drawable.blue_meow_animation,
                R.drawable.blue_sad_animation,
                R.drawable.blue_to_sleep_animation,
                R.drawable.blue_sleep_animation,
                R.drawable.blue_from_sleep_animation),
            false, 100, 100, 100, 100, R.string.breed2_info, false),
        Cat(3, R.string.breed3, 1000, R.drawable.cat_idle1, R.drawable.cat_idle_animation,
            CatAnimation(
                R.drawable.cat_idle_animation,
                R.drawable.cat_eat_animation,
                R.drawable.cat_wash_animation,
                R.drawable.cat_meow_animation,
                R.drawable.cat_sad_animation,
                R.drawable.cat_to_sleep_animation,
                R.drawable.cat_sleep_animation,
                R.drawable.cat_from_sleep_animation),
            false, 100, 100, 100, 100, R.string.breed3_info, false)
    )
}
