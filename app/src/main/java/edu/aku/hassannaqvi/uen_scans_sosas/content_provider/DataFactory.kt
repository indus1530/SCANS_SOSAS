package edu.aku.hassannaqvi.uen_scans_sosas.content_provider

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.widget.Toast
import edu.aku.hassannaqvi.uen_scans_sosas.contracts.FamilyMembersContract
import edu.aku.hassannaqvi.uen_scans_sosas.ui.InfoActivity
import kotlinx.coroutines.*

class DataFactory(private val context: Context, private val cluster_no: String, private val hhno: String) {

    init {
        GlobalScope.launch { populateList() }
    }

    private suspend fun populateList() = withContext(Dispatchers.IO) {
        //        InfoActivity.motherList.value = mutableListOf()
        var indexCursor: Cursor? = null
        val index = async {
            indexCursor = setContent(kishType = 1)
        }
        if (index.await().let { true }) {
            if (indexCursor != null) {
                if (indexCursor!!.count > 0) {
                    while (indexCursor!!.moveToNext()) {
                        val lst: MutableList<FamilyMembersContract> = mutableListOf()
                        lst.add(FamilyMembersContract().hydrateContentData(indexCursor))
                        withContext(Dispatchers.Main) {
                            InfoActivity.motherList.value = lst
                        }
                    }

                    var indexCursorChild: Cursor? = null
                    val childIndex = async { indexCursorChild = setContent(InfoActivity.motherList.value?.get(0), 2) }
                    if (childIndex.await().let { true }) {
                        if (indexCursorChild != null) {
                            if (indexCursorChild!!.count > 0) {
                                InfoActivity.childList = mutableListOf()
                                while (indexCursorChild!!.moveToNext()) {
                                    withContext(Dispatchers.Main) {
                                        InfoActivity.childList.add(FamilyMembersContract().hydrateContentData(indexCursorChild))
                                    }
                                }
                            }
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        InfoActivity.motherList.value = mutableListOf()
                        Toast.makeText(context, "No Mother Found!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setContent(fmc: FamilyMembersContract? = null, kishType: Int): Cursor? {
        val uri = Uri.parse("content://com.scans.familymember")
        val columns = arrayOf(
                FamilyMemberInterface.COLUMN_ID,
                FamilyMemberInterface.COLUMN_UID,
                FamilyMemberInterface.COLUMN_UUID,
                FamilyMemberInterface.COLUMN_LUID,
                FamilyMemberInterface.COLUMN_KISH_SELECTED,
                FamilyMemberInterface.COLUMN_CLUSTERNO,
                FamilyMemberInterface.COLUMN_HHNO,
                FamilyMemberInterface.COLUMN_SERIAL_NO,
                FamilyMemberInterface.COLUMN_NAME,
                FamilyMemberInterface.COLUMN_RELATION_HH,
                FamilyMemberInterface.COLUMN_AGE,
                FamilyMemberInterface.COLUMN_MOTHER_NAME,
                FamilyMemberInterface.COLUMN_MOTHER_SERIAL,
                FamilyMemberInterface.COLUMN_GENDER,
                FamilyMemberInterface.COLUMN_MARITAL,
                FamilyMemberInterface.COLUMN_SD)

        val whereClause: String
        val whereArgs: Array<String>
        if (kishType == 1) {
            whereClause = (FamilyMemberInterface.COLUMN_CLUSTERNO + "=? AND " + FamilyMemberInterface.COLUMN_HHNO + "=? AND "
                    + FamilyMemberInterface.COLUMN_KISH_SELECTED + "=? ")
            whereArgs = arrayOf(cluster_no, hhno, kishType.toString())
        } else {
            whereClause = (FamilyMemberInterface.COLUMN_CLUSTERNO + "=? AND " + FamilyMemberInterface.COLUMN_HHNO + "=? AND "
                    + FamilyMemberInterface.COLUMN_MOTHER_SERIAL + "=? AND " + FamilyMemberInterface.COLUMN_UUID + "=? AND " + FamilyMemberInterface.COLUMN_MOTHER_NAME + "=? AND (" + FamilyMemberInterface.COLUMN_AGE + " IN (5,6,7,8,9))")
            whereArgs = arrayOf(cluster_no, hhno, fmc!!.serialno, fmc.uuid, fmc.name)
        }
        val orderBy = "${FamilyMemberInterface.COLUMN_ID} ASC"

        val resolver = context.contentResolver

        return resolver.query(uri, columns, whereClause, whereArgs, orderBy)


    }

}