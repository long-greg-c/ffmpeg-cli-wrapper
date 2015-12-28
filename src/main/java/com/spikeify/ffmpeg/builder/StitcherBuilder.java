package com.spikeify.ffmpeg.builder;

import com.google.common.collect.ImmutableList;
import com.spikeify.ffmpeg.builder.commands.BasicCommand;
import com.spikeify.ffmpeg.builder.commands.CaptionCommand;
import com.spikeify.ffmpeg.builder.commands.FadeInCommand;
import com.spikeify.ffmpeg.builder.commands.FadeOutCommand;
import com.spikeify.ffmpeg.builder.elements.VideoObject;
import org.apache.commons.io.FilenameUtils;

import java.util.Arrays;
import java.util.List;

public class StitcherBuilder {

	private FFmpegBuilder parent;

	private List<VideoObject> videoObjectBuilderList;

	private int inputSeq = 0; //input counter for videos and video overlays
	private String closing = " ;\n";
	private static final String[] imageExt = new String[]{"jpg", "jpeg", "png"}; //supported inputs for images. More can be added

	protected StitcherBuilder(FFmpegBuilder parent, List<VideoObject> videoObjectBuilderList) {
		this.videoObjectBuilderList = videoObjectBuilderList;
		this.parent = parent;
	}

	private void addPath(ImmutableList.Builder<String> args, VideoObject videoObject) {
		if (videoObject != null && videoObject.getPath() != null && videoObject.getPath().length() > 0) {
			String ext = FilenameUtils.getExtension(videoObject.getPath());

			if (Arrays.asList(imageExt).contains(ext)) {
				args.add("-loop", "1", "-i", videoObject.getPath()); //images have loop parameter to work with it as with video
			}else{
				args.add("-i", videoObject.getPath());
			}

		}
	}


	private BasicCommand processVideoObject(VideoObject videoObject, boolean isOverlay) {
		BasicCommand bcb = BasicCommand.set(videoObject, inputSeq++);
		String audioOperation = "";
		String videoOperation = bcb.getBasicCmd();

		//set caption
		CaptionCommand captionCommand = CaptionCommand.set(videoObject.getCaptions(), videoObject.getCaption());
		videoOperation += captionCommand.getCmd();

		//set fade in
		FadeInCommand fadeInCommand = FadeInCommand.set(videoObject.getFadeIn(), isOverlay);
		String videoFadeIn = fadeInCommand.getVideoCmd();
		String audioFadeIn = fadeInCommand.getAudioCmd();

		//set fade out
		FadeOutCommand fadeOutCommand = FadeOutCommand.set(videoObject.getFadeOut(), videoObject.getEnd(), isOverlay);
		String videoFadeOut = fadeOutCommand.getVideoCmd();
		String audioFadeOut = fadeOutCommand.getAudioCmd();


		//fade in and fade out video
		if (videoFadeIn.length() > 0 || videoFadeOut.length() > 0) {
			videoOperation += videoFadeIn + videoFadeOut;
		}

		//fade in and fade out audio
		if (audioFadeIn.length() > 0 || audioFadeOut.length() > 0) {
			audioOperation = bcb.getAudioStreamInput() + bcb.getAudioTrimCmd() + (bcb.getAudioTrimCmd().length() > 0 ? ", " : "") + audioFadeIn + (audioFadeOut.length() > 0 ? ", " : "") + audioFadeOut + bcb.getAudioStreamOutput();
		}

		//start video at given offset. Mostly used for overlay videos
		if(videoObject.getVideoStartOffset() != 0.0){
			videoOperation += ", setpts=PTS+" + String.valueOf(videoObject.getVideoStartOffset()) + "/TB";
		}

		bcb.setVideoOperation(videoOperation); //set video operation
		bcb.setAudioOperation(audioOperation); //set audio operation
		return bcb;
	}

	private String processOverlay(VideoObject videoObject, BasicCommand bcb) {
		String operation = "";

		if(videoObject.getOverlayVideoList() != null && videoObject.getOverlayVideoList().size() > 0) {
			//video has overlays
			for (VideoObject overlayVideo : videoObject.getOverlayVideoList()) {
				if (overlayVideo != null) {


					overlayVideo.setStart(videoObject.getStart()); //reset overlay video length. Important because images does not have duration.
					overlayVideo.setEnd(videoObject.getEnd());

					//generate basic operation for video overlay
					BasicCommand bcbOverlay = processVideoObject(overlayVideo, true);
					operation += bcbOverlay.getVideoStreamInput() + bcbOverlay.getVideoOperation() + bcbOverlay.getVideoStreamOutput() + closing;

					//overlay basic operation
					String overlayOperation = "overlay=format=rgb";

					//overlay object location on main video
					if (overlayVideo.getOverlayX() != 0) {
						overlayOperation += ":x=" + String.valueOf(overlayVideo.getOverlayX());
					}
					if (overlayVideo.getOverlayY() != 0) {
						overlayOperation += ":y=" + String.valueOf(overlayVideo.getOverlayY());
					}

					//todo add suport for multiple overlays
					operation += bcb.getVideoStreamInput() + bcbOverlay.getVideoStreamOutput() + overlayOperation + bcb.getVideoTempOutput() + closing;
				}
			}
			operation += bcb.getVideoTempOutput() + bcb.getVideoOperation() + bcb.getVideoStreamOutput() + closing;

		}else{
			//video does not have overlays
			operation += bcb.getVideoStreamInput() + bcb.getVideoOperation() + bcb.getVideoStreamOutput() + closing;
		}

		return operation;
	}

	public FFmpegBuilder done() {
		ImmutableList.Builder<String> args = new ImmutableList.Builder<>();

		String filterComplex = "";
		String videoOut = "vout";
		String audioOut = "aout";

		if (videoObjectBuilderList != null && videoObjectBuilderList.size() > 0) {
			//add inputs
			for (VideoObject videoObject : videoObjectBuilderList) {
				addPath(args, videoObject); //main video

				//video overlays
				if(videoObject.getOverlayVideoList() != null) {
					for (VideoObject overlayVideo : videoObject.getOverlayVideoList()) {
						addPath(args, overlayVideo);
					}
				}
			}

			//process video object
			int numVideos = 0; //number of main videos
			String concat = "";

			for (VideoObject videoObject : this.videoObjectBuilderList) {
				if (videoObject != null) {
					BasicCommand bcb = processVideoObject(videoObject, false); //process main video
					filterComplex += processOverlay(videoObject, bcb); //process video overlays
					filterComplex += bcb.getAudioOperation().length() > 0 ? bcb.getAudioOperation() + closing : "";

					concat += bcb.getVideoStreamOutput() + (bcb.getAudioOperation().length() > 0 ? bcb.getAudioStreamOutput() : bcb.getAudioStreamInput());
					numVideos++;
				}
			}

			concat += "concat=n=" + String.valueOf(numVideos) + ":v=1:a=1 [" + videoOut + "][" + audioOut + "]";
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
