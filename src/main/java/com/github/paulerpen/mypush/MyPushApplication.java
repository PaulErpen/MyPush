package com.github.paulerpen.mypush;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.paulerpen.mypush.user.User;
import com.github.paulerpen.mypush.user.UserRepository;

@SpringBootApplication
public class MyPushApplication {
	
	@Autowired
	private UserRepository repository;
	
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(MyPushApplication.class, args);
    }
    @PostConstruct
    public void setup() {
    	/*try {
    		System.out.println(System.getProperty("user.dir"));
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
    		
    		for (String l : content) {
				String[] tmp2 = l.split(" ");
				System.out.println("Saved User: "+tmp2[0]);
				repository.save(new User(tmp2[0],tmp2[1]));
			}
    	}catch (FileNotFoundException e) {
    		e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		// fetch all user
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (User user : repository.findAll()) {
			System.out.println(user.toString());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
			String result = encoder.encode(user.getPassword());
			System.out.println(result);
		}
    }

}
