package kz.example.myozinshe.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentFavoriteBinding
import kz.example.myozinshe.domain.models.FavoriteModelItem
import kz.example.myozinshe.domain.utils.CustomDividerItemDecoration
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.adapter.FavoriteMovieAdapter
import kz.example.myozinshe.presentation.interfaces.FavoriteClickMovie
import kz.example.myozinshe.presentation.viewModel.FavoriteViewModel

class FavoriteFragment : Fragment() {
    private var binding: FragmentFavoriteBinding? = null
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var favoriteMovieAdapter: FavoriteMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        fetchFavoriteMovies()
        setupUI()
        setupBackNavigation()
        setObserve()

    }

    private fun fetchFavoriteMovies() {
        val token = PreferenceProvider(requireContext()).getToken()!!
        favoriteViewModel.fetchFavoriteMovies(token)
    }

    private fun setObserve() {
        favoriteViewModel.favoriteMovie.observe(viewLifecycleOwner, ::handleFavoriteMovie)
        favoriteViewModel.errorCode.observe(viewLifecycleOwner, ::handleErrorCode)
    }

    private fun handleFavoriteMovie(items: List<FavoriteModelItem>){
        if (!items.isNullOrEmpty()) {
            favoriteMovieAdapter.subminList(items)
        }
    }

    private fun handleErrorCode(codeError: Int){
        Toast.makeText(requireContext(),getString(codeError),Toast.LENGTH_SHORT).show()
    }

    private fun setupUI() {
        favoriteMovieAdapter = FavoriteMovieAdapter().apply {
            onTouchItem(object : FavoriteClickMovie {
                override fun onItemClick(item: FavoriteModelItem) {
                    val action = FavoriteFragmentDirections.actionFavoriteFragmentToAboutMovieFragment(item.id)
                    navController.navigate(action)
                }
            })
        }
        setupRecyclerView(binding?.rcFavoriteFragment, favoriteMovieAdapter )
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?,adapter: FavoriteMovieAdapter) {
        recyclerView?.apply {
            this.adapter = adapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
        }
    }

    private fun setupBackNavigation() {
//        requireActivity().onBackPressedDispatcher.addCallback(
//            viewLifecycleOwner,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    navController.navigate(R.id.mainFragment)
//                }
//            })
    }



    private fun setupNavigationHost() {
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
            additionalToolBarConfig(
                toolbarVisible = true,
                btnBackVisible = false,
                btnExitVisible = false,
                title = "Tizim"
            )
//            additionalToolBarConfig(true, btnExitVisible = false, titleVisible = true, title = "Tizim")

            onClickListener(R.id.mainFragment)
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
        binding =null
    }
}
