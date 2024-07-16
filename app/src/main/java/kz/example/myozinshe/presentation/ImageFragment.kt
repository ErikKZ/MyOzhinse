package kz.example.myozinshe.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kz.example.myozinshe.R
import kz.example.myozinshe.databinding.FragmentImageBinding
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.viewModel.ImageViewModel

class ImageFragment : Fragment() {
    private var binding: FragmentImageBinding? = null

    private val imageViewModel: ImageViewModel by viewModels()
    private lateinit var navController: NavController

    private val args: ImageFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        setObserve()
        imageViewModel.loadImg(args.link)
    }


    private fun setObserve() {
        imageViewModel.imageLink.observe(viewLifecycleOwner, :: handleImageLink)
    }

    private fun handleImageLink(link: String) {
        Glide.with(requireActivity())
            .load(link)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error_image)
            .into(binding?.imageView!!)
        binding?.imageView!!.maximumScale = 10F
    }

    private fun setupNavigationHost() {
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
            additionalToolBarConfig(
                toolbarVisible = true,
                btnBackVisible = true,
                btnExitVisible = false,
                title = "Сурет"
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}