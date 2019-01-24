package com.measurelet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    class ValueEventListenerContainer {

        public DatabaseReference db;
        public ValueEventListener listener;

        public ValueEventListenerContainer(DatabaseReference db, ValueEventListener listener) {

            this.db = db;
            this.listener = listener;

        }
    }

    private List<AsyncTask> tasks;
    private List<ValueEventListenerContainer> valueEventListeners;

    public void addAsyncTask(AsyncTask t) {
        tasks.add(t);
        t.execute();
    }

    public void addListener(DatabaseReference db, ValueEventListener l) {
        valueEventListeners.add(new ValueEventListenerContainer(db, l));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tasks = new ArrayList();
        valueEventListeners = new ArrayList();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (ValueEventListenerContainer v : valueEventListeners ) {
            v.db.addValueEventListener(v.listener);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        for (AsyncTask t : tasks
        ) {
            t.cancel(true);
        }

        for (ValueEventListenerContainer v : valueEventListeners
        ) {
            v.db.removeEventListener(v.listener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tasks.clear();
        valueEventListeners.clear();
    }

}

