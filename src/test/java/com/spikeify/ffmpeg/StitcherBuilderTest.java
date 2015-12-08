package com.spikeify.ffmpeg;

import com.spikeify.ffmpeg.builder.FFmpegBuilder;
import com.spikeify.ffmpeg.builder.elements.Caption;
import com.spikeify.ffmpeg.builder.elements.FadeIn;
import com.spikeify.ffmpeg.builder.elements.FadeOut;
import com.spikeify.ffmpeg.builder.elements.VideoObject;
import com.spikeify.ffmpeg.job.FFmpegJob;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StitcherBuilderTest {

	FFmpeg ffmpeg = new FFmpeg();
	FFprobe ffprobe = new FFprobe();

	public StitcherBuilderTest() throws IOException {}

	@Test
	public void testStitchVideos() throws IOException {
		//initialize videoObjectsList
		String input = getClass().getResource("video.mp4").getPath();
		String output = input.substring(0, input.lastIndexOf(File.separator)) + File.separator + "output.mp4";
		List<VideoObject> videoObjectList = new ArrayList<>();

		//settings for video 1
		FadeIn fadeIn1 = new FadeIn(0, 1);
		FadeOut fadeOut1 = new FadeOut(1, 1);
		Caption caption1 = new Caption("/Library/Fonts/Krungthep.ttf", "First One").setColor("white").setSize(40).setX(50).setY(30).setMovingByY().setTextMovingSpeed(7).setStartPositionOffset(100);
		VideoObject videoObject1 = new VideoObject(input).setFadeIn(fadeIn1).setFadeOut(fadeOut1).setCaption(caption1);
		videoObjectList.add(videoObject1);

		//settings for video 2
		FadeIn fadeIn2 = new FadeIn(0, 1);
		FadeOut fadeOut2 = new FadeOut(1, 1);
		Caption caption2 = new Caption("/Library/Fonts/Krungthep.ttf", "Second One").setColor("white").setSize(40).setX(50).setY(30).setMovingByY().setTextMovingSpeed(7).setStartPositionOffset(100);
		VideoObject videoObject2 = new VideoObject(input).setFadeIn(fadeIn2).setFadeOut(fadeOut2).setCaption(caption2);
		videoObjectList.add(videoObject2);

		//set duration to each video
		ffprobe.setDuration(videoObjectList);

		//define stitching settings
		FFmpegBuilder builder = new FFmpegBuilder().overrideOutputFiles(true).addOutput(output).stitchVideos(videoObjectList).done();

		//execute
		FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
		FFmpegJob job = executor.createJob(builder);
		job.run();

		//add output video
		List<VideoObject> videoObjectOutputList = new ArrayList<>();
		VideoObject videoObject3 = new VideoObject(output);
		videoObjectOutputList.add(videoObject3);

		//set duration to output video
		ffprobe.setDuration(videoObjectOutputList);



		//check video length
		assertEquals(8.871, videoObject1.getDuration(), 3);
		assertEquals(17.764, videoObjectOutputList.get(0).getDuration(), 5);
	}

}