package com.example.workout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions


class Maps : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var listDouble: ArrayList<Double>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = this.intent.extras!!.getString("title")
        listDouble = intent.getSerializableExtra("arrayList") as ArrayList<Double>
        // Retrieve the content view that renders the map.
        setContentView(R.layout.maps)
        setTitle(title)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val arrayLatLng = convertToLatLng(listDouble)
        println(arrayLatLng)
        val polyline1 = googleMap.addPolyline(PolylineOptions()
                .addAll(arrayLatLng).width(3f))

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayLatLng[0], 20f))

    }

    fun convertToLatLng(arrayDouble: ArrayList<Double>): MutableList<LatLng>{
        var arrayLatLng : MutableList<LatLng> = ArrayList()
        for (i in 0 until arrayDouble.size/2){
            arrayLatLng.add(LatLng(arrayDouble[i * 2], arrayDouble[(i * 2) + 1]))
        }
        return (arrayLatLng)
    }

    private fun <T> List<T>.toArrayList(): ArrayList<T>{
        return ArrayList(this)
    }

}