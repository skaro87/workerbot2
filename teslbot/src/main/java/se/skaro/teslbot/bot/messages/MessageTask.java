package se.skaro.teslbot.bot.messages;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TimerTask;

/**
 * The Class MessageTask. Used to poll messages from the MessageQueue
 */
public class MessageTask extends TimerTask{
	
	/** The message queue. */
	private Queue<Message> messageQueue;

	public MessageTask(){
		messageQueue = new LinkedList<Message>();
	}

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	public void run() {
		if (!messageQueue.isEmpty()){
			messageQueue.poll().sendMessage();
		}
	}
	
	/**
	 * Gets the message queue.
	 *
	 * @return the message queue
	 */
	public Queue<Message> getMessageQueue() {
		return messageQueue;
	}

	
	
	

}
