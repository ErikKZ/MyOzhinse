package kz.example.myozinshe.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.domain.api.ApiInterface
import kz.example.myozinshe.domain.api.ServiceBuilder
import kz.example.myozinshe.domain.models.MovieIdModel
import kz.example.myozinshe.domain.models.MovieInfoResponse

class AboutMovieViewModel : ViewModel() {
    val response = ServiceBuilder.buildService(ApiInterface::class.java)

    private val _selectMovie = MutableLiveData<MovieInfoResponse>()
    val selectMovie: LiveData<MovieInfoResponse> = _selectMovie

    private val _favoriteState = MutableLiveData<Boolean>()
    val favoriteState: LiveData<Boolean> = _favoriteState


    private val _errorCode = MutableLiveData<Int>()
    val errorCode: LiveData<Int> = _errorCode

    fun fetchMovie(token: String, id: Int) {
        viewModelScope.launch {
            runCatching { response.movieWithId("Bearer $token", id) }
                .onSuccess { movie ->
                    _selectMovie.value = movie
                }.onFailure {
                    _errorCode.value = R.string.error_movie_response
                }
        }
    }

    fun favoriteSelect(token: String, id: Int, state: Boolean) {
        viewModelScope.launch {
            if (!state) {
                runCatching {
                    response.addToFavorite("Bearer $token", MovieIdModel(id))
                }.onSuccess {
                    _favoriteState.value = true
                    fetchMovie(token, it.movieId)
                }.onFailure {
                    _errorCode.value = R.string.error_favorite_response
                }.getOrNull()
            } else {
                runCatching {
                    response.deleteAtFavorite("Bearer $token", MovieIdModel(id))
                }.onSuccess {
                    _favoriteState.value = false
                    fetchMovie(token, id)
                }.onFailure {t ->
                    _errorCode.value = R.string.error_favoriteFalse_response
                }
            }
        }
    }

    fun retrofitMovieId(token: String, id: Int) {
        viewModelScope.launch {
            runCatching {
                val movie = response.movieWithId("Bearer $token", id)
                _selectMovie.value = movie
            }.onFailure {
                _errorCode.value= R.string.error_movie_response
            }.getOrNull()
        }
    }

}