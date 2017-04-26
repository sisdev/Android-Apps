/**
 * This class holds all possible query of this application
 */
package com.assusoft.eFairEmall.databaseMaster;

public class Query {
	//all table name listed here
	public final static String ADMIN="admin";
	public final static String APPOINTMENT="appointment";
	public final static String EXHIBITOR_LOCATION="exhibitorLocation";
	public final static String EXHIBITOR_PRODUCTS="exhibitorproducts";
	public final static String EXHIBITORS="exhibitors";
	public final static String PRODUCTPHOTOS="productPhotos";
	public final static String PRODUCTS="products";
	public final static String ROLES="roles";
	public final static String USERS="users";
	public final static String VENUEMAP="venuemap";
	public final static String VISITORS="visitors";
	public final static String SELECT_GREATEST_TIMESTAMP="select  max(" +
			"IFNULL((select max(creationDate) from exhibitorLocation),0),"+
			"IFNULL((select max(creationDate) from exhibitors),0),"+
			"IFNULL((select max(creationDate) from venuemap),0),"+
			"IFNULL((select max(creationDate) from company),0),"+
			"IFNULL((select max(creationDate) from products),0)," +
			"IFNULL((select max(creationDate) from exhibitorentryexit),0)," +
			"IFNULL((select max(creationDate) from FacilityInformation),0)," +
			"IFNULL((select max(creationDate) from language),0)," +
			"IFNULL((select max(creationDate) from exhibitorLang),0)," +
			"IFNULL((select max(creationDate) from event),0)," +
			"IFNULL((select max(creationDate) from eventInfo),0)," +
			"IFNULL((select max(creationDate) from eventLocation),0)," +
			"IFNULL((select max(creationDate) from exhibitorProducts),0)," +
			"IFNULL((select max(creationDate) from productPhotos),0)," +
			"IFNULL((select max(creationDate) from resource),0)," +
			"IFNULL((select max(creationDate) from links),0)," +
			"IFNULL((select max(creationDate) from file_setting),0)," +
			"IFNULL((select max(creationDate) from exhibitorCategory),0)," +
			"IFNULL((select max(creationDate) from visitor_questionnaire),0)," +
			"IFNULL((select max(creationDate) from visitor_que_option),0),"+
			"IFNULL((select max(creationDate) from mart_facilities),0),"+
			"IFNULL((select max(creationDate) from adServe),0))";
				
