package drinks.recipes.app.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import drinks.recipes.app.Manager.DatabaseHandler
import drinks.recipes.app.R
import drinks.recipes.app.Models.RecipeModelTwo
import drinks.recipes.app.Utils
import drinks.recipes.app.ViewModel.FavViewModel
import kotlinx.android.synthetic.main.recipe_layout.view.*

class FavoriteAdapter(val viewModel: FavViewModel, val arrayList: ArrayList<RecipeModelTwo>, val context: Context): RecyclerView.Adapter<FavoriteAdapter.NotesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.NotesViewHolder {
        var root = LayoutInflater.from(parent.context).inflate(R.layout.recipe_layout,parent,false)
        return NotesViewHolder(root)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.NotesViewHolder, position: Int) {
        val databaseHandler: DatabaseHandler = DatabaseHandler(context)
        holder.bind(arrayList.get(position), databaseHandler, arrayList)
    }

    override fun getItemCount(): Int {
        if(arrayList.size==0){
            Toast.makeText(context,"List is empty",Toast.LENGTH_LONG).show()
        }else{

        }
        return arrayList.size
    }


    inner class NotesViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind(blog: RecipeModelTwo, adb: DatabaseHandler, data: ArrayList<RecipeModelTwo>){
            binding.titleTxt.text = blog.strDrink
            binding.recipe_photo.setImageBitmap(Utils.getImage(blog.image))
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
                data.removeAt(position)
                notifyDataSetChanged()
                adb.deleteRecipe(blog.idDrink)
//                notifyItemRemoved(arrayList.indexOf(blog))
            }
        }

    }

}