package dk.itu.mmad.galleryapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

import dk.itu.mmad.galleryapp.fragments.ThumbnailsFragment;
import dk.itu.mmad.galleryapp.loaders.ImageServiceTask;
import dk.itu.mmad.galleryapp.adapters.ImagePagerAdapter;


public class ImageActivity extends ActionBarActivity
{
	private ImagePagerAdapter _imagePagerAdapter;
	private CacheManager _cacheManager;
	private static ThumbnailsFragment _thumbnailsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pager);

		_cacheManager = CacheManager.getInstance();

		initializePager();
		loadImages();
	}

	public Map<String, Bitmap> getLoadedImages()
	{
		return _cacheManager.snapshot();
	}

	private void loadImages()
	{
		ImageServiceTask imageFetcher = new ImageServiceTask(this);
		imageFetcher.execute();
	}

	private void initializePager()
	{
		if (_imagePagerAdapter != null)
			return;

		try
		{
			_imagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), _cacheManager.snapshot());
		} catch (IOException e)
		{
			Toast.makeText(this, e.getMessage(),
					Toast.LENGTH_SHORT).show();
			finish();
		}
		ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(_imagePagerAdapter);
	}

	public void addNewImage(String key, Bitmap bitmap)
	{
		if (_cacheManager.addToCache(key, bitmap))
			_imagePagerAdapter.addImage(key, bitmap);
	}

	public boolean isImageAlreadyLoaded(String key)
	{
		return _cacheManager.isInCache(key);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_image, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.refresh)
		{
			_cacheManager.clear();
			_imagePagerAdapter.removeAll();
			loadImages();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void showThumbnails()
	{
		// No images are loaded- do not show thumbnails.
		if (getLoadedImages().size() <= 0)
			return;

		_thumbnailsFragment = new ThumbnailsFragment();
		_thumbnailsFragment.setShowsDialog(true);
		_thumbnailsFragment.show(getFragmentManager(), "");
	}

	public void setCurrentPage(int position)
	{
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setCurrentItem(position, true);
	}

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
	{
		super.onSaveInstanceState(outState, outPersistentState);
	}
}
