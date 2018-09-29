package eu.epitech.clement.cauproject1.model;

import com.google.gson.JsonElement;

import eu.epitech.clement.cauproject1.tools.realm.Creator;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MemoRealm extends RealmObject {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @PrimaryKey
    private int id;
    private String title;
    private String date;
    private boolean priority;

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public static Creator<MemoRealm> getCreatorInstance() {
        return VisitorRealmCreator.Builder(MemoRealm.class)
                .build();
    }

    public static class VisitorRealmCreator extends Creator<MemoRealm> {

        @Override
        public MemoRealm createFromJson(JsonElement el) {
            return super.createFromJson(el);
        }
        @Override
        public Class<MemoRealm> getObjClass() {
            return MemoRealm.class;
        }
    }
}
