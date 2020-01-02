package be.scoutswondelgem.wafelbak.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.models.Order
import kotlinx.android.synthetic.main.row_order.view.*



class OrderAdapter(private val orderList: List<Order>): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){
    //for the transaction of fragments
    var selectedOrder: Int? = null
    private var listener: (() -> Unit)? = null

    fun setListener(listener: (() -> Unit)?) {
        this.listener = listener
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    inner class OrderViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        var orderId : TextView = view.orderId
        var orderIdValue : TextView = view.orderIdValue
        var amountOfWafflesLabel : TextView = view.amountOfWafflesLabel
        var amountOfWafflesValue : TextView = view.amountOfWafflesValue
        var desiredDeliveryTimeLabel : TextView = view.desiredDeliveryTimeLabel
        var desiredDeliveryTimeValue : TextView = view.desiredDeliveryTimeValue
        var editOrderButton: ImageButton = view.button_editOrder

        init {
            editOrderButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            selectedOrder = orderIdValue.text.toString().toInt()
            listener?.invoke()
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
        viewHolder.orderId.setText(R.string.orderId)
        viewHolder.orderId.setTypeface(null, Typeface.BOLD)
        viewHolder.orderIdValue.text = orderList[position].orderId.toString()
        viewHolder.amountOfWafflesLabel.setText(R.string.amountOfWaffles)
        viewHolder.amountOfWafflesValue.text = orderList[position].amountOfWaffles.toString()
        viewHolder.desiredDeliveryTimeLabel.setText(R.string.desiredDeliveryTime)
        viewHolder.desiredDeliveryTimeValue.text = orderList[position].desiredDeliveryTime.levertijd
        /*viewHolder.editOrderButton.setOnClickListener {
            val value = viewHolder.orderIdValue.text.toString()
            val convertedValue = value.toInt()
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.main_content_container, EditOrderFragment.newInstance(convertedValue))
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ?.addToBackStack(null)
                ?.commit()
        }*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = orderList.size

}
