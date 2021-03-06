package edu.aku.hassannaqvi.uen_scans_sosas.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.validatorcrawler.aliazaz.Validator
import edu.aku.hassannaqvi.uen_scans_sosas.R
import edu.aku.hassannaqvi.uen_scans_sosas.adapter.ChildListAdapter
import edu.aku.hassannaqvi.uen_scans_sosas.content_provider.DataFactory
import edu.aku.hassannaqvi.uen_scans_sosas.contracts.FamilyMembersContract
import edu.aku.hassannaqvi.uen_scans_sosas.contracts.FormsContract
import edu.aku.hassannaqvi.uen_scans_sosas.core.DatabaseHelper
import edu.aku.hassannaqvi.uen_scans_sosas.core.MainApp.*
import edu.aku.hassannaqvi.uen_scans_sosas.databinding.ActivityInfoBinding
import edu.aku.hassannaqvi.uen_scans_sosas.ui.other.EndingActivity
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class InfoActivity : AppCompatActivity() {

    lateinit var bi: ActivityInfoBinding
    lateinit var db: DatabaseHelper
    var villageCode: String? = null
    var areaCode: String? = null
    lateinit var adapter: ChildListAdapter

    var flag = false

    companion object {
        var motherList = MutableLiveData<MutableList<FamilyMembersContract>>()
        lateinit var womenList: MutableList<Pair<Int, Boolean>>
        lateinit var childList: MutableList<FamilyMembersContract>

        fun checkingWomenExist(serial: Int): Boolean {
            val item = womenList.find { it.first == serial }
            return item?.second ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bi = DataBindingUtil.setContentView(this, R.layout.activity_info)
        bi.callback = this
        db = DatabaseHelper(this)

        setupViews()
    }

    private fun setupViews() {
        womenList = ArrayList()
        setupRecyclerView()

        bi.checkHH.setOnClickListener {
            if (!formValidation()) return@setOnClickListener
            val cluster: Int = Integer.valueOf(bi.clusterNumber.text.toString())
            val loginFlag = if (cluster <= 5000) {
                !(userName == "test1234" || userName == "dmu@aku" || userName.substring(0, 4) == "user")
            } else {
                userName == "test1234" || userName == "dmu@aku" || userName.substring(0, 4) == "user"
            }
            if (!loginFlag) {
                Toast.makeText(this, "Can't proceed test cluster for current user!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            bi.motherList.visibility = View.VISIBLE
            DataFactory(this@InfoActivity, bi.clusterNumber.text.toString(), bi.hhName.text.toString())
        }

        motherList.observe(this, Observer { item ->
            adapter.setMList(item)
        })

    }

    private fun setupRecyclerView() {
        adapter = ChildListAdapter(this@InfoActivity, mutableListOf(), true)
        bi.motherList.layoutManager = LinearLayoutManager(this@InfoActivity)
        bi.motherList.adapter = adapter
        adapter.setItemClicked { item, position, isMother, holder ->
            openDialog(this@InfoActivity, item, isMother)
            itemClick = OnItemClick {
                if (!flag) {
                    saveDraft(item)
                    if (!updateDB()) {
                        return@OnItemClick
                    }
                }
                womenList.add(Pair(item.serialno.toInt(), true))

                startActivity(Intent(this@InfoActivity, SectionBActivity::class.java).putExtra(motherInfo, item))
            }

        }
    }

    private fun saveDraft(item: FamilyMembersContract) {
        fc = FormsContract()
        val pref = getSharedPreferences("tagName", Context.MODE_PRIVATE)
        fc.devicetagID = pref.getString("tagName", null)
        fc.deviceID = deviceId
        fc.formDate = DateFormat.format("dd-MM-yyyy HH:mm", Date()).toString()
        fc.user = userName
        fc.talukdaCode = talukaCode.toString()
        fc.uc = ucCode.toString()
        fc.areaCode = areaCode
        fc.village = villageCode
        fc.appversion = appInfo.appVersion
        fc.clusterCode = bi.clusterNumber.text.toString()
        fc.hhno = bi.hhName.text.toString()

        val json = JSONObject()
        json.put("_luid", item.uuid)
        fc.setsA(json.toString())


        setGPS(this)

    }

    private fun formValidation(): Boolean {
        return Validator.emptyCheckingContainer(this, bi.checkLayout)
    }

    private fun updateDB(): Boolean {
        val rowId: Long
        val db = DatabaseHelper(this)
        rowId = db.addForm(fc)
        return if (rowId > 0) {
            fc._ID = rowId.toString()
            fc._UID = fc.deviceID + fc._ID
            db.updateFormID()
            true
        } else {
            false
        }
    }

    override fun onResume() {
        super.onResume()

        if (womenList.size == 0) return

        if (womenList.size == motherList.value!!.size) {
            finish()
            startActivity(Intent(this, EndingActivity::class.java).putExtra("complete", true))
            return
        }

        flag = true

        bi.checkLayout.visibility = View.GONE

        adapter.notifyDataSetChanged()

    }
}
