package net.trollyloki.bukkit_rich_presence.core;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;

public class DataUtils {

	public static void writeString(DataOutput out, String string) throws IOException {
		if (string == null) {
			out.writeInt(-1);
		} else {
			out.writeInt(string.length());
			for (int i = 0; i < string.length(); i++)
				out.writeChar(string.charAt(i));
		}
	}

	public static String readString(DataInput in) throws IOException {
		int length = in.readInt();
		if (length == -1) {
			return null;
		} else {
			String string = "";
			for (int i = 0; i < length; i++)
				string += in.readChar();
			return string;
		}
	}

	public static <T extends Enum<T>> void writeEnum(DataOutput out, T value) throws IOException {
		if (value == null) {
			writeString(out, null);
		} else {
			writeString(out, value.name());
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T readEnum(DataInput in, Class<T> clazz) throws IOException {
		String string = readString(in);
		if (string == null) {
			return null;
		} else {
			try {
				return (T) clazz.getDeclaredMethod("valueOf", String.class).invoke(null, string);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static void writeInstant(DataOutput out, Instant instant) throws IOException {
		if (instant == null) {
			out.writeLong(-1);
		} else {
			out.writeLong(instant.getEpochSecond());
			out.writeLong(instant.getNano());
		}
	}

	public static Instant readInstant(DataInput in) throws IOException {
		long epochSecond = in.readLong();
		if (epochSecond == -1) {
			return null;
		} else {
			long nanoAdjustment = in.readLong();
			return Instant.ofEpochSecond(epochSecond, nanoAdjustment);
		}
	}

}
