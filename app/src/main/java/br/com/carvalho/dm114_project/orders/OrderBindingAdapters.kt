package br.com.carvalho.dm114_project.orders

import android.widget.TextView
import androidx.databinding.BindingAdapter

class OrderBindingAdapters {
    @BindingAdapter("orderId")
    fun bindOrderID(txtOrderId: TextView, OrderId: Long?) {
        OrderId?.let {
            val price = ""+"%.0f".format(OrderId)
            txtOrderId.text = price
        }
    }
}