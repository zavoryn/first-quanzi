package cn.metast.tuoke.framework.common.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.cache.ApiCacheRet;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "HttpRetArr", description = "HttpRetArr")
public class HttpRetArr<T> implements Serializable, ApiCacheRet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "44001=网络错误或服务器返回异常；44002=json解析或转model异常；44003=token失效了；44006=系统关闭中;", name = "code")
	public int code;

	@ApiModelProperty(value = "返回的消息", name = "msg")
	public String msg;

	@ApiModelProperty(value = "返回实体列表", name = "retArr")
	public List<T> retArr;

	//为了ApiCacheRet 接口保留的
	public String retStr;


	public HttpRetArr(int code, String msg, List<T> retObj) {
		this.code = code;
		this.msg = msg;
		this.retArr = retObj;
	}

	public static <T> HttpRetArr<T> genResult(int code, String msg, List<T> retObj) {
		HttpRetArr<T> result = new HttpRetArr<T>(code, msg, retObj);
		return result;
	}

	public static <T> HttpRetArr<T> success(String msg, List<T> data) {
		return genResult(1, msg, data);
	}

	public static <T> HttpRetArr<T> success(String msg) {
		return genResult(1, msg, null);
	}

	public static <T> HttpRetArr<T> fail(String message) {
		return genResult(2, message, null);
	}

	public static <T> HttpRetArr<T> fail(int code, String message) {
		return genResult(code, message, null);
	}

	@Override
	public String toString() {
		return "HttpRetArr{" + "code=" + code + ", msg='" + msg + '\'' + ", retArr=" + retArr + '}';
	}
	@Override
	public Object datObj()
	{
		return retArr;
	}

	@Override
	public void chgDat(String data) {
		retStr = data;
		retArr=null;
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
