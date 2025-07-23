package com.example.helldiversplanetstatus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONObject

class HelldiverAdapter(private val planetList: List<JSONObject>): RecyclerView.Adapter<HelldiverAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val planetImage: ImageView
        val nameOfPlanet: TextView
        val libPercentage: TextView
        val planetDiscription: TextView
        init {
            // Find our RecyclerView item's ImageView for future use
            planetImage = view.findViewById(R.id.PlanetImage)
            nameOfPlanet = view.findViewById(R.id.planetName)
            libPercentage = view.findViewById(R.id.Liberation_Percentage)
            planetDiscription = view.findViewById(R.id.planetDescription)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.helldiverplanetitems, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val planetInfo: JSONObject = planetList[position]
        if(!planetInfo.isNull("biome")) {
                    holder.planetDiscription.setText(planetInfo.getJSONObject("biome").getString("description"))
                } else {
                    holder.planetDiscription.setText("Planet Description Error code 404: Not Found")
                }
        holder.nameOfPlanet.setText(planetInfo.getString("name"))
        holder.libPercentage.setText(planetInfo.getString("percentage"))
        val faction: String = planetInfo.getString("faction")
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
        Glide.with(holder.itemView)
            .load(planet)
            .centerCrop()
            .into(holder.planetImage)
    }

    override fun getItemCount() = planetList.size
}