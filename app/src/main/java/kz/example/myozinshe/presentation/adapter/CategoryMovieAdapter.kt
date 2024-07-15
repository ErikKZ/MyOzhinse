package kz.example.myozinshe.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.example.myozinshe.databinding.ItemFavoriteRcViewBinding
import kz.example.myozinshe.domain.models.Content
import kz.example.myozinshe.presentation.interfaces.CategoryClickMovie

class CategoryMovieAdapter : RecyclerView.Adapter<CategoryMovieAdapter.CategoryMovieHolder>() {

    private var onItemClickListener: CategoryClickMovie? = null

    fun onTouchItem(listener: CategoryClickMovie) {
        this.onItemClickListener = listener
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(list: List<Content>) {
        differ.submitList(list)
    }

    inner class CategoryMovieHolder(private val binding: ItemFavoriteRcViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: Content) {
            binding.apply {
                textTvTitle.text = item.name
                textTvYear.text = item.year.toString()

                var additionalInfoGenre = " "
                for (i in item.genres) {
                    additionalInfoGenre += "• ${i.name}"
                }
                textTvGenres.text = additionalInfoGenre
                Glide.with(binding.root)
                    .load(item.poster.link)
                    .into(imgFavorite)

                btnWatchVideo.setOnClickListener {
                    onItemClickListener?.onItemClick(item)
                }

                itemView.setOnClickListener {
                    onItemClickListener?.onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryMovieAdapter.CategoryMovieHolder {
        return CategoryMovieHolder(
            ItemFavoriteRcViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryMovieAdapter.CategoryMovieHolder, position: Int) =
        holder.bindItem(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size


}