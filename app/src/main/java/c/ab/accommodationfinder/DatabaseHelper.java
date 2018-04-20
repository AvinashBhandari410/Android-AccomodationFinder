package c.ab.accommodationfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "accommodationfinder";
    SQLiteDatabase db;

    private static final int DATA_BASE_VERSION = 18;

    //SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor;

    //table name
    public String UserReg_TABLE = "user_registration";
    public String AvailAccommodation_TABLE = "Available_Accommodation";
    //public static final String PLACED_ORDER ="placed_order";

    //column name of UserReg_TABLE
    public String user_id = "user_id";
    public String user_firstName = "user_firstName";
    public String user_lastName = "user_lastName";
    public String user_email = "user_email";
    public String user_pwd = "user_pwd";
    public String user_active = "user_active";
    public String user_createdOn = "user_createdOn";

    //column name of AvailAccommodation_TABLE
    public String accommodation_id = "accommodation_id";
    public String accommodation_uid = "accommodation_uid";
    public String accommodation_title = "accommodation_title";
    public String accommodation_description = "accommodation_desc";
    //public String accommodation_size = "accommodation_size";
    public String accommodation_cost = "accommodation_cost";
    public String accommodation_contactdetail = "accommodation_phone";
    public String accommodation_availbleDate = "accommodation_date";


    String CREATE_UR_TABLE = "CREATE TABLE " + UserReg_TABLE + "("
            + user_id + " INTEGER PRIMARY KEY, "
            + user_firstName + " TEXT NOT NULL, "
            + user_lastName + " TEXT NOT NULL, " + user_email + " TEXT NOT NULL, "
            + user_pwd + " TEXT NOT NULL, " + user_active + " BOOLEAN, " + user_createdOn + " TEXT );";

    String CREAT_AA_TABLE = "CREATE TABLE " + AvailAccommodation_TABLE + "("

            + accommodation_id + " INTEGER PRIMARY KEY, " + accommodation_uid + " INTEGER , " + accommodation_title + " TEXT, "
            + accommodation_description + " TEXT, " +
            //accommodation_size + " TEXT, "
            accommodation_cost + " FLOAT, " + accommodation_contactdetail + " TEXT, "
            + accommodation_availbleDate + " TEXT, "
            + " FOREIGN KEY (" + accommodation_uid + ") REFERENCES " + UserReg_TABLE + " (" + user_id + "));";

    public DatabaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        // db = this.getWritableDatabase();

    }

    public String getDate() {
        Calendar c = Calendar.getInstance();

        String sDate = c.get(Calendar.YEAR) + "/"
                + c.get(Calendar.MONTH)
                + "/" + c.get(Calendar.DAY_OF_MONTH)
                + " at " + c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE);
        return sDate;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            //Log.e("in onOPen", "foreign key is enable");
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_AA_TABLE);
        db.execSQL(CREATE_UR_TABLE);
        Log.e("db on create call", "called");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*// Drop older table if existed
                db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
				db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE);
				db.execSQL("DROP TABLE IF EXISTS " + PLACED_ORDER);
				// Create tables again
				onCreate(db);*/

    }

    // insert accomodation details
    public long addAccommodationPost(AccommodationAvailable accommodationAvailable, int userID) {
        ContentValues cv = new ContentValues();
        System.out.println("Title " + accommodationAvailable.getAcom_title());
        System.out.println("Desc " + accommodationAvailable.getAcom_desc());
        System.out.println("Cost " + accommodationAvailable.getAcom_cost());
        System.out.println("Phone " + accommodationAvailable.getAcom_contactdetail());
        System.out.println("Available Date " + accommodationAvailable.getAcom_availbleDate());
        System.out.println("Available Date " + getDate());
        System.out.println("UserID" + getDate());
        cv.put(accommodation_uid, userID);
        cv.put(accommodation_title, accommodationAvailable.getAcom_title());
        cv.put(accommodation_description, accommodationAvailable.getAcom_desc());
        // cv.put(accommodation_size, accommodationAvailable.getAcom_size());
        cv.put(accommodation_cost, accommodationAvailable.getAcom_cost());
        cv.put(accommodation_contactdetail, accommodationAvailable.getAcom_contactdetail());
        cv.put(accommodation_availbleDate, accommodationAvailable.getAcom_availbleDate());
        System.out.println(cv);
        db = this.getWritableDatabase();
        long accom_id = db.insert(AvailAccommodation_TABLE, null, cv);
        System.out.println(AvailAccommodation_TABLE);

        System.out.println("AccomendationId " + accom_id);
        db.close();
        return accom_id;
    }

    //insert user
    public long addUser(UserEntity userEntity) {
        ContentValues cv = new ContentValues();
        System.out.println(CREATE_UR_TABLE);
        System.out.println("FirstName " + userEntity.getFirstName());
        System.out.println("LastName " + userEntity.getLastName());
        System.out.println("Email " + userEntity.getEmail());
        System.out.println("Password " + userEntity.getPassword());
        System.out.println("IsActive " + userEntity.isUserActive());
        System.out.println("user_createdOn " + getDate());
        cv.put(user_firstName, userEntity.getFirstName());
        cv.put(user_lastName, userEntity.getLastName());
        cv.put(user_email, userEntity.getEmail());
        cv.put(user_pwd, userEntity.getPassword());
        cv.put(user_active, userEntity.isUserActive());
        cv.put(user_createdOn, getDate());
        //insert row
        db = this.getWritableDatabase();
        long user_id = db.insert(UserReg_TABLE, null, cv);
        System.out.println("Userid " + user_id);
        db.close();
        return user_id;
    }

    public int getHighestID() {
        final String MY_QUERY = "SELECT last_insert_rowid()";
        Cursor cur = db.rawQuery(MY_QUERY, null);
        cur.moveToFirst();
        int ID = cur.getInt(0);
        cur.close();
        return ID;
    }

    public boolean validateUser(String email, String pwd) {
        db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + UserReg_TABLE + " WHERE  user_email = ? AND user_pwd= ? ";
        cursor = getReadableDatabase().rawQuery(sqlQuery, new String[]{email, pwd});
        if (cursor.getCount() > 0)
            return true;
        return false;
    }


    public int getUserID(String user_email) {
        db = this.getWritableDatabase();
        int ID = 0;
        cursor = db.rawQuery("SELECT user_id FROM " + UserReg_TABLE + " WHERE user_email ='" + user_email + "'", null);
        if (cursor != null && cursor.moveToNext()) {

            do {
                ID = cursor.getInt(0);
                System.out.println("ID" + ID);
            }
            while (cursor.moveToNext());
        }
        return ID;
    }


    public List<AccommodationAvailable> getAllAvailableAccommodation(String user_email) {
        // List<AccommodationAvailable> accommodationList = new ArrayList<AccommodationAvailable>();
        //select all query
        String sqlQuery = "SELECT accommodation_id, accommodation_title, accommodation_cost,accommodation_phone " +
                "FROM " + AvailAccommodation_TABLE + " JOIN " + UserReg_TABLE +
                " ON Available_Accommodation.accommodation_uid = user_registration.user_id WHERE user_email<> ?";

        System.out.println(sqlQuery);
        cursor = getReadableDatabase().rawQuery(sqlQuery, new String[]{user_email});

        List<AccommodationAvailable> accommodationList = new ArrayList<AccommodationAvailable>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            AccommodationAvailable accommodationAvailable = new AccommodationAvailable();
            accommodationAvailable.setAccommodation_id(Integer.parseInt(cursor.getString(0)));
            accommodationAvailable.setAcom_title(cursor.getString(1));
            accommodationAvailable.setAcom_cost(cursor.getString(2));
            accommodationAvailable.setAcom_contactdetail(cursor.getString(3));
            System.out.println("accommodation_id: " + cursor.getString(0));
            System.out.println("accommodation_title: " + cursor.getString(1));
            System.out.println("accommodation_cost: " + cursor.getString(2));
            System.out.println("accommodation_phone: " + cursor.getString(3));
            accommodationList.add(accommodationAvailable);
        }
        return accommodationList;
    }


    public List<AccommodationAvailable> getAllMyAvailableAccommodation(String user_email) {
        // List<AccommodationAvailable> accommodationList = new ArrayList<AccommodationAvailable>();
        //select all query
        String sqlQuery = "SELECT accommodation_id,accommodation_title, accommodation_cost,accommodation_phone " +
                "FROM " + AvailAccommodation_TABLE + " JOIN " + UserReg_TABLE +
                " ON Available_Accommodation.accommodation_uid = user_registration.user_id WHERE user_email = ?";

        System.out.println(sqlQuery);
        cursor = getReadableDatabase().rawQuery(sqlQuery, new String[]{user_email});

        List<AccommodationAvailable> accommodationList = new ArrayList<AccommodationAvailable>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            AccommodationAvailable accommodationAvailable = new AccommodationAvailable();
            accommodationAvailable.setAccommodation_id(Integer.parseInt(cursor.getString(0)));
            accommodationAvailable.setAcom_title(cursor.getString(1));
            accommodationAvailable.setAcom_cost(cursor.getString(2));
            accommodationAvailable.setAcom_contactdetail(cursor.getString(3));
            // System.out.println("accommodation_id: " + cursor.getString(0));
            //System.out.println("accommodation_title: " + cursor.getString(1));
            //System.out.println("accommodation_cost: " + cursor.getString(2));
            //System.out.println("accommodation_phone: " + cursor.getString(3));
            accommodationList.add(accommodationAvailable);
        }
        return accommodationList;
    }

    public boolean checkUserExist(String email) {
        db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + UserReg_TABLE + " WHERE  user_email = ?";
        cursor = getReadableDatabase().rawQuery(sqlQuery, new String[]{user_email});
        if (cursor.getCount() > 0)
            return true;
        return false;
    }


    //get single customer
