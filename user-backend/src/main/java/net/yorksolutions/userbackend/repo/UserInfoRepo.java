package net.yorksolutions.userbackend.repo;

import net.yorksolutions.userbackend.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInfoRepo extends CrudRepository<UserInfo, UUID> {
    Optional<UserInfo> findByUsernameAndPassword(String username, String password);
    Boolean existsByUsername(String username);
}
