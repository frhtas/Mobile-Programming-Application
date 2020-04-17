package ce.yildiz.edu.tr.mobileprogrammingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private ArrayList<User> myUserList;
    private LayoutInflater inflater;

    public UserAdapter(Context context, ArrayList<User> users) {
        this.inflater = LayoutInflater.from(context);
        this.myUserList = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_user_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User selectedUser = myUserList.get(position);
        holder.setData(selectedUser, position);
    }

    @Override
    public int getItemCount() {
        return myUserList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView usernameText, passwordText, userName, password;
        ImageView userImage;
        LinearLayout linearLayout;
        CardView itemUserCard;

        public MyViewHolder(View itemView) {
            super(itemView);
            usernameText = (TextView) itemView.findViewById(R.id.usernameText);
            passwordText = (TextView) itemView.findViewById(R.id.passwordText);
            userName = (TextView) itemView.findViewById(R.id.userName);
            password = (TextView) itemView.findViewById(R.id.password);
            userImage = (ImageView) itemView.findViewById(R.id.userImage);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

            itemUserCard = (CardView) itemView.findViewById(R.id.item_user_card);
            itemUserCard.setOnLongClickListener(this);
        }


        // A function which set User data to CardView
        public void setData(final User selectedUser, int position) {
            userName.setText(selectedUser.getUsername());
            password.setText("****");

            password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    password.setText(selectedUser.getPassword());
                }
            });
            userImage.setImageResource(selectedUser.getUserImage());
            linearLayout.setTag(this);
        }


        // An override function which delete User infos from RecyclerView after long click to CardView, it is not deleting User from app, just for this activity
        @Override
        public boolean onLongClick(View view) {
            itemUserCard.removeAllViews();
            return true;
        }

    }
}