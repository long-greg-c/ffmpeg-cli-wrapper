package com.spikeify.ffmpeg.builder;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class Stitcher {

	private FFmpegOutputBuilder parent;

	private List<VideoObject> videoObjectList;

	protected Stitcher(FFmpegOutputBuilder parent, List<VideoObject> videoObjectList) {
		this.videoObjectList = videoObjectList;
		this.parent = parent;
	}

	public FFmpegBuilder done(){
		ImmutableList.Builder<String> args = new ImmutableList.Builder<>();

		String filterComplex = "";
		String videoOut = "vout";
		String audioOut = "aout";

		if(videoObjectList != null && videoObjectList.size() > 0){
			for(VideoObject videoObject: videoObjectList){
				if(videoObject != null && videoObject.getVideoPath() != null && videoObject.getVideoPath().length() > 0){
					args.add("-i", videoObject.getVideoPath());
				}
			}

			int seqNum = 0;
			String concat = "";
			String closing = " ;\\\n";
			for(VideoObject videoObject: this.videoObjectList){
				if(videoObject != null){
					String videoStream = "[" + String.valueOf(seqNum) + ":v]";
					String basicOperation = "format=pix_fmts=yuva420p, setpts=PTS-STARTPTS";
					filterComplex += videoStream + basicOperation + videoStream + closing;
					concat += "[" + String.valueOf(seqNum) + ":v][" + String.valueOf(seqNum) + ":a]";

					seqNum++;
				}
			}

			concat += "concat=n=" + String.valueOf(seqNum) + ":v=1:a=1 ["+ videoOut +"][" + audioOut + "]";
			filterComplex +=  concat;
		}


		args.add("-filter_complex", "\"" + filterComplex + "\"");
//		args.add("-filter_complex", "'" + filterComplex + "'");
		args.add("-map", "["+videoOut+"]");
		args.add("-map", "["+audioOut+"]");

		this.parent.parent.sticherArgs = args;
		this.parent.parent.isSticher = true;
		return parent.parent;
	}
}
