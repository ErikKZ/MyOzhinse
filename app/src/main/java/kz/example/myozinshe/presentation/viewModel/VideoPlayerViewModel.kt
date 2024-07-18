package kz.example.myozinshe.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoPlayerViewModel: ViewModel() {
    private val _link = MutableLiveData<String>()
    val link: LiveData<String> = _link

    private val _playbackPozition = MutableLiveData<Long>().apply { value = 0L }
    val playbackPozition: LiveData<Long> = _playbackPozition

    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int> = _errorMessage

    fun updatePozition(position: Long) {
        _playbackPozition.value = position
    }

    fun updateMovieLink(newLink: String){
        _link.value = newLink
    }
}