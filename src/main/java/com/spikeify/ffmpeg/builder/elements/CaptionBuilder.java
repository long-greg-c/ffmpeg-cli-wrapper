package com.spikeify.ffmpeg.builder.elements;

public class CaptionBuilder {
	private String text;
	private String color;
	private String fontPath;
	private int size;
	private int x;
	private int y;
	private boolean movingByX;
	private boolean movingByY;
	private double movingSpeed;
	private double startPositionOffset;
	private boolean repeatX;
	private boolean repeatY;
	private TextBox textBox;

	public CaptionBuilder(String fontPath, String text) {
		this.text = text;
		this.fontPath = fontPath;
	}

	public CaptionBuilder setColor(String color) {
		this.color = color;
		return this;
	}

	public CaptionBuilder setSize(int size) {
		this.size = size;
		return this;
	}

	public CaptionBuilder setX(int x) {
		this.x = x;
		return this;
	}

	public CaptionBuilder setY(int y) {
		this.y = y;
		return this;
	}

	public CaptionBuilder setMovingByX(boolean movingByX) {
		this.movingByX = movingByX;
		return this;
	}

	public CaptionBuilder setMovingByY(boolean movingByY) {
		this.movingByY = movingByY;
		return this;
	}

	public CaptionBuilder setMovingSpeed(double movingSpeed) {
		this.movingSpeed = movingSpeed;
		return this;
	}

	public CaptionBuilder setStartPositionOffset(double startPositionOffset) {
		this.startPositionOffset = startPositionOffset;
		return this;
	}

	public CaptionBuilder setRepeatX(boolean repeatX) {
		this.repeatX = repeatX;
		return this;
	}

	public CaptionBuilder setRepeatY(boolean repeatY) {
		this.repeatY = repeatY;
		return this;
	}

	public CaptionBuilder setTextBox(TextBox textBox) {
		this.textBox = textBox;
		return this;
	}

	public Caption createCaption() {
		return new Caption(text, color, fontPath, size, x, y, movingByX, movingByY, movingSpeed, startPositionOffset, repeatX, repeatY, textBox);
	}
}