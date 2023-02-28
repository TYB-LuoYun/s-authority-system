package top.anets.init;

/**
 * @author ftm
 * @date 2023/2/20 0020 11:08
 */
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import top.anets.common.constants.RedisConstant;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Component
public class LuaScriptInitializer {

    private final StringRedisTemplate redisTemplate ;


    /**
     * RedisScript 类来封装 Lua 脚本，它会自动地在执行脚本时检查脚本是否已经加载到 Redis 中，如果没有，则会自动地将脚本加载到 Redis 中并获取它的 SHA1 值
     */
    private RedisScript<String> script;


    public LuaScriptInitializer(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        RedisScript<String> redisScript = new DefaultRedisScript<>(luaScript, String.class);
        script = redisScript;

    }

    private static final String luaScript =
            "local role = redis.call('hget', KEYS[1], ARGV[1])\n" +
                    "  if role then\n" +
                    "    return  role\n" +
                    "  end\n" +
                    "  for i = 2, #ARGV do\n" +
                    "    role = redis.call('hget', KEYS[2], ARGV[i]) \n" +
                    "    if role then\n" +
                    "      return role\n" +
                    "    end\n" +
                    "  end\n" +
                    "  return nil";



    public List<String> getRoleForPath(String path) {
        List<String> keys = Arrays.asList(RedisConstant.RESOURCE_ROLES_MAP,RedisConstant.MODULE_RESOURCE_ROLES_MAP);
        List<String> paths = getMatchedPaths(path);
        List<String> args = new ArrayList<>(paths.size()  );
        args.addAll(paths);
        String execute = redisTemplate.execute(script, keys, args.toArray());
        if(StringUtils.isBlank(execute)){
            return null;
        }
        JSONArray objects = JSON.parseArray(execute);
        if(objects==null || objects.size()<2){
            return null;
        }
        return (List<String>) objects.get(1);
    }



    public static List<String> getMatchedPaths(String path) {
        String lastPath = null;
        String nextpath = path;
        List<String> strings = new ArrayList<>();
        strings.add(path);
        while(nextpath.indexOf("/")!=-1){
            String dir =nextpath.substring(0, nextpath.indexOf("/"));
            if(lastPath!=null){
                lastPath += dir+"/";
                strings.add(lastPath+"**");
            }else{
                lastPath = dir+"/";
            }
            nextpath= nextpath.substring(nextpath.indexOf("/")+1);
        }
        return strings;
    }
}