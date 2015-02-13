package com.nordnet.opale.service.tracage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.rest.RestClient;
import com.nordnet.opale.util.Constants;

/**
 * L'implementation de service {@link TracageService}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Service("tracageService")
public class TracageServiceImpl implements TracageService {

	/**
	 * client REST.
	 */
	@Autowired
	private RestClient restClient;

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void ajouterTrace(String target, String key, String descr, Auteur user) throws OpaleException {

		restClient.addLog(target, key, descr, user.getIp() != null ? user.getIp().getIp() : null, user.getQui(),
				Constants.TYPE_LOG);
	}

}
