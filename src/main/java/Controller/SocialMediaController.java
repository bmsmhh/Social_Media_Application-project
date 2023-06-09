package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    
    public Javalin startAPI(){
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::textCreatorHandler);
        app.get("/messages", this::getAllMessagesHandler);
        String path = "/messages/{message_id}";
        app.patch(path, this::updateMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);

        return app;
    }
    private void getMessageByIdHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        if (message == null) {
            ctx.result("");
        } else {
            ctx.json(message);
        }
    }


    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    Message message = mapper.readValue(ctx.body(), Message.class);
    int message_id = Integer.parseInt(ctx.pathParam("message_id"));
    Message updatedMessage = messageService.updateMessage(message_id, message);
    
    if (updatedMessage == null) {
        ctx.status(400);
    } else {
        String updatedMessageJson = mapper.writeValueAsString(updatedMessage);
        ctx.result(updatedMessageJson).contentType("application/json");
    }
}

    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());
    }
    private void textCreatorHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.textCreator(message);
        if(newMessage == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(newMessage));
        }
    }

    private void registerHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.register(account);
        if(newAccount == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(newAccount));
        }
    }
    
    private void loginHandler(Context context)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), 
        Account.class);
        String username = account.getUsername();
        String password = account.getPassword();
 
        Account loginAccount = accountService.login(account);
        if(loginAccount == null){
          context.status(401).json("");
        }else{
          context.json(mapper.writeValueAsString(loginAccount));
        }
     }
}
