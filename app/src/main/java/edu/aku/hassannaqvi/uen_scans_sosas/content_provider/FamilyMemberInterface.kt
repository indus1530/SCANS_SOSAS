package edu.aku.hassannaqvi.uen_scans_sosas.content_provider

interface FamilyMemberInterface {
    companion object {
        @JvmStatic
        val COLUMN_ID: String
            get() = "_id"
        val COLUMN_UID: String
            get() = "_uid"
        val COLUMN_UUID: String
            get() = "_uuid"
        val COLUMN_LUID: String
            get() = "_luid"
        val COLUMN_AGE: String
            get() = "age"
        val COLUMN_CLUSTERNO: String
            get() = "clusterno"
        val COLUMN_HHNO: String
            get() = "hhno"
        val COLUMN_RELATION_HH: String
            get() = "relHH"
        val COLUMN_KISH_SELECTED: String
            get() = "kishSelected"
        val COLUMN_NAME: String
            get() = "name"
        val COLUMN_SERIAL_NO: String
            get() = "serial_no"
        val COLUMN_MOTHER_NAME: String
            get() = "mother_name"
        val COLUMN_MOTHER_SERIAL: String
            get() = "mother_serial"
        val COLUMN_GENDER: String
            get() = "gender"
        val COLUMN_MARITAL: String
            get() = "marital"
        val COLUMN_SD: String
            get() = "sD"
        val COLUMN_SYNCED: String
            get() = "synced"
        val COLUMN_SYNCED_DATE: String
            get() = "synced_date"
    }
}