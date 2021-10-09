package test;

import com.len.LenApplication;
import com.len.entity.SysUser;
import com.len.redis.RedisService;
import com.len.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author zhuxiaomeng
 * @date 2018/1/5.
 * @email lenospmiller@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BootTest {

    @Autowired
    SysUserService userService;

    @Autowired
    RedisService redisService;

    @Test
    public void testStartJob() throws Exception {
        //12
        SysUser user = userService.getById("2211fec3e17c11e795ed201a068c6482");
        System.out.println(user.getUsername());
    }

    @Test
    public void redisTest() throws InterruptedException {
        SysUser user=new SysUser();
        userService.add(user,null);

    }

}