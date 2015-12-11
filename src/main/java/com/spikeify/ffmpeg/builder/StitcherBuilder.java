package com.spikeify.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import com.spikeify.ffmpeg.builder.commands.BasicCommand;
import com.spikeify.ffmpeg.builder.commands.CaptionCommand;
import com.spikeify.ffmpeg.builder.commands.FadeInCommand;
import com.spikeify.ffmpeg.builder.commands.FadeOutCommand;
import com.spikeify.ffmpeg.builder.elements.VideoObject;

import java.util.List;

public class StitcherBuilder {

	private FFmpegBuilder parent;

	private List<VideoObject> videoObjectBuilderList;

	protected StitcherBuilder(FFmpegBuilder parent, List<VideoObject> videoObjectBuilderList) {
		this.videoObjectBuilderList = videoObjectBuilderList;
		this.parent = parent;
	}

	public FFmpegBuilder done() {
		ImmutableList.Builder<String> args = new ImmutableList.Builder<>();

		String filterComplex = "";
		String videoOut = "vout";
		String audioOut = "aout";

		if (videoObjectBuilderList != null && videoObjectBuilderList.size() > 0) {
			//add inputs
			for (VideoObject videoObject : videoObjectBuilderList) {
				if (videoObject != null && videoObject.getPath() != null && videoObject.getPath().length() > 0) {
					args.add("-i", videoObject.getPath());
				}
			}

			//process video object
			int seqNum = 0;
			String concat = "";
			String closing = " ;\n";
			for (VideoObject videoObject : this.videoObjectBuilderList) {
				if (videoObject != null) {
					BasicCommand bcb = BasicCommand.set(videoObject, seqNum++);
					String basicOperation = bcb.getBasicCmd();

					String audioOperation = "";

					//set caption
					CaptionCommand captionCommand = CaptionCommand.set(videoObject.getCaptions(), videoObject.getCaption());
					basicOperation += captionCommand.getCmd();

					//set fade in
					FadeInCommand fadeInCommand = FadeInCommand.set(videoObject.getFadeIn());
					String videoFadeIn = fadeInCommand.getVideoCmd();
					String audioFadeIn = fadeInCommand.getAudioCmd();

					//set fade out
					FadeOutCommand fadeOutCommand = FadeOutCommand.set(videoObject.getFadeOut(), videoObject.getEnd());
					String videoFadeOut = fadeOutCommand.getVideoCmd();
					String audioFadeOut = fadeOutCommand.getAudioCmd();


					if (videoFadeIn.length() > 0 || videoFadeOut.length() > 0) {
						basicOperation += videoFadeIn + videoFadeOut;
					}
					if (audioFadeIn.length() > 0 || audioFadeOut.length() > 0) {
						audioOperation = bcb.getAudioStreamInput() + bcb.getAudioTrimCmd() + (bcb.getAudioTrimCmd().length() > 0 ? ", " : "") + audioFadeIn + (audioFadeOut.length() > 0 ? ", " : "") + audioFadeOut + bcb.getAudioStreamOutput();
					}

					//merge operations
					filterComplex += bcb.getVideoStreamInput() + basicOperation + bcb.getVideoStreamOutput() + closing;
					filterComplex += audioOperation.length() > 0 ? audioOperation + closing : "";
					concat += bcb.getVideoStreamOutput() + (audioOperation.length() > 0 ? bcb.getAudioStreamOutput() : bcb.getAudioStreamInput());
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
