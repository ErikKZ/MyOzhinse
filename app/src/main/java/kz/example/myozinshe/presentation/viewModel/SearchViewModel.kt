package kz.example.myozinshe.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.domain.api.ApiInterface
import kz.example.myozinshe.domain.api.ServiceBuilder
import kz.example.myozinshe.domain.models.SearchResponseModelItem

class SearchViewModel: ViewModel() {
    private val response = ServiceBuilder.buildService(ApiInterface::class.java)

    private val _searchMovies = MutableLiveData<List<SearchResponseModelItem>>()
    val searchMovies: LiveData<List<SearchResponseModelItem>> = _searchMovies

    private val _errorCode = MutableLiveData<Int>()
    val errorCode: LiveData<Int> = _errorCode

    fun fetchSearchMovies(token: String, search: String) {
        viewModelScope.launch {
            runCatching {
                val credentials = "{}"
                val details = "{}"
                val principal = "{}"
                response.getSearchMovie("Bearer $token", credentials,details,principal,search) }
                .onSuccess {
                    _searchMovies.value = it
                }.onFailure {
                    _errorCode.value = R.string.error_category
                }
        }
    }


}