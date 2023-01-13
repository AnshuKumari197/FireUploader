package com.coding.a30fireuploader

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.StorageReference
import android.app.ProgressDialog
import android.os.Bundle
import com.coding.a30fireuploader.R
import com.google.firebase.storage.FirebaseStorage
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.coding.a30fireuploader.MainActivity
import com.google.android.gms.tasks.OnSuccessListener
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener

class MainActivity : AppCompatActivity() {
    var mStorage: StorageReference? = null
    var button: Button? = null
    var imageView: ImageView? = null
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById<View>(R.id.button) as Button
        imageView = findViewById<View>(R.id.imageView) as ImageView
        mStorage = FirebaseStorage.getInstance().reference
        progressDialog = ProgressDialog(this)
        button!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            progressDialog!!.setMessage("UPLOADING...")
            val uri = data!!.data
            progressDialog!!.show()
            imageView!!.setImageURI(uri)
            val fileName = mStorage!!.child("Photos/" + uri!!.lastPathSegment + ".png")
            fileName.putFile(uri).addOnSuccessListener {
                Toast.makeText(this@MainActivity, "UPLOAD SUCCESS", Toast.LENGTH_SHORT).show()
                progressDialog!!.dismiss()
            }.addOnFailureListener {
                Toast.makeText(
                    this@MainActivity,
                    "UPLOAD FAILED",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private const val GALLERY = 4
    }
}