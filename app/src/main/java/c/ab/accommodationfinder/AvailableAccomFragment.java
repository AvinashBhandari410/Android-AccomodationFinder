package c.ab.accommodationfinder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AvailableAccomFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BindGrid bindGridAdapter;
    //private ArrayList<AccommodationAvailable> availableList;
    private List<AccommodationAvailable> accommodationAvailablesList;
    DatabaseHelper Helper;
    TextView txtContactDetail;

    String user_email = null;

    public AvailableAccomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.availableaccomrecycler, container, false);
        txtContactDetail = (TextView) view.findViewById(R.id.txtContactDetail);
        recyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bindGridAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        recyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerView);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            Helper = new DatabaseHelper(getContext());
            //cust_Helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        user_email = getArguments().getString("user_email");
        System.out.println("user-email on Create View" + user_email);
        accommodationAvailablesList = Helper.getAllAvailableAccommodation(user_email);
        bindGridAdapter = new BindGrid(getContext(), accommodationAvailablesList);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
