package com.idea.group.neverhaveiever.Services;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmObjectSchema;
import io.realm.RealmQuery;

public class PersistenceService implements IPersistenceService {

    private Realm realm;

    public PersistenceService(Realm realm)
    {
        this.realm = realm;
    }

    public <T extends RealmObject> T MakeObjectManaged(T item)
    {
        return realm.copyToRealm(item);
    }

    public <T extends RealmObject> List<T> GetUnmanagedObjects(Class<T> type)
    {
        RealmQuery<T> query = realm.where(type);
        if(query.findAll() == null)
            return null;
        else
            return  realm.copyFromRealm(query.findAll());
    }
    public <T extends RealmObject> T GetUnmanagedObject(Class<T> type)
    {
        RealmQuery<T> query = realm.where(type);
        if(query.findFirst() == null)
            return null;
        else
            return realm.copyFromRealm(query.findFirst());
    }
    /**
     * Inserts or updates a list of unmanaged RealmObjects
     */
    public <T extends RealmObject> void UpdateOrInsertItem(List<T> item)
    {
        realm.beginTransaction();
        realm.insertOrUpdate(item);
        realm.commitTransaction();
    }

    public <T extends RealmObject> void UpdateOrInsertItem(T  item)
    {
        realm.beginTransaction();
        realm.insertOrUpdate(item);
        realm.commitTransaction();
    }

    public void Update(DatabaseUpdate update)
    {
        realm.beginTransaction();
        update.Update();
        realm.commitTransaction();
    }

    public <T extends RealmObject> T GetItem(Class<T> type)
    {
        RealmQuery<T> query = realm.where(type);
        return query.findFirst();
    }

    public <T extends RealmObject> T GetItemForKeyValuePair(Class<T> type, String key, String value)
    {
        RealmQuery<T> query = realm.where(type).equalTo(key, value);
        return query.findFirst();
    }

    public <T extends RealmObject> List<T> GetItems(Class<T> type)
    {
        RealmQuery<T> query = realm.where(type);
        return query.findAll();
    }
    /**
     * Updates an existing RealmObject that is identified by the same {@link io.realm.annotations.PrimaryKey} or creates
     * a new copy if no existing object could be found. This is a deep copy or update i.e., all referenced objects will be
     * either copied or updated.
     * */
    public <T extends RealmObject> void SaveItem(T item)
    {
        if(item == null)
        {
            return;
        }
        RealmObjectSchema schema = realm.getSchema().get(item.getClass().getSimpleName());
        if (schema != null && !schema.hasPrimaryKey())
        {
            realm.beginTransaction();
            realm.copyToRealm(item);
            realm.commitTransaction();
        }
        else
        {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(item);
            realm.commitTransaction();
        }

    }
    /**
     * Updates an existing RealmObject that is identified by the same {@link io.realm.annotations.PrimaryKey} or creates
     * a new copy if no existing object could be found. This is a deep copy or update i.e., all referenced objects will be
     * either copied or updated.
     * */
    public <T extends RealmObject> void SaveItems(List<T> items)
    {
        if(items != null && items.size() > 0)
        {
            RealmObjectSchema schema = realm.getSchema().get(items.get(0).getClass().getSimpleName());
            realm.beginTransaction();
            if (schema != null && !schema.hasPrimaryKey())
            {
                for (T item : items) {
                    realm.copyToRealmOrUpdate(item);
                }
            }
            else
            {
                for (T item : items) {
                    realm.copyToRealm(item);
                }
            }
            realm.commitTransaction();
        }

    }

    public <T extends RealmObject> void Remove(Class<T> item)
    {
        realm.beginTransaction();
        realm.delete(item);
        realm.commitTransaction();
    }

    public <T extends RealmObject> void RemoveItem(T item)
    {
        realm.beginTransaction();
        item.deleteFromRealm();
        realm.commitTransaction();
    }

    public <T extends RealmObject> void DropDatabase()
    {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

    }

}
