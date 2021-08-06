package com.kxt.supermarket.Utils;


import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.kxt.supermarket.config.GiteeImgBedConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Kxt
 * @Date 2021/7/27 17:39
 * @Version 1.0
 * 图片上传工具类
 */

public class ImageUtils {
    //用户注册时随机获得一个头像，也可以日后自己上传更改
    public static String getRandomFace(){
        String[]images={"https://gitee.com/kxt666/knightzz/raw/master/image/L%5BG%5B~UROGA5PSD_F80)@AQC.png",
                        "https://gitee.com/kxt666/knightzz/raw/master/image/%60%7BVFN8SM)FB((F%60Q)1FU$L1.png",
                        "https://gitee.com/kxt666/knightzz/raw/master/image/C9_CRGFESH0FA~7QDB4AX)H.png",
                        "https://gitee.com/kxt666/knightzz/raw/master/image/6@DW~%7DSNE2ZKPMS5KB353IS.png",
                        "https://gitee.com/kxt666/knightzz/raw/master/image/QQ%E5%9B%BE%E7%89%8720210716221243.jpg"};
            return images[(int) (Math.random() * 5)];
    }

    //图片上传到gitee,返回图片的访问路径
    public static String upload(MultipartFile file) throws IOException {
        String trueFileName=file.getOriginalFilename();
        String suffix = trueFileName != null ? trueFileName.substring(trueFileName.indexOf(".")) : null;
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        String paramImgFile = Base64.encode(file.getBytes());

        //转存到gitee
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", GiteeImgBedConfig.ACCESS_TOKEN);
        paramMap.put("message", GiteeImgBedConfig.CREATE_REPOS_MESSAGE);
        paramMap.put("content", paramImgFile);
        String targetDir = "image/" + fileName;
        String requestUrl = String.format(GiteeImgBedConfig.CREATE_REPOS_URL, GiteeImgBedConfig.OWNER,
                GiteeImgBedConfig.REPO_NAME, targetDir);

        String resultJson = HttpUtil.post(requestUrl, paramMap);

        if (JSONUtil.parseObj(resultJson).getObj("commit") != null) {
            return GiteeImgBedConfig.GITPAGE_REQUEST_URL + targetDir;
        }
        return null;
    }
}
