package be.scoutswondelgem.wafelbak.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.databinding.FragmentEditOrderBinding
import be.scoutswondelgem.wafelbak.models.DeliveryDate
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
    private lateinit var orderTitle: TextView
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
        orderTitle = view.title_order
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
            DeliveryDate.VOORMIDDAG -> desiredDeliveryTimeGroup.check(morningChip.id)
            DeliveryDate.NAMIDDAG -> desiredDeliveryTimeGroup.check(middayChip.id)
            DeliveryDate.AVOND-> desiredDeliveryTimeGroup.check(eveningChip.id)
            DeliveryDate.MAAKTNIETUIT -> desiredDeliveryTimeGroup.check(idcChip.id)
        }
        if(order.comment !== null)
        {
            commentInput.setText(order.comment)
        } else {
            commentInput.setText("")
        }

        saveButton.setOnClickListener {//TODO passende toast melding en terugkeren naar vorige scherm
            when (amountOfWafflesGroup.checkedChipId) {
                twoWafflesChip.id -> order.amountOfWaffles = 2
                fourWafflesChip.id -> order.amountOfWaffles = 4
                sixWafflesChip.id -> order.amountOfWaffles = 6
                eightWafflesChip.id -> order.amountOfWaffles = 8
                twelveWafflesChip.id -> order.amountOfWaffles = 12
                twentyWafflesChip.id -> order.amountOfWaffles = 20
            }
            when (desiredDeliveryTimeGroup.checkedChipId) {
                morningChip.id -> order.desiredDeliveryTime = DeliveryDate.VOORMIDDAG
                middayChip.id -> order.desiredDeliveryTime = DeliveryDate.NAMIDDAG
                eveningChip.id -> order.desiredDeliveryTime = DeliveryDate.AVOND
                idcChip.id -> order.desiredDeliveryTime = DeliveryDate.MAAKTNIETUIT
            }
            if(commentInput.text !== null)
            {
                order.comment = commentInput.text.toString()
            }
            val updatedOrder = orderViewModel.updateOrder(authToken, order)
            Log.i("MyApp", updatedOrder.toString())
            fragmentManager?.popBackStackImmediate()
        }
    }
}