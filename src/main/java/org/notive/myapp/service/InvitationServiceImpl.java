package org.notive.myapp.service;

import java.util.List;

import org.notive.myapp.domain.InvitationVO;
import org.notive.myapp.mapper.InvitationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
public class InvitationServiceImpl implements InvitationService {
	
	@Setter(onMethod_ = @Autowired)
	InvitationMapper mapper;

	@Override
	public List<InvitationVO> getInvitationList(String user_id) {
		log.debug("getInvitationList({}) invoked.", user_id);
		
		return this.mapper.selectInvitationList(user_id);
	} // getInvitationList

	@Override
	public Integer createInvitation(InvitationVO invitation) {
		log.debug("createInvitation({}) invoked.", invitation);
		
		return this.mapper.insertInvitationSelectKey(invitation);
	} // createInvitation

	@Override
	public Integer removeInvitation(String group_name) {
		log.debug("removeInvitation({}) invoked.", group_name);
		
		return this.mapper.deleteInvitation(group_name);
	}

	@Override
	public Integer checkUserInvitation(Integer group_no, String user_id) {
		log.debug("checkUserInvitation({}, {}) invoked.", group_no, user_id);
		
		return this.mapper.selectCountUserInvitation(group_no, user_id);
	} // checkUserInvitation

} // end class
