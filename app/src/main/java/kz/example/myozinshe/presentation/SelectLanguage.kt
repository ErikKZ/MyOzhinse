package kz.example.myozinshe.presentation

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.BottomSheetLanguageBinding
import kz.example.myozinshe.domain.models.SelectLanguageModel
import java.util.Locale

class SelectLanguage : BottomSheetDialogFragment() {
    private var binding: BottomSheetLanguageBinding? = null
//    private val vm: VM by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomSheetLanguageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setupLanguageButtons()
    }

    private fun setupLanguageButtons() {
        val context = requireContext()
        val systemLanguage = PreferenceProvider(requireContext()).getLanguage()!!
        updateUI(systemLanguage)
        binding?.apply {
            btnSelectEnglish.setOnClickListener { changeLanguage("English") }
            btnSelectQazaq.setOnClickListener { changeLanguage("Қазақша") }
            btnSelectRussian.setOnClickListener { changeLanguage("Русский") }
        }
    }

    private fun changeLanguage(language: String) {
        PreferenceProvider(requireContext()).saveLanguage(language)
        updateLocale(language)
        updateUI(language)
//        vm.systemLanguage.value = SelectLanguageModel(language)
        navigateToProfile()
    }

    private fun updateLocale(language: String) {
        val locale = when (language) {
            "English" -> Locale("en")
            "Қазақша" -> Locale("kk")
            "Русский" -> Locale("ru")
            else -> Locale.getDefault()
        }
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        val context: Context = requireContext().createConfigurationContext(config)
//        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)
    }

    private fun updateUI(language: String) {
        binding?.apply {
            val (selectQazaq, selectEnglish, selectRussian) = when (language) {
                "English" -> Triple(R.drawable.bottom_sheet_uncheck_icon, R.drawable.bottom_sheet_check_icon, R.drawable.bottom_sheet_uncheck_icon)
                "Қазақша" -> Triple(R.drawable.bottom_sheet_check_icon, R.drawable.bottom_sheet_uncheck_icon, R.drawable.bottom_sheet_uncheck_icon)
                "Русский" -> Triple(R.drawable.bottom_sheet_uncheck_icon, R.drawable.bottom_sheet_uncheck_icon, R.drawable.bottom_sheet_check_icon)
                else -> Triple(R.drawable.bottom_sheet_uncheck_icon, R.drawable.bottom_sheet_uncheck_icon, R.drawable.bottom_sheet_uncheck_icon)
            }
            imgIconBtnSelectQazaq.background = ContextCompat.getDrawable(requireContext(), selectQazaq)
            imgIconBtnSelectEnglish.background = ContextCompat.getDrawable(requireContext(), selectEnglish)
            imgIconBtnSelectRussian.background = ContextCompat.getDrawable(requireContext(), selectRussian)
        }
    }

    private fun navigateToProfile() {

//        findNavController().navigate(
//            R.id.profileFragment, arguments,
//            NavOptions.Builder().setPopUpTo(R.id.profileFragment, true).build()     //*****************
//        )
    }
}