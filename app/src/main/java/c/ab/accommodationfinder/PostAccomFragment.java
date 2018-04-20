package c.ab.accommodationfinder;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class PostAccomFragment extends Fragment implements View.OnClickListener {

    EditText txtTitle, txtDescription, txtCost, txtPhone, txtAvailableDate;
    Button btnPost, btnCancel;
    AccommodationAvailable accommodationAvailable;
    private ListView mDrawerList;
    DatabaseHelper Helper;
    View v;
    String a_title, a_Description, a_cost, a_phone, a_availbleDate,user_email;

    public PostAccomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_post_accom, container, false);
        System.out.println("Post Accomndation page load");
        try {
            Helper = new DatabaseHelper(getContext());
            //cust_Helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        accommodationAvailable = new AccommodationAvailable();
        btnPost = (Button) v.findViewById(R.id.btnPost);
        btnCancel = (Button) v.findViewById(R.id.btnCancel);
        txtTitle = (EditText) v.findViewById(R.id.txtAccomTitle);
        txtDescription = (EditText) v.findViewById(R.id.txtDescription);
        txtCost = (EditText) v.findViewById(R.id.txtCost);
        txtPhone = (EditText) v.findViewById(R.id.txtPhone);
        txtPhone.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s)
            {
                String text = txtPhone.getText().toString();
                int  textLength = txtPhone.getText().length();
                if (text.endsWith("-") || text.endsWith(" ") || text.endsWith(" "))
                    return;
                if (textLength == 1) {
                    if (!text.contains("("))
                    {
                        txtPhone.setText(new StringBuilder(text).insert(text.length() - 1, "(").toString());
                        txtPhone.setSelection(txtPhone.getText().length());
                    }
                }
                else if (textLength == 5)
                {
                    if (!text.contains(")"))
                    {
                        txtPhone.setText(new StringBuilder(text).insert(text.length() - 1, ")").toString());
                        txtPhone.setSelection(txtPhone.getText().length());
                    }
                }
                else if (textLength == 6)
                {
                    txtPhone.setText(new StringBuilder(text).insert(text.length() - 1, " ").toString());
                    txtPhone.setSelection(txtPhone.getText().length());
                }
                else if (textLength == 10)
                {
                    if (!text.contains("-"))
                    {
                        txtPhone.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        txtPhone.setSelection(txtPhone.getText().length());
                    }
                }
            }
        });
        //txtPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        txtAvailableDate = (EditText) v.findViewById(R.id.txtAvailableDate);
        btnPost.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        user_email = getArguments().getString("user_email");
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Helper = new DatabaseHelper(getContext());
            //cust_Helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPost) {
            txtTitle.setError(null);
            txtDescription.setError(null);
            txtCost.setError(null);
            txtPhone.setError(null);
            txtAvailableDate.setError(null);

            a_title = txtTitle.getText().toString().trim();
            a_Description = txtDescription.getText().toString().trim();
            a_cost = txtCost.getText().toString().trim();
            a_phone = txtPhone.getText().toString().trim();
            a_availbleDate = txtAvailableDate.getText().toString().trim();

            if (TextUtils.isEmpty(a_title) || TextUtils.isEmpty(a_Description) || TextUtils.isEmpty(a_cost) || TextUtils.isEmpty(a_phone) || TextUtils.isEmpty(a_availbleDate)) {
                Toast.makeText(getContext(), "All fields are Mandatory", Toast.LENGTH_LONG).show();
            }
            else if (!TextUtils.isEmpty(a_phone) && a_phone.length()!=10) {
                txtPhone.setError(getString(R.string.error_invalid_phoneNumber));
                txtPhone.requestFocus();
            }
            else {
                System.out.println("Useremail while posting Accomodation " + user_email);
                int getUserID = Helper.getUserID(user_email);
                if(getUserID>0) {
                    accommodationAvailable.setAcom_uid(getUserID);
                    accommodationAvailable.setAcom_title(a_title);
                    accommodationAvailable.setAcom_desc(a_Description);
                    accommodationAvailable.setAcom_cost(a_cost);
                    accommodationAvailable.setAcom_contactdetail(a_phone);
                    accommodationAvailable.setAcom_availbleDate(a_availbleDate);
                    long isUserInserted = Helper.addAccommodationPost(accommodationAvailable, getUserID);
                    if (isUserInserted > -1) {
                        Toast.makeText(this.getContext(), "You are Successfully posted accommodation request...!!!", Toast.LENGTH_LONG).show();
                        //Redirect to my accomodation page
                        MainMenu activity = (MainMenu)getActivity();
                        if(activity instanceof MainMenu){
                            MainMenu myactivity = (MainMenu) activity;
                            myactivity.selectItem(2);
                        }
                    } else {
                        Toast.makeText(getContext(), "Error from DB" + isUserInserted, Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Some problem occured while inserting post", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (v.getId() == R.id.btnCancel) {
            MainMenu activity = (MainMenu)getActivity();
            if(activity instanceof MainMenu){
                MainMenu myactivity = (MainMenu) activity;
                myactivity.selectItem(2);
            }
        }
    }
}