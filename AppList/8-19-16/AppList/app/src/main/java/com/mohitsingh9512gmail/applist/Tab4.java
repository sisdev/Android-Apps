package com.mohitsingh9512gmail.applist;

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
import android.widget.ListView;

import com.mohitsingh9512gmail.applist.adapters.AnalysisAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohit Singh on 8/5/2016.
 */
public class Tab4 extends Fragment {

    PackageManager packageManager;
    ListView apkList3;
    View view;
    SharedPreferences sharedPreferences1;

    public static final String MyPREFERENCES1="Analysis";
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

        Log.v("tag","onCreateView of Tab4");


        view=inflater.inflate(R.layout.tab4, container, false);
        packageManager = getActivity().getPackageManager();
        List<PackageInfo> packageList = packageManager
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);

        List<PackageInfo> packageList3 = new ArrayList<PackageInfo>();
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
                packageList3.add(pi);
            }
        }



        apkList3 = (ListView)view.findViewById(R.id.applist3);

        apkList3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PackageInfo packageInfo=(PackageInfo)parent.getItemAtPosition(position);

                Intent intent=new Intent(getActivity(),Details.class);
                intent.putExtra("PackageInfo",packageInfo);
                startActivity(intent);
                onPause();

            }
        });
        apkList3.setAdapter(new AnalysisAdapter(getActivity(), packageList3, packageManager));

        return view;
    }
}