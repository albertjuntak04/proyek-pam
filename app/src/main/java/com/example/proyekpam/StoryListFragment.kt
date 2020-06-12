package com.example.proyekpam


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekpam.story.StoryListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

/**
 * A simple [Fragment] subclass.
 */

private const val TAG = "CrimeListFragment"
class StoryListFragment : Fragment() {

    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var addStory:FloatingActionButton
    interface Callbacks{
        fun onStorySelected()
        fun onStorySelected(storyId: UUID)
    }

    private var callbacks: Callbacks? = null
    private var adapter: StoryAdapter? = StoryAdapter(emptyList())
    private val storyListViewModel : StoryListViewModel by lazy {
        ViewModelProviders.of(this).get(StoryListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_story_list,container,false)
        addStory = view.findViewById(R.id.addStory) as FloatingActionButton
        storyRecyclerView = view.findViewById(R.id.list_story) as RecyclerView
        storyRecyclerView.layoutManager = LinearLayoutManager(context)
        storyRecyclerView.adapter = adapter
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storyListViewModel.storyListLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                stories ->
                stories?.let {
                    Log.i(TAG,"Got stories ${stories.size}")
                    updateUI(stories)
                }
            }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onStart() {
        super.onStart()
        addStory.setOnClickListener {
            callbacks?.onStorySelected()
        }
    }

    private fun updateUI(stories: List<Story>){
        adapter = StoryAdapter(stories)
        storyRecyclerView.adapter = adapter
    }


    companion object{
        fun newInstance(): StoryListFragment{
            return StoryListFragment()
        }
    }

    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.add_new_story, menu)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }


    private inner class StoryHolder(view: View): RecyclerView.ViewHolder(view),View.OnClickListener{
        override fun onClick(p0: View?) {
           callbacks?.onStorySelected(story.id)
        }

        init {
            itemView.setOnClickListener(this)
        }

        private lateinit var story: Story

        val titleTextView: TextView = itemView.findViewById(R.id.story_title)
        val dateTextView:TextView = itemView.findViewById(R.id.story_date)



        fun bind(story: Story){
            this.story = story
            titleTextView.text = this.story.title
            dateTextView.text = this.story.date.toString()
        }
    }
    private inner class StoryAdapter(var stories: List<Story>)
        : RecyclerView.Adapter<StoryHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
            val view = layoutInflater.inflate(R.layout.item_story, parent, false)
            return StoryHolder(view)
        }

        override fun getItemCount() = stories.size

        override fun onBindViewHolder(holder: StoryHolder, position: Int) {
            val story = stories[position]
            /*  holder.apply {
                  titleTextView.text = crime.title
                  dateTextView.text = crime.date.toString()*/
            holder.bind(story)
        }
    }



}
