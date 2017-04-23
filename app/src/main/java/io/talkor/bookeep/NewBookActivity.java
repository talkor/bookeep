package io.talkor.bookeep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.id;
import static android.R.attr.name;


public class NewBookActivity extends AppCompatActivity {


    private Button mFirebaseBtn;

    private DatabaseReference mDatabase;

    private EditText bookNameField;
    private EditText bookAuthorField;
    private Spinner bookGenreField;
    private EditText bookYearField;
    private EditText bookPagesField;

    private Long idCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseBtn = (Button) findViewById(R.id.button_add_book);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        bookNameField = (EditText) findViewById(R.id.book_name);
        bookAuthorField = (EditText) findViewById(R.id.book_author);
        bookGenreField = (Spinner) findViewById(R.id.book_genre);
        bookYearField = (EditText) findViewById(R.id.book_year);
        bookPagesField = (EditText) findViewById(R.id.book_num_pages);

        mDatabase.child("user_01").child("idCounter").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Long value = dataSnapshot.getValue(Long.class);
                idCounter = value;
             }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bookName = bookNameField.getText().toString().trim();
                String bookAuthor = bookAuthorField.getText().toString().trim();
              //  String bookGenre = bookGenreField.getText().toString().trim();
                String bookGenre = "Genre";
               // String bookYear = bookYearField.getText().toString().trim();
                String bookPages = bookPagesField.getText().toString().trim();
                String bookProgress = "0";
                String bookID = idCounter.toString();


                if (bookNameField.getText().toString().trim().equals("")) {
                    //Error in case name is empty
                    bookNameField.setError("Book name is required!");
                } else if (bookPagesField.getText().toString().trim().equals("")) {
                    //Error in case pages number is empty
                    bookPagesField.setError( "Number of pages is required!");
                } else {
                    Book book = new Book(bookID, bookName,bookAuthor,bookPages, bookGenre, bookProgress);
                    mDatabase.child("user_01").child("books").child("book_" + idCounter).setValue(book);
                  //  mDatabase.child("user_01").child("books").push().setValue(book);
                    mDatabase.child("user_01").child("idCounter").setValue(++idCounter);

                    Toast.makeText(NewBookActivity.this,"Book added successfully",Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }

    // Up button behaviour
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
