package com.github.paulerpen.mypush;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.paulerpen.mypush.user.User;
import com.github.paulerpen.mypush.user.UserRepository;

@Controller
public class SignUpController {
		
	@Autowired
	private Environment env;
	
    @Autowired
    private UserRepository userRepository;
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public void registerClient(@RequestParam(value="username", required=true) String username, 
			@RequestParam(value="password", required=true) String password, 
			@RequestParam(value="repeatPassword", required=true) String repeatPassword ,
			HttpServletResponse response,
			HttpSession session) throws IOException {
		
		System.out.println("Requesting SignUp for:");
		System.out.println(userIsNew(username));
		System.out.println(passwordValid(password));
		System.out.println(repeatPassword.equals(password));
		
		boolean userValid = true;
		if(userIsNew(username)) {
			session.setAttribute("nameTaken", "false");	
		}else {
			session.setAttribute("nameTaken", "nameTaken");
			userValid = false;
		}
		if(passwordValid(password)) {
			session.setAttribute("passwordToShort", "false");	
		}else {
			session.setAttribute("passwordToShort", "passwordToShort");
			userValid = false;
		}
		if(password.equals(repeatPassword)) {
			session.setAttribute("passwordNoMatch", "false");	
		}else {
			session.setAttribute("passwordNoMatch", "passwordNoMatch");
			userValid = false;
		}
		if(userValid) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
			String encryptedPassword = encoder.encode(password);
			userRepository.save(new User(username,encryptedPassword));
			//insertIntoUsersTxt(username, encryptedPassword);
			System.out.println("Saved new User: "+username+" Password: "+encryptedPassword);
			response.sendRedirect("/login");
		}else{
			response.sendRedirect("/signup");
		}
		
	}
	public boolean userIsNew(String username) {
		if(userRepository.findByUsername(username)==null) {
			return true;
		}
		return false;
	}
	public boolean passwordValid(String password) {
		return password.length() > 5;
	}
	public boolean insertIntoUsersTxt(String username, String encryptedPassword) {
		try {
			//Copy old users.txt
			FileReader fr = new FileReader(env.getProperty("mypush.paths.userdata"));
			BufferedReader br = new BufferedReader(fr);
			LinkedList<String> content = new LinkedList<String>();
			String tmp = br.readLine();
			while(tmp!=null) {
				content.add(tmp);
				tmp = br.readLine();
			}
			br.close();
			fr.close();
			
			//Rewrite file
			FileWriter fw = new FileWriter(env.getProperty("mypush.paths.userdata"));
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i = 0; i < content.size(); i++) {
				bw.write(content.get(i)+"\n");
			}
			bw.write(username+" "+encryptedPassword);
			bw.close();
			fw.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
