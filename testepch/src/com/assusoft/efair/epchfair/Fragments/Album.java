package com.assusoft.efair.epchfair.Fragments;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.epch.efair.delhifair.R;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Appointment;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.eFairEmall.entities.Favourite;
import com.assusoft.eFairEmall.entities.PhotoShoot;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.StorageHelper;

public class Album extends Fragment{
	
	EditText filter;
	ListView listview;
	DatabaseHelper dbH;
	ArrayList<Exhibitors> exhibitors;
	ArrayList<String> exhibitorName;
    private List<Exhibitors> userlist = null;
    ArrayAdapter<String> adapter;
    DatabaseHelper dh;
    int flag;
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.select_album_name,container,false);
		filter=(EditText) v.findViewById(R.id.albumeditText);
		listview=(ListView) v.findViewById(R.id.albumnamelistView);
		
		FrameLayout banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
//	 	   AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//	 	   amin.start();
		dh=EFairEmallApplicationContext.getDatabaseHelper();
		dh.openDatabase(DatabaseHelper.READMODE);
		userlist=dh.getAllExhibNameFromExhibAlbum();
		//Toast.makeText(getActivity(), "size-"+exhibitors.size(),Toast.LENGTH_LONG).show();
		exhibitors=new ArrayList<Exhibitors>();
		exhibitors.addAll(userlist);
		exhibitorName=new ArrayList<String>();
		for(int i=0;i<userlist.size();i++){
			exhibitorName.add(userlist.get(i).getExhibitorName());
			
		}
		
		adapter=new ArrayAdapter<String>(getActivity(),R.layout.single_list_item,exhibitorName);
		listview.setAdapter(adapter);
		
		filter.addTextChangedListener(new TextWatcher() {
			 
            @Override
            public void afterTextChanged(Editable arg0) {
               
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
                
            }

			
        });
		listview.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				HomeActivity.hideSoftKeyboard(getActivity());
				return false;
				
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				
				Bundle bundle1=getArguments();
				int flag=bundle1.getInt("FLAG");
				switch (flag) {
				case 0:
					
					if(StorageHelper.isFolderExist(ImageAsyncTask.FOLDER_NAME+File.separator+userlist.get(pos).getExhibitorName())){
						
						
						if(dh.isAlbumExist(""+userlist.get(pos).getExhibitorId())){
							Toast.makeText(getActivity(), "Album already exist",Toast.LENGTH_SHORT).show();
							return ;
						}
						
						
					}else{
						// create directory 
						StorageHelper.createFolder(ImageAsyncTask.FOLDER_NAME+File.separator+userlist.get(pos).getExhibitorName());
						
					}
					//insert data to photoshoot table
					PhotoShoot photo=new PhotoShoot(0,userlist.get(pos).getExhibitorId(),0,0,ImageAsyncTask.FOLDER_NAME+File.separator+userlist.get(pos).getExhibitorName(),null,null,null);
					
					dh.insertPhotoShoot(photo,true);
					
					
					//getActivity().getSupportFragmentManager().popBackStack();
					
					
					Bundle bundle=new Bundle();
					bundle.putInt("EXHIBITORID",userlist.get(pos).getExhibitorId());
					bundle.putString("FOLDERNAME",userlist.get(pos).getExhibitorName());
					FragmentManager fm=getActivity().getSupportFragmentManager();
					FragmentTransaction ft=fm.beginTransaction();
					
					PhotoshootDetail album=new PhotoshootDetail();
					ft.replace(R.id.content_frame,album);
					
					album.setArguments(bundle);
					ft.commit();
					
					break;
                case 1:
					//planner
                	if(dh.isAllreadyPlanned(""+userlist.get(pos).getExhibitorId(), bundle1.getString("DATE"))){
                		Toast.makeText(getActivity(), "Already planned.",Toast.LENGTH_SHORT).show();
                		return;
                	}
                	Appointment appointment=new Appointment(1,userlist.get(pos).getExhibitorId(),bundle1.getString("DATE"),
							"","",
	    				    1,"2014-08-08 10:12:00",null,"");
				
				    appointment.setEventId(-1);
				    appointment.setType(0);//1 indicates that event is scheduled
				
				    dh.submitVisitorSchedule(appointment);
				    getActivity().getSupportFragmentManager().popBackStack();
					break;
                 case 2:
					//favorites
                	 if(dh.isBookMark(userlist.get(pos).getExhibitorId())){
                		 Toast.makeText(getActivity(), "Already added to your favourite list.",Toast.LENGTH_SHORT).show();
                 		return;
                	 }
                	 Favourite favorites=new Favourite();
                	 favorites.setExhibitorId(userlist.get(pos).getExhibitorId());
                	 dh.submitBookMark(favorites);
                	 getActivity().getSupportFragmentManager().popBackStack();
					break;


				default:
					break;
				}
				
				
				
			}
		});
		return v;
	}
	
	
	public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        userlist.clear();
        if (charText.length() == 0) {
            userlist.addAll(exhibitors);
            Log.i("FILTER"," if userlist "+userlist.size()+"\t"+"exb "+exhibitorName.size());
        }
        else
        {
            for (Exhibitors user : exhibitors)
            {
                if (user.getExhibitorName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    userlist.add(user);
                    Log.i("FILTER"," else userlist "+userlist.size()+"\t"+"exb "+exhibitorName.size());
                }
            }
            Log.i("FILTER"," else userlist "+userlist.size()+"\t"+"exb "+exhibitorName.size());
        }
        
        setFilterData();
        
    }
 
 public void setFilterData(){
	 //Toast.makeText(getActivity(), "setFilterData()",Toast.LENGTH_SHORT).show();
	 exhibitorName.clear();
	 for(int i=0;i<userlist.size();i++)
		{
			exhibitorName.add(userlist.get(i).getExhibitorName());
			
		}
	 
	 Log.i("FILTER","userlist "+userlist.size()+"\t"+"exb "+exhibitorName.size()+"  exhibitors "+exhibitors.size());
	 adapter.notifyDataSetChanged();
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
	HomeActivity.setTitle("Exhibitors List");
}
 

 
 

}
