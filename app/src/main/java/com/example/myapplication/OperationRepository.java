package com.example.myapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class OperationRepository {

    private OperationDao mOperationDao;

    private LiveData<List<Operation>> mAllOperations;


    public OperationRepository(Application application) {
        Database db = Database.getDatabase(application);
        mOperationDao = db.operationDao();

        mAllOperations = mOperationDao.getAllOperations();

    }

    public LiveData<List<Operation>> getAllOperations() {
        return mAllOperations;
    }



    public void insert (Operation operation) {
        new insertAsyncTask(mOperationDao).execute(operation);
    }

    public void deleteAll()  {
        new deleteAllWordsAsyncTask(mOperationDao).execute();
    }

    public void delete(Operation operation)  {
        new deleteAsyncTask(mOperationDao).execute(operation);
    }

    public void update(Operation operation)  {
        new updateAsyncTask(mOperationDao).execute(operation);
    }

    public LiveData<Double> getDailyPrice(){


        return mOperationDao.getDailyPrice();
    }
    public LiveData<Double> getMonthlyPrice(){


        return mOperationDao.getMonthlyPrice();
    }
    public LiveData<Double> getYearlyPrice(){


        return mOperationDao.getYearlyPrice();
    }
    public LiveData <List<TotalPriceByCategory>> getPriceByCategories(){
        return mOperationDao.getPriceByCategories();
    }


    private static class insertAsyncTask extends AsyncTask<Operation, Void, Void> {

        private OperationDao mAsyncTaskDao;

        insertAsyncTask(OperationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Operation... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private OperationDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(OperationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Operation, Void, Void> {
        private OperationDao mAsyncTaskDao;

        deleteAsyncTask(OperationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Operation... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    private static class updateAsyncTask extends AsyncTask<Operation, Void, Void> {
        private OperationDao mAsyncTaskDao;

        updateAsyncTask(OperationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Operation... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
