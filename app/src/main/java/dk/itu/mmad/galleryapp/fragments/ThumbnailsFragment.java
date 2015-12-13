package dk.itu.mmad.galleryapp.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;

import dk.itu.mmad.galleryapp.R;
import dk.itu.mmad.galleryapp.adapters.ThumbnailAdapter;

/**
 * Created by domi on 07-05-2015.
 */
public class ThumbnailsFragment extends DialogFragment
{
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_thumbnails, container, false);

		if (this.getDialog() != null)
			this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		ThumbnailAdapter adapter = new ThumbnailAdapter(this.getActivity(), this);
		if (rootView != null) {
			((GridView) rootView).setAdapter(adapter);
		}
		return rootView;
	}


}
