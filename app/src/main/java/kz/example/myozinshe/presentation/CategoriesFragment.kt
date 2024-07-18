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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentCategoriesBinding
import kz.example.myozinshe.domain.models.CategoryMovieResponse
import kz.example.myozinshe.domain.models.Content
import kz.example.myozinshe.domain.utils.CustomDividerItemDecoration
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.adapter.CategoryMovieAdapter
import kz.example.myozinshe.presentation.interfaces.CategoryClickMovie
import kz.example.myozinshe.presentation.viewModel.CategoriesViewModel

class CategoriesFragment : Fragment() {
    private var binding: FragmentCategoriesBinding? = null
    private val navController by lazy { findNavController() }
    private lateinit var categoryAdapter: CategoryMovieAdapter
    private val categoriesViewModel: CategoriesViewModel by viewModels()
    private val args: CategoriesFragmentArgs by navArgs()

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
        fetchCategoryMovies()
        setupBackNavigation()
        setObserve()

    }

    private fun setupBackNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigate(R.id.mainFragment)
                }
            }
        )
    }

    private fun fetchCategoryMovies(){
        val categoryId = args.categoryId
        val token = PreferenceProvider(requireContext()).getToken()!!

        categoriesViewModel.categoryMovie(token, categoryId)
        binding?.shimmerInMainFragment?.startShimmer()
        binding?.rcCategoryFragment?.visibility= View.GONE
    }

    private fun setObserve() {
        categoriesViewModel.categoryMovie.observe(viewLifecycleOwner,::handleCatehoryMovie)
        categoriesViewModel.errorCode.observe(viewLifecycleOwner, :: handleErrorCode)
    }

    private fun handleErrorCode(errorCode: Int){
        Toast.makeText(requireContext(), getString(errorCode), Toast.LENGTH_SHORT).show()
    }

    private fun handleCatehoryMovie(categoryMovieResponse: CategoryMovieResponse){
            categoryAdapter.submitList(categoryMovieResponse.content)
            updateUI(categoryMovieResponse)
    }

    private fun updateUI(item: CategoryMovieResponse) {
        binding?.apply {
            shimmerInMainFragment?.visibility = View.GONE
            rcCategoryFragment?.visibility = View.VISIBLE
        }
//        binding?.apply {
//            shimmerInMainFragment?.stopShimmer()
//            if (item == null) {
//                shimmerInMainFragment?.visibility = View.VISIBLE
//                rcCategoryFragment?.visibility = View.GONE
//            } else {
//                shimmerInMainFragment?.visibility = View.GONE
//                rcCategoryFragment?.visibility = View.VISIBLE
//            }
//        }
    }

    private fun setupUI() {
        categoryAdapter = CategoryMovieAdapter().apply {
            onTouchItem(object : CategoryClickMovie {
                override fun onItemClick(item: Content) {
                    val action = CategoriesFragmentDirections.actionCategoriesFragmentToAboutMovieFragment(item.id)
                    navController.navigate(action)
                }
            })
        }
        setupRecyclerView(binding?.rcCategoryFragment, categoryAdapter)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?,adapter: RecyclerView.Adapter<*> ) {
        recyclerView?.apply {
            this.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.divider_1dp_grey)!!)
            addItemDecoration(dividerItemDecoration)

//            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
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

            onClickListener(R.id.mainFragment)   //******************
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