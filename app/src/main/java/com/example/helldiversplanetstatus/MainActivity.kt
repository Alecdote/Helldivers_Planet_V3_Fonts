package com.example.helldiversplanetstatus

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONObject
import kotlin.random.Random
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var planetList: MutableList<JSONObject>
    private lateinit var rvPlanets: RecyclerView

    private fun displayPlanets() {
        val client = AsyncHttpClient()
        client["https://helldiverstrainingmanual.com/api/v1/war/campaign", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Helldivers", "response successful: We got back $json")

                for (i in 0 until json.jsonArray.length()) {
                    planetList.add(json.jsonArray.getJSONObject(i))
                }
                rvPlanets.adapter = HelldiverAdapter(planetList)
                rvPlanets.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Planet Error", errorResponse)
            }
        }]
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvPlanets = findViewById(R.id.planet_list_view)
        planetList = mutableListOf()
        displayPlanets()
    }

//    private fun setupButton(button: Button) { //Previously there was a button implemented
//        button.setOnClickListener{
//            displayPlanet(randomNum)
//        }
//    }
}