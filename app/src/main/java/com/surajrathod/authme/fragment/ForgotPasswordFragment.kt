package com.surajrathod.authme.fragment

import com.surajrathod.authme.R

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.surajrathod.authme.databinding.FragmentForgotPasswordBinding
import com.surajrathod.authme.util.LoadingScreen


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgotPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgotPasswordFragment : Fragment() {


    private lateinit var binding : FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)
        binding = FragmentForgotPasswordBinding.bind(view)

        binding.btnReset.setOnClickListener {
            if(!TextUtils.isEmpty(binding.ETEmail.text.toString().trim(){it <= ' ' })){
                val d = LoadingScreen(activity as Context)
                val dd = d.loadingScreen()
                d.toggleDialog(dd)

            }else{
                Toast.makeText(activity, "please enter email address", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

}