package casaneeds.taskactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import casaneeds.appLaunch.R;
import casaneeds.appLaunch.WelcomeScreen;
import casaneeds.entity.DatabaseHelper;
import casaneeds.utils.LocationTracker;

public class AcceptPaymentActivity extends AppCompatActivity implements  View.OnFocusChangeListener {
    ListView lv;
    Button submit;

    int od_no, pmode;
    String name, address, area, amt;
    float od_amt;
    float totalAmount;

    float cashamt, creditamt, voucheramt, receivedamt;

    TextView tvod, tvcustname, tvarea, tvpmode,tvtotalamt;

    DatabaseHelper dbHelper;
    double latitude, longitude;
    LocationTracker gps;


    String paymode,od;
    LinearLayout l1,l2,l3,l4;

    EditText etcash, etcreditcard, etvoucher;
    ScrollView sv;
    TextView  tamt;

    PriceCalcAdapter pca ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_delivery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Accept Payment");
        getSupportActionBar().setIcon(R.drawable.back_btn);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(AcceptPaymentActivity.this, DeliveryItemsActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
                finish();

            }
        });


        gps = LocationTracker.getInstance();

        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        Log.d("OrderFullfillment", "Location:Langitude:" + latitude + "Longitude:" + longitude);


        od_no = getIntent().getExtras().getInt("orderno");
        name = getIntent().getExtras().getString("name");
        area = getIntent().getExtras().getString("area");
        address = getIntent().getExtras().getString("address");
        od_amt = getIntent().getExtras().getFloat("totalamt");
        pmode = getIntent().getExtras().getInt("payment");

        tvod = (TextView) findViewById(R.id.order_no);
        tvcustname = (TextView) findViewById(R.id.cust_name);
        tvarea = (TextView) findViewById(R.id.cust_area);
        tvpmode = (TextView) findViewById(R.id.pmode);
        tvtotalamt = (TextView)findViewById(R.id.price);
        submit = (Button)findViewById(R.id.submit);

        lv = (ListView)findViewById(R.id.list_items);

        etcash = (EditText)findViewById(R.id.cash);
        etcreditcard = (EditText)findViewById(R.id.creditcard);
        etvoucher = (EditText)findViewById(R.id.voucher);
        tamt = (TextView)findViewById(R.id.receivedamt);

        l1 = (LinearLayout)findViewById(R.id.l1);
        l2 = (LinearLayout)findViewById(R.id.l2);
        l3 = (LinearLayout)findViewById(R.id.l3);
        l4 = (LinearLayout)findViewById(R.id.l4);


        od = String.valueOf(od_no);
        paymode = String.valueOf(pmode);


        tvod.setText(od);
        tvcustname.setText(name);
        tvarea.setText(address);
        tvpmode.setText(paymode);

        etcash.setOnFocusChangeListener(this);
        etcreditcard.setOnFocusChangeListener(this);
        etvoucher.setOnFocusChangeListener(this);

        if(pmode == 0) {
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.VISIBLE);
            l3.setVisibility(View.VISIBLE);
            l4.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
        }
        else
        {
        final AlertDialog.Builder adb = new AlertDialog.Builder(AcceptPaymentActivity.this);

        adb.setTitle("Message !!");
        adb.setMessage("Thanks for Shopping with us ");
        adb.setIcon(R.drawable.greenalert);

        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int arg1) {
                d.dismiss();
            }
        });
        adb.show();
    }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = etcash.getText().toString();
                cashamt = Float.parseFloat(str);

                String str1 = etcreditcard.getText().toString();
                creditamt = Float.parseFloat(str1);

                String str2 = etvoucher.getText().toString();
                voucheramt = Float.parseFloat(str2);

                receivedamt = cashamt + creditamt + voucheramt;
                tamt.setText(String.valueOf(receivedamt));

                if (receivedamt == totalAmount) {
                    final AlertDialog.Builder adb = new AlertDialog.Builder(AcceptPaymentActivity.this);
                    adb.setTitle("Message !!");
                    adb.setMessage("Thanks for entering the payment details. This order is processed. ");
                    adb.setIcon(R.drawable.greenalert);
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int arg1) {
                            // Update the Order Status to Closed.
                    dbHelper.updateOrderStatusClosed(od_no);
                    dbHelper.updateReceiptAmount(od_no,cashamt,creditamt,voucheramt);

                    if (cashamt!=0) {
                        String url = "http://android.casaneeds.com/seller/orders_paymentcollect.php?OrderID=" + od_no + "&paymentMode=18&amountCollected=" + cashamt + "&locLAT=" + latitude + "&locLONG=" + longitude;
                        Log.d("OrderFullFillment", "Update Server:" + url);
                        UpdateServerAsync cashPayment = new UpdateServerAsync(AcceptPaymentActivity.this);
                        cashPayment.execute(url);
                    }
                    if (creditamt!=0) {
                        String url = "http://android.casaneeds.com/seller/orders_paymentcollect.php?OrderID=" + od_no + "&paymentMode=19&amountCollected=" + creditamt + "&locLAT=" + latitude + "&locLONG=" + longitude;
                        Log.d("OrderFullFillment", "Update Server:" + url);
                        UpdateServerAsync ccPayment = new UpdateServerAsync(AcceptPaymentActivity.this);
                        ccPayment.execute(url);
                    }


                    if (voucheramt!=0) {
                        String url = "http://android.casaneeds.com/seller/orders_paymentcollect.php?OrderID=" + od_no + "&paymentMode=22&amountCollected=" + voucheramt + "&locLAT=" + latitude + "&locLONG=" + longitude;
                        Log.d("OrderFullFillment", "Update Server:" + url);
                        UpdateServerAsync voucherPayment = new UpdateServerAsync(AcceptPaymentActivity.this);
                        voucherPayment.execute(url);
                        }

                            // Back to Welcome screen
                            Intent i = new Intent(AcceptPaymentActivity.this, WelcomeScreen.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            d.dismiss();
                        }
                    });
                    adb.show();
                }
                else if(receivedamt == 0.0f) {
                    final AlertDialog.Builder adb = new AlertDialog.Builder(AcceptPaymentActivity.this);

                    adb.setTitle("Alert !!");
                    adb.setMessage("Please Enter Payment Details");
                    adb.setIcon(R.drawable.greenalert);
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int arg1) {
                            d.dismiss();
                        }
                    });
                    adb.show();
                }
                else
                    {
                        final AlertDialog.Builder adb = new AlertDialog.Builder(AcceptPaymentActivity.this);

                        adb.setTitle("Alert !!");
                        adb.setMessage("Please take full Payment");
                        adb.setIcon(R.drawable.greenalert);
             adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int arg1) {
                            d.dismiss();
                        }
                    });
                    adb.show();
        }
    }
        });


    }
