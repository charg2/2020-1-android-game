package kr.ac.kpu.game.charg2dang.imageswitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private int pageNumber;
    private static int[] IMAGE_RES_IDS =
            {
                    R.mipmap.mm0,
                    R.mipmap.mm1,
                    R.mipmap.mm2,
                    R.mipmap.mm3,
                    R.mipmap.mm4,
                    R.mipmap.mm5,
                    R.mipmap.mm6,
            };
    private ImageView mainImageView;
    private TextView headerTextView;
    private String headerFormatString;
    private ImageButton prevButton;
    private ImageButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainImageView = findViewById(R.id.mainImageView);
        headerTextView = findViewById(R.id.headerTextView);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);

        Resources res = getResources();
        headerFormatString = res.getString(R.string.header_title_fmt);
        pageNumber = 1;
        showPage();
    }

    public void onBtnPrev(View view)
    {
        pageNumber--;
        showPage();
    }

    public void onBtnNext(View view)
    {
        pageNumber++;
        showPage();
    }

    private void showPage()
    {
        prevButton.setEnabled(pageNumber > 1);
        nextButton.setEnabled(pageNumber < IMAGE_RES_IDS.length);

        int resId = IMAGE_RES_IDS[pageNumber - 1];
        mainImageView.setImageResource(resId);
        String text = String.format(headerFormatString, pageNumber, IMAGE_RES_IDS.length);
        headerTextView.setText(text);
    }


}
