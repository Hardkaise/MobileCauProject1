package eu.epitech.clement.cauproject1.view.homepage;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.epitech.clement.cauproject1.R;
import eu.epitech.clement.cauproject1.tools.ImageTools;
import eu.epitech.clement.cauproject1.tools.StepFragment;

import static eu.epitech.clement.cauproject1.view.homepage.InformationFragment.INFORMATION_CAT;
import static eu.epitech.clement.cauproject1.view.homepage.InformationFragment.INFORMATION_FRANCE;
import static eu.epitech.clement.cauproject1.view.homepage.InformationFragment.INFORMATION_GUTEMBERG;
import static eu.epitech.clement.cauproject1.view.homepage.InformationFragment.INFORMATION_LOREM_IPSUM;
import static eu.epitech.clement.cauproject1.view.homepage.InformationFragment.INFORMATION_NAPOLEON;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends StepFragment {
    @BindView(R.id.description_title) TextView title;
    @BindView(R.id.description_text) TextView text;
    @BindView(R.id.description_img) ImageView img;

    private int typeDisplayed;

    public DescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_description, container, false);
        ButterKnife.bind(this, root);
        displayFragment();
        return root;
    }

    private void displayFragment() {
        switch (typeDisplayed) {
            case INFORMATION_NAPOLEON:
                title.setText(R.string.napoleon);
                text.setText(R.string.napoleon_description);
                ImageTools.setImageRounded(img, ((BitmapDrawable) getResources().getDrawable(R.drawable.napoleon)).getBitmap());
                break;
            case INFORMATION_FRANCE:
                title.setText(R.string.france);
                text.setText(R.string.france_description);
                ImageTools.setImageRounded(img, ((BitmapDrawable) getResources().getDrawable(R.drawable.flag_france)).getBitmap());
                break;
            case INFORMATION_LOREM_IPSUM:
                title.setText(R.string.Lorem_Ipsum);
                text.setText(R.string.lorem_ipsum_description);
                ImageTools.setImageRounded(img, ((BitmapDrawable) getResources().getDrawable(R.drawable.lorem_ipsum)).getBitmap());
                break;
            case INFORMATION_CAT:
                title.setText(R.string.cat);
                text.setText(R.string.cat_description);
                ImageTools.setImageRounded(img, ((BitmapDrawable) getResources().getDrawable(R.drawable.cat)).getBitmap());
                break;
            case INFORMATION_GUTEMBERG:
                title.setText(R.string.gutemberg);
                text.setText(R.string.gutemberg_description);
                ImageTools.setImageRounded(img, ((BitmapDrawable) getResources().getDrawable(R.drawable.gutenberg)).getBitmap());
                break;
            default:
                System.exit(-1);
                break;
        }
    }

    public void setTypeDisplayed(int typeDisplayed) {
        this.typeDisplayed = typeDisplayed;
    }

    @Override
    public boolean onBackPressed() {
        stepDone();
        return super.onBackPressed();
    }
}
