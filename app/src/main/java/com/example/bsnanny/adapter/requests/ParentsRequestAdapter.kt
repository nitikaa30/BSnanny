package com.example.bsnanny.adapter.requests

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bsnanny.databinding.RequestsItemsBinding
import com.example.bsnanny.models.feedbackModel.FeedbackList
import com.example.bsnanny.models.requests.parent.Booking
import com.example.bsnanny.models.requests.parent.Parent
import com.example.bsnanny.models.requests.parent.User
import com.example.bsnanny.utils.fragmentTransactions.FragmentTransaction
import com.example.bsnanny.viewmodels.requests.RequestViewModel
import com.example.bsnanny.views.fragments.chat.ChatFragment
import com.example.bsnanny.views.fragments.profile.ProfileFragment

class ParentsRequestAdapter(private val mList: ArrayList<Booking>, private val viewModelStoreOwner: ViewModelStoreOwner)
    : RecyclerView.Adapter<ParentsRequestAdapter.ParentRequestsViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParentsRequestAdapter.ParentRequestsViewHolder {
        val binding=RequestsItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ParentRequestsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ParentsRequestAdapter.ParentRequestsViewHolder,
        position: Int
    ) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    fun setRequests(requests: List<Booking>) {
        mList.clear()
        mList.addAll(requests)
        notifyDataSetChanged()
    }
    inner class ParentRequestsViewHolder(private val binding: RequestsItemsBinding)
        : RecyclerView.ViewHolder(binding.root){

            fun bind(list: Booking){
                binding.apply {
                    name.text=list.Parent.User.name
                    place.text=list.Parent.User.address
                    parent.text=list.Parent.User.gender
                    child.text=list.Parent.no_of_children.toString()
                    Glide.with(itemView)
                        .load(list.Parent.User.avatar)
                        .into(requestsImage)
                    accept.setOnClickListener {
                        val viewModel= ViewModelProvider(viewModelStoreOwner)[RequestViewModel::class.java]
                        viewModel.acceptRequest(list)
                    }
                    reject.setOnClickListener {
                        val viewModel=ViewModelProvider(viewModelStoreOwner)[RequestViewModel::class.java]
                        viewModel.rejectRequest(list)
                    }
                    chat.setOnClickListener {
//                        FragmentTransaction.replaceFragment(ChatFragment(), fragmentActivity = , ChatFragment().tag)
                    }
                }
            }
    }
}