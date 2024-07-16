package kz.example.myozinshe.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kz.example.myozinshe.R
import kz.example.myozinshe.data.preference.PreferenceProvider
import kz.example.myozinshe.databinding.FragmentSearchBinding
import kz.example.myozinshe.domain.models.SearchResponseModelItem
import kz.example.myozinshe.domain.utils.CustomDividerItemDecoration
import kz.example.myozinshe.domain.utils.provideNavigationHost
import kz.example.myozinshe.presentation.adapter.SearchMovieAdapter
import kz.example.myozinshe.presentation.interfaces.SearchMovieClick
import kz.example.myozinshe.presentation.viewModel.SearchViewModel

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private lateinit var navController: NavController
    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var searchMovieAdapter: SearchMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        setupUI()
        setObserve()
        btnReaction()

    }

    private fun btnReaction() {
        val token = PreferenceProvider(requireContext()).getToken()!!

        binding?.apply {
            btnOzinshe1.setOnClickListener {
                navigation(1, "ÖZINŞE-де танымал")
            }
            btnTelehikaya5.setOnClickListener {
                navigation(5, "Телехикаялар")
            }
            btnTolyqMultFilm8.setOnClickListener {
                navigation(8, "Толықметрлі мультфильмдер")
            }
            btnMult9.setOnClickListener {
                navigation(9, "Мультсериалдар")
            }
            btnSitkomdar31.setOnClickListener {
                navigation(31, "Ситкомдар")
            }

            editTextSearchMovie?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().isNullOrEmpty()) {
                        btnRefreshEditText?.visibility = View.GONE
                    } else {
                        searchViewModel.fetchSearchMovies(token, s.toString())

                        btnRefreshEditText?.visibility = View.VISIBLE
                        btnRefreshEditText?.setOnClickListener {
                            binding?.editTextSearchMovie?.text?.clear()
                            binding?.sanattarConstraintLayout?.visibility = View.VISIBLE
                            binding?.searchResultConstraintLayout?.visibility = View.GONE
                        }
                    }
                }
            })
        }

    }

    private fun navigation(categoryId: Int, nameCategory: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToCategoriesFragment(categoryId)
        navController.navigate(action)
    }

    private fun setObserve() {
        searchViewModel.searchMovies.observe(viewLifecycleOwner, ::hundleSearchMovies)
        searchViewModel.errorCode.observe(viewLifecycleOwner, ::hundleErrorCode)
    }

    private fun hundleErrorCode(codeError: Int) {
        Toast.makeText(requireContext(), getString(codeError), Toast.LENGTH_SHORT).show()
    }

    private fun hundleSearchMovies(searchResponseModelList: List<SearchResponseModelItem>) {
        if (searchResponseModelList.isNullOrEmpty()) {
            binding?.sanattarConstraintLayout?.visibility = View.VISIBLE
            binding?.searchResultConstraintLayout?.visibility = View.GONE
        } else {
            searchMovieAdapter?.submitList(searchResponseModelList)
            binding?.sanattarConstraintLayout?.visibility = View.GONE
            binding?.searchResultConstraintLayout?.visibility = View.VISIBLE
        }
    }

    private fun setupUI() {
        searchMovieAdapter = SearchMovieAdapter().apply {
            onTouchItem(object : SearchMovieClick {
                override fun onItemClick(item: SearchResponseModelItem) {
                    val action =
                        SearchFragmentDirections.actionSearchFragmentToAboutMovieFragment(item.id)
                    navController.navigate(action)
                }
            })
        }

        setupRecyclerView(binding?.rcViewSearchFragment, searchMovieAdapter)

        binding?.sanattarConstraintLayout?.visibility = View.VISIBLE
        binding?.searchResultConstraintLayout?.visibility = View.GONE
        binding?.btnRefreshEditText?.visibility = View.GONE
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?, adapter: SearchMovieAdapter) {
        recyclerView?.apply {
            this.adapter = adapter
            addItemDecoration(CustomDividerItemDecoration(requireContext().getDrawable(R.drawable.divider_1dp_grey)!!))
        }
    }

    private fun setupNavigationHost() {
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
            additionalToolBarConfig(
                toolbarVisible = true,
                btnBackVisible = true,
                btnExitVisible = false,
                title = getString(R.string.Izdeu)
            )
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
        binding = null
    }
}