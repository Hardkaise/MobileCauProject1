package eu.epitech.clement.cauproject1.view.memo;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.epitech.clement.cauproject1.R;
import eu.epitech.clement.cauproject1.base.MyostoMyApplication;
import eu.epitech.clement.cauproject1.model.MemoRealm;
import eu.epitech.clement.cauproject1.view.homepage.HomepageActivity;
import io.realm.Realm;

public class MemoActivity extends AppCompatActivity {
    @BindView(R.id.memo_text) EditText text;
    @BindView(R.id.memo_date) TextView date;
    @BindView(R.id.memo_priority) CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.memo_confirm)
    public void onConfirmClick() {
        if (text.getText().toString().contentEquals(""))
            displayDialog(getString(R.string.error), getString(R.string.error_content_memo));
        else if (date.getText().toString().contentEquals(getString(R.string.na)))
            displayDialog(getString(R.string.error), getString(R.string.error_date));
        else {
            MyostoMyApplication.getDefaultRealm().beginTransaction();
            MemoRealm memoRealm = new MemoRealm();
            Number number = MyostoMyApplication.getDefaultRealm().where(MemoRealm.class).max("id");
            if (number == null)
                memoRealm.setId(1);
            else
                memoRealm.setId(number.intValue() + 1);
            memoRealm.setDate(date.getText().toString());
            memoRealm.setTitle(text.getText().toString());
            memoRealm.setPriority(checkBox.isChecked());
            MyostoMyApplication.getDefaultRealm().copyToRealmOrUpdate(memoRealm);
            MyostoMyApplication.getDefaultRealm().commitTransaction();
            finish();
            startActivity(new Intent(this, HomepageActivity.class));
        }
    }

    @OnClick(R.id.memo_date_button)
    public void onDateClick() {
        DatePickerDialog dialog = new DatePickerDialog(MemoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date.setText(String.format("%04d/%02d/%02d", i, i1, i2));
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void displayDialog(String title, String text) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MemoActivity.this)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        alert.create().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomepageActivity.class));
        finish();
    }
}
