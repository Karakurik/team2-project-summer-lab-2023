package ru.itis.team2.summer2023.lab.game.RecycleView

import ru.itis.team2.summer2023.lab.R

object KitchenRepository {
    val list: List<Product> = listOf(
        Product(id = 1, name = R.string.fish, R.drawable.fish, 0, 5, true),
        Product(id = 2, name =  R.string.sausage, R.drawable.sausage, 100, 10, false),
        Product(id = 3, name = R.string.milk, R.drawable.milk,250, 15, false),
        Product(id = 4, name = R.string.catjelly, R.drawable.jelly,250, 20, false),
        Product(id = 5, name = R.string.surprise, R.drawable.secret,1000, 50, false)
    )
}
