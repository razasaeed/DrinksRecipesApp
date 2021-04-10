package drinks.recipes.app

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class MyService : Service() {

    private val TAG = "ServiceExample"


    override fun onCreate() {
        Log.i(TAG, "Service onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i(TAG, "Service onStartCommand " + startId)

        var i: Int = 0
        var flag: Int = 0



        val timer = Timer()
        val monitor = object : TimerTask() {
            override fun run() {
                val hh = SimpleDateFormat("hh")
                val currentHour = hh.format(Date())
                val mm = SimpleDateFormat("mm")
                val currentMin = mm.format(Date())
                val ss = SimpleDateFormat("ss")
                val currentSec = ss.format(Date())

                if (currentHour.equals("14") && currentMin.equals("50") && currentSec.equals("10") && flag == 0) {
                    NotificationMaker.sendNotification("title", "body", applicationContext)
                    flag = 1
                }
                else if (mm.equals("14") && currentSec.equals("13")){
                    flag = 0
                }
            }
        }
        timer.schedule(monitor, 60000, 60000)

        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "Service onBind")
        return null
    }

    override fun onDestroy() {
        Log.i(TAG, "Service onDestroy")
    }
}