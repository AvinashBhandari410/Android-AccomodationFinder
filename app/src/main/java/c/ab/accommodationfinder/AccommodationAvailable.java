package c.ab.accommodationfinder;

import java.util.ArrayList;

import c.ab.accommodationfinder.UserEntity;

/**
 * Created by avina on 4/14/2018.
 */

public class AccommodationAvailable {


    //used to save app data
    Integer accommodation_id =null;
    Integer acom_uid = null;
    String acom_title= null;
    String acom_desc = null;
    //static String acom_size = null;
    String acom_cost = null;
    String acom_contactdetail = null;
    String acom_availbleDate = null;

    public Integer getAccommodation_id() {
        return accommodation_id;
    }

    public void setAccommodation_id(Integer accommodation_id) {
        this.accommodation_id = accommodation_id;
    }

    public Integer getAcom_uid() {
        return acom_uid;
    }

    public void setAcom_uid(Integer acom_uid) {
        this.acom_uid = acom_uid;
    }

    public String getAcom_title() {
        return acom_title;
    }

    public void setAcom_title(String acom_title) {
        this.acom_title = acom_title;
    }

    public String getAcom_desc() {
        return acom_desc;
    }

    public void setAcom_desc(String acom_desc) {
        this.acom_desc = acom_desc;
    }

    public String getAcom_cost() {
        return acom_cost;
    }

    public void setAcom_cost(String acom_cost) {
        this.acom_cost = acom_cost;
    }

    public String getAcom_contactdetail() {
        return acom_contactdetail;
    }

    public void setAcom_contactdetail(String acom_contactdetail) {
        this.acom_contactdetail = acom_contactdetail;
    }

    public String getAcom_availbleDate() {
        return acom_availbleDate;
    }

    public void setAcom_availbleDate(String acom_availbleDate) {
        this.acom_availbleDate = acom_availbleDate;
    }

//public String getAcom_size() {
     //   return acom_size;
   // }

//    public void setAcom_size(String acom_size) {
  //      AccommodationAvailable.acom_size = acom_size;
   // }


 /*   public class BindImage
    {
        int imageID;
        public int getImageID() {
            return imageID;
        }
        public void setImageID(int imageID) {
            this.imageID = imageID;
        }
    }*/

}

