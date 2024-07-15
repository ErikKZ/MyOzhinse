package kz.example.myozinshe.presentation.interfaces

import kz.example.myozinshe.domain.models.Screenshot

interface ImageClick {
    fun onClickItem(item: Screenshot)
}