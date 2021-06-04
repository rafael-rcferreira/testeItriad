package br.com.itriad.challenge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

	@Value("${application.name}")
	private	String	appName;
	
}
