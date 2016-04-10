package com.test.chattest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.adapter.ConstractListAdapter;
import com.test.controller.ConstractController;
import com.test.view.ConstractView;

/**
 * Created by wzy on 2016/3/21.
 */
public class ConstractFragment extends Fragment{

    private ConstractView constractView;
    private ConstractController constractController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.constract_fragment, container, false);
        constractView = new ConstractView(this, view);
        constractView.initModule();
        constractController = new ConstractController(constractView, getContext());
        constractView.setOnItemClickListener(constractController);
        constractView.setOnItemLongClickListener(constractController);
        return view;
    }

    public ConstractController getConstractController(){
        return constractController;
    }
}
