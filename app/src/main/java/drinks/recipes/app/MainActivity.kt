package drinks.recipes.app

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import drinks.recipes.app.Fragments.FavFragment
import drinks.recipes.app.Fragments.HomeFragment
import drinks.recipes.app.Manager.DatabaseHandler
import drinks.recipes.app.Models.RecipeModelTwo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    private var notificationManager: NotificationManager? = null

    private val CHANNEL_ID = "recipe_channel_01"
    private val notificationId = 1
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CreateNotificationChannel()

        notificationManager =
            getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannelNew(
            "com.ebookfrenzy.notifydemo.news",
            "NotifyDemo News",
            "Example News Channel")

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        val recipes: List<RecipeModelTwo> = databaseHandler.getRecipes()
        Log.d("sizeis", recipes.size.toString())

        for(e in recipes){
            Log.d("name", e.idDrink.toString())
        }

        linearLayoutManager = LinearLayoutManager(this)
        recipesRV.layoutManager = linearLayoutManager

        btnHome.setOnClickListener { view: View? ->
            val textFragment = HomeFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()

            btnHome.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            homeTxt.setTextColor(resources.getColor(R.color.colorWhite))
            homeIcon.setColorFilter(ContextCompat.getColor(applicationContext, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

            btnFav.setBackgroundColor(resources.getColor(R.color.colorWhite))
            favTxt.setTextColor(resources.getColor(R.color.colorPrimary))
            favicon.setColorFilter(ContextCompat.getColor(applicationContext, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        btnFav.setOnClickListener { view: View? ->

            // intent = Intent(this, MyService::class.java)
            // startService(intent)

            /*NotificationMaker.sendNotification("title", "body", applicationContext)*/

            val textFragment = FavFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()

            btnFav.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            favTxt.setTextColor(resources.getColor(R.color.colorWhite))
            favicon.setColorFilter(ContextCompat.getColor(applicationContext, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

            btnHome.setBackgroundColor(resources.getColor(R.color.colorWhite))
            homeTxt.setTextColor(resources.getColor(R.color.colorPrimary))
            homeIcon.setColorFilter(ContextCompat.getColor(applicationContext, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        val textFragment = HomeFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container,textFragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    fun finishMe() { finish() }

    private fun createNotificationChannelNew(id: String, name: String,
                                          description: String) {

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern =
            longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager?.createNotificationChannel(channel)
    }

    private fun CreateNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notif Desc"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID,name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}