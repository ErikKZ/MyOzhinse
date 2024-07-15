package kz.example.myozinshe.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.domain.api.ApiInterface
import kz.example.myozinshe.domain.api.ServiceBuilder
import kz.example.myozinshe.domain.models.FavoriteModelItem

class FavoriteViewModel: ViewModel() {
    private val response = ServiceBuilder.buildService(ApiInterface::class.java)

    private val _favoriteMovie = MutableLiveData<List<FavoriteModelItem>>()
    val favoriteMovie : LiveData<List<FavoriteModelItem>> = _favoriteMovie

    private val _errorCode = MutableLiveData<Int>()
    val errorCode : LiveData<Int> = _errorCode

    fun fetchFavoriteMovies(token :String) {
        viewModelScope.launch {
            runCatching { response.getFavoriteMovies("Bearer $token")  }
                .onSuccess {items ->
                    if (!items.isNullOrEmpty()) {
                        _favoriteMovie.value = items
                    }
                }.onFailure {
                    _errorCode.value = R.string.error_movie_response
                }
        }
    }
}