	public final static String  SELECT_ALL_PRODUCT ="SELECT * FROM Products";
	////? MARK IS REPLACES BY selectedPRODUCTID
	public final static String SELECT_ALL_EXIBITOR_OF_A_PRODUCT ="SELECT  exhibitorName,exhibitorId FROM exhibitors where exhibitorId in"+
		   	"(select exhibitorId from ExhibitorProducts where productId=?)";
	////? MARK IS REPLACES BY selectedExibitorId
	/*public final static String SELECT_EXIBITOR_DETAILS ="select exhibitors.exhibitorId,venuemap.type,floorName,exhibitorLocation.standNo,companyName,companyDescription,address1,address2,city,state,company.country,pinCode,"+
	"contactPerson,mobileNo,exhibitors.contactNo,fax,exhibitors.email,website,hallNo,filePath,xLocation,yLocation from "+ 
 "venuemap ,exhibitorlocation,exhibitors,company where venueMap.locationId=(select locationId from exhibitorlocation where exhibitorId=?)" +
 "And exhibitors.exhibitorId=? And exhibitorlocation.exhibitorId=? and exhibitors.companyId=company.companyId and venueMap.year=2014";*/
	public final static String SELECT_EXIBITOR_DETAILS ="select exhibitors.exhibitorId,venuemap.type,floorName,exhibitorLocation.standNo,companyName,companyDescription,address1,address2,city,state,company.country,pinCode,"+
				" contactPerson,mobileNo,exhibitors.contactNo,fax,exhibitors.email,website,hallNo,filePath,xLocation,yLocation,refNo from "+ 
			 " venuemap ,exhibitorlocation,exhibitors,company where venueMap.locationId=exhibitorLocation.locationId "+
			 " And exhibitors.exhibitorId =?  And exhibitorlocation.exhibitorId=? and exhibitors.companyId=company.companyId and venueMap.year=2014 and standNo like  ?";
	//? MARK IS REPLACES BY selectedExibitorId
	public final static String SELECT_LIST_OF_PRODUCT_PROVIDED_BY_EXIBITOR ="select productName from Products where productId in(select  " +
            "productId from ExhibitorProducts where exhibitorId=?)";	
	public final static String SELECT_LIST_OF_PRODUCT_PROVIDED_BY_EXIBITOR1 ="select productName,productId from Products where productId in(select  " +
            "productId from ExhibitorProducts where exhibitorId=?)";	
	public final static String SELECT_IMG_AS_PER_PRODUCT="select filePath from productphotos where productId=(select productId from products where productName=?)";
	//Search image by exhibitor wise
	public final static String SEARCH_IMAGE_BY_EXHIBITOR_WISE="select filePath from productphotos where exhibitorId=(SELECT exhibitorId FROM exhibitors WHERE exhibitorName like ?)";
	//search image by product wise
	public final static String SEARCH_IMAGE_BY_PRODUCT_WISE="select filePath from productPhotos where productId in(select productId from products where productName like ?)";
    //search query ALL QUESTION MARK REPLACED BY SEARCHTEXT
	public final static String SELECT_NAMEWISE_EXIBITORS_SEARCH ="SELECT exhibitorName,exhibitorId FROM exhibitors WHERE exhibitorName like ?";
	public final static String SELECT_CITYWISE_EXIBITOR_SEARCH ="SELECT exhibitorName,exhibitorId FROM exhibitors WHERE city like ?";
	public final static String SELECT_PRODUCTWISE_EXIBITOR_SEARCH ="select exhibitorName,exhibitorId from exhibitors where exhibitorId in (select exhibitorId from exhibitorproducts where productId  in" +
			"(select productId from products where productName like ?))";	
	public final static String SELECT_EXHIBITORWISE_PRODUCTS_EARCH="select productName,productId from products where productId in(select productId from exhibitorproducts where exhibitorId in" +
			"(select exhibitorId from exhibitors where exhibitorName like ?))";
	public final static String SEARCH_PHOTOS_BY_EXHIBITOR_NAME="select filePath from productphotos where exhibitorId in(select exhibitorId from exhibitors where exhibitorName like ?)";
    public final static String SELECT_ALL_PHOTOS="select filePath from productphotos";
    //this query is used to access all appointment schedule
    public final static String SELECT_ALL_APPOINTMENT_SCHEDULE="select Exhibitors.exhibitorId,Exhibitors.exhibitorName, "+
                                                       "date,startTime,endTime,status from Exhibitors,Appointment where "+
    		                                           " Appointment.exhibitorId=Exhibitors.exhibitorId";
    //this query is used to delete the particular schedule
    public final static String DELETE_APPOINTMENT="date=? "+"and startTime=? "+
                                                   "and endTime=?";
    //21-dec-2013
    public final static String  SELECT_ALL_EXHIBITOR_NAME="select companyName,exhibitorId from company,exhibitors where company.companyId in(exhibitors.companyId)";
	public final static String  SELECT_ALL_PRODUCT1 ="SELECT productName FROM Products";
	public final static String SELECT_EXHIBITOR_DETAIL_BY_EXHIBITOR_NAME="select exhibitors.exhibitorId,type,floorName,standNo,exhibitorName,exhibitorDescription,address1,city,state,country,nationality,pinCode,contactPerson,mobileNo,contactNo,fax,email,website from "+
	"venuemap ,exhibitorlocation,exhibitors where  venuemap.locationId=(select locationId from exhibitorlocation where exhibitorId=(select exhibitorId from exhibitors where exhibitorName=?))And exhibitorlocation.exhibitorId=(select exhibitorId from exhibitors where exhibitorName=?) And exhibitorName=?";
	 public final static String SELECT_ALL_PHOTOS_AND_EXHIBITOR_NAME="select filePath,exhibitorName from productphotos,exhibitors where productphotos.exhibitorId=exhibitors.exhibitorId";
//Selecting ground location floor,first floor likewise.....
	 public final static String SELECT_LOCATION="select xLocation,yLocation from exhibitorlocation where locationId=?";
	 
	 
	 
	 
	 //belows queries related to NBTI project
	 //event date must be passed in yyyy-mm-dd formate
	 public final static String SELECT_ALL_EVENTINFO_FOR_DATE="select * from eventinfo where eventDate=?";
	 public final static String SELECT_ALL_EVENTINFO="select * from eventinfo where eventDate=?";
	 public final static String SELECT_ALL_EVENTINFO_BY_ID="select * from eventinfo where eventId=? ";
	 public final static String SELECT_EVENTDETAILS_OF_SELECTED_EVENT="select venueMap.locationId,mapName,"+
	        "type,floorName,hallNo,eventLocationId,locationName,description  from venueMap,eventLocation  "+
            " where venueMap.locationId=eventLocation.locationId and eventLocationId=?";
	 
