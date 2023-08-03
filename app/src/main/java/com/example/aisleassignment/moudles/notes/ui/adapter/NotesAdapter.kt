package com.example.aisleassignment.moudles.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aisleassignment.databinding.ItemHeaderTextBinding
import com.example.aisleassignment.databinding.ItemProfileBinding
import com.example.aisleassignment.databinding.ItemSuggestionBinding
import com.example.aisleassignment.databinding.ItemUpgradeBinding
import com.example.aisleassignment.moudles.notes.models.ui.Likes
import com.example.aisleassignment.moudles.notes.models.ui.Notes
import com.example.aisleassignment.moudles.notes.models.ui.Profile

class NotesAdapter :
    ListAdapter<Notes, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Notes>() {
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.viewType == newItem.viewType
        }

    }) {

    enum class NoteType(val id: Int) {
        HEADER(1)
    }

    companion object {
        const val HEADER_TEXT = 101
        const val PROFILE = 102
        const val UPGRADE = 103
        const val SUGGESTION = 104
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }


    inner class HeaderTextViewHolder(binding: ItemHeaderTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Notes?) {

        }
    }

    inner class ProfileViewHolder(private val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Notes?) {
            item?.let {
                it as Profile
                binding.apply {
                    this.meena23.text = it.nameAge.toString()
                    Glide.with(root.context).load(it.imgSource)
//                        .placeholder(R.drawable.circular_progressbar)
                        .into(ivProfile)
                }
            }
        }
    }

    inner class UpgradeViewHolder(binding: ItemUpgradeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Notes?) {

        }
    }

    inner class SuggestionViewHolder(private val binding: ItemSuggestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Notes?) {
            val likeAdapter = SuggestionAdapter()
            binding.rvSuggestion.apply {
                adapter = likeAdapter
                layoutManager = GridLayoutManager(this.context, 2)
            }
            likeAdapter.submitList((item as Likes).listOfLikes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_TEXT -> {
                HeaderTextViewHolder(
                    ItemHeaderTextBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            PROFILE -> {
                ProfileViewHolder(
                    ItemProfileBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            UPGRADE -> {
                UpgradeViewHolder(
                    ItemUpgradeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            SUGGESTION -> {
                SuggestionViewHolder(
                    ItemSuggestionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                HeaderTextViewHolder(
                    ItemHeaderTextBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderTextViewHolder -> {
                holder.bindData(getItem(position))
            }

            is ProfileViewHolder -> {
                holder.bindData(getItem(position))
            }

            is UpgradeViewHolder -> {
                holder.bindData(getItem(position))
            }

            is SuggestionViewHolder -> {
                holder.bindData(getItem(position))
            }
        }
    }
}