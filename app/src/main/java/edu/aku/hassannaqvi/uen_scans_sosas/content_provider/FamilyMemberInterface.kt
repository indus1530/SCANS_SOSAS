package edu.aku.hassannaqvi.uen_scans_sosas.content_provider

import android.database.Cursor

class FamilyMemberInterface {
    companion object {
        @JvmStatic
        val COLUMN_ID = "_id"
        @JvmStatic
        val COLUMN_UID = "_uid"
        @JvmStatic
        val COLUMN_UUID = "_uuid"
        @JvmStatic
        val COLUMN_LUID = "_luid"
        @JvmStatic
        val COLUMN_AGE = "age"
        @JvmStatic
        val COLUMN_CLUSTERNO = "clusterno"
        @JvmStatic
        val COLUMN_HHNO = "hhno"
        @JvmStatic
        val COLUMN_RELATION_HH = "relHH"
        @JvmStatic
        val COLUMN_KISH_SELECTED = "kishSelected"
        @JvmStatic
        val COLUMN_NAME = "name"
        @JvmStatic
        val COLUMN_SERIAL_NO = "serial_no"
        @JvmStatic
        val COLUMN_MOTHER_NAME = "mother_name"
        @JvmStatic
        val COLUMN_MOTHER_SERIAL = "mother_serial"
        @JvmStatic
        val COLUMN_GENDER = "gender"
        @JvmStatic
        val COLUMN_MARITAL = "marital"
        @JvmStatic
        val COLUMN_SD = "sD"

        @JvmStatic
        fun hydrate(cursor: Cursor): FamilyMemberContent? {
            return FamilyMemberContent(
                    cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_UID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_UUID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LUID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_KISH_SELECTED)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CLUSTERNO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_HHNO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_SERIAL_NO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_RELATION_HH)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_AGE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MOTHER_NAME))?.let { it } ?: "",
                    cursor.getString(cursor.getColumnIndex(COLUMN_MOTHER_SERIAL))?.let { it } ?: "",
                    cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MARITAL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_SD))

            )
        }
    }
}