package com.mohitsingh9512gmail.applist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohit Singh on 8/4/2016.
 */
//Our class extending fragment
public class Tab1 extends Fragment {

    PackageManager packageManager;
    ListView apkList;
    View view;

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

        //Returning the layout file after inflating
        //Change R.layout.main in you classes
        view=inflater.inflate(R.layout.main, container, false);
        packageManager = getActivity().getPackageManager();
        List<PackageInfo> packageList = packageManager
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);

        List<PackageInfo> packageList1 = new ArrayList<PackageInfo>();
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

        apkList = (ListView)view.findViewById(R.id.applist);

        apkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PackageInfo packageInfo=(PackageInfo)parent.getItemAtPosition(position);

                Intent intent=new Intent(getActivity(),Details.class);
                intent.putExtra("PackageInfo",packageInfo);
                startActivity(intent);

            }
        });
        apkList.setAdapter(new ApkAdapter(getActivity(), packageList1, packageManager));

        return view;
    }
}