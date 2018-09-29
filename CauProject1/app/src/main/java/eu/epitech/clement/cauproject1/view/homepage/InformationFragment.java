package eu.epitech.clement.cauproject1.view.homepage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.epitech.clement.cauproject1.R;
import eu.epitech.clement.cauproject1.tools.StepFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends StepFragment {
    public static final int INFORMATION_NAPOLEON = 1;
    public static final int INFORMATION_FRANCE = 2;
    public static final int INFORMATION_LOREM_IPSUM = 3;
    public static final int INFORMATION_CAT = 4;
    public static final int INFORMATION_GUTEMBERG = 5;

    public InformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick({R.id.information_napoleon, R.id.information_france, R.id.information_lorem_ipsum, R.id.information_cat, R.id.information_gutemberg})
    public void onClickNapoleon(View view) {
        switch (view.getId()) {
            case R.id.information_napoleon:
                sendMessageToActivity(INFORMATION_NAPOLEON);
                break;
            case R.id.information_france:
                sendMessageToActivity(INFORMATION_FRANCE);
                break;
            case R.id.information_lorem_ipsum:
                sendMessageToActivity(INFORMATION_LOREM_IPSUM);
                break;
            case R.id.information_cat:
                sendMessageToActivity(INFORMATION_CAT);
                break;
            case R.id.information_gutemberg:
                sendMessageToActivity(INFORMATION_GUTEMBERG);
                break;
            default:
                System.exit(-1);
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        stepDone();
        return super.onBackPressed();
    }
}
