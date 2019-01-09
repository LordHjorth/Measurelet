package com.measurelet.Database.Database_Online;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public abstract class OnlineDatabase {

    private static FirebaseDatabase DB_INSTANCE;
    private static DatabaseReference DB_REFERENCE;

    public static DatabaseReference getAppDatabase() {


        if (DB_INSTANCE == null) {
            DB_INSTANCE = FirebaseDatabase.getInstance();
            DB_INSTANCE.setPersistenceEnabled(true);
            Log.d("Instance", "Instance created!");
        }
        if(DB_REFERENCE == null){
            DB_REFERENCE = DB_INSTANCE.getReference();
            Log.d("Reference", "Reference created!");
        }
        return DB_REFERENCE;
    }

    public static DatabaseReference getChildDBReference(List<String> childNames){
        DatabaseReference childRef = getAppDatabase();
        for(String name : childNames){
            childRef = childRef.child(name);
        }
        return childRef;
    }

    private static ValueEventListener update = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            System.out.println("Succeeded");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            System.out.println("Cancelled");
        }
    };
}
