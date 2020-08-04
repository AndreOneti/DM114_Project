package br.com.carvalho.dm114_project.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.carvalho.dm114_project.persistence.Order
import br.com.carvalho.dm114_project.persistence.OrderRepository

class OrderListViewModel : ViewModel() {
    private lateinit var _orders: MutableLiveData<List<Order>>
    val orders: MutableLiveData<List<Order>>
        get() = _orders

    init {
        getOrders()
    }

    private fun getOrders() {
        _orders = OrderRepository.getOrders()
    }

    override fun onCleared() {
        super.onCleared()
    }
}