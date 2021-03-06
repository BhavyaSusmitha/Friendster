package com.app.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.DAO.FriendDao;
import com.app.DAO.UserDao;
import com.app.Models.ErrorClazz;
import com.app.Models.Friend;
import com.app.Models.User;


@RestController
public class FriendController {
	public FriendController(){
		System.out.println("Friend Controller bean is created");
	}
	
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private UserDao userDao;
	@RequestMapping(value="/suggestedusers",method=RequestMethod.GET)
public ResponseEntity<?> getSuggestedUsers(HttpSession session){
	String email=(String)session.getAttribute("email"); 
	if(email==null){
		ErrorClazz errorClazz=new ErrorClazz(7,"Unauthorized access..please login");
	return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
	}
		
	List<User> suggestedUsers=friendDao.getSuggestedUsers(email);
return new ResponseEntity<List<User>>(suggestedUsers,HttpStatus.OK);
}
	@RequestMapping(value="/addfriend",method=RequestMethod.POST)
	public ResponseEntity<?> addFriend(@RequestBody User toIdValue,HttpSession session){
		String email=(String)session.getAttribute("email"); 
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(7,"Unauthorized access..please login");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User fromId=userDao.getUser(email);
		Friend friend=new Friend();
		friend.setFromId(fromId);
		friend.setToId(toIdValue);
		friend.setStatus('P');
		friendDao.addFriend(friend);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@RequestMapping(value="/pendingrequests",method=RequestMethod.GET)
 	public ResponseEntity<?> getPendingRequests(HttpSession session){
		String email=(String)session.getAttribute("email"); 
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(7,"Unauthorized access..please login");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		List<Friend> pendingRequests=friendDao.pendingRequests(email);
		return new ResponseEntity<List<Friend>>(pendingRequests,HttpStatus.OK);
	}
	@RequestMapping(value="/updatestatus",method=RequestMethod.PUT)
	public ResponseEntity<?> updateStatus(@RequestBody Friend friendRequest,HttpSession session){
		String email=(String)session.getAttribute("email"); 
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(7,"Unauthorized access..please login");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		friendDao.updateStatus(friendRequest);
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
}
