package com.spikeify.ffmpeg;

import com.spikeify.ffmpeg.builder.FFmpegBuilder;
import com.spikeify.ffmpeg.job.FFmpegJob;
import com.spikeify.ffmpeg.job.SinglePassFFmpegJob;
import com.spikeify.ffmpeg.job.TwoPassFFmpegJob;

import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class FFmpegExecutor {

	final FFmpeg ffmpeg;
	final FFprobe ffprobe;

	public FFmpegExecutor(FFmpeg ffmpeg, FFprobe ffprobe) {
		this.ffmpeg  = checkNotNull(ffmpeg);
		this.ffprobe = checkNotNull(ffprobe);
	}

	public FFmpegJob createJob(FFmpegBuilder builder) {
		// Single Pass
		final List<String> args = builder.build();
		return new SinglePassFFmpegJob(ffmpeg, args);
	}

	/**
	 * Info: https://trac.ffmpeg.org/wiki/x264EncodingGuide#twopass
	 * @param builder
	 * @return A new two-pass FFmpegJob
	 */
	public FFmpegJob createTwoPassJob(FFmpegBuilder builder) {

		// Random prefix so multiple runs don't clash
		String passlogPrefix = UUID.randomUUID().toString();

		// Two pass
		final boolean override = builder.getOverrideOutputFiles();

		final List<String> args1 = builder
			.setPass(1).setPassPrefix(passlogPrefix)
			.overrideOutputFiles(true)
			.build();

		final List<String> args2 = builder
			.setPass(2).setPassPrefix(passlogPrefix)
			.overrideOutputFiles(override)
			.build();

		return new TwoPassFFmpegJob(ffmpeg, args1, args2);
	}
}
