package com.thecats.views;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.thecats.MainActivity;
import com.thecats.R;
import com.thecats.adpaters.CustomAdapter;
import com.thecats.databinding.GridViewFragmentBinding;
import com.thecats.models.CatsImage;
import com.thecats.viewmodels.GridViewViewModel;

import java.util.List;

public class GridViewFragment extends Fragment {


    public static GridViewFragment newInstance() {
        return new GridViewFragment();
    }

    GridViewFragmentBinding binding;
    List<CatsImage> catsImages;
    CustomAdapter customAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.grid_view_fragment, container, false);
        View view = binding.getRoot();

        binding.tvNewPost.setVisibility(View.GONE);
        binding.tvNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAdapter = new CustomAdapter(requireContext(), catsImages);
                binding.simpleGridView.setAdapter(customAdapter);
                binding.imgView.setVisibility(View.GONE);
                binding.tvNewPost.setVisibility(View.GONE);
            }
        });

        // setting the default loader gif in the imageView
        Glide.with(this)
                .load(getActivity().getDrawable(R.drawable.exp_loader_new))
                .into(binding.imgView);
        if (catsImages != null) {
            // Create an object of CustomAdapter and set Adapter to GirdView
             customAdapter = new CustomAdapter(requireActivity(), catsImages);
            binding.simpleGridView.setAdapter(customAdapter);
            binding.imgView.setVisibility(View.GONE);
        } else
            binding.imgView.setVisibility(View.VISIBLE);

        if (isNetworkAvailable())
            MainActivity.gridViewViewModel.getDatumMutableLiveData().observe(requireActivity(), userListUpdateObserverGridView);
        else
            Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();


        return view;


    }


    Observer<List<CatsImage>> userListUpdateObserverGridView = new Observer<List<CatsImage>>() {
        @Override
        public void onChanged(List<CatsImage> userArrayList) {
            catsImages = userArrayList;
            // Create an object of CustomAdapter and set Adapter to GirdView
            if (MainActivity.gridViewFragment.isVisible()) {
                if (customAdapter == null) {
                    customAdapter = new CustomAdapter(requireContext(), catsImages);
                    binding.simpleGridView.setAdapter(customAdapter);
                }
                else
                binding.tvNewPost.setVisibility(View.VISIBLE);
                binding.imgView.setVisibility(View.GONE);

            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}