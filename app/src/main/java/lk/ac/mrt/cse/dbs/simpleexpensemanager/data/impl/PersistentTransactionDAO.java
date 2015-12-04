package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by Dulanjaya Tennekoon on 15/12/04.
 */
public class PersistentTransactionDAO implements TransactionDAO{
    DBHelper dbHelper;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public PersistentTransactionDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LogsInfo.ACC_NO, accountNo);
        values.put(LogsInfo.AMOUNT, amount);
        values.put(LogsInfo.DATE, format.format(date));
        values.put(LogsInfo.TYPE, expenseType == ExpenseType.EXPENSE);
        db.insert(LogsInfo.TABLE, null, values);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transaction = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + LogsInfo.TABLE, null);

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String d = cursor.getString(cursor.getColumnIndex(LogsInfo.DATE));
                Date date = null;
                try {
                    date = format.parse(d);
                }catch(ParseException e) {

                }
                Double amount = cursor.getDouble(cursor.getColumnIndex(LogsInfo.AMOUNT));
                String acc_no = cursor.getString(cursor.getColumnIndex(LogsInfo.ACC_NO));
                ExpenseType expense;
                int exp = cursor.getInt(cursor.getColumnIndex(LogsInfo.TYPE));
                if(exp == 1) {
                    expense = ExpenseType.EXPENSE;
                } else {
                    expense = ExpenseType.INCOME;
                }
                transaction.add(new Transaction(date, acc_no, expense,amount));
            }
        }
        cursor.close();
        db.close();
        return transaction;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(LogsInfo.TABLE, new String[]{LogsInfo.TYPE, LogsInfo.ACC_NO, LogsInfo.AMOUNT, LogsInfo.DATE},null, null, null, null," " + LogsInfo.KEY + " DESC" , String.valueOf(limit));

        while (cursor.moveToNext()) {
            try {
                ExpenseType e;
                if (cursor.getInt(0) == 1) {
                    e = ExpenseType.EXPENSE;
                } else {
                    e = ExpenseType.INCOME;
                }
                Transaction trans = new Transaction(format.parse(cursor.getString(3)), cursor.getString(1), e, cursor.getDouble(2));
                transactionList.add(trans);
            } catch(ParseException e) {

            }
        }
        cursor.close();
        db.close();
        return transactionList;
    }
}
