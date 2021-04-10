package drinks.recipes.app.Adapters

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import drinks.recipes.app.Manager.DatabaseHandler
import drinks.recipes.app.R
import drinks.recipes.app.Models.RecipeModel
import drinks.recipes.app.Utils
import drinks.recipes.app.ViewModel.MainViewModel
import kotlinx.android.synthetic.main.recipe_layout.view.*

class RecipesAdapter(val viewModel: MainViewModel, val arrayList: ArrayList<RecipeModel>, val context: Context): RecyclerView.Adapter<RecipesAdapter.NotesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipesAdapter.NotesViewHolder {
        var root = LayoutInflater.from(parent.context).inflate(R.layout.recipe_layout,parent,false)
        return NotesViewHolder(root)
    }

    override fun onBindViewHolder(holder: RecipesAdapter.NotesViewHolder, position: Int) {
        val databaseHandler: DatabaseHandler = DatabaseHandler(context)
        holder.bind(arrayList.get(position), databaseHandler)
    }

    override fun getItemCount(): Int {
        if(arrayList.size==0){
            Toast.makeText(context,"List is empty",Toast.LENGTH_LONG).show()
        }else{

        }
        return arrayList.size
    }


    inner class NotesViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind(blog: RecipeModel, adb: DatabaseHandler){
            binding.titleTxt.text = blog.strDrink
            Picasso.with(context).load(blog.strDrinkThumb).into(binding.recipe_photo)
            if (blog.strAlcoholic == "Alcoholic") {
                binding.alcoholCheck.isChecked = true
            }else{
                binding.alcoholCheck.isChecked = false
            }
            for(e in adb.getRecipes()){
                if (e.idDrink == blog.idDrink){
                    Log.d("idsare", blog.idDrink.toString()+" \n")
                    binding.btnFav.setImageResource(R.drawable.filled_star)
                }
            }
            binding.btnFav.setOnClickListener { view ->
                view.btnFav.setImageResource(R.drawable.filled_star)
                val bitmap = (binding.recipe_photo.drawable as BitmapDrawable).bitmap
                var status = adb.insertData(blog.idDrink, blog.strDrink.toString(), blog.strDrinkAlternate.toString(),
                    blog.strTags.toString(), blog.strVideo.toString(), blog.strCategory.toString(), blog.strIBA.toString(),
                    blog.strAlcoholic.toString(), blog.strGlass.toString(), blog.strInstructions.toString(),
                    blog.strImageSource.toString(), blog.strDrinkThumb.toString(), Utils.getBytes(bitmap))

                Toast.makeText(view.context, "${status} and ${blog.strDrink}", Toast.LENGTH_SHORT).show()
            }
        }

    }

}