package com.example.weatherapp.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherapp.R
import com.example.weatherapp.app.getCity
import com.example.weatherapp.fragments.BaseFragment
import com.example.weatherapp.fragments.HomeFragment
import com.example.weatherapp.fragments.SplashFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity(), Navigator {

    companion object {
        const val LOCATION_REQ_CODE = 456
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateWithoutBackStack(SplashFragment.newInstance())
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQ_CODE
            )
        } else {
            getLastLocation()
        }
    }

    private fun getLastLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    navigateWithoutBackStack(HomeFragment.newInstance(it.getCity(this)))
                } ?: run {
                    continueWithDefaultValue()
                }
            }
            .addOnFailureListener {
                continueWithDefaultValue()
            }
    }

    override fun navigateWithBackStack(fragment: BaseFragment, tag: String?) {
        supportFragmentManager.beginTransaction().replace(R.id.containerLayout, fragment)
            .addToBackStack(tag).commit()
    }

    override fun navigateWithoutBackStack(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction().replace(R.id.containerLayout, fragment).commit()
    }

    private fun continueWithDefaultValue() {
        navigateWithoutBackStack(HomeFragment.newInstance(null))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_REQ_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLastLocation()
                } else {
                    continueWithDefaultValue()
                }
                return
            }
        }
    }
}
