package vaystudios.com.memory.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vaystudios.com.memory.Object.MemoryObject;
import vaystudios.com.memory.R;

/**
 * Created by Devan on 2/4/2017.
 */

public class MemoryObjectAdapter extends ArrayAdapter<MemoryObject> {

    public class ViewHolder
    {
        public TextView title;
    }



    public MemoryObjectAdapter(Context context, ArrayList<MemoryObject> memoryObjects) {
        super(context, 0 , memoryObjects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MemoryObject memoryObject = getItem(position);
        ViewHolder holder = null;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.memory_item_layout, parent, false);
            holder = new ViewHolder();
            holder.title =(TextView)convertView.findViewById(R.id.memory_item_title);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        if(holder != null)
        {


            holder.title.setText(memoryObject.title);

        }


        return convertView;
    }
}
