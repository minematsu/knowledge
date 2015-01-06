package redcomet.knowledge.control.admin;

import java.util.ArrayList;
import java.util.List;

import redcomet.common.bean.ValidateError;
import redcomet.knowledge.config.AppConfig;
import redcomet.knowledge.config.SystemConfig;
import redcomet.knowledge.control.Control;
import redcomet.web.annotation.Auth;
import redcomet.web.boundary.Boundary;
import redcomet.web.dao.MailConfigsDao;
import redcomet.web.dao.SystemConfigsDao;
import redcomet.web.entity.MailConfigsEntity;
import redcomet.web.entity.SystemConfigsEntity;

public class ConfigControl extends Control {
	
	
	
	/**
	 * 一般設定画面を表示
	 * @return
	 */
	@Auth(roles="admin")
	public Boundary config() {
		SystemConfigsDao dao = SystemConfigsDao.get();
		
		SystemConfigsEntity userAddType = dao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.SYSTEM_NAME);
		if (userAddType == null) {
			userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.SYSTEM_NAME);
			userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
		}
		setAttribute("userAddType", userAddType.getConfigValue());
		
		SystemConfigsEntity userAddNotify = dao.selectOnKey(SystemConfig.USER_ADD_NOTIFY, AppConfig.SYSTEM_NAME);
		if (userAddNotify == null) {
			userAddNotify = new SystemConfigsEntity(SystemConfig.USER_ADD_NOTIFY, AppConfig.SYSTEM_NAME);
			userAddNotify.setConfigValue(SystemConfig.USER_ADD_NOTIFY_OFF);
		}
		setAttribute("userAddNotify", userAddNotify.getConfigValue());
		
		return forward("config.jsp");
	}
	
	
	
	/**
	 * メールの設定を保存
	 * @return
	 */
	@Auth(roles="admin")
	public Boundary save() {
		List<ValidateError> errors = new ArrayList<>();
		String type = getParam("userAddType");
		String notify = getParam("userAddNotify");
		// メール送信の場合、メールの設定が完了しているかチェック
		if ((type != null && type.equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)) 
				|| (notify != null && notify.equals(SystemConfig.USER_ADD_NOTIFY_ON))) {
			MailConfigsDao mailConfigsDao = MailConfigsDao.get();
			MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.SYSTEM_NAME);
			if (mailConfigsEntity == null) {
				ValidateError error = new ValidateError("メールを送る場合、メールの送信の設定が必須です");
				errors.add(error);
			}
		}
		if (!errors.isEmpty()) {
			setResult(null, errors);
			return forward("config.jsp");
		}
		
		SystemConfigsDao dao = SystemConfigsDao.get();
		SystemConfigsEntity userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.SYSTEM_NAME);
		userAddType.setConfigValue(type);
		dao.save(userAddType);
		
		SystemConfigsEntity userAddNotify = new SystemConfigsEntity(SystemConfig.USER_ADD_NOTIFY, AppConfig.SYSTEM_NAME);
		userAddNotify.setConfigValue(notify);
		dao.save(userAddNotify);
		
		String successMsg = "保存しました";
		setResult(successMsg, errors);
		
		return forward("config.jsp");
	}
		

	
}
