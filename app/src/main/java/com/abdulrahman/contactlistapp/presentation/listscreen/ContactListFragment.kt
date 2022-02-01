package com.abdulrahman.contactlistapp.presentation.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.abdulrahman.contactlistapp.R
import com.abdulrahman.contactlistapp.databinding.FragmentItemListBinding
import com.abdulrahman.contactlistapp.presentation.details.ContactDetailFragment
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ContactDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

@ExperimentalPagingApi
class ContactListFragment : Fragment() {

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
    val viewModel: ContactsListViewModel by inject()
    private val adapter = ContactsAdapter {
        val bundle = Bundle()
        bundle.putString(ContactDetailFragment.ARG_ITEM_ID, it.id)
        findNavController().navigate(R.id.show_item_detail, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        lifecycleScope.launch {
            viewModel.observeUsers().collectLatest {
                adapter.submitData(it)
            }
        }
    }


    private fun initAdapter() {

        _binding?.contactsRecyclerView?.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ContactsLoadStateAdapter { adapter.retry() },
            footer = ContactsLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            binding.contactsRecyclerView?.isVisible =
                loadState.source.refresh is LoadState.NotLoading
            binding.progressBar?.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton?.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}