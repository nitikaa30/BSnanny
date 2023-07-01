package com.example.bsnanny.fragmentTransactions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.bsnanny.R
object FragmentTransaction {
    fun replaceFragment(fragment: Fragment, fragmentActivity: FragmentActivity, tag : String?){
        val fragmentManager = fragmentActivity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(tag)
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment, tag)
        fragmentTransaction.commit()
    }
    fun addFragment(fragment: Fragment, fragmentActivity: FragmentActivity, tag : String){
        val fragmentManager = fragmentActivity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(tag)
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment, tag)
        fragmentTransaction.commit()
    }
    fun popFragmentFromBackStack(tag: String, fragmentActivity: FragmentActivity) {
        val fragmentManager = fragmentActivity.supportFragmentManager
        fragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}