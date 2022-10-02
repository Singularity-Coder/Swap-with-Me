package com.singularitycoder.swapwithme

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.singularitycoder.swapwithme.databinding.ListItemPersonBinding

class PersonsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var personList = mutableListOf<Person>()
    private var itemClickListener: (person: Person, position: Int) -> Unit = { person, position -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ListItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PersonViewHolder).setData(personList[position])
    }

    override fun getItemCount(): Int = personList.size

    override fun getItemViewType(position: Int): Int = position

    fun setItemClickListener(listener: (person: Person, position: Int) -> Unit) {
        itemClickListener = listener
    }

    inner class PersonViewHolder(
        private val itemBinding: ListItemPersonBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(person: Person) {
            itemBinding.apply {
                tvHostName.text = person.name
                tvUsableTimeCount.text = "${person.profession}  |  Can afford ~ ${person.iCanAfford}"
//                ratingHost.rating = person.rating
//                tvRatingCount.text = "(${person.ratingCount})"
//                tvDateAdded.text = host.dateStarted.toIntuitiveDateTime()
                ivImage.load(person.tempImageDrawable) {
                    placeholder(R.drawable.ic_placeholder)
                }
                cardBody.setOnClickListener {
                    itemClickListener.invoke(person, bindingAdapterPosition)
                }
            }
        }
    }
}
