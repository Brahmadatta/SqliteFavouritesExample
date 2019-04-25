package Example.com.alphabeticalrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavouritesData extends AppCompatActivity {

    RecyclerView favouritesRecyclerView;
    SqliteFavouriteDatabase sqliteFavouriteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_data);


        favouritesRecyclerView = findViewById(R.id.favouritesRecyclerView);
        sqliteFavouriteDatabase = new SqliteFavouriteDatabase(this);

        getTheFavouritesDataFromDb();
    }

    private void getTheFavouritesDataFromDb() {

        ArrayList arrayList = sqliteFavouriteDatabase.showFavouritesList();

        if (arrayList.size() == 0){
            Toast.makeText(this, "No Favourites", Toast.LENGTH_SHORT).show();
        }else {

            GithubAdapter githubAdapter = new GithubAdapter(arrayList, this);
            favouritesRecyclerView.setHasFixedSize(true);
            favouritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            favouritesRecyclerView.setAdapter(githubAdapter);
            githubAdapter.notifyDataSetChanged();
        }
    }
}
