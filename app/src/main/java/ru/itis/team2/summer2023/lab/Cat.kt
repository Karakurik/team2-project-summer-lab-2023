package ru.itis.team2.summer2023.lab

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

data class Cat(

    val id: Int,
    val breed: Int,
    val carePoints: Int,
    val catalogImage: Int,
    var currentAnimation: Int,
    val animations: CatAnimation,
    var open: Boolean,
    var hunger: Int,
    var happy: Int,
    var sleep: Int,
    var purity: Int,
    val breed_info : Int,
    var isBusy: Boolean
){
    companion object {
        fun getCat(id: Int): Cat {
            return CatRepository.list.first { cat:Cat -> cat.id == id }
        }
        fun updateRepo(id: Int, sharedPreferences: SharedPreferences): Cat {
            val string = sharedPreferences.getString("$id cat", "")
            val newCat =  Gson().fromJson(string, Cat::class.java)
            val cat = getCat(id)
            cat.open = newCat.open
            cat.happy = newCat.happy
            cat.hunger = newCat.hunger
            cat.purity = newCat.purity
            cat.sleep = newCat.sleep
            cat.open = newCat.open
            cat.currentAnimation = newCat.currentAnimation
            return cat
        }
        fun updateSharedPrefs(id: Int, sharedPreferences: SharedPreferences){
            sharedPreferences.edit {
                putString("$id cat", Gson().toJson(getCat(id)))
            }
        }
        fun setHunger(value: Int, id: Int):Cat{
            val settingValue: Int = if (value > 100) 100 else value
            val cat = getCat(id)
            cat.hunger = settingValue
            return cat
        }
        fun setCurrentAnimation(value: Int, id: Int):Cat{
            val cat = getCat(id)
            cat.currentAnimation = value
            return cat
        }
        fun setHappy(value: Int, id: Int):Cat{
            val settingValue: Int = if (value > 100) 100 else value
            val cat = getCat(id)
            cat.happy = settingValue
            return cat
        }
        fun setSleep(value: Int, id: Int):Cat{
            val settingValue: Int = if (value > 100) 100 else value
            val cat = getCat(id)
            cat.sleep = settingValue
            return cat
        }
        fun setPurity(value: Int, id: Int):Cat{
            val settingValue: Int = if (value > 100) 100 else value
            val cat = getCat(id)
            cat.purity = settingValue
            return cat
        }
        fun setOpen(value: Boolean, id: Int):Cat{
            val cat = getCat(id)
            cat.open = value
            return cat
        }
        fun setBusy(value: Boolean, id: Int): Cat{
            val cat = getCat(id)
            cat.isBusy = value
            return cat
        }
    }
}
