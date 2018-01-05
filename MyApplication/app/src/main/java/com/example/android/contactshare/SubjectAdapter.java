package com.example.android.contactshare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ROHAN on 17-11-2017.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.Subject_ViewHolder> {
    ArrayList<CustomClassSub> subject = new ArrayList<CustomClassSub>();
    Context ctx;

    public SubjectAdapter(ArrayList<CustomClassSub> subject, Context ctx) {
        this.subject = subject;
        this.ctx = ctx;
    }

    @Override
    public Subject_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_subject, parent, false);
        Subject_ViewHolder subview = new Subject_ViewHolder(view, ctx, subject);
        return subview;
    }

    @Override
    public void onBindViewHolder(Subject_ViewHolder holder, int position) {
        CustomClassSub obj = subject.get(position);
        holder.Img.setImageResource(obj.getImageId());
        holder.SubjectName.setText(obj.getSubject());
    }

    @Override
    public int getItemCount() {
        return subject.size();
    }

    public static class Subject_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView Img;
        TextView SubjectName;
        ArrayList<CustomClassSub> subject = new ArrayList<CustomClassSub>();
        Context ctx;

        public Subject_ViewHolder(View itemView, Context ctx, ArrayList<CustomClassSub> subject) {
            super(itemView);
            this.subject = subject;
            this.ctx = ctx;
            //itemView.setOnClickListener(this);
            Img = (ImageView) itemView.findViewById(R.id.subject_image);

            SubjectName = (TextView) itemView.findViewById(R.id.subject_name);
            Img.setOnClickListener(this);
            SubjectName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int a = getAdapterPosition();
            //Log.e("i am position",Integer.toString(a));
            CustomClassSub subject = this.subject.get(a);
            Intent intent = new Intent(this.ctx, StudentMainActivity.class);
            ctx.startActivity(intent);

        }
    }

}
