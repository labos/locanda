package action;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.*;

import model.Extra;
import model.Room;
import model.RoomFacility;
import model.Structure;
import model.User;
import model.internal.Message;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;


import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class StructureAction extends ActionSupport implements SessionAware {
	
	private Map<String, Object> session = null;
	private Message message = new Message();
	

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	

	

}
