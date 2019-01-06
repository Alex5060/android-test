package xyz.eeckhout.smartcity.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.eeckhout.smartcity.R;


public class AuthorsFragment extends Fragment {

    public AuthorsFragment() {
        // Required empty public constructor
    }

    public static AuthorsFragment newInstance() {
        AuthorsFragment fragment = new AuthorsFragment();
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
        return inflater.inflate(R.layout.fragment_authors, container, false);
    }
}
