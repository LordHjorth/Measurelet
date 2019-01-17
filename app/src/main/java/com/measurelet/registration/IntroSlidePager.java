package com.measurelet.registration;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hjorth.measurelet.R;
import com.measurelet.MainActivity;
import com.measurelet.registration.transformers.ZoomOutPageTransformer;


public class IntroSlidePager extends Fragment {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // https://developer.android.com/training/animation/screen-slide#java

        super.onCreate(savedInstanceState);
        View fragment = inflater.inflate(R.layout.fragment_intro_slide_pager, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().hide();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) fragment.findViewById(R.id.pager);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);


        // Tilføj dots
        // https://stackoverflow.com/questions/20586619/android-viewpager-with-bottom-dots

        TabLayout tabLayout = (TabLayout) fragment.findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mPager, true);

        Button btn = fragment.findViewById(R.id.sign_up_btn);
        btn.setOnClickListener((view) -> {
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_termsFragment);

        });


        return fragment;
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Bundle arguments = new Bundle();
            arguments.putInt("page", position);
            IntroPageFragment p = new IntroPageFragment();
            p.setArguments(arguments);
            return p;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
