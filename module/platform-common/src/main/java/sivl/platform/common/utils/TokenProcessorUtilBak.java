package sivl.platform.common.utils;import java.io.IOException;import java.security.MessageDigest;import java.security.NoSuchAlgorithmException;import java.util.ArrayList;import java.util.List;import java.util.Map;import javax.annotation.PostConstruct;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpSession;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Component;import sivl.platform.common.constant.Constant;import sivl.platform.common.encrypt.HexConversion;import sivl.platform.common.encrypt.Sign;import sivl.platform.common.service.SysParamService;/** * session Token 处理工具 *  * @author lixuefeng * @createTime 2015年11月28日 * @version 1.0 */@Componentpublic class TokenProcessorUtilBak {	static final String TOKEN_KEY = Constant.RESUBMIT_TOKEN_KEY.getValue();	private static TokenProcessorUtilBak instance;	@Autowired	private SysParamService sysParamService;	/**	 * 最近一次生成令牌值的时间戳。	 */	private long previous;		@PostConstruct	public void init() {		//把初始化后的实体付给静态本身		instance = this;		instance.sysParamService = this.sysParamService;	}	/**	 * 判断请求参数中的令牌值是否有效。<br/>	 * true:有效，false:无效	 * 	 * @param request	 *            return boolean	 */	public synchronized boolean isTokenValid(HttpServletRequest request) {		// 得到请求的当前Session对象,如果不存在，返回null。		HttpSession session = request.getSession(false);		if (session == null) {			return false;		}		// 从Session中取出保存的令牌值。		String saved = (String) session.getAttribute(TOKEN_KEY);		if (saved == null) {			return false;		}		// 清除Session中的令牌值。		resetToken(request);		// 得到请求参数中的令牌值。		String token = request.getParameter(TOKEN_KEY);		if (token == null) {			return false;		}		return saved.equals(token);	}	/**	 * 清除Session中的令牌值。	 */	public synchronized void resetToken(HttpServletRequest request) {		HttpSession session = request.getSession(false);		if (session == null) {			return;		}		session.removeAttribute(TOKEN_KEY);	}	/**	 * 产生一个新的令牌值，保存到Session中， 如果当前Session不存在，则创建一个新的Session。	 */	public synchronized void saveToken(HttpServletRequest request) {		HttpSession session = request.getSession();		String token = generateToken(request);		if (token != null) {			session.setAttribute(TOKEN_KEY, token);		}	}	/**	 * 根据签名算法生成一个唯一的令牌。	 */	public synchronized String generateToken(HttpServletRequest request) {		String secret = sysParamService.getValue(Constant.SIGN_KEY.getKey());		String signName = sysParamService.getValue(Constant.SIGN_NAME.getKey());		if(StringUtil.isEmpty(signName)){			//签名参数名为必传字段			return null;		}		Map<String,String> paramValues = request.getParameterMap();		List<String> ignoreParamNames = new ArrayList<String>();		ignoreParamNames.add(signName);		try {			String token = Sign.sign(paramValues, ignoreParamNames, secret);			return token;		} catch (IOException e) {			return null;		}	}	private String defalueSign(HttpSession session) {		try {			MessageDigest md = MessageDigest.getInstance("MD5");			byte id[] = session.getId().getBytes();			long current = System.currentTimeMillis();			if (current == previous) {				current++;			}			previous = current;			byte now[] = new Long(current).toString().getBytes();			md.update(id);			md.update(now);			return HexConversion.byte2hex(md.digest());		} catch (NoSuchAlgorithmException e) {			return null;		}	}	/**	 * 从Session中得到令牌值，如果Session中没有保存令牌值，则生成一个新的令牌值。	 */	public synchronized String getToken(HttpServletRequest request) {		HttpSession session = request.getSession(false);		if (null == session)			return null;		String token = (String) session.getAttribute(TOKEN_KEY);		if (null == token) {			token = generateToken(request);			if (token != null) {				session.setAttribute(TOKEN_KEY, token);				return token;			} else				return null;		} else			return token;	}}