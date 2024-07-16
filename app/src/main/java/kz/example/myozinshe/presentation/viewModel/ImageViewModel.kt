package kz.example.myozinshe.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageViewModel: ViewModel() {
    private val _imageLink = MutableLiveData<String>()
    val imageLink: LiveData<String> = _imageLink

    fun loadImg(link: String) {
        _imageLink.value = link
    }

}