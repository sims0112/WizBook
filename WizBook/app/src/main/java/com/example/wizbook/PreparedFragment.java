package com.example.wizbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.wizbook.MainActivity.filePath_prepared;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreparedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreparedFragment extends Fragment implements RecyclerAdapter.Listener {
    public RecyclerAdapter adapter ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    public static List<List<String>> spell_list_prepared = new ArrayList<>();
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath_prepared));

            List<String> list = new ArrayList<String>() ;
            String line ;
            while ((line = reader.readLine()) != null) {
                list.add(line) ;
            }

            List<String> info ;
            for (String spell : list.get(0).split("~")) {
                info = Arrays.asList(spell.split("!")) ;
                spell_list_prepared.add(info) ;
            }

            //Log.d("mega", "preparedfragment: " + list.toString()) ;

            reader.close() ;
        }
        catch (FileNotFoundException e) {
            Log.d("mega", "File not found: " + e.toString()) ;
        }
        catch (IOException e) {
            Log.d("mega", "Cannot read file: " + e.toString()) ;
        }
    }

    public PreparedFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PreparedFragment newInstance(List<List<String>> spell_list_prepared) {
        PreparedFragment fragment = new PreparedFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) spell_list_prepared);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            spell_list_prepared = (List<List<String>>) getArguments().get(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("mega", "preparedfragment view created") ;
        View view = inflater.inflate(R.layout.fragment_prepared, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView2) ;
        adapter = new RecyclerAdapter(this, spell_list_prepared, 2) ;

        recyclerView.setAdapter(adapter) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view ;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit() ;
        }
    }

    @Override
    public void refreshPage() {
        setUserVisibleHint(false) ;
    }

    @Override
    public void spellInfo(String concentration, String ritual, String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext()) ;

        builder.setMessage(info + "\n" + concentration + "\n" + ritual) ;

        builder.show() ;
    }
}