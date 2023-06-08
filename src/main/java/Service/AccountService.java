package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    AccountDAO accountDAO;
    
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account register(Account account){
        Account existingAccount = accountDAO.getAccountByUserInputs(account.getUsername(), account.getPassword());
        if(existingAccount != null
        || account.getUsername().equals("")
        || account.getPassword().length() < 4)
        {
           return null;
        }
        return accountDAO.insertAccount(account);
    }
    public Account login(Account account) {
        if (account.getUsername() == null || account.getPassword() == null) {
            return null;
        }
    //check if username and password exist
    //if not exist return null
        if (account.getUsername().isEmpty() || account.getPassword().isEmpty()) {
            return null;
        }
    //We retrieve username and password from the account that exists in DataBase
    //with getAccountByInpits method of accountDao
        Account existingAccount = accountDAO.getAccountByUserInputs(account.getUsername(), account.getPassword());
    //Check if the account exists in DB
    //Check if username and password of real account match with exinsintg account in DB
    //if conditions are passed we give to the user his/her account
        if (existingAccount != null && account.getUsername().equals(existingAccount.getUsername())
                && account.getPassword().equals(existingAccount.getPassword())) {
            return existingAccount;
        }
    //if conditions are not passed... sorry but no account for you!
        return null;
    }
}
