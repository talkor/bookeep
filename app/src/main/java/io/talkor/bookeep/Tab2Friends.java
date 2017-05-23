package io.talkor.bookeep;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static io.talkor.bookeep.R.id.friends_list;
import static io.talkor.bookeep.R.id.no_friends_text_view;

/**
 * Created by Tal on 19/04/2017.
 */

public class Tab2Friends extends Fragment {

    private DatabaseReference mDatabase;
    ListView listView;
    TextView noFriendsTextView;
    ArrayList<Friend> friends = new ArrayList<Friend>();
    FriendsAdapter adapter;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab2friends, container, false);

        listView = (ListView) rootView.findViewById(friends_list);
        noFriendsTextView = (TextView) rootView.findViewById(no_friends_text_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarFriends);

        adapter = new FriendsAdapter(getActivity(), friends);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        noFriendsTextView.setVisibility(View.GONE);
        listView.setAdapter(adapter);

        mDatabase.child("user_01").child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                friends.clear();
                for (DataSnapshot sn : dataSnapshot.getChildren()) {

                    Friend friend = sn.getValue(Friend.class);
                    friends.add(friend);
                }

                progressBar.setVisibility(View.GONE);

                // disappear if there are books
                if (listView.getAdapter().getCount() == 0 && progressBar.getVisibility() == View.GONE)
                    noFriendsTextView.setVisibility(View.VISIBLE);
                else
                    noFriendsTextView.setVisibility(View.GONE);


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                      //  Intent intent = new Intent(getActivity(),BookActivity.class);
                       // String chosenBook = adapter.getItem(position).getBookID();
                       // intent.putExtra("EXTRA_BOOK_ID", "" + chosenBook); //send chosen book
                        //startActivity(intent);
                    }
                }
        );

        return rootView;
    }
}
