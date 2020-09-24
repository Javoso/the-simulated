package br.com.simulado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Youtube {

	private static final String API_URL = "https://www.googleapis.com/youtube/v3/videos?";
	private static final String KEY = "AIzaSyBy1dZGlk9qXNjYYDErC0_6RNZuBrQxYzc";
	private static final String PART = "snippet,contentDetails,statistics,status";

	public static void main(String[] args) {
		try {
			Youtube youtube = new Youtube();
			RespostaYoutube respostaYoutube = youtube.obterDadosVideo("https://www.youtube.com/watch?v=cft1NAXaW4o");
			System.out.println(respostaYoutube.items.get(0).id);

			for (Items item : respostaYoutube.items) {

				System.out.println("\n ITEM \n");

				System.out.println(item.id);

				System.out.println("\n Item-Snippet \n");

				System.out.println(item.snippet.title);
				System.out.println(item.snippet.description);
				System.out.println(item.snippet.thumbnails.high);
				System.out.println(item.snippet.thumbnails.medium);

				System.out.println("\n Item-Statistics \n");
				System.out.println(item.statistics.likeCount);
				System.out.println(item.statistics.viewCount);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public RespostaYoutube obterDadosVideo(String URL) {

		Gson gson = new Gson();
		String retornoJson = null;
		try {
			// Faz o split para obter o ID do video
			String ID = URL.split("v=")[1];
			String link = API_URL + "id=" + ID + "&" + "key=" + KEY + "&" + "part=" + PART;
			System.out.println(link);
			retornoJson = lerUrl(link);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return gson.fromJson(retornoJson, RespostaYoutube.class);
	}

	private static String lerUrl(String urlString) throws Exception {
		BufferedReader leitor = null;
		try {
			URL url = new URL(urlString);
			leitor = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = leitor.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (leitor != null)
				leitor.close();
		}

	}

	public class RespostaYoutube {
		public List<Items> items = new ArrayList<Items>();
	}

	public class Items {

		public Snippet snippet;
		public Statistics statistics;
		public String id;
	}

	public class Snippet {
		public String title;
		public String description;
		public Thumbnails thumbnails;
		public List<String> tags = new ArrayList<String>();
	}

	public class Statistics {
		public long likeCount;
		public long viewCount;
	}

	public class Thumbnails {
		Medium medium;
		High high;
	}

	public class Medium {
		String url;
		long width;
		long height;
	}

	public class High {
		String url;
		long width;
		long height;
	}

}