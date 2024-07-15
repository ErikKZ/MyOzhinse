package kz.example.myozinshe.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentAboutMovieBinding
import kz.example.myozinshe.domain.models.Genre
import kz.example.myozinshe.domain.models.MovieInfoResponse
import kz.example.myozinshe.domain.models.Screenshot
import kz.example.myozinshe.domain.utils.CustomDividerItemDecoration
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.adapter.ImageAdapter
import kz.example.myozinshe.presentation.interfaces.ImageClick
import kz.example.myozinshe.presentation.viewModel.AboutMovieViewModel

class AboutMovieFragment : Fragment() {
    private var binding: FragmentAboutMovieBinding? = null

    private val aboutMovieViewModel: AboutMovieViewModel by viewModels()
    private var token = ""

    private val args: AboutMovieFragmentArgs by navArgs()

    private lateinit var navController: NavController

    private lateinit var imageAdapter: ImageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        token = PreferenceProvider(requireContext()).getToken()!!
        aboutMovieViewModel.fetchMovie(token, args.movieId)

        binding?.btnBack?.setOnClickListener {
            navController.navigate(R.id.mainFragment) //            navController.navigateUp()
        }

        setupUI()
        setupBackNavigation()       // проверить нажати кнопки назад
        setObserve()
    }

    private fun setObserve() {
        aboutMovieViewModel.selectMovie.observe(viewLifecycleOwner, ::handleSelectMovie)
        aboutMovieViewModel.favoriteState.observe(viewLifecycleOwner, ::handleFavoriteState)
        aboutMovieViewModel.errorCode.observe(viewLifecycleOwner, ::handleErrorCode)
    }

    private fun handleFavoriteState(isFavorite: Boolean) {
        val buttonFavorite = binding?.btnFavoriteMovie
        val focusBackground =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_button_favorite_focused)
        val unFocusBackground =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_button_favorite)

        buttonFavorite?.background = if (isFavorite) focusBackground else unFocusBackground
    }

    private fun handleErrorCode(codeError: Int) {
        Toast.makeText(requireContext(), getString(codeError), Toast.LENGTH_SHORT).show()
    }

    private fun handleSelectMovie(movie: MovieInfoResponse) {
        imageAdapter?.submitList(movie.screenshots)

        handleMovieDetails(movie)
        setupPlayButton(movie)
        updateFavoriteButton(movie)

        binding?.shimmerLayout?.apply {
            stopShimmer()
            visibility = View.GONE
        }

        binding?.btnSimilarMovieMore?.setOnClickListener {
            findNavController().navigate(R.id.aboutMovieFragment) //*********** to_similarFragment
        }
    }


    private fun handleMovieDetails(movie: MovieInfoResponse) {
        binding?.run {
            fullInfoLayout?.visibility = View.VISIBLE

            textTvTittleMovie.text = movie.name
            Glide.with(requireContext()).load(movie.poster.link).into(imageView3)

            textTvDescription.text = movie.description
            setupMoreDescriptionButton()

            textTvBolimder.text = "${movie.seasonCount} сезон, ${movie.seriesCount} серия"
            textTvBolimder.visibility = if (movie.video == null) View.VISIBLE else View.GONE
            textBolimder.visibility = if (movie.video == null) View.VISIBLE else View.GONE
            btnNextAllMovie.visibility = if (movie.video == null) View.VISIBLE else View.GONE

            textTvAdditionalInfoYear.text = movie.year.toString()
            textTvGenres.text = movie.genres.joinToString(separator = " • ") { it.name }

            textTvDirector.text = movie.director
            textTvProducer.text = movie.producer

            btnFavoriteMovie?.background = if (movie.favorite) {
                resources.getDrawable(R.drawable.ic_button_favorite_focused, null)
            } else {
                resources.getDrawable(R.drawable.ic_button_favorite, null)
            }
        }
    }

    private fun setupPlayButton(movie: MovieInfoResponse) {
        binding?.btnPlayMovie?.setOnClickListener {
            val action = if (movie.video == null) {
                AboutMovieFragmentDirections.actionAboutMovieFragmentToMainFragment(1)  //*********
            } else {
                AboutMovieFragmentDirections.actionAboutMovieFragmentToMainFragment(1)  //******
            }
            findNavController().navigate(action)
        }
    }

    private fun updateFavoriteButton(movie: MovieInfoResponse) {
        binding?.btnFavoriteMovie?.setOnClickListener {
            lifecycleScope.launch {
                aboutMovieViewModel.favoriteSelect(token!!, movie.id, movie.favorite)
            }
        }
    }

    private fun setupMoreDescriptionButton() {
        binding?.btnMoreDescription?.let { button ->
            button.setOnClickListener {
                binding?.run {
                    textTvDescription.maxLines = if (textTvDescription.maxLines == 100) 7 else 100
                    fadingEdgeLayoutDescription.setFadeSizes(0, 0, if (textTvDescription.maxLines == 100) 0 else 120, 0)
                    btnMoreDescription.text = if (textTvDescription.maxLines == 100) "Жасыру" else "Толығырақ"
                }
            }
        }
    }
    private fun setupBackNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigate(R.id.mainFragment)
                }
            })
    }

    private fun setupUI() {
        imageAdapter = ImageAdapter().apply {
            onTouchItem(object : ImageClick {
                override fun onClickItem(item: Screenshot) {
                    TODO("Not yet implemented")
                }
            })
        }
        setupRecyclerView(binding?.rcViewScreenShots, imageAdapter)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?, adapter: RecyclerView.Adapter<*>) {
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
                title = "Сипаттама"
            )
//            additionalToolBarConfig(true, btnExitVisible = false, titleVisible = true, title = "Сипаттама")

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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}