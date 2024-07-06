package kz.example.myozinshe.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kz.example.myozinshe.databinding.ItemWelcomepageSliderBinding
import kz.example.myozinshe.domain.models.WelcomePageInfoModel

class WelcomePageAdapter() : RecyclerView.Adapter<WelcomePageAdapter.WelcomePageViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<WelcomePageInfoModel>() {
        override fun areItemsTheSame(
            oldItem: WelcomePageInfoModel,
            newItem: WelcomePageInfoModel
        ): Boolean {
            return oldItem.img == newItem.img
        }

        override fun areContentsTheSame(
            oldItem: WelcomePageInfoModel,
            newItem: WelcomePageInfoModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(list: List<WelcomePageInfoModel>) {
        differ.submitList(list)
    }

    inner class WelcomePageViewHolder(private val binding: ItemWelcomepageSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: WelcomePageInfoModel) = with(binding) {
            textTvTitleWelcomePageSlider.text = item.title
            textTvDescriptionWelcomePageSlider.text = item.description
            imgTvWelcomeBannerOpacityBack.setImageResource(item.img)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomePageViewHolder {
        return WelcomePageViewHolder(
            ItemWelcomepageSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: WelcomePageViewHolder, position: Int) =
        holder.bindItem(differ.currentList[position])

}