package com.vladik_bakalo.sharephoto;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vladik_bakalo.sharephoto.dbwork.DBWork;
import com.vladik_bakalo.sharephoto.dummy.PhotoContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PhotoHistoryFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private PhotoContent photoContent;
    private DBWork workDB;
    private Context applicationContext;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PhotoHistoryFragment() {
        photoContent = new PhotoContent();
    }

    @SuppressWarnings("unused")
    public static PhotoHistoryFragment newInstance(int columnCount) {
        PhotoHistoryFragment fragment = new PhotoHistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workDB = new DBWork(getContext());
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        photoContent.setPhotoItemsFromCursor(workDB.getCursorHistory());
        workDB.closeAllConnections();
        workDB = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
            view = inflater.inflate(R.layout.fragment_photohistory_list, container, false);

            // Set the adapter
            if (view instanceof RecyclerView) {
                Context context = view.getContext();
                RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new MyPhotoHistoryRecyclerViewAdapter(photoContent.ITEMS, applicationContext));
            }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            applicationContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
