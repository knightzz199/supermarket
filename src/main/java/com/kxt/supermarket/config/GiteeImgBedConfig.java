package com.kxt.supermarket.config;

/**
 * @Author Kxt
 * @Date 2021/7/27 17:53
 * @Version 1.0
 * 码云图床配置
 */


public interface GiteeImgBedConfig {

    //码云私人令牌
    String ACCESS_TOKEN = "********";
    //用户名
    String OWNER = "kxt666";
    //仓库名称
    String REPO_NAME = "knightzz";
    //上传图床的消息
    String CREATE_REPOS_MESSAGE = "(ง ˙o˙)ว";

    /**
     * 新建文件URL
     * 第一个 %s =>仓库所属空间地址(owner)
     * 第二个 %s => 仓库路径(repo)
     * 第三个 %s => 文件的路径(path)
     */
    String CREATE_REPOS_URL = "https://gitee.com/api/v5/repos/%s/%s/contents/%s";
    //GitPage请求路径
    String GITPAGE_REQUEST_URL = "https://gitee.com/"+OWNER+"/"+REPO_NAME+"/raw/master/";
}
