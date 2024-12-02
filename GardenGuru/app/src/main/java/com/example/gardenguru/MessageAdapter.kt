package com.example.gardenguru

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.Message
import com.example.gardenguru.R

class MessageRecyclerViewAdapter(
    private val messages: List<Message>
) : RecyclerView.Adapter<MessageRecyclerViewAdapter.MyViewHolder>() {

    // ViewHolder class
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val leftChatView: LinearLayout = itemView.findViewById(R.id.left_chat_view)
        val rightChatView: LinearLayout = itemView.findViewById(R.id.right_chat_view)
        val leftChatText: TextView = itemView.findViewById(R.id.left_chat_text_view)
        val rightChatText: TextView = itemView.findViewById(R.id.right_chat_text_view)
    }

    // Inflate the item layout and create a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return MyViewHolder(view)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message = messages[position]

        if (message.sentBy == Message.SENT_BY_ME) {
            // Show the right chat view and hide the left one
            holder.rightChatView.visibility = View.VISIBLE
            holder.leftChatView.visibility = View.GONE
            holder.rightChatText.text = message.message
        } else {
            // Show the left chat view and hide the right one
            holder.leftChatView.visibility = View.VISIBLE
            holder.rightChatView.visibility = View.GONE
            holder.leftChatText.text = message.message
        }
    }

    // Return the total number of items in the list
    override fun getItemCount(): Int = messages.size
}
