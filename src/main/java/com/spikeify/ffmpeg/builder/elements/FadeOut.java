package com.spikeify.ffmpeg.builder.elements;

public class FadeOut extends Fade{

	private double fromEnd;

	public FadeOut(double fromEnd, double duration) {
		super(duration);
		this.fromEnd = fromEnd;
	}

	public double getFromEnd() {
		return fromEnd;
	}
}
