package com.spikeify.ffmpeg;

import com.spikeify.ffmpeg.builder.FFmpegBuilder;
import com.spikeify.ffmpeg.builder.VideoObject;
import com.spikeify.ffmpeg.job.FFmpegJob;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StitcherTest {

	FFmpeg ffmpeg = new FFmpeg();
	FFprobe ffprobe = new FFprobe();

	ExecutorService executor = Executors.newSingleThreadExecutor();

	public StitcherTest() throws IOException {}

	@Test
	public void testStitchVideos(){
		String basePath = "/Users/hiphop/Documents/stitching/";
		List<VideoObject> videoObjectList = new ArrayList<>();

		videoObjectList.add(new VideoObject(basePath +"livecaptv.mp4"));
		videoObjectList.add(new VideoObject(basePath +"one.mp4"));

		FFmpegBuilder builder = new FFmpegBuilder().overrideOutputFiles(true).addOutput(basePath+"topvideos.mp4").stitchVideos(videoObjectList).done();

		FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
		FFmpegJob job = executor.createJob(builder);
		job.run();

	}

}