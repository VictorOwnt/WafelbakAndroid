package be.scoutswondelgem.wafelbak.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.databinding.FragmentStatisticsBinding
import be.scoutswondelgem.wafelbak.viewmodels.OrderViewModel
import kotlinx.android.synthetic.main.fragment_statistics.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class StatisticsFragment: Fragment() {
    //Voor creatie StatisticsFragment
    companion object {
        @JvmStatic
        fun newInstance() = StatisticsFragment()
    }

    //UI elementen
    private lateinit var totalOrderImg: ImageView
    private lateinit var totalOrderText: TextView
    private lateinit var totalOrderValue: TextView
    private lateinit var totalWafflesImg: ImageView
    private lateinit var totalWafflesText: TextView
    private lateinit var totalWafflesValue: TextView
    private lateinit var bestStreetImg: ImageView
    private lateinit var bestStreetText: TextView
    private lateinit var bestStreetValue: TextView
    private lateinit var totalDeliveredImg: ImageView
    private lateinit var totalDeliveredText: TextView
    private lateinit var totalDeliveredValue: TextView

    //Injecteren
    private val orderViewModel by viewModel<OrderViewModel>()
    private val sharedPreferences: SharedPreferences = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStatisticsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        activity?.title = "Statistieken"
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.orderViewModel = orderViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        totalOrderImg = view.totalOrdersImg
        totalOrderText = view.totalOrders
        totalOrderValue = view.totalOrdersValue
        totalWafflesImg = view.totalWafflesImg
        totalWafflesText = view.totalWaffles
        totalWafflesValue = view.totalWafflesValue
        bestStreetImg = view.bestStreetImg
        bestStreetText = view.bestStreet
        bestStreetValue = view.bestStreetValue
        totalDeliveredImg = view.totalDeliveredImg
        totalDeliveredText = view.totalDelivered
        totalDeliveredValue = view.totalDeliveredValue
        calculateStats(sharedPreferences.getString("TOKEN","")!!)
    }

    fun calculateStats(authToken: String) {
        val ordersAndUsers = orderViewModel.getOrdersJoined(authToken)
        var totalOrders = 0
        var totalWaffles = 0
        var totalStreets = ""
        var totalDelivered = 0
        ordersAndUsers.forEach{
            totalOrders += 1
            totalWaffles += it.amountOfWaffles
            totalStreets += (it.user.street + ",")
            if(it.deliveryStatus.status == "Bezorgd") {
                totalDelivered += 1
            }
        }
        val allStreets = totalStreets.split(',')
        val streetFrequencyMap = allStreets.groupingBy {it}.eachCount()
        var index = 0
        var bestStreet = ""
        streetFrequencyMap.forEach{
            if(it.value > index) {
                index = it.value
                bestStreet = it.key
            }
        }
        totalOrderValue.text = totalOrders.toString()
        totalWafflesValue.text = totalWaffles.toString()
        bestStreetValue.text = bestStreet
        totalDeliveredValue.text = totalDelivered.toString()
    }
}