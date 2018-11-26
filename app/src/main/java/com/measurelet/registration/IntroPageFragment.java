package com.measurelet.registration;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hjorth.measurelet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroPageFragment extends Fragment {

    public IntroPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int page = getArguments().getInt("page", 1);

        System.out.println(page);

        View fragment;

        switch (page){
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


        Button btn = fragment.findViewById(R.id.sign_up_btn);
        btn.setOnClickListener((view) -> {
            // Skift til T&C Screen
            TermsFragment terms = new TermsFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_frame, terms).addToBackStack(null).commit();

        });

        return fragment;
    }

}
