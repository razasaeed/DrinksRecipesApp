package drinks.recipes.app

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesService {

    @GET("api/json/v1/1/search.php?")
    fun getRecipesList(@Query("s") year : String) : Call<RecipeResponse>

    @GET("api/json/v1/1/search.php?")
    fun getAlphabeticRecipesList(@Query("f") year : String) : Call<RecipeResponse>

}