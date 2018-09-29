package eu.epitech.clement.cauproject1.view.homepage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.epitech.clement.cauproject1.R;
import eu.epitech.clement.cauproject1.adapter.MemoListAdapter;
import eu.epitech.clement.cauproject1.base.MyostoMyApplication;
import eu.epitech.clement.cauproject1.model.MemoRealm;
import eu.epitech.clement.cauproject1.tools.StepFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoListFragment extends StepFragment {
    @BindView(R.id.memo_list_view) RecyclerView memoList;
    @BindView(R.id.memo_list_no_memo) TextView noMemo;

    public MemoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_memo_list, container, false);
        ButterKnife.bind(this, root);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        memoList.setLayoutManager(llm);
        List<MemoRealm> memoRealmsPriority = MyostoMyApplication.getPriorityMemos();
        List<MemoRealm> memoRealmsNoPriority = MyostoMyApplication.getNoPriorityMemos();
        List<MemoRealm> memoRealms = new ArrayList<>();
        if (memoRealmsPriority != null)
            memoRealms.addAll(memoRealmsPriority);
        if (memoRealmsNoPriority != null)
            memoRealms.addAll(memoRealmsNoPriority);
        memoList.setAdapter(new MemoListAdapter(memoRealms));
        if (memoRealms.isEmpty())
            noMemo.setVisibility(View.VISIBLE);
        return root;
    }

    @Override
    public boolean onBackPressed() {
        stepDone();
        return super.onBackPressed();
    }
}
