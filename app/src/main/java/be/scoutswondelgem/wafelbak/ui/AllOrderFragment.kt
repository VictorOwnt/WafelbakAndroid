package be.scoutswondelgem.wafelbak.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
import be.scoutswondelgem.wafelbak.adapters.OrderAdminAdapter
import be.scoutswondelgem.wafelbak.databinding.FragmentAllOrderBinding
import be.scoutswondelgem.wafelbak.viewmodels.OrderViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_all_order.view.*
import kotlinx.android.synthetic.main.order_list.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class AllOrderFragment : Fragment() {
    //Voor creatie AllOrderFragment
    companion object {
        @JvmStatic
        fun newInstance() = AllOrderFragment()
    }

    //Ui elementen:
    private lateinit var titleTextView : TextView
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var searchTextView : TextView
    private lateinit var searchButton: FloatingActionButton
    private lateinit var noOrdersTextView: TextView


    //Injecteren:
    private val orderViewModel by viewModel<OrderViewModel>()
    private val sharedPreferences: SharedPreferences = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAllOrderBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_order, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.orderViewModel = orderViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView = view.title_orders
        searchTextView = view.search
        searchButton = view.button_search
        noOrdersTextView = view.noOrdersYet
        fillListView(sharedPreferences.getString("TOKEN", "")!!)
        searchButton.setOnClickListener {

        }
    }

    private fun fillListView(authToken: String){
        val ordersAndUsers = orderViewModel.getOrdersJoined(authToken)
        if(ordersAndUsers.isNotEmpty())
        {
            noOrdersTextView.visibility = View.GONE
            orderRecyclerView = order_list
            linearLayoutManager = LinearLayoutManager(activity)
            orderRecyclerView.layoutManager = linearLayoutManager
            val adapter = OrderAdminAdapter(ordersAndUsers)
            adapter.onItemClick = { orderAndUser ->
                val order = orderViewModel.getOrderById(authToken, orderAndUser.orderId)
                val dialogBuilder = AlertDialog.Builder(activity!!)
                    .setCancelable(true)
                    .setNegativeButton("Nee") {
                            dialog, id -> dialog.dismiss()
                    }
                    .setPositiveButton("Ja") {
                            dialog, id -> dialog.dismiss()
                        val deletedOrder = orderViewModel.deleteOrder(authToken, order)
                        fragmentManager!!.beginTransaction()
                            .replace(R.id.main_content_container, newInstance())
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