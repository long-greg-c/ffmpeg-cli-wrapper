package com.spikeify.ffmpeg.builder.elements;

public class TextBox {

	private int x;
	private int y;
	private double width;
	private double height;
	private double tickness;
	private double opacity;
	private String color;
	private boolean wholeWidth;
	private boolean wholeHeight;

	private TextBox(int x, int y, double width, double height, double tickness, double opacity, String color, boolean wholeWidth, boolean wholeHeight) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tickness = tickness;
		this.opacity = opacity;
		this.color = color;
		this.wholeWidth = wholeWidth;
		this.wholeHeight = wholeHeight;
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

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getTickness() {
		return tickness;
	}

	public void setTickness(double tickness) {
		this.tickness = tickness;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isWholeWidth() {
		return wholeWidth;
	}

	public void setWholeWidth(boolean wholeWidth) {
		this.wholeWidth = wholeWidth;
	}

	public boolean isWholeHeight() {
		return wholeHeight;
	}

	public void setWholeHeight(boolean wholeHeight) {
		this.wholeHeight = wholeHeight;
	}

	public static class TextBoxBuilder {
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
}
