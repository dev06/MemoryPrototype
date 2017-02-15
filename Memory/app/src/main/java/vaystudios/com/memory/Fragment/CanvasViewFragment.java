package vaystudios.com.memory.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import vaystudios.com.memory.Object.MemoryObject;
import vaystudios.com.memory.R;
import vaystudios.com.memory.View.CanvasView;

/**
 * Created by Devan on 2/13/2017.
 */

public class CanvasViewFragment extends Fragment {

    MemoryObject memoryObject;
    RelativeLayout relativeLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.canvas_view_fragment_layout, container, false);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.canvas_view_layout);
        if(memoryObject != null)
        {
            relativeLayout.removeAllViews();

            if(memoryObject.texts != null)
            {
                for(int i = 0;i < memoryObject.texts.size(); i++)
                {
                    memoryObject.texts.get(i).setInteract(false);
                    relativeLayout.addView(memoryObject.texts.get(i));
                }

            }

            if(memoryObject.bitmaps != null)
            {
                for(int i = 0;i < memoryObject.bitmaps.size(); i++)
                {
                    memoryObject.bitmaps.get(i).setInteract(false);
                    relativeLayout.addView(memoryObject.bitmaps.get(i));
                }
            }

        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Toast.makeText(getContext(), "View Destroyed", Toast.LENGTH_SHORT).show();
        relativeLayout.removeAllViews();
    }

    public void setMemoryObject(MemoryObject memoryObject)
    {
        this.memoryObject = memoryObject;
    }

}
