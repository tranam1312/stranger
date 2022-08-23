package com.example.stranger.ui.home

import android.util.Log
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.stranger.R
import com.example.stranger.base.recyclerview.BaseDiffUtilItemCallback
import com.example.stranger.base.recyclerview.BaseRecyclerAdapter
import com.example.stranger.base.recyclerview.BaseViewHolder
import com.example.stranger.databinding.ItemHaderHomeBinding
import com.example.stranger.model.ItemHome
import com.example.stranger.ui.home.HomeFragment.Companion.OPEN_POST
import com.example.stranger.ui.home.HomeFragment.Companion.OPEN_PROFILE
import com.example.stranger.ui.home.viewholer.HeaderHomeViewHolder
import com.example.stranger.ui.home.viewholer.HomeViewHolder

class HomeAdapter(
    private val viewModel: HomeViewModel, private val openFragment: (String) -> Unit
) : BaseRecyclerAdapter<ItemHome, BaseViewHolder<ItemHome, ViewDataBinding>>(ItemDiffUtilCallback()) {

    companion object {
        const val ITEM_HEADER = 0
        const val ITEM_HOME = 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ItemHome, ViewDataBinding> {
        val viewDataBinding = getViewHolderDataBinding(parent, viewType)
        return when (viewType) {
            ITEM_HEADER -> HeaderHomeViewHolder(viewModel, viewDataBinding)
            else -> HomeViewHolder(viewModel, viewDataBinding)
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemHome, ViewDataBinding>,
        position: Int
    ) {
        when (holder) {
            is HeaderHomeViewHolder -> {
                (holder.binding as ItemHaderHomeBinding).profileImage.setOnClickListener {
                    openFragment.invoke(OPEN_PROFILE)
                }
                holder.binding.text.setOnClickListener {
                    openFragment.invoke(OPEN_POST)
                }
            }
            is HomeViewHolder -> if (itemCount > 1) {
                holder.bind(getItem(position - 1))
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ITEM_HEADER else ITEM_HOME
    }

    class ItemDiffUtilCallback : BaseDiffUtilItemCallback<ItemHome>() {
        override fun areContentsTheSame(oldItem: ItemHome, newItem: ItemHome): Boolean =
            oldItem == newItem
    }

    override fun getItemLayoutResource(viewType: Int): Int {
        return if (viewType == ITEM_HEADER) R.layout.item_hader_home else R.layout.item_home
    }
}