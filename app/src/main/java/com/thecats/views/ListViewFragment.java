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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.thecats.MainActivity;
import com.thecats.R;
import com.thecats.adpaters.MyItemRecyclerViewAdapter;
import com.thecats.databinding.ListViewFragmentBinding;
import com.thecats.models.CatsImage;

import java.util.List;

import javax.inject.Inject;

public class ListViewFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory factory;

    MyItemRecyclerViewAdapter adapter;


    ListViewFragmentBinding binding;
    List<CatsImage> catsImages;

    public static ListViewFragment newInstance() {
        return new ListViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.list_view_fragment, container, false);
        View view = binding.getRoot();
        binding.tvNewPost.setVisibility(View.GONE);

        binding.tvNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                adapter = new MyItemRecyclerViewAdapter(catsImages, requireActivity());
                binding.recyclerView.setAdapter(adapter);
                view.setVisibility(View.GONE);
            }
        });

        // setting the image in the imageView
        Glide.with(this)
                .load(getActivity().getDrawable(R.drawable.exp_loader_new))
                .into(binding.imgView);

        if (catsImages != null) {
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            adapter = new MyItemRecyclerViewAdapter(catsImages, requireActivity());
            binding.recyclerView.setAdapter(adapter);
            binding.imgView.setVisibility(View.GONE);
        } else
            binding.imgView.setVisibility(View.VISIBLE);

        if (isNetworkAvailable())
            MainActivity.listViewViewModel.getDatumMutableLiveData().observe(requireActivity(), userListUpdateObserverListView);
        else
            Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    Observer<List<CatsImage>> userListUpdateObserverListView = new Observer<List<CatsImage>>() {
        @Override
        public void onChanged(List<CatsImage> userArrayList) {
            catsImages = userArrayList;
            if (MainActivity.listViewFragment.isVisible()) {
                if (adapter == null) {
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    adapter = new MyItemRecyclerViewAdapter(catsImages, requireActivity());
                    binding.recyclerView.setAdapter(adapter);
                } else
                    binding.tvNewPost.setVisibility(View.VISIBLE);
                binding.imgView.setVisibility(View.GONE);

            }
        }
    };

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}