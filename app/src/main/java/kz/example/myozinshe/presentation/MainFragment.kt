package kz.example.myozinshe.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentMainBinding
import kz.example.myozinshe.domain.models.GenreResponse
import kz.example.myozinshe.domain.models.GenreResponseItem
import kz.example.myozinshe.domain.models.MainPageModel
import kz.example.myozinshe.domain.models.MoviesMain
import kz.example.myozinshe.domain.models.MoviesMainItem
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
    private var binding: FragmentMainBinding? = null

    private val mainViewModel: MainViewModel by viewModels()
    private val navController by lazy { findNavController() }


    private lateinit var mainMovieAdapter: MainMovieAdapter

    private lateinit var genreMainAdapter: GenreMainAdapter

    private val categoryAdapters = mutableListOf<MainPageCategoryAdapter>()
//  0- rcViewCategoryOzinshe,1-rcViewCategoryMovie2,2- rcViewCategoryTolyqMult,3- rcViewCategoryMultSerial,4- rcViewCategorySitcom


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.apply {
            textTVERROR.visibility = View.GONE
            shimmerInMainFragment.startShimmer()
            shimmerInMainFragmentAdd.startShimmer()
            imgERRORView.visibility = View.GONE
            constrainMainFragment.visibility = View.GONE

            listOf(
                btnCategoryAllMovie1,
                btnCategoryAllMovie2,
                btnCategoryAllMovie3,
                btnCategoryAllMovie4,
                btnCategoryAllMovie5,
                btnCategoryAllMovie6
            ).forEach { button ->
                button.visibility = View.INVISIBLE
            }
        }

        setupUI()
        setObserve()

        mainMoviePoster()
    }

    private fun mainMoviePoster() {
        val token = PreferenceProvider(requireContext()).getToken()!!

        with(mainViewModel) {
            movie(token)
            getMovieOzinshe(token)
            getMovieTelihikaialar(token)
            getGenreRC(token)
            getMovieTolyqMultfilm(token)
            getMovieMultSerial(token)
            getMovieSitcom(token)
        }
    }

    private fun setObserve() {
        with(mainViewModel) {
            mainMovie.observe(viewLifecycleOwner, ::handleMainMovie)
            mainMovy.observe(viewLifecycleOwner, ::handleMainMovy)
            mainTelihikaialar.observe(viewLifecycleOwner, ::handleMainTelihikaialar)
            mainMult.observe(viewLifecycleOwner, ::handleMainMult)
            mainMultSerial.observe(viewLifecycleOwner, ::handleMultMainSerial)
            mainSitcom.observe(viewLifecycleOwner, ::handleMainSitcom)

            mainGenre.observe(viewLifecycleOwner, ::handleMainGenre)
            errorMessage.observe(viewLifecycleOwner, ::handleErrorMessage)
        }
    }

    private fun handleMainGenre(genreResponse: GenreResponse) {
        if (!genreResponse.isNullOrEmpty()) {
            genreMainAdapter.submitList(genreResponse)
            binding?.apply {
                btnCategoryAllMovie3.visibility = View.VISIBLE
                textTvCategoryTitle3.text = getString(R.string.ChooceGenre)
            }
        }
    }
    private fun handleMainSitcom(mainPageModel: MainPageModel) {
        val movyes = mainPageModel[4].movies
        val sitcomAdapter = categoryAdapters.getOrNull(4)
        sitcomAdapter?.submitList(movyes)

        if (!movyes.isNullOrEmpty()) {
            binding?.apply {
                btnCategoryAllMovie6.visibility = View.VISIBLE
                btnCategoryAllMovie6.setOnClickListener { t ->
                    navToCategory(31)
                }
                textTvCategoryTitle6.text = mainPageModel[4].categoryName
            }
        }
    }

    private fun handleMultMainSerial(mainPageModel: MainPageModel) {
        val movyes = mainPageModel[3].movies
        val multSerialMainAdapter = categoryAdapters.getOrNull(3)
        multSerialMainAdapter?.submitList(movyes)
//        mainPageCategoryAdaptermainMultSerial.submitList(movyes)

        if (!movyes.isNullOrEmpty()) {
            binding?.apply {
                btnCategoryAllMovie5.visibility = View.VISIBLE
                btnCategoryAllMovie5.setOnClickListener { t ->
                    navToCategory(9)
                }
                textTvCategoryTitle5.text = mainPageModel[3].categoryName
            }
        }
    }

    private fun handleMainMult(mainPageModel: MainPageModel) {
        val movyes = mainPageModel[2].movies
        val multMainAdapter = categoryAdapters.getOrNull(2)
        multMainAdapter?.submitList(movyes)

        if (!movyes.isNullOrEmpty()) {
            binding?.apply {
                btnCategoryAllMovie4.visibility = View.VISIBLE
                btnCategoryAllMovie4.setOnClickListener { t ->
                    navToCategory(8)
                }
                textTvCategoryTitle4.text = mainPageModel[2].categoryName
            }
        }
    }

    private fun handleMainTelihikaialar(mainPageModel: MainPageModel) {
        val movyes = mainPageModel[1].movies
        val mainTelehikailarAdapter = categoryAdapters.getOrNull(1)
        mainTelehikailarAdapter?.submitList(movyes)


        if (!movyes.isNullOrEmpty()) {
            binding?.apply {
                btnCategoryAllMovie2.visibility = View.VISIBLE
                btnCategoryAllMovie2.setOnClickListener { t ->
                    navToCategory(5)
                }
                textTvCategoryTitle2.text = mainPageModel[1].categoryName
            }
        }
    }

    private fun navToCategory(categoryId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToCategoriesFragment(categoryId)
        findNavController().navigate(action)
    }

    private fun handleMainMovy(mainPageModel: MainPageModel) {
        val movyes = mainPageModel[0].movies
        val mainMovyAdapter = categoryAdapters.getOrNull(0)
        mainMovyAdapter?.submitList(movyes)

        if (!movyes.isNullOrEmpty()) {
            binding?.apply {
                shimmerInMainFragmentAdd.stopShimmer()
                shimmerInMainFragmentAdd.visibility = View.GONE

                btnCategoryAllMovie1.visibility = View.VISIBLE
                btnCategoryAllMovie1.setOnClickListener { t ->
                    navToCategory(1)
                }

                textTvCategoryTitle1.text = mainPageModel[0].categoryName
            }
        }
    }

    private fun handleMainMovie(movies: MoviesMain) {
        mainMovieAdapter.submitList(movies)

        if (movies.isNullOrEmpty()) {
            binding?.apply {
                textTVERROR.visibility = View.VISIBLE
                imgERRORView.visibility = View.VISIBLE
                nestedScroll.visibility = View.GONE
            }
        } else {
            binding?.apply {
                shimmerInMainFragment.stopShimmer()
                shimmerInMainFragment.visibility = View.GONE
                constrainMainFragment.visibility = View.VISIBLE
            }
        }
    }

    private fun handleErrorMessage(errorCode: Int) {
        val show = errorCode != 0
        binding?.apply {
            textTVERROR.visibility = if (show) View.VISIBLE else View.GONE   // может ошибку из VM брать ?
            imgERRORView.visibility = if (show) View.VISIBLE else View.GONE
            recyclerViewPlaceForMainMovies.visibility = if (show) View.VISIBLE else View.GONE // ПРОВРЕИТЬ когда бэк не подключен, может вообще убрать!
            nestedScroll.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    private fun setupUI() {
        mainMovieAdapter = MainMovieAdapter().apply {
            onTouchItem(object : MainItemClick {
                override fun onItemClick(item: MoviesMainItem) {
                    val action = MainFragmentDirections.actionMainFragmentToAboutMovieFragment(item.movie.id)
                    navController.navigate(action)
                }
            })
        }

        genreMainAdapter = GenreMainAdapter().apply {
            onTouchItem(object : ItemOnClickChooseGenre {
                override fun onClickItem(item: GenreResponseItem) {
                    val action = MainFragmentDirections.actionMainFragmentToCategoriesFragment(item.id)
                    navController.navigate(action)
                }
            })
        }

        setupRecyclerView(binding?.recyclerViewPlaceForMainMovies, mainMovieAdapter)
        setupRecyclerView(binding?.rcViewCategoryCategories, genreMainAdapter)
        setupCategoryAdapters()
    }

    private fun setupCategoryAdapters() {
        listOf(
            binding?.rcViewCategoryOzinshe,
            binding?.rcViewCategoryMovie2,
            binding?.rcViewCategoryTolyqMult,
            binding?.rcViewCategoryMultSerial,
            binding?.rcViewCategorySitcom
        ).forEachIndexed { index, recyclerView ->
            val adapter = MainPageCategoryAdapter().apply {
                onTouchItem(object : ClickInterfaceMain {
                    override fun onItemClick(item: Movy) {
                        val action = MainFragmentDirections.actionMainFragmentToAboutMovieFragment(item.id)
                        navController.navigate(action)
                    }
                })
            }
            setupRecyclerView(recyclerView, adapter)
            categoryAdapters.add(adapter)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?, adapter: RecyclerView.Adapter<*>) {
        recyclerView?.apply {
            this.adapter = adapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
//            context?.let { ctx ->
//                addItemDecoration(CustomDividerItemDecoration(ctx.getDrawable(R.drawable.divider_1dp_grey)!!))
//            }
        }
    }

    private fun setupNavigationHost() {
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}