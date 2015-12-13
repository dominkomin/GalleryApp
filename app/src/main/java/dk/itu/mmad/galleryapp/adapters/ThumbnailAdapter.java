package dk.itu.mmad.galleryapp.adapters;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dk.itu.mmad.galleryapp.fragments.ThumbnailsFragment;
import dk.itu.mmad.galleryapp.ImageActivity;
import dk.itu.mmad.galleryapp.R;

/**
 * Created by domi on 07-05-2015.
 */
public class ThumbnailAdapter extends BaseAdapter
{
	private ImageActivity imageActivity;
	private ThumbnailsFragment thumbnailsFragment;
	private List<String> _keys;
	private Map<String, Bitmap> images;

	public ThumbnailAdapter(Context activity, Fragment fragmentContext)
	{
		thumbnailsFragment = (ThumbnailsFragment)fragmentContext;
		imageActivity = (ImageActivity)activity;
		images = imageActivity.getLoadedImages();
		_keys = new ArrayList<>(images.keySet());
	}

	public int getCount()
	{
		return _keys.size();
	}

	public Object getItem(int position)
	{
		return null;
	}

	public long getItemId(int position)
	{
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent)
	{

		View thumbnailView;
		if (convertView == null)
		{
			LayoutInflater _inflater = (LayoutInflater) imageActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			thumbnailView = _inflater.inflate(R.layout.fragment_thumbnail, null);
			ImageView imageView = (ImageView) thumbnailView.findViewById(R.id.thumbnail);
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(images.get(_keys.get(position)), 150, 150, false);
			imageView.setImageBitmap(scaledBitmap);

			thumbnailView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					imageActivity.setCurrentPage(position);
					thumbnailsFragment.dismiss();
				}
			});
		} else
		{
			thumbnailView = convertView;
		}
		return thumbnailView;
	}
}
