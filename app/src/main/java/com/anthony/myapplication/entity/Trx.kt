package com.anthony.myapplication.entity

import com.anthony.myapplication.entity.Customers
import com.anthony.myapplication.entity.Employee
import com.anthony.myapplication.entity.Order

class Trx (
    var trxId:String?="",
    var customers: Customers? = null,
    var long: String? = "",
    var lat: String? = "",
    var employee: Employee?=null,
    var startDate:String?="",
    var timeDate:String?="",
    var pickUpDate:String?="",
    var productImageUrl:String?="",
    var sendDate:String?="",
    var sendEmployee: Employee?=null,
    var recipient:String?="",
    var recipientImageUrl:String?="",
    var cost:Int?= 0,
    var status:String?="",
    var orders:List<Order>?= null
        )