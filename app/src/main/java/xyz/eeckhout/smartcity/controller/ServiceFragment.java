package xyz.eeckhout.smartcity.controller;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import xyz.eeckhout.smartcity.R;
import xyz.eeckhout.smartcity.adapter.OpinionAdapter;
import xyz.eeckhout.smartcity.adapter.PhotoAdapter;
import xyz.eeckhout.smartcity.dataAccess.DeleteOpinionAT;
import xyz.eeckhout.smartcity.dataAccess.PutOpinionAT;
import xyz.eeckhout.smartcity.model.PhotoDTO;
import xyz.eeckhout.smartcity.model.ServiceDTO;
import xyz.eeckhout.smartcity.model.ServiceOpinionDTO;
import xyz.eeckhout.smartcity.viewModel.ServiceViewModel;

public class ServiceFragment extends Fragment implements PutOpinionAT.GetOpinionsATResults, DeleteOpinionAT.DeleteOpinionsATResults {

    private ServiceViewModel mViewModel;
    private TextView serviceDisponibilite;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int currentPage = 1;
    private int pageSize = 5;
    private RecyclerView mPhotoRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mOpinionsRecyclerView;
    private RecyclerView.Adapter mAdapterOpinions;
    private RecyclerView.LayoutManager mLayoutManagerOpinions;
    private TextView photoEmptyView;
    private TextView opinionsEMmptyView;
    private String token;
    private List<PhotoDTO> photos = null;
    private int serviceId;
    public static ServiceFragment newInstance() {
        return new ServiceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).checkIsLogged();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_fragment, container, false);
        serviceDisponibilite = view.findViewById(R.id.service_disponibilite);

        mPhotoRecyclerView = (RecyclerView) view.findViewById(R.id.service_recyclerview_photo);
        photoEmptyView = view.findViewById(R.id.service_photo_empty);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mPhotoRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mPhotoRecyclerView.setLayoutManager(mLayoutManager);

        mOpinionsRecyclerView = (RecyclerView) view.findViewById(R.id.service_recyclerviewer_opinion);
        mOpinionsRecyclerView.setHasFixedSize(true);
        opinionsEMmptyView = view.findViewById(R.id.service_opinions_empty);

        // use a linear layout manager
        mLayoutManagerOpinions = new LinearLayoutManager(getContext());
        mOpinionsRecyclerView.setLayoutManager(mLayoutManagerOpinions);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("accessToken", "");
        Log.i("erreur", token);
        mViewModel = ViewModelProviders.of(getActivity()).get(ServiceViewModel.class);

        final Observer<ServiceDTO> serviceObserver = new Observer<ServiceDTO>() {
            @Override
            public void onChanged(@Nullable ServiceDTO service) {
                if(service != null) {
                    serviceId = service.getId();
                    getActivity().setTitle(service.getServiceName());
                    try {
                        JSONObject object = service.getFacultativeValue() != null && !service.getFacultativeValue().isEmpty() ? new JSONObject(service.getFacultativeValue()) : null;
                        switch (service.getCategoryId()) {
                            case 1:
                                int availableBikes = object != null ? object.has("AvailableBikes") ? object.getInt("AvailableBikes") : 0 : 0;
                                serviceDisponibilite.setText(""+availableBikes);
                                break;
                            case 2:
                                serviceDisponibilite.setText(object != null ? ""+object.getInt("Places") : "");
                                break;
                            case 3:
                                serviceDisponibilite.setText(object.getString("Places"));
                                break;
                        }
                        getPhotos();
                        getOpinions();
                    }
                    catch(Exception e){
                        Log.i("rrreur", e.getMessage());
                    }
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.getService().observe(this, serviceObserver);
    }

    public void updateOpinion(ServiceOpinionDTO opinionDTO){
        opinionDTO.setIsVisible(false);
        new PutOpinionAT(opinionDTO, token, this).execute();
    }

    public void deleteOpinion(ServiceOpinionDTO opinionDTO){
        new DeleteOpinionAT(opinionDTO, token, this).execute();
    }

    public void getPhotos(){
        final Observer<List<PhotoDTO>> photosObserver = new Observer<List<PhotoDTO>>() {
            @Override
            public void onChanged(@Nullable List<PhotoDTO> photos) {
                if(photos != null && photos.size() > 0) {
                    Log.i("erreur", photos.size()+"");
                    mAdapter = new PhotoAdapter(photos, ServiceFragment.this);
                    mPhotoRecyclerView.setAdapter(mAdapter);
                    mPhotoRecyclerView.setVisibility(View.VISIBLE);
                    photoEmptyView.setVisibility(View.GONE);
                }
                else {
                    mPhotoRecyclerView.setVisibility(View.GONE);
                    photoEmptyView.setVisibility(View.VISIBLE);
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.getPhotos(serviceId,0,5,PreferenceManager.getDefaultSharedPreferences(getContext()).getString("accessToken", "")).observe(this, photosObserver);

    }

    public void getOpinions(){
        final Observer<List<ServiceOpinionDTO>> opinionsObserver = new Observer<List<ServiceOpinionDTO>>() {
            @Override
            public void onChanged(@Nullable List<ServiceOpinionDTO> opinions) {
                if(opinions != null && opinions.size() > 0) {
                    mAdapterOpinions = new OpinionAdapter(opinions, token, ServiceFragment.this);
                    mOpinionsRecyclerView.setAdapter(mAdapterOpinions);
                    mOpinionsRecyclerView.setVisibility(View.VISIBLE);
                    opinionsEMmptyView.setVisibility(View.GONE);
                }
                else {
                    mOpinionsRecyclerView.setVisibility(View.GONE);
                    opinionsEMmptyView.setVisibility(View.VISIBLE);
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.getOpinions(serviceId,0,5,token).observe(this, opinionsObserver);
    }

    @Override
    public void getOpinionsATResults(Boolean updateSuccess) {
        if(updateSuccess){
            getOpinions();
        }
    }

    @Override
    public void getOpinionsATResultsError(Exception exception) {
        Log.i("erreur", exception.getMessage());
    }

    @Override
    public void deleteOpinionsATResults(Boolean deleteSuccess) {
        if(deleteSuccess){
            getOpinions();
        }
    }

    @Override
    public void deleteOpinionsATResultsError(Exception exception) {
        Log.i("erreur", exception.getMessage());
    }
}
