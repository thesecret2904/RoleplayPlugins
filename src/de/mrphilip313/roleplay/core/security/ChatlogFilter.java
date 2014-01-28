package de.mrphilip313.roleplay.core.security;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ChatlogFilter implements Filter{

	@Override
	public boolean isLoggable(LogRecord record) {
		if(record.getMessage().contains("issued server command:")){
			return false;
		}
		return true;
	}

}
