package com.spikeify.ffmpeg;

import com.spikeify.ffmpeg.builder.FFmpegBuilder;
import com.spikeify.ffmpeg.builder.elements.*;
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
		TextBox textBox1 = new TextBoxBuilder().setColor("black").setHeight(100).setWidth(100).setX(50).setY(50).setOpacity(0.5).createTextBox();
		Caption caption1 = new CaptionBuilder("/Library/Fonts/Krungthep.ttf", "First One").setColor("white").setSize(40).setX(50).setY(30).setMovingByY(true).setRepeatY(true).setMovingSpeed(7).setStartPositionOffset(100).setTextBox(textBox1).createCaption();
		VideoObject videoObject1 = new VideoObjectBuilder(input).setFadeInBuilder(FadeIn.fadeIn(0, 1)).setFadeOut(FadeOut.fadeOut(1, 1)).setCaption(caption1).createVideoObject();
		videoObjectList.add(videoObject1);

		//settings for video 2
		Caption caption2 = new CaptionBuilder("/Library/Fonts/Krungthep.ttf", "Second One").setColor("white").setSize(40).setX(50).setY(30).setMovingByY(true).setMovingSpeed(7).setStartPositionOffset(100).createCaption();
		VideoObject videoObject2 = new VideoObjectBuilder(input).setFadeInBuilder(FadeIn.fadeIn(0, 1)).setFadeOut(FadeOut.fadeOut(1, 1)).setCaption(caption2).createVideoObject();
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
		VideoObject videoObject3 = new VideoObjectBuilder(output).createVideoObject();
		videoObjectOutputList.add(videoObject3);

		//set duration to output video
		ffprobe.setDuration(videoObjectOutputList);

		//check video length
		assertEquals(8.871, videoObjectList.get(0).getEnd(), 3);
		assertEquals(17.764, videoObjectOutputList.get(0).getEnd(), 5);
	}

	@Test
	public void testStitchShorterVideos() throws IOException {
		//initialize videoObjectsList
		String input = getClass().getResource("video.mp4").getPath();
		String output = input.substring(0, input.lastIndexOf(File.separator)) + File.separator + "output.mp4";
		List<VideoObject> videoObjectList = new ArrayList<>();

		//settings for video 1
		TextBox textBox1 = new TextBoxBuilder().setColor("black").setHeight(100).setWidth(100).setX(50).setY(50).setOpacity(0.5).createTextBox();
		Caption caption1 = new CaptionBuilder("/Library/Fonts/Krungthep.ttf", "First One").setColor("white").setSize(40).setX(50).setY(30).setMovingByY(true).setRepeatY(true).setMovingSpeed(7).setStartPositionOffset(100).setTextBox(textBox1).createCaption();
		VideoObject videoObject1 = new VideoObjectBuilder(input).setFadeInBuilder(FadeIn.fadeIn(0, 1)).setFadeOut(FadeOut.fadeOut(1, 1)).setCaption(caption1).setStart(2).setEnd(4).createVideoObject();
		videoObjectList.add(videoObject1);

		//settings for video 2
		Caption caption2 = new CaptionBuilder("/Library/Fonts/Krungthep.ttf", "Second One").setColor("white").setSize(40).setX(50).setY(30).setMovingByY(true).setMovingSpeed(7).setStartPositionOffset(100).createCaption();
		VideoObject videoObject2 = new VideoObjectBuilder(input).setFadeInBuilder(FadeIn.fadeIn(0, 1)).setFadeOut(FadeOut.fadeOut(1, 1)).setCaption(caption2).setStart(4).setEnd(6).createVideoObject();
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
		VideoObject videoObject3 = new VideoObjectBuilder(output).createVideoObject();
		videoObjectOutputList.add(videoObject3);

		//set duration to output video
		ffprobe.setDuration(videoObjectOutputList);

		//check video length
		assertEquals(4.024, videoObjectOutputList.get(0).getEnd(), 5);
	}


}