package com.nordnet.opale.mock;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.domain.Tracage;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.TracageRepository;
import com.nordnet.opale.service.tracage.TracageService;
import com.nordnet.opale.util.Constants;

/**
 * 
 * @author anisselmane.
 * 
 */
public class LogMock implements TracageService {

	/**
	 * le tracage repository. {@link TracageRepository}.
	 */
	@Autowired
	private TracageRepository tracageRepository;

	/**
	 * Constructeur par defaut.
	 */
	public LogMock() {

	}

	@Override
	public void ajouterTrace(String target, String key, String descr, Auteur user) throws OpaleException {
		Tracage trace =
				new Tracage(target, key, descr, user.getIp() != null ? user.getIp().getIp() : null, user.getQui(),
						Constants.TYPE_LOG, new Date());

		tracageRepository.save(trace);
	}
}
