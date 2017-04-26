package com.mohitsingh9512gmail.applist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mohit Singh on 8/5/2016.
 */
public class Details extends Activity {

    PackageInfo packageInfo;
    TextView appLabel, packageName, version, features;
    TextView permissions, andVersion, installed, lastModify, path;
    Button btnLaunch,btnMarkFav;
    PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        packageManager=getPackageManager();
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
             packageInfo=extras.getParcelable("PackageInfo");

        }
        else
        {
            Toast.makeText(getApplicationContext(),"This is null ",Toast.LENGTH_LONG).show();
        }

        findViewsById();
        setValues();

        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = packageManager.getLaunchIntentForPackage(packageInfo.packageName);

                    if (intent != null) {
                        startActivity(intent);
                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        btnMarkFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b=(Button)v;
                if(b.)
            }
        });
    }
    private void findViewsById() {
        appLabel = (TextView) findViewById(R.id.applabel);
        packageName = (TextView) findViewById(R.id.package_name);
        version = (TextView) findViewById(R.id.version_name);
        features = (TextView) findViewById(R.id.req_feature);
        permissions = (TextView) findViewById(R.id.req_permission);
        andVersion = (TextView) findViewById(R.id.andversion);
        path = (TextView) findViewById(R.id.path);
        installed = (TextView) findViewById(R.id.insdate);
        lastModify = (TextView) findViewById(R.id.last_modify);
        btnLaunch=(Button)findViewById(R.id.btnLaunch);
        btnMarkFav=(Button)findViewById(R.id.btnMarkFav);
    }


    private void setValues() {
        // APP name
        appLabel.setText(getPackageManager().getApplicationLabel(
                packageInfo.applicationInfo));

        // package name
        packageName.setText(packageInfo.packageName);

        // version name
        version.setText(packageInfo.versionName);

        // target version
        andVersion.setText(Integer
                .toString(packageInfo.applicationInfo.targetSdkVersion));

        // path
        path.setText(packageInfo.applicationInfo.sourceDir);

        // first installation
        installed.setText(setDateFormat(packageInfo.firstInstallTime));

        // last modified
        lastModify.setText(setDateFormat(packageInfo.lastUpdateTime));

        // features
        if (packageInfo.reqFeatures != null)
            features.setText(getFeatures(packageInfo.reqFeatures));
        else
            features.setText("-");

        // uses-permission
        if (packageInfo.requestedPermissions != null)
            permissions
                    .setText(getPermissions(packageInfo.requestedPermissions));
        else
            permissions.setText("-");
    }

    @SuppressLint("SimpleDateFormat")
    private String setDateFormat(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = formatter.format(date);
        return strDate;
    }

    // Convert string array to comma separated string
    private String getPermissions(String[] requestedPermissions) {
        String permission = "";
        for (int i = 0; i < requestedPermissions.length; i++) {
            permission = permission + requestedPermissions[i] + ",\n";
        }
        return permission;
    }

    // Convert string array to comma separated string
    private String getFeatures(FeatureInfo[] reqFeatures) {
        String features = "";
        for (int i = 0; i < reqFeatures.length; i++) {
            features = features + reqFeatures[i] + ",\n";
        }
        return features;
    }

}
