package com.spikeify.ffmpeg.builder.elements;

import java.util.List;

public class VideoObject {

	private String path; //video path
	private double start = -1; //video start time
	private double end = -1; //video end time

	private FadeIn fadeIn;
	private FadeOut fadeOut;

	private List<Caption> captions;
	private Caption caption;

	private VideoObject(String path, double start, double end, FadeIn fadeIn, FadeOut fadeOut, List<Caption> captions, Caption caption) {
		this.path = path;
		this.start = start;
		this.end = end;
		this.fadeIn = fadeIn;
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

	public FadeIn getFadeIn() {
		return fadeIn;
	}

	public void setFadeIn(FadeIn fadeInBuilder) {
		this.fadeIn = fadeInBuilder;
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

	public static class VideoObjectBuilder {
		private String path;
		private double start = -1;
		private double end = -1;
		private FadeIn fadeIn;
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

		public VideoObjectBuilder setFadeIn(FadeIn fadeIn) {
			this.fadeIn = fadeIn;
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
			return new VideoObject(path, start, end, fadeIn, fadeOut, captions, caption);
		}
	}
}
