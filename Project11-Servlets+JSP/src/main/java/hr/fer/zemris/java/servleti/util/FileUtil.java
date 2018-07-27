package hr.fer.zemris.java.servleti.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.servleti.GlasanjeRezultatiServlet.ResultData;

/**
 * Utility class for creating file and for reading
 * voting results.
 * 
 * @author Luka Grgić
 */
public class FileUtil {

	/**
	 * Creates file if it doesn't exist.
	 * 
	 * @param path file path
	 * @param req request
	 * @throws IOException exception
	 */
	public static synchronized void createIfDoesntExists(Path path, HttpServletRequest req) throws IOException {
		if (!Files.exists(path)) {
			String definition = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
			List<String> lines = Files.readAllLines(Paths.get(definition), StandardCharsets.UTF_8);
			String newFile = "";

			for (String line : lines) {
				String[] values = line.split("\t");

				newFile += values[0] + "\t0\n";
			}

			newFile = newFile.substring(0, newFile.length() - 1);

			Files.createFile(path);
			Files.write(path, newFile.getBytes(StandardCharsets.UTF_8));
		}
	}

	/**
	 * Reads file containing voting results.
	 * 
	 * @param req request
	 * @return voting results
	 * @throws IOException exception
	 */
	public static List<ResultData> getResults(HttpServletRequest req) throws IOException {
		String definitionFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		String resultsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path definitions = Paths.get(definitionFileName);
		Path results = Paths.get(resultsFileName);
		
		List<String> resultLines = null;
		if (Files.exists(results)) {
			resultLines = Files.readAllLines(results, StandardCharsets.UTF_8);
		} 
		
		List<String> definitionLines = Files.readAllLines(definitions, StandardCharsets.UTF_8);
		List<ResultData> resultData = new ArrayList<>();

		for (int i = 0; i < definitionLines.size(); i++) {
			String[] definitionLine = definitionLines.get(i).split("\t");
			String[] resultLine = resultLines != null ? resultLines.get(i).split("\t") : null;

			resultData.add(new ResultData(
									definitionLine[1], 
									resultLine != null ? resultLine[1] : "0", 
									definitionLine[2]
						  ));
		}

		resultData = resultData.stream()
								.sorted((s1, s2) -> {
									return s2.voteResult.compareTo(s1.voteResult);
								})
								.collect(Collectors.toList());
	
		return resultData;
	}

}
