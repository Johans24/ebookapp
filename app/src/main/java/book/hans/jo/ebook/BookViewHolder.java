package book.hans.jo.ebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by mota on 31/3/2018.
 */

public class BookViewHolder extends BaseViewHolder<Book> implements View.OnClickListener {
    public BookViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind() {
        itemView.setOnClickListener(this);
        TextView title = this.itemView.findViewById(R.id.title);
        title.setText(item.getTitle());
        TextView author = this.itemView.findViewById(R.id.author);
        author.setText(item.getAuthor());
        loadImage();
    }

    private void loadImage() {
        Log.i("BOOK","B:"+item);
        File imgFile = new File(item.getCover());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = itemView.findViewById(R.id.image);
            myImage.setImageBitmap(myBitmap);
        }
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(itemView.getContext(),BookActivity.class);
        i.putExtra("title",item.getTitle());
        i.putExtra("author",item.getAuthor());
        i.putExtra("cover",item.getCover());
        i.putExtra("location",item.getLocation());
        itemView.getContext().startActivity(i);
    }
}
