package dk.itu.mmad.galleryapp.loaders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import dk.itu.mmad.galleryapp.ImageActivity;

/**
 * Created by domi on 02-05-2015.
 */
public class ImageServiceTask extends AsyncTask<Void, Void, List<String>>
{
	static final String URL = " http://www.itu.dk/people/jacok/MMAD/services/images/";
	private Throwable _error;
	ImageActivity _activity;

	public ImageServiceTask(ImageActivity activity)
	{
		_activity = activity;
	}

	protected List<String> doInBackground(Void... params)
	{
		InputStream inputStream = null;
		JsonReader jsonReader = null;

		try
		{
			URL ImageUrl = new URL(URL);
			URLConnection connection = ImageUrl.openConnection();

			inputStream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			List<String> result = new ArrayList<String>();

			jsonReader = new JsonReader(reader);
			jsonReader.beginArray();
			while (jsonReader.peek() != JsonToken.END_ARRAY)
			{
				result.add(jsonReader.nextString());
			}
			jsonReader.endArray();

			return result;

		} catch (MalformedURLException e)
		{
			_error = e;
		} catch (IOException e)
		{
			_error = e;
		} catch (Exception e)
		{
			_error = e;
		} finally
		{
			try
			{
				if (inputStream != null)
				{
					inputStream.close();
				}
				if (jsonReader != null)
				{
					jsonReader.close();
				}
			} catch (Exception e)
			{
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<String> urls)
	{
		if (urls != null)
		{
			// Download all images asynchronously.
			for (String url : urls)
			{
				String[] UrlParts = url.split("/");
				String imageName = UrlParts[UrlParts.length - 1];

				if (_activity.isImageAlreadyLoaded(imageName))
					continue;

				ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask(_activity);
				String finalUrl = URL + url;
				imageDownloaderTask.execute(new String[]{finalUrl, imageName});
			}
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
