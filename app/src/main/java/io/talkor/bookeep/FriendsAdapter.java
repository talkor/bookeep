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

public class FriendsAdapter extends ArrayAdapter<Friend> {

    public FriendsAdapter(Activity context, ArrayList<Friend> friends) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, friends);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_friends, parent, false);
        }

        Friend currentFriend = getItem(position);

        TextView userNameView = (TextView) listItemView.findViewById(R.id.user_name_view);
        userNameView.setText(currentFriend.getFriendName());

      //  TextView bookAuthorView = (TextView) listItemView.findViewById(R.id.book_author_view);
       // bookAuthorView.setText(currentBook.getBookAuthor());

        TextView userStreakView = (TextView) listItemView.findViewById(R.id.user_streak_view);
        userStreakView.setText("Streak: " + currentFriend.getFriendStreak());

        return listItemView;
    }

}
