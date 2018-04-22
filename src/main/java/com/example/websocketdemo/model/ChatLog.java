package com.example.websocketdemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChatLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
	@Column(name="chat_message")
    private String chatMessage;
    @Column(name="user_id")
    private String userId;
    @Column(name="message_type")
    private String messageType;
    
    public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public ChatLog() {
    }
    public ChatLog(String chatMessage, String userId, String messageType) {
    	this.chatMessage = chatMessage;
        this.userId = userId;
        this.messageType = messageType;
    }
    //getters, setters, toString, hashCode, equals
    
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChatMessage() {
		return chatMessage;
	}
	public void setChatMessage(String chatMessage) {
		this.chatMessage = chatMessage;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}