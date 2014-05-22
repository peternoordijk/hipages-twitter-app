package au.com.hipages.twitter.domain;

import javax.xml.bind.annotation.XmlRootElement;

import twitter4j.TwitterException;

@SuppressWarnings("serial")
public class HipagesException extends Exception {
	public HipagesException(TwitterException exception) {
		super(String.format("Exception connecting to Twitter: %s", exception.getMessage()));
	}
	
	public ErrorMessage getErrorMessage() {
		ErrorMessage message = new ErrorMessage();
		message.setError(getMessage());
		return message;
	}
	
	@XmlRootElement
	public class ErrorMessage {
		private String error;

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}
		
	}
}
