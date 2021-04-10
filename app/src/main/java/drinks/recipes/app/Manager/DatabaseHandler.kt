package drinks.recipes.app.Manager

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.util.Log
import drinks.recipes.app.Models.RecipeModel
import drinks.recipes.app.Models.RecipeModelTwo

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 3
        private val DATABASE_NAME = "RecipesDatabase"
        private val TABLE_CONTACTS = "tbl_recipes"
        private val idDrink = "idDrink"
        private val strDrink = "strDrink"
        private val strDrinkAlternate = "strDrinkAlternate"
        private val strTags = "strTags"
        private val strVideo = "strVideo"
        private val strCategory = "strCategory"
        private val strIBA = "strIBA"
        private val strAlcoholic = "strAlcoholic"
        private val strGlass = "strGlass"
        private val strInstructions = "strInstructions"
        private val strImageSource = "strImageSource"
        private val strDrinkThumb = "strDrinkThumb"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE tbl_recipes (idDrink INTEGER PRIMARY KEY, strDrink TEXT, strDrinkAlternate TEXT, strTags TEXT, strVideo TEXT, strCategory TEXT, strIBA TEXT, strAlcoholic TEXT, strGlass TEXT, strInstructions TEXT, strImageSource TEXT, strDrinkThumb TEXT, image BLOB)")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }

    fun insertData(idDrink: Int, strDrink: String, strDrinkAlternate: String, strTags: String,
                   strVideo: String, strCategory: String, strIBA: String,
                   strAlcoholic: String, strGlass: String, strInstructions: String, strImageSource: String,
                   strDrinkThumb: String, image: ByteArray):Long {

        Log.d("datais", idDrink.toString()+" "+strDrink)

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("idDrink", idDrink)
        contentValues.put("strDrink", strDrink)
        contentValues.put("strDrinkAlternate", strDrinkAlternate)
        contentValues.put("strTags", strTags)
        contentValues.put("strVideo", strVideo)
        contentValues.put("strCategory", strCategory)
        contentValues.put("strIBA", strIBA)
        contentValues.put("strAlcoholic", strAlcoholic)
        contentValues.put("strGlass", strGlass)
        contentValues.put("strInstructions", strInstructions)
        contentValues.put("strImageSource", strImageSource)
        contentValues.put("strDrinkThumb", strDrinkThumb)
        contentValues.put("image", image)
        val success = db.insert("tbl_recipes", null, contentValues)
        db.close()
        return success
    }

    fun getRecipes(): ArrayList<RecipeModelTwo> {
        val recipesList:ArrayList<RecipeModelTwo> = ArrayList<RecipeModelTwo>()
        val selectQuery = "SELECT * FROM tbl_recipes"
        val db = this.readableDatabase
        val recipes = RecipeModel()

        val cursor = db.rawQuery(selectQuery, null)

        var a: Int
        var b: String
        var c: String
        var d: String
        var e: String
        var f: String
        var g: String
        var h: String
        var i: String
        var j: String
        var k: String
        var l: String
        var m: ByteArray

        if (cursor.moveToFirst()) {
            do {
                a = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idDrink")))
                b = cursor.getString(cursor.getColumnIndex("strDrink"))
                c = cursor.getString(cursor.getColumnIndex("strDrinkAlternate"))
                d = cursor.getString(cursor.getColumnIndex("strTags"))
                e = cursor.getString(cursor.getColumnIndex("strVideo"))
                f = cursor.getString(cursor.getColumnIndex("strCategory"))
                g = cursor.getString(cursor.getColumnIndex("strIBA"))
                h = cursor.getString(cursor.getColumnIndex("strAlcoholic"))
                i = cursor.getString(cursor.getColumnIndex("strGlass"))
                j = cursor.getString(cursor.getColumnIndex("strInstructions"))
                k = cursor.getString(cursor.getColumnIndex("strImageSource"))
                l = cursor.getString(cursor.getColumnIndex("strDrinkThumb"))
                m = cursor.getBlob(cursor.getColumnIndex("image"))
                val rec= RecipeModelTwo(
                    a,
                    b,
                    c,
                    d,
                    e,
                    f,
                    g,
                    h,
                    i,
                    j,
                    k,
                    l,
                    m
                )
                recipesList.add(rec)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return recipesList
    }

    fun deleteRecipe(idDrink: Int):Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_CONTACTS,"idDrink="+idDrink,null)
        db.close()
        return success
    }

    /*//method to read data
    fun viewEmployee():List<EmpModelClass>{
        val empList:ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var userName: String
        var userEmail: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val emp= EmpModelClass(userId = userId, userName = userName, userEmail = userEmail)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    //method to update data
    fun updateEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(KEY_EMAIL,emp.userEmail ) // EmpModelClass Email

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to delete data
    fun deleteEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }*/

}