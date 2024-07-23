package kz.example.myozinshe.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentSplashBinding
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kotlin.time.Duration.Companion.seconds


class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = PreferenceProvider(requireContext()).getToken()
        lifecycleScope.launch {
            delay(1.seconds)
            navigateBasedOnToken(token)
        }
    }



    private fun navigateBasedOnToken(token: String?) {
        if (token == "without_token") {
            findNavController().navigate(R.id.welcomeFragment)
        } else if (!token.isNullOrEmpty() ) {
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        } else {
            findNavController().navigate(R.id.welcomeFragment)
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