	 public final static String SELECT_ALL_APPOINTMENT_SCHEDULE1="select Exhibitors.exhibitorId,Exhibitors.exhibitorName,"+
                                                       "date,Appointment.startTime,Appointment.endTime,status,type,Appointment.eventId,"+
			                  "eventName from Exhibitors,Appointment ,eventInfo where  "+ 
    		                 " Appointment.exhibitorId=Exhibitors.exhibitorId or  Appointment.eventId=eventInfo.eventId";
	 
	 public final static String DELETE_APPOINTMENT_BY_ID="appointmentId=?";
		public final static String SELECT_ALL_APPOINTMENT_SCHEDULE_EVENT="select eventinfo.eventId,appointmentId,eventName,type,"+
		        "appointment.date,appointment.startTime,appointment.endTime,status from appointment,eventInfo "+
				" where eventinfo.eventId=appointment.eventId";
		public final static String SELECT_ALL_APPOINTMENT_SCHEDULE_EXHIBITOR="select exhibitors.exhibitorId,appointmentId,companyName," +
				"appointment.type,appointment.date,appointment.startTime,appointment.endTime,appointment.status,hallNo,standNo " +
				"  from appointment,exhibitors,company,venuemap,exhibitorLocation " +
				" where exhibitors.exhibitorId=appointment.exhibitorId and company.companyId=exhibitors.companyId " +
				"and venuemap.locationId=exhibitorLocation.locationId and exhibitors.exhibitorId=exhibitorLocation.exhibitorId ";
	 //selecting venue map name by product id
	 public final static String SELECT_ALL_EXIBITOR_LOCATION_OF_A_PRODUCT_ID="select mapName from venuemap where locationId in (select locationId from exhibitorlocation where exhibitorId in " +
	 		"(SELECT exhibitorId FROM exhibitors where exhibitorId in (select exhibitorId from ExhibitorProducts where productId=?)))";
	 
	 public final static String VALIDATE_APPOINTMENT="select * from appointment "+
	                                            " where date=? and strftime('%H:%M',startTime) "+
			                                   "  not between  strftime('%H:%M',?) And  strftime('%H:%M',?)";
	
