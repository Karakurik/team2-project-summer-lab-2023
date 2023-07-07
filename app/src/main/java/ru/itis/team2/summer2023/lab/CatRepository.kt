package ru.itis.team2.summer2023.lab

import android.content.res.Resources
import ru.itis.team2.summer2023.lab.start.StartActivity

object CatRepository {
    val list: List<Cat> = listOf(
        Cat(1, R.string.breed1, 1, R.raw.cat, true, 100, 100, 100, 100),
        Cat(2, R.string.breed2, 1, R.raw.cat, false, 100, 100, 100, 100),
        Cat(3, R.string.breed3, 5, R.raw.cat, false, 100, 100, 100, 100)
    )
}
