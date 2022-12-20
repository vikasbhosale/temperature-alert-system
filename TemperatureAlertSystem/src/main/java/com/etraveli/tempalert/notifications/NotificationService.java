package com.etraveli.tempalert.notifications;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author VBhosale
 *
 */
@Component
@Slf4j
public class NotificationService {

	public void sendNotification(String message) {
		log.info(message);
	}
}
