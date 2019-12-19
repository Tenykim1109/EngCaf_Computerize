package com.student.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.corner_page.*
import java.util.*
import androidx.core.app.NotificationCompat.getExtras
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T





object order {
    var studentID: String = student.INSTANCE.id;
    var menu: String = ""
    var state: Int = 1
}


class cornerActivity : AppCompatActivity(){
    data class User(
            var form: String = "",
            var name: String = "",
            var price: String = ""
    )
    lateinit var parent_path : List<String>
    var data_count : Int = 0
    var ref = FirebaseDatabase.getInstance().reference
    val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.corner_page)
        var intent: Intent = getIntent()
        val select_corner = intent.extras!!.getString("select").toString();
        parent_path = select_corner.split("_");
        val mItem = ArrayList<User>()
        var adapter = ListAdapter(this,mItem)
        listView.adapter = adapter


        ref.child(select_corner).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(data_count == 0) {
                    Log.d("TAG","dataSnapshot")
                    for (p0 in dataSnapshot.children) {
                        var userdata = User(
                                p0.child("form").value.toString(),
                                p0.child("name").value.toString(),
                                p0.child("price").value.toString()
                        )
                        Log.d("TAG","forLoop")
                        mItem.add(userdata)
                    }
                    adapter.notifyDataSetChanged()
                    data_count = data_count +1
                }
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private class ViewHolder{
        var image: ImageView? = null
        var Text: TextView? = null
        var button: Button? = null
    }

    inner class ListAdapter(val context: Context, val item : ArrayList<User>) : BaseAdapter(){

        override fun getCount() = item.size
        override fun getItem(p0: Int) = item[p0]
        override fun getItemId(p0: Int) = p0.toLong()
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
            var view : View
            val holder : ViewHolder

            if (p1 == null) {
                view = LayoutInflater.from(context).inflate(R.layout.row, null)
                holder = ViewHolder()
                holder.image = view?.findViewById<ImageView>(R.id.imageView)
                holder.Text = view?.findViewById<TextView>(R.id.textView3)
                holder.button = view?.findViewById<Button>(R.id.button1)
                view.tag = holder
            } else {
                holder = p1.tag as ViewHolder
                view = p1
                return view
            }
            var mitem = item[p0]

            val test: String = mitem.name + "\n" + mitem.price + "원"
            val submoney = student.INSTANCE.money - mitem.price.toInt()
            holder.Text?.text = test
            Log.d("TAG","input text")



            val storageRef = storage.reference.child(parent_path[0]).child(mitem.name +"."+mitem.form)
            storageRef.downloadUrl.addOnCompleteListener{
                task ->  if(task.isSuccessful) {
                Glide.with(this@cornerActivity).load(task.getResult()).into(holder.image!!)
            }
            }
            holder.button?.setOnClickListener {
                if(student.INSTANCE.order == ""){
                    order.menu = mitem.name
                    ref.child(parent_path[0] + "_order").child(order.studentID).setValue(order)
                    ref.child("student").child(order.studentID).child("order").setValue("주문수락대기중")
                    ref.child("student").child(order.studentID).child("money").setValue(submoney)
                    Toast.makeText(this@cornerActivity, "주문 성공!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@cornerActivity, "이미 신청한 주문이 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            return view
        }
    }
}