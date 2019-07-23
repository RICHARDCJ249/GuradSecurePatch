package com.iflytek.tab1.errorbook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iflytek.tab1.errorbook.R;
import com.iflytek.tab1.errorbook.utill.MarkdownWebView;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class AboutUsFragment extends Fragment {
    MarkdownWebView mMarkdownWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_about_us, container, false);
        mMarkdownWebView = (MarkdownWebView) mView.findViewById(R.id.markdown);
        mMarkdownWebView.setText(getFromAssets("README.md"));
        return mView;
    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            bufReader.close();
            inputReader.close();
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
