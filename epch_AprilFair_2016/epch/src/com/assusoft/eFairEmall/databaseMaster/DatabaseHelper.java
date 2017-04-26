package com.assusoft.eFairEmall.databaseMaster;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.assusoft.eFairEmall.entities.AdService;
import com.assusoft.eFairEmall.entities.Admin;
import com.assusoft.eFairEmall.entities.Appointment;
import com.assusoft.eFairEmall.entities.Company;
import com.assusoft.eFairEmall.entities.Event;
import com.assusoft.eFairEmall.entities.EventInfo;
import com.assusoft.eFairEmall.entities.EventLocation;
import com.assusoft.eFairEmall.entities.ExhibitorCategory;
import com.assusoft.eFairEmall.entities.ExhibitorEntryExit;
import com.assusoft.eFairEmall.entities.ExhibitorLanguage;
import com.assusoft.eFairEmall.entities.ExhibitorLocation;
import com.assusoft.eFairEmall.entities.ExhibitorProducts;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.eFairEmall.entities.FacilityEntryExit;
import com.assusoft.eFairEmall.entities.FacilityInformation;
import com.assusoft.eFairEmall.entities.Favourite;
import com.assusoft.eFairEmall.entities.FileSettings;
import com.assusoft.eFairEmall.entities.Language;
import com.assusoft.eFairEmall.entities.Links;
import com.assusoft.eFairEmall.entities.MartFacilities;
import com.assusoft.eFairEmall.entities.PhotoShoot;
import com.assusoft.eFairEmall.entities.ProductPhoto;
import com.assusoft.eFairEmall.entities.Products;
import com.assusoft.eFairEmall.entities.Resource;
import com.assusoft.eFairEmall.entities.Roles;
import com.assusoft.eFairEmall.entities.Users;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.assusoft.eFairEmall.entities.VisitorQueOption;
import com.assusoft.eFairEmall.entities.VisitorQuestionnaire;
import com.assusoft.eFairEmall.entities.Visitors;
import com.assusoft.efair.epchfair.Fragments.ExbListViewAdapter;
import com.assusoft.efair.epchfair.Fragments.Map;
import com.epch.efair.delhifair.MainActivity;


public class DatabaseHelper {

	public static final String DBNAME = "EPCH_DB.db";
	private static final int DBVERSION = 1;
	public static final int READMODE = 1;
	public static final int WRITEMODE = 2;
	public static final int RECORD_DELETED=0;
	public static final int RECORD_EXIST=1;
	public static final int STATUS_ONE=1;
	public static final int STATUS_ZERO=0;
	private DBHelper helper;
	private SQLiteDatabase db;
	public int flag;
	private static final int EVENTSTARTHOUR=11;
	Context context;
	public DatabaseHelper(Context context) {
		
		try {
			helper = new DBHelper(context, DBNAME, null, DBVERSION);
			Log.i("EFAIR", "helper object created.");
		} catch (Exception e) {
			Log.i("EFAIR", "Error in creating helper object");
		}
	}

	public void openDatabase(int mode) {
		try { 
			Log.i("EFAIR","open DB");
			if (mode == READMODE) {
				Log.i("EFAIR", "Opening database in read mode...");
				db = helper.getReadableDatabase();
				Log.i("EFAIR", "database  opened in read mode...");
			} else {
				Log.i("EFAIR", "Opening database in write mode...");
				db = helper.getWritableDatabase();
				Log.i("EFAIR", "database  opened in write mode...");
			}
		} catch (Exception e) {
			Log.i("EFAIR", "Error in opening database.");
		}
	}
	public void close()
	{
		//sss
		Log.i("EFAIR", "database  is closed...");
		db.close();
	}
	
	
	
