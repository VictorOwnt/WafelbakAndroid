package be.scoutswondelgem.wafelbak.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.databinding.FragmentLoginBinding
import be.scoutswondelgem.wafelbak.viewmodels.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import javax.security.auth.login.LoginException

class LoginFragment : Fragment() {

    //Ui elementen:
    private lateinit var signinButton: Button
    private lateinit var emailInput: TextInputEditText
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInput: TextInputEditText
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var registerButton: Button

    //Injecteren:
    private val userViewModel by viewModel<UserViewModel>()
    private val sharedPreferences: SharedPreferences = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signinButton = view.button_sign_in
        emailInput = view.input_email
        emailInputLayout = view.inputlayout_email
        passwordInput = view.input_password
        passwordInputLayout = view.inputlayout_password
        registerButton = view.button_register

        // OnClickListener sign in button
        signinButton.setOnClickListener {
                try {
                    var loggedInUser = userViewModel.login(
                        emailInput.text.toString(),
                        passwordInput.text.toString()
                    )

                    // Save logged in user
                    if (loggedInUser.role.role == "user")
                    {
                        sharedPreferences.edit()
                            .putString("ID", loggedInUser.userId.toString())
                            .putString("EMAIL", loggedInUser.email)
                            .putString("FIRSTNAME", loggedInUser.firstName)
                            .putString("LASTNAME", loggedInUser.lastName)
                            .putString("ROLE", loggedInUser.role.role)
                            .putString("TOKEN", "Bearer " + loggedInUser.token)
                            .putBoolean("ISLOGGEDIN", true).apply()
                    } else {
                        sharedPreferences.edit()
                            .putString("ID", loggedInUser.userId.toString())
                            .putString("EMAIL", loggedInUser.email)
                            .putString("ROLE", loggedInUser.role.role)
                            .putString("TOKEN", "Bearer " + loggedInUser.token)
                            .putBoolean("ISLOGGEDIN", true).apply()
                    }
                    // Open MainActivity
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    (activity as AuthActivity).hideKeyboard()
                    activity!!.finish()
                } catch (e: LoginException) {
                    (activity as AuthActivity).hideKeyboard()
                    AlertDialog.Builder(context!!)
                        .setTitle("Something went wrong")
                        .setMessage(e.message)
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(R.drawable.ic_error)
                        .show()
                }
        }

        // OnClickListener register button
        registerButton.setOnClickListener{
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.auth_content_container, RegisterFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("LoginFragment")
                .commit()
        }

        // TextWatchers
        emailInput.addTextChangedListener(emailWatcher)
        passwordInput.addTextChangedListener(passwordWatcher)
    }

    private val emailWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {} //TODO password not strong enough, email valid
        override fun afterTextChanged(s: Editable) {
            emailInputLayout.error = getString(R.string.required)
            emailInputLayout.isErrorEnabled = emailInput.text.isNullOrBlank()
        }
    }

    private val passwordWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {} //TODO password not strong enough, email valid
        override fun afterTextChanged(s: Editable) {
            passwordInputLayout.error = getString(R.string.required)
            passwordInputLayout.isErrorEnabled = passwordInput.text.isNullOrBlank()

            val nonBlank = !(emailInput.text.isNullOrBlank() || passwordInput.text.isNullOrBlank())
            signinButton.isEnabled = nonBlank
        }
    }
}