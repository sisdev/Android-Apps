package in.hcl.mybooks;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_book, et_author, et_year_pub ;
    Button btn1;

    DbHelper dbh ;
    String image_loc ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_book = (EditText) findViewById(R.id.editText1);
        et_author = (EditText) findViewById(R.id.editText2);
        et_year_pub = (EditText) findViewById(R.id.editText3);
        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbh = new DbHelper(this,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.main_menu,menu);
         return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.submit:
                Toast.makeText(this,"Submit Button Pressed", Toast.LENGTH_LONG).show();
                String book_nm = et_book.getText().toString();
                String author = et_author.getText().toString();
                Integer year = Integer.parseInt(et_year_pub.getText().toString());
                if (image_loc == null)
                {
                    image_loc = "No Data";
                }
                dbh.insert_book(book_nm,author,year,image_loc);
                break;
            case R.id.list_books:
                Toast.makeText(this,"List Books Button Pressed", Toast.LENGTH_LONG).show();
                Cursor c1 = dbh.query_books();
                if (c1.moveToFirst())
                {
                    do{
                        String book1 = c1.getString(c1.getColumnIndex("book_name"));
                        String author1 = c1.getString(c1.getColumnIndex("author_name"));
                      //  String yr_pub = c1.getString(c1.getColumnIndex("year_published"));
                      //String img_loc = c1.getString(c1.getColumnIndex("book_image_loc_name"));
                        Toast.makeText(this,"Book:"+book1+":"+author1,Toast.LENGTH_LONG).show();
                    //  Toast.makeText(this,"Year of Publication:"+yr_pub+" Image Location:"+img_loc,Toast.LENGTH_LONG).show();
                    } while (c1.moveToNext());
                }
                break ;

        }

        return true ;
    }

    @Override
    public void onClick(View v) {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        image_loc = data.getData().toString() ;
        if (image_loc == null)
        {
            image_loc="No Value found" ;
        }
    }
}
