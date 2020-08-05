package br.com.carvalho.dm114_project.persistence

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlin.collections.ArrayList
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter
import java.util.*

private const val TAG = "OrderRepository"

private const val COLLECTION = "orders"

private const val FIELD_USER_NAME = "username"
private const val FIELD_STATUS = "status"
private const val FIELD_PRODUCT_CODE = "productCode"
private const val FIELD_ORDER_ID = "orderId"
private const val FIELD_USER_ID = "userId"
private const val FIELD_DATE = "data"
private const val FIELD_ID = "id"

private const val FORMAT_PATTER = "yyyy-MM-dd HH:mm"
private const val FORMAT_PATTER_FILTER = "yyyy-MM-dd HH:mm:ss"

object OrderRepository {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveOrder(order: Order): String {
        val document = if (order.id != null) {
            firebaseFirestore.collection(COLLECTION).document(order.id!!)
        } else {
            order.userId = firebaseAuth.currentUser!!.uid
            firebaseFirestore.collection(COLLECTION).document()
        }

        val currentDate = now()
        val stringDate = currentDate.format(DateTimeFormatter.ofPattern(FORMAT_PATTER_FILTER))

        order.data = stringDate

        order.id = document.id

        document.set(order)

        return document.id
    }

    fun deleteOrder(documetId: String) {
        val document = firebaseFirestore.collection(COLLECTION).document(documetId)
        document.delete()
    }

    fun getOrders(): MutableLiveData<List<Order>> {
        val liveOrders = MutableLiveData<List<Order>>()

        firebaseFirestore.collection(COLLECTION)
            .whereEqualTo(FIELD_USER_ID, firebaseAuth.currentUser!!.uid)
            .orderBy(FIELD_DATE, Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.w(TAG, "Listen failed.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    val orders = ArrayList<Order>()
                    querySnapshot.forEach {
                        val order = it.toObject<Order>()
                        order.id = it.id
                        orders.add(order)
                    }
                    liveOrders.postValue(orders)
                } else {
                    Log.d(TAG, "No order has been found")
                }
            }

        return liveOrders
    }

    fun getOrderByCode(date: Date, orderId: String): MutableLiveData<Order> {
        val liveOrder: MutableLiveData<Order> = MutableLiveData()

        firebaseFirestore.collection(COLLECTION)
            .whereEqualTo(FIELD_DATE, date)
            .whereEqualTo(FIELD_ORDER_ID, orderId)
            .whereEqualTo(FIELD_USER_ID, firebaseAuth.currentUser!!.uid)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.w(TAG, "Listen failed.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    val products = ArrayList<Order>()
                    querySnapshot.forEach {
                        val order = it.toObject<Order>()
                        order.id = it.id
                        products.add(order)
                    }
                    liveOrder.postValue(products[0])
                } else {
                    Log.d(TAG, "No order has been found")
                }
            }

        return liveOrder
    }

    fun getOrderById(documentId: String): MutableLiveData<Order> {
        val liveOrder: MutableLiveData<Order> = MutableLiveData()

        firebaseFirestore.collection(COLLECTION)
            .document(documentId)

        firebaseFirestore.collection(COLLECTION)
            .whereEqualTo(FIELD_ID, documentId)
            .whereEqualTo(FIELD_USER_ID, firebaseAuth.currentUser!!.uid)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.w(TAG, "Listen failed.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    val orders = ArrayList<Order>()
                    querySnapshot.forEach {
                        val order = it.toObject<Order>()
                        order.id = it.id
                        orders.add(order)
                    }
                    liveOrder.postValue(orders[0])
                } else {
                    Log.d(TAG, "No order has been found")
                }
            }

        return liveOrder
    }
}