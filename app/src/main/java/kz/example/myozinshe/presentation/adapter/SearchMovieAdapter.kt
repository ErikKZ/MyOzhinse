package kz.example.myozinshe.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.example.myozinshe.databinding.ItemFavoriteRcViewBinding
import kz.example.myozinshe.domain.models.SearchResponseModelItem
import kz.example.myozinshe.presentation.interfaces.SearchMovieClick

class SearchMovieAdapter() : RecyclerView.Adapter<SearchMovieAdapter.SearchMovieHolder>() {

    private var onItemClickListener: SearchMovieClick? = null

    fun onTouchItem(listener: SearchMovieClick) {
        this.onItemClickListener = listener
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<SearchResponseModelItem>() {
        override fun areItemsTheSame(
            oldItem: SearchResponseModelItem,
            newItem: SearchResponseModelItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchResponseModelItem,
            newItem: SearchResponseModelItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(list: List<SearchResponseModelItem>) = differ.submitList(list)

    inner class SearchMovieHolder(private val binding: ItemFavoriteRcViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: SearchResponseModelItem) {
            binding.apply {
                textTvTitle.text = item.name
                textTvYear.text = item.year.toString()

                var additionalInfoGenre = " "
                for (i in item.genres) {
                    additionalInfoGenre += "• ${i.name}"
                }
                Glide.with(binding.root.context)
                    .load(item.poster.link)
                    .into(imgFavorite);
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
    ): SearchMovieAdapter.SearchMovieHolder = SearchMovieHolder(ItemFavoriteRcViewBinding.inflate(
        LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: SearchMovieAdapter.SearchMovieHolder, position: Int) =
        holder.bindItem(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

}