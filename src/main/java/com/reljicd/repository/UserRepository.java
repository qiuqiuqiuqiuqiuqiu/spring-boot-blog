package com.reljicd.repository;

import com.reljicd.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByUsername(@Param("username") String username);

    Optional<User> findById(Long id);

    Page<User> findAllByOrderByIdDesc(Pageable pageable);


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update USER set PASSWORD=?2 where USER_ID=?1 ",nativeQuery = true)
    void updatePasswordById(@Param(value = "id") Long id,@Param(value = "password") String password);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "delete from USER_ROLE  where USER_ID=?1 ",nativeQuery = true)
    void deleteRoleUserByUserId(@Param(value = "id") Long id);


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "insert into USER_ROLE(USER_ID,ROLE_ID)VALUES(?1,?2)",nativeQuery = true)
    void AddRoleUser(@Param(value = "USER_ID") Long USER_ID,@Param(value = "ROLE_ID") Long ROLE_ID);
}