	     //Search by hall no.
		 public final static String SEARCH_EXHIBITORS_BY_HALL_NO="select exhibitors.exhibitorId,companyName,venueMap.hallNo,standNo,venueMap.type "+
                                    "from exhibitors,venueMap,exhibitorLocation,company  where "+ 
                                    "exhibitors.exhibitorId=exhibitorLocation.exhibitorId "+
                                    "and venueMap.locationId=exhibitorLocation.locationId  "+
                                    "and company.companyId=exhibitors.companyId and hallNo like ?"+
                                    "ORDER BY standNo ASC";
		 public final static String SEARCH_EXHIBITORS_BY_CITY="select exhibitors.exhibitorId,companyName,venueMap.hallNo,standNo,venueMap.type "+
                 "from exhibitors,venueMap,exhibitorLocation,company  where "+ 
                 "exhibitors.exhibitorId=exhibitorLocation.exhibitorId "+
                 "and venueMap.locationId=exhibitorLocation.locationId  and exhibitors.exhibitorId in(select exhibitorlocation.exhibitorId from exhibitorlocation where "+
                 "exhibitorlocation.locationId in(select venuemap.locationId from venuemap where year=2014 )) " +
                 "and company.companyId=exhibitors.companyId and company.city like ?"+
                 "ORDER BY companyName ASC";
		 public final static String SELECT_ALL_HALL_NO="select DISTINCT hallNo from venuemap where year=2014 and venuemap.hallNo!='0'";
		 public final static String  SELECT_ALL_LANGUAGE ="select langName from language";
		 public final static String  SEARCH_EXHIBITORS_BY_LANGUAGE ="select exhibitorId,exhibitorName from exhibitors where exhibitorId in (select exhibitorId from exhibitorlang where langId in (select langId from language where langName like ?))";
		 public final static String  SELECT_ALL_EVENTNAME ="select eventName from eventinfo";
		 public final static String  SELECT_ALL_HALL_NAME="select mapName from venuemap";
		 public final static String  SELECT_EXHIBITOR_EVENT_NAME_WISE="select exhibitorId,exhibitorName from exhibitors where exhibitorId in (select exhibitorId from exhibitorlocation where locationId in(select locationId from eventlocation where eventLocationId in(select eventLocationId " +
		 		"from eventinfo where  eventName like ?)))";
		 public final static String  SEARCH_EXHIBITORS_HALL_NAME_WISE="select exhibitorId,exhibitorName from exhibitors where exhibitorId in(select exhibitorId from exhibitorlocation where locationid in(select locationId from venuemap where mapName like ?))";
//Searching location along with above search
		 public final static String SEARCH_LOCATION_BY_EXHIBITORS_NAME="select mapName from venuemap where locationId in(select locationId from exhibitorlocation where exhibitorId in(select exhibitorId from exhibitors where exhibitorName like ?))";
         
		 
		 public final static String SELECT_ALL_EXHIBITOR_OF_A_PRODUCT_WITH_LOCATION="select exhibitors.exhibitorId,exhibitors.exhibitorName,venueMap.hallNo "+
		 " from exhibitors,venueMap,exhibitorLocation  where exhibitors.exhibitorId=exhibitorLocation.exhibitorId "+
				 " and venueMap.locationId=exhibitorLocation.locationId  and exhibitors.exhibitorId in "+
		 " (select exhibitorId from exhibitorproducts,products where exhibitorproducts.productId=products.productId and products.productId=?)";
		 public final static String SELECT_ALL_EXHIBITOR_OF_A_PRODUCT_NAME_WITH_LOCATION="select exhibitors.exhibitorId,exhibitors.exhibitorName,venueMap.hallNo "+
				 " from exhibitors,venueMap,exhibitorLocation  where exhibitors.exhibitorId=exhibitorLocation.exhibitorId "+
						 " and venueMap.locationId=exhibitorLocation.locationId  and exhibitors.exhibitorId in "+
				 " (select exhibitorId from exhibitorproducts,products where exhibitorproducts.productId=products.productId and products.productName like ?)";
         public final static String SELECT_ALL_EXHIBITOR_BY_PRODUCTNAME_WITH_LOCATION="select exhibitors.exhibitorId,companyName,venueMap.hallNo,standNo,venueMap.type "+
		 " from exhibitors,venueMap,exhibitorLocation,company  where exhibitors.exhibitorId=exhibitorLocation.exhibitorId "+
				 " and venueMap.locationId=exhibitorLocation.locationId and venueMap.year=2014 and exhibitors.exhibitorId in "+
		 " (select exhibitorId from exhibitorproducts,products where exhibitorproducts.productId=products.productId and products.productName like ?) " +
		 "and company.companyId=exhibitors.companyId ORDER BY companyName";

