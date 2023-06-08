package Service;

import Model.Account;
import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;
    
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    public MessageService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    
    public Message textCreater(Message message) {
    Message existingMessage = messageDAO.getMessageById(message.getMessage_id());
    
    if (existingMessage == null
            || message.getMessage_text().equals("")
            || message.getMessage_text().length() < 255){
                return null;
            }
        Integer account_id = message.getPosted_by();        
        Account existingAccount = accountDAO.getAccountById(account_id);
        if(existingAccount == null 
        || existingAccount.getAccount_id() != account_id){
            return null;
        }           
    return messageDAO.insertMessage(message);
  }
}