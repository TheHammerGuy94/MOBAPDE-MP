package edu.mobapde.selina.shuffle;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlbumFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 
 */
public class AlbumFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARTIST = "artist";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String artist;
    private RecyclerView albumView;
    private AlbumAdapter aa;
    private Button backBtn;
    private boolean hideBack;

    private OnFragmentInteractionListener mListener;

    public AlbumFragment() {
        // Required empty public constructor
        hideBack = false;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param artist Parameter 1.
     * @return A new instance of fragment AlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance(String artist) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARTIST, artist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            artist = getArguments().getString(ARTIST);
        }
        MusicProvider mp = new MusicProvider(getActivity().getContentResolver());
        ArrayList<Album> albums = new ArrayList<>();
        if (artist != null){
            /*List<String> albumNames;
            albumNames = mp.getAlbumsOf(artist);
            for (String album: albumNames){
                albums.add(new Album(album,artist));
            }*/
            albums = mp.getAlbumsOf(artist);
        } else {
            albums = mp.getAllAlbums();
        }
        Resources res = getResources();
        aa = new AlbumAdapter(res,albums);
        aa.setListener(new AlbumAdapter.OnClickListener() {
            @Override
            public void onAlbumClick(String album) {
                onButtonPressed(album);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_album, container, false);
        backBtn = (Button) v.findViewById(R.id.backButton);
        if( hideBack ) {
            backBtn.setVisibility(View.GONE);
        } else {
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.up();
                    }
                }
            });
        }
        albumView = (RecyclerView) v.findViewById(R.id.albumView);
        albumView.setAdapter(aa);
        albumView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String albumName) {
        if (mListener != null) {
            mListener.onFragmentInteraction(BuildPlaylistActivity.ALBUM, albumName);
        }
    }

    public void hideBack() {
        hideBack = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int fragmentType, String value);
        void up();
    }

}
