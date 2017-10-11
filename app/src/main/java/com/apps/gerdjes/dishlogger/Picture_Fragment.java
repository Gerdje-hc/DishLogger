package com.apps.gerdjes.dishlogger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Picture_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Picture_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Picture_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ImageView mImageView;

    public Picture_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Picture_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Picture_Fragment newInstance(String param1, String param2) {
        Picture_Fragment fragment = new Picture_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View view= inflater.inflate(R.layout.fragment_picture_, container, false);
        mImageView = (ImageView) view.findViewById(R.id.mImageView1);
        setPic(mParam1);

        ////////////////////////////  - database

        mListView = (ListView) view.findViewById(R.id.recordList);
        mNewDishNameInput = (EditText) view.findViewById(R.id.newRecordInput);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        populateList();

        ////////////////////////////

        ///////////////////////////
        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecord(view);
            }
        });


        //////////////////////////

        return view;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }

    public ImageView getmImageView() {
        return mImageView;
    }

    public void setPic(String mCurrentPhotoPath) {
        // Get the dimensions of the View


        int targetW = 600;
        int targetH = 600;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Log.d("gerdje", mCurrentPhotoPath);
        mImageView.setImageBitmap(bitmap);
    }

    ///////////////////////////////////
    private EditText mNewDishNameInput;
    private ListView mListView;
    private DatabaseHelper mDatabaseHelper;

    private void populateList ()
    {
        List<Dish> list = mDatabaseHelper.GetData();
        List<String> infoList = new ArrayList<>();

        for (Dish dish : list)
        {
            infoList.add(dish.getName() + " with id " + dish.getAccountId());

        }

        mListView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, infoList ));


    }
    public void addRecord (View v) {
        String strName = mNewDishNameInput.getText().toString().trim();
        if (TextUtils.isEmpty(strName)) {
            Toast.makeText(getActivity(), "Please enter a name for your new Dish", Toast.LENGTH_SHORT).show();
            return;

        }

        Dish dish = new Dish();
        dish.setName(strName);
        mDatabaseHelper.addDish(dish);
        Toast.makeText(getActivity(), "New name successfully added", Toast.LENGTH_SHORT).show();
        populateList();
    }

    @SuppressWarnings("deprectation")
    public void deleteAllRecords(View v)

    {
        List<Dish> list = mDatabaseHelper.GetData();
        if (null != list&& list.size() > 0)

        {
            mDatabaseHelper.deleteAllDishes();
            mNewDishNameInput.setText("");
            Toast.makeText(getActivity(), "Removed all data from the databasetable", Toast.LENGTH_SHORT).show();
            populateList();
        }
        else {
            Toast.makeText(getActivity(), "No data found in the database", Toast.LENGTH_SHORT).show();

        }
    }
    ////////////////////////////////////////

    }

