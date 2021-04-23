package com.example.sneakersalert.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.sneakersalert.DataClasses.ItemNav
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.item_parent.view.*


class AdapterNavigation(
    val context: Context,
    val lParent: ArrayList<ItemNav>,
    val lChild: HashMap<ItemNav, ArrayList<ItemNav>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return lParent.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return if (lChild[lParent[groupPosition]] == null) {
            0

        } else {
            lChild[lParent[groupPosition]]!!.size
        }
    }

    override fun getGroup(groupPosition: Int): Any {
        return lParent[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return lChild[lParent[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    @SuppressLint("ResourceAsColor")
    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var convertView = convertView
        val headerTitle = getGroup(groupPosition) as ItemNav
        if (convertView == null) {
            val infalInflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.item_parent, null)
        }
        val lbHeader = convertView?.findViewById<TextView>(R.id.item)
        lbHeader?.text = headerTitle.text
        convertView?.textView2?.visibility = View.GONE
        if (getChildrenCount(groupPosition) == 0) {
            convertView?.arrowdown?.setImageResource(0)
        } else {
            if (isExpanded)
                convertView?.arrowdown?.setImageResource(R.drawable.dropup)
            else
                convertView?.arrowdown?.setImageResource(R.drawable.dropdown)
        }


        return convertView

    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView

        val childText = getChild(groupPosition, childPosition) as ItemNav

        if (convertView == null) {
            val infalInflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.item_child, null)
        }

        val txtListChild = convertView?.findViewById<View>(R.id.text_child) as TextView

        txtListChild.text = childText.text

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

}