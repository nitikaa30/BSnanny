package com.example.bsnanny.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.getSystemService
import androidx.viewpager.widget.PagerAdapter
import com.example.bsnanny.R


class ViewPagerAdapter(context: Context) : PagerAdapter() {
    private lateinit var context : Context
    init {
        this.context = context
    }
    private var images = arrayOf(
        R.drawable.first,
        R.drawable.second,
        R.drawable.third
    )
    private var headings = arrayOf(
        "Connect With Nanny","Find The  Best Nanny" , "Choose The  Best Nanny"
    )
    private var descriptions = arrayOf(R.string.intro, R.string.intro, R.string.intro)
    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view : View =  layoutInflater.inflate(R.layout.slider_layout, container, false)
        val image : ImageView = view.findViewById(R.id.imageView)
        val heading : TextView = view.findViewById(R.id.heading)
        val description : TextView = view.findViewById(R.id.description)
        image.setImageResource(images[position])
        heading.text = headings[position]
        description.setText(descriptions[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}