package au.com.hipages.twitter.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * A status which contains pretty much exactly the same information as {@link twitter4j.Status} but needed in order to convert this object to JSON format.
 *
 */
public class TwitterStatus {
	/** Id is of type String because JavaScript has problems with long data types */
	private String id;
	
	private String text;
	private long date;
	private List<StatusImage> images = new LinkedList<StatusImage>();

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return String.valueOf(id);
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void addImage(StatusImage image) {
		images.add(image);
	}

	public List<StatusImage> getImages() {
		return images;
	}

	public void setImages(List<StatusImage> images) {
		this.images = images;
	}
}
