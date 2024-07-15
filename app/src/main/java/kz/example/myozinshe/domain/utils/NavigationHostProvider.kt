package kz.example.myozinshe.domain.utils

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface NavigationHostProvider {
    fun setNavigationVisibility(visible: Boolean)
//    fun setNavigationToolBar(visible: Boolean,isGone:Boolean)
    fun additionalToolBarConfig(toolbarVisible: Boolean, btnBackVisible:Boolean, btnExitVisible:Boolean, title:String)
    fun onClickListener(id:Int)
    fun showBottomSheetExit(unit: BottomSheetDialogFragment)
}