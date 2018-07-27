package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Path;

/**
 * Class that stores path to article and its
 * similarity coefficient.
 * 
 * @author Luka Grgić
 */
public class Article implements Comparable<Article>{
	/** path */
	private Path path;
	/** similarity coefficient */
	private Double similarityKoef;
	
	/**
	 * Constructor.
	 * 
	 * @param path path
	 * @param similarityKoef similarity coefficient
	 */
	public Article(Path path, Double similarityKoef) {
		this.path = path;
		this.similarityKoef = similarityKoef;
	}

	/**
	 * Gets path.
	 * 
	 * @return path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Gets similarity coefficient
	 * 
	 * @return similarity coefficient
	 */
	public Double getSimilarityKoef() {
		return similarityKoef;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((similarityKoef == null) ? 0 : similarityKoef.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (similarityKoef == null) {
			if (other.similarityKoef != null)
				return false;
		} else if (!similarityKoef.equals(other.similarityKoef))
			return false;
		return true;
	}

	@Override
	public int compareTo(Article article) {
		return Double.compare(article.getSimilarityKoef(), this.getSimilarityKoef());
	}
	
}
