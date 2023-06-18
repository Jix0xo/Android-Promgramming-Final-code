package com.example.forage.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forage.databinding.ListItemForageableBinding
import com.example.forage.model.Forageable

class ForageableListAdapter(
    private val clickListener: (Forageable) -> Unit
) : ListAdapter<Forageable, ForageableListAdapter.ForageableViewHolder>(DiffCallback) {

    // ViewHolder 클래스
    class ForageableViewHolder(
        private val binding: ListItemForageableBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        // Forageable 데이터를 ViewHolder에 바인딩하는 메서드
        fun bind(forageable: Forageable) {
            binding.forageable = forageable
            binding.executePendingBindings()
        }
    }

    // DiffUtil 콜백 객체
    companion object DiffCallback : DiffUtil.ItemCallback<Forageable>() {
        override fun areItemsTheSame(oldItem: Forageable, newItem: Forageable): Boolean {
            // 아이템이 동일한지 비교합니다. 여기서는 Forageable의 id가 같은지 비교합니다.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Forageable, newItem: Forageable): Boolean {
            // 아이템의 내용이 동일한지 비교합니다. 여기서는 Forageable 객체 자체가 동일한지 비교합니다.
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForageableViewHolder {
        // ViewHolder를 생성하고 뷰를 인플레이트합니다.
        val layoutInflater = LayoutInflater.from(parent.context)
        return ForageableViewHolder(
            ListItemForageableBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ForageableViewHolder, position: Int) {
        // ViewHolder와 데이터를 바인딩하고, 클릭 리스너를 설정합니다.
        val forageable = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener(forageable)
        }
        holder.bind(forageable)
    }
}
