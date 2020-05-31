package com.henrynam.mynote.presentation.addnote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.henrynam.mynote.data.Note
import com.henrynam.mynote.databinding.ItemNodeBinding

class NoteAdapter(private val notes: MutableList<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var listener: NoteAdapterListener? = null

    interface NoteAdapterListener {
        fun onItemClick(node: Note)
    }

    fun setListener(listener: NoteAdapterListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNodeBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    fun setData(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }

    fun addNote(position: Int, note: Note) {
        notes.add(position, note)
        notifyItemInserted(position)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notes[position])

    inner class ViewHolder(val binding: ItemNodeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
            binding.note = item

            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }
}