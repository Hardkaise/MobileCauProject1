package eu.epitech.clement.cauproject1.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.epitech.clement.cauproject1.R;
import eu.epitech.clement.cauproject1.base.MyostoMyApplication;
import eu.epitech.clement.cauproject1.model.MemoRealm;

public class MemoListAdapter extends RecyclerView.Adapter<MemoListAdapter.MemoHolder> {
    private List<MemoRealm> memoRealms;

    public MemoListAdapter(List<MemoRealm> memoRealms) {
        this.memoRealms = memoRealms;
    }

    @NonNull
    @Override
    public MemoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.memo_list_item, viewGroup, false);
        return new MemoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoHolder memoHolder, int i) {
        memoHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return memoRealms.size();
    }

    public class MemoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.memo_item_priority) ImageView priority;
        @BindView(R.id.memo_item_date) TextView date;
        @BindView(R.id.memo_item_text) TextView title;
        private int pos ;

        public MemoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int pos) {
            if (memoRealms.get(pos).isPriority())
                priority.setImageDrawable(itemView.getResources().getDrawable(R.drawable.alarm));
            else priority.setImageDrawable(itemView.getResources().getDrawable(R.drawable.noalarm));
            date.setText(memoRealms.get(pos).getDate());
            title.setText(memoRealms.get(pos).getTitle());
            this.pos = pos;
        }

        @OnClick(R.id.memo_item_root)
        public void onClickRoot() {
            Log.e("pass here", "otooaotap");
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext())
                    .setTitle(R.string.are_you_sure)
                    .setMessage(memoRealms.get(pos).getTitle())
                    .setPositiveButton(itemView.getContext().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                          MyostoMyApplication.deleteMemo(memoRealms.get(pos).getId());
                            memoRealms.remove(pos);
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(itemView.getContext().getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            builder.create().show();
        }
    }
}
