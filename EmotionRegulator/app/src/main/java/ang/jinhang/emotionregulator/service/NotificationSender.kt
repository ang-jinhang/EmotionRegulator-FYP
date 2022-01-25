package ang.jinhang.emotionregulator.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock

class NotificationSender {
    fun scheduleNotification(context: Context, intent: Intent) {
        val notificationChannelCreator = NotificationChannelCreator()
        notificationChannelCreator.createNotificationChannel()

        val informationIntent = Intent(context, NotificationBroadcastReceiver::class.java)
        val notificationIntent = PendingIntent.getBroadcast(
            context,
            1,
            informationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 600000,
                    notificationIntent
                )
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                alarmManager.set(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 600000,
                    notificationIntent
                )
            }
            else -> {
                alarmManager.set(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 600000,
                    notificationIntent
                )
            }
        }
    }
}