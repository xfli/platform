package sivl.platform.base.model;import java.io.Serializable;import javax.xml.bind.annotation.XmlRootElement;/** * demo 模型 * @author lixuefeng * @createTime 2015年11月25日 * @version 1.0 */@XmlRootElement(name = "demo")public class DemoModel implements Serializable{	private static final long serialVersionUID = 1L;	//名字	private String name;	//编码	private String code;	//值	private Double value;	public String getName() {		return name;	}	public void setName(String name) {		this.name = name;	}	public String getCode() {		return code;	}	public void setCode(String code) {		this.code = code;	}	public Double getValue() {		return value;	}	public void setValue(Double value) {		this.value = value;	}		}