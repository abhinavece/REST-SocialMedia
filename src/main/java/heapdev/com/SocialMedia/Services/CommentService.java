package heapdev.com.SocialMedia.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import heapdev.com.SocialMedia.Database.DatabaseClass;
import heapdev.com.SocialMedia.Exceptions.CustomizedException;
import heapdev.com.SocialMedia.Models.Comment;
import heapdev.com.SocialMedia.Models.Message;

public class CommentService {

	public static final Logger logger = LoggerFactory.getLogger(CommentService.class);

	private static Map<Long, Message> messageList = DatabaseClass.getMessages();

	public CommentService() {

		Map<Long, Comment> commentMap = new HashMap<>();

		Comment c1 = new Comment(1l, "Comment from Abhinav Singh", "abhinavece");
		Comment c2 = new Comment(2l, "Comment from Viral Thakur", "thakur.urvil");
		Comment c3 = new Comment(3l, "Comment from Asgar Ali", "getme");
		Comment c4 = new Comment(4l, "Comment from Mohan Chandra", "freekyStudd");

		commentMap.put(c1.getId(), c1);
		commentMap.put(c2.getId(), c2);
		commentMap.put(c3.getId(), c3);
		commentMap.put(c4.getId(), c4);

		for (Message m : messageList.values()) {
			m.setComments(commentMap);
		}
	}

	public List<Comment> getAllComments(long messageId) {
		return new ArrayList<Comment>(messageList.get(messageId).getComments().values());
	}

	public Comment getCommentById(long messageId, long commentId) {
		try {
			if (messageList.containsKey(messageId)) {
				if (messageList.get(messageId).getComments().containsKey(commentId)) {
					logger.info("We have found MessageID-" + messageId + " and CommentID-" + commentId);
					return messageList.get(messageId).getComments().get(commentId);
				}
			}
			return null;
		} catch (Exception e) {
			throw new CustomizedException("Exception occured !!");
		}
	}

	public Comment createNewComment(Long messageId, Comment comment) {
		try {
			if (messageList.containsKey(messageId)) {
				comment.setId(messageList.get(messageId).getComments().size() + 1);
				messageList.get(messageId).getComments().put(comment.getId(), comment);
				// Map<Long, Comment> commentMap = new HashMap<>();
				// commentMap.put(comment.getId(), comment);
				// messageList.get(messageId).setComments(commentMap);
				return comment;
			}
			return null;
		} catch (Exception e) {
			throw new CustomizedException("Exception thrown !!!");
		}
	}

}
