package com.spikeify.ffmpeg.builder.elements;

public class FadeOut extends Fade {

	private double end;
	private boolean lengthUnknown;

	private FadeOut(double end, double duration, boolean lengthUnknown) {
		super(duration);
		this.end = end;
		this.lengthUnknown = lengthUnknown;
	}

	public double getEnd() {
		return end;
	}

	public boolean isLengthUnknown() {
		return lengthUnknown;
	}

	public static class FadeOutBuilder{
		private double fromEnd;
		private double duration;
		private boolean lengthUnknown;

		public FadeOutBuilder(double fromEnd, double duration) {
			this.fromEnd = fromEnd;
			this.duration = duration;
		}

		public FadeOutBuilder setLengthUnknown(boolean lengthUnknown) {
			this.lengthUnknown = lengthUnknown;
			return this;
		}

		public FadeOut createFadeOut(){
			return new FadeOut(fromEnd, duration, lengthUnknown);
		}
	}

}
