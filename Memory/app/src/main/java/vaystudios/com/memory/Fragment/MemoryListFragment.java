package vaystudios.com.memory.Fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import vaystudios.com.memory.Adapter.MemoryObjectAdapter;
import vaystudios.com.memory.Database.DatabaseHandler;
import vaystudios.com.memory.MainActivity;
import vaystudios.com.memory.Object.MemoryObject;
import vaystudios.com.memory.R;
import vaystudios.com.memory.View.CanvasView;

/**
 * Created by Devan on 2/4/2017.
 */

public class MemoryListFragment extends Fragment
{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memory_list_fragment, container, false);

        final ListView listView = (ListView)view.findViewById(R.id.listView_memoryListView);
        ImageView createMemory = (ImageView)view.findViewById(R.id.btn_createMemory);
        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity().getBaseContext());
        databaseHandler.open();
        MainActivity.memoryObjects = databaseHandler.getAllMemory();
        databaseHandler.close();


        createMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CanvasEditFragment canvasEditFragment = new CanvasEditFragment();

                getFragmentManager().beginTransaction().addToBackStack(canvasEditFragment + "").replace(R.id.activity_main, canvasEditFragment, canvasEditFragment + "").commit();

            }
        });


        final MemoryObjectAdapter adapter = new MemoryObjectAdapter(getActivity().getApplicationContext(), MainActivity.memoryObjects);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l)
            {
                //The is called when you click on a memory from the memory list fragment
                final MemoryObject memoryObject = (MemoryObject)adapterView.getItemAtPosition(i);
                CanvasViewFragment canvasViewFragment = new CanvasViewFragment();
                canvasViewFragment.setMemoryObject(memoryObject);
                getFragmentManager().beginTransaction().addToBackStack(canvasViewFragment + "").replace(R.id.activity_main, canvasViewFragment, canvasViewFragment + "").commit();


//
//
//                final TextView textView = new TextView(getContext());
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setView(textView);
//                builder.setTitle("Delete");
//                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity().getBaseContext());
//                        databaseHandler.open();
//                        databaseHandler.deleteMemory(memoryObject.id);
//                        MainActivity.memoryObjects.clear();
//                        MainActivity.memoryObjects.addAll(databaseHandler.getAllMemory());
//                        adapter.notifyDataSetChanged();
//                        databaseHandler.close();
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//
//                builder.show();
//
//
//


            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //The is called when you click on a memory from the memory list fragment

                final MemoryObject memoryObject = (MemoryObject)parent.getItemAtPosition(position);


                final TextView textView = new TextView(getContext());
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(textView);
                builder.setTitle("Delete");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity().getBaseContext());
                        databaseHandler.open();
                        databaseHandler.deleteMemory(memoryObject.id);
                        MainActivity.memoryObjects.clear();
                        MainActivity.memoryObjects.addAll(databaseHandler.getAllMemory());
                        adapter.notifyDataSetChanged();
                        databaseHandler.close();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();


                return true;
            }
        });

        return view;
    }
}
