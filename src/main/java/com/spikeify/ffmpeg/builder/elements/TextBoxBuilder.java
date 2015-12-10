package com.spikeify.ffmpeg.builder.elements;

public class TextBoxBuilder {
	private int x;
	private int y;
	private double width;
	private double height;
	private double tickness;
	private double opacity;
	private String color;
	private boolean wholeWidth;
	private boolean wholeHeight;

	public TextBoxBuilder setX(int x) {
		this.x = x;
		return this;
	}

	public TextBoxBuilder setY(int y) {
		this.y = y;
		return this;
	}

	public TextBoxBuilder setWidth(double width) {
		this.width = width;
		return this;
	}

	public TextBoxBuilder setHeight(double height) {
		this.height = height;
		return this;
	}

	public TextBoxBuilder setTickness(double tickness) {
		this.tickness = tickness;
		return this;
	}

	public TextBoxBuilder setOpacity(double opacity) {
		this.opacity = opacity;
		return this;
	}

	public TextBoxBuilder setColor(String color) {
		this.color = color;
		return this;
	}

	public TextBoxBuilder setWholeWidth(boolean wholeWidth) {
		this.wholeWidth = wholeWidth;
		return this;
	}

	public TextBoxBuilder setWholeHeight(boolean wholeHeight) {
		this.wholeHeight = wholeHeight;
		return this;
	}

	public TextBox createTextBox() {
		return new TextBox(x, y, width, height, tickness, opacity, color, wholeWidth, wholeHeight);
	}
}