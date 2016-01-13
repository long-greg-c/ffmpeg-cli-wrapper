package com.spikeify.ffmpeg.builder.elements;

public class Caption {

	private String text;
	private String color;
	private String fontPath;
	private int size;
	private int x = -1;
	private int y = -1;
	private double alpha;

	private boolean movingByX;
	private boolean movingByY;
	private double movingSpeed = 1.0;
	private double startPositionOffset = 0;
	private boolean repeatX;
	private boolean repeatY;

	//border
	private double borderWidth;
	private String borderColor;

	//shadow
	private String shadowColor;
	private int shadowX;
	private int shadowY;

	private boolean disableNormalExp;

	private TextBox textBox;

	private Caption(String text, String color, String fontPath, int size, int x, int y, double alpha, boolean movingByX, boolean movingByY, double movingSpeed, double startPositionOffset, boolean repeatX, boolean repeatY, double borderWidth, String borderColor, String shadowColor, int shadowX, int shadowY, boolean disableNormalExp, TextBox textBox) {
		this.text = processText(text);
		this.color = color;
		this.fontPath = fontPath;
		this.size = size;
		this.x = x;
		this.y = y;
		this.alpha = alpha;
		this.movingByX = movingByX;
		this.movingByY = movingByY;
		this.movingSpeed = movingSpeed;
		this.startPositionOffset = startPositionOffset;
		this.repeatX = repeatX;
		this.repeatY = repeatY;
		this.borderWidth = borderWidth;
		this.borderColor = borderColor;
		this.shadowColor = shadowColor;
		this.shadowX = shadowX;
		this.shadowY = shadowY;
		this.disableNormalExp = disableNormalExp;
		this.textBox = textBox;
	}

	private String processText(String text) {
		return text == null ? "" : text.replace(":", "\\:").replace("\"", "\\\"").replace("'", "\u2019");
	}

	public boolean getDisableNormalExp() {
		return disableNormalExp;
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

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(double borderWidth) {
		this.borderWidth = borderWidth;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getShadowColor() {
		return shadowColor;
	}

	public void setShadowColor(String shadowColor) {
		this.shadowColor = shadowColor;
	}

	public int getShadowX() {
		return shadowX;
	}

	public void setShadowX(int shadowX) {
		this.shadowX = shadowX;
	}

	public int getShadowY() {
		return shadowY;
	}

	public void setShadowY(int shadowY) {
		this.shadowY = shadowY;
	}

	public static class CaptionBuilder {

		private String text;
		private String color;
		private String fontPath;
		private int size;
		private int x;
		private int y;
		private double alpha;
		private boolean movingByX;
		private boolean movingByY;
		private double movingSpeed;
		private double startPositionOffset;
		private boolean repeatX;
		private boolean repeatY;

		//border
		private double borderWidth;
		private String borderColor;

		//shadow
		private String shadowColor;
		private int shadowX;
		private int shadowY;

		private boolean disableNormalExp;

		private TextBox textBox;

		public CaptionBuilder(String fontPath, String text) {
			this.text = text;
			this.fontPath = fontPath;
		}

		/**
		 * If normal expansion is disabled, the text is printed verbatim.
		 *
		 * @param disableNormalExp - enable or disable normal expansion
		 * @return CaptionBuilder
		 */
		public CaptionBuilder setDisableNormalExp(boolean disableNormalExp) {
			this.disableNormalExp = disableNormalExp;
			return this;
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

		public CaptionBuilder setAlpha(double alpha) {
			this.alpha = alpha;
			return this;
		}

		public CaptionBuilder setBorderWidth(double borderWidth) {
			this.borderWidth = borderWidth;
			return this;
		}

		public CaptionBuilder setBorderColor(String borderColor) {
			this.borderColor = borderColor;
			return this;
		}

		public CaptionBuilder setShadowColor(String shadowColor) {
			this.shadowColor = shadowColor;
			return this;
		}

		public CaptionBuilder setShadowX(int shadowX) {
			this.shadowX = shadowX;
			return this;
		}

		public CaptionBuilder setShadowY(int shadowY) {
			this.shadowY = shadowY;
			return this;
		}

		public Caption createCaption() {
			return new Caption(text, color, fontPath, size, x, y, alpha, movingByX, movingByY, movingSpeed, startPositionOffset, repeatX, repeatY, borderWidth, borderColor, shadowColor, shadowX, shadowY, disableNormalExp, textBox);
		}
	}
}
