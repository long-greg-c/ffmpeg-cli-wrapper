package com.spikeify.ffmpeg;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spikeify.commons.lang3.math.gson.FractionAdapter;
import com.spikeify.ffmpeg.builder.elements.VideoObject;
import com.spikeify.ffmpeg.probe.FFmpegProbeResult;
import org.apache.commons.lang3.math.Fraction;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Wrapper around FFprobe
 * 
 * TODO ffprobe -v quiet -print_format json -show_format -show_streams mobileedge_1280x720.mp4
 * @author bramp
 *
 */
public class FFprobe {

	final Gson gson = setupGson();
	
	final String path;

	/**
	 * Function to run FFmpeg. We define it like this so we can swap it out (during testing)
	 */
	ProcessFunction runFunc = new RunProcessFunction();

	public FFprobe() {
		this.path = "ffprobe";
	}

	public FFprobe(@Nonnull String path) {
		this.path = path;
	}

	private static Gson setupGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Fraction.class, new FractionAdapter());
		return builder.create();
	}

	public String getPath() {
		return path;
	}

	public FFmpegProbeResult probe(String mediaPath) throws IOException {
		ImmutableList.Builder<String> args = new ImmutableList.Builder<String>();

		args.add(path)
			.add("-v", "quiet")
			.add("-print_format", "json")
			.add("-show_error")
			.add("-show_format")
			.add("-show_streams")

			//.add("--show_packets")
			//.add("--show_frames")

			.add(mediaPath);

		BufferedReader reader = runFunc.run(args.build());
		return gson.fromJson(reader, FFmpegProbeResult.class);
	}


	public void setDuration(List<VideoObject> videoObjectList) throws IOException {
		for (VideoObject videoObject : videoObjectList){
			if(videoObject != null && videoObject.getPath() != null){
				ImmutableList.Builder<String> args = new ImmutableList.Builder<String>();

				args.add(path)
						.add("-v", "quiet")
						.add("-print_format", "json")
						.add("-show_error")
						.add("-show_format")
						.add("-show_streams")
						.add(videoObject.getPath());

				BufferedReader reader = runFunc.run(args.build());
				FFmpegProbeResult fFmpegProbeResult = gson.fromJson(reader, FFmpegProbeResult.class);
				if(fFmpegProbeResult != null && fFmpegProbeResult.getFormat() != null) {
					if(videoObject.getStart() == -1) {
						videoObject.setStart(fFmpegProbeResult.getFormat().start_time);
					}
					if(videoObject.getEnd() == -1) {
						videoObject.setEnd(fFmpegProbeResult.getFormat().duration);
					}
				}

			}
		}

	}


}
