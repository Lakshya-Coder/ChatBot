package com.lakshyagupta7089.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lakshyagupta7089.chatbot.adapter.ChatAdapter
import com.lakshyagupta7089.chatbot.databinding.ActivityMainBinding
import com.lakshyagupta7089.chatbot.model.Chats
import com.lakshyagupta7089.chatbot.model.Message
import com.lakshyagupta7089.chatbot.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var chats: ArrayList<Chats>? = null
    private var adapter: ChatAdapter? = null
    private var layoutManager: LinearLayoutManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        chats = ArrayList()

        adapter = ChatAdapter(chats, this)
        binding!!.chatsRecyclerView.adapter = adapter

        layoutManager = LinearLayoutManager(this)
        binding!!.chatsRecyclerView.layoutManager = layoutManager

        binding!!.sendFloatingActionButton.setOnClickListener {
            if (binding!!.messageEditText.text.toString().isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter a message",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                getResponse(binding!!.messageEditText.text.toString())
                binding!!.messageEditText.setText("")
            }
        }
    }

    private fun getResponse(message: String) {
        chats!!.add(Chats(message, Util.USER))
        adapter!!.notifyDataSetChanged()

        val url =
            "http://api.brainshop.ai/get?bid=158090&key=ydUh8dFRUoKT2WKA&uid=[uid]&msg=$message"
        val BASE_URL = "http://api.brainshop.ai/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)
        val call: Call<Message> = retrofitAPI.getMessage(url)
        call.enqueue(object: Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                if (response.isSuccessful) {
                    val model: Message? = response.body()

                    chats!!.add(Chats(model!!.cnt, Util.BOT))
                    layoutManager!!.scrollToPosition(chats!!.size-1)
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                chats!!.add(Chats(t.message!!, Util.BOT))
                adapter!!.notifyDataSetChanged()
            }

        })
    }
}
