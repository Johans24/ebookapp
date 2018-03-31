package book.hans.jo.ebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

/**
 * Created by mota on 31/3/2018.
 */

public class BookAdapter extends BaseRecyclerAdapter<Book> {
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.book_item, null);
        return new BookViewHolder(view);
    }
}
