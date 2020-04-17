package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EmailActivity extends AppCompatActivity {
    EditText editText_fromEmail, editText_toEmail, editText_subject, editText_message;
    Button button_send, button_attachFile;
    TextView textView_attachment;
    Uri URI = null;
    private static final int PICK_FROM_GALLERY = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        editText_fromEmail = (EditText)findViewById(R.id.editText_fromEmail);
        editText_fromEmail.setEnabled(false);

        editText_toEmail = (EditText)findViewById(R.id.editText_toEmail);
        editText_subject = (EditText)findViewById(R.id.editText_subject);
        editText_message = (EditText)findViewById(R.id.editText_message);
        button_send = (Button)findViewById(R.id.button_send);
        button_attachFile = (Button)findViewById(R.id.button_attachFile);
        textView_attachment = (TextView)findViewById(R.id.textView_file);

        button_attachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFolder();
            }
        });

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    // A function which add the filename to a textView for attachment
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            URI = data.getData();
            if (URI != null) {
                textView_attachment.setText(URI.getLastPathSegment());
            }
            textView_attachment.setVisibility(View.VISIBLE);
        }
    }


    // A function for open phone storage if user wants to attach a file to e-mail.
    public void openFolder() {
        Intent intent = new Intent();
        String [] mimeTypes = {"image/*", "audio/*", "video/*", "application/*"};
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
    }


    // A function which send an e-mail with a client choosing, like G-Mail etc.
    public void sendEmail() {
        try {
            String to = editText_toEmail.getText().toString().trim();
            String subject = editText_subject.getText().toString().trim();
            String message = editText_message.getText().toString().trim();
            if (to.equals("") || subject.equals("") || message.equals("")) {
                Toast.makeText(EmailActivity.this, "Please fill the e-mail contents!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, message);
            if (URI != null)
                emailIntent.putExtra(Intent.EXTRA_STREAM, URI);

            this.startActivity(Intent.createChooser(emailIntent, "Choose an E-mail Client"));
        }
        catch (Throwable t) {
            Toast.makeText(this, "Request failed, try again: "+ t.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
