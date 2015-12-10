package com.spikeify.ffmpeg.builder.elements;

public class FadeOut extends Fade {

	private double fromEnd;

	private FadeOut(double fromEnd, double duration) {
		super(duration);
		this.fromEnd = fromEnd;
	}

	public double getFromEnd() {
		return fromEnd;
	}

	public static FadeOut fadeOut(double fromEnd, double duration) {
		return new FadeOut(fromEnd, duration);
	}
}
