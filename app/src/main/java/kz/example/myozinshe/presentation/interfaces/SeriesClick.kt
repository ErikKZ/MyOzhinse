package kz.example.myozinshe.presentation.interfaces

import kz.example.myozinshe.domain.models.Video

interface SeriesClick {
    fun onItemClick(item: Video)
}