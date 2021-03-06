package com.meipiao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meipiao.entity.Person;
import com.meipiao.redis.RedisUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.swing.text.html.parser.Entity;
import java.util.*;

/**
 * @Author: Chenwx
 * @Date: 2020/5/8 15:45
 */
@RestController
public class RedisController {
    private static int ExpireTime = 10;   // redis中存储的过期时间10s

    @Resource
    RedisUtil redisUtil;

    @RequestMapping("/save")
    public String save(String key, String value) {
        redisUtil.lSet(key, value);

        return "success";
    }

    @GetMapping("/test/{key}")
    public String test(@PathVariable String key) {
        String data = null;
        List<Object> objects = redisUtil.lGet(key, 1, 1);
        for (Object object : objects) {
            data = object.toString();
        }
        return data;
    }

    @GetMapping("/get/{key}")
    public String get(@PathVariable("key") String key) {
        Set<Object> data = redisUtil.sGet(key);
        for (Object datum : data) {
            System.out.println("set中的数据为" + datum);
        }
        return "data";
    }


    @DeleteMapping("/delete/{key}/{f}")
    public String delete(@PathVariable("key") String key,
                         @PathVariable("f") String f) {
        redisUtil.hdel(key, f);
        return "delete";
    }

    @GetMapping("/getSize/{key}")
    public String sGetSetSize(@PathVariable("key") String key) {
        long size = redisUtil.sGetSetSize(key);
        System.err.println("长度为" + size);
        return "success";
    }

    @RequestMapping("/aaaaa")
    public String aaaaa() {
        String json = "{\n" +
                "    \"Code\": \"0\",\n" +
                "    \"Result\": {\n" +
                "        \"Hotels\": [\n" +
                "            {\n" +
                "                \"LastId\": 7448010641,\n" +
                "                \"Time\": \"2016-10-27T09:42:26 08:00\",\n" +
                "                \"HotelID\": \"40101006\",\n" +
                "                \"StartDate\": \"2016-10-27T00:00:00 08:00\",\n" +
                "                \"EndDate\": \"2016-11-27T00:00:00 08:00\"\n" +
                "            },\n" +
                " {\n" +
                "                \"LastId\": 8448010641,\n" +
                "                \"Time\": \"2016-10-27T09:42:26 08:00\",\n" +
                "                \"HotelID\": \"40101006\",\n" +
                "                \"StartDate\": \"2016-10-27T00:00:00 08:00\",\n" +
                "                \"EndDate\": \"2016-11-27T00:00:00 08:00\"\n" +
                "            },\n" +
                " {\n" +
                "                \"LastId\": 7448010688,\n" +
                "                \"Time\": \"2016-10-27T09:42:26 08:00\",\n" +
                "                \"HotelID\": \"40101007\",\n" +
                "                \"StartDate\": \"2016-10-27T00:00:00 08:00\",\n" +
                "                \"EndDate\": \"2016-11-27T00:00:00 08:00\"\n" +
                "            },\n" +
                " {\n" +
                "                \"LastId\": 7448010699,\n" +
                "                \"Time\": \"2016-10-27T09:42:26 08:00\",\n" +
                "                \"HotelID\": \"40101008\",\n" +
                "                \"StartDate\": \"2016-10-27T00:00:00 08:00\",\n" +
                "                \"EndDate\": \"2016-11-27T00:00:00 08:00\"\n" +
                "            },\n" +
                " {\n" +
                "                \"LastId\": 7448010610,\n" +
                "                \"Time\": \"2016-10-27T09:42:26 08:00\",\n" +
                "                \"HotelID\": \"40101009\",\n" +
                "                \"StartDate\": \"2016-10-27T00:00:00 08:00\",\n" +
                "                \"EndDate\": \"2016-11-27T00:00:00 08:00\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"Guid\": \"90462b2e-fc10-477f-923d-69499802a8ca\"\n" +
                "}";
        JSONObject incr_data = JSON.parseObject(json);
        JSONArray hotels = incr_data.getJSONObject("Result").getJSONArray("Hotels");
        for (int i = 0; i < hotels.size(); i++) {
            JSONObject hotel = hotels.getJSONObject(i);
            Long hotelID = hotel.getLong("HotelID");
        }

        Object data = redisUtil.hget("incredata", "40101008");
        System.err.println("获取的数据" + data);
        return "success";
    }

    @RequestMapping("/qqqqqqq")
    public String qqqqqq() {
        Person person = new Person();
        person.setId(11);
        person.setAge(15);
        person.setName("sam");
        boolean sam = redisUtil.set("sam", person);
        if (sam) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/getget")
    public String getget() {
        redisUtil.del("b");
        return "success";
    }

    @RequestMapping("/token")
    public String token() {
        String str = "{\n" +
                "\t\"AID\": 1,\n" +
                "\t\"SID\": 50,\n" +
                "\t\"Access_Token\": \"02856107c8594d21aaf5cdfb563e875\",\n" +
                "\t\"Expires_In\": 597,\n" +
                "\t\"Refresh_Token\": \"c24rngwlriasdfsga7357ee613e500ee\"\n" +
                "}";
        JSONObject result = JSONObject.parseObject(str);
        //将返回的token信息存储到redis中
        String access_token = result.getString("Access_Token");
        Integer expires_in = result.getInteger("Expires_In");  //过期时间 s
        String refresh_token = result.getString("Refresh_Token");
        redisUtil.hset("ctrip:token", access_token, refresh_token, expires_in);

        return "success";
    }

    @RequestMapping("/token/get")
    public String getToken() {
        Map<Object, Object> get = redisUtil.hmget("a");
        for (Map.Entry<Object, Object> entry : get.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.err.println("key:" + key + "value:" + value);
        }
        redisUtil.hdel("a","12");
        redisUtil.hset("a","cwx","ly");
        return "success";
    }

}
