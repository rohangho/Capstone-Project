package com.example.android.contactshare;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ROHAN on 11-01-2018.
 */

public class AssignmentAdapter extends ArrayAdapter<CustomClassForAssignment> {
    public AssignmentAdapter(@NonNull Context context, int resource, List<CustomClassForAssignment> objects) {
        super(context, resource,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.assignment_list, parent, false);
        }
        TextView massage_to_be_displayed=(TextView)convertView.findViewById(R.id.display_message);
        TextView author_to_display=(TextView)convertView.findViewById(R.id.display_author);

        CustomClassForAssignment messege=getItem(position);
        massage_to_be_displayed.setText(messege.getMessage());
        author_to_display.setText(messege.getAuthur());
        return convertView;
    }
}
