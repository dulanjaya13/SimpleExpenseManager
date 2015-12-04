package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by Dulanjaya Tennekoon on 15/12/03.
 */
public class PersistentAccountDAO implements AccountDAO {
    private DBHelper dbHelper;

    public PersistentAccountDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    @Override
    public List<String> getAccountNumbersList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                AccountInfo.ACC_NO +
                " FROM " + AccountInfo.TABLE;
        List<String> acc_nos = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                acc_nos.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return acc_nos;
    }

    @Override
    public List<Account> getAccountsList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                AccountInfo.ACC_NO + "," +
                AccountInfo.BANK + "," +
                AccountInfo.ACC_HOLDER + "," +
                AccountInfo.INIT_BAL +
                " FROM " + AccountInfo.TABLE;

        List<Account> accs = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                String accNo = cursor.getString(cursor.getColumnIndex(AccountInfo.ACC_NO));
                String bank = cursor.getString(cursor.getColumnIndex(AccountInfo.BANK));
                String accHo = cursor.getString(cursor.getColumnIndex(AccountInfo.ACC_HOLDER));
                double bal = cursor.getDouble(cursor.getColumnIndex(AccountInfo.INIT_BAL));
                accs.add(new Account(accNo,bank,accHo,bal));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accs;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        String acc_no = null;
        String bank = null;
        String acc_ho = null;
        Double bal = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                AccountInfo.ACC_NO + ","+
                AccountInfo.BANK + "," +
                AccountInfo.ACC_HOLDER + "," +
                AccountInfo.INIT_BAL +
                " FROM " + AccountInfo.TABLE +
                " WHERE " +
                AccountInfo.ACC_NO + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {accountNo});
        if (cursor.moveToFirst()) {
            do {
                acc_no = cursor.getString(cursor.getColumnIndex(AccountInfo.ACC_NO));
                bank = cursor.getString(cursor.getColumnIndex(AccountInfo.BANK));
                acc_ho = cursor.getString(cursor.getColumnIndex(AccountInfo.ACC_HOLDER));
                bal = cursor.getDouble(cursor.getColumnIndex(AccountInfo.INIT_BAL));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return new Account(acc_no,bank,acc_ho,bal);
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AccountInfo.ACC_NO, account.getAccountNo());
        values.put(AccountInfo.BANK, account.getBankName());
        values.put(AccountInfo.ACC_HOLDER, account.getAccountHolderName());
        values.put(AccountInfo.INIT_BAL, account.getBalance());
        db.insert(AccountInfo.TABLE,null,values);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(AccountInfo.TABLE, AccountInfo.ACC_NO + "= ?", new String[] {accountNo});
        db.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }
}
