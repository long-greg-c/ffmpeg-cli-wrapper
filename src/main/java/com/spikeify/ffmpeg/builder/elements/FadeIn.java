package com.spikeify.ffmpeg.builder.elements;

public class FadeIn extends Fade{

	private double start;

	public FadeIn(double start, double duration) {
		super(duration);
		this.start = start;
	}

	public double getStart() {
		return start;
	}
}
