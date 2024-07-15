package kz.example.myozinshe.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.example.myozinshe.R
import kz.example.myozinshe.domain.api.ApiInterface
import kz.example.myozinshe.domain.api.ServiceBuilder
import kz.example.myozinshe.domain.models.CategoryMovieResponse

class CategoriesViewModel: ViewModel() {
    private val response = ServiceBuilder.buildService(ApiInterface::class.java)

    private val _categoryMovie = MutableLiveData<CategoryMovieResponse>()
    val categoryMovie: LiveData<CategoryMovieResponse> = _categoryMovie

    private val _errorCode = MutableLiveData<Int>()
    val errorCode: LiveData<Int> = _errorCode

    fun categoryMovie(token:String, categoryId: Int) {
        viewModelScope.launch {
            runCatching {
                val direction = "DESC"
                val page = 0
                val size = 20
                val sortField = "createdDate"
                response.getCategoryMovie("Bearer $token", categoryId,direction,page,size,sortField)
            }.onSuccess {
                _categoryMovie.value = it
            }.onFailure {
                _errorCode.value = R.string.error_movie_response
            }
        }
    }
}