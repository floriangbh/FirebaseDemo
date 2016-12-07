package firebase_demo.fg.com.android_firebase_demo;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

/**
 * Created by floriangabach on 07/12/2016.
 */

public class AddButtonListener implements View.OnClickListener {
    Context context;
    EditText inputTxt;

    public AddButtonListener(Context ctx, EditText inputText) {
        this.context = ctx;
        this.inputTxt = inputText;
    }

    @Override public void onClick(View v) {
        // TODO
    }
}