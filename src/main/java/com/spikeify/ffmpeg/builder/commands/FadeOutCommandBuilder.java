package com.spikeify.ffmpeg.builder.commands;

import com.spikeify.ffmpeg.builder.elements.FadeOut;

public class FadeOutCommandBuilder {

	private FadeOut fadeOut;
	private String videoCmd = "";
	private String audioCmd = "";
	private double videoLength;

	private FadeOutCommandBuilder(FadeOut fadeOut, double videoLength) {
		this.fadeOut = fadeOut;
		this.videoLength = videoLength;
	}

	private FadeOutCommandBuilder build(){
		if(this.fadeOut != null && videoLength > 0) {
			double fromEnd = this.fadeOut.getFromEnd();
			double duration = this.fadeOut.getDuration();
			this.videoCmd = ", fade=t=out:st=" + String.valueOf(this.videoLength - fromEnd) + ":d=" + String.valueOf(duration);
			this.audioCmd = "afade=t=out:st=" + String.valueOf(this.videoLength - fromEnd) + ":d=" + String.valueOf(duration);
		}
		return this;
	}

	public static FadeOutCommandBuilder set(FadeOut fadeOut, double videoLength){
		return new FadeOutCommandBuilder(fadeOut, videoLength).build();
	}

	public String getVideoCmd() {
		return videoCmd;
	}

	public String getAudioCmd() {
		return audioCmd;
	}

}