	public List<Object> getEventDetailOfSelectedEvent(String eventId){
		List<Object> list = new ArrayList<Object>();
		//important note ! when access object from list then must cast it to Users and ExhibitoMap
		Cursor cursor=db.rawQuery(Query.SELECT_ALL_EVENT_INFO_BY_EVENTID, new String[]{eventId});
		Log.i("EFAIRDB",eventId+"  in getdetails size"+Integer.toString(cursor.getCount())+"columns "+Integer.toString(cursor.getColumnCount()));
		cursor.moveToFirst();
		 try{
			int eventsId=cursor.getInt(0);
			String eventName=cursor.getString(1);
			String eventDate=cursor.getString(2);
			String startTime=cursor.getString(3);
			String endTime=cursor.getString(4);
			String eventDescription=cursor.getString(5);
			int eventLocationId=cursor.getInt(6);
			String locationName=cursor.getString(7);
			String locationDecription=cursor.getString(8);
			String xLocation=cursor.getString(9);
			String yLocation=cursor.getString(10);
			int  locationId=cursor.getInt(11);
			String hallNo=cursor.getString(12);
			String filePath=cursor.getString(13);
			
			//create ExhibitorMap object
			EventLocation location=new EventLocation();
			//set the ExhibitorMap object data 
			location.setLocationId(eventLocationId);
			location.setLocationName(locationName);
			location.setDescription(locationDecription);
			location.setxLocation(xLocation);
			location.setyLocation(yLocation);
			//add exhibitormap object to list
			list.add(location);
			//create an object of Users class
			VenueMap map=new VenueMap();
			
			map.setLocationId(locationId);
			map.setFilePath(filePath);
			map.setHallNo(hallNo);
			//map.setHallNo(hallNo);
			list.add(map);
			EventInfo exhibitor=new EventInfo();
			exhibitor.setEventName(eventName);
			exhibitor.setEventId(eventsId);
			
			
			exhibitor.setEventDate(eventDate);
			exhibitor.setStartTime(startTime);
			exhibitor.setEndTime(endTime);
			exhibitor.setDescription(eventDescription);
			
			//add user object to list
			list.add(exhibitor);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		cursor.close();
		
		return list;
	}
	
	//get detail of selected event
		public ArrayList<Object> getEventDetail(String eventLocationId){
			ArrayList<Object> eventInfo=new ArrayList<Object>();
			try{
				Cursor cursor=db.rawQuery(Query.SELECT_EVENTDETAILS_OF_SELECTED_EVENT,new String[]{eventLocationId});
				while(cursor.moveToNext()){
					
					int locationId=cursor.getInt(0);
					
					String mapName=cursor.getString(1);
					String type=cursor.getString(2);
					String floorName=cursor.getString(3);
					String hallNo=cursor.getString(4);
					VenueMap venueMap=new VenueMap();
					venueMap.setMapName(mapName);
					venueMap.setType(type);
					venueMap.setFloorName(floorName);
					venueMap.setHallNo(hallNo);
					
					int eventlocationId=cursor.getInt(5);
					String locationName=cursor.getString(6);
					String description=cursor.getString(7);
					EventLocation eventLocation=new EventLocation();
					eventLocation.setEventLocationId(eventlocationId);
					eventLocation.setLocationName(locationName);
					eventLocation.setDescription(description);
					
					
					Log.i("NBTI"," "+eventLocationId+"    "+locationName);
					
					eventInfo.add(venueMap);
					eventInfo.add(eventLocation);
					
				}		
				cursor.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			return eventInfo;
		}
		//get all the event of particular date
		public ArrayList<EventInfo> getAllEventInfo(String date){
			ArrayList<EventInfo> eventInfo=new ArrayList<EventInfo>();
			try{
				Cursor cursor=db.rawQuery(Query.SELECT_ALL_EVENTINFO,new String[]{date});
				while(cursor.moveToNext()){
					
					int eventId=cursor.getInt(0);
					int eventLocationId=cursor.getInt(1);
					String eventName=cursor.getString(2);
					String eventDate=cursor.getString(3);
					String startTime=cursor.getString(4);
					String endTime=cursor.getString(5);
					String description=cursor.getString(6);
					String creationDate=cursor.getString(7);
					String updatedBy=cursor.getString(8);
					String moderater=cursor.getString(9);
					String panelLists=cursor.getString(10);
					EventInfo info=new EventInfo(eventId,eventLocationId, eventName, eventDate, startTime, endTime,
							       description, creationDate, updatedBy,moderater,panelLists);
					info.setEventId(eventId);
					eventInfo.add(info);
					Log.i("NBTI"," "+eventLocationId+"    "+eventName);
				}		
				cursor.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			return eventInfo;
		}
		//get all the event of particular date
				public ArrayList<EventInfo> getAllEventInfoById(String id){
					ArrayList<EventInfo> eventInfo=new ArrayList<EventInfo>();
					try{
						Cursor cursor=db.rawQuery(Query.SELECT_ALL_EVENTINFO_BY_ID,new String[]{id});
						while(cursor.moveToNext()){
							
							int eventId=cursor.getInt(0);
							int eventLocationId=cursor.getInt(1);
							String eventName=cursor.getString(2);
							String eventDate=cursor.getString(3);
							String startTime=cursor.getString(4);
							String endTime=cursor.getString(5);
							String description=cursor.getString(6);
							String creationDate=cursor.getString(7);
							String updatedBy=cursor.getString(8);
							String moderater=cursor.getString(9);
							String panelLists=cursor.getString(10);
							EventInfo info=new EventInfo(eventId,eventLocationId, eventName, eventDate, startTime, endTime,
									       description, creationDate, updatedBy,moderater,panelLists);
							info.setEventId(eventId);
							eventInfo.add(info);
							Log.i("NBTI"," "+eventLocationId+"    "+eventName);
						}		
						cursor.close();
					}catch(Exception e){
						e.printStackTrace();
					}
					
					
					return eventInfo;
				}
			
	
	//get all the event of particular date
	/*public ArrayList<EventInfo> getAllEventInfoOfDate(String date){
		ArrayList<EventInfo> eventInfo=new ArrayList<EventInfo>();
		try{
			Cursor cursor=db.rawQuery(Query.SELECT_ALL_EVENTINFO_FOR_DATE,new String[]{date});
			while(cursor.moveToNext()){
				
				int eventId=cursor.getInt(0);
				int eventLocationId=cursor.getInt(1);
				String eventName=cursor.getString(2);
				String eventDate=cursor.getString(3);
				String startTime=cursor.getString(4);
				String endTime=cursor.getString(5);
				String description=cursor.getString(6);
				String creationDate=cursor.getString(7);
				String updatedBy=cursor.getString(8);
				EventInfo info=new EventInfo(eventId,eventLocationId, eventName, eventDate, startTime, endTime,
						       description, creationDate, updatedBy);
				info.setEventId(eventId);
				eventInfo.add(info);
				Log.i("NBTI"," "+eventLocationId+"    "+eventName);
			}		
			cursor.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return eventInfo;
	}
	*/
	//this method is used to access the lattest timestamp of table 
	public String getLattestTimeStam(){
		String timeStamp=null;
		try{
			Cursor cursor=db.rawQuery(Query.SELECT_GREATEST_TIMESTAMP,null);
			cursor.moveToFirst();
			if(cursor.getString(0)!=null){
				timeStamp=cursor.getString(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		Log.i("WEB_DATA","Greatest TimeStamp "+timeStamp);
		//db.close();
		return timeStamp;
	}
	
	public int selectAppointmentByEventIdAndType(String eventId,String type){
		int row=0;
		//Log.i("NBTI",date+"  "+startTime+"  "+endTime);
		String query="select * from appointment  where eventId='"+eventId+"' and type='"+type+"'";
		try{
			Cursor cursor=db.rawQuery(query,null);
			//if(cursor!=null){
				Log.i("NBTI","number of row getcount"+cursor.getCount());
				row= cursor.getCount();
			//}
			cursor.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Log.i("NBTI","number of row "+row);
		return row;
	}
	
	public int selectAppointmentByExhibitorIdAndType(String exhibitorId,String type){
		int row=0;
		//Log.i("NBTI",date+"  "+startTime+"  "+endTime);
		String query="select * from appointment  where exhibitorId='"+exhibitorId+"' and status='"+type+"'";
		try{
			Cursor cursor=db.rawQuery(query,null);
			//if(cursor!=null){
				Log.i("NBTI","number of row getcount"+cursor.getCount());
				row= cursor.getCount();
			//}
			cursor.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Log.i("NBTI","number of row "+row);
		return row;
	}
	//this method is used to validate the particular appointment
	public int validateAppointment(String date,String startTime,String endTime){
		int row=0;
		Log.i("NBTI",date+"  "+startTime+"  "+endTime);
		String query="select * from appointment  where date='"+date+"' and strftime('%H:%M',startTime)   between  strftime('%H:%M','"+startTime+"') And  strftime('%H:%M','"+endTime+"')";
		try{
			Cursor cursor=db.rawQuery(query,null);
			//if(cursor!=null){
				Log.i("NBTI","number of row getcount"+cursor.getCount());
				row= cursor.getCount();
			//}
			cursor.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Log.i("NBTI","number of row "+row);
		return row;
	}
	//submit the visitor schedule to database
	public void submitVisitorSchedule(Appointment apnmt){
		
		ContentValues row =new ContentValues();
		//row.put("appointmentId",apnmt.getEhxibitorId());
		row.put("visitorId",apnmt.getVisitorId());
		row.put("exhibitorId",apnmt.getEhxibitorId());
		//row.put("type",apnmt.getType());
		row.put("eventId",apnmt.getEventId());
		row.put("date",apnmt.getDate());
		row.put("startTime",apnmt.getStartTime());
		row.put("endTime",apnmt.getEndTime());
		row.put("status",apnmt.getStatus());
		row.put("creationDate",apnmt.getCreationDate());
		row.put("updatedBy",apnmt.getUpdatedBy());
		
		try{
		db.insert("Appointment",null,row);
		Log.i("NBTI", "One schedule record is submitted successfully...");
		}catch(Exception e){
		    e.printStackTrace();
		    Log.i("EFAIR", "Appointment:"+e);
		
		}
	}
	
	
	//fetch all visitor schedule list from database
		public ArrayList<Appointment> getAllSchedule(){
			ArrayList<Appointment> appointments=new ArrayList<Appointment>();
			Log.i("EFAIR","Fetch all schedule");
			try{
				/*Cursor cursor=db.rawQuery(Query.SELECT_ALL_APPOINTMENT_SCHEDULE_EVENT, null);
				while(cursor.moveToNext()){
					Appointment apnmt=new Appointment();
					apnmt.setEventId(cursor.getInt(0));
					apnmt.setAppointmentId(cursor.getInt(1));
					apnmt.setEventName(cursor.getString(2));
					apnmt.setType(cursor.getInt(3));
					apnmt.setDate(cursor.getString(4));
					apnmt.setStartTime(cursor.getString(5));
					apnmt.setEndTime(cursor.getString(6));
					apnmt.setStatus(cursor.getString(7));			
					
					Log.i("EFAIR","event data  "+cursor.getInt(0)+cursor.getInt(1)+cursor.getString(2));
					appointments.add(apnmt);
				}
				cursor.close();*/
				Cursor cursor1=db.rawQuery(Query.SELECT_ALL_APPOINTMENT_SCHEDULE_EXHIBITOR, null);
				Log.i("EFAIR","exhibitor schedule..."+cursor1.getCount());
				
				while(cursor1.moveToNext()){
					Appointment apnmt=new Appointment();
					Log.i("EFAIR","exhibitor schedule..."+cursor1.getInt(0));
					apnmt.setExhibitorId(cursor1.getInt(0));
					
					apnmt.setAppointmentId(cursor1.getInt(1));
					apnmt.setExhibitorName(cursor1.getString(2)+"~~"+"Hall No. "+cursor1.getString(8)+", Stand No. "+cursor1.getString(9));
					apnmt.setType(cursor1.getInt(3));
					apnmt.setDate(cursor1.getString(4));
					apnmt.setStartTime(cursor1.getString(5));
					apnmt.setEndTime(cursor1.getString(6));
					apnmt.setStatus(Integer.parseInt(cursor1.getString(7)));					
					
					
					Log.i("EFAIR","Exhibitor Data type  "+cursor1.getInt(3)+"data  "+ cursor1.getInt(0)+cursor1.getInt(1)+cursor1.getString(2));
					appointments.add(apnmt);
				}
				cursor1.close();
		   }catch(Exception e){
				Log.i("NBTI","[EXCEPTION WHEN getting shedule] "+e.getLocalizedMessage());
			}
			
			return appointments;
		}
	
	//fetch all visitor schedule list from database
	public ArrayList<Appointment> fechAllSchedule(){
		ArrayList<Appointment> appointments=new ArrayList<Appointment>();
		Log.i("EFAIR","Fetch all schedule");
		try{
			Cursor cursor=db.rawQuery(Query.SELECT_ALL_APPOINTMENT_SCHEDULE1, null);
			while(cursor.moveToNext()){
				Appointment apnmt=new Appointment();
				apnmt.setExhibitorId(cursor.getInt(0));
				apnmt.setExhibitorName(cursor.getString(1));
				apnmt.setDate(cursor.getString(2));
				apnmt.setStartTime(cursor.getString(3));
				apnmt.setEndTime(cursor.getString(4));
				apnmt.setStatus(Integer.parseInt(cursor.getString(5)));
				apnmt.setType((byte)cursor.getInt(6));
				apnmt.setEventId(cursor.getInt(7));
				apnmt.setEventName(cursor.getString(8));
				apnmt.setAppointmentId(cursor.getInt(9));
				Log.i("EFAIR",cursor.getInt(0)+cursor.getString(1)+cursor.getString(2));
				appointments.add(apnmt);
			}
			cursor.close();
	   }catch(Exception e){
			e.printStackTrace();
		}
		
		return appointments;
	}
	
	//this method is used to delete particular schedule
		public int deleteVisitorScheduleBasedOnId(String appointmentId){
			try{
				//db.rawQuery(Query.DELETE_APPOINTMENT,new String[]{startTime,endTime,day});
			int row=db.delete("Appointment",Query.DELETE_APPOINTMENT_BY_ID,new String[]{appointmentId});
			return row;
			}catch(Exception e){
				e.printStackTrace();
			}
			return 0;
		}
	
	//this method is used to delete particular schedule
	public int deleteVisitorSchedule(String startTime,String endTime,String day){
		try{
			//db.rawQuery(Query.DELETE_APPOINTMENT,new String[]{startTime,endTime,day});
		int row=	db.delete("Appointment",Query.DELETE_APPOINTMENT,new String[]{day,startTime,endTime});
		return row;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	//this method is used to submit the single user details to database
	public void submitUser(ArrayList<Users> users){
		
		try{
			int count=0;
			int length =users.size();
			long startTransTime=System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_USERS);
			
			for(int i=0;i<length;i++)
			{
				 Users user=users.get(i);
				 stmt.bindString(1, ""+user.getUserId());
				 stmt.bindString(2, ""+user.getRoleId());
				 stmt.bindString(3, user.getLoginEmail());
				 stmt.bindString(4, user.getPassword());
				 stmt.bindString(5, user.getSecretQuestion());
				 stmt.bindString(6, user.getSecretAnswer());
				 stmt.bindString(7, user.getLastLoggedIn());
				 stmt.bindString(8, user.getCreationDate());
				 stmt.bindString(9, user.getUpdatedBy());
				 stmt.bindString(10,""+user.getStatus());

					stmt.execute();
					stmt.clearBindings();
					 count++;				
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR","total records:"+length+" total recoded added:"+count);
			Log.i("EFAIR","Time"+(System.currentTimeMillis()-startTransTime)+"ms");
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
		
		/*try{
			   int length=users.size();
			   int count=0;
			   for (int i=0;i<length;i++){
					 Users user=users.get(i);  
				   
					ContentValues row =new ContentValues();
					row.put("userId",user.getUserId());
					row.put("roleId",user.getRoleId());
					row.put("loginEmail",user.getLoginEmail());
					row.put("password",user.getPassword());
					row.put("secretQuestion",user.getSecretQuestion());
					row.put("secretAnswer",user.getSecretAnswer());
					row.put("status",user.getStatus());
					row.put("lastLoggedIn",user.getLastLoggedIn());
					row.put("creationDate",user.getCreationDate());
					row.put("updatedBy",user.getUpdatedBy());		
					
					//insert single row to Users table
					db.insert("Users",null,row);
					count++;
			   }
			   Log.i("EFAIR", ""+ count+" user record is submitted successfully...");
		}catch(Exception e)	   
		{
			e.printStackTrace();
		}*/
		
		
	}
	//this method is used to submit the detail of role table
	public void submitRoles(ArrayList<Roles> roles){
		try{
			 int length=roles.size();
			 int count=0;
			 long startTransTime=System.currentTimeMillis();
			 db.beginTransaction();
			 SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_ROLES);
				 
			 for(int i=0;i<length;i++)
			 {
				 Roles role=roles.get(i);
				 stmt.bindString(1, ""+role.getRoleId());
				 stmt.bindString(2, role.getRoleName());
				 stmt.bindString(3, role.getRoleDetails());
				 stmt.bindString(4, role.getCreationDate());
				 stmt.bindString(5, role.getUpdatedBy());
				 stmt.bindString(6, ""+role.getStatus());
				 stmt.execute();
				 stmt.clearBindings();
				 count++;
			 }
			 db.endTransaction();

				Log.i("EFAIR","total records:"+length+" total recoded added:"+count);
				Log.i("EFAIR","Time"+(System.currentTimeMillis()-startTransTime)+"ms");
			 /*for(int i=0;i<length;i++){
				Roles role=roles.get(i);
				ContentValues row=new ContentValues();
				row.put("roleId",role.getRoleId());
				row.put("roleName",role.getRoleName());
				row.put("roleDetails",role.getRoleDetails());
				row.put("creationDate",role.getCreationDate());
				row.put("updatedBy",role.getUpdatedBy());
				//insert single row to RoleArea table
				db.insert("Roles",null,row);
				count++;
			 }*/
			 Log.i("EFAIR", ""+count+" RoleArea record is submitted successfully...");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.setTransactionSuccessful();
		}
		
	}
	
	
	//this method is used to submit the single record to the product table
	public void submitProducts(ArrayList<Products> products){
		
		try{
			int length=products.size();
			int count=0;
			int del=0;
			long startTransTime=System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_PRODUCTS);
			for(int i=0;i<length;i++){
				Products product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("products", "productId=?", new String[]{""+product.getProductId()});
					del++;
					continue;
				}
				stmt.bindString(1, ""+product.getProductId());
				stmt.bindString(2, product.getProductName());
				stmt.bindString(3, product.getProductType());
				stmt.bindString(4, product.getProductBrand());
				stmt.bindString(5, product.getDescription());
				stmt.bindString(6, product.getCreationDate());
				stmt.bindString(7, product.getUpdatedBy());
				stmt.bindString(8, ""+product.getStatus());
				
				stmt.execute();
				stmt.clearBindings();
				count++;
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR", "Total records in proucts table is "+length+", and "+count+" record is submitted successfully..."+del+"records with status 0");
			Log.i("EFAIR", "Total execution time for proucts table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," product submit exception:"+e);
		} finally{
			db.endTransaction();
		}
		
	}
		
		
		/*try{  //db = helper.getWritableDatabase();
			int length=products.size();
			int count=0;
			for(int i=0;i<length;i++){
				//DownloadingData.initializeData.myProgress((float)i/length);
				Products product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("products", "productId=?", new String[]{""+product.getParentId()});
					continue;
				}
				
				ContentValues row =new ContentValues();
				row.put("productId",product.getProductId());
				row.put("productName",product.getProductName());
				row.put("productType",product.getProductType());
				row.put("productBrand",product.getProductBrand());
				row.put("description",product.getDescription());
				row.put("creationDate",product.getCreationDate());
				row.put("updatedBy",product.getUpdatedBy());
				row.put("status", product.getStatus());
				
				if(DownloadingData.isDbUpdate)//if updates are available
				{  
					if(isNewItem("products",""+product.getProductId(),""))
				    {
					  db.update("products", row, "productId=?", new String[] {""+product.getProductId()});
					  Log.i("UPDATES", "New product is updated");
				    }else{
				    	db.insert("products", null, row);
				    	 Log.i("UPDATES", "New product is added");
				    }	
				}else{
					db.insert("products", null, row);
				}
				
				count++;
			}
			Log.i("EFAIR", ""+count+" product record is submitted successfully...");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," product submit exception:"+e);
		}*/
		
	
	
	public void submitResource(ArrayList<Resource> products){
		
		try{  
			int length=products.size();
			int count=0;
			long startTransTime = System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_RESOURCE);
			for(int i=0;i<length;i++){
				Resource product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("resource", "locationId=? and resource=?", new String[]{""+product.getLocationId(), product.getResource()});
					continue;
				}
				stmt.bindString(1, ""+product.getLocationId());
				stmt.bindString(2, product.getResource());
				stmt.bindString(3, product.getType());				
				stmt.bindString(4, product.getCreationDate());
				stmt.bindString(5, product.getUpdatedBy());
				stmt.bindString(6, ""+product.getStatus());
				
				stmt.execute();
				stmt.clearBindings();
				count++;
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR", "Total records in resource table is "+length+", and "+count+" record is submitted successfully...");
			Log.i("EFAIR", "Total execution time for resource table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," resource submit exception:"+e);
		}finally{
			db.endTransaction();
		}
		
		/*try{  //db = helper.getWritableDatabase();
			int length=products.size();
			int count=0;
			for(int i=0;i<length;i++){
				//DownloadingData.initializeData.myProgress((float)i/length);
				Resource product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("resource", "locationId=? and resource=?", new String[]{""+product.getLocationId(), product.getResource()});
					continue;
				}
				
				ContentValues row =new ContentValues();
				row.put("locationId",product.getLocationId());
			//	row.put("productId",i);
			//	row.put("parentId",i);
				row.put("resource",product.getResource());
				row.put("type",product.getType());
				
				row.put("creationDate",product.getCreationDate());
				row.put("updatedBy",product.getUpdatedBy());
				row.put("status", product.getStatus());
				
				if(DownloadingData.isDbUpdate)//if updates are available
				{  
					if(isNewItem("resource",""+product.getLocationId(),""))
				    {
					  db.update("resource", row, "locationId=? and resource=?", new String[]{""+product.getLocationId(), product.getResource()});
					  Log.i("UPDATES", "New product is updated");
				    }else{
				    	db.insert("resource", null, row);
				    	 Log.i("UPDATES", "New product is added");
				    }	
				}else{
					db.insert("resource", null, row);
				}
				
				count++;
			}
			Log.i("EFAIR", ""+count+" product record is submitted successfully...");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," product submit exception:"+e);
		}*/
		
	}
	
	public void submitFilesetting(ArrayList<FileSettings> products){
		
		try{  //db = helper.getWritableDatabase();
			int length=products.size();
			int count=0;
			int del=0;
			long startTransTime = System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_FILE_SETTING);
			for(int i=0;i<length;i++){
				FileSettings product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("file_setting", "id=?", new String[]{""+product.getId()});
					del++;
					continue;
				}
				stmt.bindString(1, ""+product.getId());
				stmt.bindString(2, product.getFileName());
				stmt.bindString(3, product.getFilePath());
				stmt.bindString(4, product.getCreationDate());
				stmt.bindString(5, product.getUpdatedBy());
				stmt.bindString(6, ""+product.getStatus());
				
				stmt.execute();
				stmt.clearBindings();
				count++;
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR", "Total records in file_setting table is "+length+", and "+count+" record is submitted successfully..."+del+" recorde with status 0");
			Log.i("EFAIR", "Total execution time for file_setting table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," file_setting submit exception:"+e);
		}finally{
			db.endTransaction();
		}
		
		/*try{  //db = helper.getWritableDatabase();
			int length=products.size();
			int count=0;
			for(int i=0;i<length;i++){
				//DownloadingData.initializeData.myProgress((float)i/length);
				FileSettings product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("file_setting", "id=?", new String[]{""+product.getId()});
					continue;
				}
				
				ContentValues row =new ContentValues();
				row.put("id",product.getId());
				row.put("productId",i);
				row.put("parentId",i);
				row.put("fileName",product.getFileName());
				row.put("filePath",product.getFilePath());
				
				row.put("creationDate",product.getCreationDate());
				row.put("updatedBy",product.getUpdatedBy());
				row.put("status", product.getStatus());
				
				if(DownloadingData.isDbUpdate)//if updates are available
				{  
					if(isNewItem("file_setting",""+product.getId(),""))
				    {
					  db.update("file_setting", row, "id=?", new String[] {""+product.getId()});
					  Log.i("UPDATES", "New file_setting is updated");
				    }else{
				    	db.insert("file_setting", null, row);
				    	 Log.i("UPDATES", "New file_setting is added");
				    }	
				}else{
					db.insert("file_setting", null, row);
				}
				
				count++;
			}
			Log.i("EFAIR", ""+count+" file_setting record is submitted successfully...");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," product submit exception:"+e);
		}*/
		
	}
	
	//this method is used to submit the exhibitors record to database
	public void submitExhibitors(ArrayList<Exhibitors> exhibitors){
		try{
			int length=exhibitors.size();
			int count=0;
			int del=0;
			long startTransTime=System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_EXHIBITORS);
			for(int i=0;i<length;i++){
				Exhibitors exhibitor=exhibitors.get(i);
				if(exhibitor.getStatus()==RECORD_DELETED){
					try{
						db.delete("exhibitors","exhibitorId=?",new String[]{""+exhibitor.getExhibitorId()});
						del++;						
					}catch(Exception e){
						e.printStackTrace();
					}
					continue;
				}
				stmt.bindString(1, ""+exhibitor.getPkId());
				stmt.bindString(2, ""+exhibitor.getExhibitorId());
				stmt.bindString(3, exhibitor.getContactPerson());
				stmt.bindString(4, exhibitor.getEmail());
				stmt.bindString(5, exhibitor.getContactNo());
				stmt.bindString(6, exhibitor.getMobileNo());
				stmt.bindString(7, exhibitor.getFax());
				stmt.bindString(8, exhibitor.getCreationDate());
				stmt.bindString(9, exhibitor.getUpdatedBy());
				stmt.bindString(10, ""+exhibitor.getCategory());
				stmt.bindString(11, ""+exhibitor.getCompanyId());
				stmt.bindString(12, exhibitor.getCountry());
				stmt.bindString(13, exhibitor.getType());
				stmt.bindString(14, ""+exhibitor.getStatus());
				stmt.bindString(15, exhibitor.getRefNo());
				
				stmt.execute();
				stmt.clearBindings();
				count++;
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR", "Total records in exhibitors table is "+length+", and "+count+" record is submitted successfully..."+del+" records with status 0");
			Log.i("EFAIR", "Total execution time for exhibitors table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," records inserted excetion in Exhibitors table... "+e);
		}finally{
			db.endTransaction();
		}
	}
		/*try{
			int length=exhibitors.size();
			int count=0;
			for(int i=0;i<length;i++){
				//DownloadingData.initializeData.myProgress(1+(float)i/length);
				Exhibitors exhibitor=exhibitors.get(i);
				ContentValues row=new ContentValues();
				
				if(exhibitor.getStatus()==RECORD_DELETED){
					try{
						//delete the record from database
						if(isNewItem("exhibitors",""+exhibitor.getExhibitorId(),"")){
							db.delete("exhibitors","exhibitorId=?",new String[]{""+exhibitor.getExhibitorId()});
							continue;
						}
						
					}catch(Exception e){
						e.printStackTrace();
					}
					continue;
				}
				row.put("pkId",exhibitor.getPkId());
				row.put("exhibitorId",exhibitor.getExhibitorId());
				/*row.put("exhibitorName",exhibitor.getExhibitorName());
				row.put("exhibitorDescription",exhibitor.getDescription());
				row.put("address1",exhibitor.getAddress1());
				row.put("address2",exhibitor.getAddress2());
				row.put("city",exhibitor.getCity());
				row.put("state",exhibitor.getState());
				row.put("pinCode",exhibitor.getPinCode());
				row.put("country",exhibitor.getCountry());
				row.put("nationality",exhibitor.getNationality());
				row.put("website",exhibitor.getWebSite());
				row.put("logoPath",exhibitor.getLogoPath());
				
				row.put("contactPerson",exhibitor.getContactPerson());
				row.put("email",exhibitor.getEmail());
				row.put("contactNo",exhibitor.getContactNo());
				row.put("mobileNo",exhibitor.getMobileNo());
				row.put("fax",exhibitor.getFax());
				row.put("creationDate",exhibitor.getCreationDate());
				row.put("updatedBy",exhibitor.getUpdatedBy());
				row.put("category",exhibitor.getCategory());
				row.put("companyId",exhibitor.getCompanyId());
				row.put("country",exhibitor.getCountry());
				row.put("type",exhibitor.getType());
				row.put("status",exhibitor.getStatus());
				row.put("refNo",exhibitor.getRefNo());
				if(DownloadingData.isDbUpdate)//if updates are available
				{  
					if(isNewItem("exhibitors",""+exhibitor.getExhibitorId(),""))
				    {
					  db.update("exhibitors", row, "exhibitorId=?", new String[] {""+exhibitor.getExhibitorId()});
					  Log.i("UPDATES", "New exhibitors is updated");
				    }else{
				    	db.insert("exhibitors",null,row);
				    	 Log.i("UPDATES", "New exhibitors is added");
				    }	
				}else{
					db.insert("exhibitors",null,row);
				}
				count++;
			}
			Log.i("EFAIR",""+count+" records inserted successfully in Exhibitors table... ");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," records inserted excetion in Exhibitors table... "+e);
		}
	}*/
	
	public void submitVisitors(ArrayList<Visitors> visitors){
		try{
			int length=visitors.size();
			int count=0;
			int del=0;
			for(int i=0;i<length;i++){
			  	Visitors visitor=visitors.get(i);
			  	if(visitor.getStatus()==STATUS_ZERO){
			  		db.delete("visitors", "visitorId=?", new String[]{""+visitor.getVisitorId()});
			  		del++;
			  		continue;
			  	}
			  	
			  	ContentValues row=new ContentValues();
			  	row.put("visitorId",visitor.getVisitorId());
				row.put("visitorName",visitor.getVisitorName());
				row.put("visitorPurpose",visitor.getVisitorPurpose());
				row.put("visitorType",visitor.getVisitorType());
				row.put("visitorSex",visitor.getVisitorSex());
				row.put("companyName",visitor.getCompanyName());
				row.put("address1",visitor.getAddress1());
				row.put("address2",visitor.getAddress2());
				row.put("city",visitor.getCity());
				row.put("state",visitor.getState());
				row.put("pinCode",visitor.getPincode());
				row.put("country",visitor.getCountry());
				row.put("nationality",visitor.getNationality());
				row.put("website",visitor.getWebsite());
				row.put("contactNo",visitor.getContactNo());
				row.put("mobileNo",visitor.getMobileNo());
				row.put("creationDate",visitor.getCreationDate());
				row.put("updatedBy",visitor.getUpdatedBy());
				row.put("status", visitor.getStatus());
				db.insert("visitors",null,row);
				
				count++;
			}
			Log.i("EFAIR",""+count+" records inserted successfully in visitors table... ");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//this method is used to submit record of exhibitorproduct table 
	public void submitExhibitorProduct(ArrayList<ExhibitorProducts> exhibitorProducts){
		
		try{
			int length=exhibitorProducts.size();
			int count=0;
			long startTransTime = System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_EXHIBITOR_PRODUCTS);				
			for(int i=0;i<length;i++){
		       ExhibitorProducts exhibitorProduct=exhibitorProducts.get(i);
		       if(exhibitorProduct.getStatus()==STATUS_ZERO){
		    	   db.delete("ExhibitorProducts", "productId=? and exhibitorId=?", new String[] {""+exhibitorProduct.getProductId(),""+exhibitorProduct.getExibitorsId()});
		    	   continue;
		       }			       
		        stmt.bindString(1, ""+exhibitorProduct.getProductId());
	            stmt.bindString(2, ""+exhibitorProduct.getExibitorsId());
	            stmt.bindString(3, exhibitorProduct.getCreationDate());
	            stmt.bindString(4, exhibitorProduct.getUpdatedBy());
	            stmt.bindString(5, ""+exhibitorProduct.getStatus());
	             
	            stmt.execute();
	            stmt.clearBindings();			       
				count++;
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR", "Total records in ExhibitorProducts table is "+length+", and "+count+" record(s) is submitted successfully...");
			Log.i("EFAIR", "Total execution time for ExhibitorProducts table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
	        db.endTransaction();
		}
		
			/*try{
				int length=exhibitorProducts.size();
				int count=0;
				for(int i=0;i<length;i++){
			       ExhibitorProducts exhibitorProduct=exhibitorProducts.get(i);
			       if(exhibitorProduct.getStatus()==STATUS_ZERO){
			    	   db.delete("ExhibitorProducts", "productId=? and exhibitorId=?", new String[] {""+exhibitorProduct.getProductId(),""+exhibitorProduct.getExibitorsId()});
			    	   continue;
			       }
			       //DownloadingData.initializeData.myProgress(9+(float)i/length);
			       ContentValues row =new ContentValues();
					row.put("productId",exhibitorProduct.getProductId());
					row.put("exhibitorId",exhibitorProduct.getExibitorsId());
					row.put("creationDate",exhibitorProduct.getCreationDate());
					row.put("updatedBy",exhibitorProduct.getUpdatedBy());
					row.put("status", exhibitorProduct.getStatus());
					if(DownloadingData.isDbUpdate)//if updates are available
					{  
						if(isNewItem("exhibitorproducts",""+exhibitorProduct.getProductId(),""+exhibitorProduct.getExibitorsId()))
					    {
						  db.update("ExhibitorProducts", row, "productId=? and exhibitorId=?", new String[] {""+exhibitorProduct.getProductId(),""+exhibitorProduct.getExibitorsId()});
						  Log.i("UPDATES", "New ExhibitorProducts is updated");
					    }else{
					    	db.insert("ExhibitorProducts", null,row);
					    	 Log.i("UPDATES", "New ExhibitorProducts is added");
					    }	
					}else{
						db.insert("ExhibitorProducts", null,row);
					}
					count++;
				}
				Log.i("EFAIR", ""+count+" exbitorProduct record is submitted successfully...");
			}catch(Exception e ){
				e.printStackTrace();
			}*/
		}
	//this method is used to submit the single record of productphoto table
	public void submitProductPhoto(ArrayList<ProductPhoto> productPhotos){
		
		try{
			   int length=productPhotos.size();
			   int count=0;
			   long startTransTime = System.currentTimeMillis();
			   db.beginTransaction();
			   SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_PRODUCT_PHOTO);
			   for(int i=0;i<length;i++){
				    ProductPhoto productPhoto=productPhotos.get(i);
				    if(productPhoto.getStatus()==STATUS_ZERO){
				    	db.delete("ProductPhotos", "productPhotoId=?", new String[] {""+productPhoto.getProductPhotoId()});
				    	continue;
				    }
					stmt.bindString(1, ""+productPhoto.getProductPhotoId());
					stmt.bindString(2, ""+productPhoto.getProductId());
					stmt.bindString(3, ""+productPhoto.getExhibitorId());
					stmt.bindString(4, productPhoto.getFilePath());
					stmt.bindString(5, productPhoto.getFileName());
					stmt.bindString(6, productPhoto.getCreationDate());
					stmt.bindString(7, productPhoto.getUpdatedBy());
					stmt.bindString(8, ""+productPhoto.getStatus());
					
					stmt.execute();
					stmt.clearBindings();
					count++;
			   }
			   db.setTransactionSuccessful();
			   Log.i("EFAIR", "Total records in ExhibitorProducts table is "+length+", and "+count+" record(s) is submitted successfully...");
			   Log.i("EFAIR", "Total execution time for ExhibitorProducts table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
		
		
		/*try{
			   int length=productPhotos.size();
			   int count=0;
			   for(int i=0;i<length;i++){
				   //DownloadingData.initializeData.myProgress(10+(float)i/length);
				    ProductPhoto productPhoto=productPhotos.get(i);
				    if(productPhoto.getStatus()==STATUS_ZERO){
				    	db.delete("ProductPhotos", "productPhotoId=?", new String[] {""+productPhoto.getProductPhotoId()});
				    	continue;
				    }
					ContentValues row =new ContentValues();
					row.put("productPhotoId",productPhoto.getProductPhotoId());
					row.put("productId",productPhoto.getProductId());
					row.put("exhibitorId",productPhoto.getExhibitorId());
					row.put("filePath",productPhoto.getFilePath());
					row.put("fileName",productPhoto.getFileName());
					row.put("creationDate",productPhoto.getCreationDate());
					row.put("updatedBy",productPhoto.getUpdatedBy());
					row.put("status", productPhoto.getStatus());
					if(DownloadingData.isDbUpdate)//if updates are available
					{  
						if(isNewItem("productPhotos",""+productPhoto.getProductPhotoId(),""))
					    {
						  db.update("ProductPhotos", row, "productPhotoId=?", new String[] {""+productPhoto.getProductPhotoId()});
						  Log.i("UPDATES", "New ProductPhotos is updated");
					    }else{
					    	db.insert("ProductPhotos", null, row);
					    	 Log.i("UPDATES", "New ProductPhotos is added");
					    }	
					}else{
						db.insert("ProductPhotos", null, row);
					}
					
					count++;
			   }
				Log.i("EFAIR", ""+count+" productPhoto record is submitted successfully...");
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}
	
	//this method is used to submit the single record of map table
	public void submitVenueMap(ArrayList<VenueMap> maps){
		MainActivity.updatingVenueMapFile=maps;
		
		try{
			int length=maps.size();
			int count=0;
			int del=0;
			long startTransTime = System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_VENUE_MAP);
			for(int i=0;i<length;i++){
			   VenueMap map=maps.get(i);
			   if(map.getStatus()==STATUS_ZERO){
				   db.delete("venuemap", "locationId=?", new String[] {""+map.getLocationId()});
				   del++;
				   continue;
			   }
				stmt.bindString(1, ""+map.getLocationId());
				stmt.bindString(2, map.getMapName());
				stmt.bindString(3, map.getType());
				stmt.bindString(4, map.getFloorName());
				stmt.bindString(5, map.getHallNo());				
				stmt.bindString(6, map.getFilePath());
				stmt.bindString(7, map.getCreationDate());
				stmt.bindString(8, map.getUpdatedBy());
				stmt.bindString(9, ""+map.getProductId());
				stmt.bindString(10,""+map.getYear());
				stmt.bindString(11,""+map.getStatus());
				stmt.bindString(12,""+map.getIsUpdated());
				
				stmt.execute();
				stmt.clearBindings();
				count++;
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR", "Total records in venueMap table is "+length+", and "+count+" record is submitted successfully... Total record with 0 status:"+del);
			Log.i("EFAIR", "Total execution time for venueMap table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
		
		
		/*try{
			int length=maps.size();
			int count=0;
			for(int i=0;i<length;i++){
				//DownloadingData.initializeData.myProgress(4+(float)i/length);
			   VenueMap map=maps.get(i);
			   if(map.getStatus()==STATUS_ZERO){
				   db.delete("venuemap", "locationId=?", new String[] {""+map.getLocationId()});
				   continue;
			   }
			   ContentValues row =new ContentValues();
				row.put("locationId",map.getLocationId());
				row.put("mapName",map.getMapName());
				row.put("type",map.getType());
				row.put("floorName",map.getFloorName());
				row.put("hallNo",map.getHallNo());
				
				row.put("filePath",map.getFilePath());
				Log.i("STATUS", "map.getFilePath():"+map.getFilePath());
				row.put("creationDate",map.getCreationDate());
				row.put("updatedBy",map.getUpdatedBy());
				row.put("productId",map.getProductId());
				row.put("year",map.getYear());
				row.put("status", map.getStatus());
				
				Log.i("WEB_DATA",""+map.getYear());
				if(DownloadingData.isDbUpdate)//if updates are available
				{  
					if(isNewItem("venuemap",""+map.getLocationId(),""))
				    {
					  db.update("venuemap", row, "locationId=?", new String[] {""+map.getLocationId()});
					 
					  updatingVenueMapFile.add(row.get("filePath").toString());
				      Log.i("EXPO", "New venuemap is added");
				    }else{
				    	db.insert("venuemap", null, row);
				    }	
				}else{
					db.insert("venuemap", null, row);
				}
				count++;
			}
		 Log.i("EFAIR", ""+count+" venue map record is submitted successfully...");
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}
	
	public void submitExhibitorLocation(ArrayList<ExhibitorLocation> exhibitorLocations){
		
		try{
		      int length=exhibitorLocations.size();
		      int count=0;
		      int del=0;
		      long startTransTime = System.currentTimeMillis();
		      db.beginTransaction();
		      SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_EXHIBITOR_LOCATION);
		      for(int i=0;i<length;i++){
		    	ExhibitorLocation exhibitorLocation=exhibitorLocations.get(i);  
		        if(exhibitorLocation.getStatus()==STATUS_ZERO){
		        	db.delete("exhibitorLocation", "exhibitorLocationId=?", new String[] {""+exhibitorLocation.getExhibitorLocationId()});
		        	del++;
		        	continue;
		        }
				stmt.bindString(1, ""+exhibitorLocation.getExhibitorLocationId());
				stmt.bindString(2, ""+exhibitorLocation.getLocationId());
				stmt.bindString(3, ""+exhibitorLocation.getExhibitorId());
				stmt.bindString(4, exhibitorLocation.getStandNo());
				stmt.bindString(5, exhibitorLocation.getxLocation());
				stmt.bindString(6, exhibitorLocation.getyLocation());
				stmt.bindString(7, exhibitorLocation.getCreationDate());
				stmt.bindString(8, exhibitorLocation.getUpdatedBy());
				stmt.bindString(9, ""+ exhibitorLocation.getStatus());
				
				stmt.execute();
				stmt.clearBindings();
				count++;
		      }
		      Log.i("EFAIR", "Total records in exhibitorLocation table is "+length+", and "+count+" record is submitted successfully..."+del+" records with status 0");
		      Log.i("EFAIR", "Total execution time for exhibitorLocation table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		      db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
		
		/*try{
		      int length=exhibitorLocations.size();
		      int count=0;
		      for(int i=0;i<length;i++){
		    	  //DownloadingData.initializeData.myProgress(5+(float)i/length);
		    	ExhibitorLocation exhibitorLocation=exhibitorLocations.get(i);  
		        if(exhibitorLocation.getStatus()==STATUS_ZERO){
		        	db.delete("exhibitorLocation", "exhibitorLocationId=?", new String[] {""+exhibitorLocation.getExhibitorLocationId()});
		        	continue;
		        }
				ContentValues row =new ContentValues();
				row.put("exhibitorLocationId",exhibitorLocation.getExhibitorLocationId());
				row.put("locationId",exhibitorLocation.getLocationId());
				row.put("exhibitorId",exhibitorLocation.getExhibitorId());
				row.put("standNo",exhibitorLocation.getStandNo());
				row.put("xLocation",exhibitorLocation.getxLocation());
				row.put("yLocation",exhibitorLocation.getyLocation());
				row.put("creationDate",exhibitorLocation.getCreationDate());
				row.put("updatedBy",exhibitorLocation.getUpdatedBy());
				row.put("status", exhibitorLocation.getStatus());
				if(DownloadingData.isDbUpdate)//if updates are available
				{  
					if(isNewItem("exhibitorLocation",""+exhibitorLocation.getExhibitorLocationId(),""))
				    {
					  db.update("exhibitorLocation", row, "exhibitorLocationId=?", new String[] {""+exhibitorLocation.getExhibitorLocationId()});
					  Log.i("UPDATES", "New exhibitorLocation is updated");
				    }else{
				    	db.insert("exhibitorLocation", null,row);
				    	 Log.i("UPDATES", "New exhibitorLocation is added");
				    }	
				}else{
					db.insert("exhibitorLocation", null,row);
				}
				count++;
		      }
		  Log.i("EFAIR", ""+count+" ExhibitorLocation record is submitted successfully...");
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
	}
	
	
	public void submitAdmins(ArrayList<Admin> admins){
		
		try{
			int length=admins.size();
			int count=0,del=0;
			long StartTime=System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt=db.compileStatement(Query.INSERT_INTO_ADMIN);
			for(int i=0;i<length;i++)
			{
				Admin ad= admins.get(i);
				if(ad.getStatus()==STATUS_ZERO)
				{
					db.delete("admin", "adminId=?", new String[]{""+ad.getAdminId()});
					del++;
					continue;
				}
				stmt.bindString(1, ""+ad.getAdminId());
				stmt.bindString(2, ad.getAdminName());
				stmt.bindString(3, ""+ad.getEmployeeId());
				stmt.bindString(4, ad.getContactNo());
				stmt.bindString(5, ad.getCreationDate());
				stmt.bindString(6, ad.getUpdatedBy());
				stmt.bindString(7, ""+ad.getStatus());
				stmt.execute();
				stmt.clearBindings();
				count++;
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR","total:"+count+" records submitted successfully... Total:"+del+" recordes with status Zero");
			Log.i("Time","Time taken to insert in Admin Table"+(System.currentTimeMillis()-StartTime));
					
		}catch(Exception e)
		{
			e.printStackTrace();
			Log.i("EFAIR","Exception while submitting Admin"+e);
		}finally{
			db.endTransaction();
		}
		
		/*try{
			int length=admins.size();
			int count=0;
			for(int i=0;i<length;i++){
				Admin admin=admins.get(i);
				if(admin.getStatus()==STATUS_ZERO){
					db.delete("admin", "adminId=?", new String[]{""+admin.getAdminId()});
					continue;
				}
				ContentValues row=new ContentValues();
				row.put("adminId",admin.getAdminId());
				row.put("adminName",admin.getAdminName());
				row.put("employeeId",admin.getEmployeeId());
				row.put("contactNo",admin.getContactNo());
				row.put("creationDate",admin.getCreationDate());
				row.put("updatedBy",admin.getUpdatedBy());
				row.put("status", admin.getStatus());
				db.insert("admin", null,row);
				count++;
			}
			 Log.i("EFAIR", ""+count+" Admin record is submitted successfully...");
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}
	
	@SuppressWarnings("finally")
	public void submitExhibitorCategory(ArrayList<ExhibitorCategory> exhibitorCategories) {
	
		try{
			int count=0;
			int del=0;
			int length=exhibitorCategories.size();
			 long startTransTime = System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt= db.compileStatement(Query.INSERT_INTO_EXHIBITOR_CATEGORY);
			for(int i=0;i<length;i++){			
				ExhibitorCategory ec=exhibitorCategories.get(i);
				if(ec.getStatus()==STATUS_ZERO)
				{
					try{
						db.delete("exhibitorCategory", "categoryId=?", new String[]{""+ec.getCategoryId()});
						del++;
					}catch(Exception e)
					{
						e.printStackTrace();
					}finally
					{
						continue;
					}
				}
				stmt.bindString(1, ""+ec.getCategoryId());
				stmt.bindString(2, ec.getCategoryName());
				stmt.bindString(3, ""+ec.getStatus());
				stmt.bindString(4, ec.getCreationDate());
				stmt.bindString(5, ec.getCreationDate());
				stmt.execute();
				stmt.clearBindings();
				count++;
			}
			Log.i("EFAIR", "Total records in exhibitorCategory table is "+length+", and "+count+" record is submitted successfully..."+del+" records with status 0");
		      Log.i("EFAIR", "Total execution time for exhibitorLocation table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		      db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
	
	}
	
	public void submitQuestionnaires(ArrayList<VisitorQuestionnaire> questionnaires){
		try{
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_VISITOR_QUESTIONNAIRE);
			for(int i=0;i<questionnaires.size();i++){
				Log.i("EFAIR","Visitor Questionnaire "+questionnaires.get(i).getStatus());
				if(questionnaires.get(i).getStatus()==STATUS_ZERO){
					db.delete("visitor_questionnaire", "questionId=?", new String[]{""+questionnaires.get(i).getQuestionId()});
					continue;
				}
				stmt.bindString(1,""+questionnaires.get(i).getQuestionId());
				stmt.bindString(2,questionnaires.get(i).getQuestionType());
				stmt.bindString(3,questionnaires.get(i).getQuestion());
				stmt.bindString(4,""+questionnaires.get(i).getOrderId());
				stmt.bindString(5,questionnaires.get(i).getUserType());
				stmt.bindString(6,""+questionnaires.get(i).getStatus());
				stmt.bindString(7,questionnaires.get(i).getCreationDate());
				stmt.bindString(8,questionnaires.get(i).getUpdatedBy());
				
				stmt.execute();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			Log.i("EFAIR","Exception while inserting data into visitor_questionnaires table, "+e.getLocalizedMessage());
		}finally{
			db.endTransaction();
		}
	}
	
	public void submitQueOption(ArrayList<VisitorQueOption> options){
		try{
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_VISITOR_QUE_OPTION);
			for(int i=0;i<options.size();i++){
				if(options.get(i).getStatus()==STATUS_ZERO){
					db.delete("visitor_que_option", "optionId=?", new String[]{""+options.get(i).getOptionId()});
					continue;
				}
				stmt.bindString(1, ""+options.get(i).getOptionId());
				stmt.bindString(2, ""+options.get(i).getQuestionId());
				stmt.bindString(3, options.get(i).getOption());
				stmt.bindString(4, ""+options.get(i).getOrderId());
				stmt.bindString(5, ""+options.get(i).getStatus());
				stmt.bindString(6, options.get(i).getCreationDate());
				stmt.bindString(7, options.get(i).getUpdatedBy());
				
				stmt.execute();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			Log.i("EFIAR","Exception while inserting data into visitor_que_options table, "+e.getLocalizedMessage());
		}finally{
			db.endTransaction();
		}
	}

	//this method is used to get all the products from database
	public ArrayList<Products> getAllProducts(){
		ArrayList<Products> list=new ArrayList<Products>();
		//String[] projection=new String[]{"_id","name","address","mobile","email","courseId","fees","registerDate","updateDate"};
	
		Cursor cursor = db.query("products", null, null,null, null,null,"productName");
		//Cursor cursor1 =db.rawQuery("", null);
		while (cursor.moveToNext()) {
			
			int productId=cursor.getInt(0);
			String productName=cursor.getString(2);
			String productType=cursor.getString(3);
			String productBrand=cursor.getString(4);
			String description=cursor.getString(5);
			String creationDate= cursor.getString(6);
			String updatedBy=cursor.getString(7);
			Log.i("NBTI",""+productId+productBrand);
			//create products object
			Products product=new Products(productId,productName,productType,productBrand, description,creationDate,updatedBy);
			
			//add product object to list
			list.add(product);

		}
		//close the cursor
		cursor.close();
		Log.i("EFAIR", "All product list fetched successfully");
		return list;
		
	}
	
	public ArrayList<Exhibitors> getAllExibitorsOfSelectedProduct(String productId){
		ArrayList<Exhibitors> list=new ArrayList<Exhibitors>();
		/*String query="SELECT  exhibitorName,exhibitorId FROM exhibitors where exhibitorId in"+
			   	"(select exhibitorId from ExhibitorProducts where productId="+productId+")";
		Log.i("EFAIR",query);*/
		Cursor cursor=db.rawQuery(Query.SELECT_ALL_EXHIBITOR_OF_A_PRODUCT_WITH_LOCATION, new String[]{productId});
		//Cursor cursor2=db.rawQuery(Query.SELECT_ALL_EXIBITOR_LOCATION_OF_A_PRODUCT_ID, new String[]{productId});
		Log.i("EFAIR",Integer.toString(cursor.getCount()));
		while(cursor.moveToNext()){
			String exhibitorName=cursor.getString(1)+" Hall No. "+cursor.getString(2);
			int exhibitorId=cursor.getInt(0);
			Exhibitors exhibitors=new Exhibitors();
			//set the user object data
			exhibitors.setExhibitorName(exhibitorName);
			exhibitors.setExhibitorId(exhibitorId);
			list.add(exhibitors);
			
		}
		cursor.close();
		Log.i("EFAIR", "Exhibitor list fetched successfully");
		return list;
		
	}
	public List<Object> getDetailOfSelectedExibitor(String exibitorId,String stallNo){
		List<Object> list = new ArrayList<Object>();
		//important note ! when access object from list then must cast it to Users and ExhibitoMap
		Cursor cursor=db.rawQuery(Query.SELECT_EXIBITOR_DETAILS, new String[]{exibitorId,exibitorId,"%"+stallNo+"%"});
		Log.i("EFAIR","in getdetails size"+Integer.toString(cursor.getCount())+"columns "+Integer.toString(cursor.getColumnCount()));
		cursor.moveToFirst();
		 try{
			int exhibitorId=cursor.getInt(0);
			String type=cursor.getString(1);
			String floorName=cursor.getString(2);
			String standNo=cursor.getString(3);
			String exhibitorName=cursor.getString(4);
			String exhibiDetails=cursor.getString(5);
			String address1=cursor.getString(6);
			String address2=cursor.getString(7);
			String city=cursor.getString(8);
			String state=cursor.getString(9);
			String country=cursor.getString(10);
			//String nation=cursor.getString(11);
			String pinCode=cursor.getString(11);
			String contactPerson=cursor.getString(12);
			String mobileNo=cursor.getString(13);
			String contactNo=cursor.getString(14);
			String fax=cursor.getString(15);
			String email=cursor.getString(16);
			String website=cursor.getString(17);
			
			String hallNo=cursor.getString(18);
			String filePath=cursor.getString(19);
			String xLocation=cursor.getString(20);
			String yLocation=cursor.getString(21);
			String refNo=cursor.getString(22);
			//create ExhibitorMap object
			ExhibitorLocation location=new ExhibitorLocation();
			//set the ExhibitorMap object data 
			location.setExhibitorId(exhibitorId);
			location.setStandNo(standNo);
			location.setxLocation(xLocation);
			location.setyLocation(yLocation);
			//add exhibitormap object to list
			list.add(location);
			//create an object of Users class
			VenueMap map=new VenueMap();
			map.setType(type);
			map.setFloorName(floorName);
			map.setFilePath(filePath);
			map.setHallNo(hallNo);
			//map.setHallNo(hallNo);
			list.add(map);
			Exhibitors exhibitor=new Exhibitors();
			exhibitor.setExhibitorName(exhibitorName);
			exhibitor.setAddress1(address1);
			exhibitor.setAddress2(address2);
			exhibitor.setCity(city);
			exhibitor.setState(state);
			//exhibitor.setNationality(nation);
			exhibitor.setCountry(country);
			exhibitor.setPinCode(pinCode);
			exhibitor.setWebsite(website);
			exhibitor.setContactPerson(contactPerson);
			exhibitor.setFax(fax);
			exhibitor.setEmail(email);
			exhibitor.setMobileNo(mobileNo);
			exhibitor.setContactNo(contactNo);
			exhibitor.setDescription(exhibiDetails);
			exhibitor.setRefNo(refNo);
			//add user object to list
			list.add(exhibitor);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		cursor.close();
		
		return list;
	}
	
	
	//this method is used to get the list product of a particular company
	
	public ArrayList<String> getProductListOfExibitor(String exibitorId){
		ArrayList<String> list=new ArrayList<String>();
		Cursor cursor=db.rawQuery(Query.SELECT_LIST_OF_PRODUCT_PROVIDED_BY_EXIBITOR, new String[]{exibitorId});
		while(cursor.moveToNext()){
			String productName=cursor.getString(0);
			list.add(productName);
		}
		cursor.close();
		return list;
	}
	
	public ArrayList<Products> getProductListOfExibitor1(String exibitorId){
		ArrayList<Products> list=new ArrayList<Products>();
		Cursor cursor=db.rawQuery(Query.SELECT_LIST_OF_PRODUCT_PROVIDED_BY_EXIBITOR1, new String[]{exibitorId});
		while(cursor.moveToNext()){
			String productName=cursor.getString(0);
			int productId=cursor.getInt(1);
			Products product = new Products();
		    product.setProductId(productId);
		    product.setProductName(productName);
			list.add(product);
		}
		cursor.close();
		return list;
	}
	
	public ArrayList<String> getAllPhotoOfAProduct(
			String productName){
		ArrayList<String> list =new ArrayList<String>();
		Cursor cursor=db.rawQuery(Query.SELECT_IMG_AS_PER_PRODUCT, new String[]{productName});
		while(cursor.moveToNext()){
			String productName1=cursor.getString(0);
			list.add(productName1);
		}
		cursor.close();
		return list;
		//logic goes here
		
	}
	public ArrayList<String> getPhotosOnExhibitorWiseSearch(
			String searchString){
				ArrayList<String> list =new ArrayList<String>();
		Cursor cursor=db.rawQuery(Query.SEARCH_IMAGE_BY_EXHIBITOR_WISE,new String[]{"%"+searchString+"%"});
		while(cursor.moveToNext()){
			String exhibitorId1=cursor.getString(0);
			list.add(exhibitorId1);
			
		}
		cursor.close();
		return list;
		//logic goes here
		
	}
	public ArrayList<String> getPhotosOnProductWiseSearch(
			String searchString){
				ArrayList<String> list =new ArrayList<String>();
		Cursor cursor=db.rawQuery(Query.SEARCH_IMAGE_BY_PRODUCT_WISE,new String[]{"%"+searchString+"%"});
		while(cursor.moveToNext()){
			String searchStr=cursor.getString(0);
			list.add(searchStr);
		}
		cursor.close();
		return list;
		//logic goes here	
	}
	//this method is used to get the search result based on exhibitors name 
	public ArrayList<Exhibitors> getAllExhibitorWiseSearchResult(String exhibitorName){
		ArrayList<Exhibitors> list=new ArrayList<Exhibitors>();
		Cursor cursor=db.rawQuery(Query.SELECT_NAMEWISE_EXIBITORS_SEARCH_WITH_LOCATION,new String[]{"%"+exhibitorName+"%"});
		
		Log.i("EFAIR","Number Of result "+cursor.getCount());
		while(cursor.moveToNext()){
			String exhibitorName1;
			String type=cursor.getString(4);
			if(type.equalsIgnoreCase(Integer.toString(Map.hall))){
				exhibitorName1=cursor.getString(1)+"~~"+"Hall No."+cursor.getString(2)+", Stand No."+cursor.getString(3);
			}else{
				exhibitorName1=cursor.getString(1)+"~~"+"Mart- "+cursor.getString(2)+", Stand No."+cursor.getString(3);
			}
			int exhibitorId=cursor.getInt(0);
			//create an object of Users class
            Exhibitors exhib=new Exhibitors();
			exhib.setExhibitorName(exhibitorName1);
			exhib.setExhibitorId(exhibitorId);
			//add user object to list
			list.add(exhib);	
		}
		cursor.close();
		
		
		return list;
		
	}
	
	
	//this method is used to get the searched result based to product name
	public ArrayList<Exhibitors> getAllProductWiseSearchResult(String productName){
		ArrayList<Exhibitors> list=new ArrayList<Exhibitors>();
	    Cursor cursor=db.rawQuery(Query.SELECT_ALL_EXHIBITOR_BY_PRODUCTNAME_WITH_LOCATION,new String[]{"%"+productName+"%"});
		while(cursor.moveToNext()){
			String exhibitorName;
			String type=cursor.getString(4);
			if(type.equalsIgnoreCase(Integer.toString(Map.hall))){
				exhibitorName=cursor.getString(1)+"~~"+"Hall No."+cursor.getString(2)+", Stand No."+cursor.getString(3);
			}else{
				exhibitorName=cursor.getString(1)+"~~"+"Mart- "+cursor.getString(2)+", Stand No."+cursor.getString(3);
			}
			int exhibitorId=cursor.getInt(0);
			//create an object of Users class
			Exhibitors exhib=new Exhibitors();
			exhib.setExhibitorName(exhibitorName);
			exhib.setExhibitorId(exhibitorId);
			//add user object to list
			list.add(exhib);
		}
		cursor.close();
		return list;
	}	
	//This method used to exhibitor wise product search
	public ArrayList<String> exhibitorWiseProductSearch(
			String searchString){
		ArrayList<String> list =new ArrayList<String>();
		Cursor cursor=db.rawQuery(Query.SELECT_EXHIBITORWISE_PRODUCTS_EARCH,new String[]{"%"+searchString+"%"});
		while(cursor.moveToNext()){
			String productName=cursor.getString(0);
			list.add(productName);
		}
		cursor.close();
		return list;
	
	}
	public ArrayList<String> productWiseExhibitorSearch(
			String searchString){
		ArrayList<String> list =new ArrayList<String>();
		Cursor cursor=db.rawQuery(Query.SELECT_PRODUCTWISE_EXIBITOR_SEARCH,new String[]{"%"+searchString+"%"});
		while(cursor.moveToNext()){
			String exhibitorName=cursor.getString(0);
			list.add(exhibitorName);
		}
		cursor.close();
		return list;
	
	}
	public ArrayList<String> searchPhotosByExhibitorName(
			String searchString){
		ArrayList<String> list =new ArrayList<String>();
		Cursor cursor=db.rawQuery(Query.SEARCH_PHOTOS_BY_EXHIBITOR_NAME,new String[]{"%"+searchString+"%"});
		while(cursor.moveToNext()){
			String photoPath=cursor.getString(0);
			list.add(photoPath);
		}
		cursor.close();
		return list;
	
	}
	
	public ArrayList<Exhibitors>getAllExhibNameFromExhibTable(){
		ArrayList<Exhibitors> list =new ArrayList<Exhibitors>();
		Cursor cursor=db.rawQuery(Query.SELECT_NAMEWISE_EXIBITORS_SEARCH_WITH_LOCATION,new String[]{"%"+""+"%"});
		while(cursor.moveToNext()){
			String exhibitorName1;
			String type=cursor.getString(4);
			if(type.equalsIgnoreCase(Integer.toString(Map.hall))){
				exhibitorName1=cursor.getString(1)+"\n"+"Hall No."+cursor.getString(2)+", Stand No. "+cursor.getString(3);
			}else{
				exhibitorName1=cursor.getString(1)+"\n"+"Mart- "+cursor.getString(2)+", Stand No. "+cursor.getString(3);
			}
			int exhibitorId=cursor.getInt(0);
			//create an object of Users class
            Exhibitors exhib=new Exhibitors();
			exhib.setExhibitorName(exhibitorName1);
			exhib.setExhibitorId(exhibitorId);
			exhib.setContactPerson(cursor.getString(3));
			//add user object to list
			list.add(exhib);	
		}
		cursor.close();
		return list;
	}
	public ArrayList<Exhibitors>getAllExhibNameFromExhibAlbum(){
		ArrayList<Exhibitors> list =new ArrayList<Exhibitors>();
		Cursor cursor=db.rawQuery(Query.SELECT_NAMEWISE_EXIBITORS_SEARCH_WITH_LOCATION,new String[]{"%"+""+"%"});
		while(cursor.moveToNext()){
			String exhibitorName1;
			String type=cursor.getString(4);
			
		    exhibitorName1=cursor.getString(1);
			
			int exhibitorId=cursor.getInt(0);
			//create an object of Users class
            Exhibitors exhib=new Exhibitors();
			exhib.setExhibitorName(exhibitorName1);
			exhib.setExhibitorId(exhibitorId);
			exhib.setContactPerson(cursor.getString(3));
			//add user object to list
			list.add(exhib);	
		}
		cursor.close();
		return list;
	}
	public ArrayList<String>getAllProductNameFromProductTable(){
		ArrayList<String> list =new ArrayList<String>();
		Cursor cursor=db.rawQuery(Query.SELECT_ALL_PRODUCT1,null);
		while(cursor.moveToNext()){
			String exhibitorName=cursor.getString(0);
			list.add(exhibitorName);
		}
		cursor.close();
		return list;
		//logic goes here
		
	}
	public List<Object> getExhibitorDetailsByExhibitorName(String exhibitorName){
		List<Object> list = new ArrayList<Object>();
		//important note ! when access object from list then must cast it to Users and ExhibitoMap
		Cursor cursor=db.rawQuery(Query.SELECT_EXHIBITOR_DETAIL_BY_EXHIBITOR_NAME,new String[]{exhibitorName,exhibitorName,exhibitorName});
		Log.i("EMALL","in getdetails size="+Integer.toString(cursor.getCount())+"columns "+Integer.toString(cursor.getColumnCount()));
		cursor.moveToFirst();
		
		 try{
			
			int exhibitorId=cursor.getInt(0);
			String type=cursor.getString(1);
			String floorName=cursor.getString(2);
			String standNo=cursor.getString(3);
			String exhibitorName1=cursor.getString(4);
			String exhibitorDescription=cursor.getString(5);
			String address1=cursor.getString(6);
			String city=cursor.getString(7);
			String state=cursor.getString(8);
			String country=cursor.getString(9);
			String nationality=cursor.getString(10);
			String pinCode=cursor.getString(11);
			String contactPerson=cursor.getString(12);
			String mobileNo=cursor.getString(13);
			String contactNo=cursor.getString(14);
			String fax=cursor.getString(15);
			String email=cursor.getString(16);
			String website=cursor.getString(17);
			//create ExhibitorMap object
			ExhibitorLocation location=new ExhibitorLocation();
			//set the ExhibitorMap object data 
			location.setExhibitorId(exhibitorId);
			location.setStandNo(standNo);
			//add exhibitormap object to list
			list.add(location);
			//create an object of Users class
			VenueMap map=new VenueMap();
			map.setType(type);
			map.setFloorName(floorName);
			list.add(map);
			Exhibitors exhibitor=new Exhibitors();
	        exhibitor.setExhibitorId(exhibitorId);
			exhibitor.setExhibitorName(exhibitorName1);
			exhibitor.setDescription(exhibitorDescription);
			exhibitor.setAddress1(address1);
			exhibitor.setCity(city);
			exhibitor.setState(state);
			exhibitor.setCountry(country);
			exhibitor.setNationality(nationality);
			exhibitor.setPinCode(pinCode);
			exhibitor.setWebsite(website);
			exhibitor.setContactPerson(contactPerson);
			exhibitor.setEmail(email);
			exhibitor.setMobileNo(mobileNo);
			exhibitor.setContactNo(contactNo);
			exhibitor.setFax(fax);
			//add user object to list
			list.add(exhibitor);
			
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EMALL","db exception="+e);
		}
		cursor.close();
		
		return list;
	}
	
	
	public ArrayList<Object> selectAllPhotosAndExibitorName(){
		ArrayList<Object> list =new ArrayList<Object>();
		Cursor cursor=db.rawQuery(Query.SELECT_ALL_PHOTOS_AND_EXHIBITOR_NAME,null);
		while(cursor.moveToNext()){
			String filePath=cursor.getString(0);
			String exhibitorName=cursor.getString(1);
			Exhibitors Exhi=new Exhibitors();
			Exhi.setExhibitorName(exhibitorName);
			list.add(Exhi);
			ProductPhoto photos=new ProductPhoto();
			photos.setFilePath(filePath);
			list.add(photos);
		}
		cursor.close();
		return list;
		//logic goes here
		
	}
	
	//Select filePath from product photos by exhibitorId
		public ArrayList<String> getFilePathByExhibitorId(
				String exhibitorId){
			ArrayList<String> list =new ArrayList<String>();
			Cursor cursor=db.rawQuery(Query.SELECT_IMG_AS_PER_PRODUCT, new String[]{exhibitorId});
			while(cursor.moveToNext()){
				String exhibitorId1=cursor.getString(0);
				list.add(exhibitorId1);
			}
			cursor.close();
			return list;
		}
		//This method is use to select location of ground floor,first floor likewise....
				public ArrayList<ExhibitorLocation>getLocation(String locationId){
					ArrayList<ExhibitorLocation> list=new ArrayList<ExhibitorLocation>();
				    Cursor cursor=db.rawQuery(Query.SELECT_LOCATION,new String[]{locationId});
					while(cursor.moveToNext()){
						String xLocation=cursor.getString(0);
						String yLocation=cursor.getString(1);
						ExhibitorLocation exhiLoc=new ExhibitorLocation();
						exhiLoc.setxLocation(xLocation);
						exhiLoc.setyLocation(yLocation);
						list.add(exhiLoc);
					}
					cursor.close();
					return list;
				}	
				/*public ArrayList<VenueMap> getAllExibitorsLocationOfSelectedProduct(String productId){
					ArrayList<VenueMap> list=new ArrayList<VenueMap>();
					String query="SELECT  exhibitorName,exhibitorId FROM exhibitors where exhibitorId in"+
						   	"(select exhibitorId from ExhibitorProducts where productId="+productId+")";
					Log.i("EFAIR",query);
					Cursor cursor=db.rawQuery(Query.SELECT_ALL_EXIBITOR_LOCATION_OF_A_PRODUCT_ID, new String[]{productId});
					Log.i("EFAIR",Integer.toString(cursor.getCount()));
					while(cursor.moveToNext()){
						//String exibitorId=cursor.getString(0);
						String mapName=cursor.getString(0);
						VenueMap venuemap=new VenueMap();
						//set the user object data
						venuemap.setMapName(mapName);
						
						//add the user to list
						list.add(venuemap);
					}
					
					return list;
					}*/
				public ArrayList<Exhibitors>searchExhibitorsHallWise(String hallNo){
					ArrayList<Exhibitors> list=new ArrayList<Exhibitors>();
				    Cursor cursor=db.rawQuery(Query.SEARCH_EXHIBITORS_BY_HALL_NO,new String[]{hallNo});
					while(cursor.moveToNext()){
						int exhibitorId=cursor.getInt(0);
						String exhibitorName;
						String type=cursor.getString(4);
						if(type.equalsIgnoreCase(Integer.toString(Map.hall))){
							exhibitorName="Hall No."+cursor.getString(2)+", Stand No."+cursor.getString(3)+"~~"+cursor.getString(1);
						}else{
							exhibitorName="Mart- "+cursor.getString(2)+", Stand No."+cursor.getString(3)+"~~"+cursor.getString(1);
						}
						Exhibitors exhi=new Exhibitors();
						exhi.setExhibitorId(exhibitorId);
						exhi.setExhibitorName(exhibitorName);
						list.add(exhi);
					}
					cursor.close();
					return list;
				}	
				public ArrayList<Exhibitors>searchExhibitorsCity(String city){
					ArrayList<Exhibitors> list=new ArrayList<Exhibitors>();
				    Cursor cursor=db.rawQuery(Query.SEARCH_EXHIBITORS_BY_CITY,new String[]{"%"+city+"%"});
					while(cursor.moveToNext()){
						int exhibitorId=cursor.getInt(0);
						String exhibitorName;
						String type=cursor.getString(4);
						if(type.equalsIgnoreCase(Integer.toString(Map.hall))){
							exhibitorName=cursor.getString(1)+"~~"+"Hall No."+cursor.getString(2)+", Stand No."+cursor.getString(3);
						}else{
							exhibitorName=cursor.getString(1)+"~~"+"Mart- "+cursor.getString(2)+", Stand No."+cursor.getString(3);
						}
						Exhibitors exhi=new Exhibitors();
						exhi.setExhibitorId(exhibitorId);
						exhi.setExhibitorName(exhibitorName);
						list.add(exhi);
					}
					cursor.close();
					return list;
				}	
				public ArrayList<String>selectAllHallNo(){
					ArrayList<String> list=new ArrayList<String>();
				    Cursor cursor=db.rawQuery(Query.SELECT_ALL_HALL_NO,null);
					while(cursor.moveToNext()){
						String hall_No=cursor.getString(0);
						list.add(hall_No);
					}
					cursor.close();
					return list;
				}	
				public ArrayList<String>selectAllLangName(){
					ArrayList<String> list=new ArrayList<String>();
				    Cursor cursor=db.rawQuery(Query.SELECT_ALL_LANGUAGE,new String[]{});
					while(cursor.moveToNext()){
						String langName=cursor.getString(0);
						list.add(langName);
					}
					cursor.close();
					return list;
				}	
				public ArrayList<Exhibitors>searchExhibitorsLanguageWise(String langName){
					ArrayList<Exhibitors> list=new ArrayList<Exhibitors>();
				    Cursor cursor=db.rawQuery(Query.SEARCH_EXHIBITORS_BY_LANGUAGE_WITH_LOCATION,new String[]{"%"+langName+"%"});
					while(cursor.moveToNext()){
						int exhibitorId=cursor.getInt(0);
						String exhiName=cursor.getString(1)+"\n   Hall No. "+cursor.getString(2);
						Log.i("EFAIR","ExhibitorName="+exhiName);
						Exhibitors exh=new Exhibitors();
						exh.setExhibitorId(exhibitorId);
						exh.setExhibitorName(exhiName);
						list.add(exh);
					}
					cursor.close();
					return list;
				}
				public ArrayList<String>selectAllEventName(){
					ArrayList<String> list=new ArrayList<String>();
				    Cursor cursor=db.rawQuery(Query.SELECT_ALL_EVENTNAME,new String[]{});
					while(cursor.moveToNext()){
						String eventName=cursor.getString(0);
						list.add(eventName);
					}
					cursor.close();
					return list;
				}	
				public ArrayList<String>selectAllHallName(){
					ArrayList<String> list=new ArrayList<String>();
				    Cursor cursor=db.rawQuery(Query.SELECT_ALL_HALL_NAME,new String[]{});
					while(cursor.moveToNext()){
						String hallName=cursor.getString(0);
						list.add(hallName);
					}
					cursor.close();
					return list;
				}
				/*************** Searching event used in eventFragment class *******************/
				public ArrayList<EventInfo>searchEvents(String eventName){
					ArrayList<EventInfo> list=new ArrayList<EventInfo>();
				   try{
					Cursor cursor=db.rawQuery(Query.SEARCH_EVENT_BY_NAME_WITH_LOCATION,new String[]{"%"+eventName+"%"});
					while(cursor.moveToNext()){
						int eventId=cursor.getInt(0);
						int eventLocationId=cursor.getInt(1);
						String eventName1=cursor.getString(2).trim()+"     \nDate: "+cursor.getString(5)+" And  Hall No. "+cursor.getString(6);
						String startTime=cursor.getString(3);
						String endTime=cursor.getString(4);
						EventInfo exhi=new EventInfo();
						 exhi.setEventId(eventId);
						 exhi.setEventLocationId(eventLocationId);
						 exhi.setEventName(eventName1);
						 exhi.setStartTime(startTime);
						 exhi.setEndTime(endTime);
						 exhi.setEventDate(cursor.getString(5));
						list.add(exhi);
					}
					cursor.close();
				   }catch(Exception e){
					   e.printStackTrace();
				   }
					return list;
				}	
				public ArrayList<EventInfo>searchEventBYAuthor(String searchString){
					ArrayList<EventInfo> list=new ArrayList<EventInfo>();
				   try{
					Cursor cursor=db.rawQuery(Query.SEARCH_BY_AUTHOR_OR_PUBLISHERS,new String[]{"%"+searchString+"%","%"+searchString+"%"});
					while(cursor.moveToNext()){
						int eventId=cursor.getInt(0);
						int eventLocationId=cursor.getInt(1);
						String eventName1=cursor.getString(2).trim()+"      \nDate: "+cursor.getString(5)+" And Hall No. "+cursor.getString(6);
						String startTime=cursor.getString(3);
						String endTime=cursor.getString(4);
						EventInfo exhi=new EventInfo();
						 exhi.setEventId(eventId);
						 exhi.setEventLocationId(eventLocationId);
						 exhi.setEventName(eventName1);
						 exhi.setStartTime(startTime);
						 exhi.setEndTime(endTime);
						 exhi.setEventDate(cursor.getString(5));
						list.add(exhi);
					}
					cursor.close();
				   }catch(Exception e){
					   e.printStackTrace();
				   }
					return list;
				}	
				public ArrayList<EventInfo>searchExhibitorsHallNameWise(String hallName){
					ArrayList<EventInfo> list=new ArrayList<EventInfo>();
				    Cursor cursor=db.rawQuery(Query.SEARCH_EVENT_BY_HALL_NO,new String[]{"%"+hallName+"%"});
					while(cursor.moveToNext()){
						
						int eventId=cursor.getInt(0);
						int eventLocationId=cursor.getInt(1);
						String eventName1=cursor.getString(2).trim()+"     \nDate: "+cursor.getString(5)+"And Hall No. "+cursor.getString(6);
						String startTime=cursor.getString(3);
						String endTime=cursor.getString(4);
						EventInfo exhi=new EventInfo();
						 exhi.setEventId(eventId);
						 exhi.setEventLocationId(eventLocationId);
						 exhi.setEventName(eventName1);
						 exhi.setStartTime(startTime);
						 exhi.setEndTime(endTime);
						 exhi.setEventDate(cursor.getString(5));
						list.add(exhi);
					}
					cursor.close();
					return list;
				}	
								
					
	class DBHelper extends SQLiteOpenHelper {

		String file;
		Context ctx;

		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			ctx = context;
			
			//db=getWritableDatabase();
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i("EFAIR","in oncreateeee");
			AssetManager am = ctx.getAssets();
			try {
				InputStream in = am.open("packplus.sql");
				byte data[] = new byte[in.available()];
				in.read(data);
				in.close();
				file = new String(data);
				Log.i("EFAIR", "DBInitializer.createTables:"+file);
				DBInitializer.createTables(db, file);
			} catch (IOException e) {
				e.printStackTrace();
				Log.i("EFAIR", "DBInitializer.createTables:"+e);
			}
			 
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}/*try {
	db.execSQL("expo.sql");
	Log.i("EFAIR","in oncreate");
} catch (Exception e) {
	// TODO: handle exception
	Log.i("EFAIR","expo.sql exception:"+e);
}*/

    /*int r=R.drawable.expo2;
    context.getApplicationContext();
	Log.i("EFAIR", "insertFromFile x:"+r+","+context.getApplicationContext());
	//int x=insertFromFile(context.getApplicationContext(),R.drawable.expo2);
	//Log.i("EFAIR", "insertFromFile x:"+x);
*/			
	
	//this method is used to convert appointment time to 12 hour formate
	public String convertTimeTo12HourFormate(String time){
		String time1[]=time.split(":");
		if(Integer.parseInt(time1[0])>12){
			int hour=Integer.parseInt(time1[0])-12;
			return hour+":"+time1[1];
		}else{
			return time;
		}
	}
	/*************** Added after expo-2.0 version *********************/
	public ArrayList<Exhibitors> getAllExibitorsOfSelectedProductSorted(String productId){
		ArrayList<Exhibitors> list=new ArrayList<Exhibitors>();
		Cursor cursor=db.rawQuery(Query.SELECT_ALL_EXHIBITOR_OF_A_PRODUCT_WITH_LOCATION_SORTED, new String[]{productId});
		//Cursor cursor2=db.rawQuery(Query.SELECT_ALL_EXIBITOR_LOCATION_OF_A_PRODUCT_ID, new String[]{productId});
		Log.i("EFAIR",Integer.toString(cursor.getCount()));
		while(cursor.moveToNext()){
			String exhibitorName=cursor.getString(1)+"-Hall No. "+cursor.getString(2)+",Stand No."+cursor.getString(3);
			int exhibitorId=cursor.getInt(0);
			Exhibitors exhibitors=new Exhibitors();
			//set the user object data
			exhibitors.setExhibitorName(exhibitorName);
			exhibitors.setExhibitorId(exhibitorId);
			list.add(exhibitors);
			
		}
		ExbListViewAdapter.premiumMembers=cursor.getCount();
	   cursor=db.rawQuery(Query.SELECT_ALL_EXHIBITOR_OF_A_PRODUCT_WITH_LOCATION_SORTED2, new String[]{productId});
		while(cursor.moveToNext()){
			String exhibitorName=cursor.getString(1)+"-Hall No. "+cursor.getString(2)+",Stand No."+cursor.getString(3);
			int exhibitorId=cursor.getInt(0);
			Exhibitors exhibitors=new Exhibitors();
			//set the user object data
			exhibitors.setExhibitorName(exhibitorName);
			exhibitors.setExhibitorId(exhibitorId);
			list.add(exhibitors);
			
		}
		cursor.close();
		Log.i("EFAIR", "Exhibitor list fetched successfully");
		return list;
		
	}
	/************ Tag base search in event  */
	public ArrayList<EventInfo>searchEventTagBased(String eventName,String hallName){
		ArrayList<EventInfo> list=new ArrayList<EventInfo>();
	   try{
		Cursor cursor=db.rawQuery(Query.SEARCH_EVENT_BY_NAME_WITH_LOCATION,new String[]{"%"+eventName+"%"});
		while(cursor.moveToNext()){
			int eventId=cursor.getInt(0);
			int eventLocationId=cursor.getInt(1);
			String eventName1=cursor.getString(2).trim()+"     \nDate: "+cursor.getString(5)+" And  Hall No. "+cursor.getString(6);
			String startTime=cursor.getString(3);
			String endTime=cursor.getString(4);
			EventInfo exhi=new EventInfo();
			 exhi.setEventId(eventId);
			 exhi.setEventLocationId(eventLocationId);
			 exhi.setEventName(eventName1);
			 exhi.setStartTime(startTime);
			 exhi.setEndTime(endTime);
			 exhi.setEventDate(cursor.getString(5));
			list.add(exhi);
		}
		Log.i("EXPO", "Search event list size for even name="+list.size());
		cursor=db.rawQuery(Query.SEARCH_EVENT_BY_HALL_NO,new String[]{"%"+hallName+"%"});
		while(cursor.moveToNext()){
			int eventId=cursor.getInt(0);
			int eventLocationId=cursor.getInt(1);
			String eventName1=cursor.getString(2).trim()+"     \nDate: "+cursor.getString(5)+" And  Hall No. "+cursor.getString(6);
			String startTime=cursor.getString(3);
			String endTime=cursor.getString(4);
			EventInfo exhi=new EventInfo();
			 exhi.setEventId(eventId);
			 exhi.setEventLocationId(eventLocationId);
			 exhi.setEventName(eventName1);
			 exhi.setStartTime(startTime);
			 exhi.setEndTime(endTime);
			 exhi.setEventDate(cursor.getString(5));
			list.add(exhi);
		}
		Log.i("EXPO", "Search event list size for even name and holl no="+list.size());
		cursor.close();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
		return list;
	}	
	/************ Tag base search in exhibitors  */
	public ArrayList<Exhibitors>searchExhibitorsByTags(String exhibitorName,String langName,String productName,String hallNo){
		ArrayList<Exhibitors> list=new ArrayList<Exhibitors>();
	    Cursor cursor=db.rawQuery(Query.SELECT_NAMEWISE_EXIBITORS_SEARCH_WITH_LOCATION,new String[]{"%"+exhibitorName+"%"});
		while(cursor.moveToNext()){
			int exhibitorId=cursor.getInt(0);
			String exhiName=cursor.getString(1)+"\n   Hall No. "+cursor.getString(2);
			Log.i("EFAIR","ExhibitorName="+exhiName);
			Exhibitors exh=new Exhibitors();
			exh.setExhibitorId(exhibitorId);
			exh.setExhibitorName(exhiName);
			list.add(exh);
		}
		cursor=db.rawQuery(Query.SELECT_ALL_EXHIBITOR_BY_PRODUCTNAME_WITH_LOCATION,new String[]{"%"+productName+"%"});
		while(cursor.moveToNext()){
			int exhibitorId=cursor.getInt(0);
			String exhiName=cursor.getString(1)+"\n   Hall No. "+cursor.getString(2);
			Log.i("EFAIR","ExhibitorName="+exhiName);
			Exhibitors exh=new Exhibitors();
			exh.setExhibitorId(exhibitorId);
			exh.setExhibitorName(exhiName);
			list.add(exh);
		}
		cursor=db.rawQuery(Query.SEARCH_EXHIBITORS_BY_HALL_NO,new String[]{hallNo});
		while(cursor.moveToNext()){
			int exhibitorId=cursor.getInt(0);
			String exhiName=cursor.getString(1)+"\n   Hall No. "+cursor.getString(2);
			Log.i("EFAIR","ExhibitorName="+exhiName);
			Exhibitors exh=new Exhibitors();
			exh.setExhibitorId(exhibitorId);
			exh.setExhibitorName(exhiName);
			list.add(exh);
		}
		cursor=db.rawQuery(Query.SEARCH_EXHIBITORS_BY_LANGUAGE_WITH_LOCATION,new String[]{"%"+langName+"%"});
		while(cursor.moveToNext()){
			int exhibitorId=cursor.getInt(0);
			String exhiName=cursor.getString(1)+"\n   Hall No. "+cursor.getString(2);
			Log.i("EFAIR","ExhibitorName="+exhiName);
			Exhibitors exh=new Exhibitors();
			exh.setExhibitorId(exhibitorId);
			exh.setExhibitorName(exhiName);
			list.add(exh);
		}
		cursor.close();
		return list;
	}
	//this method is used to submit the exhibitors record to database
		public void submitLanguage(ArrayList<Language> language){
			
			try{
				int length=language.size();
				int count=0;
				long startTransTime = System.currentTimeMillis();
				db.beginTransaction();
				SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_LANGUAGE);
				for(int i=0;i<length;i++){
					Language lang=language.get(i);
					if(lang.getStatus()==STATUS_ZERO){
						db.delete("language","langId=?", new String[] {""+lang.getLangId()});
						continue;
					}
					stmt.bindString(1, ""+lang.getLangId());
					stmt.bindString(2, lang.getLangName());					
					stmt.bindString(3, lang.getCreationDate());
					stmt.bindString(4, lang.getUpdatedBy());
					stmt.bindString(5, ""+lang.getStatus());
					
					stmt.execute();
					stmt.clearBindings();
					count++;
				}
				db.setTransactionSuccessful();
				Log.i("EFAIR", "Total records in language table is "+length+", and "+count+" record is submitted successfully...");
				Log.i("EFAIR", "Total execution time for language table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
			}catch(Exception e){
				e.printStackTrace();
				Log.i("EFAIR"," records inserted excetion in Language table... "+e);
			}finally{
				db.endTransaction();
			}
		}
			/*try{
				int length=language.size();
				int count=0;
				for(int i=0;i<length;i++){
					//DownloadingData.initializeData.myProgress(2+(float)i/length);
					Language lang=language.get(i);
					if(lang.getStatus()==STATUS_ZERO){
						db.delete("language","langId=?", new String[] {""+lang.getLangId()});
						continue;
					}
					
						
					ContentValues row=new ContentValues();
					row.put("langId",lang.getLangId());
					row.put("langName",lang.getLangName());
					
					row.put("creationDate",lang.getCreationDate());
					row.put("updatedBy",lang.getUpdatedBy());
					row.put("status", lang.getStatus());
					if(DownloadingData.isDbUpdate)//if updates are available
					{  
						if(isNewItem("language",""+lang.getLangId(),""))
					    {
						  db.update("language", row, "langId=?", new String[] {""+lang.getLangId()});
						  Log.i("UPDATES", "New language is updated");
					    }else{
					    	db.insert("language",null,row);
					    	 Log.i("UPDATES", "New language is added");
					    }	
					}else{
						db.insert("language",null,row);
					}
					count++;
				}
				Log.i("EFAIR",""+count+" records inserted successfully in Language table... ");
			}catch(Exception e){
				e.printStackTrace();
				Log.i("EFAIR"," records inserted excetion in Language table... "+e);
			}
		}*/
		//this method is used to submit the exhibitors record to database
				public void submitExhibitorLang(ArrayList<ExhibitorLanguage> exhibitorLang){
					
					try{
						int length=exhibitorLang.size();
						int count=0;
						long startTransTime = System.currentTimeMillis();
						db.beginTransaction();
						SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_EXHIBITOR_LANG);
						for(int i=0;i<length;i++){
							ExhibitorLanguage exhilang=exhibitorLang.get(i);
							if(exhilang.getStatus()==STATUS_ZERO){
								db.delete("exhibitorLang","langId=? and exhibitorId=?", new String[] {""+exhilang.getLangId(),""+exhilang.getExhibitorId()});
								continue;
							}
							stmt.bindString(1, ""+exhilang.getLangId());
							stmt.bindString(2, ""+exhilang.getExhibitorId());							
							stmt.bindString(3, exhilang.getCreationDate());
							stmt.bindString(4, exhilang.getUpdatedBy());
							stmt.bindString(5, ""+exhilang.getStatus());
							
							stmt.execute();
							stmt.clearBindings();
							count++;
						}
						db.setTransactionSuccessful();
						Log.i("EFAIR", "Total records in ExhibitorLang table is "+length+", and "+count+" record is submitted successfully...");
						Log.i("EFAIR", "Total execution time for ExhibitorLang table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
					}catch(Exception e){
						e.printStackTrace();
						Log.i("EFAIR"," records inserted excetion in exhibitorLang table... "+e);
					}finally{
						db.endTransaction();
					}
					
					/*try{
						int length=exhibitorLang.size();
						int count=0;
						for(int i=0;i<length;i++){
							//DownloadingData.initializeData.myProgress(3+(float)i/length);
							ExhibitorLanguage exhilang=exhibitorLang.get(i);
							if(exhilang.getStatus()==STATUS_ZERO){
								db.delete("exhibitorLang","langId=? and exhibitorId=?", new String[] {""+exhilang.getLangId(),""+exhilang.getExhibitorId()});
								continue;
							}
							ContentValues row=new ContentValues();
							row.put("langId",exhilang.getLangId());
							row.put("exhibitorId",exhilang.getExhibitorId());
							
							row.put("creationDate",exhilang.getCreationDate());
							row.put("updatedBy",exhilang.getUpdatedBy());
							row.put("status", exhilang.getStatus());
							if(DownloadingData.isDbUpdate)//if updates are available
							{  
								if(isNewItem("exhibitorLang",""+exhilang.getLangId(),""+exhilang.getExhibitorId()))
							    {
								  db.update("exhibitorLang", row, "langId=? and exhibitorId=?", new String[] {""+exhilang.getLangId(),""+exhilang.getExhibitorId()});
								  Log.i("UPDATES", "New language is updated");
							    }else{
							    	db.insert("exhibitorLang",null,row);
							    	 Log.i("UPDATES", "New language is added");
							    }	
							}else{
								db.insert("exhibitorLang",null,row);
							}
							count++;
						}
						Log.i("EFAIR",""+count+" records inserted successfully in exhibitorLang table... ");
					}catch(Exception e){
						e.printStackTrace();
						Log.i("EFAIR"," records inserted excetion in exhibitorLang table... "+e);
					}*/
				}
				//this method is used to submit the exhibitors record to database
				public void submitEvent(ArrayList<Event> event){
					
					try{
						int length=event.size();
						int count=0;
						long startTransTime = System.currentTimeMillis();
						db.beginTransaction();
						SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_EVENT);
						for(int i=0;i<length;i++){
							Event even=event.get(i);
							if(even.getStatus()==STATUS_ZERO){
								db.delete("event", "event_loc_id=?", new String[] {""+even.getEventLocationId()});
								continue;
							}
							stmt.bindString(1, ""+even.getEventLocationId());
							stmt.bindString(2, even.getEventLocationName());
							stmt.bindString(3, even.getEventLocationDescription());
							stmt.bindString(4, even.getCreationDate());
							stmt.bindString(5, even.getUpdatedBy());
							stmt.bindString(6, ""+even.getStatus());
							
							stmt.execute();
							stmt.clearBindings();
							count++;
						}
						db.setTransactionSuccessful();
						Log.i("EFAIR", "Total records in event table is "+length+", and "+count+" record is submitted successfully...");
						Log.i("EFAIR", "Total execution time for event table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
					}catch(Exception e){
						e.printStackTrace();
						Log.i("EFAIR"," records inserted excetion in event table... "+e);
					}finally{
						db.endTransaction();
					}
					
					
					/*try{
						int length=event.size();
						int count=0;
						for(int i=0;i<length;i++){
							//DownloadingData.initializeData.myProgress(6+(float)i/length);
							Event even=event.get(i);
							if(even.getStatus()==STATUS_ZERO){
								db.delete("event", "event_loc_id=?", new String[] {""+even.getEventLocationId()});
								continue;
							}
							ContentValues row=new ContentValues();
							row.put("event_loc_id",even.getEventLocationId());
							row.put("eventLocationName",even.getEventLocationName());
							row.put("eventlocationDescription",even.getEventLocationDescription());
							row.put("creationDate",even.getCreationDate());
							row.put("updatedBy",even.getUpdatedBy());
							row.put("status", even.getStatus());
							if(DownloadingData.isDbUpdate)//if updates are available
							{  
								if(isNewItem("event",""+even.getEventLocationId(),""))
							    {
								  db.update("event", row, "event_loc_id=?", new String[] {""+even.getEventLocationId()});
								  Log.i("UPDATES", "New event is updated");
							    }else{
							    	 db.insert("event",null,row);
							    	 Log.i("UPDATES", "New event is added");
							    }	
							}else{
								db.insert("event",null,row);
							}
							
							count++;
						}
						Log.i("EFAIR",""+count+" records inserted successfully in exhibitorLang table... ");
					}catch(Exception e){
						e.printStackTrace();
						Log.i("EFAIR"," records inserted excetion in exhibitorLang table... "+e);
					}*/
				}
				//this method is used to submit the exhibitors record to database
				public void submitEventInfo(ArrayList<EventInfo> eventInfo){
					
					try{
						int length=eventInfo.size();
						int count=0;
						long startTransTime = System.currentTimeMillis();
						db.beginTransaction();
						SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_EVENT_INFO);
						for(int i=0;i<length;i++){
							EventInfo even=eventInfo.get(i);
							if(even.getStatus()==STATUS_ZERO){
								db.delete("eventInfo", "eventId=?", new String[] {""+even.getEventId()});
								continue;
							}
							
							stmt.bindString(1, ""+even.getEventId());
							stmt.bindString(2, ""+even.getEventLocationId());
							stmt.bindString(3, even.getEventName());
							stmt.bindString(4, even.getEventDate());
							stmt.bindString(5, even.getStartTime());
							stmt.bindString(6, even.getEndTime());
							stmt.bindString(6, even.getDescription());
							stmt.bindString(8, even.getCreationDate());
							stmt.bindString(9, even.getUpdatedBy());
							stmt.bindString(10,even.getModerater());
							stmt.bindString(11,even.getPanelLists());
							stmt.bindString(12,even.getEventType());
							stmt.bindString(13,""+even.getStatus());
							
							stmt.execute();
							stmt.clearBindings();
							count++;
						}
						db.setTransactionSuccessful();
						Log.i("EFAIR", "Total records in eventInfo table is "+length+", and "+count+" record is submitted successfully...");
						Log.i("EFAIR", "Total execution time for eventInfo table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
					}catch(Exception e){
						e.printStackTrace();
						Log.i("EFAIR"," records inserted excetion in eventInfo table... "+e);
					}finally{
						db.endTransaction();
					}
					/*try{
						int length=eventInfo.size();
						Log.i("EFAIR", "eventInfoLength:"+length);
						int count=0;
						for(int i=0;i<length;i++){
							//DownloadingData.initializeData.myProgress(7+(float)i/length);
							EventInfo even=eventInfo.get(i);
							if(even.getStatus()==STATUS_ZERO){
								db.delete("eventInfo", "eventId=?", new String[] {""+even.getEventId()});
								continue;
							}
							ContentValues row=new ContentValues();
							row.put("eventId",even.getEventId());
							row.put("eventLocationId",even.getEventLocationId());
							row.put("eventName",even.getEventName());
							row.put("eventDate",even.getEventDate());
							row.put("startTime",even.getStartTime());
							row.put("endTime",even.getEndTime());
							row.put("description",even.getDescription());
							row.put("creationDate",even.getCreationDate());
							row.put("updatedBy",even.getUpdatedBy());
							row.put("moderator",even.getModerater());
							row.put("panelists",even.getPanelLists());
							row.put("eventType",even.getEventType());
							row.put("status", even.getStatus());
							Log.i("EXPO","even.getEventDate()"+even.getEventDate()+"panelists:"+even.getPanelLists());
							if(DownloadingData.isDbUpdate)//if updates are available
							{  
								if(isNewItem("eventInfo",""+even.getEventId(),""))
							    {
								  db.update("eventInfo", row, "eventId=?", new String[] {""+even.getEventId()});
								  Log.i("UPDATES", "New eventInfo is updated");
							    }else{
							    	db.insert("eventInfo",null,row);
							    	 Log.i("UPDATES", "New eventInfo is added");
							    }	
							}else{
								db.insert("eventInfo",null,row);
							}
							
							count++;
						}
						Log.i("EFAIR",""+count+" records inserted successfully in eventInfo table... ");
						
					}catch(Exception e){
						e.printStackTrace();
						Log.i("EFAIR"," records inserted excetion in eventInfo table... "+e);
					}*/
				}
				//this method is used to submit the exhibitors record to database
				public void submitEventLocation(ArrayList<EventLocation> eventLocation){
					
					try{
						int length=eventLocation.size();
						int count=0;
						long startTransTime = System.currentTimeMillis();
						db.beginTransaction();
						SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_EVENT_LOCATION);
						for(int i=0;i<length;i++){
							EventLocation even=eventLocation.get(i);
							if(even.getStatus()==STATUS_ZERO){
								db.delete("eventLocation", "eventLocationId=?", new String[] {""+even.getEventLocationId()});
								continue;
							}
							stmt.bindString(1, ""+even.getEventLocationId());
							stmt.bindString(2, ""+even.getLocationId());
							stmt.bindString(3, even.getLocationName());
							stmt.bindString(4, even.getDescription());
							stmt.bindString(5, even.getxLocation());
							stmt.bindString(6, even.getyLocation());
							stmt.bindString(7, even.getCreationDate());
							stmt.bindString(8, even.getUpdatedBy());
							stmt.bindString(9,""+even.getEvent_loc_id());
							stmt.bindString(10,""+even.getStatus());
							
							stmt.execute();
							stmt.clearBindings();
							count++;
						}
						db.setTransactionSuccessful();
						Log.i("EFAIR", "Total records in eventLocation table is "+length+", and "+count+" record(s) is submitted successfully...");
						Log.i("EFAIR", "Total execution time for eventLocation table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
					}catch(Exception e){
						e.printStackTrace();
						Log.i("EFAIR"," records inserted excetion in eventLocation table... "+e);
					}finally{
						db.endTransaction();
					}
					
					/*try{
						int length=eventLocation.size();
						int count=0;
						for(int i=0;i<length;i++){
							//DownloadingData.initializeData.myProgress(8+(float)i/length);
							EventLocation even=eventLocation.get(i);
							if(even.getStatus()==STATUS_ZERO){
								db.delete("eventLocation", "eventLocationId=?", new String[] {""+even.getEventLocationId()});
								continue;
							}
							ContentValues row=new ContentValues();
							row.put("eventLocationId",even.getEventLocationId());
							row.put("eventLocationId",even.getEventLocationId());
							row.put("locationId",even.getLocationId());
							row.put("locationName",even.getLocationName());
							row.put("description",even.getDescription());
							row.put("xLocation",even.getxLocation());
							row.put("yLocation",even.getyLocation());
							row.put("creationDate",even.getCreationDate());
							row.put("updatedBy",even.getUpdatedBy());
							row.put("event_loc_id",even.getEvent_loc_id());
							row.put("status", even.getStatus());
							if(DownloadingData.isDbUpdate)//if updates are available
							{  
								if(isNewItem("eventLocation",""+even.getEventLocationId(),""))
							    {
								  db.update("eventLocation", row, "eventLocationId=?", new String[] {""+even.getEventLocationId()});
								  Log.i("UPDATES", "New eventLocation is updated");
							    }else{
							    	db.insert("eventLocation",null,row);
							    	 Log.i("UPDATES", "New eventLocation is added");
							    }	
							}else{
								db.insert("eventLocation",null,row);
							}
							
							count++;
						}
						Log.i("EFAIR",""+count+" records inserted successfully in eventLocation table... ");
					}catch(Exception e){
						e.printStackTrace();
						Log.i("EFAIR"," records inserted exception in eventLocation table... "+e);
					}*/
				}
				
				/***This method is used to put adService records***/
				public void submitAdService(ArrayList<AdService> adServices){
					try{
						int count=0;
						for(int i=0;i<adServices.size();i++){
							/*DownloadingData.initializeData.myProgress((float)i/adServices.size());*/
							
							if(adServices.get(i).getStatus()==STATUS_ZERO){
								try{
									db.delete("adServe", "adId=?", new String[]{""+adServices.get(i).getAdId()});
								}catch(Exception e){
									
								}
								continue;
							}
							
							ContentValues row = new ContentValues();
							row.put("adId", adServices.get(i).getAdId());
							row.put("placementId", adServices.get(i).getPlacementId());
							row.put("hashCode", adServices.get(i).getHashCode());
							row.put("url", adServices.get(i).getUrl());
							row.put("status", adServices.get(i).getStatus());
							row.put("creationDate", adServices.get(i).getCreationDate());
							row.put("updatedBy", adServices.get(i).getUpdatedBy());
							
							db.insertWithOnConflict("adServe", null, row, SQLiteDatabase.CONFLICT_REPLACE);
							
							count++;				
						}
						 Log.i("EFAIR", ""+count+" adServe record is submitted successfully...");
					}catch(Exception e){
						 Log.i("EFAIR", "Exception when insertting record into adServe table.");
						e.printStackTrace();
					}
				}
				
				/***This method is used to put martFacility records***/
				public void submitMartFacilities(ArrayList<MartFacilities> martFacilityies){
					try{
						int count=0;
						for(int i=0;i<martFacilityies.size();i++){
							/*DownloadingData.initializeData.myProgress((float)i/martFacilityies.size());*/
							Log.i("NUM","martFacilityies.size()in DB:"+martFacilityies.size());
							if(martFacilityies.get(i).getStatus()==STATUS_ZERO){
								try{
									db.delete("mart_facilities", "facilityId=?", new String[]{""+martFacilityies.get(i).getFacilityId()});
								}catch(Exception e){
									
								}
								continue;
							}
							
							ContentValues row = new ContentValues();
							row.put("facilityId", martFacilityies.get(i).getFacilityId());
							row.put("facilityName", martFacilityies.get(i).getFacilityName());
							row.put("imgName", martFacilityies.get(i).getImgName());
							row.put("status", martFacilityies.get(i).getStatus());
							row.put("creationDate", martFacilityies.get(i).getCreationDate());
							row.put("updatedBy", martFacilityies.get(i).getUpdatedBy());
							
							db.insertWithOnConflict("mart_facilities", null, row, SQLiteDatabase.CONFLICT_REPLACE);
							
							count++;				
						}
						 Log.i("EFAIR", ""+count+" martFacility record is submitted successfully...");
					}catch(Exception e){
						 Log.i("EFAIR", "Exception when insertting record into martFacility table.");
						e.printStackTrace();
					}
				}
				
				//this method is used to submit the exhibitors record to database
				public void submitCompany(ArrayList<Company> company){
					
					try{
						int length=company.size();
						int count=0;
						long startTransTime = System.currentTimeMillis();
						db.beginTransaction();
						SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_COMPANY);
						for(int i=0;i<length;i++){
							Company com=company.get(i);
							if(com.getStatus()==STATUS_ZERO){
								db.delete("company", "companyId=?", new String[] {""+com.getCompanyId()});
								continue;
							}
							stmt.bindString(1, ""+com.getCompanyId());
							stmt.bindString(2, com.getCompanyName());
							stmt.bindString(3, com.getCompanyDescription());
							stmt.bindString(4, com.getAddress1());
							stmt.bindString(5, com.getAddress2());
							stmt.bindString(6, com.getCity());
							stmt.bindString(7, com.getState());
							stmt.bindString(8, com.getPinCode());
							stmt.bindString(9, com.getCountry());
							stmt.bindString(10,com.getWebSite());
							stmt.bindString(11,com.getEmail());
							stmt.bindString(12,com.getContactNo());
							stmt.bindString(13,com.getCreationDate());
							stmt.bindString(14,com.getUpdatedBy());
							stmt.bindString(15,com.getPanNumber());
							stmt.bindString(16,""+com.getStatus());
							stmt.bindString(17,com.getNumber_of_persons_accompanying());
							
							stmt.execute();
							stmt.clearBindings();
							count++;
						}
			            db.setTransactionSuccessful();
						Log.i("EFAIR", "Total records in company table is "+length+", and "+count+" record(s) is submitted successfully...");
						Log.i("EFAIR", "Total execution time for company table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
					}catch(Exception e){
						e.printStackTrace();
						Log.i("EFAIR"," records inserted excetion in company table... "+e);
					}finally{
						db.endTransaction();
					}
					
					/*try{
						int length=company.size();
						int count=0;
						for(int i=0;i<length;i++){
							//DownloadingData.initializeData.myProgress(11+(float)i/length);
							Company com=company.get(i);
							if(com.getStatus()==STATUS_ZERO){
								db.delete("company", "companyId=?", new String[] {""+com.getCompanyId()});
								continue;
							}
							ContentValues row=new ContentValues();
							row.put("companyId",com.getCompanyId());
							row.put("companyName",com.getCompanyName());
							row.put("companyDescription",com.getCompanyDescription());
							row.put("address1",com.getAddress1());
							row.put("address2",com.getAddress2());
							row.put("city",com.getCity());
							row.put("state",com.getState());
							row.put("pinCode",com.getPinCode());
							row.put("country",com.getCountry());
							row.put("website",com.getWebSite());
							row.put("email",com.getEmail());
							row.put("contactNo",com.getContactNo());
							row.put("creationDate",com.getCreationDate());
							row.put("updatedBy",com.getUpdatedBy());
							row.put("panNumber",com.getPanNumber());
							row.put("status", com.getStatus());
							if(DownloadingData.isDbUpdate)//if updates are available
							{  
								if(isNewItem("company",""+com.getCompanyId(),""))
							    {
								  db.update("company", row, "companyId=?", new String[] {""+com.getCompanyId()});
								  Log.i("UPDATES", "New company is updated");
							    }else{
							    	db.insert("company",null,row);
							    	 Log.i("UPDATES", "New company is added");
							    }	
							}else{
								db.insert("company",null,row);
							}
							
							count++;
						}
			           
						Log.i("EFAIR",""+count+" records inserted successfully in company table... ");
					}catch(Exception e){
						e.printStackTrace();
						Log.i("EFAIR"," records inserted excetion in company table... "+e);
					}*/
				}
				//this method is used to get the search result based on company name 
				public ArrayList<Exhibitors> getAllExhibitorWiseSearchResult(List<String> companies){
					ArrayList<Exhibitors> list=new ArrayList<Exhibitors>();
					for(int i=0;i<companies.size();i++)
					{
					String companyName=companies.get(i);
					Cursor cursor=db.rawQuery(Query.SELECT_NAMEWISE_EXIBITORS_SEARCH_WITH_LOCATION,new String[]{"%"+companyName+"%"});
					
					Log.i("EFAIR","Number Of result "+cursor.getCount());
					while(cursor.moveToNext()){
						String exhibitorName1=cursor.getString(1)+"-Hall No. "+cursor.getString(2);
						int exhibitorId=cursor.getInt(0);
						//create an object of Users class
			            Exhibitors exhib=new Exhibitors();
						exhib.setExhibitorName(exhibitorName1);
						exhib.setExhibitorId(exhibitorId);
						//add user object to list
						list.add(exhib);	
					}
					cursor.close();
					}
					
					return list;
					
				}
				/****** get Layouts ****/
				public String[] getAllLayoutsImage(){
					String[] strArray=null;
					int x=0;
					try{
						Cursor cursor=db.rawQuery(Query.GET_ALL_LAYOUT_IMAGE,null);
						strArray=new String[cursor.getCount()];
						Log.i("STATUS", "Cursor:"+cursor.getCount());
						//cursor.moveToFirst();
						while(cursor.moveToNext())
						{ 
							strArray[x]=cursor.getString(0);
							Log.i("STATUS", "cursor.getString(0):"+cursor.getString(0));
							x++;
						}
						
					}catch(Exception e){
						e.printStackTrace();
						Log.i("STATUS", "Image retrive exception:"+e);
					}
					db.close();
					return strArray;
				}
				/****** get Layouts ****/
				public String[] getAllLayoutsImage2015(){
					String[] strArray=null;
					int x=0;
					try{
						Cursor cursor=db.rawQuery(Query.GET_ALL_LAYOUT_IMAGE2015,null);
						strArray=new String[cursor.getCount()];
						Log.i("STATUS", "Cursor:"+cursor.getCount());
						//cursor.moveToFirst();
						while(cursor.moveToNext())
						{ 
							strArray[x]=cursor.getString(0);
							Log.i("STATUS", "cursor.getString(0):"+cursor.getString(0));
							x++;
						}
						
					}catch(Exception e){
						e.printStackTrace();
						Log.i("STATUS", "Image retrive exception:"+e);
					}
					db.close();
					return strArray;
				}
				
				
				public boolean isAlbumExist(String exhibitorId){
					Cursor cursor=db.query("photoShoot", null,"exhibitorId=?",new String[]{exhibitorId}, null,null,null);
					if(cursor!=null){
						if(cursor.getCount()>0){
							return true;
						}else{
							return false;
						}
					}
					return false;
				}
/****************** Some util Area *********************/
	public boolean isTablesEmpty()
	{	int length;
		Cursor cursor=db.rawQuery(Query.IS_TABLES_EMPTY,null);
		length=cursor.getCount();
		cursor.close();
		Log.i("EXPO", "CMP length:"+length);
		if(length>0)
		{
			return false;
		}
		return true;
	}
	public boolean isNewItem(String tableName,String id1,String id2)
	{   Cursor cursor=null;
		try {
			Tables table = Tables.valueOf(tableName);
	      switch (table) {
	          case products:
	        	  cursor=db.rawQuery(Query.IS_NEW_ITEM_PRODUCT,new String[]{id1});
	        	  break;
	          case exhibitors:
	        	  cursor=db.rawQuery(Query.IS_NEW_ITEM_EXHIBITORS,new String[]{id1});
	        	  break;
	          case language:
	        	  cursor=db.rawQuery(Query.IS_NEW_ITEM_LANGUAGE,new String[]{id1});
	              break;
	          case exhibitorLang:
	        	  cursor=db.rawQuery(Query.IS_NEW_ITEM_EXHIBITOR_LANG,new String[]{id1,id2});
	        	  break;
	          case venuemap:
	        	  cursor=db.rawQuery(Query.IS_NEW_ITEM_VENUE_MAP,new String[]{id1});
	        	  break;
	          case exhibitorLocation:
	        	  cursor=db.rawQuery(Query.IS_NEW_ITEM_EXHIBITOR_LOCATION,new String[]{id1});
	        	  break;
	          case event:
	        	  cursor=db.rawQuery(Query.IS_NEW_ITEM_EVENT,new String[]{id1});
	              break;
              case eventInfo:
            	  cursor=db.rawQuery(Query.IS_NEW_ITEM_EVENT_INFO,new String[]{id1});
	              break;
              case eventLocation:
            	  cursor=db.rawQuery(Query.IS_NEW_ITEM_EVENT_LOCATION,new String[]{id1});
	              break;
              case exhibitorproducts:
            	  cursor=db.rawQuery(Query.IS_NEW_ITEM_EXIBITOR_PRODUCT,new String[]{id1,id2});
                  break;
              case productPhotos:
            	  cursor=db.rawQuery(Query.IS_NEW_ITEM_PRODUCT_PHOTOS,new String[]{id1}); 
                  break;
              case company:
            	  cursor=db.rawQuery(Query.IS_NEW_ITEM_COMPANY,new String[]{id1}); 
                  break;
              case resource:
            	  cursor=db.rawQuery(Query.IS_NEW_ITEM_RESOURCE,new String[]{id1}); 
                  break;
              case file_setting:
            	  cursor=db.rawQuery(Query.IS_NEW_ITEM_FILESETTING,new String[]{id1}); 
                  break;
                  
              case ExhibitorEntryExit:
            	  cursor=db.rawQuery(Query.IS_NEW_ITEM_EXHIBITORENTRYEXIT,new String[]{id1}); 
                  break;
                  
              case FacilityInformation:
            	  cursor=db.rawQuery(Query.IS_NEW_ITEM_FACILITYINFO,new String[]{id1}); 
                  break;
                  
              case facilityEntryExit:
            	  cursor=db.rawQuery(Query.IS_NEW_ITEM_FACILITYEXITENTRY,new String[]{id1}); 
                  break;
                  
	      }
		Log.i("EFAIR", "isItemNew:"+cursor.getCount());
		if(cursor!=null)
		{
		  if(cursor.getCount()>0)
		  {
			return true;
		  }
		}
		cursor.close();
	   } catch (Exception e) {
		// TODO: handle exception
		Log.i("EFAIR", "isItemNew exceptionnnn:"+e);
	}
		
		return false;
	}
	
	
	public List<Object> getDetailOfsourceAndDestExibitor(String exhibitorId1,String standNo1){
		List<Object> list = new ArrayList<Object>();
		//important note ! when access object from list then must cast it to Users and ExhibitoMap
		Cursor cursor=db.rawQuery(Query.SELECT_SOURCE_DEST_DETAILS_EXB, new String[]{exhibitorId1,standNo1});
		Log.i("EFAIR","in getdetails size"+Integer.toString(cursor.getCount())+" , columns "+Integer.toString(cursor.getColumnCount()));
		
		while(cursor.moveToNext()){
		 try{
			int exhibitorId=cursor.getInt(0);
			String type=cursor.getString(1);
			String floorName=cursor.getString(2);
			String standNo=cursor.getString(3);
			String exhibitorName=cursor.getString(4);
			String exhibiDetails=cursor.getString(5);
			String address1=cursor.getString(6);
			String city=cursor.getString(8);
			String state=cursor.getString(9);
			String country=cursor.getString(10);
			//String nation=cursor.getString(11);
			String pinCode=cursor.getString(11);
			String contactPerson=cursor.getString(12);
			String mobileNo=cursor.getString(13);
			String contactNo=cursor.getString(14);
			String fax=cursor.getString(15);
			String email=cursor.getString(16);
			String website=cursor.getString(17);
			
			String hallNo=cursor.getString(18);
			String filePath=cursor.getString(19);
			String xLocation=cursor.getString(20);
			String yLocation=cursor.getString(21);
			int exhibitorLocationId=cursor.getInt(22);
			int locationId=cursor.getInt(23);
			//create ExhibitorMap object
			ExhibitorLocation location=new ExhibitorLocation();
			//set the ExhibitorMap object data 
			location.setExhibitorId(exhibitorId);
			location.setStandNo(standNo);
			location.setxLocation(xLocation);
			location.setyLocation(yLocation);
			location.setExhibitorLocationId(exhibitorLocationId);
			//add exhibitormap object to list
			list.add(location);
			//create an object of Users class
			VenueMap map=new VenueMap();
			map.setType(type);
			map.setFloorName(floorName);
			map.setFilePath(filePath);
			map.setHallNo(hallNo);
			map.setLocationId(locationId);
			//map.setHallNo(hallNo);
			list.add(map);
			Exhibitors exhibitor=new Exhibitors();
			exhibitor.setExhibitorName(exhibitorName);
			exhibitor.setAddress1(address1);
			exhibitor.setCity(city);
			exhibitor.setState(state);
			//exhibitor.setNationality(nation);
			exhibitor.setCountry(country);
			exhibitor.setPinCode(pinCode);
			exhibitor.setWebsite(website);
			exhibitor.setContactPerson(contactPerson);
			exhibitor.setFax(fax);
			exhibitor.setEmail(email);
			exhibitor.setMobileNo(mobileNo);
			exhibitor.setContactNo(contactNo);
			exhibitor.setDescription(exhibiDetails);
			exhibitor.setExhibitorId(exhibitorId);
			//add user object to list
			list.add(exhibitor);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		} 
		cursor.close();
		
		return list;
	}
	
	public List<ExhibitorEntryExit> getExhibitorEntryExitPoint(String exbLocIdFrom,String exbLocIdTo){
		
		List<ExhibitorEntryExit> entryExit=new ArrayList<ExhibitorEntryExit>();
		Cursor cursor=db.rawQuery(Query.SELECT_EXHIBITOR_ENTRY_EXIT, new String[]{exbLocIdFrom,exbLocIdTo});
		Log.i("TABLE_DATA","[SIZE OF ENTRYEXIT] "+cursor.getColumnCount());
		while(cursor.moveToNext()){
			try{
				int exhibitorLocId=cursor.getInt(0);
				String x=cursor.getString(1);
				String y=cursor.getString(2);
				String type=cursor.getString(3);
				ExhibitorEntryExit obj=new ExhibitorEntryExit(exhibitorLocId, x, y, 1,null,null);
				obj.setType(type);
				entryExit.add(obj);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return entryExit;
	}
	
	public ArrayList<ExhibitorEntryExit> getAllElevatorLocationFloor(String locationId,String elevatorStatus){
		ArrayList<ExhibitorEntryExit> data=new ArrayList<ExhibitorEntryExit>();
		Cursor cursor=db.rawQuery(Query.SELECT_ELEVATOR_LOCATION_OF_FLOOR,new String[]{locationId,elevatorStatus});
		while(cursor.moveToNext()){
			try{
				int locationId1=cursor.getInt(0);
				String locationName=cursor.getString(1);
				String xLoc=cursor.getString(2);
				String yLoc=cursor.getString(3);
				String type=cursor.getString(4);
				
				ExhibitorEntryExit entryExit=new ExhibitorEntryExit(-1, xLoc, yLoc,0,null,null);
				entryExit.setType(type);
				data.add(entryExit);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return data;
		
	}
	
	
	public ArrayList<PhotoShoot> getAllAlbumList(){
		ArrayList<PhotoShoot> data=new ArrayList<PhotoShoot>();
		Cursor cursor=db.rawQuery(Query.SELECT_ALL_ALBUMS,null);
		while(cursor.moveToNext()){
			try{
				int exhibitorId=cursor.getInt(0);
				String companyName=cursor.getString(1);
				PhotoShoot phs=new PhotoShoot();
				phs.setExhibitorId(exhibitorId);
				phs.setPhotopath(companyName);
				
				
				data.add(phs);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return data;
		
	}
	
public void insertPhotoShoot(PhotoShoot photo,boolean flag){
		try{
	    ContentValues row=new ContentValues();
		
		row.put("photopath",photo.getPhotopath());
		row.put("exhibitorId",photo.getExhibitorId());
		row.put("visitorId",photo.getVisitorId());
		row.put("productId",photo.getProductId());
		Log.i("NBT","productId"+photo.getProductId());
		row.put("notes",photo.getNotes());
		if(flag){
		    db.insert("photoShoot",null,row);
		    Log.i("EXPO","Data inserted in photoshoot");
		}else{
			//this is used to update the notes
			db.update("photoShoot", row,"photopath=?",new String[]{photo.getPhotopath()});
			Log.i("EXPO","Data updated in photoshoot");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

public void deletePhotoshoot(String photopath){
	String query="delete from photoShoot where photopath like '%"+photopath+"%'";
	String whereClause="photopath like '%"+photopath+"%'";
	//db.execSQL(query, null);
	db.delete("photoShoot", whereClause, null);
	
}


public ArrayList<Exhibitors> getAllCity(){
	ArrayList<Exhibitors> data=new ArrayList<Exhibitors>();
	
	Cursor cursor=db.rawQuery(Query.SELECT_ALL_CITY,null);
	
	while(cursor.moveToNext()){
		try{
			
			Exhibitors phs=new Exhibitors();
			phs.setCity(cursor.getString(0));
			
			data.add(phs);
			Log.i("EXPO",cursor.getString(1));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	return data;
	
}

public ArrayList<PhotoShoot> getAlbumPhotopathList(String exhibitorId,String startPath){
	ArrayList<PhotoShoot> data=new ArrayList<PhotoShoot>();
	String query="Select * from photoShoot where exhibitorId="+exhibitorId+"  and photopath like '%"+startPath+"%'";
	//Cursor cursor=db.rawQuery(Query.SELECT_ALL_PHOTO_PATH,new String[]{exhibitorId,startPath});
	Cursor cursor=db.rawQuery(query,null);
	while(cursor.moveToNext()){
		try{
			
			PhotoShoot phs=new PhotoShoot();
			phs.setId(cursor.getInt(0));
			phs.setPhotopath(cursor.getString(1));
			phs.setExhibitorId(cursor.getInt(2));
			phs.setProductId(cursor.getInt(4));
			phs.setNotes(cursor.getString(5));
			
			
			data.add(phs);
			Log.i("EXPO",cursor.getString(1));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	return data;
	
}


	public boolean isAllreadyPlanned(String exhibitorId,String date){
		Cursor cursor=db.query("appointment",null,"exhibitorId=? and date=?",new String[]{exhibitorId,date},null,null,null);
	    if(cursor!=null){
	    	boolean flag=cursor.getCount()>0?true:false;
	    	return flag;
	    }else{
	    	return false;
	    }
	}
	
	
	public void submitBookMark(Favourite myFavorites){
		try{
				ContentValues row=new ContentValues();
				
				row.put("id",myFavorites.getExhibitorId());
				row.put("exhibitorId",myFavorites.getExhibitorId());
				row.put("creationDate",0);
				row.put("updatedBy","Ak");
				
				db.insert("favourite", null,row);
				Log.i("EFAIR", "MyFavorites record is submitted successfully...");
				
			 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public int deleteBookMark(int exhibitorId){
		try{
				//db.insert("myFavorites", null,row);
			int i=db.delete("favourite", "exhibitorId=?", new String[]{String.valueOf(exhibitorId)});
			Log.i("EFAIR", i+" MyFavorites record is submitted deleted...");
			return i;	
			 
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	public int getBookMark(int id)
	{   
		Cursor cursor=db.rawQuery(Query.MY_FAVORITES_EXHI_ID,new String[]{String.valueOf(id)});
		if(cursor.getCount()>0)
		{	
		cursor.moveToFirst();
		int fId=cursor.getInt(0);
		cursor.close();
		return fId;
		}
		return -1;
	}
	public boolean isBookMark(int id)
	{   
		Cursor cursor=db.rawQuery(Query.MY_FAVORITES_EXHI_ID,new String[]{String.valueOf(id)});
		if(cursor!=null)
		{	
			boolean flag=cursor.getCount()>0?true:false;
			return flag;
		
		}else{
			return false;
		}
		
	}
	public List <Favourite> getAllBookMark(int id)
	{    List<Favourite> list=new ArrayList<Favourite>();
		Cursor cursor=db.rawQuery(Query.MY_FAVORITES_EXHI_ID,new String[]{String.valueOf(id)});
		if(cursor.getCount()>0)
		{	
        while(cursor.moveToNext())
        {
        	Favourite myFavorites=new Favourite();
		myFavorites.setExhibitorId(cursor.getInt(0));
		list.add(myFavorites);
        }
		cursor.close();
		return list;
		}
		return null;
	}
	public ArrayList <Exhibitors> getAllBookMark()
	{    ArrayList<Exhibitors> list=new ArrayList<Exhibitors>();
	     
		Cursor cursor=db.rawQuery(Query.FAVORITES_DATA,null);
		if(cursor.getCount()>0)
		{	
        while(cursor.moveToNext())
        {
        	Exhibitors myFavorites=new Exhibitors();
		    myFavorites.setExhibitorId(cursor.getInt(0));
		    myFavorites.setExhibitorName(cursor.getString(1)+"~~"+"Hall No. "+cursor.getString(2)+", Stand No. "+ cursor.getString(3));
		    Log.i("EXPO",cursor.getString(1));
		    list.add(myFavorites);
        }
		cursor.close();
		
		}
		return list;
	}
	
	public ArrayList<VenueMap> getVenueMapDetailByType(String type){
		ArrayList<VenueMap> maplist=new ArrayList<VenueMap>();
		Cursor cursor=db.rawQuery(Query.VENUEMAP_INFO_BYTYPE,new String[]{type});
		try{
			while (cursor.moveToNext()) {
				VenueMap map=new VenueMap();
				map.setLocationId(cursor.getInt(0));
				map.setMapName(cursor.getString(1));
				map.setType(cursor.getString(2));
				map.setFloorName(cursor.getString(3));
				map.setHallNo(cursor.getString(4));
				map.setFilePath(cursor.getString(5));
				maplist.add(map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return maplist;
	}
	
	public VenueMap getVenueMapByLocationId(int locationId){
		VenueMap map = new VenueMap();
		Cursor cursor = db.rawQuery(Query.GET_VENUE_MAP_INFO_BY_LOCATION_ID, new String[]{""+locationId});
		if(cursor!=null){
			cursor.moveToFirst();
			map.setLocationId(cursor.getInt(cursor.getColumnIndex("locationId")));
			map.setFilePath(cursor.getString(cursor.getColumnIndex("filePath")));
			map.setFloorName(cursor.getString(cursor.getColumnIndex("floorName")));
			map.setMapName(cursor.getString(cursor.getColumnIndex("mapName")));
			map.setHallNo(cursor.getString(cursor.getColumnIndex("hallNo")));
		}
		return map;
	}
	
	public ExhibitorEntryExit getExhibitorEntryExitByExhibitor(int exhibitorId, int locationId){
		ExhibitorEntryExit entryExit = new ExhibitorEntryExit();
		Cursor cursor = db.rawQuery(Query.GET_EXHIBITOR_ENTRY_BY_EXHIBITOR, new String[]{""+exhibitorId,""+locationId});
		if(cursor!=null){
			cursor.moveToFirst();
			entryExit.setId(cursor.getInt(cursor.getColumnIndex("Id")));
			entryExit.setExhibitorLocationId(cursor.getInt(cursor.getColumnIndex("exhibitorLocationId")));
			entryExit.setxLoc(cursor.getString(cursor.getColumnIndex("x")));
			entryExit.setyLoc(cursor.getString(cursor.getColumnIndex("y")));
		}
		return entryExit;
	}
	public ArrayList<FileSettings> getAllFileSettings(int id){
		ArrayList<FileSettings> data=new ArrayList<FileSettings>( );
		Cursor cursor=db.query("file_setting", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
		try{
			while (cursor.moveToNext()) {
				FileSettings file=new FileSettings(cursor.getInt(0),cursor.getString(2),cursor.getString(1));
				data.add(file);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return data;
	}
	public ArrayList<Products> getMatchedProduct(String productName){
		ArrayList<Products> data=new ArrayList<Products>();
		Cursor cursor=db.rawQuery(Query.PRODUCT_SEARCH,new String[]{"%"+productName+"%"});
		try{
			while (cursor.moveToNext()) {
				Products product=new Products();
				product.setProductId(cursor.getInt(0));
				product.setProductName(cursor.getString(1));
				product.setParentId(cursor.getInt(2));
				data.add(product);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return data;
	}
	
	public ArrayList<ExhibitorCategory> getExhibitorCategory(){
		ArrayList<ExhibitorCategory> data=new ArrayList<ExhibitorCategory>();
		Cursor cursor=db.query("exhibitorCategory",null,null,null,null,null,"categoryName");
		try{
			while (cursor.moveToNext()) {
				ExhibitorCategory product=new ExhibitorCategory();
				product.setCategoryId(cursor.getInt(0));
				product.setCategoryName(cursor.getString(1));
				
				data.add(product);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	public ArrayList<Exhibitors> getCategoryWiseSearchResult(String categoryId){
		ArrayList<Exhibitors> list=new ArrayList<Exhibitors>();
		Cursor cursor=db.rawQuery(Query.SEARCH_EXHIBITOR_CATEGORY_WISE,new String[]{categoryId});
		
		Log.i("EFAIR","Number Of result "+cursor.getCount());
		while(cursor.moveToNext()){
			String exhibitorName1;
			String type=cursor.getString(4);
			if(type.equalsIgnoreCase(Integer.toString(Map.hall))){
				exhibitorName1=cursor.getString(1)+"~~"+"Hall No."+cursor.getString(2)+", Stand No."+cursor.getString(3);
			}else{
				exhibitorName1=cursor.getString(1)+"~~"+"Mart- "+cursor.getString(2)+", Stand No."+cursor.getString(3);
			}
			int exhibitorId=cursor.getInt(0);
			//create an object of Users class
            Exhibitors exhib=new Exhibitors();
			exhib.setExhibitorName(exhibitorName1);
			exhib.setExhibitorId(exhibitorId);
			//add user object to list
			list.add(exhib);	
		}
		cursor.close();
		
		
		return list;
		
	}
	
	public ArrayList<AdService> getAdsURL(){
		ArrayList<AdService> adList = new ArrayList<AdService>();
		Cursor cursor = db.rawQuery(Query.GET_ALL_AD_URL, null);
				try{
					if(cursor!=null){
						while(cursor.moveToNext()){
							AdService adSer = new AdService();
							String URL = new String();
							adSer.setUrl(cursor.getString(cursor.getColumnIndex("url")));
							adSer.setHashCode(cursor.getString(cursor.getColumnIndex("hashCode")));
							adList.add(adSer);
						}
					}
					cursor.close();
					
				}catch(Exception e){
					
				}
				return adList;
	}
	
	public ArrayList<AdService> getAdURLplacementIdWise(String placementId){
		ArrayList<AdService> list = new ArrayList<AdService>();
		Cursor cursor	= db.rawQuery(Query.GET_HASHCODE_AND_ADURL_PLACEMENT_ID_WISE, new String[]{""+placementId});
		
		try{
			if(cursor!=null){
				while(cursor.moveToNext()){
					AdService ad = new AdService();
					ad.setHashCode(cursor.getString(0));
					ad.setUrl(cursor.getString(1));
					list.add(ad);
				}
			}
			cursor.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	public enum Tables {

	    products,
	    exhibitors,
	    language,
	    exhibitorLang,
	    venuemap,
	    exhibitorLocation,
	    event,
	    eventInfo,
	    eventLocation,
	    exhibitorproducts,
	    productPhotos,
	    company,
	    resource,
	    file_setting,
	    ExhibitorEntryExit,
	    FacilityInformation,
	    facilityEntryExit
	  }


	public void submitExhibitorEntryExit(
			ArrayList<ExhibitorEntryExit> products) {
		
		try{
			int length=products.size();
			int count=0;
			long startTransTime = System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_EXHIBITOR_ENTRY_EXIT);
			for(int i=0;i<length;i++){
				ExhibitorEntryExit product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("ExhibitorEntryExit", "id=?", new String[] {""+product.getId()});
					continue;
				}
				stmt.bindString(1, ""+product.getId());	
				stmt.bindString(2, ""+product.getExhibitorLocationId());				
				stmt.bindString(3, product.getxLoc());
				stmt.bindString(4, product.getyLoc());
				stmt.bindString(5, product.getType());
				stmt.bindString(6, product.getCreationDate());
				stmt.bindString(7, product.getUpdatedBy());
				stmt.bindString(8, ""+product.getStatus());
				
				stmt.execute();
				stmt.clearBindings();
				count++;
				//Log.i("DATA","creationDate "+product.getCreationDate());
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR", "Total records in ExhibitorEntryExit table is "+length+", and "+count+" record is submitted successfully...");
			Log.i("EFAIR", "Total execution time for ExhibitorEntryExit table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," ExhibitorEntryExit submit exception:"+e);
		}finally{
			db.endTransaction();
		}
		
		/*try{  //db = helper.getWritableDatabase();
			int length=products.size();
			int count=0;
			for(int i=0;i<length;i++){
				//DownloadingData.initializeData.myProgress((float)i/length);
				ExhibitorEntryExit product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("ExhibitorEntryExit", "exhibitorLocationId=?", new String[] {""+product.getExhibitorLocationId()});
					continue;
				}
				ContentValues row =new ContentValues();
				row.put("exhibitorLocationId",product.getExhibitorLocationId());
				
				row.put("x",product.getxLoc());
				row.put("y",product.getyLoc());
				row.put("type", product.getType());
				row.put("creationDate",product.getCreationDate());
				row.put("updatedBy",product.getUpdatedBy());
				row.put("status", product.getStatus());
				
				if(DownloadingData.isDbUpdate)//if updates are available
				{  
					if(isNewItem("ExhibitorEntryExit",""+product.getExhibitorLocationId(),""))
				    {
					  db.update("ExhibitorEntryExit", row, "exhibitorLocationId=?", new String[] {""+product.getExhibitorLocationId()});
					  Log.i("TABLE_DATA", "New data in ExhibitorEntryExit is updated");
				    }else{
				    	db.insert("ExhibitorEntryExit", null, row);
				    	 Log.i("TABLE_DATA", "New data in ExhibitorEntryExit is added");
				    }	
				}else{
					db.insert("ExhibitorEntryExit", null, row);
				}
				
				count++;
			}
			Log.i("TABLE_DATA", ""+count+" file_setting record is submitted successfully...");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," product submit exception:"+e);
		}*/
		
	}

	public void submitFacilityInfo(
			ArrayList<FacilityInformation> products) {
		
		try{
			int length=products.size();
			int count=0;
			long startTransTime = System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_FACILITY_INFORMATION);
			for(int i=0;i<length;i++){
				FacilityInformation product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("FacilityInformation", "facilityInfoId=?", new String[] {""+product.getFacilityId()});
					continue;
				}
				stmt.bindString(1, ""+product.getFacilityId());				
				stmt.bindString(2, product.getxLoc());
				stmt.bindString(3, product.getyLoc());
				stmt.bindString(4, ""+product.getLocationId());
				stmt.bindString(5, product.getLocationName());
				stmt.bindString(6, product.getType());
				stmt.bindString(7, product.getCreationDate());
				stmt.bindString(8, product.getUpdatedBy());
				stmt.bindString(9, ""+product.getStatus());
				
				stmt.execute();
				stmt.clearBindings();
				count++;
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR", "Total records in FacilityInformation table is "+length+", and "+count+" record is submitted successfully...");
			Log.i("EFAIR", "Total execution time for FacilityInformation table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("TABLE_DATA"," FacilityInformation submit exception:"+e);
		}finally{
			db.endTransaction();
		}
		
	}
		
		/*try{  //db = helper.getWritableDatabase();
			int length=products.size();
			int count=0;
			for(int i=0;i<length;i++){
				//DownloadingData.initializeData.myProgress((float)i/length);
				FacilityInformation product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("FacilityInformation", "facilityInfoId=?", new String[] {""+product.getFacilityId()});
					continue;
				}
				ContentValues row =new ContentValues();
				row.put("facilityInfoId",product.getFacilityId());
				
				row.put("x",product.getxLoc());
				row.put("y",product.getyLoc());
				row.put("locationId",product.getLocationId());
				row.put("locationName",product.getLocationName());
				row.put("type", product.getType());
				row.put("creationDate",product.getCreationDate());
				row.put("updatedBy",product.getUpdatedBy());
				row.put("status", product.getStatus());
				
				if(DownloadingData.isDbUpdate)//if updates are available
				{  
					if(isNewItem("FacilityInformation",""+product.getFacilityId(),""))
				    {
					  db.update("FacilityInformation", row, "facilityInfoId=?", new String[] {""+product.getFacilityId()});
					  Log.i("TABLE_DATA", "New data in FacilityInformation is updated");
				    }else{
				    	db.insert("FacilityInformation", null, row);
				    	 Log.i("TABLE_DATA", "New data in FacilityInformation is added");
				    }	
				}else{
					db.insert("FacilityInformation", null, row);
				}
				
				count++;
			}
			Log.i("TABLE_DATA", ""+count+" file_setting record is submitted successfully...");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("TABLE_DATA"," product submit exception:"+e);
		}*/
		
	

	public void submitfacilityEntryExit(
			ArrayList<FacilityEntryExit> products) {
		
		try{
			int length=products.size();
			int count=0;
			long startTransTime = System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_FACILITY_ENTRY_EXIT);
			for(int i=0;i<length;i++){
				FacilityEntryExit product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("facilityEntryExit", "id=?", new String[] {""+product.getId()});
					continue;
				}
				stmt.bindString(1, ""+product.getId());
				stmt.bindString(2, ""+product.getFacilityInfoId());
				stmt.bindString(3, product.getxLoc());
				stmt.bindString(4, product.getyLoc());
				stmt.bindString(5, product.getCreationDate());
				stmt.bindString(6, product.getUpdatedBy());
				stmt.bindString(7, ""+product.getStatus());
				
				stmt.execute();
				stmt.clearBindings();
				count++;
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR", "Total records in facilityEntryExit table is "+length+", and "+count+" record is submitted successfully...");
			Log.i("EFAIR", "Total execution time for facilityEntryExit table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," facilityEntryExit submit exception:"+e);
		}finally{
			db.endTransaction();
		}
		
		/*try{  //db = helper.getWritableDatabase();
			int length=products.size();
			int count=0;
			for(int i=0;i<length;i++){
				//DownloadingData.initializeData.myProgress((float)i/length);
				FacilityEntryExit product=products.get(i);
				if(product.getStatus()==STATUS_ZERO){
					db.delete("facilityEntryExit", "id=?", new String[] {""+product.getId()});
					continue;
				}
				ContentValues row =new ContentValues();
				
				row.put("id",product.getId());
				row.put("facilityInfoId",product.getFacilityInfoId());
				row.put("x",product.getxLoc());
				row.put("y",product.getyLoc());
				row.put("creationDate",product.getCreationDate());
				row.put("updatedBy",product.getUpdatedBy());
				row.put("status", product.getStatus());
				
				if(DownloadingData.isDbUpdate)//if updates are available
				{  
					if(isNewItem("facilityEntryExit",""+product.getId(),""))
				    {
					  db.update("facilityEntryExit", row, "id=?", new String[] {""+product.getId()});
					  Log.i("UPDATES", "New file_setting is updated");
				    }else{
				    	db.insert("facilityEntryExit", null, row);
				    	 Log.i("UPDATES", "New file_setting is added");
				    }	
				}else{
					db.insert("facilityEntryExit", null, row);
				}
				
				count++;
			}
			Log.i("EFAIR", ""+count+" file_setting record is submitted successfully...");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," product submit exception:"+e);
		}*/
		
	}
	
	/**submitting static data **/
	public void submitLinks(ArrayList<Links> links){
		
		try{
			int count=0;
			long startTransTime = System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt = db.compileStatement(Query.INSERT_INTO_LINKS);
			for(int i=0;i<links.size();i++){
				if(links.get(i).getStatus()==STATUS_ZERO){
					try{
						db.delete("links", "linkId=?", new String[]{""+links.get(i).getLinkId()});
					}catch(Exception e){ }
					continue;
				}
				stmt.bindString(1, ""+links.get(i).getLinkId());
				stmt.bindString(2, links.get(i).getLinkUrl());
				stmt.bindString(3, links.get(i).getLinkName());
				stmt.bindString(4, ""+links.get(i).getStatus());
				stmt.bindString(5, links.get(i).getCreationDate());
				stmt.bindString(6, links.get(i).getUpdatedBy());
				
				stmt.execute();
				stmt.clearBindings();
				count++;				
			}
			db.setTransactionSuccessful();
			Log.i("EFAIR", "Total records in links table is "+links.size()+", and "+count+" record is submitted successfully...");
			Log.i("EFAIR", "Total execution time for links table is ["+(System.currentTimeMillis()-startTransTime)+"] ms");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR"," links submit exception:"+e);
		}finally{
			db.endTransaction();
		}
		
		/*try{
			int count=0;
			for(int i=0;i<links.size();i++){
				DownloadingData.initializeData.myProgress((float)i/links.size());
				
				if(links.get(i).getStatus()==STATUS_ZERO){
					try{
						db.delete("links", "linkId=?", new String[]{""+links.get(i).getLinkId()});
					}catch(Exception e){
						
					}
					continue;
				}
				
				ContentValues row = new ContentValues();
				row.put("linkId", links.get(i).getLinkId());
				row.put("linkUrl", links.get(i).getLinkUrl());
				row.put("linkName", links.get(i).getLinkName());
				row.put("status", links.get(i).getStatus());
				row.put("creationDate", links.get(i).getCreationDate());
				row.put("updatedBy", links.get(i).getUpdatedBy());
		
				db.insertWithOnConflict("links", null, row, SQLiteDatabase.CONFLICT_REPLACE);
				
				count++;				
			}
			 Log.i("EFAIR", ""+count+" link record is submitted successfully...");
		}catch(Exception e){
			 Log.i("EFAIR", "Exception when insertting record into links table.");
			e.printStackTrace();
		}*/
	}
	
	public ArrayList<FacilityInformation> getEntryExitBylocationId(String locationId,String type,String locationName){
		ArrayList<FacilityInformation> data=new ArrayList<FacilityInformation>();
		Cursor cursor=db.rawQuery(Query.SELECT_ENTRY_EXIT_BY_LOCATIONID_AND_NAME,new String[]{locationId,"%"+type+"%","%"+locationName+"%"});
		try{
			Log.i("EFAIR","getEntryExitBylocationId "+cursor.getCount()+" location id "+locationId+" Location name : "+locationName);
			while(cursor.moveToNext()){
				FacilityInformation info=new FacilityInformation();
				info.setFacilityId(cursor.getInt(0));
				info.setLocationId(Integer.parseInt(cursor.getString(1)));
				info.setLocationName(cursor.getString(2));
				info.setxLoc(cursor.getString(3));
				info.setyLoc(cursor.getString(4));
				data.add(info);
			}
			cursor.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return data;
	}
	public ArrayList<FacilityInformation> getEntryExitBylocationId(String locationId,String type){
		ArrayList<FacilityInformation> data=new ArrayList<FacilityInformation>();
		Cursor cursor=db.rawQuery(Query.SELECT_ENTRY_EXIT_BY_LOCATIONID,new String[]{locationId,type});
		try{
			if(cursor!=null){
				Log.i("EFAIR","getEntryExitBylocationId "+cursor.getCount()+" location id "+locationId);
				cursor.moveToFirst();
				if(type.equalsIgnoreCase("2")){
					cursor.moveToNext();
					cursor.moveToNext();
				}
				FacilityInformation info=new FacilityInformation();
				info.setFacilityId(cursor.getInt(0));
				info.setLocationId(Integer.parseInt(cursor.getString(1)));
				info.setLocationName(cursor.getString(2));
				info.setxLoc(cursor.getString(3));
				info.setyLoc(cursor.getString(4));
				data.add(info);
			}
			cursor.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return data;
	}
	
	public FacilityInformation getFacilityInfronationByLocationName(int locationId, String name, String type){
		FacilityInformation face=new FacilityInformation();
		Cursor cursor = db.rawQuery(Query.GET_FACILITY_BY_LOCATION_NAME_ND_TYPE, new String[]{""+locationId,name, type});
		if(cursor!=null){
			cursor.moveToFirst();
			face.setFacilityId(cursor.getInt(cursor.getColumnIndex("facilityInfoId")));
			face.setLocationId(cursor.getInt(cursor.getColumnIndex("locationId")));
			face.setLocationName(cursor.getString(cursor.getColumnIndex("locationName")));
			face.setxLoc(cursor.getString(cursor.getColumnIndex("x")));
			face.setyLoc(cursor.getString(cursor.getColumnIndex("y")));
		}
		return face;
	}
	
	public String getFileNameOfVenueMap(int type){
		String fileName="";
		Cursor cursor = db.rawQuery(Query.GET_FILE_NAME_FROM_VENU_MAP, new String[]{""+type});
		if(cursor.getCount()>0){
			cursor.moveToFirst();
			fileName=cursor.getString(0);
		}
		return fileName;
	}
	
	public boolean isExhibitorLocationExist(String exhibitorId,String stallNo){
		
		//Cursor cursor=db.query("exhibitorLocation",null,"exhibitorId=? and standNo=? and xLocation !='0'", new String[]{exhibitorId,stallNo},null,null,null);
		Cursor cursor=db.rawQuery(Query.IS_EXHIBITOR_LOCATION_WITH_ENTRYEXIT_EXIST,new String[]{exhibitorId,stallNo});
		if(cursor!=null){
			if(cursor.getCount()>0){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Favourite> getAllFavourites() {
		ArrayList<Favourite> list = new ArrayList<Favourite>();
		Cursor cursor=db.query("favourite", null, null, null, null, null, null, null);
		if(cursor!=null){
			while (cursor.moveToNext()) {
				Favourite fav = new Favourite();
				fav.setId(cursor.getInt(cursor.getColumnIndex("id")));
				fav.setExhibitorId(cursor.getInt(cursor.getColumnIndex("exhibitorId")));
				fav.setVisitorId(cursor.getInt(cursor.getColumnIndex("visitorId")));
				fav.setUpdatedBy(cursor.getString(cursor.getColumnIndex("updatedBy")));
				fav.setCreationDate(cursor.getString(cursor.getColumnIndex("creationDate")));
				list.add(fav);
			}
		}		
		return list;
	}
	
	public ArrayList<Appointment> getAllAppointment()
	{
		ArrayList<Appointment> list=new ArrayList<Appointment>();
		Log.i("appointment","getAllAppointment()");
		Cursor cursor=db.query("appointment", null, null, null, null, null, null, null);
		if(cursor!=null){
			while (cursor.moveToNext()) {
				Appointment app = new Appointment();
				
				app.setVisitorId(cursor.getInt(cursor.getColumnIndex("visitorId")));			
				app.setExhibitorId(cursor.getInt(cursor.getColumnIndex("exhibitorId")));				
				app.setDate(cursor.getString(cursor.getColumnIndex("date")));				
				app.setStartTime(cursor.getString(cursor.getColumnIndex("startTime")));				
				app.setEndTime(cursor.getString(cursor.getColumnIndex("endTime")));				
				app.setStatus(cursor.getInt(cursor.getColumnIndex("status")));				
				app.setUpdatedBy(cursor.getString(cursor.getColumnIndex("updatedBy")));				
				app.setCreationDate(cursor.getString(cursor.getColumnIndex("creationDate")));
				
				list.add(app);
				
				Log.i("appointment",app.visitorId+" "+app.exhibitorId+" "+app.date+" "+
						app.startTime+" "+ app.endTime+" "+app.status+" "+app.creationDate+" "+
						app.updatedBy+" ");
				
			}
		}		
		return list;
	}
	

	public ArrayList<PhotoShoot> getAllPhotoShoots() {
		ArrayList<PhotoShoot> list = new ArrayList<PhotoShoot>();
		Cursor cursor=db.query("photoShoot", null, null, null, null, null, null, null);
		if(cursor!=null){
			while (cursor.moveToNext()) {
				PhotoShoot photo = new PhotoShoot();
				photo.setId(cursor.getInt(cursor.getColumnIndex("id")));
				photo.setPhotopath(cursor.getString(cursor.getColumnIndex("photopath")));
				photo.setExhibitorId(cursor.getInt(cursor.getColumnIndex("exhibitorId")));
				photo.setVisitorId(cursor.getInt(cursor.getColumnIndex("visitorId")));
				photo.setProductId(cursor.getInt(cursor.getColumnIndex("productId")));
				photo.setNotes(cursor.getString(cursor.getColumnIndex("notes")));
				photo.setUpdatedBy(cursor.getString(cursor.getColumnIndex("updatedby")));
				photo.setCreationDate(cursor.getString(cursor.getColumnIndex("creationDate")));
				list.add(photo);
			}
		}		
		return list;
	}
	
	public ArrayList<VisitorQuestionnaire> getAllQuestionnaires(){
		ArrayList<VisitorQuestionnaire> list = new ArrayList<VisitorQuestionnaire>();
		Cursor cursor = db.rawQuery(Query.SELECT_ALL_FROM_VISITOR_QUESTIONNAIRE, null);
		if(cursor!=null){
			while(cursor.moveToNext()){
				VisitorQuestionnaire que = new VisitorQuestionnaire();
				que.setQuestionId(cursor.getInt(cursor.getColumnIndex("questionId")));
				que.setOrderId(cursor.getInt(cursor.getColumnIndex("orderId")));
				que.setQuestionType(cursor.getString(cursor.getColumnIndex("questionType")));
				que.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
				que.setUserType(cursor.getString(cursor.getColumnIndex("userType")));
				list.add(que);
			}
		}
		return list;
	}
	
	public ArrayList<VisitorQueOption> getAllQueOptionQuestionIdWise(int questionId){
		ArrayList<VisitorQueOption> list = new ArrayList<VisitorQueOption>();
		Cursor cursor = db.rawQuery(Query.SELECT_QUE_OPTION_QUESTION_ID_WISE, new String[]{""+questionId});
		if(cursor!=null){
			while(cursor.moveToNext()){
				VisitorQueOption option = new VisitorQueOption();
				option.setOptionId(cursor.getInt(cursor.getColumnIndex("optionId")));
				option.setOrderId(cursor.getInt(cursor.getColumnIndex("orderId")));
				option.setQuestionId(cursor.getInt(cursor.getColumnIndex("questionId")));
				option.setOption(cursor.getString(cursor.getColumnIndex("option")));
				list.add(option);
			}
		}
		return list;
	}
	
	public String getLinkURLByLinkName(String linkName){
		String linkUrl="";
		Cursor cursor = db.rawQuery(Query.SELECT_LINK_URL_FROM_LINK_BY_LINK_NAME, new String[]{linkName});
		try{
			if(cursor!=null){
				cursor.moveToNext();
				linkUrl=cursor.getString(0);
				Log.i("EFAIR", "linkUrl"+linkUrl);
			}
		}catch(Exception e){e.printStackTrace();}
		return linkUrl;
	}
	
	public void submitBookMark(ArrayList<Favourite> myFavorites){
		for(int i=0;i<myFavorites.size();i++){
			try{
				ContentValues row=new ContentValues();				
				row.put("id",myFavorites.get(i).getExhibitorId());
				row.put("exhibitorId",myFavorites.get(i).getExhibitorId());
				row.put("creationDate","2015-03-11 00:00:00");
				row.put("updatedBy","Vijay Pal");
				
				db.insert("favourite", null,row);
				Log.i("EFAIR", "MyFavorites record is submitted successfully...");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void insertPhotoShoot(ArrayList<PhotoShoot> photo,boolean flag){
		for(int i=0;i<photo.size();i++){
			try{
			    ContentValues row=new ContentValues();		
				row.put("photopath",photo.get(i).getPhotopath());
				row.put("exhibitorId",photo.get(i).getExhibitorId());
				row.put("visitorId",photo.get(i).getVisitorId());
				row.put("productId",photo.get(i).getProductId());
				row.put("notes",photo.get(i).getNotes());
				if(flag){
				    db.insert("photoShoot",null,row);
				    Log.i("EXPO","Data inserted in photoshoot");
				}else{
					//this is used to update the notes
					db.update("photoShoot", row,"photopath=?",new String[]{photo.get(i).getPhotopath()});
					Log.i("EXPO","Data updated in photoshoot");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void insertAppointment(ArrayList<Appointment> appointment)
	{
		try{
			int length=appointment.size();
			int count=0;
			long startTime=System.currentTimeMillis();
			db.beginTransaction();
			SQLiteStatement stmt=db.compileStatement(Query.INSERT_INTO_APPONITMENT);
			for(int i=0;i<appointment.size();i++)
			{
			
				Appointment appointment2=appointment.get(i);
				stmt.bindString(1, ""+appointment2.getVisitorId());
				stmt.bindString(2, ""+appointment2.getEhxibitorId());
				stmt.bindString(3,  appointment2.getDate());
				stmt.bindString(4, appointment2.getStartTime());
				stmt.bindString(5, appointment2.getEndTime());
				stmt.bindString(6, ""+appointment2.getStatus());
				stmt.bindString(7, appointment2.getCreationDate());
				stmt.bindString(8, appointment2.getUpdatedBy());
				stmt.execute();
				stmt.clearBindings();
				count++;
			}			
			db.endTransaction();
			Log.i("EFAIR", "Total records in links table is "+length+", and "+count+" record is submitted successfully...");
			Log.i("EFAIR", "Total execution time for links table is ["+(System.currentTimeMillis()-startTime)+"] ms");
				/*	ContentValues row=new ContentValues();
					row.put("visitorId", appointment.get(i).getVisitorId());
					row.put("exhibitorId", appointment.get(i).getEhxibitorId());
					row.put("date", appointment.get(i).getDate());
					row.put("startTime", appointment.get(i).getStartTime());
					row.put("endTime", appointment.get(i).getEndTime());
					row.put("status", appointment.get(i).getStatus());
					row.put("creationDate", appointment.get(i).getCreationDate());
					row.put("updatedBy", appointment.get(i).getUpdatedBy());
					
					
					db.insert("appointment",null,row);
					Log.i("EXPO","Data inserted in appointment");*/
				
		}catch(Exception e)
				{
					e.printStackTrace();
				}
			
	}
	
	public ArrayList<com.assusoft.eFairEmall.entities.MartFacilities> mapFacilites(){
		ArrayList<MartFacilities> facility_list = new ArrayList<MartFacilities>();
		Cursor cursor = db.rawQuery(Query.MART_FACILITIES, null);
		if(cursor!=null){
			while (cursor.moveToNext()) {
				MartFacilities fas = new MartFacilities();
				Log.i("NUM","facilityId  : "+cursor.getInt(cursor.getColumnIndex("facilityId"))+" facility name: "+cursor.getString(cursor.getColumnIndex("facilityName")));
				fas.setFacilityId(cursor.getInt(cursor.getColumnIndex("facilityId")));
				fas.setFacilityName(cursor.getString(cursor.getColumnIndex("facilityName")));
				fas.setImgName(cursor.getString(cursor.getColumnIndex("imgName")));
				facility_list.add(fas);
			}
		}
		
		return facility_list;
	}
	
	public ArrayList<FacilityInformation> mapFacilitesDetail(int facility_id){
		ArrayList<FacilityInformation> facility_list = new ArrayList<FacilityInformation>();
		Cursor cursor = db.rawQuery(Query.MART_FACILITIE_DETAILS,new String[]{""+facility_id});
		if(cursor!=null){
			while (cursor.moveToNext()) {
				FacilityInformation fas = new FacilityInformation();
				fas.setType(cursor.getString(cursor.getColumnIndex("type")));
				fas.setxLoc(cursor.getString(cursor.getColumnIndex("x")));
				fas.setyLoc(cursor.getString(cursor.getColumnIndex("y")));
				facility_list.add(fas);
			}
		}
		
		return facility_list;
	}
	
	public void updateDownloads(int locationId){
		try{
			ContentValues raw = new ContentValues();
			raw.put("isUpdated", 0);
			db.update("venuemap", raw, "locationId=?", new String[]{String.valueOf(locationId)});
		}catch(Exception e){e.printStackTrace();}
	}
	
	public ArrayList<VenueMap> getAllUpdatingVenuemap(int isUpdated){
		ArrayList<VenueMap> maps = new ArrayList<VenueMap>();
		Cursor cursor = db.rawQuery(Query.GET_ALL_VENUEMAP_UDATES, new String[]{""+isUpdated});
		if(cursor!=null){
			while (cursor.moveToNext()) {
				VenueMap map = new VenueMap();
				map.setLocationId(cursor.getInt(cursor.getColumnIndex("locationId")));
				map.setFilePath(cursor.getString(cursor.getColumnIndex("filePath")));
				map.setMapName(cursor.getString(cursor.getColumnIndex("mapName")));
				map.setIsUpdated(cursor.getInt(cursor.getColumnIndex("isUpdated")));
				maps.add(map);
			}
		}
		return maps;
	}
}
