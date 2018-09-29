package eu.epitech.clement.cauproject1.view.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.epitech.clement.cauproject1.R;
import eu.epitech.clement.cauproject1.adapter.DrawerExpandableListAdapter;
import eu.epitech.clement.cauproject1.tools.Alarm;
import eu.epitech.clement.cauproject1.tools.StepFragment;
import eu.epitech.clement.cauproject1.tools.Storage;
import eu.epitech.clement.cauproject1.view.alarm.AlarmPickerActivity;
import eu.epitech.clement.cauproject1.view.memo.MemoActivity;

import static eu.epitech.clement.cauproject1.view.homepage.CalendarFragment.INFORMATION_ALARM;
import static eu.epitech.clement.cauproject1.view.homepage.HomeFragment.INFORMATION_CALENDAR;
import static eu.epitech.clement.cauproject1.view.homepage.HomeFragment.INFORMATION_INFORMATION;
import static eu.epitech.clement.cauproject1.view.homepage.HomeFragment.INFORMATION_LIST;
import static eu.epitech.clement.cauproject1.view.homepage.InformationFragment.INFORMATION_CAT;
import static eu.epitech.clement.cauproject1.view.homepage.InformationFragment.INFORMATION_FRANCE;
import static eu.epitech.clement.cauproject1.view.homepage.InformationFragment.INFORMATION_GUTEMBERG;
import static eu.epitech.clement.cauproject1.view.homepage.InformationFragment.INFORMATION_LOREM_IPSUM;
import static eu.epitech.clement.cauproject1.view.homepage.InformationFragment.INFORMATION_NAPOLEON;

