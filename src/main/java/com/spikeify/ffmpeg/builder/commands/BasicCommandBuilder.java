package com.spikeify.ffmpeg.builder.commands;

import com.spikeify.ffmpeg.builder.elements.VideoObject;

public class BasicCommandBuilder {

	private VideoObject videoObject;
	private int seqNum;
	private String basicCmd = "";
	private String audioTrimCmd = "";
	private String videoStreamInput;
	private String videoStreamOutput;
	private String audioStreamInput;
	private String audioStreamOutput;

	private BasicCommandBuilder(VideoObject videoObject, int seqNum) {
		this.videoObject = videoObject;
		this.seqNum = seqNum;
	}

	private BasicCommandBuilder build() {
		if (videoObject != null) {
			this.videoStreamInput = "[" + String.valueOf(seqNum) + ":v]";
			this.videoStreamOutput = "[v" + String.valueOf(seqNum) + "]";
			this.audioStreamInput = "[" + String.valueOf(seqNum) + ":a]";
			this.audioStreamOutput = "[a" + String.valueOf(seqNum) + "]";

			//process video and audio trim operations
			if (videoObject.getStart() > 0 || videoObject.getEnd() > 0) {
				this.basicCmd = "trim=";
				this.audioTrimCmd = "atrim=";

				this.basicCmd += String.valueOf(videoObject.getStart()) + ":" + String.valueOf(videoObject.getEnd()) + ", ";
				this.audioTrimCmd += String.valueOf(videoObject.getStart()) + ":" + String.valueOf(videoObject.getEnd());
				this.audioTrimCmd += ", asetpts=PTS-STARTPTS";

				videoObject.setEnd(videoObject.getEnd() - videoObject.getStart()); //reset video end

			}
			this.basicCmd += "format=pix_fmts=yuva420p, setpts=PTS-STARTPTS";
		}
		return this;
	}

	public static BasicCommandBuilder set(VideoObject videoObject, int seqNum) {
		return new BasicCommandBuilder(videoObject, seqNum).build();
	}

	public String getBasicCmd() {
		return basicCmd;
	}

	public String getAudioTrimCmd() {
		return audioTrimCmd;
	}

	public String getVideoStreamInput() {
		return videoStreamInput;
	}

	public String getVideoStreamOutput() {
		return videoStreamOutput;
	}

	public String getAudioStreamInput() {
		return audioStreamInput;
	}

	public String getAudioStreamOutput() {
		return audioStreamOutput;
	}
}
