package com.atguigu.yygh.hosp.mongotest1;

import com.atguigu.yygh.hosp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date 2023/6/28
 * @Author SLF
 * @Description:
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {
    List<User> findByNameLike(String name);

    List<User> findByName(String name);
}
