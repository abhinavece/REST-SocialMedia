package heapdev.com.SocialMedia.Services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import heapdev.com.SocialMedia.Database.DatabaseClass;
import heapdev.com.SocialMedia.Exceptions.CustomizedException;
import heapdev.com.SocialMedia.Models.Message;

public class MessageService {

	public static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	private static Map<Long, Message> messageList = DatabaseClass.getMessages();

	public MessageService() {

		messageList.put(1L, new Message(1L, "Message-1", "abhinavece"));
		messageList.put(2L, new Message(2L, "Message-2", "ramesh.sachin"));
		messageList.put(3L, new Message(3L, "Message-3", "thakur.urvil"));
		messageList.put(4L, new Message(4L, "Message-4", "getme"));
		messageList.put(5L, new Message(5L, "Message-5", "freekyStudd"));
		messageList.put(6L, new Message(6L, "Message-6", "freekyStudd"));
	}

	public List<Message> getAllMessages() {
		try {
			logger.info("fetched all the messages");
			return new ArrayList<Message>(messageList.values());
		} catch (Exception e) {
			throw new CustomizedException("Exception occured");
		}
	}

	public Message getMessageWithId(Long id) {
		try {
			if (messageList.containsKey(id)) {
				logger.info("==> Message with id: " + id + " is available and fetched successfully.");
				return messageList.get(id);
			} else {
				logger.info("==> No id found in database, returning null !!");
				return null;
			}
		} catch (Exception e) {
			throw new CustomizedException("Exception throw !!");
		}
	}

	public List<Message> getAllMessagesByYear(int year) {
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message msg : messageList.values()) {
			cal.setTime(msg.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(msg);
			}
		}
		return messagesForYear;
	}

	public List<Message> getAllMessagesPaginated(int start, int size) {
		List<Message> messagesPaginated = new ArrayList<>(messageList.values());
		if (start + size > messagesPaginated.size())
			return new ArrayList<Message>(messagesPaginated);
		return messagesPaginated.subList(start, start + size);
	}

	public Message createMessage(Message message) {
		try {
			message.setId(messageList.size() + 1);
			messageList.put(message.getId(), message);
			logger.info("A new message has been created with ID: " + message.getId());
			return message;
		} catch (Exception e) {
			throw new CustomizedException("Exception throw !!");
		}
	}

	public Message updateMessage(Message message) {
		try {
			if (messageList.containsKey(message.getId())) {
				messageList.put(message.getId(), message);
				return message;
			}
			return null;
		} catch (Exception e) {
			throw new CustomizedException("Exception throw");
		}
	}

	public Message deleteMessage(long id) {
		if (messageList.containsKey(id)) {
			return messageList.remove(id);
		} else {
			throw new CustomizedException("Exception throw: No data found");
		}
	}

}