/*	public ModelClass getSingleInfo(String phone){

		cursor = db.query(CUSTOMER_TABLE, new String[] { C_NAME, C_PHONE, C_EMAIL, C_ADDR}, C_PHONE + "=?",
						new String[] {phone} , null,null,null,null);
		
			if(cursor != null)
				cursor.moveToFirst();	
			ModelClass modelClass = new ModelClass(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
			
			Log.e("name_value",cursor.getString(1));
			
			return modelClass;
	}*/
    //getting all customer

	/*public List<ModelClass> getAllCustomer(){
        List<ModelClass> CustomerList = new ArrayList<ModelClass>();
		//select all query
		String sqlQuery = "SELECT  * FROM " + CUSTOMER_TABLE;
		cursor = db.rawQuery(sqlQuery, null);
		
		//looping through all row and adding to list
		if(cursor.moveToFirst()){
			do{
				ModelClass myClass = new ModelClass();
				myClass.setName(cursor.getString(0));
				myClass.setPhone(Integer.parseInt(cursor.getString(1)));
				myClass.setEmail(cursor.getString(2));
				myClass.setName(cursor.getString(3));
				//adding to list
				CustomerList.add(myClass);
			}while(cursor.moveToNext());
		}
		
		return CustomerList;
	}
	   
	//updating customer info
	public int updateCustomer(ModelClass myClass){
		ContentValues cv = new ContentValues();
		cv.put(C_NAME, myClass.getName());
		cv.put(C_EMAIL, myClass.getEmail());
		cv.put(C_ADDR, myClass.getAddress());
		
		//updating a row
		return db.update(CUSTOMER_TABLE,cv,C_PHONE + " = ?", new String[] {String.valueOf(myClass.getPhone())});
		
	} 
	//deleting customer info
	public void deleteCustomer(ModelClass myClass){
		db.delete(CUSTOMER_TABLE, C_PHONE + " = ?", new String[] {String.valueOf(myClass.getPhone())});
		db.close();
	}
	//getting contact count
	public int getCustomerCount(){
		String countQuery = " SELECT * FROM " + CUSTOMER_TABLE;
		cursor = db.rawQuery(countQuery, null);
		cursor.close();
		
		return cursor.getCount();
		
	}*/
}
