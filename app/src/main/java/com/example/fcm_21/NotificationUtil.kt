package com.example.fcm_21

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object NotificationUtil {
    @SuppressLint("UnspecifiedImmutableFlag")
    fun addNotification(remoteMessage: RemoteMessage,context: Context) {
        val notifyIntent = Intent(remoteMessage.notification!!.clickAction).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        notifyIntent.putExtra("title", remoteMessage.data["title"])
        notifyIntent.putExtra("body", remoteMessage.data["body"])
        notifyIntent.putExtra("image", remoteMessage.data["image"])

        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, context.getString(R.string.app_name)).apply {
            priority = NotificationCompat.PRIORITY_HIGH
            setCategory(NotificationCompat.CATEGORY_MESSAGE)
            setSmallIcon(R.mipmap.ic_launcher)
            setAutoCancel(true)
            setContentTitle(remoteMessage.notification?.title)
            setContentText(remoteMessage.notification?.body)
            setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            setSound(defaultSoundUri, AudioManager.STREAM_NOTIFICATION)
            setDefaults(NotificationCompat.DEFAULT_LIGHTS)
            setContentIntent(notifyPendingIntent)
        }

        if (remoteMessage.notification?.imageUrl != null) {
            val bitmap = getBitmapFromUrl(remoteMessage.notification?.imageUrl.toString())
            if (bitmap != null) {
                builder.setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null)
                ).setLargeIcon(bitmap)
            }
        }
        
        with(NotificationManagerCompat.from(context)) {
            notify(Random().nextInt(), builder.build())
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotification(remoteMessage: RemoteMessage,context: Context){
        val notifyIntent = Intent(remoteMessage.notification!!.clickAction).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        notifyIntent.putExtra("title", remoteMessage.data["title"])
        notifyIntent.putExtra("body", remoteMessage.data["body"])
        notifyIntent.putExtra("image", remoteMessage.data["image"])

        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channel_id=context.getString(R.string.app_name)
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channel_id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(uri)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            . setContentIntent(notifyPendingIntent)

        val notificationManager = context.getSystemService(FirebaseMessagingService.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channel_id,
                remoteMessage.notification?.title,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.setSound(uri, null)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val bitmap = getBitmapFromUrl(remoteMessage.notification?.imageUrl.toString())
        if (bitmap != null) {
            builder.setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null)
            ).setLargeIcon(bitmap)
        }

        notificationManager.notify(Random().nextInt(), builder.build())
    }

    private fun getBitmapFromUrl(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            null
        }
    }
}