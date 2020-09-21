package com.abelsalcedo.ubereats.providers;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeofireProvider {

    private DatabaseReference mDatabase;
    private GeoFire mGeofire;

    public GeofireProvider () {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("active_delivery");
        mGeofire = new GeoFire(mDatabase);
    }

    public void saveLocation(String idDelivery, LatLng latLng) {
        mGeofire.setLocation(idDelivery, new GeoLocation(latLng.latitude, latLng.longitude));
    }

    public void removeLocation(String idDelivery) {
        mGeofire.removeLocation(idDelivery);
    }

    public GeoQuery getActiveDelivery(LatLng latLng) {
        GeoQuery geoQuery = mGeofire.queryAtLocation(new GeoLocation(latLng.latitude, latLng.longitude), 10);
        geoQuery.removeAllListeners();
        return geoQuery;
    }
}
