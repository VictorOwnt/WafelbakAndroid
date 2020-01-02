package be.scoutswondelgem.wafelbak.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.databinding.FragmentEditOrderBinding
import be.scoutswondelgem.wafelbak.viewmodels.OrderViewModel
import kotlinx.android.synthetic.main.fragment_edit_order.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_ORDER_ID = "orderId"

class EditOrderFragment: Fragment() {

    private var orderId: Int? = null
    //Voor creatie EditOrderFragment
    companion object {
        @JvmStatic
        fun newInstance(orderId: Int?) =
            EditOrderFragment().apply {
                arguments = Bundle().apply {
                    if (orderId != null) {
                        putInt(ARG_ORDER_ID, orderId)
                    }
                }
            }
    }

    //UI elementen
    private lateinit var addOrderText: TextView

    //Injecteren
    private val orderViewModel by viewModel<OrderViewModel>()
    private val sharedPreferences: SharedPreferences = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEditOrderBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_order, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.orderViewModel = orderViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            orderId = it.getInt(ARG_ORDER_ID)
        }
        addOrderText = view.orderIdText
        addOrderText.text = orderId.toString()
        //fillListView(sharedPreferences.getString("TOKEN", "")!!, sharedPreferences.getString("ID", "")!!.toInt())
    }
}