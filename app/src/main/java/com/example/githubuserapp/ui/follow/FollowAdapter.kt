package com.example.githubuserapp.ui.follow

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.Const.TAG
import com.example.githubuserapp.R
import com.example.githubuserapp.model.UserResponse
import com.example.githubuserapp.ui.detail.DetailUserActivity

class FollowAdapter(private val inUser: List<UserResponse>): RecyclerView.Adapter<FollowAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_user, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = inUser[position]

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.imgAvatar)

        holder.tvUsername.text = user.login

        Log.d(TAG, user.toString())

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, user.login)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return inUser.size
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
    }
}