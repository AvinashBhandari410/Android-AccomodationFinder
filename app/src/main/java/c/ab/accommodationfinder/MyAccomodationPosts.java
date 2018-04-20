package c.ab.accommodationfinder;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccomodationPosts extends Fragment {


    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BindGrid bindGridAdapter;
    private ArrayList<AccommodationAvailable> availableList;
    DatabaseHelper Helper;
    String user_email = null;

    public MyAccomodationPosts() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            Helper = new DatabaseHelper(getContext());
            //cust_Helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.availableaccomrecycler, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerView);
        bindGridAdapter = new BindGrid(getContext(), availableList);
        System.out.println("bindGridAdapter" + availableList);
        //layoutManager = new LinearLayoutManager(MainMenu.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(bindGridAdapter);
        return view;
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
        user_email = getArguments().getString("user_email");
        System.out.println("user-email on Create View" + user_email);
        availableList = new ArrayList<>();
        List<AccommodationAvailable> accommodationAvailablesList = Helper.getAllMyAvailableAccommodation(user_email);
        for (AccommodationAvailable accommodationAvailable : accommodationAvailablesList
                ) {
            //   System.out.println("Data" + accommodationAvailable.getAcom_title());
            availableList.add(accommodationAvailable);
        }

    }
}
