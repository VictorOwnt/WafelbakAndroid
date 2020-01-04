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
import be.scoutswondelgem.wafelbak.databinding.FragmentCreateOrderBinding
import be.scoutswondelgem.wafelbak.models.DeliveryDate
import be.scoutswondelgem.wafelbak.viewmodels.OrderViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_edit_order.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class CreateOrderFragment: Fragment() {
    //Voor creatie CreateOrderFragment
    companion object {
        @JvmStatic
        fun newInstance() = CreateOrderFragment()
    }

    //UI elementen
    private lateinit var orderTitle: TextView
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreateOrderBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_order, container, false)
        activity?.title = "Bestelling Plaatsen"
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.orderViewModel = orderViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderTitle = view.title_order
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
        saveButton.setOnClickListener{
            createOrder(sharedPreferences.getString("TOKEN", "")!!,sharedPreferences.getString("ID", "")!!.toInt())
        }
    }

    fun createOrder(authtoken: String, userId: Int) {
        var amountOfWaffles = 2
        when (amountOfWafflesGroup.checkedChipId) {
            twoWafflesChip.id -> amountOfWaffles = 2
            fourWafflesChip.id -> amountOfWaffles = 4
            sixWafflesChip.id -> amountOfWaffles = 6
            eightWafflesChip.id -> amountOfWaffles = 8
            twelveWafflesChip.id -> amountOfWaffles = 12
            twentyWafflesChip.id -> amountOfWaffles = 20
        }
        var desiredDeliveryTime: DeliveryDate = DeliveryDate.MAAKTNIETUIT
        when (desiredDeliveryTimeGroup.checkedChipId) {
            morningChip.id -> desiredDeliveryTime = DeliveryDate.VOORMIDDAG
            middayChip.id -> desiredDeliveryTime = DeliveryDate.NAMIDDAG
            eveningChip.id -> desiredDeliveryTime = DeliveryDate.AVOND
            idcChip.id -> desiredDeliveryTime = DeliveryDate.MAAKTNIETUIT
        }
        var comment: String = ""
        if(commentInput.text !== null)
        {
            comment = commentInput.text.toString()
        }
        val order = orderViewModel.createOrder(authtoken, amountOfWaffles, desiredDeliveryTime, comment, userId)
        fragmentManager!!.beginTransaction()
            .replace(R.id.main_content_container, OrderFragment.newInstance())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack("CreateOrderFragment")
            .commit()
        Toast.makeText(
            activity,
            "Bestelling " + order.orderId + " successvol geplaatst!",
            Toast.LENGTH_SHORT
        ).show()
    }
}