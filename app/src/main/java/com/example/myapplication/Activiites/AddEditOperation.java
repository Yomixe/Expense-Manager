package com.example.myapplication.Activiites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.OperationViewModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEditOperation extends AppCompatActivity {

    private OperationViewModel mOperationViewModel;
    private EditText mEditPriceView;
    private EditText mEditNameView;
    private EditText mEditDateView;
    private Spinner mEditCategoryView;
    private DatePickerDialog picker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neweditoperation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEditPriceView = findViewById(R.id.edit_price);
        mEditNameView = findViewById(R.id.edit_name);
        mEditDateView = findViewById(R.id.edit_date);
        mEditCategoryView = findViewById(R.id.edit_category);


        final List<String> categories = new ArrayList<String>();
        categories.add("Wybierz kategorię");
        categories.add("Jedzenie");
        categories.add("Rozrywka");
        categories.add("Samochód");
        categories.add("Odzież");
        categories.add("Elektronika");
        categories.add("Zdrowie i uroda");
        categories.add("Praca");
        categories.add("Inne");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item,
                categories);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        mEditCategoryView.setAdapter(adapter);
        mEditCategoryView.setSelection(0);

        mOperationViewModel = ViewModelProviders.of(this).get(OperationViewModel.class);
        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_ID")) {
            setTitle("Edytuj operacje");

            mEditPriceView.setText(intent.getStringExtra("EXTRA_PRICE"));
            mEditNameView.setText(intent.getStringExtra("EXTRA_NAME"));
            mEditDateView.setText(intent.getStringExtra("EXTRA_DATE"));
            mEditCategoryView.setSelection(categories.indexOf(intent.getStringExtra("EXTRA_CATEGORY")));
        } else {
            setTitle("Dodaj operację");

        }

        mEditDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddEditOperation.this,
                        new DatePickerDialog.OnDateSetListener() {

                            private String convertDate(int input) {
                                if (input >= 10) {
                                    return String.valueOf(input);
                                } else {
                                    return "0" + input;
                                }
                            }
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                mEditDateView.setText(year + "-" + convertDate(monthOfYear + 1) + "-" + convertDate(dayOfMonth));

                            }

                        }, year, month, day);
                picker.show();
            }
        });


    }

    private void saveOperation() {
        String str_price = mEditPriceView.getText().toString();
        String name = mEditNameView.getText().toString();
        String date = mEditDateView.getText().toString();
        String category = mEditCategoryView.getSelectedItem().toString();
        Double price = Double.parseDouble(mEditPriceView.getText().toString());
        if (str_price.trim().isEmpty() || name.trim().isEmpty() || date.trim().isEmpty() || mEditCategoryView.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Uzupełnij wszystkie pola", Toast.LENGTH_LONG).show();
            return;
        } else if(str_price.trim().length()>5) {
            Toast.makeText(getApplicationContext(), "Podana cena jest nieprawidłowa", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();

        data.putExtra("EXTRA_PRICE", price);
        data.putExtra("EXTRA_NAME", name);
        data.putExtra("EXTRA_DATE", date);
        data.putExtra("EXTRA_CATEGORY", category);
        int id = getIntent().getIntExtra("EXTRA_ID", -1);
        if (id != -1) {
            data.putExtra("EXTRA_ID", id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_operation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_operation:
                saveOperation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}