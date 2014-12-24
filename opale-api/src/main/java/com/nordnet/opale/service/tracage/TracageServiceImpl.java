package com.nordnet.opale.service.tracage;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.domain.Tracage;
import com.nordnet.opale.repository.TracageRepository;

/**
 * L'implementation de service {@link TracageService}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Service("tracageService")
public class TracageServiceImpl implements TracageService {

	/**
	 * le tracage repository. {@link TracageRepository}.
	 */
	@Autowired
	private TracageRepository tracageRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void ajouterTrace(String user, String referenceDraft, String action) {
		Tracage tracage = new Tracage();
		tracage.setUser(user);
		tracage.setReferenceDraft(referenceDraft);
		tracage.setAction(action);
		tracage.setDate(new Date());
		tracageRepository.save(tracage);

	}

}
