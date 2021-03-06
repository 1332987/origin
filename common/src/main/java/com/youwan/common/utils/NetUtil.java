package com.youwan.common.utils;

import com.ajaxjs.util.Encode;
import com.ajaxjs.util.IoHelper;
import com.ajaxjs.util.MapTool;
import com.ajaxjs.util.io.StreamUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;


@Slf4j
public class NetUtil extends IoHelper {
    /**
     * 设置请求方法
     */
    public final static BiConsumer<HttpURLConnection, String> setMedthod = (conn, method) -> {
        try {
            conn.setRequestMethod(method);
        } catch (ProtocolException e) {
            log.error("错误：" + e);
        }
    };
    /**
     * 设置 cookies
     */
    public final static BiConsumer<HttpURLConnection, Map<String, String>> setCookies = (conn, map) -> conn.addRequestProperty("Cookie", MapTool.join(map, ";"));
    /**
     * 请求来源
     */
    public final static BiConsumer<HttpURLConnection, String> setReferer = (conn, url) -> conn.addRequestProperty("Referer", url); // httpUrl.getHost()?
    /**
     * 设置超时 （单位：秒）
     */
    public final static BiConsumer<HttpURLConnection, Integer> setTimeout = (conn, timeout) -> conn.setConnectTimeout(timeout * 1000);
    /**
     * 客户端识别
     */
    public final static BiConsumer<HttpURLConnection, String> setUserAgent = (conn, url) -> conn.addRequestProperty("User-Agent", url);
    /**
     * 默认的客户端识别
     */
    public final static Consumer<HttpURLConnection> setUserAgentDefault = conn -> setUserAgent.accept(conn, "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
    /**
     * HTTP Basic 用户认证
     */
    public final static BiConsumer<HttpURLConnection, String[]> setBasicAuth = (conn, auth) -> {
        String username = auth[0], password = auth[1];
        String encoding = Encode.base64Encode(username + ":" + password);
        conn.setRequestProperty("Authorization", "Basic " + encoding);
    };
    /**
     * 设置启动 GZip 请求
     */
    public final static Consumer<HttpURLConnection> setGizpRequest = conn -> conn.addRequestProperty("Accept-Encoding", "gzip, deflate");
    public final static Consumer<HttpURLConnection> setFormPost = conn -> conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    /**
     * request 头和上传文件内容之间的分隔符
     */
    private static final String BOUNDARY = "---------------------------123821742118716";
    /**
     * 多段 POST 的分隔
     */
    private static final String divField = "\r\n--%s\r\nContent-Disposition: form-data; name=\"%s\"\r\n\r\n%s";
    private static final String divFile = "\r\n--%s\r\nContent-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\n";

    /**
     * 简单 GET 请求（原始 API 版），返回文本。
     *
     * @param url 请求目标地址
     * @return 响应内容（如 HTML，JSON 等）
     */
    public static String simpleGET(String url) {
        try {
            return byteStream2stringStream(new URL(url).openStream());
        } catch (IOException e) {
            log.error("错误：" + e);
            return null;
        }
    }

    /**
     * HttpURLConnection 工厂函数
     *
     * @param url 请求目的地址
     * @return HttpURLConnection 对象
     */
    public static HttpURLConnection initHttpConnection(String url) {
        URL httpUrl = null;

        try {
            httpUrl = new URL(url);
        } catch (MalformedURLException e) {
            log.error(e + "初始化连接出错！URL {0} 格式不对！" + url);
        }

        try {
            return (HttpURLConnection) httpUrl.openConnection();
        } catch (IOException e) {
            log.error(e + "初始化连接出错！URL {0}。" + url);
        }

        return null;
    }

    /**
     * 发送请求，返回响应信息
     *
     * @param conn         链接对象
     * @param isEnableGzip 是否需要 GZip 解码
     * @param callback     回调里面请记得关闭 InputStream
     * @return
     */
    public static <T> T getResponse(HttpURLConnection conn, Boolean isEnableGzip, Function<InputStream, T> callback) {
        try {
            InputStream in = conn.getInputStream();// 发起请求，接收响应

            // 是否启动 GZip 请求
            // 有些网站强制加入 Content-Encoding:gzip，而不管之前的是否有 GZip 的请求
            boolean isGzip = isEnableGzip || "gzip".equals(conn.getHeaderField("Content-Encoding"));

            if (isGzip)
                in = new GZIPInputStream(in);

            int responseCode = conn.getResponseCode();
            if (responseCode >= 400) {// 如果返回的结果是400以上，那么就说明出问题了
                RuntimeException e = new RuntimeException(responseCode < 500 ? responseCode + "：客户端请求参数错误！" : responseCode + "：抱歉！我们服务端出错了！");
                log.error("错误：" + e);
            }

            if (callback == null) {
                in.close();
            } else
                return callback.apply(in);
        } catch (IOException e) {
            log.error("错误：" + e);
        }

        return null;
    }

    /**
     * GET 请求，返回文本内容
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        return get(url, false);
    }

    /**
     * GET 请求，返回文本内容
     *
     * @param url
     * @param isGzip
     * @return
     */
    public static String get(String url, boolean isGzip) {
        HttpURLConnection conn = initHttpConnection(url);
        if (isGzip)
            setGizpRequest.accept(conn);

        return getResponse(conn, isGzip, NetUtil::byteStream2stringStream);
    }

    /**
     * HEAD 请求
     *
     * @param url
     * @param
     * @return
     */
    public static HttpURLConnection head(String url) {
        HttpURLConnection conn = initHttpConnection(url);
        setMedthod.accept(conn, "HEAD");
        conn.setInstanceFollowRedirects(false); // 必须设置 false，否则会自动 redirect 到 Location 的地址

        getResponse(conn, false, null);// 不需要转化响应文本，节省资源
        return conn;
    }

    /**
     * 得到 HTTP 302 的跳转地址
     *
     * @param url 目标地址
     * @return 跳转地址
     */
    public static String get302redirect(String url) {
        return head(url).getHeaderField("Location");
    }

    /**
     * 检测资源是否存在
     *
     * @param url 目标地址
     * @return true 表示 404 不存在
     */
    public static boolean is404(String url) {
        try {
            return head(url).getResponseCode() == 404;
        } catch (IOException e) {
            log.error("错误：" + e);
            return false;
        }
    }

    /**
     * 得到资源的文件大小
     *
     * @param url 目标地址
     * @return 文件大小
     */
    public static long getFileSize(String url) {
        return Long.parseLong(head(url).getHeaderField("content-length"));
    }

    /**
     * 获取远程资源的大小 （另外一种写法，可作为参考之）
     *
     * @param url 目标地址
     * @return 文件大小
     */
    public static long getRemoteSize(String url) {
        long size = 0;

        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            size = conn.getContentLength();
            conn.disconnect();
        } catch (IOException e) {
            log.error("错误：" + e);
        }

        return size;
    }

