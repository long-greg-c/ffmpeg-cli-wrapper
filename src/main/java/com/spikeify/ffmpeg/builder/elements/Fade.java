package com.spikeify.ffmpeg.builder.elements;

public class Fade {

	private double duration;

	Fade(double duration) {
		this.duration = duration;
	}

	public double getDuration() {
		return duration;
	}
}
