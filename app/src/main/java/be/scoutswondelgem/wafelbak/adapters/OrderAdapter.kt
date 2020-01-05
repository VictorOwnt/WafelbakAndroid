package be.scoutswondelgem.wafelbak.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.models.Order
import kotlinx.android.synthetic.main.row_order.view.*



class OrderAdapter(private val orderList: List<Order>): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){
    //for the transaction of fragments
    var onItemClick: ((Order) -> Unit)? = null
    var onItemClick2: ((Order) -> Unit)? = null

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    inner class OrderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var orderId : TextView = view.orderId
        var orderIdValue : TextView = view.orderIdValue
        var amountOfWafflesLabel : TextView = view.amountOfWafflesLabel
        var amountOfWafflesValue : TextView = view.amountOfWafflesValue
        var desiredDeliveryTimeLabel : TextView = view.desiredDeliveryTimeLabel
        var desiredDeliveryTimeValue : TextView = view.desiredDeliveryTimeValue
        var statusField: TextView = view.orderStatusField
        var statusValue: TextView = view.orderStatusFieldValue
        var editOrderButton: ImageButton = view.button_editOrder
        var removeOrderButton: ImageButton = view.button_removeOrder

        init {
            editOrderButton.setOnClickListener{
                onItemClick?.invoke(orderList[adapterPosition])
            }
            removeOrderButton.setOnClickListener{
                onItemClick2?.invoke(orderList[adapterPosition])
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): OrderViewHolder {
        // Create a new view.
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_order, viewGroup, false)
        return OrderViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: OrderViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        val order: Order = orderList[position]
        viewHolder.orderId.setText(R.string.orderId)
        viewHolder.orderId.setTypeface(null, Typeface.BOLD)
        viewHolder.orderIdValue.text = order.orderId.toString()
        viewHolder.amountOfWafflesLabel.setText(R.string.amountOfWaffles)
        viewHolder.amountOfWafflesValue.text = order.amountOfWaffles.toString()
        viewHolder.desiredDeliveryTimeLabel.setText(R.string.desiredDeliveryTime)
        viewHolder.desiredDeliveryTimeValue.text = order.desiredDeliveryTime.levertijd
        viewHolder.statusField.setText(R.string.status)
        viewHolder.statusValue.text = order.deliveryStatus.status
        
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = orderList.size

}
