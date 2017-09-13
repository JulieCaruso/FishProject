package com.example.jcaruso.fishproject.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.app.App;

public class AuthenticatorManager {

    private Context mContext;

    public AuthenticatorManager(Context context) {
        mContext = context;
    }

    public static Account getAccount(String accountName) {
        return new Account(accountName, App.ACCOUNT_TYPE);
    }

    public void addAccount(Activity callingActivity, AccountManagerCallback<Bundle> callback) {
        AccountManager accountManager = (AccountManager) mContext.getSystemService(Context.ACCOUNT_SERVICE);
        accountManager.addAccount(App.ACCOUNT_TYPE, App.AUTH_TOKEN_TYPE, null, null, callingActivity, callback, null);
    }

    public void createAccount(User user) {
        Account account = getAccount(user.getUsername());
        AccountManager accountManager = (AccountManager) mContext.getSystemService(Context.ACCOUNT_SERVICE);
        accountManager.addAccountExplicitly(account, user.getPassword(), null);
    }

    public void invalidateToken(String authToken) {
        AccountManager accountManager = (AccountManager) mContext.getSystemService(Context.ACCOUNT_SERVICE);
        accountManager.invalidateAuthToken(App.ACCOUNT_TYPE, authToken);
    }

    public void getAuthToken(AccountManagerCallback<Bundle> callback) {
        AccountManager accountManager = (AccountManager) mContext.getSystemService(Context.ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccountsByType(App.ACCOUNT_TYPE);
        if (accounts.length > 0) {
            Log.d("azerty", String.valueOf(accounts[0]));
            accountManager.getAuthToken(accounts[0], App.AUTH_TOKEN_TYPE, null, false, callback, null);
        }
    }
}
