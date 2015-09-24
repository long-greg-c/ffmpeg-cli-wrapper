package com.spikeify.ffmpeg.builder;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.spikeify.ffmpeg.modelmapper.Mapper;
import com.spikeify.ffmpeg.options.AudioEncodingOptions;
import com.spikeify.ffmpeg.options.EncodingOptions;
import com.spikeify.ffmpeg.options.MainEncodingOptions;
import com.spikeify.ffmpeg.options.VideoEncodingOptions;
import com.spikeify.ffmpeg.probe.FFmpegProbeResult;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.math.Fraction;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builds a representation of a single output/encoding setting
 */
public class FFmpegOutputBuilder implements Cloneable {

	final private static String DEVNULL = SystemUtils.IS_OS_WINDOWS ? "NUL" : "/dev/null";

	FFmpegBuilder parent;

	/**
	 * Output filename
	 */
	public String filename;

	public String format;

	public Long startOffset; // in millis
	public Long duration; // in millis
	public String preset;
	public boolean save_pass1; //save pass to a file instead of /dev/null

	public boolean audio_enabled = true;
	public String audio_codec;
	public int audio_channels;
	public int audio_sample_rate;
	public String audio_bit_depth;
	public int audio_bit_rate;
	public int audio_quality;
	public String map_metadata;
	public String audio_write_xing;

	public boolean video_enabled = true;
	public String video_codec;
	public Fraction video_frame_rate;
	public int video_width;
	public int video_height;
	public int video_bit_rate;
	public Integer video_frames;
	public String video_preset;
	public String video_filter;
	public int video_constant_rate_factor;
	public String video_tune;
	public String video_profile_v;
	public boolean video_fast_start;

	public boolean subtitle_enabled = true;

	public FFmpegBuilder.Strict strict = FFmpegBuilder.Strict.NORMAL;

	public long targetSize = 0; // in bytes
	public int pass_padding_bitrate = 1024; // in bits per second

	public boolean throwWarnings = true;
	public Integer loop;

	public FFmpegOutputBuilder() {
	}

	protected FFmpegOutputBuilder(FFmpegBuilder parent, String filename) {
		this.parent = parent;
		this.filename = filename;
	}

	public FFmpegOutputBuilder useOptions(EncodingOptions opts) {
		Mapper.map(opts, this);
		return this;
	}

	public FFmpegOutputBuilder useOptions(MainEncodingOptions opts) {
		Mapper.map(opts, this);
		return this;
	}

	public FFmpegOutputBuilder useOptions(AudioEncodingOptions opts) {
		Mapper.map(opts, this);
		return this;
	}

	public FFmpegOutputBuilder useOptions(VideoEncodingOptions opts) {
		Mapper.map(opts, this);
		return this;
	}

	public FFmpegOutputBuilder disableVideo() {
		this.video_enabled = false;
		return this;
	}

	public FFmpegOutputBuilder disableAudio() {
		this.audio_enabled = false;
		return this;
	}

	public FFmpegOutputBuilder disableSubtitle() {
		this.subtitle_enabled = false;
		return this;
	}

	public FFmpegOutputBuilder setFilename(String filename) {
		this.filename = checkNotNull(filename);
		return this;
	}

	/**
	 * set pass1 to a output-pass1.ext file instead of /dev/null
	 */
	public FFmpegOutputBuilder setSavePass1(boolean setSavePass1) {
		this.save_pass1 = setSavePass1;
		return this;
	}

	public String getFilename() {
		return filename;
	}


	/**
	 * A preset is a collection of options that will provide a certain encoding speed to compression ratio.
	 * A slower preset will provide better compression (compression is quality per filesize).
	 * This means that, for example, if you target a certain file size or constant bit rate, you will achieve better quality with a slower preset.
	 * Similarly, for constant quality encoding, you will simply save bitrate by choosing a slower preset.
	 *
	 * @param preset ultrafast,superfast, veryfast, faster, fast, medium, slow, slower, veryslow, placebo
	 * @return FFmpegOutputBuilder
	 */
	public FFmpegOutputBuilder setPreset(String preset) {
		this.preset = checkNotNull(preset);
		return this;
	}

	public FFmpegOutputBuilder setFormat(String format) {
		this.format = checkNotNull(format);
		return this;
	}

	public FFmpegOutputBuilder setVideoBitRate(int bit_rate) {
		if (bit_rate > 0) {
			this.video_enabled = true;
			this.video_bit_rate = bit_rate;
		}
		return this;
	}

	public FFmpegOutputBuilder setVideoCodec(String codec) {
		if (codec != null) {
			this.video_enabled = true;
			this.video_codec = codec;
		}
		return this;
	}

