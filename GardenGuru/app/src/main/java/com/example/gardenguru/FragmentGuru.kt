package com.example.gardenguru

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import io.github.cdimascio.dotenv.Dotenv

//Alternate version of using the apiKey from the .env file located at the bottom of the file
private val dotenv: Dotenv = Dotenv.configure()
    .directory("GardenGuru/.env")
    .load()
    private val apiKey = dotenv["GEMINI_API_KEY"]

class FragmentGuru : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var messages: MutableList<Message>
    lateinit var messageAdapter: MessageRecyclerViewAdapter
    lateinit var responseBody: String
    lateinit var jsonResponse: JSONObject
    lateinit var candidatesArray: JSONArray
    lateinit var firstCandidate: JSONObject
    lateinit var content: JSONObject
    lateinit var partsArray: JSONArray
    lateinit var firstPart: JSONObject
    lateinit var text: String

    private fun addToChat(message: String, sentBy: String) {
        requireActivity().runOnUiThread {
            messages.add(Message(message, sentBy))
            messageAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }
    fun addResponse(response: String) {
        addToChat(response, Message.SENT_BY_BOT)
    }
    fun callAPI(question: String) {
        // Define JSON payload for Gemini API
        val jsonBody = JSONObject().apply {
            try {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", question)
                            })
                        })
                    })
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Create the request body
        val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())

        // Build the HTTP request
        val request = okhttp3.Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=$apiKey")
            .header("Content-Type", "application/json")
            .post(requestBody)
            .build()

        // Make the HTTP call using OkHttp
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                addResponse("Request failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        addResponse("Unexpected response code: ${response.code}")
                    } else {
                        responseBody = response.body?.string().toString()
                        jsonResponse = JSONObject(responseBody)
                        candidatesArray = jsonResponse.optJSONArray("candidates")
                        firstCandidate = candidatesArray.getJSONObject(0)
                        content = firstCandidate.optJSONObject("content")
                        partsArray = content.optJSONArray("parts")
                        firstPart = partsArray.getJSONObject(0)
                        text = firstPart.optString("text", "No text found")
                        addResponse(text)
                    }
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_guru, container, false)
        val welcomeTextView: TextView = view.findViewById(R.id.welcome_message)
        val messageEditText: EditText = view.findViewById(R.id.message_edit_text)
        val sendButton: ImageButton = view.findViewById(R.id.send_btn)
        recyclerView= view.findViewById(R.id.recycler_view)
        messages = mutableListOf()
        //SET UP RECYCLER VIEW
        messageAdapter = MessageRecyclerViewAdapter(messages)
        recyclerView.adapter = messageAdapter
        val llm = LinearLayoutManager(requireContext());
        llm.stackFromEnd = true;
        recyclerView.layoutManager = llm;
        //ON CLICK LISTENER
        sendButton.setOnClickListener{
            val question = messageEditText.text.toString().trim();
            addToChat(question,Message.SENT_BY_ME);
            messageEditText.setText("")
            callAPI(question);
            welcomeTextView.visibility = View.GONE
        };
        return view
    }
    companion object {
        fun newInstance(): FragmentGuru {
            return FragmentGuru()
        }
    }
}


