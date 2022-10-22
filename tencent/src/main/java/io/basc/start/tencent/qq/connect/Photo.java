package io.basc.start.tencent.qq.connect;

import io.basc.framework.json.JsonObject;
import io.basc.framework.json.JsonObjectWrapper;

public class Photo extends JsonObjectWrapper {

	public Photo(JsonObject target) {
		super(target);
	}

	/**
	 * 照片的小图ID。照片采用双ID结构，根据任一ID都可获得照片信息
	 * 
	 * @return
	 */
	public String getSloc() {
		return getAsString("sloc");
	}

	/**
	 * 照片的大图ID。照片采用双ID结构，根据任一ID都可获得照片信息
	 * 
	 * @return
	 */
	public String getLloc() {
		return getAsString("lloc");
	}

	/**
	 * 照片的标题
	 * 
	 * @return
	 */
	public String getName() {
		return getAsString("name");
	}

	/**
	 * 照片的描述信息
	 * 
	 * @return
	 */
	public String getDesc() {
		return getAsString("desc");
	}

	/**
	 * 照片上次被修改的时间
	 * 
	 * @return
	 */
	public long getUpdatedTime() {
		return getAsLong("updated_time");
	}

	/**
	 * 照片的上传时间 2011-05-26 11:44:39
	 * 
	 * @return
	 */
	public String getUploadedTime() {
		return getAsString("uploaded_time");
	}

	/**
	 * 照片的小图的url
	 * 
	 * @return
	 */
	public String getSmallUrl() {
		return getAsString("small_url");
	}

	/**
	 * 照片的大图的详细信息集合
	 * 
	 * @return
	 */
	public LargeImage getLargeImage() {
		return getAsObject("large_image", LargeImage.class);
	}
}
