/**
 * 
 */
package com.xa3ti.blackcat.message.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.service.impl.BaseService;
import com.xa3ti.blackcat.base.util.Pageable;
import com.xa3ti.blackcat.base.util.SearchFilter;
import com.xa3ti.blackcat.base.util.SearchFilter.Operator;
import com.xa3ti.blackcat.business.entity.Message;
import com.xa3ti.blackcat.business.repository.MessageRepository;
import com.xa3ti.blackcat.business.service.MessageService;
import com.xa3ti.blackcat.message.Msg;
import com.xa3ti.blackcat.message.MsgConstant;
import com.xa3ti.blackcat.message.MsgPusher;
import com.xa3ti.blackcat.message.util.DBConnection;

/**
 * @author nijie
 *
 */
@Service("MsgService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class MsgServiceImpl extends BaseService implements MsgService {
	@Autowired
	MsgPusher msgPusher;

	@Autowired
	private MessageService messageServiceProxy;

	@Autowired
	private MessageRepository messageRepository;

	private static final Logger logger = LoggerFactory
			.getLogger(MsgServiceImpl.class);

	public void updateMessageRetryTimes(Long msgId, Integer times) {

		Message msg = messageRepository.findOne(msgId);
		if (msg != null) {
			msg.setRetryTimes(times);
			messageRepository.save(msg);
		}
	}

	public void updateMessageSuccess(Long msgId) {
		Message msg = messageRepository.findOne(msgId);
		if (msg != null) {
			msg.setMstatus(MsgConstant.Status.SUCCESS);
			messageRepository.save(msg);
		}
	}

	public void updateMessageFailure(Long msgId, String errorReason) {

		Message msg = messageRepository.findOne(msgId);
		if (msg != null) {
			msg.setMstatus(MsgConstant.Status.FAILURE);
			msg.setFailureReason(errorReason);
			messageRepository.save(msg);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.message.persistence.MsgService#updateMessageInfo(
	 * java.lang.Long, java.lang.String)
	 */
	@Override
	public void updateMessageInfo(Long msgId, String info) {
		Message msg = messageRepository.findOne(msgId);
		if (msg != null) {
			msg.setFailureReason(info);
			messageRepository.save(msg);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.message.persistence.MsgService#getMessage(java.lang
	 * .Integer)
	 */
	@Override
	public void getMessage(Integer priority) {

		Pageable pageable = new Pageable(0, 1); //0,1代表查全部

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("EQ_priority",priority);
		filters.put("EQ_mstatus",MsgConstant.Status.UNSEND);

		XaResult<Page<Message>> xa;
		try {
			xa = messageServiceProxy
					.findMessageEQStatusByFilter(1, filters, pageable);
			
			Page<Message> page = xa.getObject();

			List<Message> list = page.getContent();

			Iterator<Message> it = list.iterator();
			while (it.hasNext()) {
				logger.debug("fetch data set message...");
				Message message = it.next();
				Msg m = new Msg();
				m.setId(message.getTid());
				m.setChannel(message.getChannel());
				m.setMobile(message.getMobile());
				m.setClinicId(message.getGroupId());
				m.setContent(message.getContent());
				m.setMstatus(message.getMstatus());
				m.setReceiverId(message.getReceiverId());
				m.setReceiverType(message.getReceiverType());
				m.setSend_date(message.getSendDate());
				m.setSender(String.valueOf(message.getSenderId()));
				m.setTitle(message.getTitle());
				m.setRetryTimes(message.getRetryTimes());
				m.setPriority(message.getPriority());

				msgPusher.addMessageToQueue(m);
			}
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
