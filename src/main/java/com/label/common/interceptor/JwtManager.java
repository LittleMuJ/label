package com.label.common.interceptor;


import com.label.common.util.RandomUtil;
import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtManager {

    //加密私钥
    private final static String base64Secret = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";
    //过期时间
    private final static int expiresSecond = 3600000;
    
    private static Logger logger = LoggerFactory.getLogger(JwtManager.class);

    public static Claims parseJWT(String jsonWebToken,String str) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(str))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    //进行token分解将加密过的token分解成原始生成token
    public static Map<String,Object> TokenDecompose(String jsonWebToken) {
    	Map<String,Object> map = new HashMap<>();
    	//将token值以"."分为数组
    	 String[] tokenList = jsonWebToken.split("\\.");
    	 //获取盐的第二部分
    	 String mid = tokenList[1].substring(tokenList[1].length()-3);
    	 //获取盐的第一部分
    	 String left = tokenList[2].substring(0, 3);
    	 //获取盐的第三部分
    	 String right = tokenList[2].substring(tokenList[2].length()-4);
    	 //盐
    	 String randStr = left+mid+right;
    	 //token
    	 String token = tokenList[0]+"."+tokenList[1].substring(0,tokenList[1].length()-3)+"."+tokenList[2].substring(3,tokenList[2].length()-4);
    	 
    	 
    	 //userName =token ;password=randStr

        Claims claims =parseJWT(token,randStr);

        String userName= "";
        String userType = "";
        String userId= "";
        if(claims!=null){
            try {
                if (StringUtils.isNotBlank(claims.get("user_name").toString())) {
                    userName= claims.get("user_name").toString();
                }
                if (StringUtils.isNotBlank(claims.get("user_id").toString())) {
                    userId= claims.get("user_id").toString();
                }
                if (StringUtils.isNotBlank(claims.get("user_type").toString())) {
                    userType=claims.get("user_type").toString();
                }
            } catch (Exception e) {
                throw new AuthenticationException("分析token认证失败！");
            }
        }
        map.put("username",userName);
        map.put("userType",userType);
        map.put("userId",userId);
        return map;

    }

    //1、确定加盐算法结构
    /**
     * 生成随机数（盐）并和“”做md5加密
     * +用户信息做md5加密
     * */
    //2、tokenId的确定
    public static String createToken(String userName,Integer userId,String userType){
    	
    	//生成随机数作为生成签名的密钥
    	String randStr = RandomUtil.createRandomChar(10);
    	String left = randStr.substring(0, 3);
    	String middle = randStr.substring(3, 6);
    	String right = randStr.substring(6);
    	logger.info(left);
    	logger.info(middle);
    	logger.info(right);
        //logger.info签名的算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取时间戳
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(randStr);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

       /* Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("user_name", username);
        claims.put("user_role", roles);
        claims.put("user_privilege", privileges);
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")//头部
                .setClaims(claims)
                .setId("tokenid")
                .signWith(signatureAlgorithm, signingKey);//尾巴;*/
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")//头部
                .claim("user_id", userId)
                .claim("user_name", userName)
                //.claim("user_sex", user.getGender())
                .claim("user_type", userType)//是后台用户还是标注用户
                .setId("tokenid")
                .signWith(signatureAlgorithm, signingKey);//尾巴
        //添加Token过期时间
        if (expiresSecond >= 0) {
            long expMillis = nowMillis + expiresSecond;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        //生成JWT
        String token = builder.compact();
        //当使用点时，前面加两个点，不然会报出越界异常
        String[] tokenList = token.split("\\.");
        
        //新的token值按随机数2,1,3的顺序排列
        String newToken = tokenList[0]+"."+tokenList[1]+middle+"."+left+tokenList[2]+right;
        
        return newToken;
    }

    
    //
    public static void validateToken(String token) {
        try{
            Claims claims = parseJWT(token,"");
            String username = claims.get("user_name").toString();
            String role = claims.get("user_role").toString();
            String user_privilege = claims.get("user_privilege").toString();
            String tokenid = claims.getId();
            System.out.println("[user_name]:"+username);
            System.out.println("[user_role]:"+role);
            System.out.println("[tokenid]:"+tokenid);
            System.out.println("[user_privilege]:"+user_privilege);
        } catch(ExpiredJwtException e) {
            System.out.println("token expired");
        } catch (InvalidClaimException e) {
            System.out.println("token invalid");
        } catch (Exception e) {
            System.out.println("token error");
        }
    }

    public static void main(String[] args) {
       // System.out.println(createToken("admin","add","23"));
    	/*SdUser user = new SdUser();
    	user.setId(1);
    	user.setName("张飞");
    	user.setGender(1);*/

    	//createToken(user);
    }

}
