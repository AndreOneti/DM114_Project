package br.com.carvalho.dm114_project.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductInfoViewModelFactory (private val code: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductInfoViewModel::class.java)) {
            return ProductInfoViewModel(code) as T
        }
        throw IllegalArgumentException("The ProductInfoViewModel class is required")
    }
}