package sivl.platform.common.model.request;import java.io.Serializable;/** * 基础请求<br/> * 目的： * @author lixuefeng * @createTime 2015年12月22日 * @version 1.0 */public abstract class BaseRequest implements Serializable {	private static final long serialVersionUID = 1L;	//校验参数(用于对请求进行安全校验算法：|代表或者)	private String filter;		public String getFilter() {		return filter;	}	public void setFilter(String filter) {		this.filter = filter;	}	}