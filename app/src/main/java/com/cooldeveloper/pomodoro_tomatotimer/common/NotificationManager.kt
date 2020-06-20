package com.cooldeveloper.pomodoro_tomatotimer.common

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import com.cooldeveloper.pomodoro_tomatotimer.R
import com.cooldeveloper.pomodoro_tomatotimer.timer.TimerActivity

class NotificationManager {

    private lateinit var customNotification: Notification

    fun showNotification(text: String, title: String, color: Int, context: Context) {
        createNotificationChannel(context)

        // Apply the layouts to the notification
        customNotification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_shutter_speed_24px)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentTitle(title)
            .setContentText(text)
            .addAction(R.drawable.ic_clear_red_24dp, "Cancel", getStopPendingIntent(context))
            .setOnlyAlertOnce(true)
            .setColor(context.resources.getColor(color, context.theme))
            .setContentIntent(newLauncherIntent(context))
            .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.drawable.ic_shutter_speed_24px))
            .build()

        NotificationManagerCompat.from(context).notify(
            NOTIFICATION_ID,
            customNotification
        )
    }

    private fun getStopPendingIntent(context: Context): PendingIntent {
        val cancelIntent = Intent(context, StopNotificationBroadcastReceiver::class.java)

        return PendingIntent.getBroadcast(
            context,
            0,
            cancelIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun newLauncherIntent(context: Context?): PendingIntent {
        val intent = Intent(context, TimerActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = CHANNEL_ID
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_DISMISS_TEXT, importance)
            channel.description = descriptionText
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}