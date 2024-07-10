package kz.example.myozinshe.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentMainBinding
import kz.example.myozinshe.domain.models.GenreResponseItem
import kz.example.myozinshe.domain.models.MainPageModelItem
import kz.example.myozinshe.domain.models.Movy
import kz.example.myozinshe.domain.utils.CustomDividerItemDecoration
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.adapter.GenreMainAdapter
import kz.example.myozinshe.presentation.adapter.MainMovieAdapter
import kz.example.myozinshe.presentation.adapter.MainPageCategoryAdapter
import kz.example.myozinshe.presentation.interfaces.ClickInterfaceMain
import kz.example.myozinshe.presentation.interfaces.ItemOnClickChooseGenre
import kz.example.myozinshe.presentation.interfaces.MainItemClick
import kz.example.myozinshe.presentation.viewModel.MainViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    private lateinit var mainMovieAdapter: MainMovieAdapter
    private lateinit var mainPageCategoryAdapter: MainPageCategoryAdapter
    private lateinit var genreMainAdapter: GenreMainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        with(binding) {
            textTVERROR?.visibility = View.GONE
            shimmerInMainFragment?.startShimmer()
            shimmerInMainFragmentAdd?.startShimmer()
            imgERRORView?.visibility = View.GONE
            constrainMainFragment?.visibility= View.GONE

            run {
                btnCategoryAllMovie1.visibility = View.INVISIBLE
                btnCategoryAllMovie2.visibility = View.INVISIBLE
                btnCategoryAllMovie3.visibility = View.INVISIBLE
                btnCategoryAllMovie4.visibility = View.INVISIBLE
                btnCategoryAllMovie5.visibility = View.INVISIBLE
                btnCategoryAllMovie6.visibility = View.INVISIBLE
            }
        }

        val token = PreferenceProvider(requireContext()).getToken()!!

        setupUI()
        observe()

        mainMoviePoster(token)
    }

    private fun mainMoviePoster(token: String) {

        mainViewModel.movie(token)
        mainViewModel.getMovieOzinshe(token)
        mainViewModel.getMovieTelihikaialar(token)
        mainViewModel.getGenreRC(token)
        mainViewModel.getMovieTolyqMultfilm(token)
        mainViewModel.getMovieMultSerial(token)
        mainViewModel.getMovieSitcom(token)

    }
    private fun observe() {
        TODO("Not yet implemented")
    }

    private fun setupUI() {
        mainMovieAdapter = MainMovieAdapter()

        with(binding.recyclerViewPlaceForMainMovies) {
            adapter = mainMovieAdapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
        }

        mainMovieAdapter.onTouchItem(object : MainItemClick {
            override fun onItemClick(item: MainPageModelItem) {
//                val action = FragmentMainDirections.actionFragmentMainToAboutMovieFragment(item.id)
//                findNavController().navigate(action)
            }
        })

        mainPageCategoryAdapter = MainPageCategoryAdapter()

        with(binding.rcViewCategoryOzinshe) {
            adapter = mainPageCategoryAdapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
        }

        with(binding.rcViewCategoryMovie2) {
            adapter = mainPageCategoryAdapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
        }

        with(binding.rcViewCategoryTolyqMult) {
            adapter = mainPageCategoryAdapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
        }

        with(binding.rcViewCategoryMultSerial) {
            adapter = mainPageCategoryAdapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
        }

        with(binding.rcViewCategorySitcom) {
            adapter = mainPageCategoryAdapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
        }

        mainPageCategoryAdapter.onTouchItem(object : ClickInterfaceMain {
            override fun onItemClick(item: Movy) {
                TODO("Not yet implemented")
            }
        })

        genreMainAdapter = GenreMainAdapter()

        with(binding.rcViewCategoryCategories) {
            adapter = mainPageCategoryAdapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
        }

        genreMainAdapter.onTouchItem(object : ItemOnClickChooseGenre {
            override fun onClickItem(item: GenreResponseItem) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
            setNavigationToolBar(false,true)
            additionalToolBarConfig(false, btnExitVisible = false, titleVisible = false, title = "")
        }
    }

    override fun onPause() {
        super.onPause()
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
            setNavigationToolBar(false,true)
            additionalToolBarConfig(false, btnExitVisible = false, titleVisible = false, title = "")
        }
    }
}