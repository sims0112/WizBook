package com.example.wizbook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import static com.example.wizbook.RecyclerAdapter.slotWriter ;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /** There is an issue where on install the fragments (spells, known, prepared) arn't being created. After restarting the app a couple of times, then it creates?
     */

    public Map<String, String> levels = new HashMap<>() ;
    //this is for recycler adapter
    public static Map<String, Integer> spells = new LinkedHashMap<>() ;;
    public Double total_lvl ;
    // ignoring spells known and prepared (and possibly known cantrips) because spells known and prepared is a very tedious thing to implement when I can just trust the user to know what spells they have. Cantrip limits might not be used because I'm not imposing other limits
    public Map<Integer, TabItem> tabs = new HashMap<>() ;
    public List<List<String>> spell_list = new ArrayList<>();
    public static final String ALL_SPELLS = "com.example.WizBook.ALLSPELLS" ;
    public static TextView slot_block ;
    private Integer caster_level ;
    public static String filePath_allspells, filePath_known, filePath_prepared, filePath_slotblock ;

    public void onMakeChar(View view) {
        spells = new LinkedHashMap<>() ;
        Intent intent = new Intent(this, CreationActivity.class) ;
        startActivity(intent) ;
    }

    public String classBuild() {

        String build = "" ;
        for (Map.Entry<String, String> set : levels.entrySet()) {
             build += set.getKey() + " " + set.getValue() + "/ " ;
        }

        return build ;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void calcCasterLevel() {

        total_lvl = 0.0;
        String type;
        Double lvl;
        for (Map.Entry<String, String> set : levels.entrySet()) {

            type = set.getKey();
            lvl = Double.parseDouble(set.getValue());

            switch (type) {
                case "Artificer":
                    total_lvl += Math.ceil(lvl / 2);
                    break;

                case "Bard":

                case "Cleric":

                case "Druid":

                case "Sorcerer":

                case "Wizard":
                    total_lvl += lvl;
                    break;

                case "Paladin":

                case "Ranger":
                    total_lvl += Math.floor(lvl / 2);
                    break;

                case "Eldritch Knight":

                case "Arcane Trickster":
                    total_lvl += Math.floor(lvl / 3);
                    break;
            }

        }
        caster_level = total_lvl.intValue();
    }
    public void calcSpellSlots(Integer caster_level) {
        // the cases of 11,13,15 are duplicates of 12,14,16 respectively
        switch (caster_level) {
            case 0 : break ;

            case 1 :
                spells.put("1st", 2) ;
                break ;

            case 2 :
                spells.put("1st", 3) ;
                break ;

            case 3 :
                spells.put("1st", 4) ;
                spells.put("2nd", 2) ;
                break ;

            case 4 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                break ;

            case 5 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 2) ;
                break ;

            case 6 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                break ;

            case 7 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                spells.put("4th", 1) ;
                break ;

            case 8 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                spells.put("4th", 2) ;
                break ;

            case 9 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                spells.put("4th", 3) ;
                spells.put("5th", 1) ;
                break ;

            case 10 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                spells.put("4th", 3) ;
                spells.put("5th", 2) ;
                break ;

            case 11 :

            case 12 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                spells.put("4th", 3) ;
                spells.put("5th", 2) ;
                spells.put("6th", 1) ;
                break ;

            case 13 :

            case 14 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                spells.put("4th", 3) ;
                spells.put("5th", 2) ;
                spells.put("6th", 1) ;
                spells.put("7th", 1) ;
                break;

            case 15 :

            case 16 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                spells.put("4th", 3) ;
                spells.put("5th", 2) ;
                spells.put("6th", 1) ;
                spells.put("7th", 1) ;
                spells.put("8th", 1) ;
                break ;

            case 17 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                spells.put("4th", 3) ;
                spells.put("5th", 2) ;
                spells.put("6th", 1) ;
                spells.put("7th", 1) ;
                spells.put("8th", 1) ;
                spells.put("9th", 1) ;
                break ;

            case 18 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                spells.put("4th", 3) ;
                spells.put("5th", 3) ;
                spells.put("6th", 1) ;
                spells.put("7th", 1) ;
                spells.put("8th", 1) ;
                spells.put("9th", 1) ;
                break ;

            case 19 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                spells.put("4th", 3) ;
                spells.put("5th", 3) ;
                spells.put("6th", 2) ;
                spells.put("7th", 1) ;
                spells.put("8th", 1) ;
                spells.put("9th", 1) ;
                break ;

            case 20 :
                spells.put("1st", 4) ;
                spells.put("2nd", 3) ;
                spells.put("3rd", 3) ;
                spells.put("4th", 3) ;
                spells.put("5th", 3) ;
                spells.put("6th", 2) ;
                spells.put("7th", 2) ;
                spells.put("8th", 1) ;
                spells.put("9th", 1) ;
                break ;
        }
    }

    public static String displaySlots(Map<String, Integer> spells) {

        String slot_lvls = "Level" ;
        String slots = "Slot" ;
        for (Map.Entry<String, Integer> set : spells.entrySet()) {
            slot_lvls += String.format("%5s", set.getKey()) ;
            slots += String.format("%5d  ", set.getValue()) ;
        }

        String slot_block = slot_lvls + "\n" + slots ;

        return slot_block ;
    }

    public void onLongRest(View view) {
        calcSpellSlots(caster_level);
        Log.d("mega", spells.toString()) ;
        slot_block = findViewById(R.id.textView3) ;
        slot_block.setText(displaySlots(spells)) ;
        slotWriter(filePath_slotblock, spells);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            filePath_known = this.getFilesDir().getPath() + "/wiz_book_known.txt";
            filePath_prepared = this.getFilesDir().getPath() + "/wiz_book_prepared.txt";
            filePath_slotblock = this.getFilesDir().getPath() + "/wiz_book_slotblock.txt" ;

            String filePath = this.getFilesDir().getPath() + "/wiz_book_concrete.txt" ;
            BufferedReader reader = new BufferedReader(new FileReader(filePath)) ;

            List<String> list = new ArrayList<String>() ;
            String line ;
            while ((line = reader.readLine()) != null) {
                list.add(line) ;
            }
            TextView char_name = findViewById(R.id.textView2) ;
            char_name.setText(list.get(0)) ;

            String[] cLvl ;
            for (String pair : list.get(1).split("#")) {
                cLvl = pair.split(",") ;
                levels.put(cLvl[0], cLvl[1]) ;
            }
            TextView class_build = findViewById(R.id.textView) ;
            class_build.setText(classBuild()) ;

            List<String> info ;
            for (String spell : list.get(2).split("~")) {
                info = Arrays.asList(spell.split("!")) ;
                spell_list.add(info) ;
            }
            Log.d("mega", "flag") ;
            Log.d("mega", spell_list.toString()) ;

            reader.close() ;

            calcCasterLevel();
            calcSpellSlots(caster_level) ;
            BufferedReader reader2 = new BufferedReader(new FileReader(filePath_slotblock));
            String s = "" ;
            String line2 ;
            while ((line2 = reader2.readLine()) != null) {
                s += line2 ;
            }
            String[] slots ;
            for (String pair : s.split("#")) {
                slots = pair.split(",") ;
                spells.put(slots[0], Integer.parseInt(slots[1])) ;
            }

            reader2.close();
        }
        catch (FileNotFoundException e) {
            Log.d("mega", "File not found: " + e.toString()) ;
        }
        catch (IOException e) {
            Log.d("mega", "Cannot read file: " + e.toString()) ;
        }


        slot_block = findViewById(R.id.textView3) ;
        slot_block.setText(displaySlots(spells)) ;

        TabLayout tabLayout = findViewById(R.id.tabBar) ;
        ViewPager viewPager = findViewById(R.id.ViewPager) ;

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this, spell_list) ;
        viewPager.setAdapter(pagerAdapter) ;
        //tabLayout.setupWithViewPager(viewPager) ;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position ==1 ) {
                    pagerAdapter.getItem(position) ;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /*@Override
    public void onPause() {
        super.onPause() ;
        Log.d("mega", "PAUSED!!!") ;

        try {
            String filePath = this.getFilesDir().getPath() + "/wiz_book_changeable.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            List<String> spell ;
            for (int i = 0; i < spell_list_known.size(); i++) {
                spell = spell_list_known.get(i) ;
                writer.write(spell.get(0) + "," + spell.get(1) + "," + spell.get(2) + "," + spell.get(3) + "," + spell.get(4) + "," + spell.get(5) + "," + spell.get(6)) ;
                writer.write("#") ;
            }
            writer.newLine() ;
            for (int i = 0; i < spell_list_prepared.size(); i++) {
                spell = spell_list_prepared.get(i) ;
                writer.write(spell.get(0) + "," + spell.get(1) + "," + spell.get(2) + "," + spell.get(3) + "," + spell.get(4) + "," + spell.get(5) + "," + spell.get(6)) ;
                writer.write("#") ;
            }
        }
        catch (FileNotFoundException e) {
            Log.d("mega", "File not found: " + e.toString()) ;
        }
        catch (IOException e) {
            Log.d("mega", "Cannot read file: " + e.toString()) ;
        }
    }*/

    public void calcCantrip(String type, Integer lvl) {
        Integer total_cantrip = 0 ;

        switch (type) {
            case "Cleric" :

            case "Wizard" :
                switch (lvl) {
                    case 1 :

                    case 2 :

                    case 3 : total_cantrip = 3 ; break ;

                    case 4 :

                    case 5 :

                    case 6 :

                    case 7 :

                    case 8 :

                    case 9 : total_cantrip = 4 ; break ;

                    case 10 :

                    case 11 :

                    case 12 :

                    case 13 :

                    case 14 :

                    case 15 :

                    case 16 :

                    case 17 :

                    case 18 :

                    case 19 :

                    case 20 : total_cantrip = 5 ; break ;
                }
                break ;

            case "Bard" :

            case "Druid" :

            case "Warlock" :
                switch (lvl) {
                    case 1 :

                    case 2 :

                    case 3 : total_cantrip = 2 ; break ;

                    case 4 :

                    case 5 :

                    case 6 :

                    case 7 :

                    case 8 :

                    case 9 : total_cantrip = 3 ; break ;

                    case 10 :

                    case 11 :

                    case 12 :

                    case 13 :

                    case 14 :

                    case 15 :

                    case 16 :

                    case 17 :

                    case 18 :

                    case 19 :

                    case 20 : total_cantrip = 4 ; break ;
                }
                break ;

            case "Artificer" :
                switch (lvl) {
                    case 1 :

                    case 2 :

                    case 3 :

                    case 4 :

                    case 5 :

                    case 6 :

                    case 7 :

                    case 8 :

                    case 9 : total_cantrip = 2 ; break ;

                    case 10 :

                    case 11 :

                    case 12 :

                    case 13 : total_cantrip = 3 ; break ;

                    case 14 :

                    case 15 :

                    case 16 :

                    case 17 :

                    case 18 :

                    case 19 :

                    case 20 : total_cantrip = 4 ; break ;
                }
                break ;

            case "Sorcerer" :
                switch (lvl) {
                    case 1 :

                    case 2 :

                    case 3 : total_cantrip = 4 ; break ;

                    case 4 :

                    case 5 :

                    case 6 :

                    case 7 :

                    case 8 :

                    case 9 : total_cantrip = 5 ; break ;

                    case 10 :

                    case 11 :

                    case 12 :

                    case 13 :

                    case 14 :

                    case 15 :

                    case 16 :

                    case 17 :

                    case 18 :

                    case 19 :

                    case 20 : total_cantrip = 6 ; break ;
                }
                break ;

            case "Eldritch Knight" :

            case "Arcane Trickster" :
                // cantrips known are reduced by one because knowing mage hand is a requirement
                switch (lvl) {
                    case 1 :

                    case 2 : break ;

                    case 3 :

                    case 4 :

                    case 5 :

                    case 6 :

                    case 7 :

                    case 8 :

                    case 9 : total_cantrip = 2 ; break ;

                    case 10 :

                    case 11 :

                    case 12 :

                    case 13 :

                    case 14 :

                    case 15 :

                    case 16 :

                    case 17 :

                    case 18 :

                    case 19 :

                    case 20 : total_cantrip = 3 ; break ;
                }
                break ;
        }

        spells.put(type, total_cantrip) ;
    }
}