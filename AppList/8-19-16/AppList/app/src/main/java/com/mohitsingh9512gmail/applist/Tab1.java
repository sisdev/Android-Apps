package com.mohitsingh9512gmail.applist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.mohitsingh9512gmail.applist.adapters.AnalysisAdapter;
import com.mohitsingh9512gmail.applist.adapters.ApkAdapter;
import com.mohitsingh9512gmail.applist.adapters.GridAdpater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohit Singh on 8/4/2016.
 */
//Our class extending fragment
public class Tab1 extends Fragment {

    PackageManager packageManager;
    ListView apkList;
    GridView apkList1;
    View view;
    List<PackageInfo> packageList1;
    SharedPreferences sharedPreferences1;
    public static final String MyPREFERENCES1="Analysis";
    SharedPreferences sharedPreferences5;
    public static final String MyPREFERENCES5 = "AppSettings" ;
    public static int maxAnalysisValue=0;
    int grid_view_status;

    /**
     * Return whether the given PackgeInfo represents a system package or not.
     * User-installed packages (Market or otherwise) should not be denoted as
     * system packages.
     *
     * @param pkgInfo
     * @return boolean
     */
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
                : false;
    }

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sharedPreferences5=getActivity().getSharedPreferences(MyPREFERENCES5, Context.MODE_APPEND);

        if(sharedPreferences5.getInt("grid_view_status",-1)==-1)
        {
            SharedPreferences.Editor editor=sharedPreferences5.edit();
            editor.putInt("grid_view_status",0);
            editor.apply();

        }
        else
        {
            grid_view_status=sharedPreferences5.getInt("grid_view_status",-1);
        }
        Log.v("tag","onCreateView of Tab1");
        if(grid_view_status==1)
        {
            view=inflater.inflate(R.layout.tab1_grid, container, false);
        }
        else
        {
            view=inflater.inflate(R.layout.tab1_list, container, false);
        }

        packageManager = getActivity().getPackageManager();

        sharedPreferences1=getActivity().getSharedPreferences(MyPREFERENCES1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences1.edit();

        List<PackageInfo> packageList = packageManager
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);

        packageList1 = new ArrayList<PackageInfo>();
        maxAnalysisValue=0;
        /*
         * Does not Work
        Collections.sort(packageList1, new Comparator<PackageInfo>() {

            @Override
            public int compare(PackageInfo lhs, PackageInfo rhs) {

                return packageManager.getApplicationLabel(
                        lhs.applicationInfo).toString().compareTo(packageManager.getApplicationLabel(
                        lhs.applicationInfo).toString());
            }
        });
        */

        /*To filter out System apps*/
        for(PackageInfo pi : packageList) {
            boolean b = isSystemPackage(pi);
            if(!b) {
                packageList1.add(pi);

            }
        }
         /* Making shared preference*/
        for(PackageInfo pi:packageList1)
        {

            if(sharedPreferences1.getInt(pi.packageName,-1)==-1)
            {
                editor.putInt(pi.packageName,0);
            }
            else
            {
                maxAnalysisValue=maxAnalysisValue+sharedPreferences1.getInt(pi.packageName,0);
            }
        }
        editor.apply();
        AnalysisAdapter.maxAnalysisValue=maxAnalysisValue;

        changeView();
        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        changeView();
    }

    public void changeView()
    {

        if(grid_view_status==1)
        {
            apkList1=(GridView)view.findViewById(R.id.gridView_tab1);

            apkList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    PackageInfo packageInfo=(PackageInfo)parent.getItemAtPosition(position);

                    Intent intent=new Intent(getActivity(),AppActivity.class);
                    intent.putExtra("PackageInfo",packageInfo);
                    startActivity(intent);
                    onPause();

                }
            });

            apkList1.setAdapter(new GridAdpater(getActivity(), packageList1, packageManager));
        }
        else
        {
            apkList = (ListView)view.findViewById(R.id.applist);

            apkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    PackageInfo packageInfo=(PackageInfo)parent.getItemAtPosition(position);

                    Intent intent=new Intent(getActivity(),AppActivity.class);
                    intent.putExtra("PackageInfo",packageInfo);
                    startActivity(intent);
                    onPause();

                }
            });
            apkList.setAdapter(new ApkAdapter(getActivity(), packageList1, packageManager));
        }
    }
}