package drinks.recipes.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import drinks.recipes.app.Manager.DatabaseHandler
import drinks.recipes.app.Models.RecipeModelTwo

class NotificationMaker {

    companion object {

        fun sendNotification(title: String, body: String, context: Context) {

            val databaseHandler: DatabaseHandler = DatabaseHandler(context)
            val recipes: List<RecipeModelTwo> = databaseHandler.getRecipes()
            Log.d("sizeis", recipes.size.toString())
            var num: Int = (0..(recipes.size-1)).random()
            Log.d("randomis", recipes.get(num).strDrink +" and "+num.toString())

            val notificationLayout = RemoteViews(context.packageName,R.layout.custom_view)
            notificationLayout.setTextViewText(R.id.titletxtview, recipes.get(num).strDrink)
            notificationLayout.setImageViewBitmap(R.id.imagev, Utils.getImage(recipes.get(num).image))
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

//
//        val pendingIntent =
//            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val NOTIFICATION_CHANNEL_ID = "my_channel_id_01"
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager




            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "My Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
                )

                // Configure the notification channel.
                notificationChannel.description = "Channel description"
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.RED
                notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
            }

            val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            val mNotification = builder
                .setContentTitle(title)
                .setContentText(body)
                .setCustomBigContentView(notificationLayout)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                //     .setPriority(Notification.PRIORITY_MAX)

                .setContentIntent(pendingIntent)

                .setAutoCancel(true)
                //                .setDefaults(Notification.DEFAULT_ALL)
                //                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.samosa)
                .build()

            notificationManager.notify(/*notification id*/0, mNotification)


        }

    }
}