package com.example.proyekpam


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import com.example.proyekpam.story.StoryDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class TambahStoryFragment : Fragment() {
    private lateinit var story: Story
    private lateinit var titleStory: EditText
    private lateinit var createStory: Button

    private val storyDetailViewModel: StoryDetailViewModel by lazy {
        ViewModelProviders.of(this).get(StoryDetailViewModel::class.java)
    }

    interface Callbacks{
        fun onAddStory()
    }

    private var callbacks: Callbacks? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        story = Story()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view= inflater.inflate(R.layout.fragment_tambah_story, container, false)
        titleStory = view.findViewById(R.id.title_story)
        createStory = view.findViewById(R.id.create_story)
        return view
    }

    override fun onStart() {
        super.onStart()
        createStory.setOnClickListener {
//            Toast.makeText(context,"daa",Toast.LENGTH_SHORT).show()
            saveStory()
            callbacks?.onAddStory()
        }
    }



    companion object{
        fun newInstance(): TambahStoryFragment{
            return TambahStoryFragment()
        }
    }

    private fun saveStory(){
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        var title = titleStory.getText().toString()
        story.title = title
        story.date = currentDate
        storyDetailViewModel.saveStory(story)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as TambahStoryFragment.Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }


}
