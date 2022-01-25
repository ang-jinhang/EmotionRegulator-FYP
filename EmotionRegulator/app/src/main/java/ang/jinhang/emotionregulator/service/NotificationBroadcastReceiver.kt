package ang.jinhang.emotionregulator.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import ang.jinhang.emotionregulator.R
import ang.jinhang.emotionregulator.emotionregulation.EmotionUpdater

class NotificationBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val launchIntent = Intent(context, EmotionUpdater::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0)

        val notification = NotificationCompat.Builder(context!!, "1")
            .setContentTitle("Hi there!")
            .setContentText("Should we continue the next cycle of regulation?")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notificationManager.notify(1, notification)
    }
}