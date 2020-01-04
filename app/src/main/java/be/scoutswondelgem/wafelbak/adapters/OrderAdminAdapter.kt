package be.scoutswondelgem.wafelbak.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.models.OrderAndUser
import kotlinx.android.synthetic.main.row_admin_order.view.*


class OrderAdminAdapter(private val orderList: List<OrderAndUser>): RecyclerView.Adapter<OrderAdminAdapter.OrderViewHolder>(){
    //for the transaction of fragments
    var onItemClick: ((OrderAndUser) -> Unit)? = null

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
        var removeOrderButton: ImageButton = view.button_removeOrder
        var nameField: TextView = view.name
        var nameValue: TextView = view.nameValue
        var addressField: TextView = view.address
        var addressValue: TextView = view.addressValue
        var extraField: TextView = view.extraField
        var extraFieldValue: TextView = view.extraFieldValue

        init {
            removeOrderButton.setOnClickListener{
                onItemClick?.invoke(orderList[adapterPosition])
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): OrderViewHolder {
        // Create a new view.
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_admin_order, viewGroup, false)
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
        viewHolder.nameField.setText(R.string.name)
        viewHolder.nameValue.text = (orderList[position].user.firstName + " "+ orderList[position].user.lastName)
        viewHolder.addressField.setText(R.string.address)
        viewHolder.addressValue.text = (orderList[position].user.street + " " +
                orderList[position].user.streetNumber + ", " + orderList[position].user.postalCode + " " + orderList[position].user.city)
        if(orderList[position].user.streetExtra !== null && orderList[position].comment!== null)
        {
            viewHolder.extraField.setText(R.string.extraField)
            viewHolder.extraFieldValue.text = ("AdresExtra: "  + orderList[position].user.streetExtra + ", " + orderList[position].comment)
        } else if(orderList[position].user.streetExtra !== null && orderList[position].comment === null) {
            viewHolder.extraField.setText(R.string.extraField)
            viewHolder.extraFieldValue.text = ("AdresExtra: "  + orderList[position].user.streetExtra)
        } else if(orderList[position].user.streetExtra === null && orderList[position].comment !== null) {
            viewHolder.extraField.setText(R.string.extraField)
            viewHolder.extraFieldValue.text = orderList[position].comment
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = orderList.size

}
