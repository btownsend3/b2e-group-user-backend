package net.yorksolutions.userbackend.services;

import net.yorksolutions.userbackend.entity.UserInfo;
import net.yorksolutions.userbackend.repo.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserInfoRepo userInfoRepo;
    private HashMap<UUID,UUID> tokenMap;

    @Autowired
    public UserService(UserInfoRepo userInfoRepo){
        this.userInfoRepo = userInfoRepo;
        this.tokenMap = new HashMap<>();
    }

     public UUID login(String username, String password){
        Optional<UserInfo> userData = this.userInfoRepo.findByUsernameAndPassword(username,password);
        if(userData.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        UUID token = UUID.randomUUID();
        UserInfo user = userData.get();
         this.tokenMap.put(token, user.getId());
         return token;
     }

    public void logout(UUID token){
        if(!tokenMap.containsKey(token))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TOKEN DOES NOT EXIST");
        tokenMap.remove(token);
    }
    public void create(String username, String password, List<String> permissions, String role){
        if(userInfoRepo.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        UserInfo newUser = new UserInfo(username,password,permissions,role);
        this.userInfoRepo.save(newUser);
    }
}
