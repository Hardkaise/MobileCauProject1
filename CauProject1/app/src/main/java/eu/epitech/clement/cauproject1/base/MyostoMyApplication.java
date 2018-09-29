package eu.epitech.clement.cauproject1.base;

import android.app.Application;
import android.content.Context;

import java.util.List;

import eu.epitech.clement.cauproject1.model.MemoRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class MyostoMyApplication extends Application {
    private static Realm realm;
    private static RealmConfiguration realmConfig;
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        realmConfig = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        getDefaultRealm();
        appContext = getBaseContext();
    }

    public static Realm getDefaultRealm() {
        if (realm == null)
            realm = getRealmInstance();
        return realm;
    }

    private static Realm getRealmInstance() {
        return Realm.getInstance(realmConfig);
    }

    public static List<MemoRealm> getPriorityMemos() {
        List<MemoRealm> memoRealms = null;
        if (realm != null) {
            RealmResults<MemoRealm> realmResults = realm.where(MemoRealm.class).equalTo("priority", true).findAll().sort("title", Sort.ASCENDING);
            memoRealms = realm.copyFromRealm(realmResults);
        }
        return memoRealms;
    }

    public static List<MemoRealm> getNoPriorityMemos() {
        List<MemoRealm> memoRealms = null;
        if (realm != null) {
            RealmResults<MemoRealm> realmResults = realm.where(MemoRealm.class).equalTo("priority", false).findAll().sort("title", Sort.ASCENDING);
            memoRealms = realm.copyFromRealm(realmResults);
        }
        return memoRealms;
    }

    public static void deleteMemo(final int id) {
        if (realm != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<MemoRealm> realms = realm.where(MemoRealm.class).equalTo("id", id).findAll();
                    realms.deleteAllFromRealm();
                }
            });
        }
    }
}
