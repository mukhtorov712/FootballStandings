package uz.pdp.footballappmvvm.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit

class LocationHelper(private val context: Context) {
    // FusedLocationProviderClient - Main class for receiving location updates.
    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    // LocationRequest - Requirements for the location updates, i.e.,
    // how often you should receive updates, the priority, etc.
    private var locationRequest: LocationRequest = LocationRequest.create().apply {
        // Sets the desired interval for
        // active location updates.
        // This interval is inexact.
        interval = TimeUnit.SECONDS.toMillis(60)

        // Sets the fastest rate for active location updates.
        // This interval is exact, and your application will never
        // receive updates more frequently than this value
        fastestInterval = TimeUnit.SECONDS.toMillis(30)

        // Sets the maximum time when batched location
        // updates are delivered. Updates may be
        // delivered sooner than this interval
        maxWaitTime = TimeUnit.MINUTES.toMillis(2)

        priority = Priority.PRIORITY_HIGH_ACCURACY
    }

    // LocationCallback - Called when FusedLocationProviderClient
    // has a new Location
    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.lastLocation?.let {
                currentLocation = it
                // use latitude and longitude as per your need
            } ?: run {
                Log.d(TAG, "Location information isn't available.")
            }
        }
    }

    // This will store current location info
    var currentLocation: Location? = null


    companion object {
        private const val TAG = "Loc"
    }


    fun requestUpdate() {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.myLooper())
        }

    }

    fun removeUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Location Callback removed.")
                } else {
                    Log.d(TAG, "Failed to remove Location Callback.")
                }
            }
    }
}