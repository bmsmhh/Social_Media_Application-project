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
    public Account login(Account account){
        if (account.getUsername() == null || account.getUsername().equals("")
        || account.getPassword() == null || account.getPassword().equals("")) {
    return null; 
}
        
        //this method check if the account with provided username, password exists
        //but!!! it doesn't check if inputs match with provided inputs
        
            Account existingAccount = accountDAO.getAccountByUserInputs
            (account.getUsername(), account.getPassword());
            if (account.getUsername().equals(existingAccount.getUsername())
            && account.getPassword().equals(existingAccount.getPassword())) {
                return existingAccount; 
            }
            
               return null;
        }
   /* public Account login(Account account) {
    if (account != null && account.getUsername() != null 
        && account.getPassword() != null) {
        Account existingAccount = accountDAO.getAccountByUserInputs(account.getUsername(), account.getPassword());
        if (existingAccount != null 
            && account.getUsername().equals(existingAccount.getUsername())
                && account.getPassword().equals(existingAccount.getPassword())) {
            return existingAccount;
        }
    }
    return null;
}
 */
}  
