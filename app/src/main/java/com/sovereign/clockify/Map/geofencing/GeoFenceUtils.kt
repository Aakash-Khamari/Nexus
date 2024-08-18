package com.sovereign.clockify.Map.geofencing

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

object GeofenceUtils {

    fun createGeofence(context: Context, officeLatLng: LatLng, officeId: String) {
        val geofence = Geofence.Builder()
            .setRequestId(officeId)
            .setCircularRegion(
                officeLatLng.latitude,
                officeLatLng.longitude,
                200f // Radius in meters
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        val geofencingClient = LocationServices.getGeofencingClient(context)
        geofencingClient.addGeofences(geofencingRequest, getGeofencePendingIntent(context))
            .addOnSuccessListener {
                // Geofence added successfully
            }
            .addOnFailureListener {
                // Failed to add geofence
            }
    }

    private fun getGeofencePendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}
