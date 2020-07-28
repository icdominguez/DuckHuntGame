package com.icdominguez.duckhunt.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icdominguez.duckhunt.R;
import com.icdominguez.duckhunt.models.User;

import java.util.List;

public class MyUserRecyclerViewAdapter extends RecyclerView.Adapter<MyUserRecyclerViewAdapter.ViewHolder> {

    private final List<User> mValues;

    public MyUserRecyclerViewAdapter(List<User> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        int pos = position + 1;
        holder.tvPosition.setText(pos + "º");
        holder.tvDucks.setText(String.valueOf(mValues.get(position).getDucks()));
        holder.tvUsername.setText(mValues.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvPosition;
        public final TextView tvDucks;
        public final TextView tvUsername;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvPosition = view.findViewById(R.id.textViewPosition);
            tvDucks = view.findViewById(R.id.textViewDucks);
            tvUsername = view.findViewById(R.id.textViewUsername);
        }
    }
}