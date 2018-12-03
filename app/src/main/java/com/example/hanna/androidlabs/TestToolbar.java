package com.example.hanna.androidlabs;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    private String currentMessage = "You selected item 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar lab8_toolbar = findViewById(R.id.lab8_toolbar);
        setSupportActionBar(lab8_toolbar);
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        switch (id) {
            case R.id.option1:
                Log.d("Toolbar", "Option 1 selected");
                Snackbar.make(findViewById(R.id.option1Button), currentMessage, Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.option2:
                Log.d("Toolbar", "Option 2 selected");
                showDialogOption2();
                break;
            case R.id.option3:
                Log.d("Toolbar", "Option 3 selected");
                showDialogOption3();
                break;
            case R.id.aboutItem:
                Toast toast = Toast.makeText(getApplicationContext(), "Version 1.0, by Hanna Bernyk", Toast.LENGTH_LONG);
                toast.show();
                break;

        }
        return true;
    }

    private void showDialogOption2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.menu_dialog_title_option2);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showDialogOption3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.menu_dialog_title_option3);
        LayoutInflater inflater = this.getLayoutInflater();

        View dialogView =  inflater.inflate(R.layout.option_3, null);

        final EditText newMessageText = (EditText)dialogView.findViewById(R.id.newMessage);

        builder.setView(dialogView).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                currentMessage = newMessageText.getText().toString();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    public void onClickOption1Button(View view) {
        Snackbar.make(findViewById(R.id.option1Button), currentMessage, Snackbar.LENGTH_SHORT).show();
    }
}
