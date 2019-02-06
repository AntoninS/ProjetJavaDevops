package Model.common;

import java.io.Serializable;

public class Message implements Serializable{
	
	String sender;
	String content;
	int id;
	
	public Message(String sender, String content)
	{
		this.sender = sender;
		this.content = content;
	}
	
	public String toString()
	{
		String res = this.content;
		
		return res;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}

}
