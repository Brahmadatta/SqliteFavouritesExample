package escapadetechnologies.com.alphabeticalrecyclerview;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class SqliteFavouriteDatabase extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "favourites.db";


    //Table for favourites

    public static final String INCREMENT_ID = "increment_id";

    public static final String TABLE_GITHUB = "GITHUB_DATA";

    public static final String NAME = "name";

    public static final String FULL_NAME = "full_name";
    public static final String ID = "id";

    public static final String FAVOURITE = "favourites";



    public SqliteFavouriteDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FAVOURITES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GITHUB + "("
                + INCREMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ID + " TEXT,"
                + NAME + " TEXT,"
                + FULL_NAME + " TEXT,"
                + FAVOURITE + " DEFAULT 0"
                + ")";

        db.execSQL(CREATE_FAVOURITES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GITHUB);
    }

    public ArrayList getFavouritesData(){

        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GITHUB,null);

        if (cursor.moveToFirst()){

            do {

                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(SqliteFavouriteDatabase.NAME,cursor.getString(cursor.getColumnIndex(SqliteFavouriteDatabase.NAME)));
                hashMap.put(SqliteFavouriteDatabase.FULL_NAME,cursor.getString(cursor.getColumnIndex(SqliteFavouriteDatabase.FULL_NAME)));
                hashMap.put(SqliteFavouriteDatabase.ID,cursor.getString(cursor.getColumnIndex(SqliteFavouriteDatabase.ID)));

                arrayList.add(hashMap);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public ArrayList showFavouritesList(){


        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM GITHUB_DATA where favourites = 1";
        Cursor cursor = db.rawQuery(query ,null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(SqliteFavouriteDatabase.NAME,cursor.getString(cursor.getColumnIndex(SqliteFavouriteDatabase.NAME)));
                hashMap.put(SqliteFavouriteDatabase.FULL_NAME,cursor.getString(cursor.getColumnIndex(SqliteFavouriteDatabase.FULL_NAME)));
                hashMap.put(SqliteFavouriteDatabase.ID,cursor.getString(cursor.getColumnIndex(SqliteFavouriteDatabase.ID)));

                arrayList.add(hashMap);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }


    public String isFavourite(String id){
        SQLiteDatabase database = this.getReadableDatabase();
        String fav = null;
        Cursor cursor = database.rawQuery("SELECT * FROM " + SqliteFavouriteDatabase.TABLE_GITHUB + " WHERE " + ID +  " = " + id,null);
        if (cursor.moveToFirst()){
            do {
               fav = cursor.getString(cursor.getColumnIndex(SqliteFavouriteDatabase.FAVOURITE));
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return fav;
    }
}
