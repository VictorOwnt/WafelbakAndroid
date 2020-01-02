package be.scoutswondelgem.wafelbak.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private lateinit var titleTextView : TextView
    private lateinit var addOrderTextView : TextView
    private lateinit var addOrderButton: FloatingActionButton
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView = view.title_order
        addOrderTextView = view.addOrder
        addOrderButton = view.button_addOrder
        fillListView(sharedPreferences.getString("TOKEN", "")!!, sharedPreferences.getString("ID", "")!!.toInt())
    }

    private fun fillListView(authToken: String, id: Int){
        val orders = orderViewModel.getOrdersForCurrentUser(authToken, id)
        orderRecyclerView = order_list
        linearLayoutManager = LinearLayoutManager(activity)
        orderRecyclerView.layoutManager = linearLayoutManager
        val adapter = OrderAdapter(orders)
        adapter.setListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.main_content_container, EditOrderFragment.newInstance(adapter.selectedOrder))
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ?.addToBackStack(null)
                ?.commit()
        }
        orderRecyclerView.adapter = adapter
        /*orderRecyclerView.setOnClickListener { parent, view, position, id ->

        }*/
        /*adapter.setListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.main_content_container, EditOrderFragment.newInstance(adapter.orderID))
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ?.addToBackStack(null)
                ?.commit()
        }*/
        //orderRecyclerView.adapter = adapter
    }
}