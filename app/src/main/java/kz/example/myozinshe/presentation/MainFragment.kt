package kz.example.myozinshe.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
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
    private lateinit var navController: NavController

    private lateinit var mainMovieAdapter: MainMovieAdapter
    private lateinit var genreMainAdapter: GenreMainAdapter

    //    private lateinit var mainPageCategoryAdapterMainMovy: MainPageCategoryAdapter
//    private lateinit var mainPageCategoryAdapterMainTelihikaialar: MainPageCategoryAdapter
//    private lateinit var mainPageCategoryAdaptermainmainMult: MainPageCategoryAdapter
//    private lateinit var mainPageCategoryAdaptermainMultSerial: MainPageCategoryAdapter
//    private lateinit var mainPageCategoryAdapterMainSitcom: MainPageCategoryAdapter
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

        navController = findNavController()

        binding?.apply {
            textTVERROR?.visibility = View.GONE
            shimmerInMainFragment?.startShimmer()
            shimmerInMainFragmentAdd?.startShimmer()
            imgERRORView?.visibility = View.GONE
            constrainMainFragment?.visibility = View.GONE

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

        mainViewModel.movie(token)
        mainViewModel.getMovieOzinshe(token)
        mainViewModel.getMovieTelihikaialar(token)
        mainViewModel.getGenreRC(token)
        mainViewModel.getMovieTolyqMultfilm(token)
        mainViewModel.getMovieMultSerial(token)
        mainViewModel.getMovieSitcom(token)

    }

    private fun setObserve() {
        mainViewModel.mainMovie.observe(viewLifecycleOwner, ::handleMainMovie)
        mainViewModel.mainMovy.observe(viewLifecycleOwner, ::handleMainMovy)
        mainViewModel.mainTelihikaialar.observe(viewLifecycleOwner, ::handleMainTelihikaialar)
        mainViewModel.mainMult.observe(viewLifecycleOwner, ::handleMainMult)
        mainViewModel.mainMultSerial.observe(viewLifecycleOwner, ::handleMultMainSerial)
        mainViewModel.mainSitcom.observe(viewLifecycleOwner, ::handleMainSitcom)

        mainViewModel.mainGenre.observe(viewLifecycleOwner, ::handleMainGenre)
        mainViewModel.errorMessage.observe(viewLifecycleOwner, ::handleErrorMessage)


//        mainViewModel.mainMovie.observe(viewLifecycleOwner) {
//            mainMovieAdapter.submitList(it)
//
//            if (it.isNullOrEmpty()) {
//                with(binding) {
//                    textTVERROR?.visibility=View.VISIBLE
//                    imgERRORView?.visibility =View.VISIBLE
//                    nestedScroll?.visibility= View.GONE
//                }
//            } else {
//                with(binding) {
//                    shimmerInMainFragment?.stopShimmer()
//                    shimmerInMainFragment?.visibility =View.GONE
//                    constrainMainFragment?.visibility= View.VISIBLE
//                }
//            }
//        }
//
//        mainViewModel.mainMovy.observe(viewLifecycleOwner) {mainPageModel ->
//            val movyes = mainPageModel[0].movies
//            mainPageCategoryAdapterMainMovy.submitList(movyes)
//
//            if (!movyes.isNullOrEmpty()) {
//                with(binding) {
//                    shimmerInMainFragmentAdd?.stopShimmer()
//                    shimmerInMainFragmentAdd?.visibility = View.GONE
//
//                    btnCategoryAllMovie1?.visibility = View.VISIBLE
//                    btnCategoryAllMovie1?.setOnClickListener { t->
//                        TODO("Argument navigation")
//                    }
//
//                    textTvCategoryTitle1?.text = mainPageModel[0].categoryName
//                }
//            }
//        }
//
//        mainViewModel.mainTelihikaialar.observe(viewLifecycleOwner) {mainPageModel ->
//            val movyes = mainPageModel[1].movies
//            mainPageCategoryAdapterMainTelihikaialar.submitList(movyes)
//
//            if (!movyes.isNullOrEmpty()) {
//                with(binding) {
//                    btnCategoryAllMovie2?.visibility = View.VISIBLE
//                    btnCategoryAllMovie2?.setOnClickListener { t->
//                        TODO("Argument navigation")
//                    }
//
//                    textTvCategoryTitle2?.text = mainPageModel[1].categoryName
//                }
//            }
//        }
//
//        mainViewModel.mainMult.observe(viewLifecycleOwner) {mainPageModel ->
//            val movyes = mainPageModel[2].movies
//            mainPageCategoryAdaptermainmainMult.submitList(movyes)
//
//            if (!movyes.isNullOrEmpty()) {
//                with(binding) {
//                    btnCategoryAllMovie4?.visibility = View.VISIBLE
//                    btnCategoryAllMovie4?.setOnClickListener { t->
//                        TODO("Argument navigation")
//                    }
//
//                    textTvCategoryTitle4?.text = mainPageModel[2].categoryName
//                }
//            }
//        }
//
//        mainViewModel.mainMultSerial.observe(viewLifecycleOwner) {mainPageModel ->
//            val movyes = mainPageModel[3].movies
//            mainPageCategoryAdaptermainMultSerial.submitList(movyes)
//
//            if (!movyes.isNullOrEmpty()) {
//                with(binding) {
//                    btnCategoryAllMovie5?.visibility = View.VISIBLE
//                    btnCategoryAllMovie5?.setOnClickListener { t->
//                        TODO("Argument navigation")
//                    }
//
//                    textTvCategoryTitle5?.text = mainPageModel[3].categoryName
//                }
//            }
//        }
//
//        mainViewModel.mainSitcom.observe(viewLifecycleOwner) {mainPageModel ->
//            val movyes = mainPageModel[4].movies
//            mainPageCategoryAdapterMainSitcom.submitList(movyes)
//
//            if (!movyes.isNullOrEmpty()) {
//                with(binding) {
//                    btnCategoryAllMovie6?.visibility = View.VISIBLE
//                    btnCategoryAllMovie6?.setOnClickListener { t->
//                        TODO("Argument navigation")
//                    }
//
//                    textTvCategoryTitle6?.text = mainPageModel[4].categoryName
//                }
//            }
//        }
//
//        mainViewModel.mainGenre.observe(viewLifecycleOwner) {
//            genreMainAdapter.submitList(it)
//
//            if (!it.isNullOrEmpty()) {
//                with(binding) {
//                    btnCategoryAllMovie3?.visibility = View.VISIBLE
//                    textTvCategoryTitle3?.text = getString(R.string.ChooceGenre)
//                }
//            }
//        }
//
//        mainViewModel.errorMessage.observe(viewLifecycleOwner) {
//            with(binding) {
//                textTVERROR?.visibility = View.VISIBLE
//                imgERRORView?.visibility = View.VISIBLE
//                recyclerViewPlaceForMainMovies?.visibility = View.VISIBLE  // ПРОВРЕИТЬ когда бэк не подключен
//                nestedScroll?.visibility = View.GONE
//            }
//        }
    }

    private fun handleMainGenre(genreResponse: GenreResponse) {
        genreMainAdapter.submitList(genreResponse)

        if (!genreResponse.isNullOrEmpty()) {
            binding?.apply {
                btnCategoryAllMovie3?.visibility = View.VISIBLE
                textTvCategoryTitle3?.text = getString(R.string.ChooceGenre)
            }
        }
    }
    private fun handleMainSitcom(mainPageModel: MainPageModel) {
        val movyes = mainPageModel[4].movies
        val sitcomAdapter = categoryAdapters.getOrNull(4)
        sitcomAdapter?.submitList(movyes)

//        mainPageCategoryAdapterMainSitcom.submitList(movyes)

        if (!movyes.isNullOrEmpty()) {
            binding?.apply {
                btnCategoryAllMovie6?.visibility = View.VISIBLE
                btnCategoryAllMovie6?.setOnClickListener { t ->
                    TODO("Argument navigation")
                }
                textTvCategoryTitle6?.text = mainPageModel[4].categoryName
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
                btnCategoryAllMovie5?.visibility = View.VISIBLE
                btnCategoryAllMovie5?.setOnClickListener { t ->
                    TODO("Argument navigation")
                }
                textTvCategoryTitle5?.text = mainPageModel[3].categoryName
            }
        }
    }

    private fun handleMainMult(mainPageModel: MainPageModel) {
        val movyes = mainPageModel[2].movies
        val multMainAdapter = categoryAdapters.getOrNull(2)
        multMainAdapter?.submitList(movyes)

//        mainPageCategoryAdaptermainmainMult.submitList(movyes)

        if (!movyes.isNullOrEmpty()) {
            binding?.apply {
                btnCategoryAllMovie4?.visibility = View.VISIBLE
                btnCategoryAllMovie4?.setOnClickListener { t ->
                    TODO("Argument navigation")
                }
                textTvCategoryTitle4?.text = mainPageModel[2].categoryName
            }
        }
    }

    private fun handleMainTelihikaialar(mainPageModel: MainPageModel) {
        val movyes = mainPageModel[1].movies
        val mainTelehikailarAdapter = categoryAdapters.getOrNull(1)
        mainTelehikailarAdapter?.submitList(movyes)

//        mainPageCategoryAdapterMainTelihikaialar.submitList(movyes)

        if (!movyes.isNullOrEmpty()) {
            binding?.apply {
                btnCategoryAllMovie2?.visibility = View.VISIBLE
                btnCategoryAllMovie2?.setOnClickListener { t ->
                    TODO("Argument navigation")
                }
                textTvCategoryTitle2?.text = mainPageModel[1].categoryName
            }
        }
    }

    private fun handleMainMovy(mainPageModel: MainPageModel) {
        val movyes = mainPageModel[0].movies
        val mainMovyAdapter = categoryAdapters.getOrNull(0)
        mainMovyAdapter?.submitList(movyes)

//        mainPageCategoryAdapterMainMovy.submitList(movyes)

        if (!movyes.isNullOrEmpty()) {
            binding?.apply {
                shimmerInMainFragmentAdd?.stopShimmer()
                shimmerInMainFragmentAdd?.visibility = View.GONE

                btnCategoryAllMovie1?.visibility = View.VISIBLE
                btnCategoryAllMovie1?.setOnClickListener { t ->
                    TODO("Argument navigation")
                }

                textTvCategoryTitle1?.text = mainPageModel[0].categoryName
            }
        }
    }

    private fun handleMainMovie(movies: MoviesMain) {
        mainMovieAdapter.submitList(movies)

        if (movies.isNullOrEmpty()) {
            binding?.apply {
                textTVERROR?.visibility = View.VISIBLE
                imgERRORView?.visibility = View.VISIBLE
                nestedScroll?.visibility = View.GONE
            }
        } else {
            binding?.apply {
                shimmerInMainFragment?.stopShimmer()
                shimmerInMainFragment?.visibility = View.GONE
                constrainMainFragment?.visibility = View.VISIBLE
            }
        }
    }

    private fun handleErrorMessage(t: Int) {
        binding?.apply {
            textTVERROR?.visibility = View.VISIBLE         // может ошибку из VM брать ?
            imgERRORView?.visibility = View.VISIBLE
            recyclerViewPlaceForMainMovies?.visibility =
                View.VISIBLE  // ПРОВРЕИТЬ когда бэк не подключен
            nestedScroll?.visibility = View.GONE
        }
    }

    private fun setupUI() {
        mainMovieAdapter = MainMovieAdapter().apply {
            onTouchItem(object : MainItemClick {
                override fun onItemClick(item: MoviesMainItem) {
                    // Реализация действия при клике на элемент для mainPageCategoryAdapterMainMovy
                }
            })
        }

        genreMainAdapter = GenreMainAdapter().apply {
            onTouchItem(object : ItemOnClickChooseGenre {
                override fun onClickItem(item: GenreResponseItem) {
                    // Реализация действия при клике на элемент для mainPageCategoryAdapterMainMovy
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
                        // Реализация действия при клике на элемент для mainPageCategoryAdapterMainMovy
                    }
                })
            }
            setupRecyclerView(recyclerView, adapter)
            categoryAdapters.add(adapter)
        }
    }


