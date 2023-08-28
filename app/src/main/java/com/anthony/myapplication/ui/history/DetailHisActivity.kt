package com.anthony.myapplication.ui.history

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthony.myapplication.R
import com.anthony.myapplication.entity.Order
import com.anthony.myapplication.helper.DateTimeHelper
import com.anthony.myapplication.ui.camera.Constant
import com.anthony.myapplication.ui.employee.EmpViewModel
import com.anthony.myapplication.ui.trx.FinishActivity
import com.anthony.myapplication.ui.trx.OrderAdapter
import com.anthony.myapplication.ui.trx.TrxViewModel
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Float
import java.text.NumberFormat
import java.util.*

class DetailHisActivity : AppCompatActivity() {
    private lateinit var rvOrder: RecyclerView
    private lateinit var btnBack: ImageButton
    private lateinit var btnMap: Button
    private lateinit var btnCard: Button
    private lateinit var tvName: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvPickup: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpPhone: TextView
    private lateinit var tvSendDate: TextView
    private lateinit var tvSendName: TextView
    private lateinit var tvSendPhone: TextView
    private lateinit var tvRecipient: TextView
    private lateinit var tvCost: TextView
    private lateinit var tvStatus: TextView
    private lateinit var trxViewModel: TrxViewModel
    private lateinit var empViewModel: EmpViewModel
    private var orders = arrayListOf<Order>()
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var long:String
    private lateinit var lat:String
    private lateinit var imageProUrl:String
    private lateinit var status:String
    private lateinit var recipientUrl:String
    private lateinit var cusAddress:String
    private var cost:Int = 0
    private lateinit var trxId:String
    private lateinit var imgLetter: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
    private val locale = Locale("id", "id")
    private val nf: NumberFormat = NumberFormat.getInstance(locale)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_his)
        btnBack = findViewById(R.id.btn_back)
        btnMap = findViewById(R.id.btn_map)
        btnCard = findViewById(R.id.btn_card)
        rvOrder = findViewById(R.id.rvOrder)
        tvName = findViewById(R.id.tv_name)
        tvAddress = findViewById(R.id.tv_address)
        tvPhone = findViewById(R.id.tv_phone)
        tvDate = findViewById(R.id.tv_date)
        tvTime = findViewById(R.id.tv_time)
        tvPickup = findViewById(R.id.tv_pickUp_date)
        tvEmpName = findViewById(R.id.tv_employee_name)
        tvEmpPhone = findViewById(R.id.tv_employee_phone)
        tvSendDate = findViewById(R.id.tv_send_date)
        tvSendName = findViewById(R.id.tv_send_name)
        tvSendPhone = findViewById(R.id.tv_send_phone)
        tvRecipient = findViewById(R.id.tv_recipient)
        tvCost = findViewById(R.id.tv_cost)
        tvStatus = findViewById(R.id.tv_status)
        imgLetter = findViewById(R.id.img_product)

        trxViewModel = ViewModelProvider(this@DetailHisActivity)[TrxViewModel::class.java]
        empViewModel = ViewModelProvider(this@DetailHisActivity)[EmpViewModel::class.java]

        scaleGestureDetector = ScaleGestureDetector(applicationContext, ScaleListener())

        btnBack.setOnClickListener {
            finish()
        }

        btnCard.setOnClickListener {
            showPhoto()
        }


        trxId = intent.getStringExtra("TRX_ID").toString()

        rvOrder.setHasFixedSize(false)
        rvOrder.layoutManager = LinearLayoutManager(applicationContext)
        orderAdapter = OrderAdapter(orders)


        trxViewModel.getTrxHisById(trxId).observe(this@DetailHisActivity){
            orders.clear()
            val cusName = it.customers!!.name
            val cusPhone = it.customers!!.phone
            cusAddress = it.customers!!.address.toString()
            val startDate = it.startDate
            val startTime = it.timeDate
            val pickUpDate = it.pickUpDate
            val pickUpName = it.employee!!.name
            val pickUpPhone = it.employee!!.phone
            val sendDate = it.sendDate
            val sendName = it.sendEmployee!!.name
            val sendPhone = it.sendEmployee!!.phone
            val recipient = it.recipient
            cost = it.cost!!.toInt()
            status = it.status.toString()
            long = it.long.toString()
            lat = it.lat.toString()
            imageProUrl = it.productImageUrl.toString()
            recipientUrl = it.recipientImageUrl.toString()
            tvName.text = cusName
            tvAddress.text = cusAddress
            tvPhone.text = cusPhone
            tvName.text = cusName
            tvDate.text = startDate
            tvTime.text = startTime
            tvPickup.text = pickUpDate
            tvEmpName.text = pickUpName
            tvEmpPhone.text = pickUpPhone
            tvSendDate.text = sendDate
            tvSendName.text = sendName
            tvSendPhone.text = sendPhone
            tvRecipient.text = recipient
            tvCost.text = "Rp "+nf.format(cost).toString().replace(",","")
            tvStatus.text = status
            orders.addAll(it.orders!!)
            rvOrder.adapter = orderAdapter


        }

        btnMap.setOnClickListener {
            val strUri = "http://maps.google.com/maps?q=loc:$lat,$long ($cusAddress)"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))
            intent.setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity")
            startActivity(intent)
        }



    }

    private fun showCostDialog() {
        val builder = AlertDialog.Builder(this@DetailHisActivity)
        builder.setTitle("Total biaya")
        val edtCost = EditText(this@DetailHisActivity)
        edtCost.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(edtCost)
        builder.setMessage("Yakin akan diinput?")

        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val costs: Int = edtCost.text.toString().toInt()
            val mData = Constant.ROOT_DB
            mData.child("trx").child(trxId).child("cost").setValue(costs)

        }

        builder.setNegativeButton(getString(R.string.no)) { _, _ ->

        }

        builder.setNeutralButton("Cancel") { _, _ ->

        }
        builder.show()
    }

    private fun shoDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Kirim Order")
        builder.setMessage("Yakin akan kirim Order?")

        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val mData: DatabaseReference = FirebaseDatabase.getInstance("https://persontracking-39122-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("PickUp")
            empViewModel.getEmpById().observe(this@DetailHisActivity){
                val name = it.name
                val address = it.address
                val phone = it.phone
                val area = it.area
                val status = "Dikirim"
                val sendDate = DateTimeHelper.currentDate
                mData.child("trx").child(trxId).child("sendEmployee").child("name").setValue(name)
                mData.child("trx").child(trxId).child("sendEmployee").child("phone").setValue(phone)
                mData.child("trx").child(trxId).child("sendEmployee").child("address").setValue(address)
                mData.child("trx").child(trxId).child("sendEmployee").child("area").setValue(area)
                mData.child("trx").child(trxId).child("status").setValue(status)
                mData.child("trx").child(trxId).child("sendDate").setValue(sendDate)
                Toast.makeText(applicationContext,"Pengambilan Order Kirim berhasil!!", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton(getString(R.string.no)) { _, _ ->
            Toast.makeText(applicationContext, "Tidak jadi diambil!!", Toast.LENGTH_SHORT).show()

        }

        builder.setNeutralButton("Cancel") { _, _ ->

        }
        builder.show()
    }

    private fun shoAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pengambilan Order")
        builder.setMessage("Yakin akan ambil Order?")

        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val mData: DatabaseReference = FirebaseDatabase.getInstance("https://persontracking-39122-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("PickUp")
            empViewModel.getEmpById().observe(this@DetailHisActivity){
                val name = it.name
                val address = it.address
                val phone = it.phone
                val area = it.area
                val status = "Diambil"
                val pickUpDate = DateTimeHelper.currentDate
                mData.child("trx").child(trxId).child("employee").child("name").setValue(name)
                mData.child("trx").child(trxId).child("employee").child("phone").setValue(phone)
                mData.child("trx").child(trxId).child("employee").child("address").setValue(address)
                mData.child("trx").child(trxId).child("employee").child("area").setValue(area)
                mData.child("trx").child(trxId).child("status").setValue(status)
                mData.child("trx").child(trxId).child("pickUpDate").setValue(pickUpDate)
                Toast.makeText(applicationContext,"Pengambilan Order berhasil!!", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton(getString(R.string.no)) { _, _ ->
            Toast.makeText(applicationContext, "Order tidak diambil!!", Toast.LENGTH_SHORT).show()

        }

        builder.setNeutralButton("Cancel") { _, _ ->

        }
        builder.show()
    }

    private fun showPhoto() {
        imgLetter.visibility = View.VISIBLE
        if (imageProUrl.isNotEmpty()){
            Glide.with(applicationContext)
                .load(imageProUrl)
                .override(300,300)
                .into(imgLetter)

        }else{
            Toast.makeText(applicationContext,"Maaf foto tidak ditemukan!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(motionEvent)
        return true
    }
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = Float.max(0.1f, Float.min(scaleFactor, 10.0f))
            imgLetter.scaleX = scaleFactor
            imgLetter.scaleY = scaleFactor
            return true
        }
    }
}