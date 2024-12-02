package com.example.gardenguru

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var welcomeTextView: TextView
    lateinit var messageEditText: EditText
    lateinit var sendButton: ImageButton
    lateinit var messages: MutableList<Message>
    lateinit var messageAdapter: MessageRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        messages = mutableListOf()
        recyclerView = findViewById(R.id.recycler_view)
        welcomeTextView = findViewById(R.id.welcome_message)
        messageEditText = findViewById(R.id.message_edit_text)
        sendButton = findViewById(R.id.send_btn)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //setup recycler view
        messageAdapter = MessageRecyclerViewAdapter(messages)
        recyclerView.adapter = messageAdapter
        val llm = LinearLayoutManager(this);
        llm.stackFromEnd = true;
        recyclerView.layoutManager = llm;

        sendButton.setOnClickListener{
            val question = messageEditText.text.toString().trim();
            addToChat(question,Message.SENT_BY_ME);
            welcomeTextView.visibility = View.GONE
        };
    }
    fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            messages.add(Message(message, sentBy))
            messageAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }

}