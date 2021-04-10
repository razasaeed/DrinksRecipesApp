package drinks.recipes.app.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavViewModel::class.java)){
            return FavViewModel() as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}