package rodriguez.miguel.practicabd;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Consult_Activity extends AppCompatActivity {


    private DBHelper dbHelper;
    private EditText idEditText;
    private TextView resultTextView;
    private Button searchButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);

        dbHelper = new DBHelper(this);

        idEditText = findViewById(R.id.id_edit_text);
        resultTextView = findViewById(R.id.result_text_view);
        searchButton = findViewById(R.id.search_button);
        backButton = findViewById(R.id.back_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = idEditText.getText().toString().trim();
                if (!idStr.isEmpty()) {
                    long id = Long.parseLong(idStr);
                    Cursor cursor = dbHelper.getTripById(id);
                    if (cursor.moveToFirst()) {
                        String origin = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ORIGIN));
                        String destination = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DESTINATION));
                        String date = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE));
                        String time = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TIME));
                        double total = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_TOTAL));
                        resultTextView.setText("ID: " + id + "\nOrigen: " + origin + "\nDestino: " + destination + "\nFecha: " + date + "\nHora: " + time + "\nTotal: " + total);
                    } else {
                        Toast.makeText(Consult_Activity.this, "ID no encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Consult_Activity.this, "Por favor, ingresa un ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}