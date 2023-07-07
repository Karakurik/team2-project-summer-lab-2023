package ru.itis.team2.summer2023.lab

data class Cat(

    val id: Int,
    val breed: Int,
    var carePoints: Int,
    val catalogImage: Int,
    val animations: CatAnimation,
    var open: Boolean,
    var hunger: Int,
    var happy: Int,
    var sleep: Int,
    var purity: Int,
    val breed_info : Int,
    var age: Long,

)
