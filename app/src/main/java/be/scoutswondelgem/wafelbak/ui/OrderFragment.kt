package be.scoutswondelgem.wafelbak.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.adapters.OrderAdapter
import be.scoutswondelgem.wafelbak.databinding.FragmentOrderBinding
import be.scoutswondelgem.wafelbak.viewmodels.OrderViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_order.view.*
import kotlinx.android.synthetic.main.order_list.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel



class OrderFragment : Fragment() {
    //Voor creatie OrderFragment
    companion object {
        @JvmStatic
        fun newInstance() = OrderFragment()
    }
    //Ui elementen:
    private lateinit var addOrderTextView : TextView
    private lateinit var addOrderButton: FloatingActionButton
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var noOrdersTextView: TextView


    //Injecteren:
    private val orderViewModel by viewModel<OrderViewModel>()
    private val sharedPreferences: SharedPreferences = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentOrderBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.orderViewModel = orderViewModel
        return binding.root
    }

   /* override fun onResume() {
        super.onResume()
        // Set title //TODO nullpointerexception
        activity!!.actionBar!!
            .setTitle(R.string.orders_title)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addOrderTextView = view.addOrder
        addOrderButton = view.button_addOrder
        noOrdersTextView = view.noOrdersYet
        fillListView(sharedPreferences.getString("TOKEN", "")!!, sharedPreferences.getString("ID", "")!!.toInt())
        addOrderButton.setOnClickListener {
            fragmentManager!!.beginTransaction()
                .replace(R.id.main_content_container, CreateOrderFragment.newInstance(), "CreateOrderFragment")
                .setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN))
                .addToBackStack("OrderFragment")
                .commit()
        }
    }

    private fun fillListView(authToken: String, id: Int){
        val orders = orderViewModel.getOrdersForCurrentUser(authToken, id)
        if (orders.isNotEmpty())
        {
            noOrdersTextView.visibility = GONE
            orderRecyclerView = order_list
            linearLayoutManager = LinearLayoutManager(activity)
            orderRecyclerView.layoutManager = linearLayoutManager
            val adapter = OrderAdapter(orders)
            adapter.onItemClick = { order ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.main_content_container, EditOrderFragment.newInstance(order.orderId), "EditOrderFragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack("OrderFragment")
                    .commit()
            }
            adapter.onItemClick2 = { order ->
                val dialogBuilder = AlertDialog.Builder(activity!!)
                    .setCancelable(true)
                    .setNegativeButton("Nee") {
                            dialog, id -> dialog.dismiss()
                    }
                    .setPositiveButton("Ja") {
                            dialog, id -> dialog.dismiss()
                        val deletedOrder = orderViewModel.deleteOrder(authToken, order)
                        fragmentManager!!.beginTransaction()
                            .replace(R.id.main_content_container, newInstance(), "OrderFragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                        Toast.makeText(
                            activity,
                            "Bestelling " + deletedOrder.orderId + " successvol verwijderd!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                val alert = dialogBuilder.create()
                alert.setTitle("Ben je zeker dat je bestelling " + order.orderId + " wilt verwijderen?")
                alert.show()
            }
            orderRecyclerView.adapter = adapter
        }
    }
}