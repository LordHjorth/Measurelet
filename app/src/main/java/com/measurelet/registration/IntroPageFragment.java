package com.measurelet.registration;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hjorth.measurelet.R;
import com.measurelet.MainActivity;

public class IntroPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int page = getArguments().getInt("page", 1);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        System.out.println(page);

        View fragment;

        switch (page) {
            case 0:
                fragment = inflater.inflate(R.layout.fragment_intro_page_0, container, false);
                break;
            case 1:
                fragment = inflater.inflate(R.layout.fragment_intro_page_1, container, false);
                break;
            case 2:
                fragment = inflater.inflate(R.layout.fragment_intro_page_2, container, false);
                break;
            case 3:
                fragment = inflater.inflate(R.layout.fragment_intro_page_3, container, false);
                break;
            case 4:
                fragment = inflater.inflate(R.layout.fragment_intro_page_4, container, false);
                break;
            default:
                fragment = inflater.inflate(R.layout.fragment_intro_page_4, container, false);
                break;
        }

        return fragment;
    }

}
