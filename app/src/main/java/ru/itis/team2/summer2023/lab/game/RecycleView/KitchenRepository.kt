package ru.itis.team2.summer2023.lab.game.RecycleView

import ru.itis.team2.summer2023.lab.R

object KitchenRepository {
    val list: List<Product> = listOf(
        Product(id = 1, name = "1", R.drawable.dialog_background, 1, 1, true),
        Product(id = 2, name = "2", R.drawable.dialog_background, 2, 5, false),
        Product(id = 3, name = "3", R.drawable.dialog_background,3, 7, false),
        Product(id = 4, name = "4", R.drawable.dialog_background, 5, 10, false)
    )
}
