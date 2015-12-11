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


	public static class FadeOutBuilder{
		private double fromEnd;
		private double duration;

		public FadeOutBuilder(double fromEnd, double duration) {
			this.fromEnd = fromEnd;
			this.duration = duration;
		}

		public FadeOut createFadeOut(){
			return new FadeOut(fromEnd, duration);
		}
	}

}
