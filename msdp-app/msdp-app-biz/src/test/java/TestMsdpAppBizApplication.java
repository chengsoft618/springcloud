import com.iyysoft.msdp.dp.app.MsdpAppBizApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author mao.chi
 * @date 2019年08月21日
 * 统一应用管理中心
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MsdpAppBizApplication.class)
public class TestMsdpAppBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMsdpAppBizApplication.class, args);
    }

}
