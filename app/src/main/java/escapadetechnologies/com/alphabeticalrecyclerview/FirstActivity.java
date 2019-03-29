package escapadetechnologies.com.alphabeticalrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {

    Button favouritesData,githubData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        githubData = findViewById(R.id.githubData);
        favouritesData = findViewById(R.id.favouritesData);



        githubData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this,MainActivity.class));
            }
        });

        favouritesData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this,FavouritesData.class));

                /*Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = (DatePickerDialog) DatePickerDialog.newInstance(
                        FirstActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAutoHighlight(true);
                dpd.show(getFragmentManager(), "Datepickerdialog");*/
            }
        });
    }

}