         public final static String SELECT_NAMEWISE_EXIBITORS_SEARCH_WITH_LOCATION="select exhibitors.exhibitorId,companyName,venueMap.hallNo,standNo,venueMap.type "+  
        			  "from exhibitors,venueMap,exhibitorLocation,company  where exhibitors.exhibitorId=exhibitorLocation.exhibitorId " +
  				   "and venueMap.locationId=exhibitorLocation.locationId and company.companyId=exhibitors.companyId and venueMap.year=2014 and companyName like  ? order by companyName" ;
         public final static String  SEARCH_EXHIBITORS_BY_LANGUAGE_WITH_LOCATION="select exhibitors.exhibitorId,companyName,venueMap.hallNo   from "+
  				  " exhibitors,venueMap,exhibitorLocation,company  where exhibitors.exhibitorId=exhibitorLocation.exhibitorId "+
        		 " and venueMap.locationId=exhibitorLocation.locationId  and exhibitors.exhibitorId in "+
  				  " (select exhibitorId from language,exhibitorLang  where exhibitorLang.langId=language.langId and langName  like  ?)" +
  				  "and company.companyId=exhibitors.companyId";
		  public final static String SEARCH_EVENT_BY_NAME_WITH_LOCATION="select eventInfo.eventId,eventInfo.eventLocationId,eventInfo.eventName,"+
  				  " eventInfo.startTime,eventInfo.endTime,eventInfo.eventDate ,venueMap.hallNo from "+
				  " eventInfo,eventLocation,venueMap where eventInfo.eventLocationId=eventLocation.eventLocationId "+
  				  " and eventLocation.locationId=venueMap.locationId and eventInfo.eventName like ?";
  		  public final static String SEARCH_EVENT_BY_HALL_NO="select eventInfo.eventId,eventInfo.eventLocationId,"+
  				  " eventInfo.eventName,eventInfo.startTime,eventInfo.endTime,eventInfo.eventDate ,"+
  				  " venueMap.hallNo from eventInfo,eventLocation,venueMap where "+
  				  " eventInfo.eventLocationId=eventLocation.eventLocationId and eventLocation.locationId=venueMap.locationId and venueMap.hallNo like ? ";
         public final static String SELECT_ALL_EVENT_INFO_BY_EVENTID="SELECT eventId,eventName,eventDate,startTime,endTime,eventInfo.description,"+
  				  "eventLocation.eventLocationId,locationName,eventLocation.description,xLocation,yLocation,venueMap.locationId,hallNo,filePath "+
        		 " from eventInfo,eventLocation,venueMap where eventInfo.eventLocationId=eventLocation.eventLocationId and venueMap.locationId=eventLocation.locationId and eventId=?";
         public final static String SEARCH_BY_AUTHOR_OR_PUBLISHERS="select eventInfo.eventId,eventInfo.eventLocationId,eventInfo.eventName,"+
 				  " eventInfo.startTime,eventInfo.endTime,eventInfo.eventDate ,venueMap.hallNo from "+
				  " eventInfo,eventLocation,venueMap where eventInfo.eventLocationId=eventLocation.eventLocationId "+
 				  " and eventLocation.locationId=venueMap.locationId and (eventInfo.description like ? or eventInfo.eventName like ?)";
        
         /********************** Additions After Expo-2.0 version**********************/
         public final static String SELECT_ALL_EXHIBITOR_OF_A_PRODUCT_WITH_LOCATION_SORTED="select exhibitors.exhibitorId,companyName,venueMap.hallNo,standNo from "+ 
"exhibitors,venueMap,exhibitorLocation,company  where exhibitors.exhibitorId=exhibitorLocation.exhibitorId "+ 
"and venueMap.locationId=exhibitorLocation.locationId  and exhibitors.exhibitorId in "+ 
"(select exhibitorId from exhibitorproducts,products where exhibitorproducts.productId=products.productId and products.productId=? "+
"and exhibitors.category=1) and exhibitors.companyId=company.companyId";
         public final static String SELECT_ALL_EXHIBITOR_OF_A_PRODUCT_WITH_LOCATION_SORTED2="select exhibitors.exhibitorId,companyName,venueMap.hallNo,standNo from "+ 
        		 "exhibitors,venueMap,exhibitorLocation,company  where exhibitors.exhibitorId=exhibitorLocation.exhibitorId "+ 
        		 "and venueMap.locationId=exhibitorLocation.locationId  and exhibitors.exhibitorId in "+ 
        		 "(select exhibitorId from exhibitorproducts,products where exhibitorproducts.productId=products.productId and products.productId=? "+
        		 "and exhibitors.category !=1) and exhibitors.companyId=company.companyId";
         public final static String IS_TABLES_EMPTY="select*from company";
         public final static String IS_NEW_ITEM_PRODUCT="select*from products where productId=?";
         public final static String IS_NEW_ITEM_EXHIBITORS="select*from exhibitors where exhibitorId=?";
         public final static String IS_NEW_ITEM_LANGUAGE="select*from language where langId=?";
         public final static String IS_NEW_ITEM_EXHIBITOR_LANG="select*from exhibitorlang where langId=? and exhibitorId=?";
         public final static String IS_NEW_ITEM_VENUE_MAP="select*from venuemap where locationId=?";
         public final static String IS_NEW_ITEM_EXHIBITOR_LOCATION="select*from exhibitorlocation where exhibitorLocationId=?";
         public final static String IS_NEW_ITEM_EVENT="select*from event where event_loc_id=?";
         public final static String IS_NEW_ITEM_EVENT_INFO="select*from eventInfo where eventId=?";
         public final static String IS_NEW_ITEM_EVENT_LOCATION="select*from eventlocation where eventLocationId=?";
         public final static String IS_NEW_ITEM_EXIBITOR_PRODUCT="select*from exhibitorproducts where productId=? and exhibitorId=?";
         public final static String IS_NEW_ITEM_PRODUCT_PHOTOS="select*from productphotos where productPhotoId=?";
         public final static String IS_NEW_ITEM_COMPANY="select*from company where companyId=?";
         public final static String IS_NEW_ITEM_RESOURCE="select*from resource where locationId=?";
         public final static String IS_NEW_ITEM_FILESETTING="select*from file_setting where id=?";
         public final static String GET_ALL_LAYOUT_IMAGE="select DISTINCT filePath from venuemap";
         public final static String GET_ALL_LAYOUT_IMAGE2015="select DISTINCT filePath from venuemap where year=2015";
         
