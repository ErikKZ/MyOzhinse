package kz.example.myozinshe.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentCategoriesBinding
import kz.example.myozinshe.domain.models.Video
import kz.example.myozinshe.domain.utils.CustomDividerItemDecoration
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.adapter.SeriesAdapter
import kz.example.myozinshe.presentation.interfaces.SeriesClick
import kz.example.myozinshe.presentation.viewModel.SeriesViewModel

class SeriesFragment : Fragment() {
    private var binding: FragmentCategoriesBinding? = null
    private val seriesViewModel: SeriesViewModel by viewModels()
    private val navController by lazy { findNavController() }


    private lateinit var seriesAdapter: SeriesAdapter
    private val args: SeriesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        fetchSeries()
        setObserve()
    }

    private fun fetchSeries() {
        val token = PreferenceProvider(requireContext()).getToken()!!
        val id = args.movieId
        seriesViewModel.fetchSeries(token,id)
    }

    private fun setObserve() {
        seriesViewModel.seriesMovies.observe(viewLifecycleOwner,::handleSeriesMovies)
    }
    private fun handleSeriesMovies(items: List<Video>) {
        seriesAdapter.submitList(items)
    }

    private fun setupUI() {
        seriesAdapter = SeriesAdapter().apply {
            onTouchItem(object : SeriesClick{
                override fun onItemClick(item: Video) {
                    val action = SeriesFragmentDirections.actionSeriesFragmentToVideoPlayerFragment(item.link, args.movieId )
                    navController.navigate(action)
                }
            })
        }
        setupRecyclerView(binding?.rcCategoryFragment,seriesAdapter)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?,adapter: SeriesAdapter) {
        recyclerView?.apply {
            this.adapter = adapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
        }
    }

    private fun setupNavigationHost() {
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
            additionalToolBarConfig(
                toolbarVisible = true,
                btnBackVisible = true,
                btnExitVisible = false,
                title = "Бөлімдер"

            )
            val action = SeriesFragmentDirections.actionSeriesFragmentToAboutMovieFragment(args.movieId)
            onClickListener(R.id.aboutMovieFragment, args.movieId,action)
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