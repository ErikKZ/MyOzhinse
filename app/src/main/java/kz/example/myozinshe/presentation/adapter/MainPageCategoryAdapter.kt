package kz.example.myozinshe.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.example.myozinshe.databinding.ItemMainCategoryRcBinding
import kz.example.myozinshe.domain.models.Movy
import kz.example.myozinshe.presentation.interfaces.ClickInterfaceMain

class MainPageCategoryAdapter :
    RecyclerView.Adapter<MainPageCategoryAdapter.MainPageCategoryViewHolder>() {

    private val onItemClickListener: ClickInterfaceMain? = null

    fun onTouchItem(listener: ClickInterfaceMain) {
        this.onItemClickListener
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Movy>() {
        override fun areItemsTheSame(oldItem: Movy, newItem: Movy): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movy, newItem: Movy): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(list: List<Movy>) {
        differ.submitList(list)
    }

    inner class MainPageCategoryViewHolder(private val binding: ItemMainCategoryRcBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: Movy) {
            with(binding) {
                textTVMovieCategoryTitle.text = item.name
                textTVMovieCategoryDescription.text = item.genres[0].name

                Glide.with(root.context)
                    .load(item.poster.link)
                    .into(imgMovieCategory)

                itemView.setOnClickListener {
                    onItemClickListener?.onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageCategoryViewHolder {
        return MainPageCategoryViewHolder(
            ItemMainCategoryRcBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: MainPageCategoryViewHolder, position: Int) =
        holder.bindItem(differ.currentList[position])
}



