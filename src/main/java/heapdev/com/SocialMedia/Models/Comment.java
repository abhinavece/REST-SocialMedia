package heapdev.com.SocialMedia.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment {

	private long id;
	private String comment;
	private Date created;
	private String author;
	private List<Link> links= new ArrayList<>();

	public Comment() {
	}

	public Comment(long id, String comment, String author) {
		super();
		this.id = id;
		this.comment = comment;
		this.author = author;
		this.created = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void addLink(String uri, String rel) {
		Link link = new Link();
		link.setUrl(uri);
		link.setRel(rel);
		links.add(link);
	}

}
