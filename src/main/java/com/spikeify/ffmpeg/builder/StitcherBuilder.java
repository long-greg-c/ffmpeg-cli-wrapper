package com.spikeify.ffmpeg.builder;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.spikeify.ffmpeg.builder.elements.Caption;
import com.spikeify.ffmpeg.builder.elements.VideoObject;

import java.util.List;

public class StitcherBuilder {

	private FFmpegBuilder parent;

	private List<VideoObject> videoObjectList;

	protected StitcherBuilder(FFmpegBuilder parent, List<VideoObject> videoObjectList) {
		this.videoObjectList = videoObjectList;
		this.parent = parent;
	}

	public FFmpegBuilder done() {
		ImmutableList.Builder<String> args = new ImmutableList.Builder<>();

		String filterComplex = "";
		String videoOut = "vout";
		String audioOut = "aout";

		if (videoObjectList != null && videoObjectList.size() > 0) {
			//add inputs
			for (VideoObject videoObject : videoObjectList) {
				if (videoObject != null && videoObject.getPath() != null && videoObject.getPath().length() > 0) {
					args.add("-i", videoObject.getPath());
				}
			}

			//process video object
			int seqNum = 0;
			String concat = "";
			String closing = " ;\n";
			for (VideoObject videoObject : this.videoObjectList) {
				if (videoObject != null) {
					String videoStreamInput = "[" + String.valueOf(seqNum) + ":v]";
					String videoStreamOuput = "[v" + String.valueOf(seqNum) + "]";
					String audioStreamInput = "[" + String.valueOf(seqNum) + ":a]";
					String audioStreamOuput = "[a" + String.valueOf(seqNum) + "]";

					String basicOperation = "format=pix_fmts=yuva420p, setpts=PTS-STARTPTS";

					String audioOperation = "", videoFadeIn = "", audioFadeIn = "", videoFadeOut = "", audioFadeOut = "";

					//set caption
					if (videoObject.getCaption() != null) {
						Caption caption = videoObject.getCaption();
						String captionOperation = ", drawtext=fontfile=" + caption.getFontPath() + ":text='" + caption.getText() + "'";

						if(caption.getSize() != 0){
							captionOperation +=":fontsize=" + String.valueOf(caption.getSize());
						}

						if(caption.getColor() != null){
							captionOperation +=":fontcolor="+caption.getColor();
						}

						if(caption.getX() > -1){
							if(caption.isMovingByX()){
								captionOperation += ":x=mod(n*" + caption.getMovingSpeed() + "\\, W+ "+ String.valueOf(caption.getStartPositionOffset())+") - " + String.valueOf(caption.getStartPositionOffset());
							}else{
								captionOperation +=":x=" + String.valueOf(caption.getX());
							}

						}

						if(caption.getY() > -1){
							if(caption.isMovingByY()){
								captionOperation += ":y=mod(n*" + caption.getMovingSpeed() + "\\, H+"+ String.valueOf(caption.getStartPositionOffset())+") - " + String.valueOf(caption.getStartPositionOffset());
							}else {
								captionOperation += ":y=" + String.valueOf(caption.getY());
							}
						}


						basicOperation += captionOperation;


					}

					//set fade in
					if (videoObject.getFadeIn() != null) {
						double start = videoObject.getFadeIn().getStart();
						double duration = videoObject.getFadeIn().getDuration();
						videoFadeIn = ", fade=t=in:st=" + String.valueOf(start) + ":d=" + String.valueOf(duration);
						audioFadeIn = "afade=t=in:st=" + String.valueOf(start) + ":d=" + String.valueOf(duration);
					}

					//set fade out
					if (videoObject.getFadeOut() != null) {
						double fromEnd = videoObject.getFadeOut().getFromEnd();
						double duration = videoObject.getFadeOut().getDuration();
						videoFadeOut = ", fade=t=out:st=" + String.valueOf(videoObject.getDuration() - fromEnd) + ":d=" + String.valueOf(duration);
						audioFadeOut = "afade=t=out:st=" + String.valueOf(videoObject.getDuration() - fromEnd) + ":d=" + String.valueOf(duration);
					}


					if (videoFadeIn.length() > 0 || videoFadeOut.length() > 0) {
						basicOperation += videoFadeIn + videoFadeOut;
					}
					if (audioFadeIn.length() > 0 || audioFadeOut.length() > 0) {
						audioOperation = audioStreamInput + audioFadeIn + (audioFadeOut.length() > 0 ? ", " : "") + audioFadeOut + audioStreamOuput;
					}

					//merge operations
					filterComplex += videoStreamInput + basicOperation + videoStreamOuput + closing;
					filterComplex += audioOperation.length() > 0 ? audioOperation + closing : "";
					concat += videoStreamOuput + (audioOperation.length() > 0 ? audioStreamOuput : audioStreamInput);
					seqNum++;
				}
			}

			concat += "concat=n=" + String.valueOf(seqNum) + ":v=1:a=1 [" + videoOut + "][" + audioOut + "]";
			filterComplex += concat;
		}


		args.add("-filter_complex", filterComplex);
		args.add("-map", "[" + videoOut + "]");
		args.add("-map", "[" + audioOut + "]");


		this.parent.sticherArgs = args;
		this.parent.isSticher = true;
		return this.parent;
	}
}
