package xyz.eeckhout.smartcity.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.eeckhout.smartcity.R;


public class RGPDFragment extends Fragment {

    public RGPDFragment() {
        // Required empty public constructor
    }

    public static RGPDFragment newInstance() {
        RGPDFragment fragment = new RGPDFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rgpd, container, false);
    }
}
