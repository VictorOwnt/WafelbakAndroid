package be.scoutswondelgem.wafelbak.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
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
import be.scoutswondelgem.wafelbak.models.enums.DeliveryStatus
import be.scoutswondelgem.wafelbak.viewmodels.OrderViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mancj.materialsearchbar.MaterialSearchBar
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
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var noOrdersTextView: TextView
    private lateinit var searchBar: MaterialSearchBar
    private lateinit var searchButton: FloatingActionButton
    private lateinit var searchTextView: TextView


    //Injecteren:
    private val orderViewModel by viewModel<OrderViewModel>()
    private val sharedPreferences: SharedPreferences = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }
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
        noOrdersTextView = view.noOrdersYet
        searchBar = view.searchBar
        searchBar.visibility = View.GONE
        searchButton = view.button_search
        searchTextView = view.search
        searchButton.setOnClickListener {
            it.visibility = View.GONE
            searchTextView.visibility = View.GONE
            searchBar.visibility = View.VISIBLE
            searchBar.enableSearch()                    //TODO focus? keyboard down?
        }
        fillListView(sharedPreferences.getString("TOKEN", "")!!)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button.
        when (item.itemId) {
            R.id.action_stats -> {
               activity?.supportFragmentManager!!.beginTransaction()
                    .replace(R.id.main_content_container, StatisticsFragment.newInstance(), "StatisticsFragment")
                    .addToBackStack("AllOrderFragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }
        return true
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
                when(order.deliveryStatus.status) {
                    "Te Bezorgen"-> {
                        order.deliveryStatus = DeliveryStatus.DELIVERED
                        orderViewModel.completeOrder(authToken, order)
                        activity?.supportFragmentManager!!.beginTransaction()
                            .replace(R.id.main_content_container, newInstance(), "AllOrderFragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                        Toast.makeText(
                            activity,
                            "Bestelling " + order.orderId + " werd voltooid!",
                            Toast.LENGTH_SHORT
                        ).show()
                        //TODO change color or something?
                    }
                    "Bezorgd"-> {
                        val dialogBuilder = AlertDialog.Builder(activity!!)
                            .setCancelable(true)
                            .setNegativeButton("Nee") {
                                    dialog, id -> dialog.dismiss()
                            }
                            .setPositiveButton("Ja") {
                                    dialog, id -> dialog.dismiss()
                                order.deliveryStatus = DeliveryStatus.NOTDELIVERED
                                orderViewModel.completeOrder(authToken, order)
                                activity?.supportFragmentManager!!.beginTransaction()
                                    .replace(R.id.main_content_container, newInstance(), "AllOrderFragment")
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .commit()
                                Toast.makeText(
                                    activity,
                                    "Bestelling " + order.orderId + " successvol onvoltooid gemaakt!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        val alert = dialogBuilder.create()
                        alert.setTitle("Ben je zeker dat bestelling " + order.orderId + " nog niet voltooid is?")
                        alert.show()
                    }
                }
            }
            adapter.onItemClick2 = { orderAndUser ->
                val order = orderViewModel.getOrderById(authToken, orderAndUser.orderId)
                val dialogBuilder = AlertDialog.Builder(activity!!)
                    .setCancelable(true)
                    .setNegativeButton("Nee") {
                            dialog, id -> dialog.dismiss()
                    }
                    .setPositiveButton("Ja") {
                            dialog, id -> dialog.dismiss()
                        val deletedOrder = orderViewModel.deleteOrder(authToken, order)
                        activity?.supportFragmentManager!!.beginTransaction()
                            .replace(R.id.main_content_container, newInstance(), "AllOrderFragment")
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
            searchBar.addTextChangeListener(object: TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    //SEARCH FILTER
                    adapter.filter.filter(charSequence)
                }
                override fun afterTextChanged(editable: Editable) {}        //TODO experimenteren met button aftertextChanged visibility
            })
        }
    }
}