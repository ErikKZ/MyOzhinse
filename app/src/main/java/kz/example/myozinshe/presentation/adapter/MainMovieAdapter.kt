package kz.example.myozinshe.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.example.myozinshe.databinding.ItemMainMovieRcBinding
import kz.example.myozinshe.domain.models.MainPageModelItem
import kz.example.myozinshe.domain.models.MoviesMainItem
import kz.example.myozinshe.presentation.interfaces.MainItemClick

class MainMovieAdapter : RecyclerView.Adapter<MainMovieAdapter.MainMovieHolder>() {

    private var onMainItemClick: MainItemClick? = null

    fun onTouchItem(listener: MainItemClick) {
        this.onMainItemClick = listener
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<MainPageModelItem>() {
        override fun areItemsTheSame(oldItem: MainPageModelItem, newItem: MainPageModelItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MainPageModelItem, newItem: MainPageModelItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submit(list: ArrayList<MainPageModelItem>) {
        differ.submitList(list)
    }

    inner class MainMovieHolder(private val binding: ItemMainMovieRcBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(mainItem: MainPageModelItem) {
            with(binding) {
                textTvMainMovieTittle.text = mainItem.movie.name
                textTvMainMovieDescription.text = mainItem.movie.description

                Glide.with(binding.root.context)
                    .load(mainItem.link)
                    .into(imgMainMovie)

                itemView.setOnClickListener {
                    onMainItemClick?.onItemClick(mainItem)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMovieHolder {
        return MainMovieHolder(
            ItemMainMovieRcBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MainMovieHolder, position: Int) {
        holder.bindItem(differ.currentList[position])
    }

}