import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * A class for downloading movie data from the internet.
 * Code adapted from Google.
 *
 * YOUR TASK: Add comments explaining how this code works!
 *
 * @author Joel Ross & Kyungmin Lee
 */
public class MovieDownloader {

	public static String[] downloadMovieData(String movie) {

		//construct the url for the omdbapi API
		String urlString = "";
		try {
			urlString = "http://www.omdbapi.com/?s=" + URLEncoder.encode(movie, "UTF-8") + "&type=movie";
		}catch(UnsupportedEncodingException uee){
			return null;
		}

		// Initialize HttpURLConnection, BufferedReader
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;

		// Initialize movies array
		String[] movies = null;

		try {
			// Construct a URL object
			URL url = new URL(urlString);

			// Build connection using url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Specify request method as GET so the movie server knows this request is only trying to retrieve data
			urlConnection.setRequestMethod("GET");

			// Start connection
			urlConnection.connect();

			// Construct InputStream by getting streams from our connection
			InputStream inputStream = urlConnection.getInputStream();

			// Create a buffer with type of string
			StringBuffer buffer = new StringBuffer();

			// If movie server responds us no inputStream, then we just want to return null
			if (inputStream == null) {
				return null;
			}

			// Create BufferedReader with the inputStream we defined above and used to read those streams
			// BufferedReader is used to read streams we receive from movie server
			reader = new BufferedReader(new InputStreamReader(inputStream));

			// Read first line of data stored in our BufferedReader
			String line = reader.readLine();

			// Keep reading lines from BufferedReader and store retrieved data to our StringBuffer until we get null
			// null means we have finished reading all data from streams
			while (line != null) {
				buffer.append(line + "\n");
				line = reader.readLine();
			}

			// If we don't get any data from BufferedReader and our StringBuffer is empty, return null
			if (buffer.length() == 0) {
				return null;
			}

			// Turn result into JSON format
			String results = buffer.toString();
			results = results.replace("{\"Search\":[","");
			results = results.replace("]}","");
			results = results.replace("},", "},\n");

			movies = results.split("\n");
		}
		catch (IOException e) {
			return null;
		}
		finally {
			// After we have successfully execeuted a search and got data, we want to disconnect our connection
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			if (reader != null) {
				try {
					reader.close();
				}
				catch (IOException e) {
				}
			}
		}

		return movies;
	}


	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		boolean searching = true;

		while(searching) {
			System.out.print("Enter a movie name to search for or type 'q' to quit: ");

			// Get user input
			String searchTerm = sc.nextLine().trim();

			// Continue searching unless user input is q
			if(searchTerm.toLowerCase().equals("q")){
				searching = false;
			}
			else {
				String[] movies = downloadMovieData(searchTerm);
				for(String movie : movies) {
					System.out.println(movie);
				}
			}
		}
		sc.close();
	}
}
