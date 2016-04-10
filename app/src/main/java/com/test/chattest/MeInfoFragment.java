package com.test.chattest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.controller.MeinfoController;
import com.test.view.MeinfoView;

/**
 * Created by wzy on 2016/3/21.
 */
public class MeInfoFragment extends Fragment {

    private MeinfoView mMeinfoView;
    private MeinfoController mMeinfoController;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.meinfo_fragment, container, false);
        mMeinfoView  = (MeinfoView) view.findViewById(R.id.meinfoview);
        mMeinfoView.initModule(view);
        mMeinfoController = new MeinfoController(this, mMeinfoView);
        mMeinfoView.setOnClickListener(mMeinfoController);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void startLoginActvity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
