package kz.example.myozinshe.presentation.interfaces

import kz.example.myozinshe.domain.models.FavoriteModelItem

interface FavoriteClickMovie {
    fun onItemClick(item: FavoriteModelItem)
}