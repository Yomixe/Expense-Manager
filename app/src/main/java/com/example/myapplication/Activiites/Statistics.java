package com.example.myapplication.Activiites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import com.example.myapplication.OperationViewModel;
import com.example.myapplication.R;
import com.example.myapplication.TotalPriceByCategory;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Statistics extends AppCompatActivity {
    private OperationViewModel mOperationViewModel;
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        setTitle("Statystyki");
        mOperationViewModel = ViewModelProviders.of(this).get(OperationViewModel.class);
        final TextView YearlyTotalPrice = findViewById(R.id.yearly_info);
        final TextView MonthlyTotalPrice = findViewById(R.id.monthly_info);
        final TextView DailyTotalPrice = findViewById(R.id.daily_info);


        pieChart = findViewById(R.id.piechart);

        mOperationViewModel.getYearlyPrice().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double price) {

                if (price==null) {
                    YearlyTotalPrice.setText("W tym roku jeszcze nic nie wydałeś");

                }else{
                    YearlyTotalPrice.setText("W tym roku wydałeś " + price + "zł");
                }
            }}
        );
        mOperationViewModel.getMonthlyPrice().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double price) {

                if (price==null ) {
                    MonthlyTotalPrice.setText("W tym miesiącu jeszcze nic nie wydałeś");

                }else{

                    MonthlyTotalPrice.setText("W tym miesiącu wydałeś " +price+ "zł");
                }
            }}
        );
        mOperationViewModel.getDailyPrice().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double price) {

                if (price==null) {
                    DailyTotalPrice.setText("Dzisiaj jeszcze nic nie wydałeś");
                }else{

                    DailyTotalPrice.setText("Dzisiaj wydałeś " + price+ "zł");
                }
            }}
        );
        mOperationViewModel.getPriceByCategories().observe(this, new Observer<List<TotalPriceByCategory>>() {
            @Override
            public void onChanged(@Nullable List<TotalPriceByCategory> list) {
                pieEntries = new ArrayList<>();
                Float total=0.00f;



                for(TotalPriceByCategory x: list) {

                    pieEntries.add(new PieEntry(x.getTotal(), x.getCategory()));
                    total+=x.getTotal();
                }
                    pieDataSet = new PieDataSet(pieEntries,"");

                    pieData = new PieData(pieDataSet);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.getLegend().setEnabled(false);
                    pieChart.setData(pieData);
                    pieData .setValueFormatter(new DefaultValueFormatter(2));
                    pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                    pieDataSet.setSliceSpace(2f);
                    pieDataSet.setValueTextColor(Color.WHITE);
                    pieDataSet.setValueTextSize(10f);
                    pieDataSet.setSliceSpace(5f);

                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    df.setMinimumFractionDigits(2);
                    pieChart.setCenterText(String.format("Łącznie wydałeś już " + df.format(total)+"zł"));
                pieChart.setNoDataText("Brak danych");
                    pieChart.invalidate();

            }}
        );
    }
}
