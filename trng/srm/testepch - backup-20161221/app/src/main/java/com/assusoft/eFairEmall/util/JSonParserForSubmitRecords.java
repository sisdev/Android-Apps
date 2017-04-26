/**
 * @author Akshay kumar
 * This class is used to parse the json record of updated data
 */

package com.assusoft.eFairEmall.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.assusoft.eFairEmall.entities.FileSettings;
import com.assusoft.eFairEmall.entities.Language;
import com.assusoft.eFairEmall.entities.Links;
import com.assusoft.eFairEmall.entities.MartFacilities;
import com.assusoft.eFairEmall.entities.ProductPhoto;
import com.assusoft.eFairEmall.entities.Products;
import com.assusoft.eFairEmall.entities.Resource;
import com.assusoft.eFairEmall.entities.Roles;
import com.assusoft.eFairEmall.entities.Users;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.assusoft.eFairEmall.entities.VisitorQueOption;
import com.assusoft.eFairEmall.entities.VisitorQuestionnaire;
import com.assusoft.eFairEmall.entities.Visitors;

public class JSonParserForSubmitRecords {
	private MySharedPreferences pref;
	private String databaseName;
	private static final String NULL="Null";
	private static final String NOT_AVAILABLE="Not Available";
	String INTEGER_NULL="0";
	 Context context;
	private ArrayList<Products> products=new ArrayList<Products>();
	private ArrayList<Roles> roles=new ArrayList<Roles>();
	private ArrayList<Users> users=new ArrayList<Users>();
	private ArrayList<Exhibitors>exhibitors=new ArrayList<Exhibitors>();
	private ArrayList<Visitors> visitors=new ArrayList<Visitors>();
	private ArrayList<ExhibitorProducts> exhibitorProducts=new ArrayList<ExhibitorProducts>();
	private ArrayList<Admin> admins=new ArrayList<Admin>();
	private ArrayList<Appointment> appointments=new ArrayList<Appointment>();
	private ArrayList<ProductPhoto> productPhotoes=new ArrayList<ProductPhoto>();
	private ArrayList<VenueMap> venueMaps=new ArrayList<VenueMap>();
	private ArrayList<ExhibitorLocation> exhibitorLocations=new ArrayList<ExhibitorLocation>();
	private ArrayList<Language> language=new ArrayList<Language>();
	private ArrayList<ExhibitorLanguage> exhibitorLanguage=new ArrayList<ExhibitorLanguage>();
	private ArrayList<Event> event=new ArrayList<Event>();
	private ArrayList<EventInfo> eventInfo=new ArrayList<EventInfo>();
	private ArrayList<EventLocation> eventLocation=new ArrayList<EventLocation>();
	private ArrayList<Company> company=new ArrayList<Company>();
	private ArrayList<Resource> resource=new ArrayList<Resource>();
	private ArrayList<FileSettings> fileSetting=new ArrayList<FileSettings>();
	private ArrayList<ExhibitorEntryExit> exhibitorEntryExit =new ArrayList<ExhibitorEntryExit>();
	private ArrayList<FacilityInformation> facilityInfo=new ArrayList<FacilityInformation>();
	private ArrayList<FacilityEntryExit> facilityEntryExit=new ArrayList<FacilityEntryExit>();
	private ArrayList<ExhibitorCategory> exhibitorCategories = new  ArrayList<ExhibitorCategory>();
	private ArrayList<Links> links = new ArrayList<Links>();
	private ArrayList<VisitorQuestionnaire> questionnaires = new ArrayList<VisitorQuestionnaire>();
	private ArrayList<VisitorQueOption> options = new ArrayList<VisitorQueOption>();
	private ArrayList<MartFacilities> martFacilities =new ArrayList<MartFacilities>(); 
	
	private ArrayList<AdService> adServices = new ArrayList<AdService>();
	
