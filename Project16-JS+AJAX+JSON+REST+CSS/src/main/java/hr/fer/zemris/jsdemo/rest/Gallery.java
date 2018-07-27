package hr.fer.zemris.jsdemo.rest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Singleton class that stores data from all pictures.
 * Data if fetched from file opisnik.txt and is stored
 * as list of GallerySegment objects.
 * 
 * @author Luka Grgić
 */
public class Gallery {

	/**
	 * List of galley segments
	 */
	private List<GallerySegment> gallerySegments;
	/**
	 * All of the tags
	 */
	private Set<String> tags;
	/**
	 * Path to file with pictures descriptors
	 */
	private String decsriptorPath;
	/**
	 * Tells if Gallery has been initialized
	 */
	private boolean isInitialized = false;

	/**
	 * Instance of the Gallery.
	 */
	private static Gallery gallery = new Gallery();

	/**
	 * Constructor.
	 */
	private Gallery() {
	}

	/**
	 * Gets instance of Gallery
	 * 
	 * @return instance of Gallery
	 */
	public static Gallery getInstance() {
		return gallery;
	}

	/**
	 * Initializes data. Creates all segments
	 * and puts them in gallerySegments list and
	 * fills tags with all tags.
	 */
	private void initGallery() {
		gallerySegments = new ArrayList<>();
		tags = new HashSet<>();
		
		List<String> file = null;

		try {
			file = Files.readAllLines(Paths.get(decsriptorPath), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < file.size(); i+=3) {
			Stack<String> data = new Stack<>();

			for (int j = i; j < i + 3; j++) {
				data.push(file.get(j));
			}

			Set<String> segmentTags = Set.of(data.get(2).split(", "));
			tags.addAll(segmentTags);
			gallerySegments.add(new GallerySegment(data.get(0), data.get(1), segmentTags));
		}
		
		isInitialized = true;
	}
	
	/**
	 * Gets segments with tag <code>tag</code>
	 * 
	 * @param tag tag that is searched for
	 * @return list of segments with tag <code>tag</code>
	 */
	public List<GallerySegment> getSegmentsWithTag(String tag) {
		if (!isInitialized) initGallery();
		
		List<GallerySegment> segments = new ArrayList<>();
		
		for (GallerySegment segment : gallerySegments) {
			if (segment.getTags().contains(tag)) {
				segments.add(segment);
			}
		}
		
		return segments;
	}
	
	/**
	 * Gets segment with name <code>name</code>
	 * 
	 * @param name name that is searched for
	 * @return segment with name <code>name</code>
	 */
	public GallerySegment getSegment(String name) {
		if (!isInitialized) initGallery();
		
		for (GallerySegment segment : gallerySegments) {
			if (segment.getName().equals(name)) {
				return segment;
			}
		}
		
		return null;
	}

	/**
	 * Gets all segments.
	 * 
	 * @return all segments
	 */
	public List<GallerySegment> getGallerySegments() {
		if (!isInitialized) initGallery();
		
		return gallerySegments;
	}

	/**
	 * Gets all tags.
	 * 
	 * @return all tags
	 */
	public Set<String> getTags() {
		if (!isInitialized) initGallery();
		
		return tags;
	}

	/**
	 * Sets path to descriptor file.
	 * 
	 * @param decsriptorPath descriptor path
	 */
	public void setDecsriptorPath(String decsriptorPath) {
		this.decsriptorPath = decsriptorPath;
	}

	
}
