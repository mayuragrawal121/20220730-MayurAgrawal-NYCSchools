package com.nycschools.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nycschools.data.School
import com.nycschools.databinding.ListItemSchoolBinding

class SchoolsAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<MainViewHolder>() {
    private var schools = mutableListOf<School>()
    fun setSchoolList(movies: List<School>) {
        this.schools = movies.toMutableList()
        notifyDataSetChanged()
    }

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

interface ClickListener {
    fun onClick(dbn: String)
}