	public FFmpegOutputBuilder setVideoFrameRate(Fraction frame_rate) {
		if (frame_rate != null) {
			this.video_enabled = true;
			this.video_frame_rate = frame_rate;
		}
		return this;
	}

	public FFmpegOutputBuilder setVideoFrameRate(double frame_rate) {
		return setVideoFrameRate(Fraction.getFraction(frame_rate));
	}

	/**
	 * Set the number of video frames to record.
	 *
	 * @param frames
	 * @return this
	 */
	public FFmpegOutputBuilder setFrames(int frames) {
		this.video_enabled = true;
		this.video_frames = frames;
		return this;
	}

	public FFmpegOutputBuilder setVideoPreset(String preset) {
		this.video_enabled = true;
		this.video_preset = checkNotNull(preset);
		return this;
	}

	protected static boolean isValidSize(int widthOrHeight) {
		return widthOrHeight > 0 || widthOrHeight == -1;
	}

	public FFmpegOutputBuilder setVideoWidth(int width) {
		Preconditions.checkArgument(isValidSize(width), "Width must be valid greater than 0");

		this.video_enabled = true;
		this.video_width = width;
		return this;
	}

	public FFmpegOutputBuilder setVideoHeight(int height) {
		if (isValidSize(height)) {
			this.video_enabled = true;
			this.video_height = height;
		}
		return this;
	}

	public FFmpegOutputBuilder setVideoResolution(int width, int height) {
		if (isValidSize(width)) {
			this.video_enabled = true;
			this.video_width = width;
			this.video_height = height;
		}
		return this;
	}

	/**
	 * Sets Video Filter
	 * TODO Build a fluent Filter builder
	 *
	 * @param filter
	 * @return this
	 */
	public FFmpegOutputBuilder setVideoFilter(String filter) {
		this.video_enabled = true;
		this.video_filter = checkNotNull(filter);
		return this;
	}

	/**
	 * This method allows the encoder to attempt to achieve a certain output quality for the whole file when output file size is of less importance.
	 * This provides maximum compression efficiency with a single pass.
	 * Each frame gets the bitrate it needs to keep the requested quality level.
	 * The downside is that you can't tell it to get a specific filesize or not go over a specific size or bitrate.
	 */
	public FFmpegOutputBuilder setConstantRateFactor(int contantRateFactor) {
		if (contantRateFactor > 0) {
			this.video_constant_rate_factor = contantRateFactor;
		}
		return this;
	}

	/**
	 * You can optionally use -video_tune to change settings based upon the specifics of your input.
	 * For example, if your input is animation then use the animation tuning, or if you want to preserve grain then use the grain tuning.
	 *
	 * @param video_tune film, animation, grain, stillimage, psnr, ssim, fastdecode, zerolatency
	 * @return FFmpegOutputBuilder
	 */
	public FFmpegOutputBuilder setVideoTune(String video_tune) {
		this.video_tune = checkNotNull(video_tune);
		return this;

	}

	/**
	 * Another optional setting is -profile:v which will limit the output to a specific H.264 profile.
	 * This can generally be omitted unless the target device only supports a certain profile (see Compatibility).
	 * Note that usage of -profile:v is incompatible with lossless encoding.
	 *
	 * @param profileV baseline, main, high, high10, high422, high444.
	 * @return FFmpegOutputBuilder
	 */
	public FFmpegOutputBuilder setVideoProfile(String profileV) {
		this.video_profile_v = checkNotNull(profileV);
		return this;
	}

	/**
	 * You can add -movflags +faststart as an output option if your videos are going to be viewed in a browser.
	 * This will move some information to the beginning of your file and allow the video to begin playing before it is completely downloaded by the viewer.
	 * It is not required if you are going to use a video service such as YouTube.
	 */
	public FFmpegOutputBuilder enableVideoFastStart() {
		this.video_fast_start = true;
		return this;
	}

	public FFmpegOutputBuilder setAudioCodec(String codec) {
		if (codec != null) {
			this.audio_enabled = true;
			this.audio_codec = codec;
		}
		return this;
	}

	public FFmpegOutputBuilder setAudioChannels(int channels) {
		if (channels > 0) {
			this.audio_enabled = true;
			this.audio_channels = channels;
		}
		return this;
	}

	/**
	 * Sets the Audio Sample Rate, for example 440000
	 *
	 * @param sample_rate
	 * @return this
	 */
	public FFmpegOutputBuilder setAudioSampleRate(int sample_rate) {
		if (sample_rate > 0) {
			this.audio_enabled = true;
			this.audio_sample_rate = sample_rate;
		}
		return this;
	}

