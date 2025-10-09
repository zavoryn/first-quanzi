package cn.metast.tuoke.framework.common.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.cache.ApiCacheRet;

import java.io.Serializable;


@ApiModel(value = "HttpRet", description = "HttpRet")
public class HttpRet<T> implements Serializable, ApiCacheRet {

	/**
	 * 如果只返回简单类型T用 SingleString
	 */
	private static final long serialVersionUID = 1L;

	public static final int SUCCESS = 1;

	public static final int FAIL = 2;

	//@ApiModelProperty(value = "error")
    //public int error=0;

    //@ApiModelProperty(value = "url")
    //public String url;

	@ApiModelProperty(value = "1=成功；44001=网络错误或服务器返回异常；44002=json解析或转model异常；44003=token失效了;44006=系统关闭中;44009=游客不能使用此功能", name = "code")
	public int code;

	@ApiModelProperty(value = "返回的消息", name = "msg")
	public String msg;

	@ApiModelProperty(value = "返回实体", name = "retObj")
	public T retObj;

	//为了ApiCacheRet 接口保留的
	public String retStr;


	public HttpRet(int code, String msg, T retObj) {
		this.code = code;
		this.msg = msg;
		this.retObj = retObj;
	}
/*
	public HttpRet(String url,int error, T retObj) {
		this.error = code;
		this.url = url;
		this.retObj = retObj;
	}
*/
	//
	public static <T> HttpRet<T> genResult(int code, String msg, T retObj) {
		HttpRet<T> result = new HttpRet<T>(code, msg, retObj);
		return result;
	}
/*
	public static <T> HttpRet<T> genResultFileDto(String url,int error, T retObj) {
		HttpRet<T> result = new HttpRet<T>(url,error,  retObj);
		return result;
	}
*/

	public static <T> HttpRet<T> success(String msg, T data) {
		return genResult(1, msg, data);
	}
/*
	public static <T> HttpRet<T> successFileDto(String url, T data) {
		return genResultFileDto(url,0,  data);
	}
*/

	public static <T> HttpRet<T> success(String msg) {
		return genResult(1, msg, null);
	}

	public static <T> HttpRet<T> fail(String message) {
		return genResult(2, message, null);
	}

	public static <T> HttpRet<T> fail(int code, String message) {
		return genResult(code, message, null);
	}

	@Override
	public String toString() {
		return "RestResult{" + "code=" + code + ", msg='" + msg + '\'' + ", retObj=" + retObj + '}';
	}

	@Override
	public Object datObj() {
		return retObj;
	}

	@Override
	public void chgDat(String data) {
		retStr = data;
		retObj=null;
	}
	@Override
	public boolean success() {
		return code == 1;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}




}
