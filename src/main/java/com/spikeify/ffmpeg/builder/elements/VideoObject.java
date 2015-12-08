package com.spikeify.ffmpeg.builder.elements;

public class VideoObject {

	private String path;
	private double duration;
	private double startTime;
	private FadeIn fadeIn;
	private FadeOut fadeOut;
	private Caption caption;

	//constructor
	public VideoObject(String videoPath){
		this.path = videoPath;
	}

	//optional arguments
	public VideoObject setFadeIn(FadeIn fadeIn) {
		this.fadeIn = fadeIn;
		return this;
	}

	public VideoObject setFadeOut(FadeOut fadeOut) {
		this.fadeOut = fadeOut;
		return this;
	}

	public VideoObject setCaption(Caption caption){
		this.caption = caption;
		return this;
	}


	//setters
	public void setVideoDuration(double videoDuration) {
		this.duration = videoDuration;
	}

	public void setStartTime(double videoStartTime) {
		this.startTime = videoStartTime;
	}

	//getters
	public String getPath() {
		return path;
	}

	public FadeIn getFadeIn() {
		return fadeIn;
	}

	public FadeOut getFadeOut() {
		return fadeOut;
	}

	public double getStartTime() {
		return startTime;
	}

	public double getDuration() {
		return duration;
	}

	public Caption getCaption() {
		return caption;
	}
}
