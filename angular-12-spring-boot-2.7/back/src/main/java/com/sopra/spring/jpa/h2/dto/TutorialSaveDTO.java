package com.sopra.spring.jpa.h2.dto;

public class TutorialSaveDTO {

	private long id;

	private String title;

	private String description;

	private boolean published;

	public TutorialSaveDTO() {

	}

	public TutorialSaveDTO(String title, String description, boolean published) {
		this.title = title;
		this.description = description;
		this.published = published;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}

	@Override
	public String toString() {
		return "TutorialDTO [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
	}

}
