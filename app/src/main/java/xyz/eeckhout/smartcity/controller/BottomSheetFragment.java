package xyz.eeckhout.smartcity.controller;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import xyz.eeckhout.smartcity.R;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private BottomSheetViewModel viewModel;
    private Marker marker;
    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(BottomSheetViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
        //Instancier vos composants graphique ici (faîtes vos findViewById)

        TextView preview = view.findViewById(R.id.preview);
        preview.setText(viewModel.getMarker().getTitle());

        return view;
    }

}
