package com.luolingchen.mydaily.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luolingchen.mydaily.R;

/**
 * Created by luolingchen on 2016-07-24.
 */
public class MainFragment extends Fragment {

    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = View.inflate(mActivity,R.layout.fragment_main,null);
        return view;
    }
}
