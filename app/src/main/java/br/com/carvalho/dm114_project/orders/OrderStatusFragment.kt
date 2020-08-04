package br.com.carvalho.dm114_project.orders

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import br.com.carvalho.dm114_project.databinding.OrderStatusBinding
import androidx.lifecycle.ViewModelProviders
import br.com.carvalho.dm114_project.R
import br.com.carvalho.dm114_project.persistence.Order
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import br.com.carvalho.dm114_project.databinding.OrderListStatusBinding

private const val TAG = "OrderStatusFragment"
private const val ORDER_DETAIL = "orderDetail"

class OrderStatusFragment : Fragment() {

    private val orderInfoViewModel: OrderInfoViewModel by lazy {
        ViewModelProviders.of(this).get(OrderInfoViewModel::class.java)
    }

    private lateinit var binding: OrderStatusBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderStatusBinding.inflate(inflater)

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

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
    }
}