package com.sangxiang;

/**
 *
 * Created by fujg on 2017/5/18.
 */
public class Constant {

	/** 超级管理员ID */
	public static final Long SUPER_ADMIN = 1L;

	/**
	 * 菜单类型
	 */
	public enum MenuType {
		/**
		 * 目录
		 */
		CATALOG(0),
		/**
		 * 菜单
		 */
		MENU(1),
		/**
		 * 按钮
		 */
		BUTTON(2);

		private int value;

		MenuType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}


	/**
	 * 定时任务状态
	 *
	 */
	public enum JobStatus {

		/**
		 * 暂停
		 */
		PAUSE(0),
		/**
		 * 正常
		 */
		NORMAL(1);

		private int value;

		private JobStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 值是否有效
	 */
	public enum ActiveFlag {

		/**
		 * 无效
		 */
		FALSE("0"),

		/**
		 * 有效
		 */
		TRUE("1");

		private final String value;

		private ActiveFlag(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

	
	/**
	 * CMS文章 1:禁用 0:启用
	 */
	public static final Boolean CONTENT_LOCK = true;
	public static final Boolean CONTENT_UNLOCK = false;
	
	
	/**
	 * 操作日志Request存储Name
	 */
	public static final String OPERATION_LOG_CONTENT = "operationLogContent";// 操作日志

	public static final String REQUEST_PARAMS = "requestParams";// 操作日志参数重写
	
	/**
	 * 排序字段，根据Service 中定义
	 */
	public static final String ORDER_TYPE0 = "0";
	public static final String ORDER_TYPE1 = "1";
	public static final String ORDER_TYPE2 = "2";
	public static final String ORDER_TYPE3 = "3";

	/**
	 * 排序方式，0：ASC，1：DESC
	 */
	public static final String ORDER_ASC = "0";
	public static final String ORDER_DESC = "1";

	/**
	 * 页面SELECT未选择
	 */
	public static final String PAGE_UNSELECT = "0";


	/**
	 * 打表表单缓存前缀
	 */
	public static final String FORM_BUFFER_PREFIX = "form:template:buffer:prefix:";

}
