package br.com.carvalho.dm114_project.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import br.com.carvalho.dm114_project.persistence.Order

class OrderInfoViewModel : ViewModel()  {
    val order = MutableLiveData<Order>()
}