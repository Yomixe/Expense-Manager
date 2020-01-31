package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Activiites.AddEditOperation;
import com.example.myapplication.Activiites.Statistics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private OperationViewModel mOperationViewModel;
    public static final int NEW_OPERATION_REQUEST = 1;
    public static final int EDIT_OPERATION_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final OperationListAdapter adapter = new OperationListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mOperationViewModel = ViewModelProviders.of(this).get(OperationViewModel.class);
        mOperationViewModel.getAllOperations().observe(this, new Observer<List<Operation>>() {
            @Override
            public void onChanged(@Nullable final List<Operation> Operations) {
                adapter.setOperations(Operations);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                final int position = viewHolder.getAdapterPosition();
                final Operation myOperation = adapter.getOperationAtPosition(position);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Czy na pewno chcesz usunąć?");
                builder.setCancelable(false);
                builder.setPositiveButton(
                        "Tak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mOperationViewModel.delete(myOperation);
                                Toast.makeText(MainActivity.this, "Usunięto " +
                                        myOperation.getName(), Toast.LENGTH_LONG).show();
                            }
                        });

                builder.setNegativeButton(
                        "Nie",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                adapter.notifyItemChanged(position);
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }

        });

        helper.attachToRecyclerView(recyclerView);
        adapter.setOnItemClikListner(new OperationListAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(Operation operation) {
                Intent intent = new Intent(MainActivity.this, AddEditOperation.class);
                intent.putExtra("EXTRA_ID", operation.getId());
                intent.putExtra("EXTRA_PRICE", operation.getStrPrice());
                intent.putExtra("EXTRA_NAME", operation.getName());
                intent.putExtra("EXTRA_DATE", operation.getDate());
                intent.putExtra("EXTRA_CATEGORY", operation.getCategory());
                startActivityForResult(intent, EDIT_OPERATION_REQUEST);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Czy na pewno chcesz usunąć?");

            builder.setPositiveButton(
                    "Tak",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mOperationViewModel.deleteAll();
                            Toast.makeText(MainActivity.this, "Usunięto wszystkie dane", Toast.LENGTH_LONG).show();
                        }
                    });

            builder.setNegativeButton(
                    "Nie",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(MainActivity.this, "Dane nie zostały usunięte", Toast.LENGTH_LONG).show();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void showStatisticsActivity(View view) {
        Intent intent = new Intent(MainActivity.this, Statistics.class);
        startActivity(intent);
    }

    public void showAddEditOperationActivity(View view) {
        Intent intent = new Intent(MainActivity.this, AddEditOperation.class);
        startActivityForResult(intent, NEW_OPERATION_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_OPERATION_REQUEST && resultCode == RESULT_OK) {
            Double price = data.getDoubleExtra("EXTRA_PRICE", 1);
            String name = data.getStringExtra("EXTRA_NAME");
            String date = data.getStringExtra("EXTRA_DATE");
            String category = data.getStringExtra("EXTRA_CATEGORY");

            Operation operation = new Operation(price, name, date, category);
            mOperationViewModel.insert(operation);
            Toast.makeText(this, "Operacja dodana", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_OPERATION_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra("EXTRA_ID", -1);
            if (id == -1) {
                Toast.makeText(this, "Operacja nie została zaktualizowana", Toast.LENGTH_SHORT).show();
                return;
            }
            Double price = data.getDoubleExtra("EXTRA_PRICE", 1);
            String name = data.getStringExtra("EXTRA_NAME");
            String date = data.getStringExtra("EXTRA_DATE");
            String category = data.getStringExtra("EXTRA_CATEGORY");

            Operation operation = new Operation(id, price, name, date, category);
            mOperationViewModel.update(operation);
            Toast.makeText(this, "Operacja zaktualizowana", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Operacja nie została dodana", Toast.LENGTH_SHORT).show();
        }

    }
}


