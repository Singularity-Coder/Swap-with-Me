package com.singularitycoder.swapwithme

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.singularitycoder.swapwithme.databinding.ListItemPersonBinding

class PersonsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var swapItemList = mutableListOf<SwapItem>()
    private var itemClickListener: (swapItem: SwapItem, position: Int) -> Unit = { person, position -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ListItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PersonViewHolder).setData(swapItemList[position])
    }

    override fun getItemCount(): Int = swapItemList.size

    override fun getItemViewType(position: Int): Int = position

    fun setItemClickListener(listener: (swapItem: SwapItem, position: Int) -> Unit) {
        itemClickListener = listener
    }

    inner class PersonViewHolder(
        private val itemBinding: ListItemPersonBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(swapItem: SwapItem) {
            itemBinding.apply {
                tvHostName.text = swapItem.name
//                ratingHost.rating = person.rating
//                tvRatingCount.text = "(${person.ratingCount})"
//                tvDateAdded.text = host.dateStarted.toIntuitiveDateTime()
                ivImage.load(swapItem.tempImageDrawable) {
                    placeholder(R.drawable.ic_placeholder)
                }
                cardBody.setOnClickListener {
                    itemClickListener.invoke(swapItem, bindingAdapterPosition)
                }
            }
        }
    }
}
