package com.idea.group.neverhaveiever.Services;

import java.util.List;

import io.realm.RealmObject;

public interface IPersistenceService {

    <T extends RealmObject> T MakeObjectManaged(T item);
    <T extends RealmObject> List<T> GetUnmanagedObjects(Class<T> type);
    <T extends RealmObject> T GetUnmanagedObject(Class<T> type);
    <T extends RealmObject> void UpdateOrInsertItem(List<T> item);
    <T extends RealmObject> void UpdateOrInsertItem(T item);
    <T extends RealmObject> T GetItem(Class<T> type);
    <T extends RealmObject> T GetItemForKeyValuePair(Class<T> type, String key, String value);
    <T extends RealmObject> List<T> GetItems(Class<T> type);
    <T extends RealmObject> void SaveItem(T item);
    <T extends RealmObject> void SaveItems(List<T> item);
    <T extends RealmObject> void Update(DatabaseUpdate update);
    <T extends RealmObject> void Remove(Class<T> item);
    <T extends RealmObject> void RemoveItem(T item);
    <T extends RealmObject> void DropDatabase();

}