         public final static String IS_NEW_ITEM_EXHIBITORENTRYEXIT="select*from ExhibitorEntryExit where exhibitorLocationId=?";
         public final static String IS_NEW_ITEM_FACILITYINFO="select*from FacilityInformation where facilityInfoId=?";
         public final static String IS_NEW_ITEM_FACILITYEXITENTRY="select*from facilityEntryExit where id=?";
        
         public final static String SELECT_SOURCE_DEST_DETAILS_EXB="SELECT exhibitors.exhibitorId, "+
                                                 "venuemap.type , floorName, exhibitorLocation.standNo, companyName, companyDescription, address1, address2, " +
                                                 "city, state, company.country, pinCode, contactPerson, mobileNo, exhibitors.contactNo, fax, exhibitors.email, website, hallNo, " +
                                                 "filePath, xLocation, yLocation,exhibitorLocationId,venuemap.locationId FROM venuemap, exhibitorLocation, exhibitors, company " +
                                                 "WHERE venuemap.locationId = exhibitorLocation.locationId AND exhibitors.exhibitorId = exhibitorLocation.exhibitorId " +
                                                 "AND exhibitors.companyId = company.companyId AND exhibitors.exhibitorId =? AND exhibitorLocation.standNo=?";
          
         
         
         public final static String SELECT_EXHIBITOR_ENTRY_EXIT="SELECT  exhibitorLocationId,x,y,type from ExhibitorEntryExit where exhibitorLocationId in (?,?)";
         public final static String SELECT_ELEVATOR_LOCATION_OF_FLOOR="select locationId,locationName,x,y,type from FacilityInformation where locationId in (?) and type=?";
         
         public final static String SELECT_ALL_ALBUMS="SELECT  distinct  p.exhibitorId,c.companyName FROM photoShoot p,exhibitors e, company c  where  c.companyId=e.companyId and e.exhibitorId=p.exhibitorId";
         public final static String SELECT_ALL_PHOTO_PATH="Select * from photoShoot where exhibitorId=? and photopath like ?";
         public final static String SELECT_ALL_CITY="select  distinct city from company,exhibitors where company.companyId=exhibitors.companyId order by city";
         public final static String SELECT_ALL_HALLNO="select  distinct hallNo from venueMap where year='2014'";

      // public final static String MY_FAVORITES="select exhibitorId from myfavorites";
         public final static String MY_FAVORITES_EXHI_ID="select * from favourite where exhibitorId=?";
        
         //ORDER BY
         public final static String FAVORITES_DATA="select exhibitors.exhibitorId,companyName,hallNo,standNo  from favourite,exhibitors," +
         		"company,venuemap,exhibitorLocation  where exhibitors.exhibitorId=favourite.exhibitorId " +
         		"and company.companyId=exhibitors.companyId  and venuemap.locationId=exhibitorLocation.locationId " +
         		"and exhibitors.exhibitorId=exhibitorLocation.exhibitorId ORDER BY companyName";
         //ORDER BY ADDED TO VENUE MAP
         public final static String VENUEMAP_INFO_BYTYPE="SELECT  locationId,mapName,type,floorName,hallNo,filePath FROM venuemap where type like ?";
         
         public final static String GET_VENUE_MAP_INFO_BY_LOCATION_ID="SELECT * FROM venuemap WHERE locationId=?";

         public final static String PRODUCT_SEARCH="select  productId, productName,parentId from products where productName like  ? ORDER BY productName";
         public final static String SEARCH_EXHIBITOR_CATEGORY_WISE="select exhibitors.exhibitorId,companyName,venueMap.hallNo,standNo," +
					         		"venueMap.type from exhibitors,venueMap,exhibitorLocation,company  where exhibitors.exhibitorId=exhibitorLocation.exhibitorId  and " +
					         		"venueMap.locationId=exhibitorLocation.locationId and company.companyId=exhibitors.companyId and venueMap.year=2014 and exhibitors.category=?" +
					         		"ORDER BY companyName";


