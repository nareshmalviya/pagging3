package com.codelab.pagging3.ui.charcters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codelab.pagging3.databinding.ItemCharacterBinding
import com.codelab.pagging3.data.model.Characters

import javax.inject.Inject

class CharacterAdapter @Inject constructor() : PagingDataAdapter<Characters, CharacterAdapter.CharacterViewHolder>(CharacterComparator) {
    var characterClickListener: CharacterClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                characterClickListener?.onCharacterClicked(
                    binding,
                    getItem(absoluteAdapterPosition) as Characters
                )
            }
        }

        fun bind(item: Characters) = with(binding) {
            ViewCompat.setTransitionName(binding.ivAvatar, "avatar_${item.id}")
            ViewCompat.setTransitionName(binding.tvName, "name_${item.id}")
            ViewCompat.setTransitionName(binding.tvStatus, "status_${item.id}")
            character = item
        }
    }

    object CharacterComparator : DiffUtil.ItemCallback<Characters>() {
        override fun areItemsTheSame(oldItem: Characters, newItem: Characters) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Characters, newItem: Characters) =
            oldItem == newItem
    }

    interface CharacterClickListener {
        fun onCharacterClicked(binding: ItemCharacterBinding, characters: Characters)
    }
}