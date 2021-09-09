package com.example.wizbook;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.wizbook.KnownFragment.spell_list_known;
import static com.example.wizbook.PreparedFragment.spell_list_prepared;
import static com.example.wizbook.MainActivity.filePath_known;
import static com.example.wizbook.MainActivity.filePath_prepared;
import static com.example.wizbook.MainActivity.filePath_slotblock ;
import static com.example.wizbook.MainActivity.displaySlots ;
import static com.example.wizbook.MainActivity.spells ;
import static com.example.wizbook.MainActivity.slot_block ;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> localSpells = new ArrayList<String>() ;
    private ArrayList<String> localSchools = new ArrayList<String>() ;
    private ArrayList<String> localSpecials = new ArrayList<String>() ;
    private ArrayList<String> localLevels = new ArrayList<String>() ;
    private ArrayList<String> localConcentration = new ArrayList<String>() ;
    private ArrayList<String> localRitual = new ArrayList<String>() ;
    private ArrayList<String> localInfo = new ArrayList<String>() ;
    public Integer holderType ;
    private Context context ;
    private Listener listener ;
    private Map<String, String> slot_english = new HashMap<>() ;
    {
        slot_english.put("0", "0") ;
        slot_english.put("1", "1st") ;
        slot_english.put("2", "2nd") ;
        slot_english.put("3", "3rd") ;
        slot_english.put("4", "4th") ;
        slot_english.put("5", "5th") ;
        slot_english.put("6", "6th") ;
        slot_english.put("7", "7th") ;
        slot_english.put("8", "8th") ;
        slot_english.put("9", "9th") ;
    }

    interface Listener {
        void refreshPage() ;
        void spellInfo(String concentration, String ritual, String info) ;
    }

    public static class ViewHolder0 extends RecyclerView.ViewHolder {
        private final TextView name, school, special, level ;
        private final Button add_spell, info ;

        public ViewHolder0(View view) {
            super(view) ;

            name = (TextView) view.findViewById(R.id.spell_name_spell) ;
            school = (TextView) view.findViewById(R.id.spell_school_spell) ;
            special = (TextView) view.findViewById(R.id.spell_special_spell) ;
            level = (TextView) view.findViewById(R.id.spell_level_spell) ;

            add_spell = (Button) view.findViewById(R.id.add_button) ;
            info = (Button) view.findViewById(R.id.info_spell_spell) ;
        }

        public TextView getName() { return name ; }
        public TextView getSchool() { return school ; }
        public TextView getSpecial() { return special ; }
        public TextView getLevel() { return level ; }

        public Button getAddSpell() { return add_spell ; }
        public Button getInfo() { return info ;  }
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        private final TextView name, school, special, level ;
        private final Button aors_spell, subtract_spell, cast, info ;

        public ViewHolder1(View view) {
            super(view) ;

            name = (TextView) view.findViewById(R.id.spell_name_known) ;
            school = (TextView) view.findViewById(R.id.spell_school_known) ;
            special = (TextView) view.findViewById(R.id.spell_special_known) ;
            level = (TextView) view.findViewById(R.id.spell_level_known) ;

            aors_spell = (Button) view.findViewById(R.id.aors_button) ;
            cast = (Button) view.findViewById(R.id.cast_known) ;
            subtract_spell = (Button) view.findViewById(R.id.subtract_button_known) ;
            info = (Button) view.findViewById(R.id.info_spell_known) ;
        }

        public TextView getName() { return name ; }
        public TextView getSchool() { return school ; }
        public TextView getSpecial() { return special ; }
        public TextView getLevel() { return level ; }

        public Button getAorsSpell() { return aors_spell ; }
        public Button getCast() { return cast ; }
        public Button getSubtractSpell() { return subtract_spell ; }
        public Button getInfo() { return info ; }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        private final TextView name, school, special, level ;
        private final Button subtract_spell, cast, info ;

        public ViewHolder2(View view) {
            super(view) ;

            name = (TextView) view.findViewById(R.id.spell_name_prepared) ;
            school = (TextView) view.findViewById(R.id.spell_school_prepared) ;
            special = (TextView) view.findViewById(R.id.spell_special_prepared) ;
            level = (TextView) view.findViewById(R.id.spell_level_prepared) ;

            subtract_spell = (Button) view.findViewById(R.id.subtract_button) ;
            cast = (Button) view.findViewById(R.id.cast_prepared) ;
            info = (Button) view.findViewById(R.id.info_spell_prepared) ;
        }

        public TextView getName() { return name ; }
        public TextView getSchool() { return school ; }
        public TextView getSpecial() { return special ; }
        public TextView getLevel() { return level ; }

        public Button getSubtractSpell() { return subtract_spell ; }
        public Button getCast() { return cast ; }
        public Button getInfo() { return info ; }
    }

    public RecyclerAdapter(Listener context, List<List<String>> spell_list, Integer holderType) {

        if (spell_list != null) {
            for (List<String> spell : spell_list) {
                localSpells.add(spell.get(0));
                localSchools.add(spell.get(1));
                localSpecials.add(spell.get(2));
                localLevels.add(spell.get(3));
                localConcentration.add(spell.get(4)) ;
                localRitual.add(spell.get(5)) ;
                localInfo.add(spell.get(6)) ;
            }
        }

        this.holderType = holderType ;
        listener = context ;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext()) ;
        View view ;
        switch (holderType) {
            case 0:
                view = inflater.inflate(R.layout.spell_block_item, parent, false);
                return new ViewHolder0(view) ;
            case 1:
                view = inflater.inflate(R.layout.spell_block_known, parent, false);
                return new ViewHolder1(view) ;
            case 2:
                view = inflater.inflate(R.layout.spell_block_prepared, parent, false);
                return new ViewHolder2(view) ;
            default:
                return null;
        }
    }

    public void spellWriter(String filePath, List<List<String>> spell_list) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            List<String> spell;
            for (int i = 0; i < spell_list.size(); i++) {
                spell = spell_list.get(i);
                writer.write(spell.get(0) + "!" + spell.get(1) + "!" + spell.get(2) + "!" + spell.get(3) + "!" + spell.get(4) + "!" + spell.get(5) + "!" + spell.get(6));
                writer.write("~");
            }

            Log.d("mega", "changed spell_list written to: " + filePath) ;

            writer.close() ;
        }
        catch (FileNotFoundException e) {
            Log.d("mega", "File not found: " + e.toString()) ;
        }
        catch (IOException e) {
            Log.d("mega", "Cannot read file: " + e.toString()) ;
        }
    }

    public static void slotWriter(String filePath, Map<String, Integer> spells) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)) ;

            for (Map.Entry<String, Integer> set : spells.entrySet()) {
                writer.write(set.getKey() + "," + set.getValue()) ;
                writer.write("#") ;
            }

            writer.close() ;
        }
        catch (FileNotFoundException e) {
            Log.d("mega", "File not found: " + e.toString()) ;
        }
        catch (IOException e) {
            Log.d("mega", "Cannot read file: " + e.toString()) ;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView name, school, special, level ;
        Button cast, info ;
        ViewHolder0 viewHolder0 ;
        ViewHolder1 viewHolder1 ;
        ViewHolder2 viewHolder2 ;

        switch (holderType) {
            case 0:
                viewHolder0 = (ViewHolder0) holder ;
                name = viewHolder0.getName();
                school = viewHolder0.getSchool();
                special = viewHolder0.getSpecial();
                level = viewHolder0.getLevel();

                name.setText(localSpells.get(position));
                school.setText(localSchools.get(position));
                special.setText(localSpecials.get(position));
                level.setText(localLevels.get(position));
                Button add_spell = viewHolder0.getAddSpell();
                add_spell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String lvl = slot_english.get(level.getText().toString()) ;

                        ArrayList<String> spell = new ArrayList<String>();
                        spell.add(name.getText().toString());
                        spell.add(school.getText().toString());
                        spell.add(special.getText().toString());
                        spell.add(level.getText().toString());
                        spell.add(localConcentration.get(position)) ;
                        spell.add(localRitual.get(position)) ;
                        spell.add(localInfo.get(position)) ;
                        if (!spell_list_known.contains(spell) && (spells.containsKey(lvl)||lvl.equals("0"))) {
                            spell_list_known.add(spell);
                            spellWriter(filePath_known, spell_list_known) ;
                        }
                    }
                });
                info = viewHolder0.getInfo() ;
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.spellInfo(localConcentration.get(position), localRitual.get(position), localInfo.get(position)) ;
                    }
                });
                break ;
            case 1:
                viewHolder1 = (ViewHolder1) holder ;
                name = viewHolder1.getName();
                school = viewHolder1.getSchool();
                special = viewHolder1.getSpecial();
                level = viewHolder1.getLevel();

                name.setText(localSpells.get(position));
                school.setText(localSchools.get(position));
                special.setText(localSpecials.get(position));
                level.setText(localLevels.get(position));
                Button aors_spell = viewHolder1.getAorsSpell();
                aors_spell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("mega", "shit was clicked");
                        ArrayList<String> spell = new ArrayList<String>();
                        spell.add(name.getText().toString());
                        spell.add(school.getText().toString());
                        spell.add(special.getText().toString());
                        spell.add(level.getText().toString());
                        spell.add(localConcentration.get(position)) ;
                        spell.add(localRitual.get(position)) ;
                        spell.add(localInfo.get(position)) ;
                        if (!spell_list_prepared.contains(spell)) {
                            spell_list_prepared.add(spell);
                            spellWriter(filePath_prepared, spell_list_prepared) ;
                        }
                    }
                });
                cast = viewHolder1.getCast() ;
                if (localLevels.get(position).equals("0")) {
                    cast.setVisibility(View.GONE) ;
                }
                cast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String lvl = slot_english.get(level.getText().toString()) ;
                        if (spells.containsKey(lvl)) {
                            Integer previous_lvl = spells.get(lvl);
                            if (previous_lvl != 0) {
                                spells.replace(lvl, previous_lvl - 1);
                            }
                            slot_block.setText(displaySlots(spells)) ;
                            slotWriter(filePath_slotblock, spells) ;
                        }
                    }
                });
                Button subtract_spell = viewHolder1.getSubtractSpell() ;
                subtract_spell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("mega", "shit was clicked");
                        ArrayList<String> spell = new ArrayList<String>();
                        spell.add(name.getText().toString());
                        spell.add(school.getText().toString());
                        spell.add(special.getText().toString());
                        spell.add(level.getText().toString());
                        spell.add(localConcentration.get(position)) ;
                        spell.add(localRitual.get(position)) ;
                        spell.add(localInfo.get(position)) ;
                        spell_list_known.remove(spell) ;
                        spellWriter(filePath_known, spell_list_known) ;
                        listener.refreshPage() ;
                    }
                });
                info = viewHolder1.getInfo() ;
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.spellInfo(localConcentration.get(position), localRitual.get(position), localInfo.get(position)) ;
                    }
                });
                break ;
            case 2:
                viewHolder2 = (ViewHolder2) holder ;
                name = viewHolder2.getName();
                school = viewHolder2.getSchool();
                special = viewHolder2.getSpecial();
                level = viewHolder2.getLevel();

                name.setText(localSpells.get(position));
                school.setText(localSchools.get(position));
                special.setText(localSpecials.get(position));
                level.setText(localLevels.get(position));
                Button subtract = viewHolder2.getSubtractSpell();
                subtract.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("mega", "shit was clicked");
                        ArrayList<String> spell = new ArrayList<String>();
                        spell.add(name.getText().toString());
                        spell.add(school.getText().toString());
                        spell.add(special.getText().toString());
                        spell.add(level.getText().toString());
                        spell.add(localConcentration.get(position)) ;
                        spell.add(localRitual.get(position)) ;
                        spell.add(localInfo.get(position)) ;
                        spell_list_prepared.remove(spell) ;
                        spellWriter(filePath_prepared, spell_list_prepared) ;
                        listener.refreshPage() ;
                    }
                });
                cast = viewHolder2.getCast() ;
                if (localLevels.get(position).equals("0")) {
                    cast.setVisibility(View.GONE) ;
                }
                cast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String lvl = slot_english.get(level.getText().toString()) ;
                        if (spells.containsKey(lvl)) {
                            Integer previous_lvl = spells.get(lvl);
                            if (previous_lvl != 0) {
                                spells.replace(lvl, previous_lvl - 1);
                            }
                            slot_block.setText(displaySlots(spells)) ;
                            slotWriter(filePath_slotblock, spells) ;
                        }
                    }
                });
                info = viewHolder2.getInfo() ;
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.spellInfo(localConcentration.get(position), localRitual.get(position), localInfo.get(position)) ;
                    }
                });
                break ;
        }
    }

    @Override
    public int getItemCount() {
        return localSpells.size() ;
    }
}
