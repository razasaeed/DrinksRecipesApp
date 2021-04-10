package drinks.recipes.app.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import drinks.recipes.app.Models.RecipeModel

class MainViewModel: ViewModel() {
    var lst = MutableLiveData<ArrayList<RecipeModel>>()
    var newlist = arrayListOf<RecipeModel>()

    fun add(blog: ArrayList<RecipeModel>){
        newlist = blog
        lst.value=newlist
    }

    fun remove(blog: RecipeModel){
        newlist.remove(blog)
        lst.value=newlist
    }

}