package dk.itu.mmad.galleryapp.adapters;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dk.itu.mmad.galleryapp.fragments.ImageFragment;

/**
 * Created by domi on 06-05-2015.
 */
public class ImagePagerAdapter extends FragmentPagerAdapter
{

	private ArrayList<String> _keys;
	private Map<String, Bitmap> _images;

	public ImagePagerAdapter(FragmentManager fm, Map<String, Bitmap> currentImages) throws IOException
	{
		super(fm);
		_images = currentImages;
		_keys = new ArrayList<>(_images.keySet());
	}

	@Override
	public Fragment getItem(int position)
	{
		String key = _keys.get(position);
		return ImageFragment.newInstance(key, _images.get(key));
	}

	public void addImage(String key, Bitmap bitmap)
	{
		_keys.add(key);
		_images.put(key, bitmap);
		notifyDataSetChanged();
	}

	public void removeAll()
	{
		_images = new HashMap<>();
		_keys = new ArrayList<>();
		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		return _images.size();
	}

}
