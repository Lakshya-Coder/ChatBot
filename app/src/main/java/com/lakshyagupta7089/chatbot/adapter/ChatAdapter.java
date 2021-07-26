package com.lakshyagupta7089.chatbot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lakshyagupta7089.chatbot.R;
import com.lakshyagupta7089.chatbot.model.Chats;
import com.lakshyagupta7089.chatbot.util.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    private ArrayList<Chats> chats;
    private Context context;

    public ChatAdapter(ArrayList<Chats> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.user_message_item,
                        parent,
                        false);
                return new UserViewHolder(view);
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.bot_message_item,
                        parent,
                        false);
                return new BotViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        Chats chatsModel = chats.get(position);

        switch (chatsModel.getSender()) {
            case Util.USER:
                ((UserViewHolder) holder).userMessage.setText(chatsModel.getMessage());
                break;
            case Util.BOT:
                ((BotViewHolder) holder).botMessage.setText(chatsModel.getMessage());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (chats.get(position).getSender().equals(Util.USER)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView userMessage;

        public UserViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            userMessage = itemView.findViewById(R.id.user_message_text_view);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {
        public TextView botMessage;

        public BotViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            botMessage = itemView.findViewById(R.id.bot_message_text_view);
        }
    }
}
