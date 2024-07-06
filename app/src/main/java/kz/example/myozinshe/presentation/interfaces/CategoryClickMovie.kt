package kz.example.myozinshe.presentation.interfaces

import kz.example.myozinshe.domain.models.CategoryMovieResponse
import kz.example.myozinshe.domain.models.Content

interface CategoryClickMovie {
    fun onItemClick(item: Content)
}