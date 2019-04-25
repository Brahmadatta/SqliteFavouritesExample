package Example.com.alphabeticalrecyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.GithubViewHolder>{

    public ArrayList<HashMap<String,String>> githubArryaList;

    SqliteFavouriteDatabase sqliteFavouriteDatabase;

    public Context context;

    public GithubAdapter(ArrayList<HashMap<String, String>> githubArryaList, Context context) {
        this.githubArryaList = githubArryaList;
        this.context = context;
    }

    @NonNull
    @Override
    public GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_github,viewGroup,false);
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubViewHolder githubViewHolder, int i) {

        HashMap<String,String> hashMap = githubArryaList.get(i);

        githubViewHolder.name.setText(hashMap.get("name"));
        githubViewHolder.fullname.setText(hashMap.get("full_name"));
        githubViewHolder.id.setText(hashMap.get("id"));
        githubViewHolder.node_id.setText(hashMap.get("node_id"));
        githubViewHolder.priv.setText(hashMap.get("private"));
        githubViewHolder.repos_url.setText(hashMap.get("repos_url"));
        githubViewHolder.type.setText(hashMap.get("type"));



        String fav = sqliteFavouriteDatabase.isFavourite(hashMap.get("id"));

        if (fav.equals("1")){
            githubViewHolder.favourite.setVisibility(View.VISIBLE);
            githubViewHolder.notFavourite.setVisibility(View.GONE);
        }else {
            githubViewHolder.notFavourite.setVisibility(View.VISIBLE);
            githubViewHolder.favourite.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return githubArryaList.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView name,fullname,id,node_id,priv,repos_url,type;
        ImageView notFavourite,favourite;

        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            fullname = itemView.findViewById(R.id.fullname);
            notFavourite = itemView.findViewById(R.id.notFavourite);
            favourite = itemView.findViewById(R.id.favourite);
            id = itemView.findViewById(R.id.id);
            node_id = itemView.findViewById(R.id.node_id);
            priv = itemView.findViewById(R.id.priv);
            repos_url = itemView.findViewById(R.id.repos_url);
            type = itemView.findViewById(R.id.type);

            sqliteFavouriteDatabase = new SqliteFavouriteDatabase(context);


            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), ""+name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            repos_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), ""+repos_url.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            notFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favourite.setVisibility(View.VISIBLE);
                    notFavourite.setVisibility(View.GONE);


                    SQLiteDatabase db = sqliteFavouriteDatabase.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(SqliteFavouriteDatabase.FAVOURITE,1);
                    db.update(SqliteFavouriteDatabase.TABLE_GITHUB,contentValues,SqliteFavouriteDatabase.ID + " =?",new String[]{String.valueOf(id.getText().toString())});

                }
            });


            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteDatabase db = sqliteFavouriteDatabase.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(SqliteFavouriteDatabase.FAVOURITE,0);
                    db.update(SqliteFavouriteDatabase.TABLE_GITHUB,contentValues,SqliteFavouriteDatabase.ID + " =?",new String[]{String.valueOf(id.getText().toString())});

                    favourite.setVisibility(View.GONE);
                    notFavourite.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}
