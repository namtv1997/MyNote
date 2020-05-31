package com.henrynam.mynote.presentation.addnote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.henrynam.mynote.R
import com.henrynam.mynote.data.Note
import com.henrynam.mynote.databinding.ItemNodeBinding
import kotlinx.android.synthetic.main.item_node.view.*

class NoteAdapter(private var notes: MutableList<Note>) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var listener: NoteAdapterListener? = null

    interface NoteAdapterListener {
        fun onItemClick(node: Note)
    }

    fun setListener(listener: NoteAdapterListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_node, parent, false)
        )
    }

    fun setData(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }

    fun addNote(position: Int, author: Note) {
        notes.add(position, author)
        notifyItemInserted(position)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) {
            itemView.setOnClickListener {
                listener?.onItemClick(note)
            }
            if (note.title == "") itemView.tvTitle.visibility = View.GONE
            else itemView.tvTitle.visibility = View.VISIBLE

            if (note.description == "") itemView.tvContent.visibility = View.GONE
            else itemView.tvContent.visibility = View.VISIBLE

            if (note.isPin) itemView.ivMark.visibility = View.VISIBLE
            else itemView.ivMark.visibility = View.GONE

            itemView.tvTitle.text = note.title
            itemView.tvContent.text = note.description
            itemView.tvDate.text = "Tạo: ${note.createdAt}"
        }
    }

}
