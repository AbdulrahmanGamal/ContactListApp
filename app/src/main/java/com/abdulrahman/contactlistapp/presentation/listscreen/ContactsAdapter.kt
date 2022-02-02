package com.abdulrahman.contactlistapp.presentation.listscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.abdulrahman.contactlistapp.R
import com.abdulrahman.contactlistapp.data.local.UserDBEntity
import com.abdulrahman.contactlistapp.databinding.ContactsListItemBinding

class ContactsAdapter(private val onItemClickListener: (UserDBEntity) -> Unit) :
    PagingDataAdapter<UserDBEntity, ContactsAdapter.ViewHolder>(UI_MODEL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ContactsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(binding: ContactsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val contactName: TextView = binding.contactName
        val contactImage: ImageView = binding.profileImage

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.contactName.text = item?.name
        holder.contactImage.load(item?.picture?.thumbnail) {
            if (item?.gender == "male") {
                placeholder(R.drawable.ic_male_avatar)
            } else {
                placeholder(R.drawable.ic_female_avatar)
            }
            transformations(CircleCropTransformation())
        }
        with(holder.itemView) {
            setOnClickListener {
                item?.let { onItemClickListener.invoke(it) }
            }
        }
    }

    override fun getItemViewType(position: Int) = R.layout.contacts_list_item

    companion object {
        private val UI_MODEL_COMPARATOR = object : DiffUtil.ItemCallback<UserDBEntity>() {
            override fun areItemsTheSame(oldItem: UserDBEntity, newItem: UserDBEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserDBEntity, newItem: UserDBEntity): Boolean =
                oldItem == newItem
        }
    }
}
