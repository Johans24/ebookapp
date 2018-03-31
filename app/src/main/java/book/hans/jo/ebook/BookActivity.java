package book.hans.jo.ebook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import lib.folderpicker.FolderPicker;

public class BookActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        TextView title = findViewById(R.id.title);
        TextView author = findViewById(R.id.author);
        ImageView cover = findViewById(R.id.cover);
        Button donwload = findViewById(R.id.download);

        title.setText(getIntent().getStringExtra("title"));
        author.setText(getIntent().getStringExtra("author"));
        loadImage();
        donwload.setOnClickListener(this);
    }

    private void loadImage() {
        File imgFile = new File(getIntent().getStringExtra("cover"));
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView cover = findViewById(R.id.cover);
            cover.setImageBitmap(myBitmap);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, FolderPicker.class);
        startActivityForResult(intent, 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("RES","RR"+requestCode+" "+resultCode+" " + data.toString());
        if (requestCode == 1234) {
            if (resultCode == Activity.RESULT_OK) {
                downloadTo(data.getStringExtra("data"));

            } else {
                // Nothing selected
            }
        }
    }

    private void downloadTo(String path)
    {
        Log.i("DOWNLOAD",path);

        String location = getIntent().getStringExtra("location");
        File source = new File(location);

        File destination = new File(path + "/"+source.getName());
        try
        {
            FileUtils.copyFile(source, destination);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
