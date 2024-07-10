package kz.example.myozinshe.presentation.interfaces

import kz.example.myozinshe.domain.models.MainPageModelItem
import kz.example.myozinshe.domain.models.MoviesMainItem

interface MainItemClick {
    fun onItemClick(item: MainPageModelItem)
}