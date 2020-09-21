package com.vj.yoga.spyss.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vj.yoga.spyss.R;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class EventsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }
}
