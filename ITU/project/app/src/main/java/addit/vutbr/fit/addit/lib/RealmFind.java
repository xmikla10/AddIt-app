package addit.vutbr.fit.addit.lib;

/**
 * Created by david on 4.11.16.
 */

import java.util.Hashtable;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObjectSchema;


/**
 * @see http://stackoverflow.com/questions/38109905/proper-way-to-get-realm-object-by-its-primary-key-in-android-java
 * class that helps get primary key for element so that you can manipulate with it more easily
 */
public final class RealmFind {
    // shared cache for primary keys
    private static Hashtable<Class<? extends RealmModel>, String> primaryKeyMap = new Hashtable<>();

    private static String getPrimaryKeyName(Realm realm, Class<? extends RealmModel> clazz) {
        String primaryKey = primaryKeyMap.get(clazz);
        if (primaryKey != null)
            return primaryKey;
        RealmObjectSchema schema = realm.getSchema().get(clazz.getSimpleName());
        if (!schema.hasPrimaryKey())
            return null;
        primaryKey = schema.getPrimaryKey();
        primaryKeyMap.put(clazz, primaryKey);
        return primaryKey;
    }

    private static <E extends RealmModel, TKey> E findByKey(Realm realm, Class<E> clazz, TKey key) {
        String primaryKey = getPrimaryKeyName(realm, clazz);
        if (primaryKey == null)
            return null;
        if (key instanceof String)
            return realm.where(clazz).equalTo(primaryKey, (String) key).findFirst();
        else
            return realm.where(clazz).equalTo(primaryKey, (Long) key).findFirst();
    }

    public static <E extends RealmModel> E byKey(Realm realm, Class<E> clazz, String key) {
        return findByKey(realm, clazz, key);
    }

    public static <E extends RealmModel> E byKey(Realm realm, Class<E> clazz, Long key) {
        return findByKey(realm, clazz, key);
    }

    public static <E extends RealmModel> E getTaskWithKey(Realm realm, Class<E> clazz, int key) {
        return realm.where(clazz).equalTo("primaryKey", key).findFirst();
    }

    public static <E extends RealmModel> long nextPrimaryKey(Realm realm, Class<E> clazz) {
        Number biggestKey = (realm.where(clazz).max("primaryKey"));
        return (biggestKey != null ? biggestKey.longValue() : -1) + 1;
    }
}
