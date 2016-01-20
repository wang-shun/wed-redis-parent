/**
 * File Created at 15/9/29
 * <p/>
 * Copyright 2014 dianping.com.
 * All rights reserved.
 * <p/>
 * This software is the confidential and proprietary information of
 * Dianping Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with dianping.com.
 */
package com.dianping.wed.cache.redis.cnt;

/**
 * redis info 命令返回的内容对应的key
 *
 * @author bo.lv
 */
public class WeddingRedisInfoKey {

    /**
     * ================Server================
     */
    //Redis 服务器版本
    private static final String REDIS_VERSION = "redis_version";
    //Git SHA1
    private static final String REDIS_GIT_SHA1 = "redis_git_sha1";
    //Git dirty flag
    private static final String REDIS_GIT_DIRTY = "redis_git_dirty";
    private static final String REDIS_BUILD_ID = "redis_build_id";
    private static final String REDIS_MODE = "redis_mode";
    //Redis 服务器的宿主操作系统
    private static final String OS = "os";
    //架构（32 或 64 位）
    private static final String ARCH_BITS = "arch_bits";
    //Redis 所使用的事件处理机制
    private static final String MULTIPLEXING_API = "multiplexing_api";
    //编译 Redis 时所使用的 GCC 版本
    private static final String GCC_VERSION = "gcc_version";
    //服务器进程的 PID
    private static final String PROCESS_ID = "process_id";
    //Redis 服务器的随机标识符（用于 Sentinel 和集群）
    private static final String RUN_ID = "run_id";
    //TCP/IP 监听端口
    private static final String TCP_PORT = "tcp_port";
    //自 Redis 服务器启动以来，经过的秒数
    private static final String UPTIME_IN_SECONDS = "uptime_in_seconds";
    //自 Redis 服务器启动以来，经过的天数
    private static final String UPTIME_IN_DAYS = "uptime_in_days";
    private static final String HZ = "hz";
    //以分钟为单位进行自增的时钟，用于 LRU 管理
    private static final String LRU_CLOCK = "lru_clock";
    private static final String CONFIG_FILE = "config_file";

    /**
     * ================Clients================
     */
    //已连接客户端的数量（不包括通过从属服务器连接的客户端）
    private static final String CONNECTED_CLIENTS = "connected_clients";
    //当前连接的客户端当中，最长的输出列表
    private static final String CLIENT_LONGEST_OUTPUT_LIST = "client_longest_output_list";
    //当前连接的客户端当中，最大输入缓存
    private static final String CLIENT_BIGGEST_INPUT_BUF = "client_biggest_input_buf";
    //正在等待阻塞命令（BLPOP、BRPOP、BRPOPLPUSH）的客户端的数量
    private static final String BLOCKED_CLIENTS = "blocked_clients";

    /**
     * ================Memory================
     */
    //由 Redis 分配器分配的内存总量，以字节（byte）为单位
    private static final String USED_MEMORY = "used_memory";
    //以人类可读的格式返回 Redis 分配的内存总量
    private static final String USED_MEMORY_HUMAN = "used_memory_human";
    //从操作系统的角度，返回 Redis 已分配的内存总量（俗称常驻集大小）。这个值和 top 、 ps 等命令的输出一致
    private static final String USED_MEMORY_RSS = "used_memory_rss";
    //Redis 的内存消耗峰值（以字节为单位）
    private static final String USED_MEMORY_PEAK = "used_memory_peak";
    //以人类可读的格式返回 Redis 的内存消耗峰值
    private static final String USED_MEMORY_PEAK_HUMAN = "used_memory_peak_human";
    //Lua 引擎所使用的内存大小（以字节为单位）
    private static final String USED_MEMORY_LUA = "used_memory_lua";
    //used_memory_rss 和 used_memory 之间的比率
    private static final String MEM_FRAGMENTATION_RATIO = "mem_fragmentation_ratio";
    //在编译时指定的， Redis 所使用的内存分配器。可以是 libc 、 jemalloc 或者 tcmalloc 。
    private static final String MEM_ALLOCATOR = "mem_allocator";


