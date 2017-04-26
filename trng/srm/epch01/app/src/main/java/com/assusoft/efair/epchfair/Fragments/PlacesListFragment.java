package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.epch.efair.delhifair.R;

public class PlacesListFragment extends Fragment{
	Context context;
	ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.places_list_fragment, container, false);
		// Getting reference to the TextView of the Fragment
		listView = (ListView) v.findViewById(R.id.placesList);
		
		ArrayList<String> yourlist = new ArrayList<String>();
		for(int i=0;i<10;i++)
		{
			yourlist.add("ATM"+i);
		}

		ArrayAdapter adapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_list_item_1, yourlist);
		listView.setAdapter(adapter);
		// Setting currently selected river name in the TextView
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
		       
			}
		});
		return v;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "PlacesListFragment");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

}
