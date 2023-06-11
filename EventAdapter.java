package com.example.ngonew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends BaseAdapter {

    private Context context;
    private List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.activity_itemevent, parent, false);
        }

        TextView eventNameTextView = convertView.findViewById(R.id.eventNameTextView);
        TextView eventDescriptionTextView = convertView.findViewById(R.id.eventDescriptionTextView);
        TextView eventDateTextView = convertView.findViewById(R.id.eventDateTextView);

        Event event = eventList.get(position);
        eventNameTextView.setText(event.getEventName());
        eventDescriptionTextView.setText(event.getEventDescription());
        eventDateTextView.setText(event.getEventDate());

        return convertView;
    }
}
