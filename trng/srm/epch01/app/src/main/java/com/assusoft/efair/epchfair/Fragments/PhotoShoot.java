package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import com.epch.efair.delhifair.R;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
  
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;

public class PhotoShoot extends Fragment{
	ListView listview;
	Button creatAlbum;
	ArrayList<com.assusoft.eFairEmall.entities.PhotoShoot> data;
	ArrayList<com.assusoft.eFairEmall.entities.PhotoShoot> dataArraylist;
	ArrayList<String> folder;
	ArrayList<String> folderArraylist;
	EditText filter;
	ArrayAdapter<String> adapter;
	int flag=0;
	
	private DatabaseHelper helper;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.album,container,false);
		listview=(ListView) view.findViewById(R.id.albumlistView);
		creatAlbum=(Button) view.findViewById(R.id.albumBtn);
		filter=(EditText) view.findViewById(R.id.productSearchBoxSearch);
		
		helper = EFairEmallApplicationContext.getDatabaseHelper();
		helper.openDatabase(DatabaseHelper.READMODE);
		
		FrameLayout banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
	 	   /*AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	   amin.start();*/
		folderArraylist=new ArrayList<String>();
		dataArraylist=new ArrayList<com.assusoft.eFairEmall.entities.PhotoShoot>();
		creatAlbum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(helper.getAllExhibNameFromExhibTable().isEmpty()){
					Toast.makeText(getActivity(),"This feature will available soon",Toast.LENGTH_LONG).show();
					return;
				}else{
					FragmentManager fm=getActivity().getSupportFragmentManager();
					FragmentTransaction ft=fm.beginTransaction();
					Album album=new Album();
					Bundle bundle=new Bundle();
					bundle.putInt("FLAG",0);
					album.setArguments(bundle);
					ft.replace(R.id.content_frame,album);
					ft.addToBackStack(null);
					ft.commit();
				}
				
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				if(flag==1){
					
					return;
				}
				Bundle bundle=new Bundle();
				bundle.putInt("EXHIBITORID",data.get(pos).getExhibitorId());
				bundle.putString("FOLDERNAME",folder.get(pos));
				FragmentManager fm=getActivity().getSupportFragmentManager();
				FragmentTransaction ft=fm.beginTransaction();				
				PhotoshootDetail album=new PhotoshootDetail();
				ft.replace(R.id.content_frame,album);
				ft.addToBackStack(null);
				album.setArguments(bundle);
				ft.commit();
			}
		});
		
		filter.addTextChangedListener(new TextWatcher() {
			 
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = filter.getText().toString().toLowerCase(Locale.getDefault());
                filter(text);
            }
 
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                    int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
 
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
            }

			
        });
		
		
		return view;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		HomeActivity.hideSoftKeyboard(getActivity());
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Product Selection");
		DatabaseHelper db=EFairEmallApplicationContext.getDatabaseHelper();
		db.openDatabase(DatabaseHelper.WRITEMODE);
	     data=db.getAllAlbumList();
	     
		if(data.size()<=0){
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.single_list_item,new String[]{"NO Album available, Please create."});
			listview.setAdapter(adapter);
			flag=1;
		}else{
			flag=0;
			Collections.reverse(data);
			dataArraylist.addAll(data);
			folder=new ArrayList<String>();
			for(int i=0;i<dataArraylist.size();i++){
				String[] sp=dataArraylist.get(i).getPhotopath().split("/");
				folder.add(sp[0]);
			}
			folderArraylist.addAll(folder);
		    adapter=new ArrayAdapter<String>(getActivity(), R.layout.single_list_item,folder);
			listview.setAdapter(adapter);
		}
		
		
		
	}
	
	 // Filter Class
    public void filter(String charText) {
        try{
	    	charText = charText.toLowerCase(Locale.getDefault());
	        folder.clear();
	        data.clear();
	        if (charText.length() == 0) {
	            folder.addAll(folderArraylist);
	            data.addAll(dataArraylist);
	        }
	        else
	        {
	            for (com.assusoft.eFairEmall.entities.PhotoShoot product : dataArraylist)
	            {
	            	String[] sp=product.getPhotopath().split("/");
					
	                if (sp[0].toLowerCase(Locale.getDefault()).contains(charText))
	                {
	                    folder.add(sp[0]);
	                    data.add(product);
	                }
	            }
	        }
	        adapter.notifyDataSetChanged();
        }catch(Exception e){
        	e.printStackTrace();
        }
    }

}
