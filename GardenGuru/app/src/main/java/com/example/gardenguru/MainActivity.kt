package com.example.gardenguru

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.cdimascio.dotenv.Dotenv
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import io.github.cdimascio.dotenv.dotenv


class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var welcomeTextView: TextView
    lateinit var messageEditText: EditText
    lateinit var sendButton: ImageButton
    lateinit var messages: MutableList<Message>
    lateinit var messageAdapter: MessageRecyclerViewAdapter
    val dotenv = Dotenv.configure()
        .directory("/Users/raphaelholganza/Desktop/GardenGuruApp/GardenGuru") // Specify the directory where your .env file is located
        .load()
    private val apiKey = dotenv["API_KEY"]


    companion object {
        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    }
    val client = OkHttpClient()

    fun addResponse(response: String) {
        addToChat(response, Message.SENT_BY_BOT)
    }

    fun callAPI(question: String) {
        // Creating a JSON object for the request body
        val jsonBody = JSONObject().apply {
            try {
                put("model", "gpt-3.5-turbo") // Add key-value pair to the JSON object
                put("prompt", question)
                put("temperature", 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val requestBody = this.toString().toRequestBody(JSON)
            val request = okhttp3.Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header(
                    "Authorization", "Bearer $apiKey") // Replace with actual header key-value
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                    addResponse("Failed to load response due to ${e.message}")
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    if (response.isSuccessful) {
                        try {
                            val jsonObject = JSONObject(response.body?.string())
                            val jsonArray = jsonObject.getJSONArray("choices")
                            val result: String = jsonArray.getJSONObject(0).getString("text")
                            addResponse(result.trim())
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        addResponse("Failed to load response due to ${response.body?.string()}")
                    }
                }
            })
        }
    }
    private fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            messages.add(Message(message, sentBy))
            messageAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }
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
            messageEditText.setText("")
            callAPI(question);
            welcomeTextView.visibility = View.GONE
        };
    }
}