package top.anets.common.constants;

/**
 * 系统常量类
 * @author Young
 * @date 2022/4/29
 */
public final class SysConstants {

    public static final String TOKEN_SERVICE = "token-service";

    private SysConstants() {
        throw new AssertionError("No instance for you!");
    }

    /**
     * 序列生成锁key前缀
     */
    public static final String SEQUENCE_GENERATE_LOCK_KEY_PREFIX = "sequence-";

    /**
     * 医院编码锁key前缀
     */
    public static final String HOSPITAL_CODE_LOCK_KEY_PREFIX = "hos-code-";

    /**
     * 用户名锁key前缀
     */
    public static final String USERNAME_LOCK_KEY_PREFIX = "username-lock-";

    /**
     * 评分范围分隔符
     */
    public static final String SCORE_SCOPE_SEPARATOR = "-";

    /**
     * application/json;charset=utf-8
     */
    public static final String APPLICATION_JSON_UTF8 = "application/json;charset=utf-8";

    /**
     * 抽样数据session key
     */
    public static final String SAMPLE_DATA_SESSION_KEY = "sample-data";

    /**
     * 评分锁key
     */
    public static final String QUALITY_EVALUATE_KEY = "quality-evaluate-";

    /**
     * 评估信息更新锁key
     */
    public static final String ASSESS_UPDATE_KEY = "assess-update-";

    /**
     * 反馈锁key
     */
    public static final String ASSESS_RECTIFY_KEY = "assess-rectify-";

    /**
     * 客户端类型header name
     */
    public static final String USER_AGENT = "User-Agent";

    /**
     * 后台帐号锁定key
     */
    public static final String ADMIN_ACCOUNT_LOCK_KEY = "admin-account-lock-";

    /**
     * 帐号锁定时间（分钟）
     */
    public static final int ACCOUNT_LOCK_TIME = 30;

    /**
     * 登录错误重试次数
     */
    public static final int LOGIN_RETRY_TIMES = 5;

    /**
     * redis 脚本
     */
    public static final String REDIS_SCRIPT = "if redis.call('EXISTS', KEYS[1]) == 1 then " +
            " redis.call('EXPIRE', KEYS[1], ARGV[2]) " +
            " return redis.call('INCR', KEYS[1]) " +
            " else " +
            " redis.call('SET', KEYS[1], ARGV[1], 'EX', ARGV[2]) " +
            " return 1 end";

    public static final String LIMIT_KEY = "limit-";

    /**
     * 登录用户令牌
     */
    public static final String LOGIN_USER_ACCESS_TOKEN = "login-user-access-token";

    /**
     * 样本锁key
     */
    public static final String ASSESS_SIMPLE_DATA_KEY = "assess-simple-data-";

    /**
     * 一分钟的秒数
     */
    public static final int SECONDS_OF_MINUTE = 60;

    /**
     * 密码正则
     */
    public static final String PWD_REG = "^[a-zA-Z0-9_!@#$%^&*(){}\\\\[\\\\]`~.,?<>/\\\\\\\\+=|;:'\\\"-]{8,20}$";
}