         public final static String SELECT_ENTRY_EXIT_BY_LOCATIONID="select * from FacilityInformation where locationId=? and type=?";
         public final static String SELECT_ENTRY_EXIT_BY_LOCATIONID_AND_NAME="select * from FacilityInformation where locationId=? and type like ? and locationName like ? ORDER BY locationName";

         public final static String IS_EXHIBITOR_LOCATION_WITH_ENTRYEXIT_EXIST="SELECT el.exhibitorLocationId FROM exhibitorLocation el,ExhibitorEntryExit ex where "+
                                                " el.exhibitorLocationId=ex.exhibitorLocationId and el.exhibitorId=? and standNo=? and xLocation !='0' and x!='0'";
         
         public final static String GET_FILE_NAME_FROM_VENU_MAP="select filePath from venuemap where type =?";

         public final static String SELECT_ALL_FROM_VISITOR_QUESTIONNAIRE="SELECT * FROM visitor_questionnaire ORDER BY orderId";
         public final static String SELECT_QUE_OPTION_QUESTION_ID_WISE="SELECT * FROM visitor_que_option WHERE questionId=? ORDER BY orderId";
         
         public final static String SELECT_LINK_URL_FROM_LINK_BY_LINK_NAME="Select linkUrl from links where linkName=?";
        		 															///"SELECT linkUrl FROM links WHERE trim(linkName)=?";
         
         public final static String GET_HASHCODE_AND_ADURL_PLACEMENT_ID_WISE="select hashCode, url from adServe where placementId=?";
         
         public final static String GET_ALL_AD_URL="SELECT * FROM adServe WHERE status = 1";
         
/**INSERTION QUERIES**/	         
        public final static String INSERT_INTO_PRODUCTS= "INSERT OR REPLACE INTO products(productId, productName, productType, productBrand,"+
     			" description, creationDate, updatedBy, status)VALUES(?,?,?,?,?,?,?,?)";
        