public class HomepageActivity extends AppCompatActivity {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_INFORMATION = 1;
    private static final int FRAGMENT_DESCRIPTION = 2;
    private static final int FRAGMENT_CALENDAR = 3;
    private static final int FRAGMENT_MEMOLIST = 4;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.homepage_expandable_list) ExpandableListView listView;
    @BindView(R.id.toolbar_title) TextView toolbarTitle;
    @BindView(R.id.nav_header_switch) Switch alarm;

    private int fragment;
    private List<String> listDataHeader;
    private HashMap<String, String[]> listDataChild;
    private ActionBarDrawerToggle toggle;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ButterKnife.bind(this);
        prepareListData();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        listView.setAdapter(new DrawerExpandableListAdapter(this, listDataHeader, listDataChild));
        startHomeFragment();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Storage.Session.with(getBaseContext()).getStateAlarm())
            alarm.setChecked(true);
    }

    @OnClick(R.id.nav_header_switch)
    public void onAlarmSwitchClick() {
        Alarm alarmed = new Alarm(getBaseContext());
        if (alarm.isChecked()) {
            if (Storage.Session.with(getBaseContext()).getAlarm() != -1) {
                alarmed.setAlarm(Storage.Session.with(getBaseContext()).getAlarm());
                Storage.Session.with(getBaseContext()).setStateAlarm(true);
            } else alarm.setChecked(false);
        }
        else {
            alarmed.cancelAlarm();
            Storage.Session.with(getBaseContext()).setStateAlarm(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragment == FRAGMENT_HOME)
                super.onBackPressed();
            else ((StepFragment)getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount())).onBackPressed();
        }
    }

    private void startHomeFragment() {
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbarTitle.setTextColor(getResources().getColor(R.color.black));
        fragment = FRAGMENT_HOME;
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setStepListener(new StepFragment.StepListener() {
            @Override
            public void onNextStep() {
            }

            @Override
            public void onMessage(Object message) {
                super.onMessage(message);
                switch ((int)message) {
                    case INFORMATION_INFORMATION:
                        startInformationFragment();
                        break;
                    case INFORMATION_CALENDAR:
                        startCalendarFragment();
                        break;
                    case INFORMATION_LIST:
                        startMemoListFragment();
                        break;
                    default:
                        System.exit(-1);
                        break;
                }
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.homepage_container, homeFragment)
                .commit();
    }

    private void startMemoListFragment() {
        fragment = FRAGMENT_MEMOLIST;
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.purpleColor));
        toolbarTitle.setTextColor(getResources().getColor(R.color.white));
        MemoListFragment fg = new MemoListFragment();
        fg.setStepListener(new StepFragment.StepListener() {
            @Override
            public void onNextStep() {
                startHomeFragment();
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homepage_container, fg)
                .commit();
    }

    private void startInformationFragment() {
        menuItem.setVisible(false);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.purpleColor));
        toolbarTitle.setTextColor(getResources().getColor(R.color.white));
        fragment = FRAGMENT_INFORMATION;
        final InformationFragment informationFragment = new InformationFragment();
        informationFragment.setStepListener(new StepFragment.StepListener() {
            @Override
            public void onNextStep() {
                startHomeFragment();
            }

            @Override
            public void onMessage(Object message) {
                super.onMessage(message);
                switch ((int)message) {
                    case INFORMATION_NAPOLEON:
                        startDescriptionFragment(INFORMATION_NAPOLEON);
                        break;
                    case INFORMATION_FRANCE:
                        startDescriptionFragment(INFORMATION_FRANCE);
                        break;
                    case INFORMATION_LOREM_IPSUM:
                        startDescriptionFragment(INFORMATION_LOREM_IPSUM);
                        break;
                    case INFORMATION_CAT:
                        startDescriptionFragment(INFORMATION_CAT);
                        break;
                    case INFORMATION_GUTEMBERG:
                        startDescriptionFragment(INFORMATION_GUTEMBERG);
                        break;
                    default:
                        System.exit(-1);
                        break;
                }
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homepage_container, informationFragment)
                .commit();
    }

    private void startDescriptionFragment(int typeDisplayed) {
        menuItem.setVisible(true);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.purpleColor));
        toolbarTitle.setTextColor(getResources().getColor(R.color.white));
        fragment = FRAGMENT_DESCRIPTION;
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        descriptionFragment.setTypeDisplayed(typeDisplayed);
        descriptionFragment.setStepListener(new StepFragment.StepListener() {
            @Override
            public void onNextStep() {
                startInformationFragment();
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homepage_container, descriptionFragment)
                .commit();
    }

    private void startCalendarFragment() {
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.purpleColor));
        toolbarTitle.setTextColor(getResources().getColor(R.color.white));
        fragment = FRAGMENT_CALENDAR;
        CalendarFragment fg = new CalendarFragment();
        fg.setStepListener(new StepFragment.StepListener() {
            @Override
            public void onNextStep() {
                startHomeFragment();
            }

            @Override
            public void onMessage(Object message) {
                super.onMessage(message);
                if ((int)message == INFORMATION_ALARM)
                   startActivity(new Intent(getBaseContext(), AlarmPickerActivity.class));
                else
                    startActivity(new Intent(getBaseContext(), MemoActivity.class));
                finish();
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homepage_container,fg)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.homepage_menu, menu);
        menuItem = menu.findItem(R.id.action_back);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_back) {
            ((StepFragment)getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount())).onBackPressed();
        }
        return true;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataHeader.add(getString(R.string.Understand));
        listDataHeader.add(getString(R.string.Care));
        listDataHeader.add(getString(R.string.Meal));
        listDataHeader.add(getString(R.string.Life));
        listDataHeader.add(getString(R.string.Calendar));
        listDataHeader.add(getString(R.string.Information));
        listDataChild.put(listDataHeader.get(0), new String[] { getString(R.string.understand, 1),
                getString(R.string.understand, 2),
                getString(R.string.understand, 3),
                getString(R.string.understand, 4),
                getString(R.string.understand, 5),
                getString(R.string.understand, 6) });
        listDataChild.put(listDataHeader.get(1), new String[] { getString(R.string.care, 1),
                getString(R.string.care, 2),
                getString(R.string.care, 3),
                getString(R.string.care, 4),
                getString(R.string.care, 5),
                getString(R.string.care, 6)});
        listDataChild.put(listDataHeader.get(2), new String[] { getString(R.string.meal, 1),
                getString(R.string.meal, 2),
                getString(R.string.meal, 3),
                getString(R.string.meal, 4),
                getString(R.string.meal, 5),
                getString(R.string.meal, 6)});
        listDataChild.put(listDataHeader.get(3), new String[] { getString(R.string.life, 1),
                getString(R.string.life, 2),
                getString(R.string.life, 3),
                getString(R.string.life, 4),
                getString(R.string.life, 5),
                getString(R.string.life, 6)});
        listDataChild.put(listDataHeader.get(4), new String[] { getString(R.string.calendar, 1),
                getString(R.string.calendar, 2),
                getString(R.string.calendar, 3),
                getString(R.string.calendar, 4),
                getString(R.string.calendar, 5),
                getString(R.string.calendar, 6)});
        listDataChild.put(listDataHeader.get(5), new String[] { getString(R.string.information, 1),
                getString(R.string.information, 2),
                getString(R.string.information, 3),
                getString(R.string.information, 4),
                getString(R.string.information, 5),
                getString(R.string.information, 6)});
    }


}
