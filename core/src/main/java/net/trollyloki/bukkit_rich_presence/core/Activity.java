package net.trollyloki.bukkit_rich_presence.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Instant;

public class Activity {

	private Type type;
	private String details, state;
	private String largeImage, largeImageText;
	private String smallImage, smallImageText;
	private Instant start, end;
	private String partyId;
	private int partySize, partyMaxSize;
	private String joinSecret, matchSecret, spectateSecret;

	public Activity(Type type, String details, String state, String largeImage, String largeImageText,
			String smallImage, String smallImageText, Instant start, Instant end, String partyId, int partySize,
			int partyMaxSize, String joinSecret, String matchSecret, String spectateSecret) {
		this.type = type;
		this.details = details;
		this.state = state;
		this.largeImage = largeImage;
		this.largeImageText = largeImageText;
		this.smallImage = smallImage;
		this.smallImageText = smallImageText;
		this.start = start;
		this.end = end;
		this.partyId = partyId;
		this.partySize = partySize;
		this.partyMaxSize = partyMaxSize;
		this.joinSecret = joinSecret;
		this.matchSecret = matchSecret;
		this.spectateSecret = spectateSecret;
	}

	public Activity(String details, String state, String largeImage, String largeImageText, String smallImage,
			String smallImageText, Instant start, Instant end, String partyId, int partySize, int partyMaxSize,
			String joinSecret, String matchSecret, String spectateSecret) {
		this.details = details;
		this.state = state;
		this.largeImage = largeImage;
		this.largeImageText = largeImageText;
		this.smallImage = smallImage;
		this.smallImageText = smallImageText;
		this.start = start;
		this.end = end;
		this.partyId = partyId;
		this.partySize = partySize;
		this.partyMaxSize = partyMaxSize;
		this.joinSecret = joinSecret;
		this.matchSecret = matchSecret;
		this.spectateSecret = spectateSecret;
	}

	public Activity(String details, String state, String largeImage, String largeImageText, String smallImage,
			String smallImageText, Instant start, Instant end, String partyId, int partySize, int partyMaxSize) {
		this.details = details;
		this.state = state;
		this.largeImage = largeImage;
		this.largeImageText = largeImageText;
		this.smallImage = smallImage;
		this.smallImageText = smallImageText;
		this.start = start;
		this.end = end;
		this.partyId = partyId;
		this.partySize = partySize;
		this.partyMaxSize = partyMaxSize;
	}

	public Activity(String details, String state, String largeImage, String largeImageText, String smallImage,
			String smallImageText, Instant start, Instant end) {
		this.details = details;
		this.state = state;
		this.largeImage = largeImage;
		this.largeImageText = largeImageText;
		this.smallImage = smallImage;
		this.smallImageText = smallImageText;
		this.start = start;
		this.end = end;
	}

	public Activity(String details, String state, String largeImage, String largeImageText, String smallImage,
			String smallImageText, Instant start) {
		this.details = details;
		this.state = state;
		this.largeImage = largeImage;
		this.largeImageText = largeImageText;
		this.smallImage = smallImage;
		this.smallImageText = smallImageText;
		this.start = start;
	}

	public Activity(String details, String state, String largeImage, String largeImageText, String smallImage,
			String smallImageText) {
		this.details = details;
		this.state = state;
		this.largeImage = largeImage;
		this.largeImageText = largeImageText;
		this.smallImage = smallImage;
		this.smallImageText = smallImageText;
	}

	public Activity(String details, String state, String largeImage, String largeImageText) {
		this.details = details;
		this.state = state;
		this.largeImage = largeImage;
		this.largeImageText = largeImageText;
	}

	public Activity(String details, String state) {
		this.details = details;
		this.state = state;
	}

	public Activity(byte[] data) throws IOException {
		try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(data))) {

			this.type = DataUtils.readEnum(in, Type.class);
			this.details = DataUtils.readString(in);
			this.state = DataUtils.readString(in);

			this.largeImage = DataUtils.readString(in);
			this.largeImageText = DataUtils.readString(in);
			this.smallImage = DataUtils.readString(in);
			this.smallImageText = DataUtils.readString(in);

			this.start = DataUtils.readInstant(in);
			this.end = DataUtils.readInstant(in);

			this.partyId = DataUtils.readString(in);
			this.partySize = in.readInt();
			this.partyMaxSize = in.readInt();

			this.joinSecret = DataUtils.readString(in);
			this.matchSecret = DataUtils.readString(in);
			this.spectateSecret = DataUtils.readString(in);

		}
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	public String getLargeImageText() {
		return largeImageText;
	}

	public void setLargeImageText(String largeImageText) {
		this.largeImageText = largeImageText;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getSmallImageText() {
		return smallImageText;
	}

	public void setSmallImageText(String smallImageText) {
		this.smallImageText = smallImageText;
	}

	public Instant getStart() {
		return start;
	}

	public void setStart(Instant start) {
		this.start = start;
	}

	public Instant getEnd() {
		return end;
	}

	public void setEnd(Instant end) {
		this.end = end;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public int getPartySize() {
		return partySize;
	}

	public void setPartySize(int partySize) {
		this.partySize = partySize;
	}

	public int getPartyMaxSize() {
		return partyMaxSize;
	}

	public void setPartyMaxSize(int partyMaxSize) {
		this.partyMaxSize = partyMaxSize;
	}

	public String getJoinSecret() {
		return joinSecret;
	}

	public void setJoinSecret(String joinSecret) {
		this.joinSecret = joinSecret;
	}

	public String getMatchSecret() {
		return matchSecret;
	}

	public void setMatchSecret(String matchSecret) {
		this.matchSecret = matchSecret;
	}

	public String getSpectateSecret() {
		return spectateSecret;
	}

	public void setSpectateSecret(String spectateSecret) {
		this.spectateSecret = spectateSecret;
	}

	public byte[] serialize() throws IOException {
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(stream)) {

			DataUtils.writeEnum(out, type);
			DataUtils.writeString(out, details);
			DataUtils.writeString(out, state);

			DataUtils.writeString(out, largeImage);
			DataUtils.writeString(out, largeImageText);
			DataUtils.writeString(out, smallImage);
			DataUtils.writeString(out, smallImageText);

			DataUtils.writeInstant(out, start);
			DataUtils.writeInstant(out, end);

			DataUtils.writeString(out, partyId);
			out.writeInt(partySize);
			out.writeInt(partyMaxSize);

			DataUtils.writeString(out, joinSecret);
			DataUtils.writeString(out, matchSecret);
			DataUtils.writeString(out, spectateSecret);

			return stream.toByteArray();
		}
	}

	@Override
	public String toString() {
		return "Activity [type=" + type + ", details=" + details + ", state=" + state + ", largeImage=" + largeImage
				+ ", largeImageText=" + largeImageText + ", smallImage=" + smallImage + ", smallImageText="
				+ smallImageText + ", start=" + start + ", end=" + end + ", partyId=" + partyId + ", partySize="
				+ partySize + ", partyMaxSize=" + partyMaxSize + ", joinSecret=" + joinSecret + ", matchSecret="
				+ matchSecret + ", spectateSecret=" + spectateSecret + "]";
	}

	public static enum Type {
		PLAYING, STREAMING, LISTENING, WATCHING
	}

}
