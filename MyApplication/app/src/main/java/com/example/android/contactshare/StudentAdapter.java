package com.example.android.contactshare;

/**
 * Created by ROHAN on 30-12-2017.
 */

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ROHAN on 17-12-2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    ArrayList<CustomClassForStudent> detail = new ArrayList<>();
    Context ctx;
    int size;
    boolean tracker[];

    public StudentAdapter(ArrayList<CustomClassForStudent> detail, Context ctx, int a) {
        this.detail = detail;
        this.ctx = ctx;
        this.size = a;
        tracker = new boolean[size];
    }

    public StudentAdapter(Parcelable[] abc, StudentMainActivity ctx, int d) {
    }

    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_displayer, parent, false);
        StudentAdapter.ViewHolder detailview = new StudentAdapter.ViewHolder(view, ctx, detail);
        for (int i = 0; i < tracker.length; i++)
            tracker[i] = true;
        return detailview;

    }

    @Override
    public void onBindViewHolder(StudentAdapter.ViewHolder holder, int position) {
        CustomClassForStudent obj = detail.get(position);
        holder.name.setText(obj.getName());
        holder.rollno.setText(obj.getRoll());
        if (obj.getBool().equalsIgnoreCase("true"))
            holder.enable.setChecked(true);
        else
            holder.enable.setChecked(false);
    }

    @Override
    public int getItemCount()
    {
        if(detail!=null)
        return detail.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ArrayList<CustomClassForStudent> detail = new ArrayList<>();
        TextView name;
        TextView rollno;
        Switch enable;
        Context ctx;

        public ViewHolder(View itemView, Context ctx, ArrayList<CustomClassForStudent> detail) {
            super(itemView);
            this.ctx = ctx;
            this.detail = detail;
            name = (TextView) itemView.findViewById(R.id.name);
            rollno = (TextView) itemView.findViewById(R.id.roll);
            enable = (Switch) itemView.findViewById(R.id.trueANDfalse);

            for(int i=0;i<detail.size();i++)
            {
                if(detail.get(i).getBool()=="true")
                {
                    enable.setChecked(true);
                }
                else
                {
                    enable.setChecked(false);
                }
            }
            name.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (enable.isChecked()) {
                enable.setChecked(false);

                int position = getAdapterPosition();
                detail.get(position).setBool("false");
                tracker[position] = false;
            } else {
                enable.setChecked(true);
                int position = getAdapterPosition();
                detail.get(position).setBool("true");
                tracker[position] = true;
            }
            // Log.e("i am position",Integer.toString(position));
        }
    }
}

