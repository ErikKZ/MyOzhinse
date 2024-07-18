package kz.example.myozinshe.presentation

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentRePasswordBinding
import kz.example.myozinshe.domain.models.UserInfo
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.viewModel.RePasswordViewModel

class RePasswordFragment : Fragment() {
    private var binding : FragmentRePasswordBinding? = null

    private val rePasswordViewModel: RePasswordViewModel by viewModels()
    private val navController by lazy { findNavController() }

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRePasswordBinding.inflate(inflater, container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        

        setupUI()
        setObserve()
    }

    private fun setupUI() {
        val token = PreferenceProvider(requireContext()).getToken()!!
        binding?.apply {
            showPassword()
            btnSendDateInDB.setOnClickListener {
                val password1 = editTextPasswordRegIn.text.toString()
                val password2 = editTextPasswordagainCheckRegIn.text.toString()
                rePasswordViewModel.updatePassword(token,password1,password2)

            }
        }
    }

    private fun showPassword(){
        val passwordPlace1 = binding?.editTextPasswordRegIn
        val passwordPlace2 = binding?.editTextPasswordagainCheckRegIn

        binding?.btnShowPassword?.setOnClickListener {

            if (passwordPlace1?.transformationMethod == HideReturnsTransformationMethod.getInstance()){
                passwordPlace1?.transformationMethod = PasswordTransformationMethod.getInstance()
            }else{
                passwordPlace1?.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }

        }
        binding?.btnShowPasswordAgain?.setOnClickListener {

            if (passwordPlace2?.transformationMethod == HideReturnsTransformationMethod.getInstance()){
                passwordPlace2?.transformationMethod = PasswordTransformationMethod.getInstance()
            }else{
                passwordPlace2?.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }

        }
    }

    private fun setObserve() {
        rePasswordViewModel.changePasswordResponse.observe(viewLifecycleOwner, ::handlechangePassword)
        rePasswordViewModel.passwordValidationState.observe(viewLifecycleOwner, ::handlePasswordValidation)
        rePasswordViewModel.passwordLengthState.observe(viewLifecycleOwner, ::handlePasswordLength)
        rePasswordViewModel.errorCode.observe(viewLifecycleOwner, ::handleErroCode)
    }

    private fun handleErroCode(codeError: Int){
        Toast.makeText(requireContext(), getString(codeError), Toast.LENGTH_SHORT).show()
    }
    private fun handlePasswordLength(isState: Boolean){
        if (!isState) {
            Toast.makeText(requireContext(),getString(R.string.error_short_password), Toast.LENGTH_SHORT).show()
        }
    }
    private fun handlePasswordValidation(isState: Boolean){
        if (!isState) {
            Toast.makeText(requireContext(),getString(R.string.error_password_equal), Toast.LENGTH_SHORT).show()
        }
    }
    private fun handlechangePassword(userInfo: UserInfo){
        Toast.makeText(requireContext(),"${getText(R.string.message_info_update)}", Toast.LENGTH_SHORT).show()
        navController.navigate(R.id.profileFragment)
    }

    private fun setupNavigationHost() {
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
            additionalToolBarConfig(
                toolbarVisible = true,
                btnBackVisible = true,
                btnExitVisible = false,
                title = getString(R.string.KupiaSozdiOzgertu)
            )
            onClickListener(R.id.profileFragment)
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}