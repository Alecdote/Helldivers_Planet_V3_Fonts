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
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONObject
import kotlin.random.Random
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private fun displayPlanet(randomNum: Int) {
        val client = AsyncHttpClient()
        val Image = findViewById<ImageView>(R.id.PlanetImage)
        val percentage = findViewById<TextView>(R.id.Liberation_Percentage)
        val planetName = findViewById<TextView>(R.id.planetName)
        val description = findViewById<TextView>(R.id.planetDescription)
        client["https://helldiverstrainingmanual.com/api/v1/war/campaign", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Helldivers", "response successful: We got back $json")
                val PlanetInfo: JSONObject = json.jsonArray.getJSONObject(randomNum)
                if(!PlanetInfo.isNull("biome")) {
                    description.setText(PlanetInfo.getJSONObject("biome").getString("description"))
                } else {
                    description.setText("Planet Description Error code 404: Not Found")
                }
                planetName.setText(PlanetInfo.getString("name"))
                val faction: String = PlanetInfo.getString("faction")
                percentage.setText(PlanetInfo.getString("percentage"))
                val planet: Int
                if(faction.equals("Terminids")) {
                    planet = R.drawable.terminids
                } else if(faction.equals("Illuminate")) {
                    planet = R.drawable.illuminates
                } else if(faction.equals("Automatons")) {
                    planet = R.drawable.automatons
                } else {
                    planet = R.drawable.superearth
                }

                Glide.with(this@MainActivity)
                    .load(planet)
                    .fitCenter()
                    .into(Image)
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
        setupButton(findViewById(R.id.changePlanetButton))
    }

    private fun setupButton(button: Button) {
        button.setOnClickListener{
            val randomNum: Int = Random.nextInt(0, 30)
            displayPlanet(randomNum)
        }
    }
}