import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.HEAD;
import java.util.Map;

/**
 * @Author: 码农
 * @Date: 2019/9/28 8:51
 */
class BFTask implements Runnable {

    @Override
    public void run() {
        okgo();
    }

    @Test
    public void okgo(){
        try {
            String url = "http://192.168.1.114:8822/app/v1";
            String sid = "01e600163c26332c8d3569f11ef3bc8a";
            MultiValueMap head = new LinkedMultiValueMap();
            head.add("Content-Type","application/json");
            head.add("Authorization","Bearer 197717c4-dc01-4c7a-b167-ae73764d711e");
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity httpEntity = new HttpEntity<>(head);
            ResponseEntity<Object> responseEntity =restTemplate.exchange(url + "/snapshot/like/{1}", HttpMethod.PUT, httpEntity, Object.class, sid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}