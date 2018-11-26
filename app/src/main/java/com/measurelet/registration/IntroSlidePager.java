package com.measurelet.registration;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hjorth.measurelet.R;
import com.measurelet.registration.transformers.ZoomOutPageTransformer;

/**
 * A simple {@link Fragment} subclass.
 */
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


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) fragment.findViewById(R.id.pager);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        // Tilføj dots
        // https://stackoverflow.com/questions/20586619/android-viewpager-with-bottom-dots

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
