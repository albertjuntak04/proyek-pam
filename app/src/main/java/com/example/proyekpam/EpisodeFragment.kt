package com.example.proyekpam


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProviders
import com.example.proyekpam.episode.EpisodeDetailViewModel
import java.text.SimpleDateFormat
import java.util.*
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
private const val ARG_STORY_ID = "story_id"
private const val ARG_EPISODE_ID = "episode_id"
private const val REQUEST_PHOTO = 2
class EpisodeFragment : Fragment() {
    private lateinit var titleEpisode: EditText
    private lateinit var episode: Episode
    private lateinit var fieldEpisode: EditText
    private lateinit var episodeImage: ImageView
    private lateinit var btnImage: Button
    private lateinit var photoFile: File
    private lateinit var photoUri : Uri
    private lateinit var btnAdd: Button



    interface Callbacks{
        fun deleteEpisode(storyId: UUID)
        fun buttonBack(storyId: UUID)
    }

    private var callbacks: Callbacks? = null


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
        episodeImage = view.findViewById(R.id.image)
        btnImage = view.findViewById(R.id.btn_image)
        btnAdd = view.findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            saveEpisode()
            callbacks?.buttonBack(episode.idStory)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as EpisodeFragment.Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        episode = Episode()
        val episodeId: UUID = arguments?.getSerializable(ARG_EPISODE_ID)as UUID
        episodeDetailViewModel.loadEpisode(episodeId)
    }

    fun getActionBar(): ActionBar? {
        return (activity as MainActivity).getSupportActionBar()
    }

    private fun dialogConfirmation(){
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(R.string.app_name)
        builder.setMessage("Do you want to delete " + episode.titleEpisode + "?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
            episodeDetailViewModel.deleteEpisode(episode)
            callbacks?.deleteEpisode(episode.idStory)
            dialog.dismiss()

        })
        builder.setNegativeButton("No",
            DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
        val alert = builder.create()
        alert.show()

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
                photoFile = episodeDetailViewModel.getPhotoFile(episode)
                photoUri = FileProvider.getUriForFile(
                    requireActivity(),
                    "com.example.proyekpam.fileprovider",
                    photoFile
                )
                updateUI()
            }
            }
        )
    }

    private fun updateUI(){
        titleEpisode.setText(episode.titleEpisode)
        fieldEpisode.setText(episode.fieldStory)
        updatePhotoView()
        getActionBar()?.setTitle(episode.titleEpisode)
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.delete_episode,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            R.id.delete_episode -> {
                dialogConfirmation()
                true
            }
            else -> return  super.onOptionsItemSelected(item)
        }

    }

    override fun onStart() {
        super.onStart()
        btnImage.apply {
            val packageManager: PackageManager = requireActivity().packageManager

            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY)
            if (resolvedActivity == null){
                isEnabled = false
            }

            setOnClickListener {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY)

                for (cameraActivity in cameraActivities){
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                startActivityForResult(captureImage, REQUEST_PHOTO)

            }
        }
    }

    private fun updatePhotoView(){
        if (photoFile.exists()){
            val bitmap = getScaledBitmap(photoFile.path, requireActivity())
            episodeImage.setImageBitmap(bitmap)
        }else{
            episodeImage.setImageDrawable(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when{
            requestCode == REQUEST_PHOTO -> {
                requireActivity().revokeUriPermission(photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                updatePhotoView()
            }
        }
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
