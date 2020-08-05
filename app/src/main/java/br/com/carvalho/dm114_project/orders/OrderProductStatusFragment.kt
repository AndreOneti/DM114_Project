package br.com.carvalho.dm114_project.orders

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.com.carvalho.dm114_project.R
import br.com.carvalho.dm114_project.databinding.OrderProductDetailBinding
import br.com.carvalho.dm114_project.product.ProductInfoViewModel
import br.com.carvalho.dm114_project.product.ProductInfoViewModelFactory

private const val TAG = "OrderProductStatusFrag"

class OrderProductStatusFragment: Fragment() {

    private lateinit var binding: OrderProductDetailBinding

    private var orderId: String? = null
    private var productCode: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderProductDetailBinding.inflate(inflater)

        orderId = OrderProductStatusFragmentArgs.fromBundle(requireArguments()).orderId
        productCode = OrderProductStatusFragmentArgs.fromBundle(requireArguments()).productCode

        val orderProductViewModelFactory = OrderProductViewModelFactory(orderId!!)
        binding.orderInfoViewModel = ViewModelProviders.of(
            this, orderProductViewModelFactory).get(OrderProductViewModel::class.java)
        
        val productViewModelFactory = ProductInfoViewModelFactory(productCode!!)
        binding.productInfoViewModel= ViewModelProviders.of(
            this, productViewModelFactory).get(ProductInfoViewModel::class.java)

        binding.setLifecycleOwner(this)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.order_delete_menu, menu)
    }
}