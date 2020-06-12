package com.example.proyekpam


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekpam.episode.EpisodeListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.item_episode.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
private const val ARG_CRIME_ID = "story_id"
private const val TAG = "StoryEpisodeFragment"
class StoryEpisodeFragment : Fragment() {
    private lateinit var addEpisode: FloatingActionButton
    private lateinit var episodeRecyclerView: RecyclerView

    interface Callbacks{
        fun addEpisode(storyId: UUID)
    }
//
    private var callbacks:Callbacks? = null
    private var adapter: StoryEpisodeFragment.EpisodeAdapter? = EpisodeAdapter(emptyList())
    private val episodeListViewModel : EpisodeListViewModel by lazy {
        ViewModelProviders.of(this).get(EpisodeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val crimeId: String = arguments?.getSerializable(ARG_CRIME_ID)as String
//        episodeListViewModel.loadEpisode(crimeId)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_story_episode, container, false)

        addEpisode = view.findViewById(R.id.addEpisode);
        episodeRecyclerView = view.findViewById(R.id.list_episode) as RecyclerView
        episodeRecyclerView.layoutManager = LinearLayoutManager(context)
        episodeRecyclerView.adapter = adapter


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        episodeListViewModel.episodeLiveData.observe(
//            viewLifecycleOwner,
//            androidx.lifecycle.Observer {
//                    episodes ->
//                episodes?.let {
//                    Log.i(TAG,"Got stories ${episodes.size}")
//                    updateUI(episodes)
//                }
//            }
//        )
    }

    private fun updateUI(episode: List<Episode>){
        adapter = EpisodeAdapter(episode)
        episodeRecyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID)as UUID
        addEpisode.setOnClickListener {
            callbacks?.addEpisode(crimeId)
        }
        Toast.makeText(context,crimeId.toString(),Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object{
        fun newInstance(storyId: UUID): StoryEpisodeFragment{
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, storyId)
            }
            return StoryEpisodeFragment().apply {
                arguments = args
            }
        }
    }



    private inner class EpisodeHolder(view: View): RecyclerView.ViewHolder(view),View.OnClickListener{
        override fun onClick(p0: View?) {
//            callbacks?.onStorySelected(story.id)
        }

        init {
            itemView.setOnClickListener(this)
        }

        private lateinit var episode: Episode

//        val titleTextView: TextView = itemView.findViewById(R.id.story_title)
//        val dateTextView: TextView = itemView.findViewById(R.id.story_date)



        fun bind(episode: Episode){
            this.episode = episode
//            titleTextView.text = this.story.title
//            dateTextView.text = this.story.date.toString()
        }
    }
    private inner class EpisodeAdapter(var episode: List<Episode>)
        : RecyclerView.Adapter<EpisodeHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
            val view = layoutInflater.inflate(R.layout.item_story, parent, false)
            return EpisodeHolder(view)
        }

        override fun getItemCount() = episode.size

        override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
            val episode = episode[position]
            /*  holder.apply {
                  titleTextView.text = crime.title
                  dateTextView.text = crime.date.toString()*/
            holder.bind(episode)
        }
    }




}
