package com.example.ticketmaster

import android.content.Intent
import com.example.ticketmaster.adapter.EventAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaster.api.ApiClient
import com.example.ticketmaster.api.EventResponse
import com.ticketmaster.discoveryapi.enums.TMMarketDomain
import com.ticketmaster.purchase.TMPurchase
import com.ticketmaster.purchase.TMPurchaseWebsiteConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_discover)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchEvents()
    }

    private fun fetchEvents() {
        val apiKey = "C1pkjWYZmFChCKF2NO0K0mOsD11pZHpA"
        val apiClient = ApiClient.instance

        apiClient.getEvents(apiKey).enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?._embedded?.events
                    if (events != null) {
                        recyclerView.adapter = EventAdapter(events) { event ->
                            // Handle event click
                            val tmPurchase = TMPurchase(
                                apiKey = apiKey,
                                brandColor = ContextCompat.getColor(
                                    this@DiscoverActivity,
                                    R.color.black
                                )
                            )

                            val tmPurchaseWeb = TMPurchaseWebsiteConfiguration(
                                event.id,
                                TMMarketDomain.US
                            )

                            val extraInfo = ExtraInfo("US")

                            val intent = Intent(this@DiscoverActivity, PurchaseActivity::class.java).apply {
                                putExtra(TMPurchase::class.java.name, tmPurchase)
                                putExtra(TMPurchaseWebsiteConfiguration::class.java.name, tmPurchaseWeb)
                                putExtra(ExtraInfo::class.java.name, extraInfo)
                            }
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(
                            this@DiscoverActivity,
                            "No events found",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(this@DiscoverActivity, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Toast.makeText(this@DiscoverActivity, "Failure: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}