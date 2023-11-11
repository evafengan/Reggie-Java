package com.reggie.utils;


import java.util.Random;

//随机生成验证码工具类
public class ValidateCodeUtils {
    //随机生成验证码
    public static Integer generateValidateCode(int length) {
        Integer code=null;
        if (length == 4) {
            code = new Random().nextInt(9999);//生成随机数，最大为9999

            if (code < 1000) {
                code = code + 1000;//保证随机数为4位数字
            }
        } else if (length == 6) {
            //随机生成一个介于0至nextInt()里的数
            code = new Random().nextInt(999999);//生成随机数，最大为999999
            if (code < 100000) {
                code = code + 100000;//保证随机数为6位数字
            }
        } else {
            throw new RuntimeException("只能生成4位或6位数字验证码");
        }
        return code;
    }

    //随机生成指定长度字符串验证码
    public static String generateValidateCode4String(int length) {
        Random rdm=new Random();
        //随机生成一个整数，然后将整数转为16进制的字符串
        String hash1 = Integer.toHexString(rdm.nextInt());
        //截取指定长度length的字符作为字符串验证码
        String capstr = hash1.substring(0, length);
        return capstr;
    }



}
