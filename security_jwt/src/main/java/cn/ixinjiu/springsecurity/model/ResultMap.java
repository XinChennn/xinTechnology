package cn.ixinjiu.springsecurity.model;


import java.util.HashMap;

/**
 * Created by XinChen on 2022/8/25
 *
 * @Description : TODO
 */
@SuppressWarnings("serial")
public class ResultMap extends HashMap<String, Object> {


	public ResultMap() {

	}

	public ResultMap success() {
		this.put("code", "200");
		return this;
	}

	public ResultMap fail(String code) {
		this.put("code", code);
		return this;
	}

	public ResultMap message(String message) {
		this.put("message", message);
		return this;
	}

	public ResultMap data(Object obj) {
		this.put("data", obj);
		return this;
	}

	public ResultMap token(String token) {
		this.put("X-Auth-Token", token);
		return this;
	}
}
