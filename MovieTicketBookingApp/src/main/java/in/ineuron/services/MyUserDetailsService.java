package in.ineuron.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.ineuron.entities.CustomUserDetails;
import in.ineuron.entities.User;
@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private UserService userService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userService.findByName(username);
		if(user==null) {
			throw new UsernameNotFoundException("UserName Not Found ");
		}
		
		return new CustomUserDetails(user);
	}

}
