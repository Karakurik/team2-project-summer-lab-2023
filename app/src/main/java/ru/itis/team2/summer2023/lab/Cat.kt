package ru.itis.team2.summer2023.lab

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

data class Cat(

    val id: Int,
    val breed: Int,
    var carePoints: Int,
    val catalogImage: Int,
    var currentAnimation: Int,
    val animations: CatAnimation,
    var open: Boolean,
    var hunger: Int,
    var happy: Int,
    var sleep: Int,
    var purity: Int,
    val breed_info : Int,
    var age: Long,
    var isBusy: Boolean
){
    companion object {
        fun findCat(id: Int): Cat? {
            for (cat in CatRepository.list) {
                if (cat.id == id) return cat
            }
            return null
        }
        fun getCat(id: Int, sharedPreferences: SharedPreferences): Cat{
            val string = sharedPreferences.getString("$id cat", "")
            return Gson().fromJson(string, Cat::class.java)
        }
        fun setHunger(value: Int, cat: Cat, sharedPreferences: SharedPreferences){
            cat.hunger = value
            findCat(cat.id)!!.hunger = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
        }
        fun setCurrentAnimation(value: Int, cat: Cat, sharedPreferences: SharedPreferences){
            cat.currentAnimation = value
            findCat(cat.id)!!.currentAnimation = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
        }
        fun setHappy(value: Int, cat: Cat, sharedPreferences: SharedPreferences){
            cat.happy = value
            findCat(cat.id)!!.happy = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
        }
        fun setSleep(value: Int, cat: Cat, sharedPreferences: SharedPreferences){
            cat.sleep = value
            findCat(cat.id)!!.sleep = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
        }
        fun setPurity(value: Int, cat: Cat, sharedPreferences: SharedPreferences){
            cat.purity = value
            findCat(cat.id)!!.purity = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
        }
        fun setOpen(value: Boolean, cat: Cat, sharedPreferences: SharedPreferences){
            cat.open = value
            findCat(cat.id)!!.open = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
        }
        fun setBusy(value: Boolean, cat: Cat, sharedPreferences: SharedPreferences){
            cat.isBusy = value
            findCat(cat.id)!!.isBusy = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
        }
        fun setAge(value: Long, cat: Cat, sharedPreferences: SharedPreferences){
            cat.age = value
            findCat(cat.id)!!.age = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
        }
    }
}
