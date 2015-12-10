package com.spikeify.ffmpeg.builder.commands;

import com.spikeify.ffmpeg.builder.elements.FadeIn;

public class FadeInCommandBuilder {

	private FadeIn fadeInBuilder;
	private String videoCmd = "";
	private String audioCmd = "";

	private FadeInCommandBuilder(FadeIn fadeInBuilder) {
		this.fadeInBuilder = fadeInBuilder;
	}

	private FadeInCommandBuilder build(){
		if(this.fadeInBuilder != null) {
			double start = this.fadeInBuilder.getStart();
			double duration = this.fadeInBuilder.getDuration();
			this.videoCmd = ", fade=t=in:st=" + String.valueOf(start) + ":d=" + String.valueOf(duration);
			this.audioCmd = "afade=t=in:st=" + String.valueOf(start) + ":d=" + String.valueOf(duration);
		}
		return this;
	}

	public static FadeInCommandBuilder set(FadeIn fadeInBuilder){
		return new FadeInCommandBuilder(fadeInBuilder).build();
	}

	public String getVideoCmd() {
		return videoCmd;
	}

	public String getAudioCmd() {
		return audioCmd;
	}
}
