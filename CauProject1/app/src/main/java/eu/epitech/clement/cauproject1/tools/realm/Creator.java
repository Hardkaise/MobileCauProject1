package eu.epitech.clement.cauproject1.tools.realm;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public abstract class Creator<T extends RealmObject> {

    protected static Gson gson;

    protected Gson getGson() {
        if (gson == null) gson = new Gson();
        return gson;
    }

    // Create the object based on the element given in parameter.
    // This element is given by getJsonElementInTree
    // By  default, it creates the new object with gson parser
    public T createFromJson(JsonElement el) {
        return getGson().fromJson(el, getObjClass());
    }

    // Get JsonElement in response tree
    // Useful in case service returns {data:{...}}
    public JsonElement getJsonElementInTree(JsonElement root) {
        if (getJsonPathForElement() == null)
            return root.getAsJsonObject();
        String[] split = getJsonPathForElement().split("\\.");
        JsonObject obj = null;
        for (String s :
                split) {
            if (obj == null) {
                if (root.isJsonNull() || !root.getAsJsonObject().has(s) || root.getAsJsonObject().get(s).isJsonNull())
                    return null;
                obj = root.getAsJsonObject().getAsJsonObject(s);
            } else {
                if (!obj.has(s) || obj.get(s).isJsonNull()) return null;
                obj = obj.getAsJsonObject(s);
            }
        }
        return obj;
    }

    public JsonArray getJsonArrayInTree(JsonElement root) {
        if (getJsonPathForArray() == null)
            return root != null ? (root.isJsonNull() ? null : root.getAsJsonArray()) : null;
        String[] split = getJsonPathForArray().split("\\.");
        JsonObject obj = null;
        int i = 0;
        int size = split.length;
        for (String s : split) {
            if (obj == null) {
                if (root.isJsonNull() || !root.getAsJsonObject().has(s) || root.getAsJsonObject().get(s).isJsonNull())
                    return null;
                obj = root.getAsJsonObject().getAsJsonObject(s);
            } else if (i < size - 1) {
                if (!obj.has(s) || obj.get(s).isJsonNull()) return null;
                obj = obj.getAsJsonObject(s);
            } else {
                if (!obj.has(s) || obj.get(s).isJsonNull()) return null;
                return obj.getAsJsonArray(s);
            }
            i++;
        }
        // Will never happen
        return null;
    }

    // Called during transaction
    public T executeRealmTransaction(Realm realm, T instance) {
        if (instance == null)
            return null;
        if (getPrimaryKeyName() == null || getSaveableAttributes() == null)
            return (T) realm.copyToRealmOrUpdate((RealmObject) instance);
        else {
            T obj = realm.where(getObjClass()).equalTo(getPrimaryKeyName(), getPrimaryKeyValue(instance)).findFirst();
            if (obj == null) return (T) realm.copyToRealmOrUpdate((RealmObject) instance);
            else {
                T local = realm.copyFromRealm(obj);
                instance = (T) realm.copyToRealmOrUpdate((RealmObject) instance);
                save(instance, local, getSaveableAttributes());
                beforeSaveInRealm(local, instance);
                return instance;
            }
        }
    }

    public void beforeSaveInRealm(T local, T realmInstance) {

    }

    public String getJsonPathForElement() {
        return null;
    }

    public String getJsonPathForArray() {
        return null;
    }

    public abstract Class<T> getObjClass();

    /*
    Override getPrimaryKeyName(), getPrimaryKeyValue() and getSaveableAttributes()
    to use auto save feature in executeRealmTransaction()
     */
    public String getPrimaryKeyName() {
        return null;
    }

    public String getPrimaryKeyValue(T object) {
        return null;
    }

    public String[] getSaveableAttributes() {
        return null;
    }

    // Methods to save vars not in service

    public final void save(T obj, T local, String[] list) {
        for (String s : list) _saveAttr(obj, local, s);
    }

    private void _saveAttr(T target, T local, String attr) {
        Object value = getValue(local, attr);
        setValue(target, attr, value);
    }

    private void setValue(T target, String attr, Object value) {
        Class clazz = target.getClass();
        if (clazz.getName().endsWith("Proxy")) clazz = clazz.getSuperclass();
        if (!(value instanceof RealmList)) {
            Method setter = getSetterMethod(clazz, attr, value);
            if (setter == null) return;
            try {
                setter.invoke(target, value);
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        } else {
            // Save realm list
            RealmList istList = (RealmList) getValue(target, attr);
            if (istList != null) {
                RealmList locList = (RealmList) value;
                Set tmpSet = new HashSet();
                tmpSet.addAll(locList);
                tmpSet.addAll(istList);
                istList.clear();
                istList.addAll(tmpSet);
            }
        }
    }

    private Object getValue(T target, String attr) {
        Class clazz = target.getClass();
        if (clazz.getName().endsWith("Proxy")) clazz = clazz.getSuperclass();
        Method getter = null;
        try {
            getter = clazz.getDeclaredMethod("get" + attr.substring(0, 1).toUpperCase() + attr.substring(1));
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        }
        if (getter == null) return null;
        try {
            return getter.invoke(target);
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    private Method getSetterMethod(Class clazz, String attr, Object value) {
        if (value == null) return null;
        Class valueClass = value.getClass();
        if (value instanceof Boolean) valueClass = boolean.class;
        if (value instanceof Integer) valueClass = int.class;
        if (value instanceof Double) valueClass = double.class;
        if (value instanceof Float) valueClass = float.class;
        if (value instanceof Long) valueClass = long.class;
        Method setter = null;
        try {
            setter = clazz.getDeclaredMethod("set" + attr.substring(0, 1).toUpperCase() + attr.substring(1), valueClass);
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        }
        return setter;
    }

    public static <U extends RealmObject> CreatorBuilder<U> Builder(Class<U> clazz) {
        return new CreatorBuilder<>(clazz);
    }

    public static final class CreatorBuilder<T extends RealmObject> {
        Class<T> claz;
        String elmPath;
        String arrPath;

        public CreatorBuilder(Class<T> clazz) {
            this.claz = clazz;
        }

        public CreatorBuilder<T> elementPath(String path) {
            this.elmPath = path;
            return this;
        }

        public CreatorBuilder<T> arrayPath(String path) {
            this.arrPath = path;
            return this;
        }

        public Creator<T> build() {
            return new Creator<T>() {
                @Override
                public Class<T> getObjClass() {
                    return claz;
                }

                @Override
                public String getJsonPathForElement() {
                    return elmPath;
                }

                @Override
                public String getJsonPathForArray() {
                    return arrPath;
                }
            };
        }
    }
}