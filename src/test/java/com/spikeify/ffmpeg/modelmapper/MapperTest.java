package com.spikeify.ffmpeg.modelmapper;

import com.spikeify.ffmpeg.builder.FFmpegOutputBuilder;
import com.spikeify.ffmpeg.options.EncodingOptions;
import com.spikeify.ffmpeg.options.AudioEncodingOptions;
import com.spikeify.ffmpeg.options.MainEncodingOptions;
import com.spikeify.ffmpeg.options.VideoEncodingOptions;

import org.junit.Assert;
import org.junit.Test;

public class MapperTest {

	@Test
	public void testMapping() {
		MainEncodingOptions main = new MainEncodingOptions("mp4", 0L, null);
		AudioEncodingOptions audio = new AudioEncodingOptions(false, null, 0, 0, null, 0, 0);
		VideoEncodingOptions video = new VideoEncodingOptions(true, null, null, 320, 0, 0, null, "scale='320:trunc(ow/a/2)*2'", "");
		
		EncodingOptions options = new EncodingOptions(main, audio, video);
		
		FFmpegOutputBuilder mappedObj = new FFmpegOutputBuilder();
		
		Mapper.map(options, mappedObj);
		
		Assert.assertNotNull(mappedObj);
	}
}
