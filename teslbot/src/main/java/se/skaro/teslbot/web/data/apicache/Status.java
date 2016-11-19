package se.skaro.teslbot.web.data.apicache;

public abstract class Status {
	
	protected String ign;
	
	public abstract String getMessage();
	
	public abstract String sendMessage();
	
	protected abstract String replacePlaceholders(String message);
	
	public void setIgn(String ign){
		this.ign = ign;
	}
	
	
	
	

}
