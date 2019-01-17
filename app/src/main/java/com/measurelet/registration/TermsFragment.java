package com.measurelet.registration;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.measurelet.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TermsFragment extends Fragment implements View.OnClickListener {


    public TermsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_terms, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().hide();

        InputStream iStream = getContext().getResources().openRawResource(R.raw.terms);
        ByteArrayOutputStream byteStream = null;
        try {
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            byteStream = new ByteArrayOutputStream();
            byteStream.write(buffer);
            byteStream.close();
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String html = byteStream.toString();

        ((TextView) fragment.findViewById(R.id.terms_text)).setText(Html.fromHtml(html));


        fragment.findViewById(R.id.terms_accept_btn).setOnClickListener(this);

        return fragment;
    }

    @Override
    public void onClick(View view) {

        ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_signupFragment);

    }
}
