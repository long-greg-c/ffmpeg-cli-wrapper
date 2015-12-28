# FFmpeg Java
by Andrew Brampton 2013-2014, upgraded dependencies and added additional features by Dean Gostiša and Roman Orač in 2015.

A fluent interface to running FFmpeg from Java.
[GitHub](https://github.com/bramp/ffmpeg-cli-wrapper) | [API docs](https://bramp.github.io/ffmpeg-cli-wrapper/apidocs/index.html)


##Setup
    <dependency>
        <groupId>com.spikeify</groupId>
        <artifactId>ffmpeg</artifactId>
        <version>check for latest version</version>
    </dependency>

##Usage
    FFmpeg ffmpeg = new FFmpeg("/path/to/ffmpeg");
	FFprobe ffprobe = new FFprobe("/path/to/ffprobe");

    FFmpegBuilder builder = new FFmpegBuilder()
    	.setInput(in)
    	.overrideOutputFiles(true)
    	.addOutput("output.mp4")
	        .setFormat("mp4")
	        .setTargetSize(250000)
	        .disableSubtitle()
	        .setAudioChannels(1)
	        .setAudioCodec("libfdk_aac")
	        .setAudioRate(48000)
	        .setAudioBitrate(32768)
	        .setVideoCodec("libx264")
	        .setVideoFramerate(Fraction.getFraction(24, 1))
	        .setVideoResolution(640, 480)
	        .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
	        .done();

	FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
	executor.createTwoPassJob(builder).run();


## Requirements 
Install static build ffmpeg to use all added features.

## Added features
We added support for (check examples in unit tests):

 - support for additional transcoding parameters,
 - create a thumbnail from input video,
 - get a dominant color of created thumbnail,
 - stitch multiple videos to a single video,
 - add fade in, fade out, text, textbox and video overlays to stitched video. 