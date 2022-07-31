package com.nycschools.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nycschools.data.School
import com.nycschools.databinding.ListItemSchoolBinding

/**
 * Created this adapter class to load schools list on RecyclerView.
 * Passing #ClickListener in constructor to communicate between Activity and Adapter.
 * */
class SchoolsAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<MainViewHolder>() {

    // Created list to hold School object and then use it to display on ui.
    private var schools = mutableListOf<School>()

    /**
     * Created this method to set School list from Activity.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setSchoolList(schools: List<School>) {
        this.schools = schools.toMutableList()
        notifyDataSetChanged()
    }

    // Layout view inflated in this method and passed to ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ListItemSchoolBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val school = schools[position]
        holder.binding.schoolName.text = school.school_name
        holder.binding.root.setOnClickListener {
            clickListener.onClick(school.dbn)
        }
    }

    override fun getItemCount() = schools.size
}

class MainViewHolder(val binding: ListItemSchoolBinding) : RecyclerView.ViewHolder(binding.root)

// Created ClickListener interface to communicate between Activity and Adapter.
interface ClickListener {
    fun onClick(dbn: String)
}