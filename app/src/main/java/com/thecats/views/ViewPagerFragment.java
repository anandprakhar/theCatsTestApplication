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
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.thecats.MainActivity;
import com.thecats.R;
import com.thecats.adpaters.ViewPagerAdapter;
import com.thecats.databinding.ViewPagerFragmentBinding;
import com.thecats.models.CatsImage;

import java.util.List;

import javax.inject.Inject;

public class ViewPagerFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory factory;

    public static ViewPagerFragment newInstance() {
        return new ViewPagerFragment();
    }

    // images array
//    int[] images = {R.drawable.the_cat_api_logo, R.drawable.the_cat_api_logo, R.drawable.the_cat_api_logo, };
    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;
    ViewPagerFragmentBinding binding;
    List<CatsImage> catsImages;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.view_pager_fragment, container, false);
        View view = binding.getRoot();

        binding.tvNewPost.setVisibility(View.GONE);

        binding.tvNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPagerAdapter = new ViewPagerAdapter(requireContext(), catsImages);
                binding.viewpager.setAdapter(mViewPagerAdapter);
                binding.imgView.setVisibility(View.GONE);
                binding.tvNewPost.setVisibility(View.GONE);
            }
        });

        // setting the image in the imageView
        Glide.with(this)
                .load(getActivity().getDrawable(R.drawable.exp_loader_new))
                .into(binding.imgView);

        if (catsImages != null) {
            mViewPagerAdapter = new ViewPagerAdapter(requireContext(), catsImages);
            binding.viewpager.setAdapter(mViewPagerAdapter);
            binding.imgView.setVisibility(View.GONE);
        } else
            binding.imgView.setVisibility(View.VISIBLE);

        binding.rightArrowImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() + 1);
            }
        });

        binding.leftArrowImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() - 1);
            }
        });

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    binding.leftArrowImgView.setVisibility(View.GONE);
                    binding.rightArrowImgView.setVisibility(View.VISIBLE);
                } else if (position == catsImages.size() - 1) {
                    binding.leftArrowImgView.setVisibility(View.VISIBLE);
                    binding.rightArrowImgView.setVisibility(View.GONE);

                } else {
                    binding.leftArrowImgView.setVisibility(View.VISIBLE);
                    binding.rightArrowImgView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (isNetworkAvailable())
            MainActivity.viewPagerViewModel.getDatumMutableLiveData().observe(requireActivity(), userListUpdateObserver);
        else
            Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        return view;


    }

    Observer<List<CatsImage>> userListUpdateObserver = new Observer<List<CatsImage>>() {
        @Override
        public void onChanged(List<CatsImage> userArrayList) {
            catsImages = userArrayList;
            if (MainActivity.viewPagerFragment.isVisible()) {
                if (mViewPagerAdapter == null) {
                    mViewPagerAdapter = new ViewPagerAdapter(requireContext(), catsImages);
                    binding.viewpager.setAdapter(mViewPagerAdapter);
                } else
                    binding.tvNewPost.setVisibility(View.VISIBLE);
                binding.imgView.setVisibility(View.GONE);
            }
        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}