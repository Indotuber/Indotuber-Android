package com.project.indotuber.viewcontroller.mainView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.indotuber.R;
import com.project.indotuber.singleton.AppController;

/**
 * Created by yoasfs on 3/25/16.
 */
public class FAQPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq_page, container, false);
        return view;
    }

    public void onResume() {
        super.onResume();
        // Tracking the screen view
        AppController.getInstance().trackScreenView("FAQ Page Fragment");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

}
