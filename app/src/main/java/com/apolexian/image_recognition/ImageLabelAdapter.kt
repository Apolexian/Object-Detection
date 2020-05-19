package com.apolexian.image_recognition

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel
import kotlinx.android.synthetic.main.item_row.view.*

class ImageLabelAdapter(private var firebaseVisionList: List<Any>) :
    RecyclerView.Adapter<ImageLabelAdapter.ItemHolder>() {
    lateinit var context: Context

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindCloud(currentItem: FirebaseVisionImageLabel) {
            itemView.itemName.text = currentItem.text
            itemView.itemAccuracy.text = "Probability : ${(currentItem.confidence * 100).toInt()}%"
        }
    }

    fun setList(visionList: List<Any>) {
        firebaseVisionList = visionList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val currentItem = firebaseVisionList[position]
        holder.bindCloud(currentItem as FirebaseVisionImageLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
        return ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_row, parent, false))
    }

    override fun getItemCount() = firebaseVisionList.size
}