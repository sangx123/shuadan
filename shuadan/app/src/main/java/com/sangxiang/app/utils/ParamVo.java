package com.sangxiang.app.utils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author  fujg
 * @see     [相关类/方法]（可选）
 * @version TODOVer 1.1
 * @Date	 2017 2017年8月8日 下午7:25:34
 *
 */
@ApiModel
public class ParamVo<E> {
	
	@ApiModelProperty(name="version", value="版本号", example="1")
	protected Long version;
	
	@ApiModelProperty(name="cmd",value="命令", example="1")
	protected String cmd;
	
	@ApiModelProperty(name="pageType",value="机器类型", example="1")
	protected String pageType;
	
	@ApiModelProperty(name="respCode",value="返回代码", example="000")
	protected String respCode;
	
	/*@ApiModelProperty(name="reSubmitToken",value="防重复提交token", example="")
	protected String reSubmitToken;*/
	
	@ApiModelProperty(name="respMsg",value="返回描述", example="请求成功！")
	protected String respMsg;
	
	@ApiModelProperty(name="pageSize",value="每页数据条数", example="10")
	protected Integer pageSize = 10;
	
	@ApiModelProperty(name="pageNumber",value="当前页数", example="1")
	protected Integer pageNumber = 1;

	@ApiModelProperty(name = "pages", value = "总页数", example = "1")
	private Integer pages = 1;

	@ApiModelProperty(name = "total", value = "总条数", example = "1")
	private Long total = 1L;

	@ApiModelProperty(name = "isFirstPage", value = "是否是第一页", example = "true")
	private boolean isFirstPage;

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean firstPage) {
		isFirstPage = firstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean lastPage) {
		isLastPage = lastPage;
	}

	@ApiModelProperty(name = "isLastPage", value = "是否是最后一页", example = "true")
	private boolean isLastPage;
	
	@ApiModelProperty(name="sign",value="签名", example="@#42334i！@34")
	protected String sign;
	
	@ApiModelProperty(name="data",value="数据")
	protected E data;
	
/*	public String getReSubmitToken() {
		return reSubmitToken;
	}
	public void setReSubmitToken(String reSubmitToken) {
		this.reSubmitToken = reSubmitToken;
	}*/

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public E getData() {
		return data;
	}
	@SuppressWarnings("unchecked")
	public void setData(E data) {
		if(data instanceof PageInfo)
		{
			PageInfo pageInfo = (PageInfo)data;
			this.pageSize = pageInfo.getPageSize();
			this.pageNumber = pageInfo.getPages();
			this.total=pageInfo.getTotal();
			this.data = (E)pageInfo.getList();
			this.isFirstPage=pageInfo.isIsFirstPage();
			this.isLastPage=pageInfo.isIsLastPage();
		}
		else
		{
			this.data = data;
		}
	}
}
