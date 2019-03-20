package escapadetechnologies.com.alphabeticalrecyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    public static final String BASE_URL = "https://api.github.com/repositories?since=1";

    RecyclerView recyclerView;

    ArrayList<HashMap<String,String>> githuArrayList;

    RecyclerView.Adapter mAdapter;

    RecyclerView.LayoutManager mLayoutManager;

    SqliteFavouriteDatabase sqliteFavouriteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        sqliteFavouriteDatabase = new SqliteFavouriteDatabase(this);


        /*ArrayList<String> myDataSet = new ArrayList<String>();

        for (int i = 0 ; i  < 26 ; i++){
            myDataSet.add(Character.toString((char)(65 + i)) + " Row item");
        }*/



        getTheData();

    }

    private void getTheData() {

        if (checkInternetConnection(this)) {

            StringRequest stringRequest = new StringRequest(BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        githuArrayList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {


                            HashMap<String, String> hashMap = new HashMap<>();
                            String name = jsonArray.getJSONObject(i).getString("name");
                            String fullname = jsonArray.getJSONObject(i).getString("full_name");
                            String id = jsonArray.getJSONObject(i).getString("id");
                            String node_id = jsonArray.getJSONObject(i).getString("node_id");
                            String priv = jsonArray.getJSONObject(i).getString("private");
                            String owner = jsonArray.getJSONObject(i).getString("owner");

                            JSONObject object = new JSONObject(owner);

                            String repos_url = object.getString("repos_url");
                            String type = object.getString("type");


                            hashMap.put("name", name);
                            hashMap.put("full_name", fullname);
                            hashMap.put("id", id);
                            hashMap.put("node_id", node_id);
                            hashMap.put("private", priv);
                            hashMap.put("repos_url", repos_url);
                            hashMap.put("type", type);


                            SQLiteDatabase db = sqliteFavouriteDatabase.getWritableDatabase();

                            ContentValues contentValues = new ContentValues();

                            contentValues.put(SqliteFavouriteDatabase.NAME, name);
                            contentValues.put(SqliteFavouriteDatabase.FULL_NAME, fullname);
                            contentValues.put(SqliteFavouriteDatabase.ID, id);
                            contentValues.put(SqliteFavouriteDatabase.NODE_ID, node_id);
                            contentValues.put(SqliteFavouriteDatabase.PRIVATE, priv);
                            contentValues.put(SqliteFavouriteDatabase.REPOS_URL, repos_url);
                            contentValues.put(SqliteFavouriteDatabase.TYPE, type);

                            db.insert(SqliteFavouriteDatabase.TABLE_GITHUB, null, contentValues);

                            githuArrayList.add(hashMap);
                        }

                        showDataFromDatabase();
                        //attachAdapter(githuArrayList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        }else if (sqliteFavouriteDatabase.getFavouritesData().size() != 0){
            ArrayList arrayList = sqliteFavouriteDatabase.getFavouritesData();

            GithubAdapter githubAdapter = new GithubAdapter(arrayList, this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(githubAdapter);
            githubAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this, "Please enable the Internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    private void showDataFromDatabase() {

        ArrayList arrayList = sqliteFavouriteDatabase.getFavouritesData();

            GithubAdapter githubAdapter = new GithubAdapter(arrayList, this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(githubAdapter);
            githubAdapter.notifyDataSetChanged();


    }

    private void attachAdapter(ArrayList<HashMap<String, String>> githuArrayList) {

        GithubAdapter githubAdapter = new GithubAdapter(githuArrayList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(githubAdapter);
        githubAdapter.notifyDataSetChanged();
    }

    private static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
