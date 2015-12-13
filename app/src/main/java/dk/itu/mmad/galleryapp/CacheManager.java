package dk.itu.mmad.galleryapp;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.Map;

/**
 * Created by domi on 07-05-2015.
 */
public class CacheManager
{
	private static CacheManager _instance;
	private LruCache<String, Bitmap> images;

	private CacheManager()
	{
		images = new LruCache<>(10);
	}

	public static CacheManager getInstance()
	{
		if (_instance == null)
			_instance = new CacheManager();
		return _instance;
	}

	public boolean addToCache(String key, Bitmap bitmap)
	{
		synchronized (images)
		{
			if (images.get(key) == null)
			{
				images.put(key, bitmap);
				return true;
			}
		}
		return false;
	}

	public boolean isInCache(String key)
	{
		return images.get(key) != null;
	}

	public Map<String, Bitmap> snapshot()
	{
		return images.snapshot();
	}

	public void clear()
	{
		images.evictAll();
	}
}
