package com.spikeify.ffmpeg.builder.commands;

import com.spikeify.ffmpeg.builder.elements.FadeOut;

public class FadeOutCommand {

	private FadeOut fadeOut;
	private String videoCmd = "";
	private String audioCmd = "";
	private double videoLength;

	private FadeOutCommand(FadeOut fadeOut, double videoLength) {
		this.fadeOut = fadeOut;
		this.videoLength = videoLength;
	}

	private FadeOutCommand build(){
		if(this.fadeOut != null && videoLength > 0) {
			double fromEnd = this.fadeOut.getFromEnd();
			double duration = this.fadeOut.getDuration();
			this.videoCmd = ", fade=t=out:st=" + String.valueOf(this.videoLength - fromEnd) + ":d=" + String.valueOf(duration);
			this.audioCmd = "afade=t=out:st=" + String.valueOf(this.videoLength - fromEnd) + ":d=" + String.valueOf(duration);
		}
		return this;
	}

	public static FadeOutCommand set(FadeOut fadeOut, double videoLength){
		return new FadeOutCommand(fadeOut, videoLength).build();
	}

	public String getVideoCmd() {
		return videoCmd;
	}

	public String getAudioCmd() {
		return audioCmd;
	}

}
