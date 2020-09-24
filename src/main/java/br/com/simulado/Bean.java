package br.com.simulado;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class Bean implements Serializable {
	
	@Getter
	@Setter
	private String enunciado;

	private static final long serialVersionUID = -9016054290587687586L;
	
	private String link = "https://www.youtube.com/embed/eUWqOAyLSLs";

	private String linkNovo = "https://www.youtube.com/embed/ihwBgO2Rw8A";
	
	public String getLinkNovo() {
		return linkNovo;
	}
	public void setLinkNovo(String linkNovo) {
		this.linkNovo = linkNovo;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public void listarDados() {
		if (isNotBlank(link)) {
			teste(link);
		}
//			return getYouTubeId(conteudo.getLink());
//		} else
//			return "";
	}

	private String getYouTubeId(String youTubeUrl) {
		String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(youTubeUrl);
		if (matcher.find()) {
			return matcher.group();
		} else {
			return null;
		}
	}


	private static final String API_URL = "https://www.googleapis.com/youtube/v3/videos?";
	private static final String KEY = "AIzaSyBy1dZGlk9qXNjYYDErC0_6RNZuBrQxYzc";
	private static final String PART = "snippet,contentDetails,statistics,status";

	public void teste(String linkVideo) {
		try {
			Bean youtube = new Bean();
			RespostaYoutube respostaYoutube = youtube.obterDadosVideo(linkVideo);

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
			System.out.println("\n LINK \n");
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
		public Medium medium;
		public High high;
	}

	public class Medium {
		public String url;
		public long width;
		public long height;
	}

	public class High {
		public String url;
		public long width;
		public long height;
	}


}