	/**
	 * Sets the Audio Bit Depth. Samples given in the FFmpeg.AUDIO_DEPTH_* constants.
	 *
	 * @param bit_depth
	 * @return this
	 */
	public FFmpegOutputBuilder setAudioBitDepth(String bit_depth) {
		if (bit_depth != null) {
			this.audio_enabled = true;
			this.audio_bit_depth = bit_depth;
		}
		return this;
	}


	/**
	 * Sets the Audio bitrate
	 *
	 * @param bit_rate
	 * @return this
	 */
	public FFmpegOutputBuilder setAudioBitRate(int bit_rate) {
		if (bit_rate > 0) {
			this.audio_enabled = true;
			this.audio_bit_rate = bit_rate;
		}
		return this;
	}

	/**
	 * Set flag for disable  audio metadata
	 *
	 * @return this
	 */
	public FFmpegOutputBuilder disableAudioMetadata() {
		this.audio_enabled = true;
		this.map_metadata = "-1";
		return this;
	}

	/**
	 * Set flag for disable audio length in metadata
	 * This is sometimes needed so that song length is detected correctly on Apple devices (OSX, IOS,...)
	 *
	 * @return this
	 */
	public FFmpegOutputBuilder disableAudioLengthInMetadata() {
		this.audio_enabled = true;
		this.audio_write_xing = "0";
		return this;
	}

	public FFmpegOutputBuilder setAudioQuality(int quality) {
		if (quality >= 1 && quality <= 5) {
			this.audio_enabled = true;
			this.audio_quality = quality;
		}
		return this;
	}

	/**
	 * Target output file size (in bytes)
	 *
	 * @param targetSize
	 * @return this
	 */
	public FFmpegOutputBuilder setTargetSize(long targetSize) {
		if (targetSize > 0) {
			this.targetSize = targetSize;
		}
		return this;
	}

	/**
	 * Decodes but discards input until the duration
	 *
	 * @param duration
	 * @param units
	 * @return this
	 */
	public FFmpegOutputBuilder setStartOffset(long duration, TimeUnit units) {
		if (units != null) {
			this.startOffset = units.toMillis(duration);
		}

		return this;
	}

	/**
	 * Stop writing the output after its duration reaches duration
	 *
	 * @param duration
	 * @param units
	 * @return this
	 */
	public FFmpegOutputBuilder setDuration(long duration, TimeUnit units) {
		checkNotNull(duration);
		checkNotNull(units);

		this.duration = units.toMillis(duration);

		return this;
	}

	public FFmpegOutputBuilder setStrict(FFmpegBuilder.Strict strict) {
		this.strict = checkNotNull(strict);
		return this;
	}

	/**
	 * When doing multi-pass we add a little extra padding, to ensure we reach our target
	 *
	 * @param bitrate
	 * @return this
	 */
	public FFmpegOutputBuilder setPassPaddingBitrate(int bitrate) {
		Preconditions.checkArgument(bitrate > 0);
		this.pass_padding_bitrate = bitrate;
		return this;
	}

	public FFmpegOutputBuilder setLoop(int loop) {
		Preconditions.checkArgument(loop >= 0);
		this.loop = loop;
		return this;
	}

	/**
	 * Finished with this output
	 *
	 * @return the parent FFmpegBuilder
	 */
	public FFmpegBuilder done() {
		Preconditions.checkState(parent != null, "Can not call done without parent being set");
		return parent;
	}

	public EncodingOptions buildOptions() {
		// TODO When/if modelmapper supports @ConstructorProperties, we map this object, instead of doing new XXX(...)
		//     https://github.com/jhalterman/modelmapper/issues/44
		return new EncodingOptions(
						new MainEncodingOptions(format, startOffset, duration),
						new AudioEncodingOptions(audio_enabled, audio_codec, audio_channels, audio_sample_rate, audio_bit_depth, audio_bit_rate, audio_quality),
						new VideoEncodingOptions(video_enabled, video_codec, video_frame_rate, video_width, video_height, video_bit_rate, video_frames, video_filter, video_preset)
		);
	}

