package kz.example.myozinshe.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kz.example.myozinshe.R
import kz.example.myozinshe.databinding.FragmentSimilarBinding
import kz.example.myozinshe.domain.utils.provideNavigationHost

class SimilarFragment : Fragment() {
    private var binding: FragmentSimilarBinding? =null

    private val args: SimilarFragmentArgs by navArgs()
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSimilarBinding.inflate(inflater,container,false)
        return binding?.root
    }

    private fun setupNavigationHost() {
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
            additionalToolBarConfig(
                toolbarVisible = true,
                btnBackVisible = true,
                btnExitVisible = false,
                title = "Ұқсас телехикаялар"
            )
            val action = SimilarFragmentDirections.actionSimilarFragmentToAboutMovieFragment(args.movieId )
            onClickListener(R.id.aboutMovieFragment, args.movieId,action)

//            onClickListener(R.id.aboutMovieFragment, args.movieId)
            Log.d("AAAAA", args.movieId.toString())

//            onClickListener(R.id.aboutMovieFragment)
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