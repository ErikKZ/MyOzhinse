package kz.example.myozinshe.domain.utils

import androidx.navigation.NavDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.example.myozinshe.presentation.AboutMovieFragmentDirections
import kz.example.myozinshe.presentation.FavoriteFragmentDirections
import kz.example.myozinshe.presentation.SeriesFragmentDirections

interface NavigationHostProvider {
    fun setNavigationVisibility(visible: Boolean)
//    fun setNavigationToolBar(visible: Boolean,isGone:Boolean)
    fun additionalToolBarConfig(toolbarVisible: Boolean, btnBackVisible:Boolean, btnExitVisible:Boolean, title:String)
    fun onClickListener(id:Int, arg: Int = 0, action: NavDirections = FavoriteFragmentDirections.actionFavoriteFragmentToAboutMovieFragment(arg))
    fun showBottomSheetExit(unit: BottomSheetDialogFragment)
}