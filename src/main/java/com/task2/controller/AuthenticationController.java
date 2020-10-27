package com.task2.controller;

import com.task2.authencation.JwtTokenUtil;
import com.task2.model.*;
import com.task2.authencation.JwtUserDetailsService;
import com.task2.repository.UserRepository;
import com.task2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;


	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	//	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		System.out.println(authenticationRequest.getPassword());
		System.out.println(authenticationRequest.getUsername());
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		HashMap object = new HashMap();
//		object.put("token", String.valueOf(new JwtResponse(token)));
//		object.put("username", userDetails.getUsername());
//		object.put("Authorization", userDetails.getAuthorities().toString());

//		return ResponseEntity.ok(new JwtResponse(token));
		User user = userRepository.findByUsername(userDetails.getUsername());
		object.put("user",user);
		object.put("token",token);
		return ResponseEntity.ok(object);
	}

	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody UserDto user) {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			throw new Exception("ABCDEF");
		}
	}
	@PostMapping("/children/register")
	public ResponseEntity<?> saveChildrenUser(@RequestBody UserDto user) {
		String parent_name = SecurityContextHolder.getContext().getAuthentication().getName();
		String parent_role = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get());
		return ResponseEntity.ok(userDetailsService.childrenSave(user,parent_name, parent_role));
	}



	@PutMapping("/authenticate")
	public ResponseEntity<?> UpdateAcc(@RequestParam(value = "id", required = true) Long user_id,
									   @RequestBody UserDto userDto) {

		userService.updateAccount(user_id, userDto);

		return new ResponseEntity("Update thanh cong", HttpStatus.OK);
	}

	@DeleteMapping("/authenticate")
	public ResponseEntity<?> DeleteAcc(@RequestParam(value = "id", required = true) Long user_id) {

		userService.deleteAccount(user_id);


		return new ResponseEntity("Delete thanh cong", HttpStatus.OK);
	}
}
