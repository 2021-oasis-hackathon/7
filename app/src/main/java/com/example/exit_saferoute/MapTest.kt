package com.example.exit_saferoute


import android.graphics.Color
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource

import java.util.*


class MapTest : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_test)
//        val retrofit =
        //          Retrofit.Builder().baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
        //              .addConverterFactory(GsonConverterFactory.create()).build()
//        val api = retrofit.create(NaverApi::class.java)

//        val callrequestPosition = api.getPosition("nsaeqr01dp", "5t2VWdCXQyF7IJc3E9wvqoHVBlO8UCUA8c8By54J","129.089441, 35.231100", "129.084454, 35.228982")
//        callrequestPosition.enqueue(object : Callback<RequestPositon> {
//            override fun onResponse(
//                call: Call<RequestPositon>,
//                response: Response<RequestPositon>
//            ) {
//                Log.d("apitest", "${response.raw()}")
//            }
//
//            override fun onFailure(call: Call<RequestPositon>, t: Throwable) {
//                Log.d("apitest", "fail")
//            }
//        })
        val fm = supportFragmentManager

        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onMapReady(naverMap: NaverMap) {
        val itnt = intent
        val addressName: String? = itnt.getStringExtra("position")
        val geocoder = Geocoder(this@MapTest, Locale.KOREAN)
        val results = geocoder.getFromLocationName(addressName, 1)
        val latlng = LatLng(results[0].latitude, results[0].longitude)

        this.naverMap = naverMap

        val locationOverlay = naverMap.locationOverlay

        locationOverlay.isVisible = true
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Face
        naverMap.uiSettings.isLocationButtonEnabled = true

        val marker = Marker()
        marker.position = latlng
        marker.map = naverMap

        val path = PathOverlay()
        path.color = Color.GREEN
        naverMap.addOnLocationChangeListener { location ->
            path.coords = listOf(latlng, LatLng(location.latitude, location.longitude))
            path.map = naverMap
        }



    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
