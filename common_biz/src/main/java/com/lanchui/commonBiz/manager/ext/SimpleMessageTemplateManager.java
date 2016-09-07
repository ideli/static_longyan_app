package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.MessageTemplate;
import com.lanchui.commonBiz.manager.MessageTemplateManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.StringUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * Created by bingcheng on 2015/3/23.
 */
public class SimpleMessageTemplateManager extends AbstractBasicManager implements MessageTemplateManager {

    protected static Logger logger = Logger.getLogger(SimpleMessageTemplateManager.class.getName());

    public SimpleMessageTemplateManager() {
        super(MessageTemplate.class);
    }

    @Override
    public MessageTemplate getMessageTemplateByAlias(String alias) throws ManagerException {
        if (StringUtil.isInvalid(alias)) {
            logger.error("[getMessageTemplateByAlias][error] alias is invalid");
        }
        List<MessageTemplate> list = getBeanListByColumn("alias", alias);
        if (CollectionUtil.isValid(list)) {
            return list.get(0);
        }
        return null;
    }
}
