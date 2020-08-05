package br.com.carvalho.dm114_project.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OrderProductViewModelFactory (private val orderId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderProductViewModel::class.java)) {
            return OrderProductViewModel(orderId) as T
        }
        throw IllegalArgumentException("The OrderProductViewlModel class is required")
    }
}