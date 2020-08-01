package br.com.carvalho.dm114_project.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.carvalho.dm114_project.databinding.OrderStatusBinding
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import br.com.carvalho.dm114_project.persistence.Order
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.json.JSONObject
import java.util.*

private const val TAG = "OrderStatusFragment"
private const val ORDER_DETAIL = "orderDetail"

class OrderStatusFragment : Fragment() {

    private val orderInfoViewModel: OrderInfoViewModel by lazy {
        ViewModelProviders.of(this).get(OrderInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = OrderStatusBinding.inflate(inflater)

        binding.setLifecycleOwner(this)

        binding.orderInfoViewModel = orderInfoViewModel

        if (this.arguments != null) {
            if (this.arguments!!.containsKey("orderInfo")) {
                val moshi = Moshi.Builder()
                    .build()
                val jsonAdapter: JsonAdapter<Order> =
                    moshi.adapter<Order>(Order::class.java)

                jsonAdapter.fromJson(this.arguments!!.getString("orderInfo")!!).let {
                    orderInfoViewModel.order.value = it
                }
            }
        }

        return binding.root
    }
}