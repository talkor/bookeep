package io.talkor.bookeep;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private Book book;
    private TextView pageNumbersTextView;
    private TextView bookNameTextView;
    private TextView bookAuthorTextView;
    private SeekBar progressBar;
    private Button updateButton;
    private int tempProgress;
    private String bookNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        bookNumber = getIntent().getStringExtra("EXTRA_BOOK_ID");

        progressBar = (SeekBar)findViewById(R.id.seekBar);
        bookNameTextView = (TextView) findViewById(R.id.bookName);
        bookAuthorTextView = (TextView) findViewById(R.id.bookAuthor);
        pageNumbersTextView =(TextView)findViewById(R.id.book_num_pages);
        //  TextView bookNameTextView = (TextView) findViewById(R.id.bookName);
        //TextView bookNameTextView = (TextView) findViewById(R.id.bookName);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("user_01").child("books").child("book_" + String.format("%03d", Integer.parseInt(bookNumber))).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                book = dataSnapshot.getValue(Book.class);

                if (book != null) {
                    bookNameTextView.setText(book.getBookName());
                    bookAuthorTextView.setText(book.getBookAuthor());
                    pageNumbersTextView.setText("Page " + book.getBookProgress() + " of " + book.getBookPages());

                    // Progress bar update
                    progressBar.setMax(Integer.parseInt(book.getBookPages()));
                    progressBar.setProgress(Integer.parseInt(book.getBookProgress()));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //   Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        updateProgressBar();

        updateButton = (Button) findViewById(R.id.button_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("user_01").child("books").child("book_" + String.format("%03d", Integer.parseInt(bookNumber))).child("bookProgress").setValue("" + tempProgress);

                Toast.makeText(BookActivity.this,"Updated",Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:     // Up button behaviour
                finish();
                return true;
            case R.id.edit_book:
                return true;
            case R.id.delete_book:
                deleteBook();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void updateProgressBar(){
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pageNumbersTextView.setText("Page " + progress + " of " + book.getBookPages());
                tempProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void deleteBook() {
        AlertDialog.Builder adb = new AlertDialog.Builder(BookActivity.this);
        adb.setMessage("Are you sure?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // destroy views
                        progressBar.setProgress(0);
                        bookNameTextView.setText(null);
                        bookAuthorTextView.setText(null);
                        pageNumbersTextView.setText(null);

                        mDatabase.child("user_01").child("books").child("book_" + String.format("%03d", Integer.parseInt(bookNumber))).getRef().setValue(null);

                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = adb.create();
        alert.setTitle("Delete book");
        alert.show();
    }
}

