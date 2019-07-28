package com.iflytek.tab1.errorbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iflytek.tab1.errorbook.R;
import com.iflytek.tab1.errorbook.utill.ApkUtill;
import com.iflytek.tab1.errorbook.utill.AppInfoAdapter;

import static java.lang.Thread.sleep;


public class HiddenAppFragment extends Fragment {
    private View mView;
    private RecyclerView mRecyclerView;
    private AppInfoAdapter mAdapter;
    private SwipeRefreshLayout Sfl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_hiddenapp, container, false);
        mRecyclerView = (RecyclerView)mView.findViewById(R.id.ListOfApp);
        Sfl = (SwipeRefreshLayout)mView.findViewById(R.id.reRefreshOfHidden);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mAdapter = new AppInfoAdapter(getContext(),new ApkUtill(MyApplication.getContext()).getAllThirtAppInfo());
        mRecyclerView.setAdapter(mAdapter);
        Sfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setmAppInfo(new ApkUtill(MyApplication.getContext()).getAllThirtAppInfo());
                mAdapter.notifyDataSetChanged();
                Sfl.setRefreshing(false);
            }
        });
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        return mView;
    }

    public AppInfoAdapter getmAdapter() {
        return mAdapter;
    }

}
