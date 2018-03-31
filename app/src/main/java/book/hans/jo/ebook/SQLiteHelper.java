package book.hans.jo.ebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mota on 31/3/2018.
 */

public class SQLiteHelper {

    private SQLiteDatabase db;
    private String databaseLocation;
    private static SQLiteHelper instance;
    private SQLiteHelper()
    {
         // TODO AQUI TENGO QUE VER DONDE CARAJO ESTA LA BD!
         databaseLocation = findDatabase();
         Log.i("DBL",databaseLocation);
         db = SQLiteDatabase.openDatabase(databaseLocation +"/metadata.db", null, 0);
    }

    public static SQLiteHelper getInstance()
    {
        if (instance == null)
            instance = new SQLiteHelper();
        return instance;
    }


    private String findDatabase()
    {
        final String st1 = System.getenv("EXTERNAL_STORAGE");
        final String st2 = System.getenv("SECONDARY_STORAGE");
        final String st3 = System.getenv("EMULATED_STORAGE_TARGET");
        String fileDir = null;
        for (String s : new String[]{st1, st2, st3}){
        if (fileDir == null && s != null)
            fileDir = s;
        }
        if (fileDir == null)
            return "";
        File dirs = new File(fileDir);
        if(!dirs.exists()) return "";
        String [] d = dirs.list();
        boolean found = false;
        for (String s : d)
        {
            if(s.equals("ebook_app"))
            {
                found = true;
                break;
            }
        }
        if (found)
            return dirs.getAbsolutePath() + "/ebook_app";
        return "";
    }

    public List<Book> getBooks(){
        ArrayList<Book> list = new ArrayList<>();
        String query = "SELECT * FROM books";
        Cursor cursor = db.rawQuery(query, null);
        try {

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Book obj = new Book();
                    //only one column
                    obj.setId(cursor.getInt(0));
                    obj.setTitle(cursor.getString(1));
                    obj.setAuthor(cursor.getString(6));
                    obj.setCover(this.databaseLocation +"/"+cursor.getString(9)+"/cover.jpg");
                    obj.setLocation(this.databaseLocation +"/"+cursor.getString(9));
                    putLocation(obj);
                    list.add(obj);
                } while (cursor.moveToNext());
            }

        } finally {
            try { cursor.close(); } catch (Exception ignore) {}
        }
        return list;
    }

    private void putLocation(Book obj) {
        String query = "SELECT * FROM data WHERE book =" + obj.getId();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        obj.setLocation(obj.getLocation()+"/"+cursor.getString(4)+"."+cursor.getString(2).toLowerCase());
        cursor.close();
    }

}
