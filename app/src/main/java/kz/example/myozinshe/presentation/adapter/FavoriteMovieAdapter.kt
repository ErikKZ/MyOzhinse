package kz.example.myozinshe.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.example.myozinshe.databinding.ItemFavoriteRcViewBinding
import kz.example.myozinshe.domain.models.FavoriteModelItem
import kz.example.myozinshe.presentation.interfaces.FavoriteClickMovie

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieHolder>() {

    private var onItemClickListener: FavoriteClickMovie? = null

    fun onTouchItem(listener: FavoriteClickMovie) {
        this.onItemClickListener = listener
    }

    private val diffCallBack =
        object : DiffUtil.ItemCallback<FavoriteModelItem>() {
            override fun areItemsTheSame(
                oldItem: FavoriteModelItem,
                newItem: FavoriteModelItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FavoriteModelItem,
                newItem: FavoriteModelItem
            ): Boolean {
                return oldItem == newItem
            }
        }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun subminList(list: List<FavoriteModelItem>) {
        differ.submitList(list)
    }

    inner class FavoriteMovieHolder(private val binding: ItemFavoriteRcViewBinding ):
        RecyclerView.ViewHolder(binding.root)  {
        fun bindItem(item: FavoriteModelItem) {
            binding.apply {
                textTvTitle.text = item.name
                textTvYear.text = item.year.toString()
                var additionalInfoGenre = " "
                for (i in item.genres) {
                    additionalInfoGenre += "• ${i.name}"
                }
                textTvGenres.text = additionalInfoGenre
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
    ): FavoriteMovieAdapter.FavoriteMovieHolder {
        return FavoriteMovieHolder(
            ItemFavoriteRcViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteMovieAdapter.FavoriteMovieHolder, position: Int) =
        holder.bindItem(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

}