     	public static final String INSERT_INTO_EXHIBITORS = "INSERT OR REPLACE INTO exhibitors(pkId, exhibitorId, contactPerson, email, "
     			+ "contactNo, mobileNo, fax, creationDate, updatedBy, category, companyId, country, type, status, refNo)" 
     			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_LANGUAGE	= "INSERT OR REPLACE INTO language(langId, langName, creationDate, updatedBy, status)"
     				+ "VALUES(?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_EXHIBITOR_LANG = "INSERT OR REPLACE INTO exhibitorLang(langId, exhibitorId, creationDate, "+
     				"updatedBy, status)VALUES(?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_VENUE_MAP = "INSERT OR REPLACE INTO venuemap(locationId, mapName, type, floorName, hallNo, filePath, "
     				+ "creationDate, updatedBy, productId, year, status, isUpdated)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_EXHIBITOR_LOCATION = "INSERT OR REPLACE INTO exhibitorLocation(exhibitorLocationId, locationId, exhibitorId, "
     				+ "standNo, xLocation, yLocation, creationDate, updatedBy, status)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_EVENT = "INSERT OR REPLACE INTO event(event_loc_id, eventLocationName, eventlocationDescription, "
     				+ "creationDate, updatedBy, status)VALUES(?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_EVENT_INFO = "INSERT OR REPLACE INTO eventInfo(eventId, eventLocationId, eventName, eventDate, "
     				+ "startTime, endTime, description, creationDate, updatedBy, moderator, panelists, "
     				+ "eventType, status)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_EVENT_LOCATION = "INSERT OR REPLACE INTO eventLocation(eventLocationId, eventLocationId, locationId, "
     				+ "locationName, description, xLocation, yLocation, creationDate, updatedBy, event_loc_id, "
     				+ "status)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_EXHIBITOR_PRODUCTS = "INSERT OR REPLACE INTO ExhibitorProducts(productId, exhibitorId, creationDate, " 
     				+ "updatedBy, status)VALUES(?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_PRODUCT_PHOTO = "INSERT OR REPLACE INTO ProductPhotos(productPhotoId, productId, exhibitorId, filePath, "
     				+ "fileName, creationDate, updatedBy, status)VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_COMPANY = "INSERT OR REPLACE INTO company(companyId, companyName, companyDescription, address1, "
     				+ "address2, city, state, pinCode, country, website, email, contactNo, creationDate, updatedBy, "
     				+ "panNumber, status,number_of_persons_accompanying)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_RESOURCE = "INSERT OR REPLACE INTO resource(locationId, resource, type, creationDate, updatedBy, "
     				+ "status)VALUES(?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_FILE_SETTING = "INSERT OR REPLACE INTO file_setting(id, fileName, filePath, creationDate,"
     				+ "updatedBy, status)VALUES(?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_EXHIBITOR_ENTRY_EXIT	= "INSERT OR REPLACE INTO ExhibitorEntryExit(id,exhibitorLocationId, x, y, type, creationDate, "
     				+ "updatedBy, status)VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_FACILITY_INFORMATION	= "INSERT OR REPLACE INTO FacilityInformation(facilityInfoId, x, y, locationId, locationName, "
     				+ "type, creationDate, updatedBy, status)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_FACILITY_ENTRY_EXIT = "INSERT OR REPLACE INTO facilityEntryExit(id, facilityInfoId, x, y, creationDate, updatedBy, "
     				+ "status)VALUES(?, ?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_LINKS = "INSERT OR REPLACE INTO links(linkId, linkUrl, linkName, status, creationDate, "
     				+ "updatedBy)VALUES(?, ?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_LINKS_CATEGORIES = "INSERT OR REPLACE INTO link_category(linkTypeId, linkTypeName, status, creationDate, "
     				+ "updatedBy)VALUES(?, ?, ?, ?, ?)";
     	
     	public static final String INSERT_INTO_USERS="INSERT OR REPLACE INTO users(userId,roleId,loginEmail," 
     				+ "password,secretQuestion,secretAnswer,lastLoggedIn,creationDate,updatedBy" 
     			    + "status)VALUES(?,?,?,?,?,?,?,?,?,?)";
     	
     	public static final String INSERT_INTO_ROLES="INSERT OR REPLACE INTO roles(roleId,roleName,roleDetails,creationDate,updatedBy,status)"
     				+ "VALUES(?,?,?,?,?,?)";
     	
     	public static final String INSERT_INTO_APPONITMENT="INSERT OR REPLACE INTO appointment(visitorId,exhibitorId,date,startTime,endTime,status,creationDate," 
     				+ "updatedBy)VALUES(?,?,?,?,?,?,?,?)";
     	
     	public static final String INSERT_INTO_EXHIBITOR_CATEGORY="INSERT OR REPLACE INTO exhibitorCategory(categoryId,categoryName,status,creationDate,updatedBy)"
     				+ "VALUES(?,?,?,?,?)";
     	
     	public static final String INSERT_INTO_ADMIN="INSERT OR REPLACE INTO admin(adminId,adminName,employeeId,contactNo,creationDate,updatedBy,status)"
     				+ "VALUES(?,?,?,?,?,?,?)";

     	public static final String INSERT_INTO_VISITOR_QUESTIONNAIRE="INSERT OR REPLACE INTO visitor_questionnaire(questionId,questionType,question,orderId,userType,"
     				+"status,creationDate,updatedBy)VALUES(?,?,?,?,?,?,?,?)";
     	
     	public static final String INSERT_INTO_VISITOR_QUE_OPTION="INSERT OR REPLACE INTO visitor_que_option(optionId,questionId,option,orderId,status,creationDate,"
     				+ "updatedBy)VALUES(?,?,?,?,?,?,?)";
     	
     	public static final String MART_FACILITIES="SELECT * FROM mart_facilities";
     	
     	public static final String MART_FACILITIE_DETAILS="SELECT * FROM FacilityInformation where type = ?";
     	
     	public static final String GET_FACILITY_BY_LOCATION_NAME_ND_TYPE="SELECT * FROM FacilityInformation where locationId=? AND locationName=? AND type=?";
     	
     	public static final String GET_EXHIBITOR_ENTRY_BY_EXHIBITOR="SELECT * FROM ExhibitorEntryExit WHERE exhibitorLocationId=(SELECT exhibitorLocationId FROM exhibitorLocation WHERE exhibitorId=? AND locationId=?)";
     	
     	public static final String GET_ALL_VENUEMAP_UDATES="SELECT * FROM venuemap WHERE isUpdated=?";
}
