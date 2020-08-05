package br.com.carvalho.dm114_project.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import br.com.carvalho.dm114_project.network.Product
import br.com.carvalho.dm114_project.network.SalesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "ProductInfoViewModel"

class ProductInfoViewModel(private val code: String): ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _product = MutableLiveData<Product>()

    val product: LiveData<Product>
        get() = _product

    init {
        getProduct(code)
    }

    private fun getProduct(code: String) {
        Log.i(TAG, "Preparing to request a product by its code")
        coroutineScope.launch {
            var getProductDeferred = SalesApi.retrofitService.getProductByCode(code)
            try {
                Log.i(TAG, "Loading product by its code")

                var productByCode = getProductDeferred.await()

                Log.i(TAG, "Name of the product ${productByCode.name}")

                _product.value = productByCode
            } catch (e: Exception) {
                Log.i(TAG, "Error: ${e.message}")
            }
        }
        Log.i(TAG, "Product requested by code")
    }

    override fun onCleared() {
        Log.i(TAG, "onCleared")
        super.onCleared()
        viewModelJob.cancel()
    }
}