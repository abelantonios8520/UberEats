package com.abelsalcedo.ubereats.providers;

import com.abelsalcedo.ubereats.models.Delivery;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DeliveryProvider {
    DatabaseReference mDatabase;

    public DeliveryProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Deliverys");
    }

    public Task<Void> create(Delivery delivery) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", delivery.getName());
        map.put("ape", delivery.getApe());
        map.put("dni", delivery.getDni());
        map.put("telef", delivery.getTelf());
        map.put("email", delivery.getEmail());
        return mDatabase.child(delivery.getId()).setValue(map);
    }
}
