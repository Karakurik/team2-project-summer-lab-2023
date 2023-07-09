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
        private fun findCat(id: Int): Cat? {
            for (cat in CatRepository.list) {
                if (cat.id == id) return cat
            }
            return null
        }
        fun getCat(id: Int, sharedPreferences: SharedPreferences): Cat{
            val string = sharedPreferences.getString("$id cat", "")
            return Gson().fromJson(string, Cat::class.java)
        }
        fun setHunger(value: Int, cat: Cat, sharedPreferences: SharedPreferences):Cat{
            val settingValue: Int = if (value > 100) 100 else value
            cat.hunger = settingValue
            findCat(cat.id)!!.hunger = settingValue
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
            return getCat(cat.id, sharedPreferences)
        }
        fun setCurrentAnimation(value: Int, cat: Cat, sharedPreferences: SharedPreferences):Cat{
            cat.currentAnimation = value
            findCat(cat.id)!!.currentAnimation = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
            return getCat(cat.id, sharedPreferences)
        }
        fun setHappy(value: Int, cat: Cat, sharedPreferences: SharedPreferences):Cat{
            val settingValue: Int = if (value > 100) 100 else value
            cat.happy = settingValue
            findCat(cat.id)!!.happy = settingValue
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
            return getCat(cat.id, sharedPreferences)
        }
        fun setSleep(value: Int, cat: Cat, sharedPreferences: SharedPreferences):Cat{
            val settingValue: Int = if (value > 100) 100 else value
            cat.sleep = settingValue
            findCat(cat.id)!!.sleep = settingValue
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
            return getCat(cat.id, sharedPreferences)
        }
        fun setPurity(value: Int, cat: Cat, sharedPreferences: SharedPreferences):Cat{
            val settingValue: Int = if (value > 100) 100 else value
            cat.purity = settingValue
            findCat(cat.id)!!.purity = settingValue
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
            return getCat(cat.id, sharedPreferences)
        }
        fun setOpen(value: Boolean, cat: Cat, sharedPreferences: SharedPreferences):Cat{
            cat.open = value
            findCat(cat.id)!!.open = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
            return getCat(cat.id, sharedPreferences)
        }
        fun setBusy(value: Boolean, cat: Cat, sharedPreferences: SharedPreferences): Cat{
            cat.isBusy = value
            findCat(cat.id)!!.isBusy = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
            return getCat(cat.id, sharedPreferences)
        }
        fun setAge(value: Long, cat: Cat, sharedPreferences: SharedPreferences): Cat{
            cat.age = value
            findCat(cat.id)!!.age = value
            sharedPreferences.edit {
                putString("${cat.id} cat", Gson().toJson(cat))
            }
            return getCat(cat.id, sharedPreferences)
        }
    }
}
