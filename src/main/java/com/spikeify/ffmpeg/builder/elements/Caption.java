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

	private TextBox textBox;


	public Caption(String fontPath, String text) {
		this.text = text;
		this.fontPath = fontPath;
	}

	public Caption setColor(String color) {
		this.color = color;
		return this;
	}

	public Caption setSize(int size) {
		this.size = size;
		return this;
	}

	public Caption setX(int x) {
		this.x = x;
		return this;
	}

	public Caption setY(int y) {
		this.y = y;
		return this;
	}

	public Caption setMovingByX(boolean movingByX) {
		this.movingByX = movingByX;
		return this;
	}

	public Caption setMovingByY(boolean movingByY) {
		this.movingByY = movingByY;
		return this;
	}

	public Caption setTextBox(TextBox textBox) {
		this.textBox = textBox;
		return this;
	}

	public String getText() {
		return text;
	}

	public String getColor() {
		return color;
	}

	public String getFontPath() {
		return fontPath;
	}

	public int getSize() {
		return size;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isMovingByX() {
		return movingByX;
	}

	public boolean isMovingByY() {
		return movingByY;
	}

	public TextBox getTextBox() {
		return textBox;
	}
}
