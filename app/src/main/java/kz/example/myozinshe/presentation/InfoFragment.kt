package kz.example.myozinshe.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.ihermandev.formatwatcher.FormatWatcher
import com.google.android.material.datepicker.MaterialDatePicker
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentInfoBinding
import kz.example.myozinshe.domain.models.UserInfo
import kz.example.myozinshe.domain.models.UserInfoRequest
import kz.example.myozinshe.domain.utils.MonthChange
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.viewModel.InfoViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class InfoFragment : Fragment() {
    private var binding: FragmentInfoBinding? = null
    private val infoViewModel: InfoViewModel by viewModels()

    private var token = ""
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        token = PreferenceProvider(requireContext()).getToken()!!

        infoViewModel.getInfo(token)
        setObserve()
    }

    private fun setObserve() {
        infoViewModel.userInfo.observe(viewLifecycleOwner, :: handleUserInfo)
        infoViewModel.isUserUpdate.observe(viewLifecycleOwner, :: handleIsUserUpdate)
    }

    private fun handleIsUserUpdate(flagUpdate: Boolean) {
        Toast.makeText(requireContext(),getText(R.string.message_info_update),Toast.LENGTH_SHORT).show()
        navController.navigate(R.id.profileFragment)
    }
    private fun handleUserInfo(userInfo: UserInfo) {
        var birthDate = ""
        var name = ""
        var phoneNumber = ""

        binding?.run {
            textTvEmail.text = userInfo.user.email
            val formatter = FormatWatcher("+7 ### ###-##-##", '#')
            editTextPhoneNumber.addTextChangedListener(formatter)

            if (userInfo.phoneNumber == null || userInfo.phoneNumber == "") {
                editTextPhoneNumber.setHint("Телефон нөміріңізді енгізіңіз...")
                editTextPhoneNumber.setText(null)
            } else {
                editTextPhoneNumber.setText(userInfo.phoneNumber.toString())
                editTextPhoneNumber.setHint("Телефон нөміріңізді енгізіңіз...")
            }


            if (userInfo.name == null || userInfo.name == "") {
                editTextName.setHint("Аты-жөніңізді еңгізіңіз...")
            } else {
                editTextName.setText(userInfo.name)
            }

            if (userInfo.birthDate == null || userInfo.birthDate == "1900-01-18") {
                textTvDate.text = "Туылған күніңізді еңгізіңіз..."
            } else {
                textTvDate.text = userInfo.birthDate.toString()
            }
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateString = userInfo.birthDate
            if (dateString != null) {
                val date = dateFormat.parse(dateString.toString())
                val calendar = Calendar.getInstance()
                calendar.time = date
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val monthName = MonthChange().setMonthNames(month)
                val resultString = "$year ж. $day $monthName"
                binding?.textTvDate?.setText(resultString)
            } else {
                binding?.textTvDate?.setText(getString(R.string.SelectDate))
            }

            textTvDate.setOnClickListener {
                val builder = MaterialDatePicker.Builder.datePicker()
                    .setTheme(R.style.MaterialDatePickerTheme)
                    .setTitleText(R.string.SelectDate)

                val datePicker = builder.build()

                datePicker.show(childFragmentManager, "datePicker")

                datePicker.addOnPositiveButtonClickListener {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val dateString = dateFormat.format(Date(it))
                    val calendar = Calendar.getInstance()
                    val text = Date(it).toString()
                    calendar.time = Date(it)
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val montName = MonthChange().setMonthNames(month)


                    textTvDate.text = "$year ж. $day $montName"
                    birthDate = dateString ?: "1900-01-18"

                }
            }
            btnUpdateInformation.setOnClickListener {
                name = editTextName.text?.toString().takeIf { !it.isNullOrEmpty() } ?: ""
                phoneNumber = editTextPhoneNumber.text.toString()

                infoViewModel.updateInfo(token, UserInfoRequest(
                    birthDate = birthDate,
                    id = userInfo.id,
                    language = userInfo.language?.toString() ?: "Қазақша",
                    name = name,
                    phoneNumber = phoneNumber
                ))

            }
        }
    }


    private fun setupNavigationHost() {
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
            additionalToolBarConfig(
                toolbarVisible = true,
                btnBackVisible = true,
                btnExitVisible = false,
                title = getString(R.string.ZhekeDerekter)
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