package com.app.easy_photo_to_link.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class that helps to work Data Base
 *
 */

public class DBWork {
    private SQLiteDatabase database;
    private DBHelperSharePhoto dbHelper;
    private Cursor data;

    public DBWork(Context appContext) {

        this.dbHelper = new DBHelperSharePhoto(appContext);
        this.database = this.dbHelper.getWritableDatabase();
    }

    public Cursor getCursorHistory() {
        data = database.query(DBHelperSharePhoto.TBL_NAME_HISTORY,
                new String[]{DBHelperSharePhoto.CM_DATE_TIME,
                        DBHelperSharePhoto.CM_LINK,
                        DBHelperSharePhoto.CM_THUMP_URL,
                        DBHelperSharePhoto._ID}, null, null, null, null, DBHelperSharePhoto._ID + " DESC");
        return data;
    }
    /*
     *Function writes Image Data(DateTime, ImageUrl, ThumbUrl) to History Table
     *
     * @param imgUrl link of the image
     * @param thumbUrl link of the thumb image
     */
    public void writePhotoDataToDB(String imgUrl, String thumbUrl) {

        ContentValues contentValues = new ContentValues();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        contentValues.put(DBHelperSharePhoto.CM_DATE_TIME, dateFormat.format(date));
        contentValues.put(DBHelperSharePhoto.CM_LINK, imgUrl);
        contentValues.put(DBHelperSharePhoto.CM_THUMP_URL, thumbUrl);

        database.insert(DBHelperSharePhoto.TBL_NAME_HISTORY, null, contentValues);

    }

    /*
     * Deletes Photo History Item in DB by link
     *
     * @param imgUrl link of the image
     */
    public boolean deletePhotoHistoryItemByLink(String imgUrl) {
        int rowId = database.delete(DBHelperSharePhoto.TBL_NAME_HISTORY, "LINK = ?", new String[]{imgUrl});
        if (rowId > 0) {
            return true;
        } else {
            return false;
        }

    }

    /*
    * Deletes all data from History table
    *
    */
    public void deletePhotoHistory() {
        database.delete(DBHelperSharePhoto.TBL_NAME_HISTORY, null, null);

    }

    /*
     * Checks History data in table and return false if is no data or true if there is data
     *
     */
    public boolean isHasHistory() {

        Cursor cursorBool = database.query(true, DBHelperSharePhoto.TBL_NAME_HISTORY,
                new String[]{DBHelperSharePhoto._ID}, null, null, null, null, null, null);
        int count = cursorBool.getCount();
        cursorBool.close();
        if (count == 0) {
            return false;
        }
        return true;
    }
    /*
     * Closes DBHelper and Cursor
     *
     */
    public void closeAllConnections() {
        if (dbHelper != null) {
            dbHelper.close();
        }
        if (data != null) {
            data.close();
        }
    }
}
