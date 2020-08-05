package br.com.carvalho.dm114_project.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.carvalho.dm114_project.persistence.Order
import br.com.carvalho.dm114_project.persistence.OrderRepository

private const val TAG = "OrderProductViewlModel"

class OrderProductViewModel(private val orderId: String): ViewModel(){
    lateinit var order: MutableLiveData<Order>

    init {
        if(orderId != null) {
            getOrder(orderId)
        }else{
            order = MutableLiveData<Order>()
            order.value = Order()
        }
    }

    private fun getOrder(orderId:String) {
        order = OrderRepository.getOrderById(orderId)
    }
}