package eu.epitech.clement.cauproject1.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage {
    private Storage(){}
    public static class SharedPrefs {
        private SharedPrefs(){}
        public static SharedPreferences.Editor getEditor(Context ctx, String name) {
            SharedPreferences prefs = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            return prefs.edit();
        }

        public static SharedPreferences getReader(Context ctx, String name) {
            return ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
    }

    public abstract static class StorageInstance {
        protected SharedPreferences reader;
        protected SharedPreferences.Editor editor;

        public StorageInstance(Context ctx, String name) {
            reader = SharedPrefs.getReader(ctx, name);
            editor = SharedPrefs.getEditor(ctx, name);
        }
    }

    public static class Session extends StorageInstance {
        private static final String PREFNAME = "PrefSession";
        private static final String ALARM = "alarm";
        private static final String ALARM_STATE = "alarm_state";

        public Session(Context ctx) {
            super(ctx, PREFNAME);
        }

        public static Session with(Context ctx) {
            return new Session(ctx);
        }

        public void setAlarm(long time) {
            editor.putLong(ALARM, time).commit();
        }

        public long getAlarm() {
            return reader.getLong(ALARM, -1);
        }

        public void setStateAlarm(boolean stateAlarm) { editor.putBoolean(ALARM_STATE, stateAlarm).commit(); }

        public boolean getStateAlarm() { return reader.getBoolean(ALARM_STATE, false); }
    }
}
