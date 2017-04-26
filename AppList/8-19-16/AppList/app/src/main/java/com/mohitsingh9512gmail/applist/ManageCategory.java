package com.mohitsingh9512gmail.applist;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.mohitsingh9512gmail.applist.adapters.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ManageCategory extends AppCompatActivity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	SharedPreferences sharedPreferences2;
	SharedPreferences sharedPreferences3;
	public static final String MyPREFERENCES2 = "AllCategory" ;
	public static final String MyPREFERENCES3 = "AppCategory" ;
	int no_of_category=0;

	PackageManager packageManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_category);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar=getSupportActionBar();
		actionBar.setTitle("Manage Categories");


		packageManager=getPackageManager();
		sharedPreferences2=getSharedPreferences(MyPREFERENCES2, Context.MODE_APPEND);
		sharedPreferences3=getSharedPreferences(MyPREFERENCES3, Context.MODE_APPEND);


		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub

				final String s=listDataChild.get(
						listDataHeader.get(groupPosition)).get(
						childPosition);
				Toast.makeText(
						getApplicationContext(),
						listDataHeader.get(groupPosition)
								+ " : "
								+ s , Toast.LENGTH_SHORT)
						.show();
				PopupMenu popup=new PopupMenu(ManageCategory.this,v);
				popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());

				//registering popup with OnMenuItemClickListener
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Toast.makeText(getApplicationContext(), s + "Deleted", Toast.LENGTH_SHORT).show();
						return true;
					}
				});
				popup.show(); ///Showing popup menu
				return false;
			}
		});
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		Map<String, ?> allEntries;
		allEntries = sharedPreferences2.getAll();
		for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
			Log.v("tag", entry.getKey() + ": " + entry.getValue().toString());
		}

		// Adding child data

		if(!allEntries.isEmpty()) {
			Iterator myVeryOwnIterator = allEntries.keySet().iterator();
			while (myVeryOwnIterator.hasNext()) {
				no_of_category++;
				String key = (String) myVeryOwnIterator.next();
				String value = (String) allEntries.get(key);
				listDataHeader.add(value);
			}
		}

		// Adding child data

		Map<String, ?> allEntries1;
		allEntries1 = sharedPreferences3.getAll();
		for (Map.Entry<String, ?> entry : allEntries1.entrySet()) {
			Log.v("tag", entry.getKey() + ": " + entry.getValue().toString());
		}

		if(!allEntries1.isEmpty()) {
			Iterator myVeryOwnIterator ;

			List<String> child;
			String cat_Name;
			for(int i=0;i<no_of_category;i++)
			{
				cat_Name=listDataHeader.get(i);
				child = new ArrayList<String>();
				myVeryOwnIterator = allEntries1.keySet().iterator();
				while (myVeryOwnIterator.hasNext()) {
					String key1 = (String) myVeryOwnIterator.next();
					String value1 = (String) allEntries1.get(key1);

					if(cat_Name.equals(value1))
					{
						try {
							ApplicationInfo app=packageManager.getApplicationInfo(key1,0);
							String appName=(String)packageManager.getApplicationLabel(app);
							child.add(appName);
						} catch (PackageManager.NameNotFoundException e) {
							e.printStackTrace();
						}
					}
				}

				listDataChild.put(listDataHeader.get(i),child);
			}
		}

	}
}
