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
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.HashMap;


import java.util.Calendar;

public class Change_Activity extends AppCompatActivity {



    private DBHelper dbHelper;
    private EditText idEditText;
    private Spinner originSpinner;
    private Spinner destinationSpinner;
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView totalTextView;
    private Button updateButton;
    private Button backButton;

    private String selectedDate;
    private String selectedTime;
    private double total;

    private HashMap<String, Double> priceMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        dbHelper = new DBHelper(this);

        idEditText = findViewById(R.id.id_edit_text);
        originSpinner = findViewById(R.id.origin_spinner);
        destinationSpinner = findViewById(R.id.destination_spinner);
        dateTextView = findViewById(R.id.date_text_view);
        timeTextView = findViewById(R.id.time_text_view);
        totalTextView = findViewById(R.id.total_text_view);
        updateButton = findViewById(R.id.update_button);
        backButton = findViewById(R.id.back_button);


        initializePriceMap();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        originSpinner.setAdapter(adapter);
        destinationSpinner.setAdapter(adapter);


        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String origin = originSpinner.getSelectedItem().toString();
                String destination = destinationSpinner.getSelectedItem().toString();
                total = calculateTotal(origin, destination);
                totalTextView.setText("Total: " + total);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        originSpinner.setOnItemSelectedListener(onItemSelectedListener);
        destinationSpinner.setOnItemSelectedListener(onItemSelectedListener);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = idEditText.getText().toString().trim();
                String origin = originSpinner.getSelectedItem().toString();
                String destination = destinationSpinner.getSelectedItem().toString();
                String date = selectedDate;
                String time = selectedTime;

                if (idStr.isEmpty() || origin.isEmpty() || destination.isEmpty() || date == null || time == null) {
                    Toast.makeText(Change_Activity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    long id = Long.parseLong(idStr);
                    total = calculateTotal(origin, destination);
                    totalTextView.setText("Total: " + total);
                    int rowsUpdated = dbHelper.updateTrip(id, origin, destination, date, time, total);
                    if (rowsUpdated > 0) {
                        Toast.makeText(Change_Activity.this, "Viaje actualizado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Change_Activity.this, "Error al actualizar el viaje", Toast.LENGTH_SHORT).show();
                    }
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

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1;
                    dateTextView.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> {
                    selectedTime = hourOfDay + ":" + minute1;
                    timeTextView.setText(selectedTime);
                }, hour, minute, false);
        timePickerDialog.show();
    }

    private void initializePriceMap() {
        priceMap = new HashMap<>();
        priceMap.put("Origen1-Destino1", 100.0);
        priceMap.put("Origen1-Destino2", 120.0);
        priceMap.put("Origen1-Destino3", 140.0);
        priceMap.put("Origen1-Destino4", 160.0);
        priceMap.put("Origen1-Destino5", 180.0);
        priceMap.put("Origen2-Destino1", 110.0);
        priceMap.put("Origen2-Destino2", 130.0);
        priceMap.put("Origen2-Destino3", 150.0);
        priceMap.put("Origen2-Destino4", 170.0);
        priceMap.put("Origen2-Destino5", 190.0);
        priceMap.put("Origen3-Destino1", 120.0);
        priceMap.put("Origen3-Destino2", 140.0);
        priceMap.put("Origen3-Destino3", 160.0);
        priceMap.put("Origen3-Destino4", 180.0);
        priceMap.put("Origen3-Destino5", 200.0);
        priceMap.put("Origen4-Destino1", 130.0);
        priceMap.put("Origen4-Destino2", 150.0);
        priceMap.put("Origen4-Destino3", 170.0);
        priceMap.put("Origen4-Destino4", 190.0);
        priceMap.put("Origen4-Destino5", 210.0);
        priceMap.put("Origen5-Destino1", 140.0);
        priceMap.put("Origen5-Destino2", 160.0);
        priceMap.put("Origen5-Destino3", 180.0);
        priceMap.put("Origen5-Destino4", 200.0);
        priceMap.put("Origen5-Destino5", 220.0);
    }

    private double calculateTotal(String origin, String destination) {
        String key = origin + "-" + destination;
        if (priceMap.containsKey(key)) {
            return priceMap.get(key);
        } else {
            return 0.0;
        }
    }
}