package be.scoutswondelgem.wafelbak.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.databinding.FragmentEditOrderBinding
import be.scoutswondelgem.wafelbak.models.enums.DeliveryTime
import be.scoutswondelgem.wafelbak.viewmodels.OrderViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
    private lateinit var orderIdLayout: TextInputLayout
    private lateinit var orderIdInput: TextInputEditText
    private lateinit var amountOfWafllesLabel: TextView
    private lateinit var amountOfWafflesGroup: ChipGroup
    private lateinit var twoWafflesChip: Chip
    private lateinit var fourWafflesChip: Chip
    private lateinit var sixWafflesChip: Chip
    private lateinit var eightWafflesChip: Chip
    private lateinit var twelveWafflesChip: Chip
    private lateinit var twentyWafflesChip: Chip
    private lateinit var desiredDeliveryTimeLabel: TextView
    private lateinit var desiredDeliveryTimeGroup: ChipGroup
    private lateinit var morningChip: Chip
    private lateinit var middayChip: Chip
    private lateinit var eveningChip: Chip
    private lateinit var idcChip: Chip
    private lateinit var commentLabel: TextView
    private lateinit var commentLayout: TextInputLayout
    private lateinit var commentInput: TextInputEditText
    private lateinit var saveButton: Button

    //Injecteren
    private val orderViewModel by viewModel<OrderViewModel>()
    private val sharedPreferences: SharedPreferences = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderId = it.getInt(ARG_ORDER_ID)
        }
    }
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
        orderIdLayout = view.inputLayout_orderId
        orderIdInput = view.input_orderId
        amountOfWafflesGroup = view.amountOfWafflesGroup
        amountOfWafllesLabel = view.amountOfWafflesLabel
        twoWafflesChip = view.twoWaffles
        fourWafflesChip = view.fourWaffles
        sixWafflesChip = view.sixWaffles
        eightWafflesChip = view.eightWaffles
        twelveWafflesChip = view.twelveWaffles
        twentyWafflesChip = view.twentyWaffles
        desiredDeliveryTimeGroup = view.desiredDeliveryTimeGroup
        desiredDeliveryTimeLabel = view.desiredDeliveryTimeLabel
        morningChip = view.morning
        middayChip = view.midday
        eveningChip = view.evening
        idcChip = view.idc
        commentLabel = view.commentLabel
        commentLayout = view.inputLayout_comment
        commentInput = view.input_comment
        saveButton = view.button_save
        fillOrderView(sharedPreferences.getString("TOKEN", "")!!, orderId!!)
    }

    private fun fillOrderView(authToken: String, id: Int) {
        val order = orderViewModel.getOrderById(authToken, id)
        if(order.deliveryStatus.status == "Bezorgd") {
            twoWafflesChip.isEnabled = false
            fourWafflesChip.isEnabled = false
            sixWafflesChip.isEnabled = false
            eightWafflesChip.isEnabled = false
            twelveWafflesChip.isEnabled = false
            twentyWafflesChip.isEnabled = false
            morningChip.isEnabled = false
            middayChip.isEnabled = false
            eveningChip.isEnabled = false
            idcChip.isEnabled = false
            commentInput.isEnabled = false
            saveButton.isEnabled = false
        }
        orderIdInput.setText(order.orderId.toString())
        when (order.amountOfWaffles) {
            2 -> amountOfWafflesGroup.check(twoWafflesChip.id)
            4 -> amountOfWafflesGroup.check(fourWafflesChip.id)
            6 -> amountOfWafflesGroup.check(sixWafflesChip.id)
            8 -> amountOfWafflesGroup.check(eightWafflesChip.id)
            12 -> amountOfWafflesGroup.check(twelveWafflesChip.id)
            20 -> amountOfWafflesGroup.check(twentyWafflesChip.id)
        }
        when (order.desiredDeliveryTime) {
            DeliveryTime.VOORMIDDAG -> desiredDeliveryTimeGroup.check(morningChip.id)
            DeliveryTime.NAMIDDAG -> desiredDeliveryTimeGroup.check(middayChip.id)
            DeliveryTime.AVOND-> desiredDeliveryTimeGroup.check(eveningChip.id)
            DeliveryTime.MAAKTNIETUIT -> desiredDeliveryTimeGroup.check(idcChip.id)
        }
        if(order.comment !== null)
        {
            commentInput.setText(order.comment)
        } else {
            commentInput.setText("")
        }

        saveButton.setOnClickListener {
            when (amountOfWafflesGroup.checkedChipId) {
                twoWafflesChip.id -> order.amountOfWaffles = 2
                fourWafflesChip.id -> order.amountOfWaffles = 4
                sixWafflesChip.id -> order.amountOfWaffles = 6
                eightWafflesChip.id -> order.amountOfWaffles = 8
                twelveWafflesChip.id -> order.amountOfWaffles = 12
                twentyWafflesChip.id -> order.amountOfWaffles = 20
            }
            when (desiredDeliveryTimeGroup.checkedChipId) {
                morningChip.id -> order.desiredDeliveryTime = DeliveryTime.VOORMIDDAG
                middayChip.id -> order.desiredDeliveryTime = DeliveryTime.NAMIDDAG
                eveningChip.id -> order.desiredDeliveryTime = DeliveryTime.AVOND
                idcChip.id -> order.desiredDeliveryTime = DeliveryTime.MAAKTNIETUIT
            }
            if(commentInput.text !== null)
            {
                order.comment = commentInput.text.toString()
            }
            val updatedOrder = orderViewModel.updateOrder(authToken, order)
            Toast.makeText(
                activity,
                "Wijziging van bestelling " + updatedOrder.orderId + " opgeslaan!",
                Toast.LENGTH_SHORT
            ).show()
            fragmentManager!!.beginTransaction()
                .replace(R.id.main_content_container, OrderFragment.newInstance(), "OrderFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("EditOrderFragment")
                .commit()
            (activity as MainActivity).hideKeyboard()
        }
    }
}