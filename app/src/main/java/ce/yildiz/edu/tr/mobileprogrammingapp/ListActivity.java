package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.os.Bundle;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        UserAdapter userAdapter = new UserAdapter(this, getAllUsers());
        recyclerView.setAdapter(userAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    // A function which get all users data from SQLite Database for listing them
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        String usernameDb, passwordDb;
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.getAllData();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                usernameDb = cursor.getString(0);
                passwordDb = cursor.getString(1);
                users.add(new User(usernameDb, passwordDb, R.drawable.user_image)); // If a user signed up, it gives a default image for user
            }
        }
        return users;
    }

}
