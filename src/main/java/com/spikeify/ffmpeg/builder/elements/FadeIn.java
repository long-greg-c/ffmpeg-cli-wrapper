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

	public static class FadeInBuilder{
		private double duration;
		private double start;

		public FadeInBuilder(double start, double duration) {
			this.duration = duration;
			this.start = start;
		}

		public FadeIn createFadeIn(){
			return new FadeIn(start, duration);
		}
	}




}
