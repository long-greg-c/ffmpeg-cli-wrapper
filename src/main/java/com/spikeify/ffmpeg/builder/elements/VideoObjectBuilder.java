package com.spikeify.ffmpeg.builder.elements;

import java.util.List;

public class VideoObjectBuilder {
	private String path;
	private double start = -1;
	private double end = -1;
	private FadeIn fadeInBuilder;
	private FadeOut fadeOut;
	private List<Caption> captions;
	private Caption caption;

	public VideoObjectBuilder(String path) {
		this.path = path;
	}

	public VideoObjectBuilder setStart(double start) {
		this.start = start;
		return this;
	}

	public VideoObjectBuilder setEnd(double end) {
		this.end = end;
		return this;
	}

	public VideoObjectBuilder setFadeInBuilder(FadeIn fadeInBuilder) {
		this.fadeInBuilder = fadeInBuilder;
		return this;
	}

	public VideoObjectBuilder setFadeOut(FadeOut fadeOut) {
		this.fadeOut = fadeOut;
		return this;
	}

	public VideoObjectBuilder setCaptions(List<Caption> captions) {
		this.captions = captions;
		return this;
	}

	public VideoObjectBuilder setCaption(Caption caption) {
		this.caption = caption;
		return this;
	}

	public VideoObject createVideoObject() {
		return new VideoObject(path, start, end, fadeInBuilder, fadeOut, captions, caption);
	}
}