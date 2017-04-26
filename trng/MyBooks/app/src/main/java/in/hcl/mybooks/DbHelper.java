package in.hcl.mybooks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dell on 19-09-2016.
 */

 class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context,int version) {
        super(context, "MyBooks", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE `books` (\n" +
                "\t`book_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`book_name`\tTEXT,\n" +
                "\t`author_name`\tTEXT,\n" +
                "\t`year_published`\tINTEGER,\n" +
                "\t`dt_added`\tTEXT,\n" +
                "\t`book_image_loc_name`\tTEXT\n" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void insert_book(String book_nm, String author_nm, int yr_pub, String image_loc)
    {
        SQLiteDatabase  db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("book_name", book_nm);
        cv.put("author_name", author_nm);
        cv.put("year_published",yr_pub);
        cv.put("book_image_loc_name",image_loc);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dt = sdf.format(new Date());
        cv.put("dt_added", dt);
        db.insert("books",null, cv);
        db.close();
    }

    Cursor query_books()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c1 = db.rawQuery("SELECT * FROM books",null);
        return c1 ;
    }


}







