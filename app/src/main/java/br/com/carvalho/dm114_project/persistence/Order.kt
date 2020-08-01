package br.com.carvalho.dm114_project.persistence

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Order (
    @Exclude var id: String? = null,
    var username: String? = null,
    var orderId: Long? = null,
    var status: String? = null,
    var productCode: String? = null,
    var userId: String? = null,
    var data: String? = null
)