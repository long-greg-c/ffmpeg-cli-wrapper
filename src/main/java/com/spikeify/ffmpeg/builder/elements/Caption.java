package com.spikeify.ffmpeg.builder.elements;

public class Caption {

	private String text;
	private String color;
	private String fontPath;
	private int size;
	private int x = -1;
	private int y = -1;


	private boolean movingByX;
	private boolean movingByY;
	private double movingSpeed = 1.0;
	private double startPositionOffset = 0;
	private boolean repeatX;
	private boolean repeatY;

	private TextBox textBox;

	private Caption(String text, String color, String fontPath, int size, int x, int y, boolean movingByX, boolean movingByY, double movingSpeed, double startPositionOffset, boolean repeatX, boolean repeatY, TextBox textBox) {
		this.text = text;
		this.color = color;
		this.fontPath = fontPath;
		this.size = size;
		this.x = x;
		this.y = y;
		this.movingByX = movingByX;
		this.movingByY = movingByY;
		this.movingSpeed = movingSpeed;
		this.startPositionOffset = startPositionOffset;
		this.repeatX = repeatX;
		this.repeatY = repeatY;
		this.textBox = textBox;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFontPath() {
		return fontPath;
	}

	public void setFontPath(String fontPath) {
		this.fontPath = fontPath;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isMovingByX() {
		return movingByX;
	}

	public void setMovingByX(boolean movingByX) {
		this.movingByX = movingByX;
	}

	public boolean isMovingByY() {
		return movingByY;
	}

	public void setMovingByY(boolean movingByY) {
		this.movingByY = movingByY;
	}

	public double getMovingSpeed() {
		return movingSpeed;
	}

	public void setMovingSpeed(double movingSpeed) {
		this.movingSpeed = movingSpeed;
	}

	public double getStartPositionOffset() {
		return startPositionOffset;
	}

	public void setStartPositionOffset(double startPositionOffset) {
		this.startPositionOffset = startPositionOffset;
	}

	public boolean isRepeatX() {
		return repeatX;
	}

	public void setRepeatX(boolean repeatX) {
		this.repeatX = repeatX;
	}

	public boolean isRepeatY() {
		return repeatY;
	}

	public void setRepeatY(boolean repeatY) {
		this.repeatY = repeatY;
	}

	public TextBox getTextBox() {
		return textBox;
	}

	public void setTextBox(TextBox textBox) {
		this.textBox = textBox;
	}

	public static class CaptionBuilder {

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
}
