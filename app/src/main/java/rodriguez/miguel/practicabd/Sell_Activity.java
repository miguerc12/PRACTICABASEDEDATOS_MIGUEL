package rodriguez.miguel.practicabd;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashMap;

public class Sell_Activity extends AppCompatActivity {



    private DBHelper dbHelper;
    private TextView idTextView;
    private Spinner originSpinner;
    private Spinner destinationSpinner;
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView totalTextView;
    private Button saveButton;
    private Button backButton;

    private String selectedDate;
    private String selectedTime;
    private double total;

    private HashMap<String, Double> priceMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        dbHelper = new DBHelper(this);

        idTextView = findViewById(R.id.trip_id);
        originSpinner = findViewById(R.id.origin_spinner);
        destinationSpinner = findViewById(R.id.destination_spinner);
        dateTextView = findViewById(R.id.date);
        timeTextView = findViewById(R.id.time);
        totalTextView = findViewById(R.id.total);
        saveButton = findViewById(R.id.save_button);
        backButton = findViewById(R.id.back_button);


        initializePriceMap();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        originSpinner.setAdapter(adapter);
        destinationSpinner.setAdapter(adapter);


        long nextId = dbHelper.getNextId();
        idTextView.setText(String.valueOf(nextId));


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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String origin = originSpinner.getSelectedItem().toString();
                String destination = destinationSpinner.getSelectedItem().toString();

                if (selectedDate == null || selectedTime == null) {
                    Toast.makeText(Sell_Activity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    long id = Long.parseLong(idTextView.getText().toString().trim());
                    boolean isInserted = dbHelper.insertTrip(id, origin, destination, selectedDate, selectedTime, total);
                    if (isInserted) {
                        Toast.makeText(Sell_Activity.this, "Boleto vendido con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Sell_Activity.this, "Error al vender el boleto", Toast.LENGTH_SHORT).show();
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