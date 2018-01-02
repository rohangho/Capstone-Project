package com.example.android.contact_share;

/**
 * Created by ROHAN on 30-12-2017.
 */

import android.content.Context;
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

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{

    ArrayList<CustomClass> detail=new ArrayList<>();
    Context ctx;
    int size;
    boolean  tracker[];
    public StudentAdapter(ArrayList<CustomClass>detail, Context  ctx, int a)
    {
        this.detail=detail;
        this.ctx=ctx;
        this.size=a;
        tracker= new boolean[size];
    }

    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_displayer,parent,false);
        StudentAdapter.ViewHolder detailview=new StudentAdapter.ViewHolder(view,ctx,detail);
        for(int i=0;i<tracker.length;i++)
            tracker[i]=true;
        return detailview;

    }

    @Override
    public void onBindViewHolder(StudentAdapter.ViewHolder holder, int position) {
        CustomClass obj=detail.get(position);
        holder.name.setText(obj.getName());
        holder.rollno.setText(obj.getRoll());
        if(obj.getBool().equalsIgnoreCase("true"))
            holder.enable.setChecked(true);
        else
            holder.enable.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ArrayList<CustomClass> detail=new ArrayList<>();
        TextView name;
        TextView rollno;
        Switch enable;
        Context ctx;
        public ViewHolder(View itemView,Context ctx,ArrayList<CustomClass> detail) {
            super(itemView);
            this.ctx=ctx;
            this.detail=detail;
            name=(TextView)itemView.findViewById(R.id.name);
            rollno=(TextView)itemView.findViewById(R.id.roll);
            enable=(Switch)itemView.findViewById(R.id.trueANDfalse);

            name.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(enable.isChecked())
            {
                enable.setChecked(false);

                int position=getAdapterPosition();
                tracker[position]=false;
            }
            else {
                enable.setChecked(true);
                int position = getAdapterPosition();
                tracker[position]=true;
            }
            // Log.e("i am position",Integer.toString(position));
        }
    }
}
