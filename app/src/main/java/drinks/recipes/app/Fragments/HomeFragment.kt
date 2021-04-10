package drinks.recipes.app.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import drinks.recipes.app.*
import drinks.recipes.app.Adapters.RecipesAdapter
import drinks.recipes.app.Manager.DatabaseHandler
import drinks.recipes.app.Models.RecipeModelTwo
import drinks.recipes.app.ViewModel.MainViewModel
import drinks.recipes.app.ViewModel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.recipesRV
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.home_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.home_fragment, container, false)

        val databaseHandler: DatabaseHandler = DatabaseHandler(requireContext())

        val recipes: List<RecipeModelTwo> = databaseHandler.getRecipes()
        Log.d("sizeis", recipes.size.toString())

        for(e in recipes){
            Log.d("name", e.idDrink.toString())
        }

        linearLayoutManager = LinearLayoutManager(requireContext())
        view.recipesRV.layoutManager = linearLayoutManager

        val factory = MainViewModelFactory()
        viewModel = ViewModelProviders.of(this,factory).get(MainViewModel::class.java)
        viewModel.lst.observe(requireActivity(), Observer{
            Log.i("data",it.toString())
            view.recipesRV.adapter= RecipesAdapter(viewModel, it, requireContext())
        })

        return view
    }

    override fun onResume() {
        super.onResume()

        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = requireActivity().findViewById(checkedId)
                if (radio == radioName){
                    val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                    val query = sharedPreferences.getString("name","margarita")
                    loadRecipesByName(query.toString())
                }else{
                    val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                    val query = sharedPreferences.getString("alphabet","a")
                    loadRecipesByAlphabet(query.toString())
                }
            })

        btnSearch.setOnClickListener(View.OnClickListener {
            val txt: String = inputTxt.text.toString()
            if(txt.trim().length==0) {
                inputTxt.error = "Required"
                inputTxt.requestFocus()
                Toast.makeText(requireActivity(), "Enter Text", Toast.LENGTH_SHORT).show()
            }else if (txt.trim().length>0 && radioName.isChecked == true){
                loadRecipesByName(inputTxt.text.toString())
            }else if (txt.trim().length>0 && radioAlphabet.isChecked == true){
                loadRecipesByAlphabet(inputTxt.text.toString())
            }
        })

        /*radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            var selectedId = radioGroup.checkedRadioButtonId
            val radio: RadioButton = group.findViewById(selectedId)
            if (selectedId == R.id.radioName){
                val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                val query = sharedPreferences.getString("name","margarita")
                loadRecipesByName(query.toString())
            }else{
                val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                val query = sharedPreferences.getString("alphabet","a")
                loadRecipesByAlphabet(query.toString())
            }

        })

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->

        })*/

    }

    private fun loadRecipesByName(query: String) {
        storeName(query)
        fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
        inputTxt.text = query.toEditable()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(RecipesService::class.java)
        val call = service.getRecipesList(query)
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Toast.makeText(requireActivity(),
                    t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                if (response.code() == 200) {
                    val recipesResponse = response.body()!!

                    try {
                        for (movie in recipesResponse.getAllFoods) {
                            Log.v("MainActivity", movie.strCategory)
                        }
//                        adapter = RecipesAdapter(recipesResponse.getAllFoods, requireActivity())
//                        recipesRV.adapter = adapter

                        viewModel.add(recipesResponse.getAllFoods)
                        recipesRV.adapter?.notifyDataSetChanged()

                    }catch (e: Exception){
                        e.printStackTrace()
                        Toast.makeText(requireActivity(), "No record found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun loadRecipesByAlphabet(query: String) {
        storealphabet(query)
        fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
        inputTxt.text = query.toEditable()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(RecipesService::class.java)
        val call = service.getAlphabeticRecipesList(query)
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Toast.makeText(requireActivity(),
                    t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                if (response.code() == 200) {
                    val recipesResponse = response.body()!!

                    try {
                        for (movie in recipesResponse.getAllFoods) {
                            Log.v("MainActivity", movie.strCategory)
                        }
//                        adapter = RecipesAdapter(recipesResponse.getAllFoods, requireActivity())
//                        recipesRV.adapter = adapter

                        viewModel.add(recipesResponse.getAllFoods)
                        recipesRV.adapter?.notifyDataSetChanged()
                    }catch (e: Exception){
                        e.printStackTrace()
                        Toast.makeText(requireActivity(), "No record found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun storeName(name: String){
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("name",name)
        editor.apply()
        editor.commit()
    }

    private fun storealphabet(alphabet: String){
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("alphabet",alphabet)
        editor.apply()
        editor.commit()
    }
}