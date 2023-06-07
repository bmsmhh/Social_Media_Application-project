package Controller;

import Model.Account;
import Service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {
    AccountService accountService;
    public SocialMediaController(){
        accountService = new AccountService();
    }
    
    public Javalin startAPI(){
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        
        return app;
    }
    private void loginHandler(Context context)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), 
        Account.class);
        String username = account.getUsername();
        String password = account.getPassword();
 
        Account loginAccount = accountService.login(account);
        if(loginAccount == null){
          context.status(401).json("Login failed");
        }else{
          context.json(mapper.writeValueAsString(loginAccount));
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
     /* private void loginHandler(Context ctx) {
        ctx.json(accountService.login(ctx.queryParam("username"),
                ctx.queryParam("password")));
    } */

    /* private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account existingAccount = accountService.login(account);
        if(existingAccount == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(existingAccount));
        }

    } */
}
