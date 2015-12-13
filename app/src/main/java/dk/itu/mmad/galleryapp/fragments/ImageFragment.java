package dk.itu.mmad.galleryapp.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dk.itu.mmad.galleryapp.ImageActivity;
import dk.itu.mmad.galleryapp.R;

/**
 * Created by domi on 06-05-2015.
 */
public class ImageFragment extends Fragment {

	private String _imageTitle;
	private Bitmap _image;

	public static ImageFragment newInstance(String imageTitle, Bitmap image) {
		ImageFragment imageFragment = new ImageFragment();
		imageFragment._imageTitle = imageTitle;
		imageFragment._image = image;
		return imageFragment;
	}

	public ImageFragment() {
		// Necessary to handle orientation changes correctly
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_image,
				container, false);
		ImageView imageView = (ImageView) rootView.findViewById(R.id.image);
		imageView.setImageBitmap(_image);
		TextView textView = (TextView) rootView.findViewById(R.id.text);
		textView.setText(_imageTitle);

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ImageActivity)getActivity()).showThumbnails();
			}
		});

		return rootView;
	}
}
