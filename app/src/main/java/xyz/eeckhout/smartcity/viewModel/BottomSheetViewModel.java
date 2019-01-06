package xyz.eeckhout.smartcity.viewModel;

import android.arch.lifecycle.ViewModel;

import com.google.android.gms.maps.model.Marker;

public class BottomSheetViewModel extends ViewModel {
    private Marker marker;

    public BottomSheetViewModel(Marker marker) {
        this.marker = marker;
    }

    public BottomSheetViewModel(){}

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
