package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

/**
 * Created by Dulanjaya Tennekoon on 15/12/03.
 */
public class AccountInfo {
    public static final String TABLE = "AccountInfo";

    public static final String ACC_NO = "acc_no";
    public static final String BANK = "bank";
    public static final String ACC_HOLDER = "acc_holder";
    public static final String INIT_BAL = "int_bal";

    public String acc_no;
    public String bank;
    public String acc_holder;
    public Double init_bal;
}
