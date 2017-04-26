package casaneeds.entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import casaneeds.taskactivity.UpdateServerAsync;
import casaneeds.utils.LocationTracker;

/**
 * Created by Sunita on 31-May-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME = "MyTask";
    private static final int DATABASE_VERSION = 1;

    public static final String 	TASK_TABLE="MyTask_Table";
    public static final String TASKID="_id";
    public static final String ORDERNO="OrderNo";
    public static final String NAME="Name";
    public static final String ADDRESS="Address";
    public static final String AREA="Area";
    public static final String AMOUNT = "Amount";   // Billed Amount
    public static final String PAYMENT_STATUS = "pymt_status";  // received from server - Paid-1/Unpaid-0
    public static final String ORDER_COMPLETION_DATE = "ord_comp_dt" ;
    public static final String STATUS = "Status";      // Received, In Process, Closed
    public static final String VOUCHERCODE = "Voucher";
    public static final String RECEIPT_AMOUNT_CASH = "receipt_amount_cash";
    public static final String RECEIPT_AMOUNT_CC = "receipt_amount_cc";
    public static final String RECEIPT_AMOUNT_VOUCHER = "receipt_amount_voucher";
    public static final String PACKING_STATUS = "packing_status";



    public static final String 	ITEMDETAILS_TABLE="ItemDetails_Table";
    public static final String ITEMID="_id";
    public static final String ORDER_NO="OrderNo";
    public static final String ITEMNo="Itemid";
    public static final String ITEMNAME="Itemname";
    public static final String ITEMQTY="Itemqty";
    public static final String ITEMFQTY="FullFillqty";
    public static final String ITEMPRICE="Price";


    SQLiteDatabase db;
    Context app_ctx ;

    private static final String CREATE_TABLE = "CREATE TABLE " + TASK_TABLE +
            " (" + TASKID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ORDERNO + " TEXT NOT NULL, " +
            NAME + " TEXT NOT NULL, " + ADDRESS + " TEXT NOT NULL, " + AREA + " TEXT NOT NULL, " +
            AMOUNT + " TEXT NOT NULL, "+ PAYMENT_STATUS + " TEXT, "  +ORDER_COMPLETION_DATE+ " TEXT, "+
            STATUS + " TEXT NOT NULL, "+RECEIPT_AMOUNT_CASH + " REAL, "+RECEIPT_AMOUNT_CC + " REAL, "+
            RECEIPT_AMOUNT_VOUCHER + " REAL, "+PACKING_STATUS + " TEXT, "+ VOUCHERCODE + " TEXT);";

    private static final String CREATE_ITEMDETAILSTABLE = "CREATE TABLE " + ITEMDETAILS_TABLE +
            " (" + ITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ORDER_NO + " TEXT NOT NULL, " +ITEMNo + " TEXT NOT NULL, " +
            ITEMNAME + " TEXT NOT NULL, " + ITEMQTY + " TEXT NOT NULL, "+ ITEMFQTY + " TEXT NOT NULL, "+ ITEMPRICE + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.app_ctx = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("INFORMATION", "DataBase Created");
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_ITEMDETAILSTABLE);
        Log.d("INFORMATION", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ITEMDETAILS_TABLE);
        // Create tables again
        onCreate(db);
    }

    //  Insert MyTask

    public void addTask(Order od)
    {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TASKID, od.getTaskNo());
        values.put(ORDERNO, od.getOrderNo());
        values.put(NAME, od.getCustName());
        values.put(ADDRESS, od.getCustAddress());
        values.put(AREA, od.getCustArea());
        values.put(AMOUNT, od.getTotalAmount());
        values.put(STATUS, od.getStatus());
        values.put(PAYMENT_STATUS, od.getPayment());

        db.insert(TASK_TABLE, null, values);
        Log.d("INFORMATION", "Task Added");

        db.close();
    }

    public void addOrupdate(Order ord1)
    {
        int exists = 0 ;
        db = this.getWritableDatabase();
        String qry1 = "Select count(*) from "+TASK_TABLE+" where "+ ORDERNO +"="+ord1.getOrderNo() ;
        Cursor c1 = db.rawQuery(qry1, null);
        if (c1.moveToFirst())
        {
            exists = c1.getInt(0);
        }
        if (exists == 0)
        {
            addTask(ord1);
        }
        else
        {
            ContentValues values = new ContentValues();
//            values.put(TASKID, ord1.getTaskNo());
//            values.put(ORDERNO, ord1.getOrderNo());
            values.put(NAME, ord1.getCustName());
            values.put(ADDRESS, ord1.getCustAddress());
            values.put(AREA, ord1.getCustArea());
            values.put(AMOUNT, ord1.getTotalAmount());
            values.put(STATUS, ord1.getStatus());
            values.put(PAYMENT_STATUS, ord1.getPayment());

            String[] whereVal = {String.valueOf(ord1.getOrderNo()) };
            db.update(TASK_TABLE, values,ORDERNO+"=?",whereVal);
        }

    }

    public void updateOrderStatusClosed(int od_no)
    {
        db = this.getWritableDatabase();
        Date d1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str_dt = sdf.format(d1);
        Log.d("DatabaseHelper", "Order Completion Date:"+str_dt);
        ContentValues values = new ContentValues();
        values.put(STATUS, "Closed");
        values.put(ORDER_COMPLETION_DATE,str_dt);
        String[] whereVal = {String.valueOf(od_no) };
        db.update(TASK_TABLE, values, ORDERNO + "=?", whereVal);
    }

    public void updateVoucherCode(int od_no, String voucher_txt)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VOUCHERCODE, voucher_txt);
        String[] whereVal = {String.valueOf(od_no) };
        db.update(TASK_TABLE, values, ORDERNO + "=?", whereVal);
        Log.d("DbHelper:InsertVoucher", "Order No is" + od_no + ":voucher code:" + voucher_txt);
        db.close();
    }

    public void updatePackingStatus(int od_no, String pack_status)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PACKING_STATUS, pack_status);
        String[] whereVal = {String.valueOf(od_no) };
        db.update(TASK_TABLE, values, ORDERNO + "=?", whereVal);
        Log.d("DbHelper:updatePacking", "Order No is:" + od_no + ":Packing Status Completed");
        db.close();
    }


    public void updateReceiptAmount(int od_no, float amount_cash, float amount_cc, float amount_voucher)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECEIPT_AMOUNT_CASH,amount_cash);
        values.put(RECEIPT_AMOUNT_CC,amount_cc);
        values.put(RECEIPT_AMOUNT_VOUCHER, amount_voucher);
        String[] whereVal = {String.valueOf(od_no)};
        db.update(TASK_TABLE, values, ORDERNO + "=?", whereVal);
        db.close();
    }

    public Cursor getVoucherCode(int od_no)
    {
        db = this.getWritableDatabase();
        String qry1 = "Select "+ VOUCHERCODE+" from "+TASK_TABLE+" where "+ ORDERNO +"="+od_no ;
        Cursor c1 = db.rawQuery(qry1, null);
        return c1;
    }


    public String getPackingStatus(int od_no)
    {
        String packingStatus = new String("0");
        db = this.getWritableDatabase();
        String qry1 = "Select "+ PACKING_STATUS+" from "+TASK_TABLE+" where "+ ORDERNO +"="+od_no ;
        Cursor c1 = db.rawQuery(qry1, null);
        if (c1.moveToFirst())
        {
            if (c1.isNull(0))
            {
                Log.d("getPackingStatus", "Packing Status is blank");
                packingStatus="0" ;
            }
            else
            {
                packingStatus=c1.getString(0);
                Log.d("getPackingStatus", "Packing Status Data from DB:"+packingStatus);
            }
        }
        c1.close();

        return packingStatus;
    }

    //  Insert ItemDetails

    public void additemdetails(Items item)
    {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ORDER_NO, item.getOrdNo());
        values.put(ITEMNo, item.getItemNo());
        values.put(ITEMNAME, item.getItemName());
        values.put(ITEMQTY, item.getItemQty());
        values.put(ITEMFQTY,item.getFqty());
        values.put(ITEMPRICE,item.getPrice());

        db.insert(ITEMDETAILS_TABLE, null, values);
        Log.d("DbHelper:additem", "Item Details Added"+item.getItemNo());

        db.close();
    }

    // 	*************************************************************		Get data in ListView		****************************************************************************************************

    public Cursor getMyActiveOrders()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("SELECT "+TASKID+" as _id , "+ORDER_NO+" , "+NAME+" , "+ADDRESS+" , "+AREA+" , "+AMOUNT+" , "+PAYMENT_STATUS+" , "+STATUS+" from "+TASK_TABLE +" Where "+STATUS+"!=?",new String [] {"Closed"});
        return cur;
    }


    public Cursor getCompletedOrders(String rpt_dt)  // Date should be in yyyy-mm-dd format only
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String str = "SELECT "+TASKID+" as _id , "+ORDER_NO+" , "+NAME+" , "+
                AREA+" , ("+ RECEIPT_AMOUNT_CASH +" + "+RECEIPT_AMOUNT_CC+" + "+RECEIPT_AMOUNT_VOUCHER +") as " +AMOUNT +" from "+TASK_TABLE +" Where "+STATUS+"=? and "+
                ORDER_COMPLETION_DATE+"=?";

        //       Cursor cur=db.rawQuery("SELECT "+ITEMID+" as _id , "+ITEMNAME+" , "+ITEMFQTY+" , "+ITEMFQTY+ " * " + ITEMPRICE + " As "+  ITEMPRICE+" from "+ITEMDETAILS_TABLE + " WHERE " + ORDER_NO  +"=?",new String [] {String.valueOf(od_no)});

        Log.d ("Completed Orders" , str);
        Cursor cur=db.rawQuery(str,new String [] {"Closed",rpt_dt});
        return cur;
    }

//  String query = "SELECT SUM("+ITEMPRICE+"*"+ITEMFQTY+") FROM "+ITEMDETAILS_TABLE +" WHERE " + ORDER_NO  +"=?" ;

    public Cursor getItemdetailsData(int od_no)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("SELECT "+ITEMID+" as _id , "+ITEMNo+" , "+ITEMNAME+" , "+ITEMQTY+" , "+ITEMFQTY+" , "+ITEMPRICE+" from "+ITEMDETAILS_TABLE + " WHERE " + ORDER_NO  +"=?",new String [] {String.valueOf(od_no)});
        return cur;
    }

    public Cursor getpackingData(int od_no)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("SELECT "+ITEMID+" as _id , "+ITEMNo+" , "+ITEMNAME+" , "+ITEMFQTY+" , "+ITEMPRICE+" from "+ITEMDETAILS_TABLE + " WHERE " + ORDER_NO  +"=? and "+ITEMFQTY+">?",new String [] {String.valueOf(od_no),"0"});
        return cur;
    }


    public void addOrUpdateItem(Items item) {
        int exists = 0 ;
        db = this.getWritableDatabase();
        String qry1 = "Select count(*) from "+ITEMDETAILS_TABLE +" where "+ ITEMNo +"="+item.getItemNo();
        Cursor c1 = db.rawQuery(qry1, null);
        if (c1.moveToFirst())
        {
            exists = c1.getInt(0);
        }
        if (exists == 0)
        {
            additemdetails(item);
        }
        else
        {
            ContentValues values = new ContentValues();

            values.put(ITEMNAME, item.getItemName());
            values.put(ITEMQTY, item.getItemQty());
            values.put(ITEMFQTY, item.getFqty());
            values.put(ITEMPRICE, item.getPrice());

            String[] whereVal = {String.valueOf(item.getItemNo()) };
            db.update(ITEMDETAILS_TABLE, values,ITEMNo+"=?",whereVal);
        }

    }

    public void updateqty(int itemNo, int fqty) {

        SQLiteDatabase db = getWritableDatabase() ;
        ContentValues cv = new ContentValues();
        cv.put(ITEMFQTY, fqty);
        db.update(ITEMDETAILS_TABLE, cv, ITEMNo + "=?", new String[]{String.valueOf(itemNo)});
        db.close();

        // Update Server With these information...

        double latitude=0.0, longitude=0.0;
        LocationTracker gps;

            gps = LocationTracker.getInstance();
            // check if GPS enabled

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                Log.d("DB:UpdateQty", "Location:Langitude:" + latitude + "Longitude:" + longitude);
        String url = "http://android.casaneeds.com/seller/orders_fulfillment.php?ItemID="+itemNo+"&ItemQTY="+fqty+"&locLAT="+latitude+"&locLONG="+longitude;
        Log.d("OrderFullFillment", "Update Server:" + url);
        UpdateServerAsync itemFullfillmentStatus = new UpdateServerAsync(app_ctx);
        itemFullfillmentStatus.execute(url);
     }

    public Cursor getDeliveryItems(int od_no) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("SELECT "+ITEMID+" as _id , "+ITEMNAME+" , "+ITEMQTY+" , "+ITEMFQTY+" from "+ITEMDETAILS_TABLE + " WHERE " + ORDER_NO  +"=?",new String [] {String.valueOf(od_no)});
        return cur;
    }

    public Cursor confirmDeliveryItems(int od_no) {
        SQLiteDatabase db=this.getReadableDatabase();
 //       Cursor cursor = db.rawQuery("SELECT " + KEY_SWIMMERLAPS + " * " + KEY_SPONSOR + " AS result FROM " + DATABASE_TABLE, null);

        Cursor cur=db.rawQuery("SELECT "+ITEMID+" as _id , "+ITEMNAME+" , "+ITEMFQTY+" , "+ITEMFQTY+ " * " + ITEMPRICE + " As "+  ITEMPRICE+" from "+ITEMDETAILS_TABLE + " WHERE " + ORDER_NO  +"=?",new String [] {String.valueOf(od_no)});
        return cur;
    }

    public float totalamt(int od_no) {
        float tot_amount ;
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "SELECT SUM("+ITEMPRICE+"*"+ITEMFQTY+") FROM "+ITEMDETAILS_TABLE +" WHERE " + ORDER_NO  +"=?" ;
        Log.d("GetTotalAmount", "Query:"+query);
        Cursor cur= db.rawQuery(query, new String[]{String.valueOf(od_no)});
        if (cur.moveToFirst()) {
            tot_amount = cur.getFloat(0);
        }
        else
        {
            tot_amount = 9.99f ;
        }
        return tot_amount;
    }
}
