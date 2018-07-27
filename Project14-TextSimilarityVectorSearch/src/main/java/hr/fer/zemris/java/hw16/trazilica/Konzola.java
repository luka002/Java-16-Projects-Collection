package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Program that finds files with content most similar to user 
 * query. User is supposed to enter words and based on them program
 * will find which files have most of those words based on TF-IDF algorithm.  
 * 
 * @author Luka Grgić
 */
public class Konzola {

	/**
	 * Method that first executes when program starts.
	 * Example queries:
	 * Enter command > query darovit glumac zadnje akademske klase
	 * Query is: [darovit, glumac, zadnje, akademske, klase]
	 * Najboljih 10 rezultata:
	 * [ 0] (0.1222) d:\clanci\vjesnik-1999-12-7-kul-5
	 * [ 1] (0.0151) d:\clanci\vjesnik-1999-12-3-kul-3
	 * [ 2] (0.0104) d:\clanci\vjesnik-1999-9-5-kul-2
	 * Enter command > type 0
	 * File with type 0 will be displayed.
	 * Enter command > results
	 * [ 0] (0.1222) d:\clanci\vjesnik-1999-12-7-kul-5
	 * [ 1] (0.0151) d:\clanci\vjesnik-1999-12-3-kul-3
	 * [ 2] (0.0104) d:\clanci\vjesnik-1999-9-5-kul-2
	 * Enter command > exit
	 * 
	 * @param args command line arguments. Path to all files
	 * is passed as command line argument.
	 */
	public static void main(String[] args) {
		Path path = Paths.get("clanci");
		Set<String> stopWords = getStopWords();
		Set<String> vocabulary = getVocabulary(path, stopWords);
		Map<String, Double> idfVector = new HashMap<>();
		Map<Path, Map<String, Integer>> tfVectors = getVectors(path, vocabulary, idfVector);
		
		for (String word : vocabulary) {
			idfVector.compute(word, (k, v) -> Math.log(tfVectors.size()/v));
		}
		
		System.out.println("Veličina riječnika je " + vocabulary.size() + " riječi.\n");
		Scanner scanner = new Scanner(System.in);
		List<Article> mostSimilarArticles = new ArrayList<>();
		int size = 0;
		
		while (true) {
			System.out.print("Enter command > ");
			String query = scanner.nextLine().trim();
			
			if (query.startsWith("query ")) {
				String[] words = query.substring(6).split("\\s+");
				List<String> queryWords = new ArrayList<>();
				
				for (String word : words) {
					if (vocabulary.contains(word)) {
						queryWords.add(word);
					}
				}
				
				System.out.println("Query is: " + queryWords.toString());
				mostSimilarArticles = createMostSimilarArticles(
						vocabulary, 
						idfVector, 
						tfVectors, 
						queryWords
				);
				
				size = mostSimilarArticles.size() <= 10 ? mostSimilarArticles.size() : 10;
				System.out.println("Najboljih " + size + " rezultata:");
				showResults(mostSimilarArticles, size);
				
			} else if (query.startsWith("type ")) {
				Integer article = null;
				
				try {
					article = Integer.parseInt(query.substring(5).trim());
				} catch (NumberFormatException e) {
					System.out.println("Type has to be number.");
					continue;
				}
				
				if (article < 0 || article >= size) {
					System.out.println("Wrong number.");
					continue;
				}
				
				displayArticle(mostSimilarArticles.get(article));
					
			} else if (query.equals("results")) {
				showResults(mostSimilarArticles, size);
				
			} else if (query.equals("exit")) {
				System.out.println("Adios!");
				break;
			
			} else {
				System.out.println("Nepoznata naredba.\n");
			}
		}
		
		scanner.close();
	}

	/**
	 * Gets stop words from a file.
	 * 
	 * @return stop words
	 */
	private static Set<String> getStopWords() {
		List<String> words = null;
		Path path = Paths.get("stoprijeci/hrvatski_stoprijeci.txt");
		
		try {
			words = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Could not get stop words.");
			System.exit(-1);
		}
		
		return new HashSet<String>(words);
	}
	
