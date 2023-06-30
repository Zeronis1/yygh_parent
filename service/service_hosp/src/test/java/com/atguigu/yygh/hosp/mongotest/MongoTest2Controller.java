package com.atguigu.yygh.hosp.mongotest;

import com.atguigu.yygh.hosp.entity.User;
import com.atguigu.yygh.hosp.mongotest1.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Date 2023/6/28
 * @Author SLF
 * @Description:
 */
@RestController
@RequestMapping("/api/mongo2")
public class MongoTest2Controller {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/testMethod2")
    public void testMethod2() {
        List<User> userList = userRepository.findByNameLike("张");
        System.out.println("userList = " + userList);
    }

    //添加
    @PostMapping("/create")
    public void createUser() {
        User user = new User();
        user.setAge(23);
        user.setName("张三");
        user.setEmail("zhangsan@qq.com");

        User user1 = userRepository.save(user);
        System.out.println("user1 = " + user1);
    }

    /**
     * 查询所有
     */
    @GetMapping("/findAll")
    public void findAllUser() {
        List<User> userList = userRepository.findAll();
        System.out.println("userList = " + userList);
    }

    //根据id查询
    @GetMapping("/queryById")
    public void queryById() {
        User user = userRepository.findById("649bec0e1af1667337caa158").get();
        System.out.println("user = " + user);
    }

    //条件查询
    @GetMapping("/queryByCondition")
    public void findUserList() {
        User user = new User();
        user.setName("张三");
        user.setAge(23);
        Example<User> userExample = Example.of(user);
        List<User> userList = userRepository.findAll(userExample);
        System.out.println("userList = " + userList);
    }

    //模糊查询
    @GetMapping("/queryByLike")
    public void findUUsersLikeName() {
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching()//构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);//改变默认大小写忽略方式：忽略大小写
        User user = new User();
        user.setName("三");
        Example<User> userExample = Example.of(user, matcher);
        List<User> userList = userRepository.findAll(userExample);
        System.out.println("userList = " + userList);
    }

    //分页查询
    @GetMapping("queryByPage")
    public void findUsersPage() {
        Sort sort = Sort.by(Sort.Direction.DESC, "age");

        Pageable pageable = PageRequest.of(0, 10, sort);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        User user = new User();
        user.setName("三");
        Example<User> userExample = Example.of(user, matcher);
        Example<User> example = Example.of(user, matcher);
        Page<User> pages = userRepository.findAll(example, pageable);
        System.out.println("pages = " + pages);
    }

    //修改
    @PutMapping("/update")
    public void updateUser() {
        User user = userRepository.findById("649be467a002d5256fcf1dfb").get();
        user.setName("菜鸡精");
        user.setAge(25);
        user.setEmail("caijijing@qq.com");
        User user1 = userRepository.save(user);
        System.out.println("user1 = " + user1);
    }

    //删除
    @DeleteMapping("/delete")
    public void deleteUser() {
       userRepository.deleteById("649be467a002d5256fcf1dfb");
    }
}
