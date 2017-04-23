package io.talkor.bookeep;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tal on 10/04/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, ArrayList<Book> books) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView bookNameView = (TextView) listItemView.findViewById(R.id.book_name_view);
        bookNameView.setText(currentBook.getBookName());

        TextView bookAuthorView = (TextView) listItemView.findViewById(R.id.book_author_view);
        bookAuthorView.setText(currentBook.getBookAuthor());

        TextView progressTextView = (TextView) listItemView.findViewById(R.id.book_progress_view);
        progressTextView.setText("Progress: " + Math.round(Double.parseDouble(currentBook.getBookProgress())/Double.parseDouble(currentBook.getBookPages())*100) + "%");

        return listItemView;
    }

}
