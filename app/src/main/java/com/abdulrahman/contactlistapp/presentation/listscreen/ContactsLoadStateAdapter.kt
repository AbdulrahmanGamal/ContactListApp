package com.abdulrahman.contactlistapp.presentation.listscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.contactlistapp.R
import com.abdulrahman.contactlistapp.databinding.ContactsLoadStateFooterViewItemBinding

class ContactsLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ContactsLoadStateViewHolder>(){

    override fun onBindViewHolder(holder: ContactsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ContactsLoadStateViewHolder =
            ContactsLoadStateViewHolder.create(parent, retry)
}

class ContactsLoadStateViewHolder(
    private val binding: ContactsLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState){
        if (loadState is LoadState.Error){
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object{
        fun create(parent: ViewGroup, retry: () -> Unit): ContactsLoadStateViewHolder{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.contacts_load_state_footer_view_item, parent, false)
            val binding = ContactsLoadStateFooterViewItemBinding.bind(view)
            return  ContactsLoadStateViewHolder(binding, retry)
        }
    }
}