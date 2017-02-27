package vaystudios.com.memory.Fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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
import vaystudios.com.memory.MainActivity;
import vaystudios.com.memory.Object.MemoryObject;
import vaystudios.com.memory.R;
import vaystudios.com.memory.Util.Coder.JSONDecoder;
import vaystudios.com.memory.Util.Coder.JSONEncoder;

/**
 * Created by Devan on 2/4/2017.
 */

public class MemoryListFragment extends Fragment
{

    JSONDecoder jsonDecoder;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         jsonDecoder = new JSONDecoder(getActivity(), getContext());
        jsonDecoder.FetchData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateMemoryList();
            }
        },100);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memory_list_fragment, container, false);

        final ListView listView = (ListView)view.findViewById(R.id.listView_memoryListView);
        this.listView = listView;
        ImageView createMemory = (ImageView)view.findViewById(R.id.btn_createMemory);
        TextView welcomeText = (TextView)view.findViewById(R.id.tv_mlf_welcomeText);
        ImageView deleteAll = (ImageView)view.findViewById(R.id.btn_deleteAll);
        welcomeText.setText("Welcome, " + MainActivity.loggedUser.getName());


        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete All Memories. Confirm");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONEncoder encoder = new JSONEncoder(getActivity(), getContext());
                        encoder.deleteAll();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                UpdateMemoryList();

                            }
                        },500);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });



                builder.show();
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateMemoryList();
                Toast.makeText(getContext(), "Refreshed!", Toast.LENGTH_SHORT).show();
            }
        });



        createMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CanvasEditFragment canvasEditFragment = new CanvasEditFragment();

                getFragmentManager().beginTransaction().addToBackStack(canvasEditFragment + "").replace(R.id.activity_main, canvasEditFragment, canvasEditFragment + "").commit();

            }
        });

        // Here the "memory" data needs to be pulled from the server and an arraylist needs to be created from that so
        // we can create a list out of those.

        UpdateMemoryList();



        return view;
    }


    public void UpdateMemoryList()
    {
        if(listView == null) return;

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
                getFragmentManager().beginTransaction().addToBackStack(canvasViewFragment + "").replace(R.id.activity_main, canvasViewFragment, "CANVAS_VIEW_FRAGMENT").commit();

            }
        });
    }




}
