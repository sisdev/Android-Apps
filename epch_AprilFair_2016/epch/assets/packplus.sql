CREATE TABLE "admin" (
  "pkId" int(11)  NOT NULL ,
  "adminId" int(11)  NOT NULL,
  "adminName" varchar(50) DEFAULT NULL,
  "employeeId" varchar(20) DEFAULT NULL,
  "contactNo" varchar(20) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("pkId")
  CONSTRAINT "admin_ibfk_1" FOREIGN KEY ("adminId") REFERENCES "users" ("userId") ON DELETE CASCADE ON UPDATE NO ACTION
);
/
CREATE TABLE "appointment" (
  "appointmentId"  integer primary key autoincrement not null ,
  "visitorId" int(11)  DEFAULT NULL,
  "exhibitorId" int(11)  DEFAULT NULL,
  "eventId" int(11)  DEFAULT NULL,
  "type" int(2) DEFAULT NULL,
  "date" varchar(20) DEFAULT NULL,
  "startTime" varchar(20) DEFAULT NULL,
  "endTime" varchar(20) DEFAULT NULL,
  "status" varchar(20) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL
  
);
;
/
CREATE TABLE "company" (
  "companyId" int(11)  NOT NULL ,
  "companyName" varchar(150) DEFAULT NULL,
  "companyDescription" varchar(1000) DEFAULT NULL,
  "address1" varchar(80) DEFAULT NULL,
  "address2" varchar(80) DEFAULT NULL,
  "city" varchar(20) DEFAULT NULL,
  "state" varchar(20) DEFAULT NULL,
  "pinCode" varchar(10) DEFAULT NULL,
  "country" varchar(20) DEFAULT NULL,
  "website" varchar(50) DEFAULT NULL,
  "email" varchar(50) DEFAULT NULL,
  "contactNo" varchar(20) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  "panNumber" text,
  PRIMARY KEY ("companyId")
);
;
/
CREATE TABLE "elect_gcm" (
  "id" int(11) NOT NULL ,
  "gcm_registration_id" text NOT NULL,
  "name" varchar(50) NOT NULL,
  "email" varchar(80) NOT NULL,
  "creationDate" timestamp NOT NULL ,
  PRIMARY KEY ("id")
);
;
/
CREATE TABLE "elect_report" (
  "id" int(11)  NOT NULL ,
  "msg_category" varchar(50) DEFAULT NULL,
  "msg_provider" varchar(50) DEFAULT NULL,
  "message" text,
  "total_sent" varchar(15) DEFAULT NULL,
  "success" varchar(15) DEFAULT NULL,
  "failures" varchar(15) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("id")
);
;
/
CREATE TABLE "event" (
  "event_loc_id" int(11) NOT NULL ,
  "eventLocationName" text,
  "eventlocationDescription" text,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("event_loc_id")
);
;
/
CREATE TABLE "eventInfo" (
  "eventId" int(11)  NOT NULL ,
  "eventLocationId" int(11)  NOT NULL,
  "eventName" varchar(50) DEFAULT NULL,
  "eventDate" date DEFAULT '0000-00-00',
  "startTime" time DEFAULT '00:00:00',
  "endTime" time DEFAULT '00:00:00',
  "description" varchar(100) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  "moderator" text,"panelists" text DEFAULT (null),
  "eventType" text,
  PRIMARY KEY ("eventId")
  CONSTRAINT "fk_eventLocationId" FOREIGN KEY ("eventLocationId") REFERENCES "eventLocation" ("eventLocationId")
);
;
/
CREATE TABLE "eventLocation" (
  "eventLocationId" int(11)  NOT NULL ,
  "locationId" int(11)  NOT NULL,
  "locationName" varchar(100) DEFAULT NULL,
  "description" varchar(100) DEFAULT NULL,
  "xLocation" varchar(50) DEFAULT NULL,
  "ylocation" varchar(50) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  "event_loc_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("eventLocationId")
  CONSTRAINT "eventLocation_ibfk_1" FOREIGN KEY ("event_loc_id") REFERENCES "event" ("event_loc_id"),
  CONSTRAINT "fk_location_id" FOREIGN KEY ("locationId") REFERENCES "venuemap" ("locationId")
);
;
/
CREATE TABLE "exhibitorLang" (
  "langId" int(11)  NOT NULL,
  "exhibitorId" int(11)  NOT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("langId","exhibitorId")
  CONSTRAINT "fk_exhibitorId" FOREIGN KEY ("exhibitorId") REFERENCES "exhibitors" ("exhibitorId"),
  CONSTRAINT "fk_langId" FOREIGN KEY ("langId") REFERENCES "language" ("langId")
);
;
/
CREATE TABLE "exhibitorLocation" (
  "exhibitorLocationId" int(11) NOT NULL ,
  "locationId" int(11)  NOT NULL,
  "exhibitorId" int(11)  NOT NULL,
  "standNo" varchar(20) DEFAULT NULL,
  "xLocation" varchar(10) DEFAULT NULL,
  "yLocation" varchar(10) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("exhibitorLocationId")
  CONSTRAINT "exhibitorLocation_ibfk_1" FOREIGN KEY ("locationId") REFERENCES "venuemap" ("locationId") ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT "exhibitorLocation_ibfk_2" FOREIGN KEY ("exhibitorId") REFERENCES "exhibitors" ("exhibitorId") ON DELETE CASCADE ON UPDATE NO ACTION
);
;
/
CREATE TABLE "exhibitorproducts" (
  "productId" int(11)  NOT NULL DEFAULT '0',
  "exhibitorId" int(11)  NOT NULL DEFAULT '0',
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("productId","exhibitorId")
  CONSTRAINT "exhibitorproducts_ibfk_1" FOREIGN KEY ("productId") REFERENCES "products" ("productId") ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT "exhibitorproducts_ibfk_2" FOREIGN KEY ("exhibitorId") REFERENCES "exhibitors" ("exhibitorId") ON DELETE CASCADE ON UPDATE NO ACTION
);
;
/
CREATE TABLE "exhibitors" (
  "pkId" int(11)  NOT NULL ,
  "exhibitorId" int(11)  NOT NULL,
  "contactPerson" varchar(50) DEFAULT NULL,
  "email" varchar(50) DEFAULT NULL,
  "contactNo" varchar(20) DEFAULT NULL,
  "mobileNo" varchar(20) DEFAULT NULL,
  "fax" varchar(20) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  "category" int(2) DEFAULT '0',
  "country" text,
  "type"  text,
  "companyId" int(11)  DEFAULT NULL,
  PRIMARY KEY ("pkId")
  CONSTRAINT "exhibitors_ibfk_1" FOREIGN KEY ("exhibitorId") REFERENCES "users" ("userId") ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT "exhibitors_ibfk_2" FOREIGN KEY ("companyId") REFERENCES "company" ("companyId"),
  CONSTRAINT "exhibitors_ibfk_3" FOREIGN KEY ("companyId") REFERENCES "company" ("companyId")
);
;
/
CREATE TABLE "fair" (
  "fair_id" int(11) NOT NULL ,
  "fair_name" text NOT NULL,
  "organiser" varchar(100) NOT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("fair_id")
);
;
/
CREATE TABLE "fair_event" (
  "_id" int(11) NOT NULL ,
  "fair_execution_id" int(11) DEFAULT NULL,
  "eventId" int(11)  DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("_id")
  CONSTRAINT "fair_event_ibfk_1" FOREIGN KEY ("fair_execution_id") REFERENCES "fair_execution" ("fair_execution_id"),
  CONSTRAINT "fair_event_ibfk_2" FOREIGN KEY ("eventId") REFERENCES "eventInfo" ("eventId")
);
;
/
CREATE TABLE "fair_execution" (
  "fair_execution_id" int(11) NOT NULL ,
  "fair_id" int(11) DEFAULT NULL,
  "fair_city" text NOT NULL,
  "exhibitionDate" date DEFAULT NULL,
  "exhibitionDescription" text,
  "exhibitorEntryfee" float(10,2) DEFAULT NULL,
  "visitorEntryfee" float(10,2) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("fair_execution_id")
  CONSTRAINT "fair_execution_ibfk_1" FOREIGN KEY ("fair_id") REFERENCES "fair" ("fair_id")
);
;
/
CREATE TABLE "fair_product" (
  "_id" int(11) NOT NULL ,
  "fair_execution_id" int(11) DEFAULT NULL,
  "productId" int(11)  DEFAULT NULL,
  "exhibitorId" int(11)  DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("_id")
  CONSTRAINT "fair_product_ibfk_1" FOREIGN KEY ("fair_execution_id") REFERENCES "fair_execution" ("fair_execution_id"),
  CONSTRAINT "fair_product_ibfk_2" FOREIGN KEY ("productId") REFERENCES "products" ("productId"),
  CONSTRAINT "fair_product_ibfk_3" FOREIGN KEY ("exhibitorId") REFERENCES "exhibitors" ("exhibitorId")
);
;
/
CREATE TABLE "fair_users" (
  "_id" int(11) NOT NULL ,
  "fair_execution_id" int(11) DEFAULT NULL,
  "userId" int(11)  DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("_id")
  CONSTRAINT "fair_users_ibfk_1" FOREIGN KEY ("fair_execution_id") REFERENCES "fair_execution" ("fair_execution_id"),
  CONSTRAINT "fair_users_ibfk_2" FOREIGN KEY ("userId") REFERENCES "users" ("userId")
);
;
/
CREATE TABLE "fair_venuemap" (
  "_id" int(11) NOT NULL ,
  "fair_execution_id" int(11) DEFAULT NULL,
  "exhibitorId" int(11)  DEFAULT NULL,
  "locationId" int(11)  DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("_id")
  CONSTRAINT "fair_venuemap_ibfk_1" FOREIGN KEY ("fair_execution_id") REFERENCES "fair_execution" ("fair_execution_id"),
  CONSTRAINT "fair_venuemap_ibfk_2" FOREIGN KEY ("exhibitorId") REFERENCES "exhibitors" ("exhibitorId"),
  CONSTRAINT "fair_venuemap_ibfk_3" FOREIGN KEY ("locationId") REFERENCES "venuemap" ("locationId")
);
;
/
CREATE TABLE "gcm_report" (
  "id" int(11)  NOT NULL ,
  "msg_category" varchar(50) DEFAULT NULL,
  "msg_provider" varchar(50) DEFAULT NULL,
  "message" text,
  "total_sent" varchar(15) DEFAULT NULL,
  "success" varchar(15) DEFAULT NULL,
  "failures" varchar(15) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("id")
);
;
/
CREATE TABLE "gcm_users" (
  "id" int(11) NOT NULL ,
  "gcm_registration_id" text NOT NULL,
  "name" varchar(50) NOT NULL,
  "email" varchar(80) NOT NULL,
  "creationDate" timestamp NOT NULL ,
  PRIMARY KEY ("id")
);
;
/
CREATE TABLE "language" (
  "langId" int(11)  NOT NULL ,
  "langName" varchar(50) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("langId")
);
;
/
CREATE TABLE "productPhotos" (
  "productPhotoId" int(11)  NOT NULL ,
  "productId" int(11)  NOT NULL,
  "exhibitorId" int(11)  NOT NULL,
  "filePath" varchar(50) DEFAULT NULL,
  "fileName" varchar(250) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("productPhotoId")
  CONSTRAINT "productPhotos_ibfk_1" FOREIGN KEY ("productId") REFERENCES "products" ("productId") ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT "productPhotos_ibfk_2" FOREIGN KEY ("exhibitorId") REFERENCES "exhibitors" ("exhibitorId") ON DELETE CASCADE ON UPDATE NO ACTION
);
;
/
CREATE TABLE "products" (
  "productId" int(11)  NOT NULL ,
  "parentId" int(11)  NOT NULL DEFAULT '0',
  "productName" varchar(150) DEFAULT NULL,
  "productType" varchar(30) DEFAULT NULL,
  "productBrand" varchar(30) DEFAULT NULL,
  "description" varchar(150) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("productId")
);
;
/
CREATE TABLE "roles" (
  "roleId" int(11)  NOT NULL ,
  "roleName" varchar(25) DEFAULT NULL,
  "roleDetails" varchar(100) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("roleId")
);
;
/
CREATE TABLE "users" (
  "userId" int(11)  NOT NULL ,
  "roleId" int(11)  NOT NULL,
  "loginEmail" varchar(60) DEFAULT NULL,
  "password" varchar(80) DEFAULT NULL,
  "secretQuestion" varchar(100) DEFAULT NULL,
  "secretAnswer" varchar(50) DEFAULT NULL,
  "status" varchar(15) DEFAULT NULL,
  "lastLoggedIn" timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  "companyId" int(11)  DEFAULT NULL,
  PRIMARY KEY ("userId")
  CONSTRAINT "users_ibfk_1" FOREIGN KEY ("roleId") REFERENCES "roles" ("roleId") ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT "users_ibfk_2" FOREIGN KEY ("companyId") REFERENCES "company" ("companyId"),
  CONSTRAINT "users_ibfk_3" FOREIGN KEY ("companyId") REFERENCES "company" ("companyId")
);
;
/
CREATE TABLE "venuemap" (
  "locationId" int(11)  NOT NULL ,
  "mapName" varchar(150) DEFAULT NULL,
  "type" varchar(50) DEFAULT NULL,
  "floorName" varchar(50) DEFAULT NULL,
  "hallNo" varchar(30) DEFAULT NULL,
  "filePath" varchar(150) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(50) DEFAULT NULL,
  "productId" int(11)  DEFAULT NULL,
  "year" int(5),
  PRIMARY KEY ("locationId")
  CONSTRAINT "venuemap_ibfk_1" FOREIGN KEY ("productId") REFERENCES "products" ("productId"),
  CONSTRAINT "venuemap_ibfk_2" FOREIGN KEY ("productId") REFERENCES "products" ("productId"),
  CONSTRAINT "venuemap_ibfk_3" FOREIGN KEY ("productId") REFERENCES "products" ("productId")
);
;
/
CREATE TABLE "visitors" (
  "pkId" int(11)  NOT NULL ,
  "visitorId" int(11)  NOT NULL,
  "visitorName" varchar(50) DEFAULT NULL,
  "visitorPurpose" varchar(50) DEFAULT NULL,
  "visitorType" varchar(20) DEFAULT NULL,
  "visitorSex" varchar(10) DEFAULT NULL,
  "companyName" varchar(50) DEFAULT NULL,
  "address1" varchar(80) DEFAULT NULL,
  "address2" varchar(80) DEFAULT NULL,
  "city" varchar(20) DEFAULT NULL,
  "state" varchar(20) DEFAULT NULL,
  "pinCode" varchar(20) DEFAULT NULL,
  "country" varchar(20) DEFAULT NULL,
  "nationality" varchar(20) DEFAULT NULL,
  "website" varchar(50) DEFAULT NULL,
  "contactNo" varchar(20) DEFAULT NULL,
  "mobileNo" varchar(20) DEFAULT NULL,
  "creationDate" timestamp NOT NULL ,
  "updatedBy" varchar(20) DEFAULT NULL,
  PRIMARY KEY ("pkId")
  CONSTRAINT "visitors_ibfk_1" FOREIGN KEY ("visitorId") REFERENCES "users" ("userId") ON DELETE CASCADE ON UPDATE NO ACTION
);
;
/
CREATE TABLE "ExhibitorEntryExit" ("Id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "exhibitorLocationId" INTEGER, "x" TEXT, "y" TEXT, "status" TEXT, "creationDate" TEXT, "updatedBy" TEXT);
/
CREATE TABLE "FacilityInformation" ("facilityInfoId" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "locationId" TEXT, "locationName" TEXT, "x" TEXT, "y" TEXT, "status" TEXT,"creationDate" TEXT, "updatedBy" TEXT);
/
CREATE TABLE "facilityEntryExit" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,  "x" INTEGER, "y" INTEGER, "facilityInfoId" INTEGER, "creationDate" TEXT, "updatedBy" TEXT);
/
CREATE TABLE "feedback" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "emailId" TEXT, "user_interface" FLOAT, "data_accuracy" FLOAT, "performance" FLOAT, "easeOfuse" FLOAT, "remarks" TEXT, "creationDate" TEXT, "updatedBy" );
/
CREATE TABLE "photoShoot" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "photopath" TEXT, "exhibitorId" INTEGER, "visitorId" INTEGER, "productId" INTEGER, "notes" TEXT, "creationDate" TEXT, "updatedby" TEXT);
/
CREATE TABLE "favourite" ("id" INTEGER PRIMARY KEY  NOT NULL ,"exhibitorId" INTEGER,"visitorId" INTEGER, "creationDate" TEXT, "updatedBy" TEXT);
/
CREATE TABLE "resource" ("locationId" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "resource" TEXT, "type" TEXT,"creationDate" TEXT, "updatedBy" TEXT);
/
CREATE TABLE "file_setting" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "fileName" TEXT, "filePath" TEXT,"creationDate" TEXT, "updatedBy" TEXT);
/
CREATE INDEX "fair_execution_fair_id" ON "fair_execution" ("fair_id");
CREATE INDEX "fair_product_fair_execution_id" ON "fair_product" ("fair_execution_id");
CREATE INDEX "fair_product_productId" ON "fair_product" ("productId");
CREATE INDEX "fair_product_exhibitorId" ON "fair_product" ("exhibitorId");
CREATE INDEX "venuemap_productId" ON "venuemap" ("productId");
CREATE INDEX "fair_event_fair_execution_id" ON "fair_event" ("fair_execution_id");
CREATE INDEX "fair_event_eventId" ON "fair_event" ("eventId");
CREATE INDEX "exhibitorLocation_locationId" ON "exhibitorLocation" ("locationId");
CREATE INDEX "exhibitorLocation_exhibitorId" ON "exhibitorLocation" ("exhibitorId");
CREATE INDEX "exhibitorproducts_exhibitorId" ON "exhibitorproducts" ("exhibitorId");
CREATE INDEX "fair_venuemap_fair_execution_id" ON "fair_venuemap" ("fair_execution_id");
CREATE INDEX "fair_venuemap_exhibitorId" ON "fair_venuemap" ("exhibitorId");
CREATE INDEX "fair_venuemap_locationId" ON "fair_venuemap" ("locationId");
CREATE INDEX "eventLocation_fk_location_id" ON "eventLocation" ("locationId");
CREATE INDEX "eventLocation_event_loc_id" ON "eventLocation" ("event_loc_id");
CREATE INDEX "fair_users_fair_execution_id" ON "fair_users" ("fair_execution_id");
CREATE INDEX "fair_users_userId" ON "fair_users" ("userId");
CREATE INDEX "exhibitorLang_fk_exhibitorId" ON "exhibitorLang" ("exhibitorId");
CREATE INDEX "users_roleId" ON "users" ("roleId");
CREATE INDEX "users_companyId" ON "users" ("companyId");
CREATE INDEX "productPhotos_productId" ON "productPhotos" ("productId");
CREATE INDEX "productPhotos_exhibitorId" ON "productPhotos" ("exhibitorId");
CREATE INDEX "appointment_exhibitorId" ON "appointment" ("exhibitorId");
CREATE INDEX "appointment_visitorId" ON "appointment" ("visitorId");
CREATE INDEX "appointment_fk_eventId" ON "appointment" ("eventId");
CREATE INDEX "admin_adminId" ON "admin" ("adminId");
CREATE INDEX "visitors_visitorId" ON "visitors" ("visitorId");
CREATE INDEX "exhibitors_exhibitorId" ON "exhibitors" ("exhibitorId");
CREATE INDEX "exhibitors_companyId" ON "exhibitors" ("companyId");
CREATE INDEX "eventInfo_fk_eventLocationId" ON "eventInfo" ("eventLocationId");
