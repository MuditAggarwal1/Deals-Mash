package hsblabs.dealsmash;


import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllDealsF extends Fragment {

    RecyclerView recyclerView;
    Context c;
    private DealsAdapter adapter;
    public List<Object> dList;

    public AllDealsF() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getActivity().getTheme().applyStyle(R.style.red,true);
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_all_deals, container, false);
        dList = new ArrayList<Object>();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(c);
        //mLayoutManager.setReverseLayout(true);
        //mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        c = getActivity();
        new AllDeals(
                new ListFetcher.OnDataloadListListener(){
                    @Override
                    public void onDataloadListReady(List<Object> list) {
                        dList = list;
                        dList.size();
                        adapter = new DealsAdapter(c, dList);
                        recyclerView.setAdapter(adapter);
                    }
                }
                ,c).execute();
        return v;
    }
}
