package be.scoutswondelgem.wafelbak.adapters

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.models.OrderAndUser
import kotlinx.android.synthetic.main.row_admin_order.view.*
import android.widget.Filterable




class OrderAdminAdapter(private val orderList: List<OrderAndUser>): RecyclerView.Adapter<OrderAdminAdapter.OrderViewHolder>() ,
    Filterable {
    //For searching
    var orderListFiltered: List<OrderAndUser> = orderList
    //For the transaction of fragments
    var onItemClick: ((OrderAndUser) -> Unit)? = null
    var onItemClick2: ((OrderAndUser) -> Unit)? = null

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
        var statusField: TextView = view.orderStatusField
        var statusValue: TextView = view.orderStatusValue
        var extraField: TextView = view.extraField
        var extraFieldValue: TextView = view.extraFieldValue
        var completeOrderButton: ImageButton = view.button_completeOrder


        init {
            completeOrderButton.setOnClickListener{
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
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_admin_order, viewGroup, false)
        return OrderViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: OrderViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        val order: OrderAndUser = orderListFiltered[position]
        if(order.deliveryStatus.status == "Bezorgd") {
            viewHolder.itemView.setBackgroundColor(Color.DKGRAY)
        }
        viewHolder.orderId.setText(R.string.orderId)
        viewHolder.orderId.setTypeface(null, Typeface.BOLD)
        viewHolder.orderIdValue.text = order.orderId.toString()
        viewHolder.amountOfWafflesLabel.setText(R.string.amountOfWaffles)
        viewHolder.amountOfWafflesValue.text = order.amountOfWaffles.toString()
        viewHolder.desiredDeliveryTimeLabel.setText(R.string.desiredDeliveryTime)
        viewHolder.desiredDeliveryTimeValue.text = order.desiredDeliveryTime.levertijd
        viewHolder.nameField.setText(R.string.name)
        viewHolder.nameValue.text = (order.user.firstName + " "+ order.user.lastName)
        viewHolder.addressField.setText(R.string.address)
        viewHolder.addressValue.text = (order.user.street + " " +
                order.user.streetNumber + ", " + order.user.postalCode + " " + order.user.city)
        viewHolder.statusField.setText(R.string.status)
        viewHolder.statusValue.text = order.deliveryStatus.status
        if(order.user.streetExtra !== null && order.comment!== null)
        {
            viewHolder.extraField.setText(R.string.extraField)
            viewHolder.extraFieldValue.text = ("AdresExtra: "  + order.user.streetExtra + ", " + order.comment)
        } else if(order.user.streetExtra !== null && order.comment === null) {
            viewHolder.extraField.setText(R.string.extraField)
            viewHolder.extraFieldValue.text = ("AdresExtra: "  + order.user.streetExtra)
        } else if(orderListFiltered[position].user.streetExtra === null && order.comment !== null) {
            viewHolder.extraField.setText(R.string.extraField)
            viewHolder.extraFieldValue.text = order.comment
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = orderListFiltered.size

    //For searching
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    orderListFiltered = orderList
                } else {
                    val filteredList = ArrayList<OrderAndUser>()
                    for (order in orderList) {
                        // name match condition. this might differ depending on your requirement
                        if (order.user.firstName.toLowerCase().contains(charString.toLowerCase()) ||
                            order.user.lastName.toLowerCase().contains(charString.toLowerCase()) ||
                            (order.user.street.toLowerCase() + " " + order.user.streetNumber).contains(charString.toLowerCase())
                        ) {
                            filteredList.add(order)
                        }
                    }

                    orderListFiltered = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = orderListFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                orderListFiltered = filterResults.values as ArrayList<OrderAndUser>
                notifyDataSetChanged()
            }
        }
    }
}
