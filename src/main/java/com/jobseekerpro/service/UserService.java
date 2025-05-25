package com.jobseekerpro.service;

import com.jobseekerpro.entity.AppUser;
import com.jobseekerpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; //for bcrypt

    public AppUser signup(String email,String password, String name,String role){
        AppUser user = new AppUser();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); //Hash the password
        user.setName(name);
        user.setRole(role);
        AppUser savedUser = userRepository.save(user);
        System.out.println("Saved User :" + savedUser.getId() + ", " + savedUser.getEmail());
        return savedUser;
    }

    public AppUser login(String email, String password){
        AppUser user = userRepository.findByEmail(email);
        if(user == null){
            throw new IllegalArgumentException("User with email "+email+" not found");
        }
        if(!passwordEncoder.matches( password, user.getPassword())){
            throw new IllegalArgumentException("Invalid Password");
        }
        return user; // Return user for JWT generation
    }

    public AppUser getUserByEmail(String email) {
    return userRepository.findByEmail(email);
            
}

}
