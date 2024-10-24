package com.sameer.silentecho.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sameer.silentecho.R
import com.sameer.silentecho.model.AixplainRequest
import com.sameer.silentecho.model.AixplainResponse
import com.sameer.silentecho.view.AixplainApiService
import com.sameer.silentecho.view.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ButtonBarLayout
import androidx.cardview.widget.CardView
import com.sameer.silentecho.databinding.FragmentChatBinding
import com.sameer.silentecho.model.PollingResponse
import java.lang.StringBuilder
import java.util.Timer
import java.util.TimerTask

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    private lateinit var pollingTimer: Timer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChatBinding.inflate(inflater,container,false)
        val view = binding.root

        if(binding.meCardView.visibility == View.VISIBLE || binding.meCardView.visibility == View.VISIBLE) {
            binding.autoText.visibility = View.GONE
        }
        else {
            autoText()
        }

        binding.chatSend.setOnClickListener {
            val message = binding.chatText.text.toString()
            if(message.isNotEmpty()) {
                binding.meCardView.visibility = View.VISIBLE
                binding.meTextView.text = message

                binding.youCardView.visibility = View.GONE
                binding.autoText.visibility = View.GONE

                binding.chatText.setText("")
                runModel(message)
            }
        }
        return view
    }

    fun autoText() {
        val label = "Hey, This is Team Silent Echo and Welcome to Aixplain's AiCodex"
        val stringBuilder = StringBuilder()
        Thread {
            try {
                for (letter in label) {
                    stringBuilder.append(letter)
                    Thread.sleep(40)
                    requireActivity().runOnUiThread {
                        // Check if the fragment is still added and activity is not null
                        if (isAdded && !isDetached && requireActivity() != null) {
                            binding.autoText.text = stringBuilder.toString()
                        }
                    }
                }
            } catch (e: InterruptedException) {
                // Handle the interruption
                e.printStackTrace()
            } catch (e: Exception) {
                // Handle other exceptions
                e.printStackTrace()
            }
        }.start()

    }

    private fun runModel(inputText: String) {
        val request = AixplainRequest(text = inputText)

        ApiClient.apiService.runModel(request).enqueue(object : Callback<AixplainResponse> {
            override fun onResponse(call: Call<AixplainResponse>, response: Response<AixplainResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val urlToPoll = response.body()!!.data
                    startPolling(urlToPoll)
                } else {
                    Log.e("ChatBot", "Response: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<AixplainResponse>, t: Throwable) {
                Log.e("ChatBot", t.message ?: "Unknown error")
            }
        })
    }

    private fun startPolling(urlToPoll: String) {
        pollingTimer = Timer()
        pollingTimer.schedule(object : TimerTask() {
            override fun run() {
                ApiClient.apiService.pollModel(urlToPoll).enqueue(object : Callback<PollingResponse> {
                    override fun onResponse(call: Call<PollingResponse>, response: Response<PollingResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            if (response.body()!!.completed) {
                                pollingTimer.cancel()
                                if(response.body()!!.data != null) {
                                    Log.d("ChatBot", response.body()!!.data)
                                    binding.youCardView.visibility = View.VISIBLE
                                    binding.youTextView.text = response.body()!!.data
                                }
                            }
                        }
                    }
                    override fun onFailure(call: Call<PollingResponse>, t: Throwable) {
                        Log.e("ChatBot", t.message ?: "Unknown error")
                    }
                })
            }
        }, 0, 5000) // Poll every 5 seconds
    }
}