package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class NoteActivity extends AppCompatActivity {
    EditText editText_title, editText_note;
    Button button_saveNote;

    String filename, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editText_title = (EditText)findViewById(R.id.editText_title);
        editText_note = (EditText)findViewById(R.id.editText_note);
        button_saveNote = (Button)findViewById(R.id.button_saveNote);

        Intent myIntent = getIntent(); // An Intent for getting filename from NotesActivity to show the note which user wants to edit or see
        String fileNameFromListView = myIntent.getStringExtra("FILE_SELECTED"); // Get the filename which is send by putExtra() method
        editText_title.setText(fileNameFromListView); // A : It will come true only if we get the filename from putExtra(), so if user click the ListView item or Edit from ContextMenu
        loadTextFile(this, fileNameFromListView); // A

        button_saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filename = editText_title.getText().toString().trim();
                content = editText_note.getText().toString().trim();
                if (!filename.equals("") && !content.equals("")) {
                    saveTextFile(filename, content);
                    finish(); // Finishing NoteActivity after saving the note and come back to NotesActivity to see our notes
                }
                else
                    Toast.makeText(getBaseContext(), "Please fill the title and content!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // A function which save the note to the Internal Storage by filename and content
    public void saveTextFile(String filename, String content) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(filename + ".txt", MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
            File fileDir = new File(getFilesDir(), filename + ".txt");
            Toast.makeText(getBaseContext(), "Note is saved.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // A function which load the note from the Internal Storage by filename
    public void loadTextFile(Context context, String filename) {
        try {

            FileInputStream fis = context.openFileInput(filename + ".txt");
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            editText_note.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