//        mainPageCategoryAdapterMainMovy = MainPageCategoryAdapter().apply {
//            onTouchItem(object : ClickInterfaceMain {
//                override fun onItemClick(item: Movy) {
//                    // Реализация действия при клике на элемент для mainPageCategoryAdapterMainMovy
//                }
//            })
//        }
//
//        mainPageCategoryAdapterMainTelihikaialar = MainPageCategoryAdapter().apply {
//            onTouchItem(object : ClickInterfaceMain {
//                override fun onItemClick(item: Movy) {
//                    // Реализация действия при клике на элемент для mainPageCategoryAdapterMainTelihikaialar
//                }
//            })
//        }
//
//        mainPageCategoryAdaptermainmainMult = MainPageCategoryAdapter().apply {
//            onTouchItem(object : ClickInterfaceMain {
//                override fun onItemClick(item: Movy) {
//                    // Реализация действия при клике на элемент для mainPageCategoryAdaptermainmainMult
//                }
//            })
//        }
//
//        mainPageCategoryAdaptermainMultSerial = MainPageCategoryAdapter().apply {
//            onTouchItem(object : ClickInterfaceMain {
//                override fun onItemClick(item: Movy) {
//                    // Реализация действия при клике на элемент для mainPageCategoryAdaptermainMultSerial
//                }
//            })
//        }
//
//        mainPageCategoryAdapterMainSitcom = MainPageCategoryAdapter().apply {
//            onTouchItem(object : ClickInterfaceMain {
//                override fun onItemClick(item: Movy) {
//                    // Реализация действия при клике на элемент для mainPageCategoryAdapterMainSitcom
//                }
//            })
//        }
//
//        setupRecyclerView(binding?.rcViewCategoryOzinshe, mainPageCategoryAdapterMainMovy)
//        setupRecyclerView(binding?.rcViewCategoryMovie2, mainPageCategoryAdapterMainTelihikaialar)
//        setupRecyclerView(binding?.rcViewCategoryTolyqMult, mainPageCategoryAdaptermainmainMult)
//        setupRecyclerView(binding?.rcViewCategoryMultSerial, mainPageCategoryAdaptermainMultSerial)
//        setupRecyclerView(binding?.rcViewCategorySitcom, mainPageCategoryAdapterMainSitcom)
//        setupRecyclerView(binding?.rcViewCategoryCategories, genreMainAdapter)
//    }

    private fun setupRecyclerView(recyclerView: RecyclerView?, adapter: RecyclerView.Adapter<*>) {
        recyclerView?.apply {
            this.adapter = adapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
//            context?.let { ctx ->
//                addItemDecoration(CustomDividerItemDecoration(ctx.getDrawable(R.drawable.divider_1dp_grey)!!))
//            }
        }
    }

    override fun onResume() {
        super.onResume()
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
            setNavigationToolBar(false, true)
            additionalToolBarConfig(false, btnExitVisible = false, titleVisible = false, title = "")
        }
    }

    override fun onPause() {
        super.onPause()
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
            setNavigationToolBar(false, true)
            additionalToolBarConfig(false, btnExitVisible = false, titleVisible = false, title = "")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}