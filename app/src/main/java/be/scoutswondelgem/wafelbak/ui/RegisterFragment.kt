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
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import be.scoutswondelgem.wafelbak.R
import be.scoutswondelgem.wafelbak.databinding.FragmentRegisterBinding
import be.scoutswondelgem.wafelbak.viewmodels.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.nulabinc.zxcvbn.Zxcvbn
import kotlinx.android.synthetic.main.fragment_register.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import javax.security.auth.login.LoginException

class RegisterFragment : Fragment() {
    //Voor creatie RegisterFragment
    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }

    //UI elementen
    private lateinit var titleText: TextView
    private lateinit var emailLayout: TextInputLayout
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var passwordInput: TextInputEditText
    private lateinit var passwordVerifyLayout: TextInputLayout
    private lateinit var passwordVerifyInput: TextInputEditText
    private lateinit var firstNameLayout: TextInputLayout
    private lateinit var firstNameInput: TextInputEditText
    private lateinit var lastNameLayout: TextInputLayout
    private lateinit var lastNameInput: TextInputEditText
    private lateinit var birthdayLayout: TextInputLayout
    private lateinit var birthdayInput: TextInputEditText
    private lateinit var streetLayout: TextInputLayout
    private lateinit var streetInput: TextInputEditText
    private lateinit var streetNumberLayout: TextInputLayout
    private lateinit var streetNumberInput: TextInputEditText
    private lateinit var streetExtraLayout: TextInputLayout
    private lateinit var streetExtraInput: TextInputEditText
    private lateinit var postalCodeLayout: TextInputLayout
    private lateinit var postalCodeInput: TextInputEditText
    private lateinit var cityLayout: TextInputLayout
    private lateinit var cityInput: TextInputEditText
    private lateinit var registerButton: Button

    //Injecteren:
    private val userViewModel by viewModel<UserViewModel>()
    private val sharedPreferences: SharedPreferences = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleText = view.title_register
        emailLayout = view.inputLayout_email
        emailInput = view.input_email
        passwordLayout = view.inputLayout_password
        passwordInput = view.input_password
        passwordVerifyLayout = view.inputLayout_verify_password
        passwordVerifyInput = view.input_verify_password
        firstNameLayout= view.inputLayout_firstName
        firstNameInput = view.input_firstName
        lastNameLayout = view.inputLayout_lastName
        lastNameInput = view.input_lastName
        birthdayLayout = view.inputLayout_birthday
        birthdayInput = view.input_birthday
        streetLayout = view.inputLayout_street
        streetInput = view.input_street
        streetNumberLayout = view.inputLayout_streetNumber
        streetNumberInput = view.input_streetNumber
        streetExtraLayout = view.inputLayout_streetExtra
        streetExtraInput = view.input_streetExtra
        postalCodeLayout = view.inputLayout_postalCode
        postalCodeInput = view.input_postalCode
        cityLayout = view.inputLayout_city
        cityInput = view.input_city
        registerButton = view.button_register

        //OnClickListener register button
        registerButton.setOnClickListener {
            try {
                var loggedInUser = userViewModel.register(
                    firstNameInput.text.toString(),
                    lastNameInput.text.toString(),
                    emailInput.text.toString(),
                    passwordInput.text.toString(),
                    birthdayInput.text.toString(),
                    streetInput.text.toString(),
                    streetNumberInput.text.toString().toInt(),
                    streetExtraInput.text?.toString(),
                    postalCodeInput.text.toString().toInt(),
                    cityInput.text.toString()
                )

                // Save logged in user
                sharedPreferences.edit()
                    .putString("ID", loggedInUser.userId.toString())
                    .putString("EMAIL", loggedInUser.email)
                    .putString("FIRSTNAME", loggedInUser.firstName)
                    .putString("LASTNAME", loggedInUser.lastName)
                    //.putString(SharedPreferencesEnum.IMGURL.string, loggedInUser.imgUrl)
                    .putBoolean("ADMIN", loggedInUser.isAdmin)
                    .putString("TOKEN", "Bearer " + loggedInUser.token)
                    .putBoolean("ISLOGGEDIN", true)
                    .putString("PREFNAME", loggedInUser.firstName + loggedInUser.userId.toString() + loggedInUser.lastName).apply()
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


        // TextWatchers
        emailInput.addTextChangedListener(emailWatcher)
        passwordInput.addTextChangedListener(passwordWatcher)
        passwordVerifyInput.addTextChangedListener(passwordVerifyWatcher)
        firstNameInput.addTextChangedListener(firstNameWatcher)
        lastNameInput.addTextChangedListener(lastNameWatcher)
        birthdayInput.addTextChangedListener(birthdayWatcher)
        streetInput.addTextChangedListener(streetWatcher)
        streetNumberInput.addTextChangedListener(streetNumberWatcher)
        postalCodeInput.addTextChangedListener(postalCodeWatcher)
        cityInput.addTextChangedListener(cityWatcher)
    }

    private val emailWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            emailLayout.error = getString(R.string.required)
            emailLayout.isErrorEnabled = emailInput.text.isNullOrBlank()
            if (!userViewModel.isValidEmail(emailInput.text.toString())) {
                emailLayout.error = getString(R.string.noEmailOrInUse)
                emailLayout.isErrorEnabled = true
            } else {
                emailLayout.isErrorEnabled = false
            }
        }
    }

    private val passwordWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            var zxcvbn = Zxcvbn()
            var strength = zxcvbn.measure(passwordInput.text.toString())
            passwordLayout.error = getString(R.string.required)
            passwordLayout.isErrorEnabled = passwordInput.text.isNullOrBlank()
            if(strength.score < 2)
            {
                passwordLayout.error = getString(R.string.passwordNotStrongEnough)
                passwordLayout.isErrorEnabled = true
            } else {
                passwordLayout.isErrorEnabled = false
            }
        }
    }

    private val passwordVerifyWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            passwordVerifyLayout.error = getString(R.string.required)
            passwordVerifyLayout.isErrorEnabled = passwordVerifyInput.text.isNullOrBlank()
            if (passwordInput.text.toString().isNotEmpty() && passwordVerifyInput.text.toString().isNotEmpty()) {
                if (passwordVerifyInput.text.toString() != passwordInput.text.toString()) {
                    passwordVerifyLayout.error = getString(R.string.passwordNotEqual)
                    passwordVerifyLayout.isErrorEnabled = true
                } else {
                    passwordVerifyLayout.isErrorEnabled = false
                }
            }
        }
    }

    private val firstNameWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            firstNameLayout.error = getString(R.string.required)
            firstNameLayout.isErrorEnabled = firstNameInput.text.isNullOrBlank()
        }
    }

    private val lastNameWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            lastNameLayout.error = getString(R.string.required)
            lastNameLayout.isErrorEnabled = lastNameInput.text.isNullOrBlank()
        }
    }

    private val birthdayWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            birthdayLayout.error = getString(R.string.required)
            birthdayLayout.isErrorEnabled = birthdayInput.text.isNullOrBlank()
        }
    }

    private val streetWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            streetLayout.error = getString(R.string.required)
            streetLayout.isErrorEnabled = streetInput.text.isNullOrBlank()
        }
    }

    private val streetNumberWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            streetNumberLayout.error = getString(R.string.required)
            streetNumberLayout.isErrorEnabled = streetNumberInput.text.isNullOrBlank()
        }
    }

    private val postalCodeWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            postalCodeLayout.error = getString(R.string.required)
            postalCodeLayout.isErrorEnabled = postalCodeInput.text.isNullOrBlank()
        }
    }

    private val cityWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            cityLayout.error = getString(R.string.required)
            cityLayout.isErrorEnabled = cityInput.text.isNullOrBlank()

            val nonBlank = !(emailInput.text.isNullOrBlank() || passwordInput.text.isNullOrBlank()
                    || passwordVerifyInput.text.isNullOrBlank() || firstNameInput.text.isNullOrBlank()
                    || lastNameInput.text.isNullOrBlank() || birthdayInput.text.isNullOrBlank()
                    || streetInput.text.isNullOrBlank() || streetNumberInput.text.isNullOrBlank()
                    || postalCodeInput.text.isNullOrBlank() || cityInput.text.isNullOrBlank())
            registerButton.isEnabled = nonBlank
        }
    }

}