//	**************************************************************		call onStart() to Load Data		***************************************************************************************************

    @Override
    public void onStart() {
        super.onStart();
        dbHelper = new DatabaseHelper(this);
        LoadData();


    }

    public void LoadData() {
        try {
            Cursor c = dbHelper.confirmDeliveryItems(od_no);

           // The desired columns to be bound
            String[] from = new String[]{DatabaseHelper.ITEMNAME, DatabaseHelper.ITEMFQTY, DatabaseHelper.ITEMPRICE};

            // the XML defined views which the data will be bound to
            int[] to = new int[]{R.id.itemname, R.id.qty, R.id.price};

            // create the adapter using the cursor pointing to the desired data as well as the layout information
            pca = new PriceCalcAdapter(this, c, 0);
            lv.setAdapter(pca);
            setListViewHeightBasedOnChildren(lv);  // List view height change
            totalAmount = dbHelper.totalamt(od_no);
            tvtotalamt.setText(String.valueOf(totalAmount));

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

      //  int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
/*
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));
*/

            view.measure(0, 0);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String str = etcash.getText().toString();
        cashamt = Float.parseFloat(str);

        String str1 = etcreditcard.getText().toString();
        creditamt = Float.parseFloat(str1);

        String str2 = etvoucher.getText().toString();
        voucheramt = Float.parseFloat(str2);

        receivedamt = cashamt + creditamt + voucheramt;
        tamt.setText(String.valueOf(receivedamt));
    }
}
