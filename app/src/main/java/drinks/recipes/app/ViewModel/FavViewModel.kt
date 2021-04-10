package drinks.recipes.app.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import drinks.recipes.app.Models.RecipeModelTwo

class FavViewModel: ViewModel() {
    var lst = MutableLiveData<ArrayList<RecipeModelTwo>>()
    var newlist = arrayListOf<RecipeModelTwo>()

    fun add(blog: ArrayList<RecipeModelTwo>){
        lst.value=blog
    }

    fun remove(index: Int){
        newlist.removeAt(index)
        lst.value=newlist
    }

}