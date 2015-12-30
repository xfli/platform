package sivl.platform.common.model;import java.io.Serializable;/** * 业务日志载体与mongodb中的载体一致<br/> * 目的：记录各种业务日志 * @author lixuefeng * @createTime 2015年12月9日 * @version 1.0 */public class BuzLogModel implements Serializable,CommonModel {	private static final long serialVersionUID = 1L;	//请求类名	private String className;	//方法名	private String methodName;	//请求参数	private String parameter;	//消息	private String msg;		/**	 * 初始化业务模型	 * @param className 请求类名	 * @param methodName 方法名	 * @param parameter 请求参数 	 * @param dataFmt 数据返回格式	 * @param ip 请求ip	 * @param msg 消息	 */	public BuzLogModel(String className,String methodName,String parameter,String msg){		this.className = className;		this.methodName= methodName;		this.parameter = parameter;		this.msg	   = msg;	}		public String getClassName() {		return className;	}	public void setClassName(String className) {		this.className = className;	}	public String getMethodName() {		return methodName;	}	public void setMethodName(String methodName) {		this.methodName = methodName;	}	public String getParameter() {		return parameter;	}	public void setParameter(String parameter) {		this.parameter = parameter;	}	public String getMsg() {		return msg;	}	public void setMsg(String msg) {		this.msg = msg;	}		@Override	public String toString() {		return "BuzLogModel [className=" + className + ", methodName="				+ methodName + ", parameter=" + parameter + ", msg=" + msg				+ "]";	}}