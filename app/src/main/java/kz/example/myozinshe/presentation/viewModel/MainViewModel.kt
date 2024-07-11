package kz.example.myozinshe.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.domain.api.ApiInterface
import kz.example.myozinshe.domain.api.ServiceBuilder
import kz.example.myozinshe.domain.models.GenreResponse
import kz.example.myozinshe.domain.models.MainPageModel
import kz.example.myozinshe.domain.models.MoviesMain
import kz.example.myozinshe.domain.models.MoviesMainItem

class MainViewModel: ViewModel() {
    val response = ServiceBuilder.buildService(ApiInterface::class.java)

    private val _mainMovie = MutableLiveData<MoviesMain>()
    val mainMovie: LiveData<MoviesMain> = _mainMovie

    private val _mainMovy = MutableLiveData<MainPageModel>()
    val mainMovy: LiveData<MainPageModel> = _mainMovy

    private val _mainTelihikaialar = MutableLiveData<MainPageModel>()
    val  mainTelihikaialar: LiveData<MainPageModel> = _mainTelihikaialar

    private val _mainGenre = MutableLiveData<GenreResponse>()
    val mainGenre: LiveData<GenreResponse> = _mainGenre

    private val _mainMult = MutableLiveData<MainPageModel>()
    val mainMult : LiveData<MainPageModel> = _mainMult

    private val _mainMultSerial = MutableLiveData<MainPageModel>()
    val mainMultSerial: LiveData<MainPageModel> =_mainMultSerial

    private val _mainSitcom = MutableLiveData<MainPageModel> ()
    val mainSitcom:LiveData<MainPageModel> =_mainSitcom

    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int> = _errorMessage

    fun movie(token :String) {
        viewModelScope.launch {
            runCatching {
                response.moviesMain("Bearer $token")
            }.onSuccess { data ->
                _mainMovie.value = data
            }.onFailure {
                _errorMessage.value = R.string.error_movie_response
            }
        }
    }

    fun getMovieOzinshe(token: String) {
        viewModelScope.launch {
            runCatching {
                response.moviesMainPage("Bearer $token")
            }.onSuccess {data ->
                _mainMovy.value = data
            }.onFailure {
                _errorMessage.value = R.string.error_movie_response
            }
        }
    }

    fun getMovieTelihikaialar(token: String) {
        viewModelScope.launch {
            runCatching {
                response.moviesMainPage("Bearer $token")
            }.onSuccess {data ->
                _mainTelihikaialar.value = data
            }.onFailure {
                _errorMessage.value = R.string.error_movie_response
            }
        }
    }

    fun getGenreRC(token: String){
        viewModelScope.launch {
            runCatching {
                response.getGenre("Bearer $token")
            }.onSuccess {data ->
                _mainGenre.value = data
            }.onFailure {
                _errorMessage.value = R.string.error_movie_response
            }
        }
    }

    fun getMovieTolyqMultfilm(token: String) {
        viewModelScope.launch {
            runCatching {
                response.moviesMainPage("Bearer $token")
            }.onSuccess {data ->
                _mainMult.value = data
            }.onFailure {
                _errorMessage.value =R.string.error_movie_response
            }
        }
    }

    fun getMovieMultSerial(token: String) {
        viewModelScope.launch {
            runCatching {
                response.moviesMainPage("Bearer $token")
            }.onSuccess { data ->
                _mainMultSerial.value = data
            }.onFailure {
                _errorMessage.value = R.string.error_movie_response
            }
        }
    }

    fun getMovieSitcom (token: String) {
        viewModelScope.launch {
            runCatching {
                response.moviesMainPage("Bearer $token")
            }.onSuccess { data ->
                _mainSitcom.value = data
            }.onFailure {
                _errorMessage.value =  R.string.error_movie_response
            }
        }
    }
}