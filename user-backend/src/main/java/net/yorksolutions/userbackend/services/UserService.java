package net.yorksolutions.userbackend.services;

import net.yorksolutions.userbackend.entity.UserInfo;
import net.yorksolutions.userbackend.repo.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class UserService {
    private final UserInfoRepo userInfoRepo;
    private HashMap<UUID, UUID> tokenMap;
    @Value("${net.yorksolutions.user-backend.auth_url}")
    private String AUTH_URL;
    private final RestTemplate restTemplate;


    @Autowired
    public UserService(@NonNull UserInfoRepo userInfoRepo) {
        this.restTemplate = new RestTemplate();
        this.userInfoRepo = userInfoRepo;
        this.tokenMap = new HashMap<>();
    }

    public Boolean checkAuth(UUID token, String role) {
        Optional<UUID> id = Optional.ofNullable(tokenMap.get(token));
        if(id.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"token not on tokenmap");
        }
        Optional<UserInfo> user = userInfoRepo.findById(id.get());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            if (user.get().getRole().equals(role)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public HashMap login(String username, String password) {
        Optional<UserInfo> userData = this.userInfoRepo.findByUsernameAndPassword(username, password);
        if (userData.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        UUID token = UUID.randomUUID();
        UserInfo user = userData.get();
        this.tokenMap.put(token, user.getId());
        HashMap data = new HashMap<>();
        data.put("token", token);
        data.put("role", user.getRole());
        return data;
    }

    public void logout(UUID token) {
        if (!tokenMap.containsKey(token))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TOKEN DOES NOT EXIST");
        tokenMap.remove(token);
    }

      public void create(String username, String password) {
        if (userInfoRepo.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        } else {
            List<String> permissions = new ArrayList<>();
            UserInfo userInfo = new UserInfo(username, password, permissions, "applicant");
            this.userInfoRepo.save(userInfo);
        }
    }
    public Iterable<UserInfo> getUsers(){
        return userInfoRepo.findAll();
    }

    public void adminCreate(String username, String password,String role) {
        if (userInfoRepo.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        } else {
            List<String> permissions = new ArrayList<>();
            UserInfo userInfo = new UserInfo(username, password, permissions,role);
            this.userInfoRepo.save(userInfo);
        }
    }

        public void edit (UserInfo userInfo){
            this.userInfoRepo.save(userInfo);
        }

        public void delete (UUID id){
            Optional<UserInfo> user = this.userInfoRepo.findById(id);
            if (user.isEmpty())
                throw new ResponseStatusException(HttpStatus.GONE, "Id does not exist");
            this.userInfoRepo.delete(user.get());
        }

    }
