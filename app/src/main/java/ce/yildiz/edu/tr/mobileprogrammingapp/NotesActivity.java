package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView_files;
    Button button_addNote;

    String[] myNoteFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        listView_files = (ListView)findViewById(R.id.listView_files);
        button_addNote = (Button)findViewById(R.id.button_addNote);

        button_addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDirectlyAddNote = new Intent(NotesActivity.this, NoteActivity.class);
                startActivity(intentDirectlyAddNote);
            }
        });

        showSavedFiles();
        listView_files.setOnItemClickListener(this);
        registerForContextMenu(listView_files);
    }


    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        showTheNote(position); // position : which ListView item is choosed?
    }


    // A function which create a ContextMenu after long clicking to ListView items (notes)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listView_files) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String filename = (String) listView_files.getItemAtPosition(info.position);
            menu.setHeaderTitle(filename);
            menu.add(Menu.NONE, 0, 0, getString(R.string.edit));
            menu.add(Menu.NONE, 1, 1, getString(R.string.delete));

        }
    }


    // A function which do something by clicking the ContextMenu items (Edit or Delete)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String filename = (String) listView_files.getItemAtPosition(info.position);
        if (menuItemIndex == 0)  // Edit
            showTheNote(info.position);
        else if (menuItemIndex == 1) // Delete
            deleteTheNote(filename);
        return true;
    }


    // An override function which updates the activity after some action like delete or add
    @Override
    public void onResume() {
        super.onResume();
        showSavedFiles();
    }


    // A function which show the notes from Internal Storage on a ListView
    public void showSavedFiles() {
        myNoteFiles = getApplicationContext().fileList();
        ArrayList<String> myNoteFilesChange = new ArrayList<>(); // For get rid of from .txt extension to show it in activity
        for (String file : myNoteFiles) {
            if (file.indexOf(".") > 0) {
                String filenameWithoutTxt = file.substring(0, file.lastIndexOf("."));
                myNoteFilesChange.add(filenameWithoutTxt);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, myNoteFilesChange);
        listView_files.setAdapter(adapter);
    }


    // A function which show the note on NoteActivity with using putExtra() with Intent
    public void showTheNote(int position) {
        Intent noteActivityIntent = new Intent(NotesActivity.this, NoteActivity.class);
        String filename = (String) listView_files.getItemAtPosition(position);
        noteActivityIntent.putExtra("FILE_SELECTED", filename);
        startActivity(noteActivityIntent);
    }


    // A function which delete a note from Internal Storage by filename
    private void deleteTheNote(String filename) {
        File dir = getFilesDir();
        File file = new File(dir, filename + ".txt");
        boolean deleted = file.delete();
        if (deleted)
            Toast.makeText(NotesActivity.this,"'" + filename + "'" + " is deleted.",Toast.LENGTH_SHORT).show();
        showSavedFiles();
    }
}
