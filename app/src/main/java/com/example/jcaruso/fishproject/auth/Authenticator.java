package com.example.jcaruso.fishproject.auth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jcaruso.fishproject.login.LoginActivity;

public class Authenticator extends AbstractAccountAuthenticator {

    private Context mContext;

    public Authenticator(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        AccountManager accountManager = AccountManager.get(mContext);
        final Bundle result = new Bundle();

        String token = accountManager.peekAuthToken(account, authTokenType);
        String password = accountManager.getPassword(account);

        // case 1 : Auth Token is available
        // if previously saved token is cached, return it
        if (!token.isEmpty()) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_PASSWORD, password);
            result.putString(AccountManager.KEY_AUTHTOKEN, token);
            return result;
        }

        // case 2 : Auth Token is not available but refresh token is
        // else if saved password is available, send to server to generate new token
        if (!password.isEmpty()) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_PASSWORD, password);
            result.putString(AccountManager.KEY_AUTHTOKEN, token);
        }

        // case 3 : Auth Token & Refresh Token aren't available but the account exists
        if (isAccountAvailable(accountManager, account)) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_PASSWORD, password);
            result.putString(AccountManager.KEY_AUTHTOKEN, token);
        }

        // otherwise, return intent to prompt the user for password

        return null;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }

    private boolean isAccountAvailable(AccountManager accountManager, Account account) {
        Account[] availableAccounts = accountManager.getAccountsByType(account.type);
        for (Account availableAccount : availableAccounts) {
            if (account.name.equals(availableAccount.name))
                return true;
        }
        return false;
    }
}
