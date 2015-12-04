package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import java.io.Serializable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;

/**
 * Created by Dulanjaya Tennekoon on 15/12/03.
 */
public class PersistentExpenseManager extends ExpenseManager {

    DBHelper dbHelper ;

    public PersistentExpenseManager(Context context) {
        dbHelper = new DBHelper(context);
        setup();
    }

    @Override
    public void setup() {
        setAccountsDAO(new PersistentAccountDAO(dbHelper));
        //setAccountsDAO(new InMemoryAccountDAO());
        setTransactionsDAO(new PersistentTransactionDAO(dbHelper));
    }
}
