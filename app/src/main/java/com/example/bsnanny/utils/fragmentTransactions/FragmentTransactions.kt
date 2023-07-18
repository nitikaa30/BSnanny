package com.example.bsnanny.utils.fragmentTransactions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.bsnanny.R
object FragmentTransactions {
    fun replaceFragment(fragment: Fragment, fragmentActivity: FragmentActivity, tag : String?){
        val fragmentManager = fragmentActivity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(tag)
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment, tag)
        fragmentTransaction.commit()
    }
    fun addFragment(fragment: Fragment, fragmentActivity: FragmentActivity, tag : String){
        val fragmentManager = fragmentActivity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(tag)
        fragmentTransaction.add(R.id.fragmentContainerView, fragment, tag)
        fragmentTransaction.commit()
    }
    fun popFragmentFromBackStack(tag: String, fragmentActivity: FragmentActivity) {
        val fragmentManager = fragmentActivity.supportFragmentManager
        fragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}