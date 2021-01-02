package fudan.se.lab2.security.jwt;

import fudan.se.lab2.repository.DoctorRepository;
import fudan.se.lab2.repository.EmergencyNurseRepository;
import fudan.se.lab2.repository.HeadNurseRepository;
import fudan.se.lab2.repository.WardNurseRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Write your code to make this filter works.
 *基于token的鉴权机制
 * 基于token的鉴权机制类似于http协议也是无状态的，它不需要在服务端去保留用户的认证信息或者会话信息。这就意味着基于token认证机制的应用不需要去考虑用户在哪一台服务器登录了，这就为应用的扩展提供了便利。
 *
 * 流程上是这样的：
 *
 * 用户使用用户名密码来请求服务器
 * 服务器进行验证用户的信息
 * 服务器通过验证发送给用户一个token
 * 客户端存储token，并在每次请求时附送上这个token值
 * 服务端验证token值，并返回数据
 * 这个token必须要在每次请求时传递给服务端，它应该保存在请求头里， 另外，服务端要支持CORS(跨来源资源共享)策略，一般我们在服务端这么做就可以了Access-Control-Allow-Origin: *。
 *
 *
 * @author LBW
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
   @Autowired
   DoctorRepository doctorRepository;
    @Autowired
    HeadNurseRepository headNurseRepository;
    @Autowired
    WardNurseRepository wardNurseRepository;
    @Autowired
    EmergencyNurseRepository emergencyNurseRepository;
    // jwtUtil类 主要提供了jwt的实现方法，如加密规则，生成token，获取token等
    private JwtConfigProperties jwtConfigProperties = new JwtConfigProperties();
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(jwtConfigProperties);
   
    //过滤器JwtRequestFilter 对指定的http请求进行拦截和用户认证等
    //过滤JWT请求，校验token是否正确
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("do filter start");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Cache-Control","no-cache");
        final String requestTokenHeader = request.getHeader("Authorization");
         if(request.getRequestURI().contains("login")||request.getRequestURI().contains("register")){

            filterChain.doFilter(request, response);
             System.out.println("do filter finish");
            return;
        }
        String username = null;
        String jwtToken = null;

        if(requestTokenHeader != null ){
            jwtToken = requestTokenHeader.substring(7);//
            logger.debug(jwtToken);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.debug("Unable to get JWT Token");
                return;
            } catch (ExpiredJwtException e) {
                response.setStatus(555);
                logger.debug("JWT Token has expired");
                return;
            }
        } else {
            logger.warn("tokenHeader is null");
        }

        identity(username,jwtToken,request,response,filterChain);
        System.out.println("do filter finish");
        
    }

    //身份验证
    public void identity(String username,String jwtToken,HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //普通用户
        System.out.print("start identity");
        if (doctorRepository.findByUsername(username) != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(!jwtTokenUtil.isTokenExpired(jwtToken)){
                filterChain.doFilter(request, response);
            }
        }else if (emergencyNurseRepository.findByUsername(username) != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(!jwtTokenUtil.isTokenExpired(jwtToken)){
                filterChain.doFilter(request, response);
            }
        }else if (headNurseRepository.findByUsername(username) != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(!jwtTokenUtil.isTokenExpired(jwtToken)){
                filterChain.doFilter(request, response);
            }
        }else if(wardNurseRepository.findByUsername(username)!= null
                && SecurityContextHolder.getContext().getAuthentication() == null){
            if(!jwtTokenUtil.isTokenExpired(jwtToken)){
                filterChain.doFilter(request, response);
            }
        }else{
            response.setStatus(666);
            logger.debug("身份错误");
        }

    }

}
