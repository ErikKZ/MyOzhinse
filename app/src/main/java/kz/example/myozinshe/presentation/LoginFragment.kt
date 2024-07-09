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
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentLoginBinding
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.viewModel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
    loginViewModel.apply {
        loginBody.observe(viewLifecycleOwner, Observer { responseBody ->
            PreferenceProvider(requireContext()).apply {
                saveToken(responseBody.accessToken)
                saveLanguage("Қазақша")
            }
            navigate(R.id.loginFragment)
        })

        emailError.observe(viewLifecycleOwner, Observer { error ->
            updateUIForError(binding.textTvErrorEmailLogIn, error, R.string.error_invalid_email_format, binding.editTextEmailLogIn, R.drawable.style_edittext_error)
        })

        passwordError.observe(viewLifecycleOwner, Observer { error ->
            updateUIForError(binding.textTvErrorResultLogIn, error, R.string.error_short_password)
        })

        loginErrorCode.observe(viewLifecycleOwner, Observer { errorCode ->
            binding.textTvErrorResultLogIn.apply {
                visibility = View.VISIBLE
                text = getString(errorCode)
            }
        })
    }
}

    private fun setupListeners() {
        binding.apply {
            btnBackWelcomeFragment.setOnClickListener { navigate(R.id.welcomeFragment) }
            btnTextTransitionForRegIn.setOnClickListener { navigate(R.id.welcomeFragment) }
            btnLogInApp.setOnClickListener {
                loginViewModel.executeLogin(editTextEmailLogIn.text.toString(), editTextPasswordLogIn.text.toString())
            }
            btnShowPassword.setOnClickListener {
                val passwordPlace = binding.editTextPasswordLogIn
                if (passwordPlace.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                    passwordPlace.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    passwordPlace.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
            }

            btnTextTransitionForRegIn.setOnClickListener {
                navigate(R.id.registrationFragment)
            }
        }
    }

    private fun navigate(destinationId: Int) {
        findNavController().navigate(destinationId)
    }

    private fun updateUIForError(textView: TextView, error: Boolean?, errorMsgResId: Int, editText: EditText? = null, errorDrawableResId: Int? = null) {
        textView.visibility = if (error == true) View.VISIBLE else View.GONE
        textView.text = if (error == true) getString(errorMsgResId) else ""

        editText?.background = if (error == true) {
            ResourcesCompat.getDrawable(resources, errorDrawableResId ?: 0, requireContext().theme)
        } else {
            ResourcesCompat.getDrawable(resources, R.drawable.style_edittext_on_any_touch, requireContext().theme)
        }
    }

    override fun onResume() {
        super.onResume()
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
            setNavigationToolBar(false, true)
        }
    }

    override fun onPause() {
        super.onPause()
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
            setNavigationToolBar(false, true)
        }
    }
}