	protected List<String> build(int pass) {
		Preconditions.checkState(parent != null, "Can not build without parent being set");

		ImmutableList.Builder<String> args = new ImmutableList.Builder<String>();

		if (targetSize > 0) {
			FFmpegProbeResult input = parent.inputProbe;
			if (input == null) {
				throw new IllegalStateException("Can not set target size, without using setInput(FFmpegProbeResult)");
			}

			// TODO factor in start time and/or number of frames

			double durationInSeconds = input.format.duration;
			int totalBitRate = (int) Math.floor((targetSize * 8) / durationInSeconds) - pass_padding_bitrate;

			// TODO Calculate audioBitRate

			if (video_enabled && video_bit_rate == 0) {
				// Video (and possibly audio)
				int audioBitRate = audio_enabled ? audio_bit_rate : 0;
				video_bit_rate = totalBitRate - audioBitRate;
			} else if (audio_enabled && audio_bit_rate == 0) {
				// Just Audio
				audio_bit_rate = totalBitRate;
			}
		}

		if (strict != FFmpegBuilder.Strict.NORMAL) {
			args.add("-strict").add(strict.toString().toLowerCase());
		}

		if (!Strings.isNullOrEmpty(format)) {
			args.add("-f").add(format);
		}

		if (startOffset != null) {
			// TODO Consider formatting into "hh:mm:ss[.xxx]"
			args.add("-ss").add(String.format("%.3f", startOffset / 1000f));
		}

		if (duration != null) {
			// TODO Consider formatting into "hh:mm:ss[.xxx]"
			args.add("-t").add(String.format("%.3f", duration / 1000f));
		}

		if (!Strings.isNullOrEmpty(preset)) {
			args.add("-preset").add(preset);
		}

		if (video_enabled) {

			if (!Strings.isNullOrEmpty(video_codec)) {
				args.add("-vcodec").add(video_codec);
			}

			if (video_width != 0 && video_height != 0) {
				args.add("-s").add(String.format("%dx%d", video_width, video_height));
			}

			if (video_frame_rate != null) {
				//args.add("-r").add(String.format("%2f", video_frame_rate));
				args.add("-r").add(video_frame_rate.toString());
			}

			if (video_bit_rate > 0) {
				args.add("-b:v").add(String.format("%dk", video_bit_rate));
			}

			if (!Strings.isNullOrEmpty(video_preset)) {
				args.add("-vpre").add(video_preset);
			}

			if (!Strings.isNullOrEmpty(video_filter)) {
				args.add("-vf").add(video_filter);
			}

			if (video_constant_rate_factor > 0) {
				args.add("-crf").add(String.format("%d", video_constant_rate_factor));
			}

			if (!Strings.isNullOrEmpty(video_tune)) {
				args.add("-tune").add(video_tune);
			}

			if (!Strings.isNullOrEmpty(video_profile_v)) {
				args.add("-profile:v").add(video_profile_v);
			}

			if (video_fast_start) {
				args.add("-movflags").add("+faststart");
			}


		} else {
			args.add("-vn");
		}

		if (audio_enabled && pass != 1) {
			if (!Strings.isNullOrEmpty(audio_codec)) {
				args.add("-acodec").add(audio_codec);
			}

			if (audio_channels > 0) {
				args.add("-ac").add(String.format("%d", audio_channels));
			}

			if (audio_sample_rate > 0) {
				args.add("-ar").add(String.format("%d", audio_sample_rate));
			}

			if (!Strings.isNullOrEmpty(audio_bit_depth)) {
				args.add("-sample_fmt").add(audio_bit_depth);
			}

			if (!Strings.isNullOrEmpty(map_metadata)) {
				args.add("-map_metadata").add(map_metadata);
			}
			if (!Strings.isNullOrEmpty(audio_write_xing)) {
				args.add("-write_xing").add(audio_write_xing);
			}

			if (audio_bit_rate > 0 && audio_quality > 0 && throwWarnings) {
				// I'm not sure, but it seems audio_quality overrides audio_bit_rate, so don't allow both
				throw new IllegalStateException("Only one of audio_bit_rate and audio_quality can be set");
			}

			if (audio_bit_rate > 0) {
				args.add("-b:a").add(String.format("%dk", audio_bit_rate));
			}

			if (audio_quality > 0) {
				args.add("-aq").add(String.format("%d", audio_quality));
			}

		} else {
			args.add("-an");
		}

		if (!subtitle_enabled)
			args.add("-sn");

		if (loop != null)
			args.add("-loop").add(String.valueOf(loop));

		// Output
		if (pass == 1) {
			if (save_pass1) {
				String filenameWithoutExt = Files.getNameWithoutExtension(filename);
				String ext = Files.getFileExtension(filename);
				args.add(filename.replace(filenameWithoutExt + "." + ext, filenameWithoutExt + "-pass1." + ext));
			} else {
				args.add(DEVNULL);
			}
		} else {
			args.add(filename);
		}

		return args.build();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}