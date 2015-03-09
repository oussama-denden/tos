package com.nordnet.opale.mock;

import java.util.concurrent.Future;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;

import com.nordnet.mandatelibrary.ws.MandateException;
import com.nordnet.mandatelibrary.ws.MandateLibraryWS;
import com.nordnet.mandatelibrary.ws.types.Account;
import com.nordnet.mandatelibrary.ws.types.Account2;
import com.nordnet.mandatelibrary.ws.types.AddCustomerResponse;
import com.nordnet.mandatelibrary.ws.types.ArrayOfMandate;
import com.nordnet.mandatelibrary.ws.types.CheckIbanResponse;
import com.nordnet.mandatelibrary.ws.types.CreateForOldCustomerResponse;
import com.nordnet.mandatelibrary.ws.types.CreateNNBPayersResponse;
import com.nordnet.mandatelibrary.ws.types.CreateResponse;
import com.nordnet.mandatelibrary.ws.types.CreditCard;
import com.nordnet.mandatelibrary.ws.types.ExistsCustomerForAccountResponse;
import com.nordnet.mandatelibrary.ws.types.Facture;
import com.nordnet.mandatelibrary.ws.types.GetMandatePdfResponse;
import com.nordnet.mandatelibrary.ws.types.GetMandateResponse;
import com.nordnet.mandatelibrary.ws.types.GetMandateforAccountResponse;
import com.nordnet.mandatelibrary.ws.types.Iban;
import com.nordnet.mandatelibrary.ws.types.InvoiceKeys;
import com.nordnet.mandatelibrary.ws.types.ListMandatesForCustomerKeyResponse;
import com.nordnet.mandatelibrary.ws.types.ListMandatesForIBANResponse;
import com.nordnet.mandatelibrary.ws.types.Mandate;
import com.nordnet.mandatelibrary.ws.types.Paypal;
import com.nordnet.mandatelibrary.ws.types.ProcessAddDebitRequestsResponse;
import com.nordnet.mandatelibrary.ws.types.ProcessGetFolioCodeRequestsResponse;
import com.nordnet.mandatelibrary.ws.types.RUM;
import com.nordnet.mandatelibrary.ws.types.RemoveCustomerForAccountResponse;
import com.nordnet.mandatelibrary.ws.types.RumCodes;
import com.nordnet.mandatelibrary.ws.types.SetEnabledResponse;
import com.nordnet.mandatelibrary.ws.types.SetFolioCodeResponse;
import com.nordnet.mandatelibrary.ws.types.UpdateAccountResponse;
import com.nordnet.mandatelibrary.ws.types.UpdateIBANResponse;
import com.nordnet.mandatelibrary.ws.types.UpdateNNBPayersResponse;
import com.nordnet.mandatelibrary.ws.types.UpdatePaymentResponse;
import com.nordnet.mandatelibrary.ws.types.UserInfos;

/**
 * 
 * @author akram-moncer
 * 
 */
public class MandateLibraryMock implements MandateLibraryWS {

