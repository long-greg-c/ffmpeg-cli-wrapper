package com.spikeify.ffmpeg.builder.commands;

import com.spikeify.ffmpeg.builder.elements.FadeOut;

public class FadeOutCommand {

	private FadeOut fadeOut;
	private String videoCmd = "";
	private String audioCmd = "";
	private double videoLength;
	private boolean isOverlay;

	private FadeOutCommand(FadeOut fadeOut, double videoLength, boolean isOverlay) {
		this.fadeOut = fadeOut;
		this.videoLength = videoLength;
		this.isOverlay = isOverlay;
	}

	private FadeOutCommand build() {
		if (this.fadeOut != null) {
			double fromEnd = this.fadeOut.getEnd();
			double duration = this.fadeOut.getDuration();
			if (fadeOut.isLengthUnknown()) {
				//video length is unknown, user sets end of fade out effect.
				this.videoCmd = ", fade=t=out:st=" + String.valueOf(fromEnd) + ":d=" + String.valueOf(duration) + (isOverlay ? ":alpha=1" : "");
				this.audioCmd = "afade=t=out:st=" + String.valueOf(fromEnd) + ":d=" + String.valueOf(duration);
			} else {
				//video length is known (was set with ffprobe) and user specifies an offset
				this.videoCmd = ", fade=t=out:st=" + String.valueOf(this.videoLength - fromEnd) + ":d=" + String.valueOf(duration) + (isOverlay ? ":alpha=1" : "");
				this.audioCmd = "afade=t=out:st=" + String.valueOf(this.videoLength - fromEnd) + ":d=" + String.valueOf(duration);
			}
		}

		return this;
	}

	public static FadeOutCommand set(FadeOut fadeOut, double videoLength, boolean isOverlay) {
		return new FadeOutCommand(fadeOut, videoLength, isOverlay).build();
	}

	public String getVideoCmd() {
		return videoCmd;
	}

	public String getAudioCmd() {
		return audioCmd;
	}

}
