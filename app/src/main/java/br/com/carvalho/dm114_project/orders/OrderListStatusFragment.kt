package br.com.carvalho.dm114_project.orders

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.carvalho.dm114_project.databinding.OrderListStatusBinding
import androidx.recyclerview.widget.RecyclerView.VERTICAL

private const val TAG = "OrderStatusFragment"
private const val ORDER_DETAIL = "orderDetail"

class OrderListStatusFragment : Fragment()  {

    private val orderListViewModel: OrderListViewModel by lazy {
        ViewModelProviders.of(this).get(OrderListViewModel::class.java)
    }

    private lateinit var binding: OrderListStatusBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderListStatusBinding.inflate(inflater)

        binding.setLifecycleOwner(this)

        binding.orderListViewModel = orderListViewModel

        val itemDecor = DividerItemDecoration(getContext(), VERTICAL);
        binding.rcvOrders.addItemDecoration(itemDecor);

        binding.rcvOrders.adapter = OrderAdapter(OrderAdapter.OrderClickListener{
            Log.i(TAG,"Product selected: ${it.id}")
            this.findNavController()
                .navigate(OrderListStatusFragmentDirections
                    .actionShowProducOrderDetail(it.id!!, it.productCode!!))
        })

        return binding.root
    }

}