	@Override
	public Response<GetMandateResponse> getMandateAsync(String rum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> getMandateAsync(String rum, AsyncHandler<GetMandateResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mandate getMandate(String rum) throws MandateException {
		if (rum == null || rum.equals("")) {
			throw new MandateException("rum cannot be empty");
		}

		Mandate mandate = new Mandate();
		Account2 account2 = new Account2();
		account2.setAccountKey("accountKey");
		mandate.setAccount(account2);
		if (rum.equalsIgnoreCase("R0000001")) {
			mandate.setEnabled(false);
		} else {
			mandate.setEnabled(true);
		}
		mandate.setRum(rum);
		return mandate;
	}

	@Override
	public Response<GetMandatePdfResponse> getMandatePdfAsync(String rum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> getMandatePdfAsync(String rum, AsyncHandler<GetMandatePdfResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getMandatePdf(String rum) throws MandateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<UpdateIBANResponse> updateIBANAsync(String rum, Iban iban, UserInfos userInfos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> updateIBANAsync(String rum, Iban iban, UserInfos userInfos,
			AsyncHandler<UpdateIBANResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateIBAN(String rum, Iban iban, UserInfos userInfos) throws MandateException {
		// TODO Auto-generated method stub

	}

	@Override
	public Response<UpdatePaymentResponse> updatePaymentAsync(String rum, Iban iban, CreditCard creditCard,
			Facture facture, Paypal paypal, UserInfos userInfos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> updatePaymentAsync(String rum, Iban iban, CreditCard creditCard, Facture facture, Paypal paypal,
			UserInfos userInfos, AsyncHandler<UpdatePaymentResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePayment(String rum, Iban iban, CreditCard creditCard, Facture facture, Paypal paypal,
			UserInfos userInfos) throws MandateException {
		// TODO Auto-generated method stub

	}

	@Override
	public Response<CreateNNBPayersResponse> createNNBPayersAsync(RumCodes rumCodes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> createNNBPayersAsync(RumCodes rumCodes, AsyncHandler<CreateNNBPayersResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createNNBPayers(RumCodes rumCodes) throws MandateException {
		// TODO Auto-generated method stub

	}

	@Override
	public Response<ExistsCustomerForAccountResponse> existsCustomerForAccountAsync(String customerKey,
			String accountKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> existsCustomerForAccountAsync(String customerKey, String accountKey,
			AsyncHandler<ExistsCustomerForAccountResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsCustomerForAccount(String customerKey, String accountKey) throws MandateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Response<ListMandatesForCustomerKeyResponse> listMandatesForCustomerKeyAsync(String customerKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> listMandatesForCustomerKeyAsync(String customerKey,
			AsyncHandler<ListMandatesForCustomerKeyResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayOfMandate listMandatesForCustomerKey(String customerKey) throws MandateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<SetEnabledResponse> setEnabledAsync(String rum, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> setEnabledAsync(String rum, boolean enabled, AsyncHandler<SetEnabledResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEnabled(String rum, boolean enabled) throws MandateException {
		// TODO Auto-generated method stub

	}

	@Override
	public Response<UpdateNNBPayersResponse> updateNNBPayersAsync(RumCodes rumCodes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> updateNNBPayersAsync(RumCodes rumCodes, AsyncHandler<UpdateNNBPayersResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateNNBPayers(RumCodes rumCodes) throws MandateException {
		// TODO Auto-generated method stub

	}

	@Override
	public Response<AddCustomerResponse> addCustomerAsync(String rum, String customerKey, UserInfos userInfos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> addCustomerAsync(String rum, String customerKey, UserInfos userInfos,
			AsyncHandler<AddCustomerResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCustomer(String rum, String customerKey, UserInfos userInfos) throws MandateException {
		// TODO Auto-generated method stub

	}

	@Override
	public Response<UpdateAccountResponse> updateAccountAsync(String rum, Account account, UserInfos userInfos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> updateAccountAsync(String rum, Account account, UserInfos userInfos,
			AsyncHandler<UpdateAccountResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAccount(String rum, Account account, UserInfos userInfos) throws MandateException {
		// TODO Auto-generated method stub

	}

	@Override
	public Response<CreateForOldCustomerResponse> createForOldCustomerAsync(String customerKey, String ics,
			Account account, Iban iban, CreditCard creditCard, Facture facture, Paypal paypal, UserInfos userInfos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> createForOldCustomerAsync(String customerKey, String ics, Account account, Iban iban,
			CreditCard creditCard, Facture facture, Paypal paypal, UserInfos userInfos,
			AsyncHandler<CreateForOldCustomerResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mandate createForOldCustomer(String customerKey, String ics, Account account, Iban iban,
			CreditCard creditCard, Facture facture, Paypal paypal, UserInfos userInfos) throws MandateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<SetFolioCodeResponse> setFolioCodeAsync(String rum, String folioCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> setFolioCodeAsync(String rum, String folioCode, AsyncHandler<SetFolioCodeResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFolioCode(String rum, String folioCode) throws MandateException {
		// TODO Auto-generated method stub

	}

	@Override
	public Response<RemoveCustomerForAccountResponse> removeCustomerForAccountAsync(String customerKey,
			String accountKey, UserInfos userInfos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> removeCustomerForAccountAsync(String customerKey, String accountKey, UserInfos userInfos,
			AsyncHandler<RemoveCustomerForAccountResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeCustomerForAccount(String customerKey, String accountKey, UserInfos userInfos)
			throws MandateException {
		// TODO Auto-generated method stub

	}

	@Override
	public Response<ProcessAddDebitRequestsResponse> processAddDebitRequestsAsync(InvoiceKeys invoiceKeys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> processAddDebitRequestsAsync(InvoiceKeys invoiceKeys,
			AsyncHandler<ProcessAddDebitRequestsResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processAddDebitRequests(InvoiceKeys invoiceKeys) throws MandateException {
		// TODO Auto-generated method stub

	}

	@Override
	public Response<CheckIbanResponse> checkIbanAsync(Iban iban) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> checkIbanAsync(Iban iban, AsyncHandler<CheckIbanResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkIban(Iban iban) throws MandateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Response<ListMandatesForIBANResponse> listMandatesForIBANAsync(String number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> listMandatesForIBANAsync(String number, AsyncHandler<ListMandatesForIBANResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayOfMandate listMandatesForIBAN(String number) throws MandateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<CreateResponse> createAsync(String customerKey, String ics, Account account, Iban iban,
			CreditCard creditCard, Facture facture, Paypal paypal, UserInfos userInfos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> createAsync(String customerKey, String ics, Account account, Iban iban, CreditCard creditCard,
			Facture facture, Paypal paypal, UserInfos userInfos, AsyncHandler<CreateResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mandate create(String customerKey, String ics, Account account, Iban iban, CreditCard creditCard,
			Facture facture, Paypal paypal, UserInfos userInfos) throws MandateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<GetMandateforAccountResponse> getMandateforAccountAsync(String accountKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> getMandateforAccountAsync(String accountKey,
			AsyncHandler<GetMandateforAccountResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mandate getMandateforAccount(String accountKey) throws MandateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<ProcessGetFolioCodeRequestsResponse> processGetFolioCodeRequestsAsync(RUM rum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> processGetFolioCodeRequestsAsync(RUM rum,
			AsyncHandler<ProcessGetFolioCodeRequestsResponse> asyncHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processGetFolioCodeRequests(RUM rum) throws MandateException {
		// TODO Auto-generated method stub

	}

}
