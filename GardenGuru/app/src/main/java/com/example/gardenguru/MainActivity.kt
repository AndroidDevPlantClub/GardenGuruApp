package com.example.gardenguru

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bottomNav: BottomNavigationView = findViewById(R.id.nav_bar)

        bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.Guru -> FragmentGuru()
                R.id.Notifications -> FragmentNotifications()
                R.id.Records -> FragmentPlantRecord()
                else -> return@setOnItemSelectedListener false
            }
            replaceFragment(fragment)
            true
        }
        bottomNav.selectedItemId = R.id.Guru
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}

///IT STARTS FROM HERE!!!! ------------------------------------>
//package com.example.gardenguru
//
//import android.os.Bundle
//import android.view.View
//import android.widget.EditText
//import android.widget.ImageButton
//import android.widget.TextView
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import io.github.cdimascio.dotenv.Dotenv
//import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.OkHttpClient
//import okhttp3.RequestBody.Companion.toRequestBody
//import org.json.JSONObject
//import io.github.cdimascio.dotenv.dotenv
//import okhttp3.Call
//import okhttp3.Callback
//import okhttp3.Response
//import okio.IOException
//import org.json.JSONArray
//import androidx.fragment.app.Fragment
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//
//class MainActivity : AppCompatActivity() {
//    lateinit var recyclerView: RecyclerView
//    lateinit var messages: MutableList<Message>
//    lateinit var messageAdapter: MessageRecyclerViewAdapter
//    lateinit var responseBody: String
//    lateinit var jsonResponse: JSONObject
//    lateinit var candidatesArray: JSONArray
//    lateinit var firstCandidate: JSONObject
//    lateinit var content: JSONObject
//    lateinit var partsArray: JSONArray
//    lateinit var firstPart: JSONObject
//    lateinit var text: String
//    //    private val dotenv: Dotenv = Dotenv.configure()
////        .directory("GardenGuru/.env") // Specify the directory where your .env file is located
////        .load()
////    private val apiKey = dotenv["API_KEY"]
//    private val apiKey = "AIzaSyCdVogHSIkIeVTe98lXP9A_lPoRo8MHqbQ"
//
////    companion object {
////        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
////    }
//
//    fun addResponse(response: String) {
//        addToChat(response, Message.SENT_BY_BOT)
//    }
//
//    fun callAPI(question: String) {
//        // Define JSON payload for Gemini API
//        val jsonBody = JSONObject().apply {
//            try {
//                put("contents", JSONArray().apply {
//                    put(JSONObject().apply {
//                        put("parts", JSONArray().apply {
//                            put(JSONObject().apply {
//                                put("text", question)
//                            })
//                        })
//                    })
//                })
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//
//        // Create the request body
//        val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())
//
//        // Build the HTTP request
//        val request = okhttp3.Request.Builder()
//            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=$apiKey")
//            .header("Content-Type", "application/json")
//            .post(requestBody)
//            .build()
//
//        // Make the HTTP call using OkHttp
//        val client = OkHttpClient()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                addResponse("Request failed: ${e.message}")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                response.use {
//                    if (!response.isSuccessful) {
//                        addResponse("Unexpected response code: ${response.code}")
//                    } else {
//                        responseBody = response.body?.string().toString()
//                        jsonResponse = JSONObject(responseBody)
//                        candidatesArray = jsonResponse.optJSONArray("candidates")
//                        firstCandidate = candidatesArray.getJSONObject(0)
//                        content = firstCandidate.optJSONObject("content")
//                        partsArray = content.optJSONArray("parts")
//                        firstPart = partsArray.getJSONObject(0)
//                        text = firstPart.optString("text", "No text found")
//                        addResponse(text)
//                    }
//                }
//            }
//        })
//    }
//
//    private fun addToChat(message: String, sentBy: String) {
//        runOnUiThread {
//            messages.add(Message(message, sentBy))
//            messageAdapter.notifyDataSetChanged()
//            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        val welcomeTextView: TextView = findViewById(R.id.welcome_message)
//        val messageEditText: EditText = findViewById(R.id.message_edit_text)
//        val sendButton: ImageButton = findViewById(R.id.send_btn)
//        val bottomNav: BottomNavigationView = findViewById(R.id.nav_bar)
//        recyclerView= findViewById(R.id.recycler_view)
//        messages = mutableListOf()
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        //SET UP RECYCLER VIEW
//        messageAdapter = MessageRecyclerViewAdapter(messages)
//        recyclerView.adapter = messageAdapter
//        val llm = LinearLayoutManager(this);
//        llm.stackFromEnd = true;
//        recyclerView.layoutManager = llm;
//
//        //ON CLICK LISTENER
//        sendButton.setOnClickListener{
//            val question = messageEditText.text.toString().trim();
//            addToChat(question,Message.SENT_BY_ME);
//            messageEditText.setText("")
//            callAPI(question);
//            welcomeTextView.visibility = View.GONE
//        };
//
//        bottomNav.setOnItemSelectedListener { item ->
//            val fragment: Fragment = when (item.itemId) {
//                R.id.Guru -> FragmentGuru()
//                R.id.Notifications -> FragmentNotifications()
//                R.id.Records -> FragmentPlantRecord()
//                else -> return@setOnItemSelectedListener false
//            }
//            replaceFragment(fragment)
//            true
//        }
//        bottomNav.selectedItemId = R.id.Guru
//    }
//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager = supportFragmentManager
//        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
//    }
//}