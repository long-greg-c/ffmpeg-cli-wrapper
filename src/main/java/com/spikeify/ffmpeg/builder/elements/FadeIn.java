package com.spikeify.ffmpeg.builder.elements;

public class FadeIn extends Fade{

	private double start;

	private FadeIn(double start, double duration) {
		super(duration);
		this.start = start;
	}

	public double getStart() {
		return start;
	}

	public static FadeIn fadeIn(double start, double duration){
		return new FadeIn(start, duration);
	}


}
