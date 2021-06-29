package com.example.common.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.common.databinding.PagingFooterItemBinding

class FooterAdapter(private val retry:() -> Unit) :
    LoadStateAdapter<FooterAdapter.FooterViewHolder>() {
    class FooterViewHolder (binding: PagingFooterItemBinding) : RecyclerView.ViewHolder(binding.root){
        var pagingFooterItemBinding = binding
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.pagingFooterItemBinding.run {
            progressBar.isVisible = loadState is LoadState.Loading
            btRetry.isVisible = loadState is LoadState.Error
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        val binding = PagingFooterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        binding.btRetry.setOnClickListener {
            retry
        }
        return FooterViewHolder(binding)
    }
}