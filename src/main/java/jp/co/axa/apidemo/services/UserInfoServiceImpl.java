package jp.co.axa.apidemo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.axa.apidemo.entities.UserInfo;
import jp.co.axa.apidemo.repositories.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoRepository repo;
	
	Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	
	@Override
	public List<String> getAllUserNames() {
		return repo.findAll().stream()
				.map(UserInfo::getUsername)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<UserInfo> getByUsername(String username) {
		Optional<UserInfo> userInfo = repo.findByUsername(username);
		logger.debug("username:{},userInfo:{}", username, userInfo);
		return userInfo;
	}

}
