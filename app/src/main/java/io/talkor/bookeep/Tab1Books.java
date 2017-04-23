package io.talkor.bookeep;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static io.talkor.bookeep.R.id.book_list;


public class Tab1Books extends Fragment {

    private DatabaseReference mDatabase;
    ListView listView;
    ArrayList<Book> books = new ArrayList<Book>();
    BookAdapter adapter;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab1books, container, false);

        listView = (ListView) rootView.findViewById(book_list);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        adapter = new BookAdapter(getActivity(), books);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        listView.setAdapter(adapter);

        mDatabase.child("user_01").child("books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                books.clear();
                for (DataSnapshot sn : dataSnapshot.getChildren()) {

                    Book book = sn.getValue(Book.class);
                    books.add(book);
                }

                progressBar.setVisibility(View.GONE);

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

                        Intent intent = new Intent(getActivity(),BookActivity.class);
                        String chosenBook = adapter.getItem(position).getBookID();
                        intent.putExtra("EXTRA_BOOK_ID", "" + chosenBook); //send chosen book
                        startActivity(intent);
                    }
                }
        );

        return rootView;
    }

}