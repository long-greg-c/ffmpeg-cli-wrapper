package com.spikeify.ffmpeg.builder.commands;

import com.spikeify.ffmpeg.builder.elements.Caption;
import com.spikeify.ffmpeg.builder.elements.TextBox;

import java.util.ArrayList;
import java.util.List;

public class CaptionCommand {

	private List<Caption> captions;
	private String cmd = "";

	private CaptionCommand(List<Caption> captions) {
		this.captions = captions;
	}

	private CaptionCommand build() {
		if (captions != null && captions.size() > 0) {
			for (Caption caption : captions) {
				//set text and font
				this.cmd += ", drawtext=fontfile=" + caption.getFontPath() + ":text='" + caption.getText() + "'";

				//set font size
				if (caption.getSize() != 0) {
					this.cmd += ":fontsize=" + String.valueOf(caption.getSize());
				}

				//set color
				if (caption.getColor() != null) {
					this.cmd += ":fontcolor=" + caption.getColor();
				}

				//set alpha
				if(caption.getAlpha() > 0 && caption.getAlpha() <= 1){
					this.cmd += ":alpha=" + caption.getAlpha();
				}

				//set border color
				if(caption.getBorderColor() != null && caption.getBorderColor().length() > 0){
					this.cmd += ":bordercolor=" + caption.getBorderColor();
				}

				//set border width
				if(caption.getBorderWidth() > 0){
					this.cmd += ":borderw=" + caption.getBorderWidth();
				}

				//set shadow color
				if(caption.getShadowColor() != null && caption.getShadowColor().length() > 0){
					this.cmd += ":shadowcolor=" + caption.getShadowColor();
				}

				//set shadow x
				if(caption.getShadowX() > 0){
					this.cmd += ":shadowx=" + caption.getShadowX();
				}

				//set shadow y
				if(caption.getShadowY() > 0){
					this.cmd += ":shadowy=" + caption.getShadowY();
				}

				//set x position
				if (caption.getX() > -1) {
					if (caption.isMovingByX()) {
						if (caption.isRepeatY()) {
							this.cmd += ":x=mod(n*" + caption.getMovingSpeed() + "\\, W+ " + String.valueOf(caption.getStartPositionOffset()) + ") - " + String.valueOf(caption.getStartPositionOffset());
						} else {
							this.cmd += ":x=n*" + caption.getMovingSpeed() + " + " + String.valueOf(caption.getStartPositionOffset()) + "\"";
						}
					} else {
						this.cmd += ":x=" + String.valueOf(caption.getX());
					}
				}

				//set y position
				if (caption.getY() > -1) {
					if (caption.isMovingByY()) {
						if (caption.isRepeatY()) {
							this.cmd += ":y=mod(n*" + caption.getMovingSpeed() + "\\, H+" + String.valueOf(caption.getStartPositionOffset()) + ") - " + String.valueOf(caption.getStartPositionOffset());
						} else {
							this.cmd += ":y=n*" + caption.getMovingSpeed() + " + " + String.valueOf(caption.getStartPositionOffset());
						}
					} else {
						this.cmd += ":y=" + String.valueOf(caption.getY());
					}
				}

				//enable or disable normal expansion. It is enabled by default
				if(caption.getDisableNormalExp()){
					this.cmd += ":expansion=none";
				}

				if (caption.getTextBox() != null) {
					TextBox textBox = caption.getTextBox();
//					command example: drawbox=y=50:color=black@0.4:width=iw:height=68:t=max
					this.cmd += ", drawbox=";

					//set height
					if (textBox.isWholeHeight()) {
						this.cmd += "height=ih:";
					} else if (textBox.getHeight() > 0) {
						this.cmd += "height=" + String.valueOf(textBox.getHeight()) + ":";
					} else {
						this.cmd += "height=0:";
					}

					//set width
					if (textBox.isWholeWidth()) {
						this.cmd += "width=iw:";
					} else if (textBox.getWidth() > 0) {
						this.cmd += "width=" + String.valueOf(textBox.getWidth()) + ":";
					} else {
						this.cmd += "width=0:";
					}

					//set x coordinate
					if(textBox.getX() >= 0){
						this.cmd += "x="+String.valueOf(textBox.getX())+":";
					}else{
						this.cmd += "x=0:";
					}

					//set y coordinate
					if(textBox.getY() >= 0){
						this.cmd += "y="+String.valueOf(textBox.getY())+":";
					}else{
						this.cmd += "y=0:";
					}

					//set color
					if (textBox.getColor() != null && textBox.getColor().length() > 0) {
						this.cmd += "color=" + textBox.getColor();
					} else {
						this.cmd += "color=black";
					}

					//set opacity
					if(textBox.getOpacity() > 0){
						this.cmd += "@"+String.valueOf(textBox.getOpacity())+":";
					}else {
						this.cmd += "@1:";
					}

					//set thickness
					if(textBox.getThickness() > 0){
						this.cmd += "t="+String.valueOf(textBox.getThickness());
					}else {
						this.cmd += "t=max";
					}


				}
			}
		}
		return this;
	}


	public static CaptionCommand set(List<Caption> captions, Caption caption) {
		if (captions == null) {
			captions = new ArrayList<>();
		}
		if (caption != null) {
			captions.add(caption);
		}
		return new CaptionCommand(captions).build();
	}

	public String getCmd() {
		return cmd;
	}
}
