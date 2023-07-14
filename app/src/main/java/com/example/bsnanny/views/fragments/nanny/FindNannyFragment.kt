package com.example.bsnanny.views.fragments.nanny

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewSwitcher.ViewFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsnanny.R
import com.example.bsnanny.adapter.findNanny.FindNannyAdapter
import com.example.bsnanny.databinding.FragmentFindNannyBinding
import com.example.bsnanny.models.findNanny.FindNanny
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class FindNannyFragment : Fragment() {
    private lateinit var binding: FragmentFindNannyBinding
    private lateinit var nannyNameArray: Array<String?>
    private lateinit var mList: ArrayList<FindNanny>
    private lateinit var searchList: ArrayList<FindNanny>
    private lateinit var adapter: FindNannyAdapter
    private var titleText: String = ""
    private var iterator: Int = 0
    private lateinit var arrayList: ArrayList<String>
    private val handler = Handler()
    private lateinit var runnable: Runnable
    private var stringIndex : Int = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFindNannyBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_find_nanny, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(binding.nannyToolbar)
        setHasOptionsMenu(true)
        binding.nannyToolbar.title = ""
        binding.nannyToolbar.setOnClickListener {
            handleToolbarClick()
        }
        arrayList = ArrayList<String>()
        arrayList.add("Find Nanny")
        arrayList.add("Get Nanny")
        arrayList.add("Choose the best Nanny")
        arrayList.add("Search Nanny")
        arrayList.add("Search Here")

        binding.requestsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mList = ArrayList<FindNanny>()
        addDataToList()
        adapter = FindNannyAdapter(mList)
        binding.requestsRecyclerView.adapter = adapter

        nannyNameArray = arrayOfNulls<String>(mList.size)

        for (i in mList.indices) {
            nannyNameArray[i] = mList[i].name
        }

        adapter.itemClicked(object : FindNannyAdapter.OnItemsClicked{
            override fun onClicked(position: Int) {
                findNavController().navigate(R.id.action_findNannyFragment_to_hireNannyFragment)
            }

        })



         runnable = object : Runnable {
            override fun run() {

                handleTitleText()
                Log.d("titleText", "run: invoked")
                println("run Invoked")
                handler.postDelayed(this, 7000)
            }
        }

        binding.titleTetSwitcher.setFactory {
            val textView = TextView(requireActivity())
            textView.setTextColor(Color.BLACK)
            textView.textSize = 22F
            textView.typeface = Typeface.DEFAULT_BOLD
            textView.gravity = Gravity.CENTER_HORIZONTAL
            textView
        }
        binding.titleTetSwitcher.setText(arrayList[stringIndex])

    }


    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onCreateOptionsMenu(menu, inflater)",
            "androidx.fragment.app.Fragment"
        )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem: MenuItem = menu.findItem(R.id.searchMenu)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.queryHint = "Search Nanny here"
        searchView.findViewById<LinearLayout>(androidx.appcompat.R.id.search_plate)
            .setBackgroundColor(Color.TRANSPARENT)


            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    filterList(newText)
                    return true
                }

            })
            super.onCreateOptionsMenu(menu, inflater)
        }

    private fun filterList(query: String?) {
        if (query != null) {
            searchList = ArrayList()
            for (i in mList) {
                if (i.name.lowercase(Locale.ROOT).contains(query)) {
                    searchList.add(i)
                }
            }
            if (searchList.isEmpty()) {
                adapter.filteredList(searchList)
                binding.nannyRCVStatus.visibility = View.VISIBLE
                binding.nannyRCVStatus.text = "No Nannies Found"
                Toasty.info(requireContext(), "No Data Found", Toast.LENGTH_SHORT, true).show()
            } else {
                adapter.filteredList(searchList)
                binding.nannyRCVStatus.visibility = View.INVISIBLE
            }
        }

    }

    private fun handleToolbarClick() {
        val searchItem = binding.nannyToolbar.menu.findItem(R.id.searchMenu)
        val searchView = searchItem.actionView as SearchView
        searchItem.expandActionView()
        searchView.isIconified = false
    }

    private fun addDataToList() {
        mList.add(
            FindNanny(
                "Rose Smith",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Nicole Neustadt",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Karin Dietrich",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(FindNanny("Leah Hahn", R.drawable.image, "28 years old", 3.0F, "New York", "3.0"))
        mList.add(
            FindNanny(
                "Leah Eiffel",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Kristin Eberhardt",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Kristin Brauer",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Heike Vogel",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Christina Schwartz",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(FindNanny("Jana Kuhn", R.drawable.image, "28 years old", 3.0F, "New York", "3.0"))
        mList.add(
            FindNanny(
                "Silke Müller",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Stephanie Schulze",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Claudia Faerber",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Ute Feierabend",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Daniela Trommler",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Melanie Traugott",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
        mList.add(
            FindNanny(
                "Melanie Kirsch",
                R.drawable.image,
                "28 years old",
                3.0F,
                "New York",
                "3.0"
            )
        )
    }

    private fun handleTitleText() {

        if (iterator <  arrayList.size){
            titleText = arrayList[iterator]
            if (stringIndex == arrayList.size - 1){
                stringIndex = 0
                binding.titleTetSwitcher.setText(arrayList[stringIndex])
            }else{
                binding.titleTetSwitcher.setText(arrayList[++stringIndex])
            }
            iterator++
        }else{
            iterator = 0
        }
    }
    override fun onStart() {
        handler.postDelayed(runnable, 7000)
        super.onStart()
    }

    override fun onStop() {
        handler.removeCallbacks(runnable)
        super.onStop()
    }

}