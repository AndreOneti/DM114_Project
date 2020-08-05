package br.com.carvalho.dm114_project.product

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("productPrice")
fun bindProductPrice(txtProductPrice: TextView, productPrice: Double?) {
    productPrice?.let {
        val price = "$ " + "%.2f".format(productPrice)
        txtProductPrice.text = price
    }
}