	/**
	 * Creates vocabulary from all files and returns is as
	 * set of strings.
	 * 
	 * @param path path to all files
	 * @param stopWords stop words
	 * @return all words from all files without stop words
	 */
	private static Set<String> getVocabulary(Path path, Set<String> stopWords) {
		ExtractVocabulary extract = new ExtractVocabulary(stopWords);
		
		try {
			Files.walkFileTree(path, extract);
		} catch (IOException e) {
			System.err.println("Could not get vocabulary.");
			System.exit(-1);
		}
		
		return extract.getVocabulary();
	}
	
	/**
	 * Creates vectors for every file and puts it in a map.
	 * Map key is path to file and value is another map that has
	 * word for key and times of that word occurrence in file as a 
	 * value.
	 * 
	 * @param path path to all files
	 * @param vocabulary vocabulary
	 * @param idfVector idfVector
	 * @return map which key is path to file and value is another map that has
	 * word for key and times of that word occurrence in file as a value
	 */
	private static Map<Path, Map<String, Integer>> getVectors(
					Path path, Set<String> vocabulary, Map<String, Double> idfVector) {
		CreateVectors createVectors = new CreateVectors(vocabulary, idfVector);
		
		try {
			Files.walkFileTree(path, createVectors);
		} catch (IOException e) {
			System.err.println("Could not get vectors.");
			System.exit(-1);
		}
		
		return createVectors.getTfVectors();
	}
	
	/**
	 * Finds most similar articles based on queryWords.
	 * 
	 * @param vocabulary vocabulary
	 * @param idfVector idfVector
	 * @param tfVectors tfVectors
	 * @param queryWords all words from user input
	 * @return list of articles that are most similar to user input
	 */
	private static List<Article> createMostSimilarArticles(Set<String> vocabulary, Map<String, Double> idfVector,
			Map<Path, Map<String, Integer>> tfVectors, List<String> queryWords) {
		
		Map<String, Double> queryVector = new HashMap<>();
		for (String word : queryWords) {
			queryVector.put(word, idfVector.get(word));
		}
		
		Map<Path, Map<String, Double>> documentsVectors = new HashMap<>();
		tfVectors.forEach((k, v) -> {
			Map<String, Double> vector = new HashMap<>();
			
			for (String word : vocabulary) {
				if (v.get(word) == null) {
					vector.put(word, 0.0);
				} else {
					vector.put(word, v.get(word)*idfVector.get(word));	
				}
			}
			
			documentsVectors.put(k, vector);
		});
		
		List<Article> mostSimilarArticles = new ArrayList<>();
		documentsVectors.forEach((k, v) -> {
			double similarityKoef = getSimilarityKoef(queryVector, v, vocabulary);
			
			if (similarityKoef > 0) {
				mostSimilarArticles.add(new Article(k, similarityKoef));	
			}
		});
		
		Collections.sort(mostSimilarArticles);
		return mostSimilarArticles;
	}

	/**
	 * Calculates similarity coefficient calculated as
	 * scalar product divided by multiplication of norms
	 * between queryVector and file vector. 
	 * 
	 * @param queryVector vector made from query
	 * @param vector file vector
	 * @param vocabulary vocabulary
	 * @return calculate coefficient
	 */
	private static double getSimilarityKoef(Map<String, Double> queryVector, 
							Map<String, Double> vector, Set<String> vocabulary) {
		double scalarProduct = 0;
		double norm1 = 0;
		double norm2 = 0;
		
		for (String word : vocabulary) {
			double v1 = queryVector.get(word) != null ? queryVector.get(word) : 0;
			double v2 = vector.get(word) != null ? vector.get(word) : 0;
			scalarProduct += v1*v2;
			norm1 += v1*v1;
			norm2 += v2*v2;
		}
		
		if (scalarProduct == 0 || norm1 == 0 || norm2 == 0) return 0;
		return scalarProduct/(Math.sqrt(norm1)*Math.sqrt(norm2));
	}
	
