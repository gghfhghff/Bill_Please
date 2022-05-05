package sg.edu.rp.c346.id21016412.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    EditText etAmount;
    EditText etPax;
    EditText etDiscount;
    EditText etPayNow;
    ToggleButton tbSVS;
    ToggleButton tbGST;
    RadioGroup rgMethod;
    RadioButton rbMethodCash;
    RadioButton rbMethodPayNow;
    Button btSplit;
    Button btReset;
    TextView tvBill;
    TextView tvEach;
    TextView tvError;
    TextView tvInputError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = findViewById(R.id.editTextAmount);
        etPax = findViewById(R.id.editTextPax);
        etDiscount = findViewById(R.id.editTextDiscount);
        etPayNow = findViewById(R.id.editTextPayNow);
        tbSVS = findViewById(R.id.toggleButtonSVS);
        tbGST = findViewById(R.id.toggleButtonGST);
        rgMethod = findViewById(R.id.radioGroupMethod);
        rbMethodCash = findViewById(R.id.radioButtonMethodCash);
        rbMethodPayNow = findViewById(R.id.radioButtonMethodPayNow);
        btSplit = findViewById(R.id.buttonSplit);
        btReset = findViewById(R.id.buttonReset);
        tvBill = findViewById(R.id.textViewBill);
        tvEach = findViewById(R.id.textViewEach);
        tvError = findViewById(R.id.textViewError);
        tvInputError = findViewById(R.id.textViewInputError);

        rbMethodPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPayNow.setEnabled(true);
            }
        });

        rbMethodCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPayNow.setEnabled(false);
            }
        });

        btSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((etAmount.getText().toString().isEmpty()) || (etPax.getText().toString().isEmpty()) || (etDiscount.getText().toString().isEmpty()) || (rgMethod.getCheckedRadioButtonId()==-1) || (rbMethodPayNow.isChecked() && etPayNow.getText().toString().isEmpty())) {
                    tvError.setVisibility(View.VISIBLE);
                } else if ((Double.parseDouble(etAmount.getText().toString()) <= 0) || (Double.parseDouble(etPax.getText().toString()) <= 0) || (Double.parseDouble(etDiscount.getText().toString()) <= 0) || (Double.parseDouble(etDiscount.getText().toString()) > 100)) {
                    tvInputError.setVisibility(View.VISIBLE);
                } else {
                    tvError.setVisibility(View.INVISIBLE);
                    tvInputError.setVisibility(View.INVISIBLE);
                    double amount = Double.parseDouble(etAmount.getText().toString());
                    double pax = Double.parseDouble(etPax.getText().toString());
                    double discount = Double.parseDouble(etDiscount.getText().toString());
                    double total = amount * (1 - discount / 100);

                    if (tbSVS.isChecked()) {
                        total = total * 1.10;
                    }

                    if (tbGST.isChecked()) {
                        total = total * 1.07;
                    }

                    double split = total / pax;
                    String strBill = String.format("Total Bill: $ %.2f", total);
                    tvBill.setText(strBill);

                    int checkedRadioId = rgMethod.getCheckedRadioButtonId();
                    if (checkedRadioId == R.id.radioButtonMethodCash) {
                        String strEach = String.format("Each Pays: $ %.2f in cash", split);
                        tvEach.setText(strEach);
                    }
                    if (checkedRadioId == R.id.radioButtonMethodPayNow) {
                        String strEach = String.format("Each Pays: $ %.2f via PayNow to " + etPayNow.getText().toString(), split);
                        tvEach.setText(strEach);
                    }
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etAmount.setText("");
                etPax.setText("");
                etDiscount.setText("");
                etPayNow.setText("");
                tvBill.setText("");
                tvEach.setText("");
                rbMethodCash.setChecked(true);
                rbMethodPayNow.setChecked(false);
                tbSVS.setChecked(false);
                tbGST.setChecked(false);
            }
        });
    }
}



