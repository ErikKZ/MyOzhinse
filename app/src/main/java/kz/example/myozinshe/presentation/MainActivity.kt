package kz.example.myozinshe.presentation

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.ActivityMainBinding
import kz.example.myozinshe.databinding.ToolbarUpnavBinding
import kz.example.myozinshe.domain.utils.NavigationHostProvider
import java.util.Locale

class MainActivity : AppCompatActivity(), NavigationHostProvider {

    private lateinit var binding: ActivityMainBinding

    //    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isDarkModeEnabled = PreferenceProvider(this).getDarkModeEnabledState()

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val language = PreferenceProvider(this).getLanguage()

        if (language == "English" || language == "Қазақша" || language == "Русский") {
            systemLanguage(language)
        } else {
            systemLanguage("Қазақша")
        }

        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationBarMainActivity?.setupWithNavController(navController)
        binding?.bottomNavigationBarMainActivity?.apply { itemIconTintList = null }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        window.statusBarColor = Color.TRANSPARENT

    }

    private fun systemLanguage(language: String) {
        val locale = when (language) {
            "English" -> Locale("en")
            "Қазақша" -> Locale("kk")
            "Русский" -> Locale("ru")
            else -> Locale("kk")
        }

        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.createConfigurationContext(config)
    }

    override fun setNavigationVisibility(visible: Boolean) {
        binding.bottomNavigationBarMainActivity?.isVisible = visible
    }

    override fun additionalToolBarConfig(
        toolbarVisible: Boolean,
        btnBackVisible: Boolean,
        btnExitVisible: Boolean,
        title: String
    ) {
        val toolbarUpnavBinding = binding?.linerLayoutToolbar as ToolbarUpnavBinding
        val toolbar = toolbarUpnavBinding.toolbarInfo
        val btnBack = toolbarUpnavBinding.btnBack
        val btnExit = toolbarUpnavBinding.btnExit
        val titleTextView = toolbarUpnavBinding.titleToolbar

        toolbar.isVisible = toolbarVisible
        toolbar.isGone = !toolbarVisible
        btnBack.isVisible = btnBackVisible
        btnExit.isVisible = btnExitVisible
        titleTextView.isVisible = title.isNotEmpty()
        titleTextView.text = title
    }

//    override fun setNavigationToolBar(visible: Boolean, isGone: Boolean) {
//        val toolbarUpnavBinding = binding?.linerLayoutToolbar as ToolbarUpnavBinding
//        val toolbar = toolbarUpnavBinding.toolbarInfo
//
//        toolbar.isVisible = visible
//        toolbar.isGone = isGone
//    }
//
//    override fun additionalToolBarConfig(
//        btnBackVisible: Boolean,
//        btnExitVisible: Boolean,
//        titleVisible: Boolean,
//        title: String
//    ) {
//        val toolbarUpnavBinding = binding?.linerLayoutToolbar as ToolbarUpnavBinding
//        val btnBack = toolbarUpnavBinding.btnBack
//        val btnExit = toolbarUpnavBinding.btnExit
//        val titleTextView = toolbarUpnavBinding.titleToolbar
//
//        btnBack.isVisible = btnBackVisible
//        btnExit.isVisible = btnExitVisible
//        titleTextView.isVisible = titleVisible
//        titleTextView.text = title
//    }

    override fun onClickListener(id: Int) {
        val toolbarUpnavBinding = binding?.linerLayoutToolbar as ToolbarUpnavBinding
        toolbarUpnavBinding.btnBack.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(id)
        }
    }

    override fun showBottomSheetExit(unit: BottomSheetDialogFragment) {
        val toolbarUpnavBinding = binding?.linerLayoutToolbar as ToolbarUpnavBinding
        toolbarUpnavBinding.btnExit.setOnClickListener {
            unit.show(supportFragmentManager, "")
        }
    }
}