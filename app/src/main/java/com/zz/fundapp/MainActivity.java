package com.zz.fundapp;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationBarView;
import com.zz.fundapp.base.BaseActivity;
import com.zz.fundapp.databinding.ActivityMainBinding;
import com.zz.fundapp.ui.home.FragmentHome;
import com.zz.fundapp.ui.me.FragmentUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    @Override
    protected void initView() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        HyLog.e("initView");
        setupViewPager(binding.vp);
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                HyLog.e("onNavigationItemSelected :" + item.getItemId());
                if(item.getItemId() == R.id.health){
                    binding.vp.setCurrentItem(0);
                }else if(item.getItemId() == R.id.me){
                    binding.vp.setCurrentItem(1);
                }
                return true;
            }
        });
        binding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                HyLog.e("onPageSelected :" + position);
                binding.bottomNavigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    public class mAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        public mAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            fragmentList=fragments;
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragmentList.get(position);
            return fragment;
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentHome());
        fragmentList.add(new FragmentUser());
        mAdapter adapter = new mAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
    }
}