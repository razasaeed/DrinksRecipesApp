package drinks.recipes.app

import com.google.gson.annotations.SerializedName
import drinks.recipes.app.Models.RecipeModel

public class RecipeResponse {

    @SerializedName("drinks")
    var getAllFoods = ArrayList<RecipeModel>()

}