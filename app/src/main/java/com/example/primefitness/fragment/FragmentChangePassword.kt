package com.example.primefitness.fragment

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.primefitness.R
import com.example.primefitness.databinding.FragmentAddMemberBinding
import com.example.primefitness.databinding.FragmentChangePasswordBinding
import com.example.primefitness.global.DB
import com.example.primefitness.global.MyFunction
import java.lang.Exception


class FragmentChangePassword : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private var db:DB?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Change Password"
        db = activity?.let { DB(it) }

        fillOldMobile()

        binding.btnChangePassword.setOnClickListener {
            try {
                if(binding.edtNewPassword.text.toString().trim().isNotEmpty()) {
                        if (binding.edtNewPassword.text.toString()
                                .trim() == binding.edtConfirmPassword.text.toString().trim()
                        ) {
                            val sqlQuery =
                                "UPDATE ADMIN SET PASSWORD=" + DatabaseUtils.sqlEscapeString(
                                    binding.edtNewNumber.text.toString().trim()
                                ) + ""
                            db?.executeQuery(sqlQuery)
                            showToast("Password changed successfully")
                        } else {
                            showToast("Passwords do not match!")
                        }
                    } else {
                        showToast("Enter new password")
                    }
            } catch (e:Exception){
                e.printStackTrace()
            }
        }

        binding.btnChangeMobile.setOnClickListener {
            try {
                if(binding.edtNewNumber.text.toString().trim().isNotEmpty()){
                val sqlQuery = "UPDATE ADMIN SET MOBILE='"+binding.edtNewNumber.text.toString().trim()+"'"
                db?.executeQuery(sqlQuery)
                showToast("Mobile number changed successfully")
                } else {
                    showToast("Enter new mobile number")
                }
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun fillOldMobile(){
        try {
            val sqlQuery = "SELECT MOBILE FROM ADMIN"
            db?.fireQuery(sqlQuery)?.use {
                val mobile = MyFunction.getvalue(it, "MOBILE")
                if(mobile.trim().isNotEmpty()){
                    binding.edtOldNumber.setText(mobile)
                }
            }
        } catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun showToast(value:String){
        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
    }
}