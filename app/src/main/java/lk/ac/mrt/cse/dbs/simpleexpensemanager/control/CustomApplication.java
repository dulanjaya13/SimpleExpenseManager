package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.app.Application;
import android.content.Context;

/**
 * Created by Dulanjaya Tennekoon on 15/12/04.
 */
public class CustomApplication extends Application{
    private static Context context;

    public void onCreate(){
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
