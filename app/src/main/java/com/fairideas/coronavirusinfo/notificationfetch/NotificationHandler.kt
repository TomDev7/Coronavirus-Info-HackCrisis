package com.fairideas.coronavirusinfo.notificationfetch

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.fairideas.coronavirusinfo.R
import java.util.*

class NotificationHandler(val appContext: Context) {

    companion object {

        val CHANNEL_ID = "com.fairideas.coronavirusinfo"
        val CHANNEL_NAME = "CoronavirusInfoHeadline"
        val CHANNEL_DESCRIPTION = "Coronavirus Info default notification channel about news headlines"
    }

    fun registerNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(text: String, targetUrl: String) {

        val notificationManager: NotificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        val notificationTitle = appContext.getString(R.string.app_name)

        val notificationBuilder =
            NotificationCompat.Builder(appContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_icon_green)
                .setContentTitle(notificationTitle)
                .setContentText(text)

        //notificationBuilder.setOnlyAlertOnce(true)
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND)
        notificationBuilder.setAutoCancel(true)

        val intentBrowser = Intent(Intent.ACTION_VIEW)
        intentBrowser.setData(Uri.parse(targetUrl))
        val pendingIntent = PendingIntent.getActivity(appContext, 0, intentBrowser, 0)
        notificationBuilder.setContentIntent(pendingIntent)
        val random = Random()
        val randomNumber = random.nextInt(80) + 65
        val notification = notificationBuilder.build()
        notificationManager.notify(randomNumber, notification)
    }
}