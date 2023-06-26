package com.example.bsnanny.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bsnanny.databinding.InvitationRcvItemsBinding
import com.example.bsnanny.models.InvitationModel

class InvitationsAdapter(private val mList: ArrayList<InvitationModel>) :
    RecyclerView.Adapter<InvitationsAdapter.InvitationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvitationViewHolder {
        val binding =
            InvitationRcvItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InvitationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InvitationViewHolder, position: Int) {
        holder.name.text = mList[position].name
        holder.age.text = mList[position].nannyAge.toString()
        holder.photo.setImageResource(mList[position].photoUri)
        holder.experience.text = mList[position].experience
        holder.country.text = mList[position].country
        holder.status.text = mList[position].status
        holder.charge.text = mList[position].charge.toString()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class InvitationViewHolder(binding: InvitationRcvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val photo = binding.invitationsImageView
        val name = binding.nannyName
        val age = binding.nannyAge
        val experience = binding.nannyExperience
        val country = binding.nannyCountry
        val status = binding.nannyStatus
        val charge = binding.nannyCost
    }

}
