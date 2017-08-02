package heapdev.com.SocialMedia.Database;

import java.util.HashMap;
import java.util.Map;

import heapdev.com.SocialMedia.Models.Message;
import heapdev.com.SocialMedia.Models.Profile;

public class DatabaseClass {

	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();

	public static Map<Long, Message> getMessages() {
		return messages;
	}

	public static Map<String, Profile> getProfiles() {
		return profiles;
	}

}
