package kz.example.myozinshe.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.domain.api.ApiInterface
import kz.example.myozinshe.domain.api.ServiceBuilder
import kz.example.myozinshe.domain.models.Video

class SeriesViewModel: ViewModel() {
    private val response = ServiceBuilder.buildService(ApiInterface::class.java)

    private val _seriesMovies = MutableLiveData<List<Video>>()
    val seriesMovies: LiveData<List<Video>> = _seriesMovies

    private val _errorCode = MutableLiveData<Int>()
    val errorCode: LiveData<Int> = _errorCode

    fun fetchSeries(token:String, movieId: Int) {
        viewModelScope.launch {
            runCatching { response.getSeries("Bearer $token", movieId) }
                .onSuccess {
                    _seriesMovies.value =  it[0].videos
                }.onFailure {
                    _errorCode.value =  R.string.error_network
                }
        }
    }

}