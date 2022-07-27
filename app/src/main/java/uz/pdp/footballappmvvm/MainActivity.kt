package uz.pdp.footballappmvvm

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.telephony.SmsManager
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.permissionx.guolindev.PermissionX


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var phoneNumber = "+998944849900"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val deppData = intent.data

        val message = deppData?.pathSegments?.get(0)
        if (deppData?.pathSegments?.get(1) != null) {
            phoneNumber = deppData.pathSegments?.get(1).toString()
        }

        if (message == "location") {
            if (isGooglePlayServicesAvailable(this)) {
                getCurrentLocation()
            }
        }
    }

    private fun isGooglePlayServicesAvailable(activity: Activity?): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(activity!!)
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                Toast.makeText(activity,
                    "This device not supported Google Play Service",
                    Toast.LENGTH_SHORT).show()
            }
            return false
        }
        return true
    }

    private fun getCurrentLocation() {
        if (checkPermission()) {
            PermissionX.init(this)
                .permissions(Manifest.permission.SEND_SMS)
                .request { allGranted, _, _ ->
                    if (allGranted && phoneNumber.length == 13) {
                        if (isLocationEnabled()) {
                            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                                val locData = it.result
                                val location = if (locData != null) {
                                    "https://maps.google.com/?q=${locData.latitude},${locData.longitude}"
                                } else {
                                    "Null Received"
                                }
                                val time = DateFormat.format("dd/MM/yyyy hh:mm:ss", locData.time)
                                    .toString()
                                val msg = "Location: $location \n Time: $time"
                                Log.d("AAA", msg)
                                sendSMS(phoneNumber, msg)
                            }
                        } else {
                            Toast.makeText(this, "Turn on Location", Toast.LENGTH_SHORT).show()
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivity(intent)
                        }
                    }
                }
        } else {
            requestPermission()
        }
    }

    private fun sendSMS(phone: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phone, null, message, null, null)
            Log.d("AAA", "Message Send")
        } catch (ex: Exception) {
            Log.d("AAA", ex.message ?: "")
            Toast.makeText(applicationContext, ex.message.toString(),
                Toast.LENGTH_LONG).show()
            ex.printStackTrace()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun checkPermission(): Boolean =
        ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

}