	public JSonParserForSubmitRecords(Context context){
		super();
		this.context=context;
		this.pref	= new MySharedPreferences(context);
	}
	public void JSonParser(JSONObject submitData){
		//databaseName=(String) updatedData.get("databaseName");
		try {
			JSONArray jProducts=submitData.getJSONArray("products");
			parseProducts(jProducts);
		    }catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("EFAIR", "jsonparser Exception in product:"+e) ;
			}
			/*JSONArray jRoles=updatedData.getJSONArray("roles");
			JSONArray jUsers=updatedData.getJSONArray("users");*/
	   try {
		    JSONArray jExhibitors=submitData.getJSONArray("exhibitors");
		   // Log.i("EXPO", "jExhibitors:"+jExhibitors.toString());
			parseExhibitors(jExhibitors);
	       }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in Exhibitors:"+e) ;
		   }
	   try {
		   JSONArray jLanguage=submitData.getJSONArray("language");
		   parseLanguage(jLanguage);
	       }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in languag:"+e) ;
		   }
	   try {
		   JSONArray jVenueMap=submitData.getJSONArray("venuemap");
		   parseVenueMap(jVenueMap);
	       }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in venuemap:"+e) ;
		   }
	   try {
		   JSONArray jExhibitorLocation=submitData.getJSONArray("exhibitorLocation");
		   parseExhibitorLocation(jExhibitorLocation);
	       }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in exhibitorLocation:"+e) ;
		   }
	   try {
		   JSONArray jEvent=submitData.getJSONArray("event");
		   parseEvent(jEvent);
	       }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in event:"+e) ;
		   }
	   try {
		   JSONArray jEventInfo=submitData.getJSONArray("eventInfo");
		   parseEventInfo(jEventInfo);
	       }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in eventInfo:"+e) ;
		   }
	   try {
		   JSONArray jEventLocation=submitData.getJSONArray("eventLocation");
		   parseEventLocation(jEventLocation);
	       }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in eventLocation:"+e) ;
		   }
	   try {
		   /*exhibitorproducts replaced by exhibitorProducts*/
		   JSONArray jExhibitorProduct=submitData.getJSONArray("exhibitorProducts");
		   parseExhibitorProducts(jExhibitorProduct);
	       }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in ExhibitorProduct:"+e) ;
		   }
	   try {
		   JSONArray jProductPhoto=submitData.getJSONArray("productPhotos");
		   parseProductPhotoes(jProductPhoto);
	       }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in ProductPhoto:"+e) ;
		   }
	   try {
		   JSONArray jCompant=submitData.getJSONArray("company");
		   parseCompany(jCompant);
	       }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in ProductPhoto:"+e) ;
		   }
	   
	   try{
		   JSONArray jLinks=submitData.getJSONArray("links");
		   parseLinks(jLinks);
		   
	   }catch(JSONException e){
		   e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in links :"+e) ;
	   }
	   
	   try{
		   JSONArray jExhibitorCategory = submitData.getJSONArray("exhibitorCategory");
		   parseExhibitorCategory(jExhibitorCategory);
		   
	   }catch(JSONException e){
		   e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in exhibitorCategory :"+e) ;
	   }
	   
	   try {
			JSONArray jExhibitorLang=submitData.getJSONArray("exhibitorLang");
		/*	JSONArray jVisitors=updatedData.getJSONArray("visitors");
			*/
			/*//uncommemt when data comes for all tables
			  JSONArray jAdmin=updatedData.getJSONArray("admin");
			
			JSONArray jAppointment=updatedData.getJSONArray("appointment");
		    parseAdmins(jAdmin);
		    parseAppointments(jAppointment);
		    
		    
		    */
			
			/*parseRoles(jRoles);
			parseUsers(jUsers);*/
			
			
			parseExhibitorLang(jExhibitorLang);
			/*parseVisitors(jVisitors);
			*/
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception:"+e) ;
		}
	   
	   try{
		   JSONArray resourceData=submitData.getJSONArray("resource");
		   parseResource(resourceData);
		   
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   
	   try{
		   JSONArray fileSettingdata=submitData.getJSONArray("fileSetting");
		   parseFileSetting(fileSettingdata);
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   try{
		   JSONArray exbEntryExitData=submitData.getJSONArray("exhibitorentryexit");
		   parseExhibitorEntryExit(exbEntryExitData);
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   try{
		   JSONArray facilityInfoData=submitData.getJSONArray("facilityInformation");
		   parseFacilityInformation(facilityInfoData);
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   try{
		   JSONArray facilityEntryExit=submitData.getJSONArray("facilityEntryExit");
		   parseFacilityEntryExit(facilityEntryExit);
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   try{
		   JSONArray questionnaire=submitData.getJSONArray("questionnaire");
		   parseQuestionnaire(questionnaire);
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   try{
		   JSONArray que_option=submitData.getJSONArray("questionnaireOption");
		   parseQueOption(que_option);
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   try {
		  String imgUrl=submitData.get("layout_baseurl").toString();
		  Log.i("WEB_DATA", "imgUrl:"+imgUrl) ;
		  saveImageUrl(imgUrl);
	       }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in ProductPhoto:"+e) ;
		   }
	   
	   /****AdServe******/
	   try{
		   JSONArray jAdServe = submitData.getJSONArray("adServe");
		   parseAdServe(jAdServe);
		   
	   }catch(JSONException e){
		   e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in adServe :"+e) ;
	   }
	   
	   /****MartFacilities******/
	   try{
		   JSONArray jMartFacility = submitData.getJSONArray("martFacilities");
		   parseMartFacilities(jMartFacility);
		   
	   }catch(JSONException e){
		   e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in martFacility :"+e) ;
	   }
	   
	   /*****STATIC PAGE BASE URL********/
	   try{
		   String baseUrl	= submitData.getString("static_pageurl").toString();
		   saveStaticPageBaseURL(baseUrl);
	   }catch(JSONException e){
		   e.printStackTrace();
			Log.i("EFAIR", "jsonparser Exception in static_pageurl :"+e) ;
	   }
	}
	
	@SuppressLint("NewApi")
	public void parseProducts(JSONArray productsObject){
		
		try{
			int length=productsObject.length();
			Log.i("EFAIR", "product length:"+length) ;
			for(int i=0;i<length;i++){
				String productId="",productName="",productType="",productBrand="",description="",creationDate="",updatedBy="";
				int status=1;
				//get single record object
				JSONObject singleRecord=(JSONObject) productsObject.get(i);
				
				if(!singleRecord.get("productId").toString().equalsIgnoreCase(NULL))
				{productId=(String) singleRecord.get("productId");}
				productId=(productId.isEmpty() ? NOT_AVAILABLE:productId);
				
				if(!singleRecord.get("productName").toString().equalsIgnoreCase(NULL))
				{productName=(String) singleRecord.get("productName"); }
				productName=(productName.isEmpty() ? NOT_AVAILABLE:productName);
				
				if(!singleRecord.get("productType").toString().equalsIgnoreCase(NULL))
				{productType=(String) singleRecord.get("productType"); }
				productType=(productType.isEmpty() ? NOT_AVAILABLE:productType);
				
				if(!singleRecord.get("productBrand").toString().equalsIgnoreCase(NULL))
				{productBrand=(String) singleRecord.get("productBrand"); }
				productBrand=(productBrand.isEmpty() ? NOT_AVAILABLE:productBrand);
				
				if(!singleRecord.get("description").toString().equalsIgnoreCase(NULL))
				{ description=(String) singleRecord.get("description");}
				description=(description.isEmpty() ? NOT_AVAILABLE:description);
				
				if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
				{creationDate=(String) singleRecord.get("creationDate"); }
				creationDate=(creationDate.isEmpty() ? NOT_AVAILABLE:creationDate);
				
				if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
				{ updatedBy=(String) singleRecord.get("updatedBy"); }
				updatedBy=(updatedBy.isEmpty() ? NOT_AVAILABLE:updatedBy);
				
				try{
					status	= Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
				/*Log.i("EFAIR",productId+","+productName+","+productType+","+productBrand+
						      ","+description+","+creationDate+",updatedBy:"+updatedBy);*/
				Products product=new Products(Integer.parseInt(productId), productName, 
						productType, productBrand, description, creationDate, updatedBy);
				product.setStatus(status);
				products.add(product);
				/*Log.i("EFAIR", "INSERT product data:"+productId+",productName"+productName+productType+
						productBrand+description+creationDate+updatedBy);*/
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR", "INSERT product exception:"+e);
		}
		
	}
	public void parseRoles(JSONArray rolesObject){
		
		try{
			int length=rolesObject.length();
			for(int i=0;i<length;i++){
				int status=1;
				//get single record object
				JSONObject singleRecord=(JSONObject) rolesObject.get(i);
				String roleId=(String) singleRecord.get("roleId");
				String roleName=(String) singleRecord.get("roleName");
				String roleDetails=(String) singleRecord.get("roleDetails");
				
				String creationDate=(String) singleRecord.get("creationDate");
				String updatedBy=(String) singleRecord.get("updatedBy");
				
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
			//	Log.i("UPDATEDREC",roleId+","+roleName+","+roleDetails+","+creationDate+","+updatedBy);
				Roles role=new Roles(roleName, 
						roleDetails, creationDate, updatedBy);
				role.setRoleId(Integer.parseInt(roleId));
				role.setStatus(status);
				roles.add(role);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	public void parseUsers(JSONArray usersObject){
		int status=1;
		try{
			int length=usersObject.length();
			for(int i=0;i<length;i++){
				//get single record object
				JSONObject singleRecord=(JSONObject) usersObject.get(i);
				String userId=(String) singleRecord.get("userId");
				String roleId=(String) singleRecord.get("roleId");
				String logInEmail=(String) singleRecord.get("loginEmail");
				String password=(String) singleRecord.get("password");
				String secretQuestion=(String) singleRecord.get("secretQuestion");
				String secretAnswer=(String) singleRecord.get("secretAnswer");
				//String status=(String) singleRecord.get("status");
				
				String lastLoggedIn=(String) singleRecord.get("lastLoggedIn");
				String creationDate=(String) singleRecord.get("creationDate");
				String updatedBy=(String) singleRecord.get("updatedBy");
				
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
		//		Log.i("UPDATEDREC",userId+","+roleId+","+logInEmail+","+password+","+secretQuestion+","+
			//	secretAnswer+","+status+","+lastLoggedIn+creationDate+","+updatedBy);
				Users user=new Users(Integer.parseInt(roleId), logInEmail, password, 
						secretQuestion, secretAnswer, status,lastLoggedIn, creationDate, 
						updatedBy);
				user.setUserId(Integer.parseInt(userId));
				users.add(user);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@SuppressLint("NewApi")
	public void parseExhibitors(JSONArray exhibitorsObject){
		
		try{ 
			int length=exhibitorsObject.length();
			Log.i("EFAIR", "jExhibitors.length():"+length);
			for(int i=0;i<length;i++){
				String category="",companyId="",updatedBy="",creationDate="",fax="",mobileNo="",contactNo="";
				String email="",contactPerson="",exhibitorId="",pkId="",country="",type="",refNo="";
				int status=1;
				//get single record object
				JSONObject singleRecord=(JSONObject) exhibitorsObject.get(i);
				
				if(!singleRecord.get("pkId").toString().equalsIgnoreCase(NULL))
				{pkId=(String) singleRecord.get("pkId"); }
				pkId=(pkId.isEmpty() ? INTEGER_NULL:pkId);
				
				if(!singleRecord.get("exhibitorId").toString().equalsIgnoreCase(NULL))
				{exhibitorId=(String) singleRecord.get("exhibitorId"); }
				exhibitorId=(exhibitorId.isEmpty() ? INTEGER_NULL:exhibitorId);
				
				if(!singleRecord.get("contactPerson").toString().equalsIgnoreCase(NULL))
				{contactPerson=(String) singleRecord.get("contactPerson"); }
				contactPerson=(contactPerson.isEmpty() ? NOT_AVAILABLE:contactPerson);
				
				if(!singleRecord.get("email").toString().equalsIgnoreCase(NULL))
				{email=(String) singleRecord.get("email"); }
				email=(email.isEmpty() ? NOT_AVAILABLE:email);
				
				if(!singleRecord.get("contactNo").toString().equalsIgnoreCase(NULL))
				{contactNo=(String) singleRecord.get("contactNo"); }
				contactNo=(contactNo.isEmpty() ? NOT_AVAILABLE:contactNo);
				
				if(!singleRecord.get("mobileNo").toString().equalsIgnoreCase(NULL))
				{mobileNo=(String) singleRecord.get("mobileNo"); }
				mobileNo=(mobileNo.isEmpty() ? NOT_AVAILABLE:mobileNo);
				
				if(!singleRecord.get("fax").toString().equalsIgnoreCase(NULL))
				{fax=(String) singleRecord.get("fax");	}	
				fax=(fax.isEmpty() ? NOT_AVAILABLE:fax);
				
				if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
				{creationDate=(String) singleRecord.get("creationDate");}
				creationDate=(creationDate.isEmpty() ? NOT_AVAILABLE:creationDate);
				
				if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
				{updatedBy=(String) singleRecord.get("updatedBy");}
				updatedBy=(updatedBy.isEmpty() ? NOT_AVAILABLE:updatedBy);
				
				
				if(!singleRecord.get("category").toString().equalsIgnoreCase(NULL))
				{category=(String) singleRecord.get("category");}
				category=(category.isEmpty() ? INTEGER_NULL:category);
				
				if(!singleRecord.get("companyId").toString().equalsIgnoreCase(NULL))
				{companyId=(String) singleRecord.get("companyId");}
				companyId=(companyId.isEmpty() ? INTEGER_NULL:companyId);
				try{
					type=singleRecord.getString("type");
				}catch(Exception e){
					e.printStackTrace();
					type="";
				}
				try{
					country=singleRecord.getString("country");
					
				}catch(Exception e){
					e.printStackTrace();
					country="";
				}
				try{
					status=Integer.parseInt(singleRecord.getString("status"));
					
				}catch(Exception e){
					e.printStackTrace();
					status=1;
				}
				try{
					refNo=singleRecord.getString("refNo");
					
				}catch(Exception e){
					e.printStackTrace();
					refNo="";
				}
				//Log.i("EFAIR",pkId+","+exhibitorId+","+contactPerson+","+email+","+contactNo+","+mobileNo+","+fax+","+creationDate+","+updatedBy);
				Exhibitors exhibitor=new Exhibitors(Integer.parseInt(pkId),Integer.parseInt(exhibitorId),contactPerson, 
						        email, contactNo,mobileNo,fax, creationDate, updatedBy,Integer.parseInt(category),Integer.parseInt(companyId));
				exhibitor.setCountry(country);
				exhibitor.setType(type);
				exhibitor.setStatus(status);
				exhibitor.setRefNo(refNo);
				exhibitors.add(exhibitor);
				
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR", "jExhibitors parsing exception:"+e);
		}
		
		
		
		
	}
	
	public void parseVisitors(JSONArray visitorsObject){
		int status=1;
		try{
			int length=visitorsObject.length();
			for(int i=0;i<length;i++){
				//get single record object
				JSONObject singleRecord=(JSONObject) visitorsObject.get(i);
				String pkId=(String) singleRecord.get("pkId");
				String visitorId=(String) singleRecord.get("visitorId");
			    String visitorName=(String) singleRecord.get("visitorName");
			    String visitorPurpose=(String) singleRecord.get("visitorPurpose");
			    String visitorType=(String) singleRecord.get("visitorType");
			    String visitorSex=(String) singleRecord.get("visitorSex");
			    String companyName=(String) singleRecord.get("companyName");
			    String address1=(String) singleRecord.get("address1");
			    String address2=(String) singleRecord.get("address2");
			    String city=(String) singleRecord.get("city");
                String state=(String) singleRecord.get("state");
                String pinCode=(String) singleRecord.get("pinCode");
                String country=(String) singleRecord.get("country");
                String nationality=(String) singleRecord.get("nationality");
                
                String website=(String) singleRecord.get("website");
                String contactNo=(String) singleRecord.get("contactNo");
                String mobileNo=(String) singleRecord.get("mobileNo");
              
				String creationDate=(String) singleRecord.get("creationDate");
				String updatedBy=(String) singleRecord.get("updatedBy");
				
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
			//	Log.i("UPDATEDREC",pkId+","+visitorId+",visitorName"+","+visitorPurpose+","+creationDate+","+updatedBy);
				Visitors visitor=new Visitors(visitorId, visitorName, visitorPurpose, visitorType,
						visitorSex, companyName, address1, address2, city, state, 
						pinCode, country, nationality, website, contactNo, mobileNo, creationDate, updatedBy);
				visitor.setStatus(status);
				visitors.add(visitor);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@SuppressLint("NewApi")
	public void parseExhibitorProducts(JSONArray exhibitorProductObject){
		int status=1;
		
		try{
			int length=exhibitorProductObject.length();
			for(int i=0;i<length;i++){
				String productId="",exhibitorId="",creationDate="",updatedBy="";
				//get single record object
				JSONObject singleRecord=(JSONObject) exhibitorProductObject.get(i);
				try {
					if(!singleRecord.get("productId").toString().equalsIgnoreCase(NULL))
					{ productId=(String) singleRecord.get("productId");	}
					  productId=(productId.isEmpty() ? INTEGER_NULL:productId);
				} catch (Exception e) {
					// TODO: handle exception
					productId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("exhibitorId").toString().equalsIgnoreCase(NULL))
					{ exhibitorId=(String) singleRecord.get("exhibitorId");	}
					 exhibitorId=(exhibitorId.isEmpty() ? INTEGER_NULL:exhibitorId);
				} catch (Exception e) {
					// TODO: handle exception
					exhibitorId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
					{ creationDate=(String) singleRecord.get("creationDate");	}
					  creationDate=(creationDate.isEmpty() ? INTEGER_NULL:creationDate);
				} catch (Exception e) {
					// TODO: handle exception
					creationDate=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
					{ updatedBy=(String) singleRecord.get("updatedBy");	}
					  updatedBy=(updatedBy.isEmpty() ? NOT_AVAILABLE:updatedBy);
				} catch (Exception e) {
					// TODO: handle exception
					updatedBy=NULL;
				}
				try{
					status=Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
			//	Log.i("UPDATEDREC",productId+","+exhibitorId+","+creationDate+","+updatedBy);
				ExhibitorProducts exhibitorProduct=new ExhibitorProducts(Integer.parseInt(productId), 
						Integer.parseInt(exhibitorId), creationDate, updatedBy);
				exhibitorProduct.setStatus(status);
				exhibitorProducts.add(exhibitorProduct);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void parseAdmins(JSONArray adminsObject){
		int status=1;
		try{
			int length=adminsObject.length();
			for(int i=0;i<length;i++){
				//get single record object
				JSONObject singleRecord=(JSONObject) adminsObject.get(i);
				String adminId=(String) singleRecord.get("adminId");
				String adminName=(String) singleRecord.get("adminName");
				String employeeId=(String) singleRecord.get("employeeId");
				String contactNo=(String) singleRecord.get("contactNo");
				
				String creationDate=(String) singleRecord.get("creationDate");
				String updatedBy=(String) singleRecord.get("updatedBy");
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
				//Log.i("UPDATEDREC",adminId+","+adminName+","+employeeId+","+creationDate+","+updatedBy);
				Admin admin=new Admin(adminName, employeeId, contactNo, creationDate, updatedBy);
				admin.setAdminId(Integer.parseInt(adminId));
				admin.setStatus(status);
				admins.add(admin);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	

	public void parseAppointments(JSONArray appointmentsObject){
		int status=1;
		try{
			int length=appointmentsObject.length();
			for(int i=0;i<length;i++){
				//get single record object
				JSONObject singleRecord=(JSONObject) appointmentsObject.get(i);
				String appointmentId=(String) singleRecord.get("appointmentId");
				String visitorId=(String) singleRecord.get("visitorId");
				String exhibitorId=(String) singleRecord.get("exhibitorId");
				String date=(String) singleRecord.get("date");
				String startTime=(String) singleRecord.get("startTime");
				String endTime=(String) singleRecord.get("endTime");
				
				String creationDate=(String) singleRecord.get("creationDate");
				String updatedBy=(String) singleRecord.get("updatedBy");
				
				try{
					status=Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
		//		Log.i("UPDATEDREC",appointmentId+","+visitorId+","+exhibitorId+","+creationDate+","+updatedBy);
				Appointment appointment=new Appointment(Integer.parseInt(visitorId), Integer.parseInt(exhibitorId),
						date, startTime, endTime, status, creationDate, updatedBy,null);
				appointment.setAppointmentId(Integer.parseInt(appointmentId));
				appointments.add(appointment);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	

	@SuppressLint("NewApi")
	public void parseProductPhotoes(JSONArray productPhotosObject){
		try{
			int length=productPhotosObject.length();
			for(int i=0;i<length;i++){
				int status=1;
				String productPhotoId="",productId="",exhibitorId="",filePath="",fileName="",creationDate="",updatedBy="";
				
				//get single record object
				JSONObject singleRecord=(JSONObject) productPhotosObject.get(i);
				try {
					if(!singleRecord.get("productPhotoId").toString().equalsIgnoreCase(NULL))
					{ productPhotoId=(String) singleRecord.get("productPhotoId");	}
					  productPhotoId=(productPhotoId.isEmpty() ? INTEGER_NULL:productPhotoId);
				} catch (Exception e) {
					// TODO: handle exception
					productPhotoId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("productId").toString().equalsIgnoreCase(NULL))
					{ productId=(String) singleRecord.get("productId");	}
					  productId=(productId.isEmpty() ? INTEGER_NULL:productId);
				} catch (Exception e) {
					// TODO: handle exception
					productId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("exhibitorId").toString().equalsIgnoreCase(NULL))
					{ exhibitorId=(String) singleRecord.get("exhibitorId");	}
					  exhibitorId=(exhibitorId.isEmpty() ? INTEGER_NULL:exhibitorId);
				} catch (Exception e) {
					// TODO: handle exception
					exhibitorId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("filePath").toString().equalsIgnoreCase(NULL))
					{ filePath=(String) singleRecord.get("filePath");	}
					  filePath=(filePath.isEmpty() ? NOT_AVAILABLE:filePath);
				} catch (Exception e) {
					// TODO: handle exception
					filePath=NULL;
				}
				try {
					if(!singleRecord.get("fileName").toString().equalsIgnoreCase(NULL))
					{ fileName=(String) singleRecord.get("fileName");	}
					  fileName=(fileName.isEmpty() ? NOT_AVAILABLE:fileName);
				} catch (Exception e) {
					// TODO: handle exception
					fileName=NULL;
				}
				try {
					if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
					{ creationDate=(String) singleRecord.get("creationDate");	}
					  creationDate=(creationDate.isEmpty() ? INTEGER_NULL:creationDate);
				} catch (Exception e) {
					// TODO: handle exception
					creationDate=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
					{ updatedBy=(String) singleRecord.get("updatedBy");	}
					  updatedBy=(updatedBy.isEmpty() ? NOT_AVAILABLE:updatedBy);
				} catch (Exception e) {
					// TODO: handle exception
					updatedBy=NULL;
				}
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
		//		Log.i("UPDATEDREC",productPhotoId+","+productId+","+exhibitorId+","+creationDate+","+updatedBy);
				ProductPhoto productPhoto=new ProductPhoto(Integer.parseInt(productId), Integer.parseInt(exhibitorId),
						filePath, fileName, creationDate, updatedBy);
				productPhoto.setProductPhotoId(Integer.parseInt(productPhotoId));
				productPhoto.setStatus(status);
				productPhotoes.add(productPhoto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	@SuppressLint("NewApi")
	public void parseVenueMap(JSONArray venueMapsObject){
		
		
		try{
			int length=venueMapsObject.length();
			for(int i=0;i<length;i++){
				String locationId="",mapName="",type="",floorName="",hallNo="",filePath="",creationDate="",updatedBy="",productId="";
				int year = 0;int status=1, isUpdated=1;
				//get single record object
				JSONObject singleRecord=(JSONObject)venueMapsObject.get(i);
				try {
					if(!singleRecord.get("locationId").toString().equalsIgnoreCase(NULL))
					{ locationId=(String) singleRecord.get("locationId");	}
					  locationId=(locationId.isEmpty() ? INTEGER_NULL:locationId);
				} catch (Exception e) {
					// TODO: handle exception
					locationId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("mapName").toString().equalsIgnoreCase(NULL))
					{ mapName=(String) singleRecord.get("mapName");	}
					mapName=(mapName.isEmpty() ? NOT_AVAILABLE:mapName);
				} catch (Exception e) {
					// TODO: handle exception
					mapName=NULL;
				}
				try {
					if(!singleRecord.get("type").toString().equalsIgnoreCase(NULL))
					{ type=(String) singleRecord.get("type");	}
					type=(type.isEmpty() ? NOT_AVAILABLE:type);
				} catch (Exception e) {
					// TODO: handle exception
					type=NULL;
				}
				try {
					if(!singleRecord.get("floorName").toString().equalsIgnoreCase(NULL))
					{ floorName=(String) singleRecord.get("floorName");	}
					floorName=(floorName.isEmpty() ? NOT_AVAILABLE:floorName);
				} catch (Exception e) {
					// TODO: handle exception
					floorName=NULL;
				}
				try {
					if(!singleRecord.get("hallNo").toString().equalsIgnoreCase(NULL))
					{ hallNo=(String) singleRecord.get("hallNo");	}
					  hallNo=( hallNo.isEmpty() ? INTEGER_NULL: hallNo);
				} catch (Exception e) {
					// TODO: handle exception
					 hallNo=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("filePath").toString().equalsIgnoreCase(NULL))
					{ 
					  filePath=(String) singleRecord.get("filePath");
					  Log.i("STATUS","filePath:"+filePath); }
					  filePath=( filePath.isEmpty() ? NOT_AVAILABLE: filePath);
				} catch (Exception e) {
					// TODO: handle exception
					filePath=NULL;
					Log.i("STATUS","filePath exception:"+e);
				}
				try {
					if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
					{ creationDate=(String) singleRecord.get("creationDate");	}
					  creationDate=( creationDate.isEmpty() ? NOT_AVAILABLE: creationDate);
				} catch (Exception e) {
					// TODO: handle exception
					creationDate=NULL;
				}
				try {
					if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
					{ updatedBy=(String) singleRecord.get("updatedBy");	}
					  updatedBy=( updatedBy.isEmpty() ? NOT_AVAILABLE: updatedBy);
				} catch (Exception e) {
					// TODO: handle exception
					updatedBy=NULL;
				}
				try {
					if(!singleRecord.get("productId").toString().equalsIgnoreCase(NULL))
					{ productId=(String) singleRecord.get("productId");}
					  productId=(productId.isEmpty() ? INTEGER_NULL:productId);
				} catch (Exception e) {
					// TODO: handle exception
					productId=INTEGER_NULL;
				}
				try {
					
					year=singleRecord.getInt("year");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
				try{
					isUpdated = Integer.valueOf(singleRecord.getString("isUpdated"));
				}catch(JSONException e){}
				
				//Log.i("EFAIR",locationId+","+mapName+","+type+","+creationDate+","+updatedBy);
				VenueMap venueMap=new VenueMap(Integer.parseInt(locationId),mapName, type, floorName, hallNo,
						filePath, creationDate, updatedBy,Integer.parseInt(productId));
				venueMap.setYear(year);
				venueMap.setStatus(status);
				venueMap.setIsUpdated(isUpdated);
				venueMaps.add(venueMap);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	@SuppressLint("NewApi")
	public void parseExhibitorLocation(JSONArray exhibitorLocationsObject){
		try{
			int length=exhibitorLocationsObject.length();
			for(int i=0;i<length;i++){
				int status=1;
				String exhibitorLocationId="",locationId="",exhibitorId="",standNo="",xLocation="",yLocation="",creationDate="",updatedBy="";
				
				//get single record object
				JSONObject singleRecord=(JSONObject)exhibitorLocationsObject.get(i);
				try {
					if(!singleRecord.get("exhibitorLocationId").toString().equalsIgnoreCase(NULL))
					{ exhibitorLocationId=(String) singleRecord.get("exhibitorLocationId");	}
					exhibitorLocationId=(exhibitorLocationId.isEmpty() ? INTEGER_NULL:exhibitorLocationId);
				} catch (Exception e) {
					// TODO: handle exception
					exhibitorLocationId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("locationId").toString().equalsIgnoreCase(NULL))
					{ locationId=(String) singleRecord.get("locationId");	}
					  locationId=(locationId.isEmpty() ? INTEGER_NULL:locationId);
				} catch (Exception e) {
					// TODO: handle exception
					locationId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("exhibitorId").toString().equalsIgnoreCase(NULL))
					{ exhibitorId=(String) singleRecord.get("exhibitorId");	}
					  exhibitorId=(exhibitorId.isEmpty() ? INTEGER_NULL:exhibitorId);
				} catch (Exception e) {
					// TODO: handle exception
					exhibitorId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("standNo").toString().equalsIgnoreCase(NULL))
					{ standNo=(String) singleRecord.get("standNo");	}
					  standNo=(standNo.isEmpty() ? INTEGER_NULL:standNo);
				} catch (Exception e) {
					// TODO: handle exception
					standNo=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("xLocation").toString().equalsIgnoreCase(NULL))
					{ xLocation=(String) singleRecord.get("xLocation");	}
					  xLocation=(xLocation.isEmpty() ? INTEGER_NULL:xLocation);
				} catch (Exception e) {
					// TODO: handle exception
					xLocation=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("yLocation").toString().equalsIgnoreCase(NULL))
					{ yLocation=(String) singleRecord.get("yLocation"); }
					  yLocation=(yLocation.isEmpty() ? INTEGER_NULL:yLocation);
				} catch (Exception e) {
					// TODO: handle exception
					yLocation=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
					{ creationDate=(String) singleRecord.get("creationDate"); }
					  creationDate=(creationDate.isEmpty() ? INTEGER_NULL:creationDate);
				} catch (Exception e) {
					// TODO: handle exception
					creationDate=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
					{ updatedBy=(String) singleRecord.get("updatedBy"); }
					  updatedBy=(updatedBy.isEmpty() ? NOT_AVAILABLE:updatedBy);
				} catch (Exception e) {
					// TODO: handle exception
					updatedBy=NULL;
				}
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
				//Log.i("UPDATEDREC",exhibitorLocationId+","+locationId+","+exhibitorId+","+creationDate+","+updatedBy);
				ExhibitorLocation exhibitorLocation=new ExhibitorLocation(Integer.parseInt(locationId), Integer.parseInt(exhibitorId), 
						               standNo, xLocation, yLocation, creationDate, updatedBy);
				exhibitorLocation.setExhibitorLocationId(Integer.parseInt(exhibitorLocationId));
				exhibitorLocation.setStatus(status);
				exhibitorLocations.add(exhibitorLocation);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	@SuppressLint("NewApi")
	public void parseLanguage(JSONArray languageObject){
		
		try{
			int length=languageObject.length();
			Log.i("EFAIR", "product length:"+length) ;
			for(int i=0;i<length;i++){
				int status=1;
				String languageId="",languageName="",creationDate="",updatedBy="";
				//get single record object
				JSONObject singleRecord=(JSONObject) languageObject.get(i);
				
				if(!singleRecord.get("langId").toString().equalsIgnoreCase(NULL))
				{languageId=(String) singleRecord.get("langId");}
				 languageId=(languageId.isEmpty() ? NOT_AVAILABLE:languageId);
				
				if(!singleRecord.get("langName").toString().equalsIgnoreCase(NULL))
				{languageName=(String) singleRecord.get("langName");}
				 languageName=(languageName.isEmpty() ? NOT_AVAILABLE:languageName); 
				
				if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
				{creationDate=(String) singleRecord.get("creationDate"); }
				 creationDate=(creationDate.isEmpty() ? NOT_AVAILABLE:creationDate);
				
				if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
				{ updatedBy=(String) singleRecord.get("updatedBy"); }
				  updatedBy=(updatedBy.isEmpty() ? NOT_AVAILABLE:updatedBy);
				
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
				//Log.i("EFAIR",languageId+","+languageName+","+creationDate+",updatedBy:"+updatedBy);
				Language lang=new Language(Integer.parseInt(languageId),languageName,creationDate, updatedBy);
				lang.setStatus(status);
				language.add(lang);
				/*Log.i("EFAIR", "INSERT product data:"+productId+",productName"+productName+productType+
						productBrand+description+creationDate+updatedBy);*/
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR", "INSERT language exception:"+e);
		}
		
	}
	@SuppressLint("NewApi")
	public void parseExhibitorLang(JSONArray exhibitorLangObject){
		int status=1;
		
		try{
			int length=exhibitorLangObject.length();
			Log.i("EFAIR", "product length:"+length) ;
			for(int i=0;i<length;i++){
				String languageId="",exhibitorId="",creationDate="",updatedBy="";
				//get single record object
				JSONObject singleRecord=(JSONObject) exhibitorLangObject.get(i);
				
				if(!singleRecord.get("langId").toString().equalsIgnoreCase(NULL))
				{languageId=(String) singleRecord.get("langId");}
				languageId=(languageId.isEmpty() ? NOT_AVAILABLE:languageId);
				
				if(!singleRecord.get("exhibitorId").toString().equalsIgnoreCase(NULL))
				{exhibitorId=(String) singleRecord.get("exhibitorId");}
				exhibitorId=(exhibitorId.isEmpty() ? NOT_AVAILABLE:exhibitorId); 
				
				if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
				{creationDate=(String) singleRecord.get("creationDate"); }
				creationDate=(creationDate.isEmpty() ? NOT_AVAILABLE:creationDate);
				
				if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
				{ updatedBy=(String) singleRecord.get("updatedBy"); }
				updatedBy=(updatedBy.isEmpty() ? NOT_AVAILABLE:updatedBy);
				
				try{
					status=Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
				//Log.i("EFAIR",languageId+","+exhibitorId+","+creationDate+",updatedBy:"+updatedBy);
			    ExhibitorLanguage lang=new ExhibitorLanguage(Integer.parseInt(languageId),Integer.parseInt(exhibitorId),creationDate, updatedBy);
			    lang.setStatus(status);
				exhibitorLanguage.add(lang);
				/*Log.i("EFAIR", "INSERT product data:"+productId+",productName"+productName+productType+
						productBrand+description+creationDate+updatedBy);*/
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.i("EFAIR", "INSERT language exception:"+e);
		}
		
	}
	@SuppressLint("NewApi")
	public void parseEvent(JSONArray eventObject){
		
		try{
			int length=eventObject.length();
			for(int i=0;i<length;i++){
				int status=1;
				String eventLocId="",eventLocationName="",eventLocationDescription="",creationDate="",updatedBy="";
				//get single record object
				JSONObject singleRecord=(JSONObject)eventObject.get(i);
				try {
					if(!singleRecord.get("event_loc_id").toString().equalsIgnoreCase(NULL))
					{ eventLocId=(String) singleRecord.get("event_loc_id");	}
					  eventLocId=(eventLocId.isEmpty() ? INTEGER_NULL:eventLocId);
				} catch (Exception e) {
					// TODO: handle exception
					eventLocId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("eventLocationName").toString().equalsIgnoreCase(NULL))
					{ eventLocationName=(String) singleRecord.get("eventLocationName");	}
					  eventLocationName=(eventLocationName.isEmpty() ? NOT_AVAILABLE:eventLocationName);
				} catch (Exception e) {
					// TODO: handle exception
					eventLocationName=NULL;
				}
				try {
					if(!singleRecord.get("eventLocationDescription").toString().equalsIgnoreCase(NULL))
					{ eventLocationDescription=(String) singleRecord.get("eventLocationDescription");	}
					  eventLocationDescription=(eventLocationDescription.isEmpty() ? NOT_AVAILABLE:eventLocationDescription);
				} catch (Exception e) {
					// TODO: handle exception
					eventLocationDescription=NULL;
				}
				
				try {
					if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
					{ creationDate=(String) singleRecord.get("creationDate"); }
					  creationDate=(creationDate.isEmpty() ? INTEGER_NULL:creationDate);
				} catch (Exception e) {
					// TODO: handle exception
					creationDate=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
					{ updatedBy=(String) singleRecord.get("updatedBy"); }
					  updatedBy=(updatedBy.isEmpty() ? NOT_AVAILABLE:updatedBy);
				} catch (Exception e) {
					// TODO: handle exception
					updatedBy=NULL;
				}
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
				Event even=new Event(Integer.parseInt(eventLocId),eventLocationName, eventLocationDescription,creationDate, updatedBy);
				even.setStatus(status);
				event.add(even);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@SuppressLint("NewApi")
	public void parseEventInfo(JSONArray eventInfoObject){
		
		try{
			int length=eventInfoObject.length();
			for(int i=0;i<length;i++){
				int status=1;
				String eventId="",eventLocId="",eventName="",eventDate="",startTime="",endTime="",description="",creationDate="",updatedBy="",moderator="",panelists="";
				String eventType;
				//get single record object
				JSONObject singleRecord=(JSONObject)eventInfoObject.get(i);
				try {
					if(!singleRecord.get("eventId").toString().equalsIgnoreCase(NULL))
					{ eventId=(String) singleRecord.get("eventId");	}
					  eventId=(eventId.isEmpty() ? INTEGER_NULL:eventId);
				} catch (Exception e) {
					// TODO: handle exception
					eventId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("eventLocationId").toString().equalsIgnoreCase(NULL))
					{ eventLocId=(String) singleRecord.get("eventLocationId");	}
					  eventLocId=(eventLocId.isEmpty() ? INTEGER_NULL:eventLocId);
				} catch (Exception e) {
					// TODO: handle exception
					eventLocId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("eventName").toString().equalsIgnoreCase(NULL))
					{ eventName=(String) singleRecord.get("eventName");	}
					  eventName=(eventName.isEmpty() ? NOT_AVAILABLE:eventName);
				} catch (Exception e) {
					// TODO: handle exception
					eventName=NULL;
				}
				
				try {
					if(!singleRecord.get("eventDate").toString().equalsIgnoreCase(NULL))
					{ eventDate=(String) singleRecord.get("eventDate");	}
					   eventDate=(eventDate.isEmpty() ?INTEGER_NULL:eventDate);
				} catch (Exception e) {
					// TODO: handle exception
					eventDate=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("startTime").toString().equalsIgnoreCase(NULL))
					{ startTime=(String) singleRecord.get("startTime");	}
					  startTime=(startTime.isEmpty() ?INTEGER_NULL:startTime);
				} catch (Exception e) {
					// TODO: handle exception
					startTime=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("endTime").toString().equalsIgnoreCase(NULL))
					{ endTime=(String) singleRecord.get("endTime");	}
					  endTime=(endTime.isEmpty() ?INTEGER_NULL:endTime);
				} catch (Exception e) {
					// TODO: handle exception
					endTime=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("description").toString().equalsIgnoreCase(NULL))
					{ description=(String) singleRecord.get("description");	}
					  description=(description.isEmpty() ?NULL:description);
				} catch (Exception e) {
					// TODO: handle exception
					description=NULL;
				}
				
				try {
					if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
					{ creationDate=(String) singleRecord.get("creationDate"); }
					  creationDate=(creationDate.isEmpty() ? INTEGER_NULL:creationDate);
				} catch (Exception e) {
					// TODO: handle exception
					creationDate=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
					{ updatedBy=(String) singleRecord.get("updatedBy"); }
					  updatedBy=(updatedBy.isEmpty() ? NOT_AVAILABLE:updatedBy);
				} catch (Exception e) {
					// TODO: handle exception
					updatedBy=NULL;
				}
				try {
					if(!singleRecord.get("moderator").toString().equalsIgnoreCase(NULL))
					{ moderator=(String) singleRecord.get("moderator"); }
					  moderator=(moderator.isEmpty() ? NOT_AVAILABLE:moderator);
				} catch (Exception e) {
					// TODO: handle exception
					moderator=NULL;
				}
				try {
					if(!singleRecord.get("panelists").toString().equalsIgnoreCase(NULL))
					{ panelists=(String) singleRecord.get("panelists"); }
					  panelists=(panelists.isEmpty() ? NOT_AVAILABLE:panelists);
				} catch (Exception e) {
					// TODO: handle exception
					panelists=NULL;
				}
				try{
					eventType=singleRecord.getString("eventType");
				}catch(Exception e){
					e.printStackTrace();
					eventType="";
				}
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
				EventInfo even=new EventInfo(Integer.parseInt(eventId),Integer.parseInt(eventLocId), eventName,eventDate,startTime,endTime,description,creationDate, updatedBy,moderator,panelists);
				even.setEventType(eventType);
				even.setStatus(status);
				eventInfo.add(even);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@SuppressLint("NewApi")
	public void parseEventLocation(JSONArray eventLocationObject){
		
		try{
			int length=eventLocationObject.length();
			for(int i=0;i<length;i++){
				int status=1;
				String eventLocId="",locationId="",locationName="",description="",xLocation="",yLocation="",creationDate="",updatedBy="",event_loc_id="";
				//get single record object
				JSONObject singleRecord=(JSONObject)eventLocationObject.get(i);
				try {
					if(!singleRecord.get("eventLocationId").toString().equalsIgnoreCase(NULL))
					{ eventLocId=(String) singleRecord.get("eventLocationId");	}
					  eventLocId=(eventLocId.isEmpty() ? INTEGER_NULL:eventLocId);
				} catch (Exception e) {
					// TODO: handle exception
					eventLocId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("locationId").toString().equalsIgnoreCase(NULL))
					{ locationId=(String) singleRecord.get("locationId");	}
					  locationId=(locationId.isEmpty() ? INTEGER_NULL:locationId);
				} catch (Exception e) {
					// TODO: handle exception
					locationId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("locationName").toString().equalsIgnoreCase(NULL))
					{ locationName=(String) singleRecord.get("locationName");	}
					  locationName=(locationName.isEmpty() ? NOT_AVAILABLE:locationName);
				} catch (Exception e) {
					// TODO: handle exception
					locationName=NULL;
				}
				try {
					if(!singleRecord.get("description").toString().equalsIgnoreCase(NULL))
					{ description=(String) singleRecord.get("description");	}
					  description=(description.isEmpty() ? NOT_AVAILABLE:description);
				} catch (Exception e) {
					// TODO: handle exception
					description=NULL;
				}
				
				try {
					if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
					{ creationDate=(String) singleRecord.get("creationDate"); }
					  creationDate=(creationDate.isEmpty() ? INTEGER_NULL:creationDate);
				} catch (Exception e) {
					// TODO: handle exception
					creationDate=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
					{ updatedBy=(String) singleRecord.get("updatedBy"); }
					  updatedBy=(updatedBy.isEmpty() ? NOT_AVAILABLE:updatedBy);
				} catch (Exception e) {
					// TODO: handle exception
					updatedBy=NULL;
				}
				try {
					if(!singleRecord.get("event_loc_id").toString().equalsIgnoreCase(NULL))
					{ event_loc_id=(String) singleRecord.get("event_loc_id"); }
					  event_loc_id=(event_loc_id.isEmpty() ? INTEGER_NULL:event_loc_id);
				} catch (Exception e) {
					// TODO: handle exception
					event_loc_id=INTEGER_NULL;
				}
				try{
					status=Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
				EventLocation even=new EventLocation(Integer.parseInt(eventLocId),Integer.parseInt(locationId),locationName, description,xLocation,yLocation,creationDate, updatedBy,Integer.parseInt(event_loc_id));
				even.setStatus(status);
				eventLocation.add(even);
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.i("WEB_DATA", "eventLocation parsing exception:"+e);
		}
	}
	@SuppressLint("NewApi")
	public void parseCompany(JSONArray companyObject){		
		try{
			int length=companyObject.length();
			for(int i=0;i<length;i++){
				int status=1;
				String companyId="",companyName="",companyDescription="",address1="",address2="",city="",state="",pinCode="",country="";
				String website="",email="",contactNo="",creationDate="",updatedBy="",panNumber="",number_of_persons_accompanying="";
				//get single record object
				JSONObject singleRecord=(JSONObject) companyObject.get(i);
				try {
					if(!singleRecord.get("companyId").toString().equalsIgnoreCase(NULL))
					{ companyId=(String) singleRecord.get("companyId");	}
					  companyId=(companyId.isEmpty() ? INTEGER_NULL:companyId);
				} catch (Exception e) {
					// TODO: handle exception
					companyId=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("companyName").toString().equalsIgnoreCase(NULL))
					{ companyName=(String) singleRecord.get("companyName");	}
					  companyName=(companyName.isEmpty() ? NOT_AVAILABLE:companyName);
					 
				} catch (Exception e) {
					// TODO: handle exception
					companyName=NULL;
				}
				try {
					if(!singleRecord.get("companyDescription").toString().equalsIgnoreCase(NULL))
					{ companyDescription=(String) singleRecord.get("companyDescription");	}
					  companyDescription=(companyDescription.isEmpty() ? NOT_AVAILABLE:companyDescription);
				} catch (Exception e) {
					// TODO: handle exception
					companyDescription=NULL;
				}
				try {
					if(!singleRecord.get("address1").toString().equalsIgnoreCase(NULL))
					{ address1=(String) singleRecord.get("address1");	}
					  address1=(address1.isEmpty() ? NOT_AVAILABLE:address1);
					 
				} catch (Exception e) {
					// TODO: handle exception
					address1=NULL;
				}
				try {
					//Log.i("address2",""+singleRecord.get("address2"));
					if(!singleRecord.get("address2").toString().equalsIgnoreCase(NULL))
					{ address2=(String) singleRecord.get("address2");}						
					  address2=(address2.isEmpty() ? NOT_AVAILABLE:address2);
					  
				} catch (Exception e) {
					// TODO: handle exception
					address2=NULL;
				}
				
				try {
					if(!singleRecord.get("city").toString().equalsIgnoreCase(NULL))
					{ city=(String) singleRecord.get("city"); }
					  city=(city.isEmpty() ? NOT_AVAILABLE:city);
				} catch (Exception e) {
					// TODO: handle exception
					city=NULL;
				}
				try {
					if(!singleRecord.get("state").toString().equalsIgnoreCase(NULL))
					{ state=(String) singleRecord.get("state"); }
					  state=(state.isEmpty() ? NOT_AVAILABLE:state);
				} catch (Exception e) {
					// TODO: handle exception
					state=NULL;
				}
				try {
					if(!singleRecord.get("pinCode").toString().equalsIgnoreCase(NULL))
					{ pinCode=(String) singleRecord.get("pinCode"); }
					  pinCode=(pinCode.isEmpty() ? NOT_AVAILABLE:pinCode);
				} catch (Exception e) {
					// TODO: handle exception
					pinCode=NULL;
				}
				try {
					if(!singleRecord.get("country").toString().equalsIgnoreCase(NULL))
					{ country=(String) singleRecord.get("country"); }
					  country=(country.isEmpty() ? NOT_AVAILABLE:country);
				} catch (Exception e) {
					// TODO: handle exception
					country=NULL;
				}
				try {
					if(!singleRecord.get("website").toString().equalsIgnoreCase(NULL))
					{ website=(String) singleRecord.get("website"); }
					  website=(website.isEmpty() ? NOT_AVAILABLE:website);
				} catch (Exception e) {
					// TODO: handle exception
					website=NULL;
				}
				try {
					if(!singleRecord.get("email").toString().equalsIgnoreCase(NULL))
					{ email=(String) singleRecord.get("email"); }
					  email=(email.isEmpty() ? NOT_AVAILABLE:email);
				} catch (Exception e) {
					// TODO: handle exception
					email=NULL;
				}
				try {
					if(!singleRecord.get("contactNo").toString().equalsIgnoreCase(NULL))
					{ contactNo=(String) singleRecord.get("contactNo"); }
					  contactNo=(contactNo.isEmpty() ? NOT_AVAILABLE:contactNo);
				} catch (Exception e) {
					// TODO: handle exception
					contactNo=NULL;
				}
				try {
					if(!singleRecord.get("creationDate").toString().equalsIgnoreCase(NULL))
					{ creationDate=(String) singleRecord.get("creationDate"); }
					  creationDate=(creationDate.isEmpty() ? INTEGER_NULL:creationDate);
				} catch (Exception e) {
					// TODO: handle exception
					creationDate=INTEGER_NULL;
				}
				try {
					if(!singleRecord.get("updatedBy").toString().equalsIgnoreCase(NULL))
					{ updatedBy=(String) singleRecord.get("updatedBy"); }
					  updatedBy=(updatedBy.isEmpty() ? NOT_AVAILABLE:updatedBy);
				} catch (Exception e) {
					// TODO: handle exception
					updatedBy=NULL;
				}
				try {
					if(!singleRecord.get("panNumber").toString().equalsIgnoreCase(NULL))
					{ panNumber=(String) singleRecord.get("panNumber"); }
					  panNumber=(panNumber.isEmpty() ? INTEGER_NULL:panNumber);
				} catch (Exception e) {
					// TODO: handle exception
					panNumber=INTEGER_NULL;
				}
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
				try{
					number_of_persons_accompanying=singleRecord.getString("number_of_persons_accompanying");
				}catch(Exception e){}
				
				Company com=new Company(Integer.parseInt(companyId),companyName,companyDescription,address1,address2,city,state,pinCode,country,website,email,contactNo,creationDate, updatedBy,panNumber);
				com.setStatus(status);
				com.setNumber_of_persons_accompanying(number_of_persons_accompanying);
				company.add(com);
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.i("WEB_DATA", "eventLocation parsing exception:"+e);
		}
	}
	
	public void parseResource(JSONArray data){
		
		try{
			int length=data.length();
			for(int i=0;i<length;i++){
				int locationId=0,status=1;
				String resource="",type="",creationDate="",updatedBy="";
				JSONObject singleRecord=data.getJSONObject(i);
				try{
					locationId=singleRecord.getInt("locationId");
				}catch(Exception e){
					e.printStackTrace();
					locationId=0;
				}
				try{
					resource=singleRecord.getString("resource");
				}catch(Exception e){
					e.printStackTrace();
					resource="";
				}
				try{
					type=singleRecord.getString("type");
				}catch(Exception e){
					e.printStackTrace();
					type="";
				}
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				try{
					creationDate=singleRecord.getString("creationDate");					
				}catch(Exception e){
					e.printStackTrace();
					creationDate="";
				}
				try{
					updatedBy=singleRecord.getString("updatedBy");					
				}catch(Exception e){
					e.printStackTrace();
					updatedBy="";
				}
				
				Resource res=new Resource(locationId, resource, type);
				res.setStatus(status);
				res.setCreationDate(creationDate);
				res.setUpdatedBy(updatedBy);
				this.resource.add(res);

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void parseFileSetting(JSONArray data){
		
		try{
			int length=data.length();
			for(int i=0;i<length;i++){
				int id,status=1;
				String fileName,filePath,creationDate="",updatedBy="";
				JSONObject singleRecord=data.getJSONObject(i);
				try{
				   id=singleRecord.getInt("id");
				}catch(Exception e){
					e.printStackTrace();
					id=0;
				}
				try{
					fileName=singleRecord.getString("fileName");
				}catch(Exception e){
					e.printStackTrace();
					fileName="";
				}
				try{
					filePath=singleRecord.getString("filePath");
				}catch(Exception e){
					e.printStackTrace();
					filePath="";
				}
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				try{
					creationDate=singleRecord.getString("creationDate");
				}catch(Exception e){
					e.printStackTrace();
				}
				try{
					updatedBy=singleRecord.getString("updatedBy");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				FileSettings file=new FileSettings(id, filePath, fileName);
				file.setStatus(status);
				file.setCreationDate(creationDate);
				file.setUpdatedBy(updatedBy);
				this.fileSetting.add(file);

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void parseExhibitorEntryExit(JSONArray data){
		
		
		try{
			int length=data.length();
			for(int i=0;i<length;i++){
				int exbLocId=0,status=1,id=0;
				String xLoc="0",yLoc="0",creationDate="0",updatedBy="",type="0";
				JSONObject singleRecord=data.getJSONObject(i);
				try{
					   id=singleRecord.getInt("id");
					}catch(Exception e){
						e.printStackTrace();
						id=0;
					}
				try{
				   exbLocId=singleRecord.getInt("exhibitorLocationId");
				}catch(Exception e){
					e.printStackTrace();
					exbLocId=0;
				}
				try{
					xLoc=singleRecord.getString("xLocation");
				}catch(Exception e){
					e.printStackTrace();
					xLoc="0";
				}
				
				try{
					yLoc=singleRecord.getString("yLocation");
				}catch(Exception e){
					e.printStackTrace();
					yLoc="0";
				}
				try{
				    status=Integer.parseInt(singleRecord.getString("status"));
				}catch(Exception e){
					e.printStackTrace();
				}
				try{
					type=singleRecord.getString("type");
				}catch(JSONException e){}
				try{
				    creationDate=singleRecord.getString("creationDate");
				}catch(Exception e){
					e.printStackTrace();
					creationDate="0";
				}
				try{
				    updatedBy=singleRecord.getString("updatedBy");
				}catch(Exception e){
					e.printStackTrace();
					updatedBy="";
				}
				
				
				ExhibitorEntryExit exb=new ExhibitorEntryExit(exbLocId, xLoc, yLoc, status, creationDate, updatedBy);
				exb.setId(id);
				exb.setType(type);
				this.exhibitorEntryExit.add(exb);

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void parseFacilityEntryExit(JSONArray data) {	
		
		try{
			int length=data.length();
			for(int i=0;i<length;i++){
				int id=0,facilityInfoId=0,status=1;
				String xLoc="0",yLoc="0",creationDate="0",updatedBy="";
				JSONObject singleRecord=data.getJSONObject(i);
				try{
				   id=singleRecord.getInt("id");
				}catch(Exception e){
					e.printStackTrace();
					id=0;
				}
				try{
					   facilityInfoId=singleRecord.getInt("facilityinfoid");
					}catch(Exception e){
						e.printStackTrace();
						facilityInfoId=0;
					}
				try{
					xLoc=singleRecord.getString("xLocation");
				}catch(Exception e){
					e.printStackTrace();
					xLoc="0";
				}
				
				try{
					yLoc=singleRecord.getString("yLocation");
				}catch(Exception e){
					e.printStackTrace();
					yLoc="0";
				}
				
				try{
				    creationDate=singleRecord.getString("creationDate");
				}catch(Exception e){
					e.printStackTrace();
					creationDate="0";
				}
				try{
				    updatedBy=singleRecord.getString("updatedBy");
				}catch(Exception e){
					e.printStackTrace();
					updatedBy="";
				}
				try{
					status = Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){}
				
				FacilityEntryExit facility=new FacilityEntryExit(id, facilityInfoId, xLoc, yLoc, creationDate, updatedBy);
				facility.setStatus(status);
				this.facilityEntryExit.add(facility);

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void parseFacilityInformation(JSONArray data) {
		
		
		try{
			int length=data.length();
			for(int i=0;i<length;i++){
				int locationId=0,facilityInfoId=0,status=1;
				String xLoc="0",yLoc="0",creationDate="0",updatedBy="",locationName="", type="";
				JSONObject singleRecord=data.getJSONObject(i);
				try{
				   locationId=singleRecord.getInt("locationId");
				}catch(Exception e){
					e.printStackTrace();
					locationId=0;
				}
				try{
					   facilityInfoId=singleRecord.getInt("facilityinfoid");
					}catch(Exception e){
						e.printStackTrace();
						facilityInfoId=0;
					}
				try{
					xLoc=singleRecord.getString("locationX");
				}catch(Exception e){
					e.printStackTrace();
					xLoc="0";
				}
				
				try{
					yLoc=singleRecord.getString("locationY");
				}catch(Exception e){
					e.printStackTrace();
					yLoc="0";
				}
				try{
				    status=Integer.parseInt(singleRecord.getString("status"));
				}catch(Exception e){
					e.printStackTrace();
					status=1;
				}
				try{
				    locationName=singleRecord.getString("locationName");
				}catch(Exception e){
					e.printStackTrace();
					locationName="";
				}
				try{
				    creationDate=singleRecord.getString("creationDate");
				}catch(Exception e){
					e.printStackTrace();
					creationDate="0";
				}
				try{
				    updatedBy=singleRecord.getString("updatedBy");
				}catch(Exception e){
					e.printStackTrace();
					updatedBy="";
				}
				try{
					type=singleRecord.getString("type");
				}catch(JSONException e){}
				
				FacilityInformation facility=new FacilityInformation(facilityInfoId, locationId, locationName, xLoc, yLoc, creationDate, updatedBy);
				facility.setStatus(status);
				facility.setType(type);
				this.facilityInfo.add(facility);

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void parseQuestionnaire(JSONArray data){
		int questionId=0,orderId=0,status=1;
		String questionType="",question="",userType="",creationDate="",updatedBy="";
		
		try{
			for(int i=0;i<data.length();i++){
				 JSONObject singleRecord=data.getJSONObject(i);
				 try{
					 questionId=singleRecord.getInt("questionId");
				 }catch(Exception e){}
				 try{
					 orderId=singleRecord.getInt("orderId");
				 }catch(Exception e){}
				 try{
					 status=singleRecord.getInt("status");
				 }catch(Exception e){}
				 try{
					 questionType=singleRecord.getString("questionType");
				 }catch(Exception e){}
				 try{
					 question=singleRecord.getString("question");
				 }catch(Exception e){}
				 try{
					 userType=singleRecord.getString("userType");
				 }catch(Exception e){}
				 try{
					 creationDate=singleRecord.getString("creationDate");
				 }catch(Exception e){}
				 try{
					 updatedBy=singleRecord.getString("updatedBy");
				 }catch(Exception e){}
				 
				 VisitorQuestionnaire questionnaire = new VisitorQuestionnaire(questionId, orderId, status, questionType, question, userType, creationDate, updatedBy);
				 questionnaires.add(questionnaire);
			}
		}catch(Exception e){}
	}
	
	public void parseQueOption(JSONArray data){
		int optionId=0,questionId=0,orderId=0,status=1;
		String option="",creationDate="",updatedBy="";
		try{
			for(int i=0;i<data.length();i++){
				 JSONObject singleRecord=data.getJSONObject(i);
				 try{
					 optionId=singleRecord.getInt("optionId");
				 }catch(Exception e){}
				 try{
					 questionId=singleRecord.getInt("questionId");
				 }catch(Exception e){}
				 try{
					 orderId=singleRecord.getInt("orderId");
				 }catch(Exception e){}
				 try{
					 status=singleRecord.getInt("status");
				 }catch(Exception e){}
				 try{
					 option=singleRecord.getString("option");
				 }catch(Exception e){}
				 try{
					 creationDate=singleRecord.getString("creationDate");
				 }catch(Exception e){}
				 try{
					 updatedBy=singleRecord.getString("updatedBy");
				 }catch(Exception e){}
				 
				 VisitorQueOption queOption = new VisitorQueOption(optionId, questionId, orderId, status, option, creationDate, updatedBy);
				 options.add(queOption);
			}
		}catch(Exception e){}
	}
	
	/***Static data **/
	public void parseLinks(JSONArray data){
		 
		 
		 try{
			 for(int i=0;i<data.length();i++){
				 int linkId = 0,linkTypeId = 0, status = 1;
				 String linkUrl = "",linkName = "",creationDate = "", updatedBy = "";
				 JSONObject singleRecord=data.getJSONObject(i);
				 
				 try{
					 linkId = Integer.parseInt(singleRecord.getString("linkId"));
				 }catch(JSONException e){			 
					 
				 }/*
				 try{
					 linkTypeId = Integer.parseInt(singleRecord.getString("linkTypeId"));
				 }catch(JSONException e){			 
					 
				 }*/
				 try{
					 status = Integer.parseInt(singleRecord.getString("status"));
				 }catch(JSONException e){			 
					 
				 }
				 try{
					 linkUrl = singleRecord.getString("linkUrl");
				 }catch(JSONException e){			 
					 
				 }
				 try{
					 linkName = singleRecord.getString("linkName");
				 }catch(JSONException e){			 
					 
				 }
				 try{
					 creationDate = singleRecord.getString("creationDate");
				 }catch(JSONException e){			 
					 
				 }
				 try{
					 updatedBy = singleRecord.getString("updatedBy");
				 }catch(JSONException e){			 
					 
				 }
				 Links link = new Links(linkId, linkTypeId, status, linkUrl, linkName, creationDate, updatedBy);
				 this.links.add(link);
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }		 
	}
	public void parseExhibitorCategory(JSONArray jExhibitorCategory) {
		
		try{
			for(int i=0;i<jExhibitorCategory.length();i++){
				
				int		categoryId=0, status=1;
				String	categoryName="", creationDate, updatedBy="";
				
				JSONObject singleRecord = jExhibitorCategory.getJSONObject(i);
				
				try{
					categoryId=Integer.parseInt(singleRecord.getString("categoryId").trim());
				}catch(JSONException e){
					status=1;
				}
				try{
					categoryName=singleRecord.getString("categoryName").trim();
				}catch(JSONException e){
					creationDate=INTEGER_NULL;
				}
				try{
					status=Integer.parseInt(singleRecord.getString("status").trim());
				}catch(JSONException e){
					status=1;
				}
				try{
					creationDate=singleRecord.getString("creationDate").trim();
				}catch(JSONException e){
					creationDate=INTEGER_NULL;
				}
				try{
					updatedBy=singleRecord.getString("updatedBy").trim();
				}catch(JSONException e){
					updatedBy=NULL;
				}
				ExhibitorCategory category = new ExhibitorCategory(categoryId, categoryName, creationDate, updatedBy);
				category.setStatus(status);
				exhibitorCategories.add(category);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*MartFacility*/
	public void parseMartFacilities(JSONArray jMartFacility) {
		
		try{
			for(int i=0;i<jMartFacility.length();i++){
				
				int		facilityId=0, status=1;
				String	facilityName="", imgName="", creationDate, updatedBy="";
				
				JSONObject singleRecord = jMartFacility.getJSONObject(i);
				
				try{
					facilityId=Integer.parseInt(singleRecord.getString("facilityId").trim());
				}catch(JSONException e){
					status=1;
				}
				try{
					facilityName=singleRecord.getString("facilityName").trim();
				}catch(JSONException e){
					creationDate=INTEGER_NULL;
				}
				try{
					imgName=singleRecord.getString("imgName").trim();
				}catch(JSONException e){
					creationDate=INTEGER_NULL;
				}
				try{
					creationDate=singleRecord.getString("creationDate").trim();
				}catch(JSONException e){
					creationDate=INTEGER_NULL;
				}
				try{
					updatedBy=singleRecord.getString("updatedBy").trim();
				}catch(JSONException e){
					updatedBy=NULL;
				}
				try{
					status=Integer.parseInt(singleRecord.getString("status").trim());
				}catch(JSONException e){
					status=1;
				}
				MartFacilities martFaclty = new MartFacilities(facilityId, facilityName, imgName, creationDate, updatedBy, status);
				//category.setStatus(status);
				martFacilities.add(martFaclty);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**AdServe**/
	public void parseAdServe(JSONArray data){
		int adId=0, status=0;
		String placementId="", hashCode="", url="", creationDate="", updatedBy="";
		try{
			for(int i=0;i<data.length();i++){
				JSONObject singleRecord = data.getJSONObject(i);
				try{
					adId=Integer.parseInt(singleRecord.getString("adId"));
				}catch(JSONException e){
					
				}
				try{
					status=Integer.parseInt(singleRecord.getString("status"));
				}catch(JSONException e){
					
				}
				try{
					placementId=singleRecord.getString("placementId");
				}catch(JSONException e){
					
				}
				try{
					hashCode=singleRecord.getString("hashCode");
				}catch(JSONException e){
					
				}
				try{
					url=singleRecord.getString("url");
				}catch(JSONException e){
					
				}
				try{
					creationDate=singleRecord.getString("creationDate");
				}catch(JSONException e){
					
				}
				try{
					updatedBy=singleRecord.getString("updatedBy");
				}catch(JSONException e){
								
				}
				AdService ad = new AdService(adId, status, placementId, hashCode, url, creationDate, updatedBy);
				this.adServices.add(ad);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void saveImageUrl(String value)
	{     
		  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		  SharedPreferences.Editor editor = preferences.edit();
		  editor.putString("ImgUrl",value);
		  editor.commit();
	}
	
	
	public void saveStaticPageBaseURL(String baseUrl){
		pref.saveUserData(MySharedPreferences.STATIC_PAGE_URL, baseUrl);
	}
	
	public ArrayList<Products> getSubmitProducts(){
		return this.products;
	}
	
	public ArrayList<Roles> getUpdatedRoles(){
		return this.roles;
	}
	public ArrayList<Users> getUpdatedUsers(){
		return this.users;
	}
	public ArrayList<Exhibitors> getSubmitExhibitors(){
		return this.exhibitors;
	}
	public ArrayList<Visitors> getUpdatedVisitors(){
		return this.visitors;
	}
	public ArrayList<ExhibitorProducts> getSubmitExhibitorProducts(){
		return this.exhibitorProducts;
	}
	public ArrayList<Appointment> getUpdatedAppointments(){
		return this.appointments;
	}
	public ArrayList<Admin> getUpdatedAdmins(){
		return this.admins;
	}
	public ArrayList<ProductPhoto> getSubmitProductPhoto(){
		return this.productPhotoes;
	}
	
	public ArrayList<VenueMap> getSubmitVenueMap(){
		return this.venueMaps;
	}
	public ArrayList<ExhibitorLocation> getSubmitExhibitorLocation(){
		return this.exhibitorLocations;
	}
	public ArrayList<Language> getSubmitLanguage(){
		return this.language;
	}
	public ArrayList<ExhibitorLanguage> getSubmitExhibitorLang(){
		return this.exhibitorLanguage;
	}
	public ArrayList<Event> getSubmitEvents(){
		return this.event;
	}
	public ArrayList<EventInfo> getSubmitEventInfo(){
		return this.eventInfo;
	}
	public ArrayList<EventLocation> getSubmitEventLocation(){
		return this.eventLocation;
	}
	public ArrayList<Company> getSubmitCompany(){
		return this.company;
	}
	public ArrayList<Resource> getSubmitResource(){
		return this.resource;
	}
	public ArrayList<FileSettings> getSubmitFileSetting(){
		return this.fileSetting;
	}
	public ArrayList<ExhibitorEntryExit> getSubmitExhibitorEntryExit(){
		return this.exhibitorEntryExit;
	}
	public ArrayList<FacilityInformation> getSubmitfacilityInformation(){
		return this.facilityInfo;
	}
	public ArrayList<FacilityEntryExit> getSubmitfacilityEntryExit(){
		return this.facilityEntryExit;
	}
	public String getDatabaseName(){
		return this.databaseName;
	}
	public ArrayList<Links> getLinks() {
		return links;
	}

	public ArrayList<ExhibitorCategory> getExhibitorCategories() {
		return exhibitorCategories;
	}
	public ArrayList<VisitorQuestionnaire> getQuestionnaires() {
		return questionnaires;
	}
	public ArrayList<VisitorQueOption> getOptions() {
		return options;
	}
	public ArrayList<MartFacilities> getMartFacilities() {
		Log.i("EFAIR","Size of martFacilities table : "+martFacilities.size());
		return martFacilities;
	}
	public ArrayList<AdService> getAdServices() {
		Log.i("EFAIR","Size of adServe table : "+adServices.size());
		return adServices;
	}
}
