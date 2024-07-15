package kz.example.myozinshe.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.example.myozinshe.databinding.ItemImgRcBinding
import kz.example.myozinshe.domain.models.Screenshot
import kz.example.myozinshe.presentation.interfaces.ImageClick

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var onItemClickListener: ImageClick? = null

    fun onTouchItem(listener: ImageClick) {
        this.onItemClickListener = listener
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Screenshot>() {
        override fun areItemsTheSame(oldItem: Screenshot, newItem: Screenshot): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Screenshot, newItem: Screenshot): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(list: List<Screenshot>) {
        differ.submitList(list)
    }

    inner class ImageViewHolder(private val binding: ItemImgRcBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBindItem(item: Screenshot) {
            binding.run {
                Glide.with(binding.root)
                    .load(item.link)
                    .into(binding.img120)
            }
            itemView.setOnClickListener {
                onItemClickListener?.onClickItem(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageAdapter.ImageViewHolder {
        return ImageViewHolder(
            ItemImgRcBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageAdapter.ImageViewHolder, position: Int) =
        holder.onBindItem(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

}