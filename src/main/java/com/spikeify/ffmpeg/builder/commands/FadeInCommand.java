package com.spikeify.ffmpeg.builder.commands;

import com.spikeify.ffmpeg.builder.elements.FadeIn;

public class FadeInCommand {

	private FadeIn fadeInBuilder;
	private String videoCmd = "";
	private String audioCmd = "";
	private boolean isOverlay;

	private FadeInCommand(FadeIn fadeInBuilder, boolean isOverlay) {
		this.fadeInBuilder = fadeInBuilder;
		this.isOverlay = isOverlay;
	}

	private FadeInCommand build(){
		if(this.fadeInBuilder != null) {
			double start = this.fadeInBuilder.getStart();
			double duration = this.fadeInBuilder.getDuration();
			this.videoCmd = ", fade=t=in:st=" + String.valueOf(start) + ":d=" + String.valueOf(duration) + (isOverlay ? ":alpha=1" : "");
			this.audioCmd = "afade=t=in:st=" + String.valueOf(start) + ":d=" + String.valueOf(duration);
		}
		return this;
	}

	public static FadeInCommand set(FadeIn fadeInBuilder, boolean isOverlay){
		return new FadeInCommand(fadeInBuilder, isOverlay).build();
	}

	public String getVideoCmd() {
		return videoCmd;
	}

	public String getAudioCmd() {
		return audioCmd;
	}
}
