package kz.example.myozinshe.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.example.myozinshe.databinding.ItemGenreRcViewBinding
import kz.example.myozinshe.domain.models.GenreResponse
import kz.example.myozinshe.domain.models.GenreResponseItem
import kz.example.myozinshe.presentation.interfaces.ItemOnClickChooseGenre

class GenreMainAdapter : RecyclerView.Adapter<GenreMainAdapter.GenreViewHolder>() {

    private var onItemClickListener: ItemOnClickChooseGenre? = null

    fun onTouchItem(listener: ItemOnClickChooseGenre) {
        this.onItemClickListener = listener
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<GenreResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GenreResponseItem,
            newItem: GenreResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GenreResponseItem,
            newItem: GenreResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(list: GenreResponse) {
        differ.submitList(list)
    }

    inner class GenreViewHolder(private val binding: ItemGenreRcViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBindItem(item: GenreResponseItem) {
            binding.textforGenres.text = item.name

            Glide.with(binding.root)
                .load(item.link)
                .into(binding.imgGenre)

            itemView.setOnClickListener {
                onItemClickListener?.onClickItem(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreViewHolder {
        return GenreViewHolder(
            ItemGenreRcViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size
    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) =
        holder.onBindItem(differ.currentList[position])
}