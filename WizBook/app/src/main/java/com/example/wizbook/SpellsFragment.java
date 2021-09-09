package com.example.wizbook;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpellsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpellsFragment extends Fragment implements RecyclerAdapter.Listener {
    public RecyclerAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    public static List<List<String>> spell_list;

    public SpellsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SpellsFragment newInstance(List<List<String>> spell_list) {
        SpellsFragment fragment = new SpellsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) spell_list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            spell_list = (List<List<String>>) getArguments().get(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_spells, container, false);

        Log.d("mega", "spellsfragment spell_list: " + spell_list.toString()) ;
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView3);
        adapter = new RecyclerAdapter(this, spell_list, 0);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void refreshPage() {

    }

    @Override
    public void spellInfo(String concentration, String ritual, String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        builder.setMessage(info + "\n" + concentration + "\n" + ritual);

        builder.show();
    }
}