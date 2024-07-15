package kz.example.myozinshe.presentation

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentRegistrationBinding
import kz.example.myozinshe.domain.models.LoginResponse
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.viewModel.RegistrationViewModel

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val registrationViewModel: RegistrationViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        with(registrationViewModel) {
            registerBody.observe(viewLifecycleOwner, ::handleRegisterBody)
            errorCode.observe(viewLifecycleOwner, ::handleErrorCode)
            emailValidationState.observe(viewLifecycleOwner, ::handleEmailValidationState)
            passwordValidationState.observe(viewLifecycleOwner, ::handlePasswordValidationState)
            passwordLengthState.observe(viewLifecycleOwner, ::handlePasswordLengthState)
        }
    }

    private fun setupListeners() {
        with(binding) {
            btnSendDateInDB.setOnClickListener {
                val email = editTextEmailRegIn.text.toString()
                val password = editTextPasswordRegIn.text.toString()
                val password2 = editTextPasswordagainCheckRegIn.text.toString()
                registrationViewModel.register(email, password, password2)
            }

            btnBack.setOnClickListener { navigate(R.id.loginFragment) }
            btnShowPassword.setOnClickListener { togglePasswordVisibility(editTextPasswordRegIn) }
            btnShowPasswordAgain.setOnClickListener { togglePasswordVisibility(editTextPasswordagainCheckRegIn) }
        }
    }

    private fun togglePasswordVisibility(editText: EditText) {
        editText.transformationMethod = if (editText.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
            PasswordTransformationMethod.getInstance()
        } else {
            HideReturnsTransformationMethod.getInstance()
        }
    }

    private fun navigate(destinationId: Int) {
        navController.navigate(destinationId)
    }

    private fun handleRegisterBody(responseBody: LoginResponse) {
        PreferenceProvider(requireContext()).apply {
            saveToken(responseBody.accessToken)
            saveLanguage("Қазақша")
        }
        navigate(R.id.loginFragment)
    }

    private fun handleErrorCode(error: Int) {
        with(binding.textTvErrorResultRegIn) {
            visibility = View.VISIBLE
            text = getString(error)
        }
    }

    private fun handleEmailValidationState(errorEmail: Boolean) {
        with(binding.textTvErrorEmailRegIn) {
            visibility = if (errorEmail) View.GONE else View.VISIBLE
            text = getString(R.string.error_invalid_email_format)
        }
    }

    private fun handlePasswordValidationState(state: Boolean) {
        with(binding.textTvErrorResultRegIn) {
            visibility = if (state) View.GONE else View.VISIBLE
            text = getString(R.string.error_password_equal)
        }
    }

    private fun handlePasswordLengthState(state: Boolean) {
        with(binding.textTvErrorPasswordLenght) {
            visibility = if (state) View.GONE else View.VISIBLE
            text = getString(R.string.error_short_password)
        }
    }

    private fun setupNavigationHost() {
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
            additionalToolBarConfig(
                toolbarVisible = false,
                btnBackVisible = false,
                btnExitVisible = false,
                title = ""
            )
        }
    }
    override fun onResume() {
        super.onResume()
        setupNavigationHost()
    }

    override fun onPause() {
        super.onPause()
        setupNavigationHost()
    }
}