package kz.example.myozinshe.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.example.myozinshe.R
import kz.example.myozinshe.databinding.ItemSeriesRcViewBinding
import kz.example.myozinshe.domain.models.Video
import kz.example.myozinshe.presentation.interfaces.SeriesClick

class SeriesAdapter() : RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {
    private var onItemClickListener: SeriesClick? = null
    fun onTouchItem(listener: SeriesClick) {
        this.onItemClickListener = listener
    }

    fun diffCallBack() = object : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack())

    fun submitList(list: List<Video>) = differ.submitList(list)


    inner class SeriesViewHolder(private val binding: ItemSeriesRcViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: Video) {
            binding.textSeries.text = "${item.number}-${binding.root.context.getString(R.string.EndWordBolim)}"

            //https://youtu.be/hycgdjf54i8
            //http://img.youtube.com/vi/hycgdjf54i8/maxresdefault.jpg
            Glide.with(binding.root)
                .load("http://img.youtube.com/vi/${item.link}/maxresdefault.jpg")
                .into(binding.imgTvSeries)
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeriesAdapter.SeriesViewHolder {
        return SeriesViewHolder(
            ItemSeriesRcViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SeriesAdapter.SeriesViewHolder, position: Int) =
        holder.bindItem(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

}