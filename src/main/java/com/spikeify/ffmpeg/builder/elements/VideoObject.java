package com.spikeify.ffmpeg.builder.elements;

import java.util.List;

public class VideoObject {

	private String path; //video path
	private double start = -1; //video start time
	private double end = -1; //video end time

	private FadeIn fadeInBuilder;
	private FadeOut fadeOut;

	private List<Caption> captions;
	private Caption caption;

	VideoObject(String path, double start, double end, FadeIn fadeInBuilder, FadeOut fadeOut, List<Caption> captions, Caption caption) {
		this.path = path;
		this.start = start;
		this.end = end;
		this.fadeInBuilder = fadeInBuilder;
		this.fadeOut = fadeOut;
		this.captions = captions;
		this.caption = caption;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getEnd() {
		return end;
	}

	public void setEnd(double end) {
		this.end = end;
	}

	public FadeIn getFadeInBuilder() {
		return fadeInBuilder;
	}

	public void setFadeInBuilder(FadeIn fadeInBuilder) {
		this.fadeInBuilder = fadeInBuilder;
	}

	public FadeOut getFadeOut() {
		return fadeOut;
	}

	public void setFadeOut(FadeOut fadeOut) {
		this.fadeOut = fadeOut;
	}

	public List<Caption> getCaptions() {
		return captions;
	}

	public void setCaptions(List<Caption> captions) {
		this.captions = captions;
	}

	public Caption getCaption() {
		return caption;
	}

	public void setCaption(Caption caption) {
		this.caption = caption;
	}
}
