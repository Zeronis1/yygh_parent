package com.atguigu.yygh.hosp.mongotest;

import com.atguigu.yygh.hosp.entity.User;
import com.atguigu.yygh.hosp.mongotest1.UserRepository;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Date 2023/6/28
 * @Author SLF
 * @Description:
 */
@RestController
@RequestMapping("/api/mongo1")
public class MongoTest1Controller {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/testMethod1")
    public void testMethod1() {
        List<User> userList = userRepository.findByName("张三");
        System.out.println("userList = " + userList);
    }

    //添加
    @PostMapping("create")
    public void createUser() {
        User user = new User();
        user.setAge(25);
        user.setName("旺仔");
        user.setEmail("wangzai@qq.com");
        User user1 = mongoTemplate.insert(user);
        System.out.println(user1);
    }

    //查询所有
    @GetMapping("/findAll")
    public void findAllUser() {
        List<User> userList = mongoTemplate.findAll(User.class);
        System.out.println("userList = " + userList);
    }

    //根据id查询
    @GetMapping("/findById")
    public void findById() {
        User user = mongoTemplate.findById("649be21ba921156e12a1bb95", User.class);
        System.out.println("user = " + user);
    }

    //条件查询
    @GetMapping("queryByCondition")
    public void findUserList() {
        Query query = new Query(Criteria
                .where("name").is("菜珠子")
                .and("age").is(20));
        List<User> userList = mongoTemplate.find(query, User.class);
        System.out.println("userList = " + userList);
    }

    //模糊查询
    @GetMapping("queryByLike")
    public void findUserLikeName() {
        String name = "仔";
        String regex = String.format("%s%s%s","^.*",name,".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<User> userList = mongoTemplate.find(query, User.class);
        System.out.println("userList = " + userList);
    }

    //分页查询
    @GetMapping("/queryPage")
    public void findUsersPage() {
        String name = "仔";
        int pageNo = 1;
        int pageSize = 5;

        Query query = new Query();
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("name").regex(pattern));
        int totalCount = (int) mongoTemplate.count(query, User.class);
        List<User> userList = mongoTemplate.find(query.skip((pageNo - 1) * pageSize).limit(pageSize), User.class);

        Map<String, Object> map = new HashMap<>();
        map.put("list",userList);
        map.put("totalCount",totalCount);

        System.out.println("totalCount = " + totalCount);
    }

    //修改
    @PutMapping("/update")
    public void updateUser() {
        User user = mongoTemplate.findById("649be21ba921156e12a1bb95", User.class);
        user.setName("菜鸡精");
        user.setAge(25);
        user.setEmail("caijijing@qq.com");

        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update();
        update.set("name",user.getName());
        update.set("age",user.getAge());
        update.set("email",user.getEmail());

        UpdateResult result = mongoTemplate.upsert(query, update, User.class);
        long count = result.getModifiedCount();
        System.out.println("count = " + count);
    }

    //删除操作
    @DeleteMapping("/delete")
    public void delete() {
        Query query = new Query(Criteria.where("_id").is("649be21ba921156e12a1bb95"));
        DeleteResult result = mongoTemplate.remove(query, User.class);
        long count = result.getDeletedCount();
        System.out.println("count = " + count);
    }
}