    /**
     * ================Persistence================
     */
    //一个标志值，记录了服务器是否正在载入持久化文件
    private static final String LOADING = "loading";
    //距离最近一次成功创建持久化文件之后，经过了多少秒
    private static final String RDB_CHANGES_SINCE_LAST_SAVE = "rdb_changes_since_last_save";
    //一个标志值，记录了服务器是否正在创建 RDB 文件
    private static final String RDB_BGSAVE_IN_PROGRESS = "rdb_bgsave_in_progress";
    //最近一次成功创建 RDB 文件的 UNIX 时间戳
    private static final String RDB_LAST_SAVE_TIME = "rdb_last_save_time";
    //一个标志值，记录了最近一次创建 RDB 文件的结果是成功还是失败
    private static final String RDB_LAST_BGSAVE_STATUS = "rdb_last_bgsave_status";
    //记录了最近一次创建 RDB 文件耗费的秒数
    private static final String RDB_LAST_BGSAVE_TIME_SEC = "rdb_last_bgsave_time_sec";
    //如果服务器正在创建 RDB 文件，那么这个域记录的就是当前的创建操作已经耗费的秒数
    private static final String RDB_CURRENT_BGSAVE_TIME_SEC = "rdb_current_bgsave_time_sec";
    //一个标志值，记录了 AOF 是否处于打开状态
    private static final String AOF_ENABLED = "aof_enabled";
    //一个标志值，记录了服务器是否正在创建 AOF 文件
    private static final String AOF_REWRITE_IN_PROGRESS = "aof_rewrite_in_progress";
    //一个标志值，记录了在 RDB 文件创建完毕之后，是否需要执行预约的 AOF 重写操作
    private static final String AOF_REWRITE_SCHEDULED = "aof_rewrite_scheduled";
    //最近一次创建 AOF 文件耗费的时长
    private static final String AOF_LAST_REWRITE_TIME_SEC = "aof_last_rewrite_time_sec";
    //如果服务器正在创建 AOF 文件，那么这个域记录的就是当前的创建操作已经耗费的秒数
    private static final String AOF_CURRENT_REWRITE_TIME_SEC = "aof_current_rewrite_time_sec";
    // 一个标志值，记录了最近一次创建 AOF 文件的结果是成功还是失败
    private static final String AOF_LAST_BGREWRITE_STATUS = "aof_last_bgrewrite_status";
    private static final String AOF_LAST_WRITE_STATUS = "aof_last_write_status";

    /**
     * ================Stats================
     */
    //服务器已接受的连接请求数量
    private static final String TOTAL_CONNECTIONS_RECEIVED = "total_connections_received";
    //服务器已执行的命令数量
    private static final String TOTAL_COMMANDS_PROCESSED = "total_commands_processed";
    //服务器每秒钟执行的命令数量
    private static final String INSTANTANEOUS_OPS_PER_SEC = "instantaneous_ops_per_sec";
    private static final String TOTAL_NET_INPUT_BYTES = "total_net_input_bytes";
    private static final String TOTAL_NET_OUTPUT_BYTES = "total_net_output_bytes";
    private static final String INSTANTANEOUS_INPUT_KBPS = "instantaneous_input_kbps";
    private static final String INSTANTANEOUS_OUTPUT_KBPS = "instantaneous_output_kbps";
    //因为最大客户端数量限制而被拒绝的连接请求数量
    private static final String REJECTED_CONNECTIONS = "rejected_connections";
    private static final String SYNC_FULL = "sync_full";
    private static final String SYNC_PARTIAL_OK = "sync_partial_ok";
    private static final String SYNC_PARTIAL_ERR = "sync_partial_err";
    //因为过期而被自动删除的数据库键数量
    private static final String EXPIRED_KEYS = "expired_keys";
    //因为最大内存容量限制而被驱逐（evict）的键数量
    private static final String EVICTED_KEYS = "evicted_keys";
    //查找数据库键成功的次数
    private static final String KEYSPACE_HITS = "keyspace_hits";
    //查找数据库键失败的次数
    private static final String KEYSPACE_MISSES = "keyspace_misses";
    //目前被订阅的频道数量
    private static final String PUBSUB_CHANNELS = "pubsub_channels";
    //目前被订阅的模式数量
    private static final String PUBSUB_PATTERNS = "pubsub_patterns";
    //最近一次 fork() 操作耗费的毫秒数
    private static final String LATEST_FORK_USEC = "latest_fork_usec";
    private static final String MIGRATE_CACHED_SOCKETS = "migrate_cached_sockets";

    /**
     * ================Replication================
     */
    //如果当前服务器没有在复制任何其他服务器，那么这个域的值就是 master ；否则的话，这个域的值就是 slave 。注意，在创建复制链的时候，一个从服务器也可能是另一个服务器的主服务器
    private static final String ROLE = "role";
    //已连接的从服务器数量
    private static final String CONNECTED_SLAVES = "connected_slaves";
    private static final String MASTER_REPL_OFFSET = "master_repl_offset";
    private static final String REPL_BACKLOG_ACTIVE = "repl_backlog_active";
    private static final String REPL_BACKLOG_SIZE = "repl_backlog_size";
    private static final String REPL_BACKLOG_FIRST_BYTE_OFFSET = "repl_backlog_first_byte_offset";
    private static final String REPL_BACKLOG_HISTLEN = "repl_backlog_histlen";

    /**
     * ================CPU================
     */
    //Redis 服务器耗费的系统 CPU
    private static final String USED_CPU_SYS = "used_cpu_sys";
    //Redis 服务器耗费的用户 CPU
    private static final String USED_CPU_USER = "used_cpu_user";
    //后台进程耗费的系统 CPU
    private static final String USED_CPU_SYS_CHILDREN = "used_cpu_sys_children";
    //后台进程耗费的用户 CPU
    private static final String USED_CPU_USER_CHILDREN = "used_cpu_user_children";

    /**
     * ================Cluster================
     */
    //一个标志值，记录集群功能是否已经开启
    private static final String CLUSTER_ENABLED = "cluster_enabled";

    /**
     * ================Keyspace================
     */
    private static final String DB0 = "db0";
}
