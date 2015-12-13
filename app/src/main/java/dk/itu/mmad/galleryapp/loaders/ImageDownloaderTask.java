package dk.itu.mmad.galleryapp.loaders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import dk.itu.mmad.galleryapp.ImageActivity;

/**
 * Created by domi on 02-05-2015.
 */
public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap>
{
	Throwable _error;
	final ImageActivity _activity;
	String _imageName;

	public ImageDownloaderTask(ImageActivity imageActivity)
	{
		this._activity = imageActivity;
	}

	@Override
	protected Bitmap doInBackground(String... links)
	{
		InputStream input = null;
		try
		{
			URL url = new URL(links[0]);
			_imageName = links[1];

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			input = connection.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(input);

			return bitmap;

		} catch (IOException e)
		{
			_error = e;
			return null;
		} finally
		{
			try
			{
				if (input != null)
				{
					input.close();
				}
			} catch (Exception e)
			{
			}
		}
	}

	@Override
	protected void onPostExecute(Bitmap bitmap)
	{
		if (bitmap != null)
		{
			_activity.addNewImage(_imageName, bitmap);
		} else if (_error != null)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(_activity);
			builder.setTitle("Error");
			builder.setMessage(_error.getMessage());
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			});
			builder.show();
		}
	}
}