    public static Function<InputStream, String> initDownload2disk_Callback(String saveDir, String fileName) {
        return in -> {
            File file = IoHelper.createFile(saveDir, fileName);
            try (OutputStream out = new FileOutputStream(file);) {
                IoHelper.write(in, out, true);
                return file.toString();
            } catch (IOException e) {
                log.error("错误：" + e);
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("错误：" + e);
                }
            }

            return null;
        };
    }

    public static String download(String url, String saveDir, String newFileName) {
        HttpURLConnection conn = initHttpConnection(url);
        setUserAgentDefault.accept(conn);
        conn.setDoInput(true);// for conn.getOutputStream().write(someBytes); 需要吗？
        conn.setDoOutput(true);

        String fileName = newFileName == null ? IoHelper.getFileNameFromUrl(url) : newFileName;
        String newlyFilePath = getResponse(conn, false, initDownload2disk_Callback(saveDir, fileName));

        return newlyFilePath;
    }

    public static String download(String url, String saveDir) {
        return download(url, saveDir, null);
    }

    /**
     * POST 请求
     *
     * @param url  请求目标地址
     * @param data 表单数据 KeyValue的请求数据，注意要进行 ? & 编码，使用 URLEncoder.encode()
     * @return 携带请求信息的 Bean
     */
    public static String post(String url, Map<String, Object> data) {
        if (data != null && data.size() > 0) {
            return post(url, MapTool.join(data, v -> v == null ? null : Encode.urlEncode(v.toString())));
        } else {
            return null;
        }
    }

    /**
     * POST 请求
     *
     * @param url    请求目标地址
     * @param params 字符串类型的请求数据
     * @return 请求之后的响应的内容
     */
    public static String post(String url, String params) {
        return post(url, params.getBytes(), null);
    }

    public static String post(String url, byte[] b, Consumer<HttpURLConnection> fn) {
        return post(url, b, fn, null);
    }

    public static String post_download(String url, Map<String, Object> data, String saveDir, String fileName) {
        if (data != null && data.size() > 0) {
            return post(url, "{\"path\":\"pages/index/index\"}".getBytes(), null, initDownload2disk_Callback(saveDir, fileName));
        } else {
            return null;
        }
    }

    /**
     * POST 请求
     *
     * @param url 请求目标地址
     * @param b   字节格式的请求数据
     * @param fn  对 Conn 进行配置
     * @return 请求之后的响应的内容
     */
    public static String post(String url, byte[] b, Consumer<HttpURLConnection> fn, Function<InputStream, String> responseHandler) {
        HttpURLConnection conn = initHttpConnection(url);
        setMedthod.accept(conn, "POST");
        conn.setDoOutput(true); // for conn.getOutputStream().write(someBytes);
        conn.setDoInput(true);

        if (fn != null)
            fn.accept(conn);
        else
            setFormPost.accept(conn);

        try (OutputStream out = conn.getOutputStream();) {
            out.write(b); // 输出流写入字节数据
            out.flush();
        } catch (IOException e) {
            log.error("写入 post 数据时失败！{0}" + e);
        }

        return getResponse(conn, false, responseHandler == null ? NetUtil::byteStream2stringStream : responseHandler);
    }


    /**
     * 多段上传
     *
     * @param url
     * @param data 若包含 File 对象则表示二进制（文件）数据
     * @return
     */
    public static String multiPOST(String url, Map<String, Object> data) {
        byte[] bytes = null;

        for (String key : data.keySet()) {
            Object v = data.get(key);
            byte[] _bytes;

            if (v instanceof File) {
                File file = (File) v;
                String fileName = file.getName();
                String field = String.format(divFile, BOUNDARY, key, fileName);

                _bytes = StreamUtil.concat(field.getBytes(), StreamUtil.fileAsByte(file));

            } else { // 普通字段
                String field = String.format(divField, BOUNDARY, key, v.toString());
                _bytes = field.getBytes();
            }

            if (bytes == null)
                bytes = _bytes;
            else
                StreamUtil.concat(bytes, _bytes);
        }

        StreamUtil.concat(bytes, ("\r\n--" + BOUNDARY + "--\r\n").getBytes());

        post(url, bytes, conn -> conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY));
        return null;
    }

    public static String startGet(String path, HashMap<String, String> mData) {

        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            //GET请求直接在链接后面拼上请求参数
            String mPath = path + "?";
            for (String key : mData.keySet()) {
                mPath += key + "=" + mData.get(key) + "&";
            }
            URL url = new URL(mPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //Get请求不需要DoOutPut
            conn.setDoOutput(false);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //连接服务器
            conn.connect();
            // 取得输入流，并使用Reader读取
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

}
