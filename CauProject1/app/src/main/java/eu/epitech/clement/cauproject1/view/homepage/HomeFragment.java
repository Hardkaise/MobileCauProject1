package eu.epitech.clement.cauproject1.view.homepage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.epitech.clement.cauproject1.R;
import eu.epitech.clement.cauproject1.tools.Alarm;
import eu.epitech.clement.cauproject1.tools.StepFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends StepFragment {
    public static final int INFORMATION_CALENDAR = 1;
    public static final int INFORMATION_INFORMATION= 2;
    public static final int INFORMATION_LIST = 3;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick({R.id.home_text_understand, R.id.home_understand})
    public void onUnderstandClick() {
        sendMessageToActivity(INFORMATION_INFORMATION);
    }

    @OnClick({R.id.home_calendar, R.id.home_text_calendar})
    public void onClickCalendar() {
        sendMessageToActivity(INFORMATION_CALENDAR);
    }

    @OnClick({R.id.home_information, R.id.home_text_information})
    public void onInformationClick() {
        sendMessageToActivity(INFORMATION_LIST);
    }

}
