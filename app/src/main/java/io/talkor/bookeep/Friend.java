package io.talkor.bookeep;


/**
 * Created by Tal on 10/04/2017.
 */

public class Friend {

    private String friendID;
    private String friendName;
    private String friendStreak;


    public Friend() {

    }

    public Friend(String friendID, String friendName, String friendStreak) {
        this.friendID = friendID;
        this.friendName = friendName;
        this.friendStreak = friendStreak;
    }

    public String getFriendID() {
        return friendID;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getFriendStreak() {
        return friendStreak;
    }

}
