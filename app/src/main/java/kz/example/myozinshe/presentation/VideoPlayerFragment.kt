package kz.example.myozinshe.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import kz.example.myozinshe.R
import kz.example.myozinshe.databinding.FragmentVideoPlayerBinding
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.viewModel.VideoPlayerViewModel

class VideoPlayerFragment : Fragment() {
    private var binding: FragmentVideoPlayerBinding? = null
    private val videoPlayerViewModel: VideoPlayerViewModel by viewModels()

    private val args: VideoPlayerFragmentArgs by navArgs()

    private val navController by lazy { findNavController() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        videoPlayerViewModel.updateMovieLink(args.link)

//        setupUI()
        setObserve()

    }

    private fun setObserve() {
        videoPlayerViewModel.link.observe(viewLifecycleOwner, ::handleSeriasMovies)
        videoPlayerViewModel.playbackPozition.observe(viewLifecycleOwner, ::handlePlaybackPozition)
    }

    fun handlePlaybackPozition(positionVideo: Long) {

    }

    fun handleSeriasMovies(link: String) {
        playerYouTube(link)
    }

    fun playerYouTube(link: String) {
        val youTubePlayerView = binding?.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView!!)

        val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {

            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)

//                val defaultPlayerUiController = DefaultPlayerUiController(
//                    youTubePlayerView!!, youTubePlayer
//                )
//
//                defaultPlayerUiController.rootView.findViewById<View>(com.pierfrancescosoffritti.androidyoutubeplayer.R.id.drop_shadow_top)  // com.pierfrancescosoffritti.androidyoutubeplayer.R.id.drop_shadow_top
//                    .apply {
//                        setBackgroundResource(R.drawable.button_exit_player)
//                        setPadding(24, 24, 24, 24)
//                        updateLayoutParams {
//                            width = 170
//                            height = 170
//                        }
//                        setOnClickListener {
//                            navController.navigate(R.id.seriesFragment)
//                        }
//                    }
//                youTubePlayerView!!.setCustomPlayerUi(defaultPlayerUiController.rootView)
//                defaultPlayerUiController.showYouTubeButton(false)
//                defaultPlayerUiController.showFullscreenButton(false)
//                defaultPlayerUiController.rootView.findViewById<com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBar>(
//                    0  //com.pierfrancescosoffritti.androidyoutubeplayer.R.id.youtube_player_seekbar
//                ).setColor(resources.getColor(R.color.primary_500, null))
//

                val defaultPlayerUiController =
                    DefaultPlayerUiController(youTubePlayerView, youTubePlayer).apply {
                        showYouTubeButton(false)
                        showVideoTitle(show = true)
                        showFullscreenButton(show = false)


                        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.button_exit_player)
                        drawable?.let {
                            setCustomAction1(it) {
                                val action =  VideoPlayerFragmentDirections.actionVideoPlayerFragmentToSeriesFragment(args.movieId)
                                navController.navigate(action)
//                                navController.navigate(R.id.mainFragment)
                            }
                        }
                    }


                // Применить кастомный UI:
                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                youTubePlayer.loadOrCueVideo(
                    lifecycle, link, 0f
                )
            }
        }
        val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()

        youTubePlayerView.initialize(listener, options)
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
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onPause() {
        super.onPause()
        setupNavigationHost()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = null
    }

}