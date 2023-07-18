package com.example.bsnanny.adapter.requests

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bsnanny.adapter.findNanny.FindNannyAdapter
import com.example.bsnanny.databinding.RequestsItemsBinding
import com.example.bsnanny.models.requests.parent.Booking
import com.example.bsnanny.models.requests.parent.Data
import com.example.bsnanny.viewmodels.requests.RequestViewModel

class ParentsRequestAdapter(private val mList: ArrayList<Data>, private val viewModelStoreOwner: ViewModelStoreOwner)
    : RecyclerView.Adapter<ParentsRequestAdapter.ParentRequestsViewHolder>() {

    private lateinit var context: Context
    interface OnItemsClicked {
        fun onClicked(position: Int)
    }

    private lateinit var mListeners: OnItemsClicked

    fun itemClicked(listeners: OnItemsClicked) {
        mListeners = listeners
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParentsRequestAdapter.ParentRequestsViewHolder {
        val binding=RequestsItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ParentRequestsViewHolder(binding,mListeners)
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
    fun setRequests(requests: List<Data>) {
        mList.clear()
        mList.addAll(requests)
        notifyDataSetChanged()
    }
    inner class ParentRequestsViewHolder(private val binding: RequestsItemsBinding, listeners: ParentsRequestAdapter.OnItemsClicked)
        : RecyclerView.ViewHolder(binding.root){

            fun bind(list: Data){
                binding.apply {
                    name.text=list.Booking.Parent.User.name
                    place.text=list.Booking.Parent.User.city
                    parent.text=list.Booking.Parent.child_category.toString()
                    child.text=list.Booking.no_of_children.toString()
                    Glide.with(itemView)
                        .load(list.Booking.Parent.User.avatar)
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
        init {
            binding.root.setOnClickListener {
                listeners.onClicked(position = bindingAdapterPosition)
            }
        }
    }
}