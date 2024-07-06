package kz.example.myozinshe.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kz.example.myozinshe.R
import kz.example.myozinshe.databinding.FragmentWelcomeBinding
import kz.example.myozinshe.domain.WelcomePageInfoList
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.adapter.WelcomePageAdapter

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    lateinit var viewPage2: ViewPager2
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        setupViewPager()

        val clickListener = View.OnClickListener { transactionInLogIn() }  // на луюбую кнокпу на View
        binding.btnSkipWelcomeFragment.setOnClickListener (clickListener)
        binding.btnNextInLogInPage.setOnClickListener (clickListener)
    }

    private fun transactionInLogIn(){
        navController.navigate(R.id.loginFragment)       //**********
    }

    private fun setupViewPager(){
        val adapter = WelcomePageAdapter()
        adapter.submitList(WelcomePageInfoList.welcomePageSlidesInfoList)

        viewPage2 = binding.vpWelcome
        viewPage2.adapter = adapter

        viewPage2.registerOnPageChangeCallback(pager2CallBack)
        binding.dotsIndicator.attachTo(viewPage2)   //old -  setViewPager2(viewPage2)

    }


 private val pager2CallBack = object : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        val isLastPage = position == WelcomePageInfoList.welcomePageSlidesInfoList.size - 1
        binding.btnSkipWelcomeFragment.visibility = if (isLastPage) View.GONE else View.VISIBLE
        binding.btnNextInLogInPage.visibility = if (isLastPage) View.VISIBLE else View.GONE
    }

}


    override fun onResume() {
        super.onResume()
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
            setNavigationToolBar(false,true)
        }
    }

    override fun onPause() {
        super.onPause()
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
            setNavigationToolBar(false,true)
        }
    }


}