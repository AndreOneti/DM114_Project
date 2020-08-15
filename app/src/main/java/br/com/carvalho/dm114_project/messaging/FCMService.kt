package br.com.carvalho.dm114_project.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*
import androidx.core.app.NotificationCompat
import br.com.carvalho.dm114_project.MainActivity
import br.com.carvalho.dm114_project.R
import br.com.carvalho.dm114_project.orders.OrderInfoViewModel
import br.com.carvalho.dm114_project.orders.OrderStatusFragment
import br.com.carvalho.dm114_project.persistence.Order
import br.com.carvalho.dm114_project.persistence.OrderRepository
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.json.JSONObject

private const val TAG = "FCMService"
private const val ORDER_DETAIL = "orderDetail"

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "FCM token: $token")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Payload: " + remoteMessage.data)
            if (remoteMessage.data.containsKey(ORDER_DETAIL)) {
                var order = JSONObject(remoteMessage.data.get(ORDER_DETAIL)!!)
                var userName = order.optString("username")
                val userEmail = FirebaseAuth.getInstance().currentUser!!.email
                if(userName == userEmail){
                    sendOrderNotification(remoteMessage.data.get(ORDER_DETAIL)!!)

                    val moshi = Moshi.Builder()
                        .build()
                    val jsonAdapter: JsonAdapter<Order> =
                        moshi.adapter<Order>(Order::class.java)

                    jsonAdapter.fromJson(remoteMessage.data.get(ORDER_DETAIL)!!).let {
                        var documentId = OrderRepository.saveOrder(it!!)

                        val bundle = Bundle()
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, documentId)
                        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
                        firebaseAnalytics.logEvent("new_item_add", bundle)

                    }
                }
            }
        }
    }

    private fun sendOrderNotification(orderInfo: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(ORDER_DETAIL, orderInfo)
        sendNotification(intent)
    }

    private fun sendNotification(intent: Intent) {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = "1"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_cloud_queue_black_24dp)
            .setContentTitle("Sales Message")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Sales provider",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}