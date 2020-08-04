package br.com.carvalho.dm114_project.orders

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.carvalho.dm114_project.persistence.Order

@BindingAdapter("orderId")
fun bindOrderID(txtOrderId: TextView, OrderId: Long?) {
    OrderId?.let {
        val orderID = OrderId.toString()
        txtOrderId.text = orderID
    }
}

@BindingAdapter("ordersList")
fun bindOrdersList(recyclerView: RecyclerView, orders: List<Order>?) {
    val adapter = recyclerView.adapter as OrderAdapter
    adapter.submitList(orders)
}