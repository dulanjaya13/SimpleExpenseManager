package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

/**
 * Created by Dulanjaya Tennekoon on 15/12/03.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
    private static final int DB_VERSION = 4;

    private static final String DB_NAME = "130586D.db";

    public DBHelper(Context context){
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_ACCOUNT_INFO = "CREATE TABLE " + AccountInfo.TABLE + "("
                + AccountInfo.ACC_NO + " TEXT PRIMARY KEY ,"
                + AccountInfo.BANK + " TEXT ,"
                + AccountInfo.ACC_HOLDER + " TEXT ,"
                + AccountInfo.INIT_BAL + " REAL );";
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT_INFO);

        String CREATE_TABLE_LOG_INFO = "CREATE TABLE " +  LogsInfo.TABLE + "("
                + LogsInfo.KEY + " INTEGER PRIMARY KEY ,"
                + LogsInfo.DATE + " TEXT ,"
                + LogsInfo.TYPE + " BOOLEAN ,"
                + LogsInfo.AMOUNT + " REAL ,"
                + LogsInfo.ACC_NO + " TEXT ,"
                + "FOREIGN KEY (" + LogsInfo.ACC_NO + ") REFERENCES " + AccountInfo.TABLE + "(" +AccountInfo.ACC_NO +  "));";
        sqLiteDatabase.execSQL(CREATE_TABLE_LOG_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AccountInfo.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LogsInfo.TABLE);
        onCreate(sqLiteDatabase);

    }
}
