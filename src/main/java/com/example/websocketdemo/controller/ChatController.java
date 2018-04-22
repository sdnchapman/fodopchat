package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatLog;
import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.model.ChatMessage.MessageType;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
import com.example.websocketdemo.dao.ChatLogRepository;


@Controller
public class ChatController {
	
	public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

	@Autowired
	private ChatLogRepository chatLogRepository;
	
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Iterable<ChatMessage> sendMessage(@Payload ChatMessage chatMessage) {
        
    	ChatLog cLog = new ChatLog(chatMessage.getContent(), chatMessage.getSender(), chatMessage.getType().name());
    	if(chatLogRepository.count() >= 150)
    	{
    		ArrayList<ChatLog> cclog = (ArrayList<ChatLog>) chatLogRepository.findAll();
    		chatLogRepository.delete(cclog.get(0));
    	}
    	chatLogRepository.save(cLog);
    	ArrayList<ChatMessage> cMessage = new ArrayList<ChatMessage>();
    	cMessage.add(chatMessage);
    	return cMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Iterable<ChatMessage> addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        
        ArrayList<ChatLog> chatLog = (ArrayList<ChatLog>) chatLogRepository.findAll();
        ChatLog cLog = new ChatLog(chatMessage.getContent(), chatMessage.getSender(), chatMessage.getType().name());
    	chatLogRepository.save(cLog);
    	ArrayList <ChatMessage> prevMessages = new ArrayList<ChatMessage>();
    	if(chatLog.size() >= 150)
    	{
    		chatLogRepository.delete(chatLog.get(0));
    		chatLog.remove(0);
    	}
		
    	
    	for(ChatLog c: chatLog)
    	{
    		String cMessage;
    		String cUser;
    		String cType;
    		if(c.getChatMessage() == null)
    		{
    			cMessage = "";
    		}
    		else
    		{
    			cMessage = c.getChatMessage();
    		}
    		
    		if(c.getUserId() == null)
    		{
    			cUser = "Unknown";
    		}
    		else
    		{
    			cUser = c.getUserId();
    		}
    		
    		if(c.getMessageType() == null)
    		{
    			cType = "CHAT";
    		}
    		else
    		{
    			cType = c.getMessageType();
    		}
    		
    		ChatMessage tempMessage = new ChatMessage();
    		tempMessage.setContent(cMessage);
    		tempMessage.setSender(cUser);
    		
    		    	if(cType.equals("CHAT"))
    		    	{
    		    		tempMessage.setType(ChatMessage.MessageType.CHAT);
    		    	}
    		    	else if(cType.equals("JOIN"))
    		    	{
    		    		tempMessage.setType(ChatMessage.MessageType.JOIN);
    		    	}
    		    	else if(cType.equals("LEAVE"))
    		    	{
    		    		tempMessage.setType(ChatMessage.MessageType.LEAVE);
    		    	}
    		
    		prevMessages.add(tempMessage);
    	}
    	
    	prevMessages.add(chatMessage);
        return prevMessages;
    }

}
