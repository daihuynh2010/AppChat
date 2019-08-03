package com.example.appchat.room;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchat.R;
import com.example.appchat.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MessageViewHolder>{
    private List<MessageModel> data = new ArrayList<MessageModel>();
    private int id;
    private Context context;

    public RoomAdapter(List<MessageModel> data, int id, Context _context) {
        this.data = data;
        this.id = id;
        this.context = _context;
    }

    public void setData(List<MessageModel> _data)
    {
        this.data = _data;
    }

    public void noDataChange(MessageModel mess){
        this.data.add(mess);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_room, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (data !=null)
            return data.size();
        return 0;
    }

    public class MessageViewHolder extends BaseViewHolder  {
        @BindView(R.id.message) TextView txtMessage;
        @BindView(R.id.owner) TextView txtOwner;
        @BindView(R.id.timestamp) TextView txtTimestamp;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void onBind(int position){
             MessageModel message = data.get(position);
             txtMessage.setText(message.content);
             txtOwner.setText(message.owner.displayName);

             txtTimestamp.setText(getDate(message.timestamp));
             if (message.owner.getId() == id){
                 txtMessage.setBackgroundColor(Color.BLUE);
                 txtMessage.setGravity(Gravity.RIGHT);
                 return;
             }
             txtMessage.setGravity(Gravity.LEFT);
             txtMessage.setBackgroundColor(Color.GRAY);
        }
    }

     private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        return date;
    }
}
