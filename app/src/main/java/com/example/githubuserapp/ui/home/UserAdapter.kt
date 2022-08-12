package com.example.githubuserapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.model.ListResponse
import com.example.githubuserapp.ui.detail.DetailUserActivity

class UserAdapter(private val inUser: ListResponse) : RecyclerView.Adapter<UserAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_user, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = inUser.listUser[position]

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.imgAvatar)

        holder.apply {
            tvUsername.text = user.login

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, user.login)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return inUser.listUser.size
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
    }
}