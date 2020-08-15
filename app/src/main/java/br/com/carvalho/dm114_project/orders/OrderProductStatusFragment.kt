package br.com.carvalho.dm114_project.orders

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import br.com.carvalho.dm114_project.R
import br.com.carvalho.dm114_project.databinding.OrderProductDetailBinding
import br.com.carvalho.dm114_project.product.ProductInfoViewModel
import br.com.carvalho.dm114_project.product.ProductInfoViewModelFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

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

        val remoteConfig = Firebase.remoteConfig
        setHasOptionsMenu(remoteConfig.getBoolean("delete_detail_view"))

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.order_delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_delete_order -> {
                Log.d(TAG, "Deleting the order")

                val bundle = Bundle()
                val firebaseAnalytics = FirebaseAnalytics.getInstance(this.requireContext())
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID,
                    binding.orderInfoViewModel!!.order.value!!.id)
                firebaseAnalytics.logEvent("delete_product", bundle)

                binding.orderInfoViewModel?.deleteOrder()
                Log.d(TAG, "Pop in navigation")
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}