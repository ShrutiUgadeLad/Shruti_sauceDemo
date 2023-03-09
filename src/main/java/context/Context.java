package context;

import dataModel.LoginDataModel;

public class Context {
    private LoginDataModel loginDataModel;

    public LoginDataModel getLoginDataModel() { return loginDataModel; }

    public void setLoginDataModel(LoginDataModel loginDataModel) {
        this.loginDataModel = loginDataModel;
    }
}