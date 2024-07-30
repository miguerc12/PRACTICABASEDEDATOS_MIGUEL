package rodriguez.miguel.practicabd;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Menu_Activity extends AppCompatActivity {


    private Button sellButton;
    private Button consultButton;
    private Button changeButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sellButton = findViewById(R.id.sell_button);
        consultButton = findViewById(R.id.consult_button);
        changeButton = findViewById(R.id.change_button);
        cancelButton = findViewById(R.id.cancel_button);

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, Sell_Activity.class);
                startActivity(intent);
            }
        });

        consultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, Consult_Activity.class);
                startActivity(intent);
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, Change_Activity.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, Cancel_Activity.class);
                startActivity(intent);
            }
        });
    }
}