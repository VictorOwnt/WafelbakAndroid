package be.scoutswondelgem.wafelbak.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.databinding.FragmentOrderBinding
import be.scoutswondelgem.wafelbak.viewmodels.UserViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_USER_ID = "userId"

class OrderFragment : Fragment() {

    private var userId: String? = null

    companion object {
        @JvmStatic
        fun newInstance(userId: String?) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USER_ID, userId)
                }
            }
    }
    //Injecteren:
    private val userViewModel by viewModel<UserViewModel>()
    private val sharedPreferences: SharedPreferences = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentOrderBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }


}