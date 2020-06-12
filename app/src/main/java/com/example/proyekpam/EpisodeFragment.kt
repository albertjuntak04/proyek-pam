package com.example.proyekpam


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.proyekpam.episode.EpisodeDetailViewModel
import com.example.proyekpam.story.StoryDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
private const val ARG_STORY_ID = "story_id"
private const val ARG_EPISODE_ID = "episode_id"
class EpisodeFragment : Fragment() {
    private lateinit var titleEpisode: EditText
    private lateinit var episode: Episode
    private lateinit var fieldEpisode: EditText


    private val episodeDetailViewModel: EpisodeDetailViewModel by lazy {
        ViewModelProviders.of(this).get(EpisodeDetailViewModel::class.java)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_episode, container, false)
        titleEpisode = view.findViewById(R.id.title_episode)
        fieldEpisode = view.findViewById(R.id.field_episode)
        return view
    }

    override fun onStop() {
        super.onStop()
        saveEpisode()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        episode = Episode()
        val episodeId: UUID = arguments?.getSerializable(ARG_EPISODE_ID)as UUID
        episodeDetailViewModel.loadEpisode(episodeId)
    }

    private fun saveEpisode(){
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        episode.titleEpisode = titleEpisode.getText().toString()
        episode.fieldStory = fieldEpisode.getText().toString()
        episode.date = currentDate
        episodeDetailViewModel.saveEpisode(episode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodeDetailViewModel.episodeLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                episode -> episode?.let {
                this.episode = episode
                updateUI()
            }
            }
        )
    }

    private fun updateUI(){
        titleEpisode.setText(episode.titleEpisode)
        fieldEpisode.setText(episode.fieldStory)
    }


    companion object{
        fun newInstance(episodeId: UUID): EpisodeFragment{
            val args = Bundle().apply {
                putSerializable(ARG_EPISODE_ID,episodeId)

            }
            return EpisodeFragment().apply {
                arguments = args
            }
        }
    }

}
