package br.com.carvalho.dm114_project.network

data class Order (
    var id: Long = 0,
    var username: String,
    var orderId: Int,
    var status: String,
    var productCode: String
)
