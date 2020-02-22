package edu.aku.hassannaqvi.uen_scans_sosas.content_provider

interface FamilyMemberInterface {
    companion object {
        @JvmStatic
        val COLUMN_ID: String
            get() = "_id"
        @JvmStatic
        val COLUMN_UID: String
            get() = "_uid"
        @JvmStatic
        val COLUMN_UUID: String
            get() = "_uuid"
        @JvmStatic
        val COLUMN_LUID: String
            get() = "_luid"
        @JvmStatic
        val COLUMN_AGE: String
            get() = "age"
        @JvmStatic
        val COLUMN_CLUSTERNO: String
            get() = "clusterno"
        @JvmStatic
        val COLUMN_HHNO: String
            get() = "hhno"
        @JvmStatic
        val COLUMN_RELATION_HH: String
            get() = "relHH"
        @JvmStatic
        val COLUMN_KISH_SELECTED: String
            get() = "kishSelected"
        @JvmStatic
        val COLUMN_NAME: String
            get() = "name"
        @JvmStatic
        val COLUMN_SERIAL_NO: String
            get() = "serial_no"
        @JvmStatic
        val COLUMN_MOTHER_NAME: String
            get() = "mother_name"
        @JvmStatic
        val COLUMN_MOTHER_SERIAL: String
            get() = "mother_serial"
        @JvmStatic
        val COLUMN_GENDER: String
            get() = "gender"
        @JvmStatic
        val COLUMN_MARITAL: String
            get() = "marital"
        @JvmStatic
        val COLUMN_SD: String
            get() = "sD"
        @JvmStatic
        val COLUMN_SYNCED: String
            get() = "synced"
        @JvmStatic
        val COLUMN_SYNCED_DATE: String
            get() = "synced_date"
    }
}