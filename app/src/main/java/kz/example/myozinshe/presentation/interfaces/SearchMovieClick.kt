package kz.example.myozinshe.presentation.interfaces

import kz.example.myozinshe.domain.models.SearchResponseModelItem

interface SearchMovieClick {
    fun onItemClick(item: SearchResponseModelItem){}
}