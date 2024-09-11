package com.security.jpa.jwt.with_jpa.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.jpa.jwt.with_jpa.Admin;
import com.security.jpa.jwt.with_jpa.AdminRepository;
import com.security.jpa.jwt.with_jpa.MyUserDetailsServices;
import com.security.jpa.jwt.with_jpa.jwt.JwtUtil;

@RestController
@RequestMapping(path = "/api/admin")
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
	@Autowired
	private  MyUserDetailsServices userDetailsServices;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtToken;
	
	@Autowired
    public AdminController(BCryptPasswordEncoder passEncoder) {
        this.passEncoder = passEncoder;
    }

	
	@PostMapping("/add")
	public ResponseEntity<Map<String, String>> addAdmin(@RequestBody Admin admin){
		Map<String, String> map = new HashMap<String, String>();
			
		String encodePass = passEncoder.encode(admin.getPassword());
		
		admin.setPassword(encodePass);
		
		adminRepo.save(admin);
			
			
		map.put("success", "admin added in database");
		return ResponseEntity.ok(map);
		
		
	}
	
	@GetMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginAdimn login){
		 Map<String, String> response = new HashMap<>();

	        try {
	            // Perform authentication
	        	 authenticationManager.authenticate(
	                     new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
	                 );

	            // If authentication is successful, you can return a success message or token
	            response.put("status", "success");
	            response.put("message", "Login successful");
	            
	            final UserDetails userDetails = userDetailsServices.loadUserByUsername(login.getUsername());
	            
	            final String jwt = jwtToken.generateToken(userDetails);
	            response.put("jwt", jwt);

	            return ResponseEntity.ok(response);

	        } catch (AuthenticationException e) {
	            // Handle authentication failure
	            response.put("status", "error");
	            response.put("message", "Invalid username or password");

	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	        }
	}
	
	@GetMapping("/get")
	public List<Admin> getAllAdmins(){
		return adminRepo.findAll();
	}
	
}
