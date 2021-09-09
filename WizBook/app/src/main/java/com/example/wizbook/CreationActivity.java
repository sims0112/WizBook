package com.example.wizbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static com.example.wizbook.MainActivity.filePath_known;
import static com.example.wizbook.MainActivity.filePath_prepared;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreationActivity extends AppCompatActivity {

    public Map<Button, Boolean> flags = new HashMap<>() ;
    public Map<Integer, String> levels = new HashMap<>() ;
    public Map<Button, EditText> edits = new HashMap<>() ;
    // levels2 is for main activity
    public Map<String, String> levels2 = new HashMap<>() ;
    public EditText t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,edit_name ;
    public Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12 ;
    public List<List<String>> spell_list = new ArrayList<>();
    public static final String CHAR_NAME = "com.example.WizBook.NAME" ;
    public static final String CHOSEN_CLASSES = "com.example.WizBook.CLASSES" ;
    public static final String ALL_SPELLS = "com.example.WizBook.ALLSPELLS" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        updateSpells() ;

        edit_name = findViewById(R.id.editTextTextPersonName2) ;

        t1 = findViewById(R.id.textView4) ;
        t2 = findViewById(R.id.textView7) ;
        t3 = findViewById(R.id.textView8) ;
        t4 = findViewById(R.id.textView9) ;
        t5 = findViewById(R.id.textView10) ;
        t6 = findViewById(R.id.textView11) ;
        t7 = findViewById(R.id.textView12) ;
        t8 = findViewById(R.id.textView13) ;
        t9 = findViewById(R.id.textView14) ;
        t10 = findViewById(R.id.textView15) ;
        t11 = findViewById(R.id.textView16) ;
        t12 = findViewById(R.id.textView17) ;

        b1 = findViewById(R.id.button3);
        b2 = findViewById(R.id.button4) ;
        b3 = findViewById(R.id.button5) ;
        b4 = findViewById(R.id.button6) ;
        b5 = findViewById(R.id.button7) ;
        b6 = findViewById(R.id.button8) ;
        b7 = findViewById(R.id.button9) ;
        b8 = findViewById(R.id.button10) ;
        b9 = findViewById(R.id.button11) ;
        b10 = findViewById(R.id.button12) ;
        b11 = findViewById(R.id.button13) ;
        b12 = findViewById(R.id.button14) ;

        flags.put(b1, false) ;
        flags.put(b2, false) ;
        flags.put(b3, false) ;
        flags.put(b4, false) ;
        flags.put(b5, false) ;
        flags.put(b6, false) ;
        flags.put(b7, false) ;
        flags.put(b8, false) ;
        flags.put(b9, false) ;
        flags.put(b10, false) ;
        flags.put(b11, false) ;
        flags.put(b12, false) ;

        edits.put(b1, t1) ;
        edits.put(b2, t2) ;
        edits.put(b3, t3) ;
        edits.put(b4, t4) ;
        edits.put(b5, t5) ;
        edits.put(b6, t6) ;
        edits.put(b7, t7) ;
        edits.put(b8, t8) ;
        edits.put(b9, t9) ;
        edits.put(b10, t10) ;
        edits.put(b11, t11) ;
        edits.put(b12, t12) ;

        t1.setEnabled(false) ;
        t2.setEnabled(false) ;
        t3.setEnabled(false) ;
        t4.setEnabled(false) ;
        t5.setEnabled(false) ;
        t6.setEnabled(false) ;
        t7.setEnabled(false) ;
        t8.setEnabled(false) ;
        t9.setEnabled(false) ;
        t10.setEnabled(false) ;
        t11.setEnabled(false) ;
        t12.setEnabled(false) ;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE) ;
        for (Map.Entry<Button, EditText> set : edits.entrySet()) {

            EditText edit = set.getValue() ;
            edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0) ;
                        edit.setEnabled(false) ;
                    }
                }
            });
        }
    }

    private void writeToFile(String name) {
        try {
            String filePath = this.getFilesDir().getPath().toString() + "/wiz_book_concrete.txt" ;
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)) ;

            writer.write(name) ;
            writer.newLine() ;

            for (Map.Entry<String, String> set : levels2.entrySet()) {
                writer.write(set.getKey() + "," + set.getValue()) ;
                writer.write("#") ;
            }
            writer.newLine() ;

            List<String> spell ;
            for (int i = 0; i < spell_list.size(); i++) {
                spell = spell_list.get(i) ;
                writer.write(spell.get(0) + "!" + spell.get(1) + "!" + spell.get(2) + "!" + spell.get(3) + "!" + spell.get(4) + "!" + spell.get(5) + "!" + spell.get(6)) ;
                writer.write("~") ;
            }

            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            Log.d("mega", e.toString()) ;
        }

    }

    public void updateSpells() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("https://www.aidedd.org/dnd-filters/spells.php").get() ;


                    for (Element row : document.select("table#liste tbody tr")) {

                        ArrayList<String> spell = new ArrayList<String>() ;
                        spell.add(row.select("td a, strong").text()) ;
                        spell.add(row.select("td.col1").text()) ;
                        spell.add(row.select("td.col2").text()) ;
                        spell.add(row.select("td.center").text()) ;   //making sure the level is added 4th
                        spell.add(row.select("td.col3").text()) ;
                        spell.add(row.select("td.col4").text()) ;
                        spell.add(row.select("td.col5").text()) ;
                        spell_list.add(spell) ;
                    }
                }
                catch (Exception e) {
                    Log.d("mega", e.toString());
                }
            }
        } ;

        Thread downloadThread = new Thread(runnable) ;
        downloadThread.start() ;
    }

    public void changeColor(View view) {

        Button button = (Button) view ;
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE) ;
        EditText edit = edits.get(button) ;

        if (flags.get(button) == false) {

            edit.setEnabled(true) ;
            edit.requestFocus() ;
            imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT) ;
            button.setBackgroundColor(getResources().getColor(R.color.purple_200));

            // the map.replace() method isn't in older builds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                flags.replace(button, true) ;
            }
        }

        else {

            button.setBackgroundColor(Color.parseColor("#F5F5F5"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                flags.replace(button, false) ;
            }
            edit.setText("") ;
        }
    }

    public Boolean lvlChecker() {

        Boolean any_bad = false ;
        Integer total = 0 ;
        for (Map.Entry<Integer, String> set : levels.entrySet()) {

            Integer id = set.getKey() ;
            String lvl = set.getValue() ;
            Button button = findViewById(id) ;
            if (!lvl.equals("")) {
                total += Integer.parseInt(lvl);
                levels2.put(button.getText().toString(), lvl) ;
            }

            if (flags.get(button) == true & ( lvl.equals("0") || lvl.length() == 0 )) {
                any_bad = true ;
            }

        }

        if (total > 20) {
            any_bad = true ;
        }

        return any_bad ;
    }

    public Boolean classChecker() {

        Boolean bool = false ;
        for (Map.Entry<Button, Boolean> set : flags.entrySet()) {

            if (set.getValue() == true) {
                bool = true ;
            }

        }

        return bool ;
    }

    public void createCharacter(View view) {


        String s1 = t1.getText().toString() ;
        String s2 = t2.getText().toString() ;
        String s3 = t3.getText().toString() ;
        String s4 = t4.getText().toString() ;
        String s5 = t5.getText().toString() ;
        String s6 = t6.getText().toString() ;
        String s7 = t7.getText().toString() ;
        String s8 = t8.getText().toString() ;
        String s9 = t9.getText().toString() ;
        String s10 = t10.getText().toString() ;
        String s11 = t11.getText().toString() ;
        String s12 = t12.getText().toString() ;

        levels.put(b1.getId(), s1) ;
        levels.put(b2.getId(), s2) ;
        levels.put(b3.getId(), s3) ;
        levels.put(b4.getId(), s4) ;
        levels.put(b5.getId(), s5) ;
        levels.put(b6.getId(), s6) ;
        levels.put(b7.getId(), s7) ;
        levels.put(b8.getId(), s8) ;
        levels.put(b9.getId(), s9) ;
        levels.put(b10.getId(), s10) ;
        levels.put(b11.getId(), s11) ;
        levels.put(b12.getId(), s12) ;

        String name = edit_name.getText().toString() ;

        if (lvlChecker() == true || classChecker() == false) {
            this.createError(Bundle.EMPTY).show();
        }

        else {
            Intent intent = new Intent(this, MainActivity.class) ;
            if (name.trim().isEmpty()) {
                name = "Character Name" ;
            }
            intent.putExtra(CHAR_NAME, name) ;
            // levels, a map object, must be 'serializable' before being passed by the intent
            intent.putExtra(CHOSEN_CLASSES, (Serializable) levels2) ;
            intent.putExtra(ALL_SPELLS, (Serializable) spell_list) ;
            writeToFile(name) ;

            File known = new File(filePath_known) ;
            File prepared = new File(filePath_prepared) ;

            if (known.delete()) {
                Log.d("mega", "known deleted") ;
            }
            else {
                Log.d("mega", "known was not deleted?") ;
            }
            prepared.delete() ;

            startActivity(intent) ;
        }
        //startActivity(intent) ;

    }

    public Dialog createError(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this) ;

        if (classChecker() == true) {
            builder.setMessage("Your chosen classes must have non-zero level to them and must sum up to no more than 20") ;
        }

        else {
            builder.setMessage("You must choose at least one class") ;
        }

        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }) ;

        return builder.create() ;
    }
}