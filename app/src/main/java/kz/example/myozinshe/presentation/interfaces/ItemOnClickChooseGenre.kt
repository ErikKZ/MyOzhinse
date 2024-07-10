package kz.example.myozinshe.presentation.interfaces

import kz.example.myozinshe.domain.models.GenreResponseItem

interface ItemOnClickChooseGenre {
    fun onClickItem(item: GenreResponseItem)
}