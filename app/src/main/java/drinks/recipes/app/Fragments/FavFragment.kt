package drinks.recipes.app.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import drinks.recipes.app.*
import drinks.recipes.app.Adapters.FavoriteAdapter
import drinks.recipes.app.Manager.DatabaseHandler
import drinks.recipes.app.Models.RecipeModelTwo
import drinks.recipes.app.ViewModel.FavViewModel
import drinks.recipes.app.ViewModel.FavViewModelFactory
import kotlinx.android.synthetic.main.fav_fragment.view.*

class FavFragment : Fragment() {

    private lateinit var viewModel: FavViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        fun newInstance(): FavFragment {
            return FavFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fav_fragment, container, false)

        val databaseHandler: DatabaseHandler = DatabaseHandler(requireContext())

        val recipes: ArrayList<RecipeModelTwo> = databaseHandler.getRecipes()
        Log.d("sizeis", recipes.size.toString())

        for(e in recipes){
            Log.d("name", e.idDrink.toString())
        }

        linearLayoutManager = LinearLayoutManager(requireContext())
        view.favRecipesRV.layoutManager = linearLayoutManager

        val factory = FavViewModelFactory()
        viewModel = ViewModelProviders.of(this,factory).get(FavViewModel::class.java)
        viewModel.lst.observe(requireActivity(), Observer{
            Log.i("data",it.toString())
            view.favRecipesRV.adapter= FavoriteAdapter(viewModel, it, requireContext())
        })

        viewModel.add(recipes)
        view.favRecipesRV.adapter?.notifyDataSetChanged()

//        adapter = FavoritesAdapter(recipes, requireContext())
//        view.favRecipesRV.adapter = adapter

        return view
    }
}