package kz.example.myozinshe.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentProfileBinding
import kz.example.myozinshe.domain.models.SelectLanguageModel
import kz.example.myozinshe.domain.models.UserInfo
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.viewModel.ProfileViewModel

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        profileViewModel.systemLanguage()

//        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//        if (Build.VERSION.SDK_INT >= 26) {
//            transaction.setReorderingAllowed(false)
//        }
//        transaction.detach(this).attach(this).commit()

        val token = PreferenceProvider(requireContext()).getToken()!!
        profileViewModel.userInfo(token)
        setupUI()
        setObserve()
    }

    private fun setupUI() {
        binding?.apply {
            listOf(btnChangePassword1, btnChangePassword2).forEach { button ->
                button.setOnClickListener { navController.navigate(R.id.rePasswordFragment) }
            }
            listOf(btnImgJekeDerekter, btnInfoTransaction).forEach { button ->
                button.setOnClickListener { navController.navigate(R.id.infoFragment) }
            }
            listOf(btnSelectLanguageIcon, textTvSelectLanguageText).forEach { view ->
                view.setOnClickListener { SelectLanguage().show(parentFragmentManager, "") }
            }
        }
    }

    private fun setObserve(){
        profileViewModel.apply {
            userInfo.observe(viewLifecycleOwner, ::handleUserInfo)
            errorCode.observe(viewLifecycleOwner, ::handleErrorCode)
            languageSystem.observe(viewLifecycleOwner, ::handleLanguageSystem)
            isDarkModeEnabled.observe(viewLifecycleOwner, ::handleIsDarkModeEnabled)
        }
    }
    private fun handleIsDarkModeEnabled(flagDark: Boolean){
        binding?.dayNightSwitch?.isChecked = flagDark

        binding?.dayNightSwitch?.setOnCheckedChangeListener(null)
        binding?.dayNightSwitch?.setOnCheckedChangeListener {_, isEnabled ->
            profileViewModel.toggleDarkMode(isEnabled)
//            activity?.recreate()
        }
    }

    private fun handleLanguageSystem(item: SelectLanguageModel){
        binding?.textTvSelectLanguageText?.text = item?.language ?: "Қазақша"
    }
    private fun handleErrorCode(item: Int){
        binding?.textTvEmailUser?.text = getString(item)
    }

    private fun handleUserInfo(item: UserInfo){
        binding?.textTvEmailUser?.text = item.user.email
    }
    private fun setupNavigationHost() {
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
            additionalToolBarConfig(
                toolbarVisible = true,
                btnBackVisible = true,
                btnExitVisible = true,
                title = getString(R.string.MyProfile)
            )
            onClickListener(R.id.mainFragment)
            showBottomSheetExit(ExitAccountFragment())
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