package xyz.eeckhout.smartcity.controller;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import org.json.JSONObject;

import xyz.eeckhout.smartcity.R;
import xyz.eeckhout.smartcity.model.ServiceGetDTO;
import xyz.eeckhout.smartcity.viewModel.BottomSheetViewModel;
import xyz.eeckhout.smartcity.viewModel.ServiceViewModel;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private BottomSheetViewModel viewModel;
    private ServiceViewModel serviceViewModel;
    private Marker marker;
    private View goButton;
    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(BottomSheetViewModel.class);
        serviceViewModel = ViewModelProviders.of(getActivity()).get(ServiceViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
        //Instancier vos composants graphique ici (faîtes vos findViewById)

        TextView title = view.findViewById(R.id.preview);
        TextView places = view.findViewById(R.id.share);
        goButton = view.findViewById(R.id.bottom_sheet_go);
        final ServiceGetDTO service = (ServiceGetDTO) viewModel.getMarker().getTag();
        title.setText(service.getServiceName());
        try {
            JSONObject object = service.getFacultativeValue() != null && !service.getFacultativeValue().isEmpty() ? new JSONObject(service.getFacultativeValue()) : null;
            switch (service.getCategoryId()) {
                case 1:
                    int availableBikes = object != null ? object.has("AvailableBikes") ? object.getInt("AvailableBikes") : 0 : 0;
                    places.setText("Disponibilités : "+availableBikes);
                    break;
                case 2:
                    places.setText(object != null ? "Nb places : " + object.getInt("Places") : "");
                    break;
                case 3:
                    places.setText("Nb places : " + object.getString("Places"));
                    break;
            }
        }
        catch(Exception e){
            Log.i("rrreur", e.getMessage());
        }

        View viewAll = view.findViewById(R.id.bottom_sheet_details);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceViewModel.getService(service.getId());
                dismiss();
                ((MainActivity) getActivity()).getServiceFragment();
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+service.getLatitude()+","+service.getLongitude());

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);

            }
        });
        return view;
    }

}
