package br.com.carvalho.dm114_project.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.carvalho.dm114_project.persistence.Order
import br.com.carvalho.dm114_project.databinding.ItemOrderBinding
import com.google.firebase.analytics.FirebaseAnalytics

class OrderAdapter (val onProductClickListener: OrderClickListener) :
    ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiff) {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderAdapter.OrderViewHolder {
        firebaseAnalytics = FirebaseAnalytics.getInstance(parent.context)
        return OrderViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {

        val order = getItem(position)
        holder.bind(order)

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, order.id)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)

            onProductClickListener.onClick(order)
        }

        holder.itemView.setOnLongClickListener {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, order.id)
            firebaseAnalytics.logEvent("attempt_delete_product", bundle)

            true
        }
    }

    companion object OrderDiff : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return ((oldItem.id == newItem.id)
                    && (oldItem.productCode.equals(newItem.productCode))
                    && (oldItem.username.equals(newItem.username))
                    && (oldItem.status.equals(newItem.status))
                    && (oldItem.userId.equals(newItem.userId))
                    && (oldItem.orderId == newItem.orderId)
                    && (oldItem.data.equals(newItem.data)))
        }
    }

    class OrderViewHolder(private var binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.order = order
            binding.executePendingBindings()
        }
    }

    class OrderClickListener(val clickListener: (order: Order) -> Unit) {
        fun onClick(order: Order) = clickListener(order)
    }
}