	/**
	 * Prints out result from user query.
	 * 
	 * @param mostSimilarArticles list of most similar articles
	 * @param size number of found articles
	 */
	private static void showResults(List<Article> mostSimilarArticles, int size) {
		for (int i = 0; i < size; i++) {
			System.out.println("[" + i + "] (" + 
					String.format("%.4f", mostSimilarArticles.get(i).getSimilarityKoef()).toString() + 
					") " + mostSimilarArticles.get(i).getPath().toAbsolutePath().toString());
		}
		
		System.out.println();
	}
	
	/**
	 * Prints out chosen article.
	 * 
	 * @param article article to be printed out
	 */
	private static void displayArticle(Article article) {
		System.out.println("--------------------------------------------");
		System.out.println("Document: " + article.getPath().toAbsolutePath().toString());
		System.out.println("--------------------------------------------");
		
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(article.getPath(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Could not read file.");
		}

		for (String line : lines) {
			System.out.println(line);
		}
		
		System.out.println("--------------------------------------------");
	}
	
	/**
	 * FileVisitor class that goes through every file
	 * and extracts all words from those files except
	 * stop words.
	 * 
	 * @author Luka Grgić
	 */
	private static class ExtractVocabulary extends SimpleFileVisitor<Path> {
		/** stop words */
		private Set<String> stopWords;
		/** vocabulary */
		private Set<String> vocabulary;
		/** Pattern upon which words will be searched */
		private Pattern pattern;

		/**
		 * Constructor.
		 * 
		 * @param stopWords stop words
		 */
		public ExtractVocabulary(Set<String> stopWords) {
			this.stopWords = stopWords;
			this.vocabulary = new HashSet<String>();
			this.pattern = Pattern.compile("\\p{IsAlphabetic}+");
		}

		/**
		 * Gets vocabulary
		 * 
		 * @return vocabulary
		 */
		public Set<String> getVocabulary() {
			return vocabulary;
		}

		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attribs) throws IOException {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			
			for (String line : lines) {
				Matcher matcher = pattern.matcher(line);
				
				while (matcher.find()) {
					String word = matcher.group().toLowerCase();
					
					if (!stopWords.contains(word)) {
						vocabulary.add(word);
					}
				}
			}
			
			return FileVisitResult.CONTINUE;
		}

	}
	
	/**
	 * FileVisitor class that goes through every file
	 * and creates tfVectors for every file and also creates
	 * idfVector.
	 * 
	 * @author Luka Grgić
	 */
	private static class CreateVectors extends SimpleFileVisitor<Path> {
		/** tfVectors */
		private Map<Path, Map<String, Integer>> tfVectors;
		/** vocabulary */
		private Set<String> vocabulary;
		/** idfVector */
		private Map<String, Double> idfVector;
		/** pattern upon words will be searched */
		private Pattern pattern;
		
		/**
		 * Constructor.
		 * 
		 * @param vocabulary vocabulary
		 * @param idfVector idfVector
		 */
		public CreateVectors(Set<String> vocabulary, Map<String, Double> idfVector) {
			this.vocabulary = vocabulary;
			this.idfVector = idfVector;
			this.tfVectors = new HashMap<>();
			this.pattern = Pattern.compile("\\p{IsAlphabetic}+");
		}

		/**
		 * Gets tfVectors.
		 * 
		 * @return tfVectors.
		 */
		public Map<Path, Map<String, Integer>> getTfVectors() {
			return tfVectors;
		}

		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attribs) throws IOException {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			Map<String, Integer> tfVector = new HashMap<>();
			
			for (String line : lines) {
				Matcher matcher = pattern.matcher(line);
				
				while (matcher.find()) {
					String word = matcher.group().toLowerCase();
					
					if (vocabulary.contains(word)) {
						tfVector.compute(word, (k, v) -> v==null ? 1 : v+1);
					}
				}
			}
			
			tfVector.forEach((k1, v1) -> {
				idfVector.compute(k1, (k2, v2) -> v2==null ? 1 : v2+1);
			});
			tfVectors.put(path, tfVector);
			return FileVisitResult.CONTINUE;
		}

	}
	
}
