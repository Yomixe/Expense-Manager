package com.example.myapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;


public class OperationViewModel extends AndroidViewModel {

    private OperationRepository mRepository;

    private LiveData<List<Operation>> mAllOperations;

    public OperationViewModel(Application application) {
        super(application);
        mRepository = new OperationRepository(application);
        mAllOperations = mRepository.getAllOperations();

    }

    LiveData<List<Operation>> getAllOperations() {
        return mAllOperations;
    }

    public void insert(Operation operation) {
        mRepository.insert(operation);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void delete(Operation operation) {
        mRepository.delete(operation);
    }

    public void update(Operation operation) {
        mRepository.update(operation);
    }

    public LiveData<Double> getDailyPrice() {
        return mRepository.getDailyPrice();
    }

    public LiveData<Double> getMonthlyPrice() { return mRepository.getMonthlyPrice(); }

    public LiveData<Double> getYearlyPrice() {
        return mRepository.getYearlyPrice();
    }

    public LiveData<List<TotalPriceByCategory>> getPriceByCategories() {
        return mRepository.getPriceByCategories();
    }

}