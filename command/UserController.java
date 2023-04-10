package command;


//THIS IS THE INVOKER FOR ALL OF THE COMMANDS THAT GET CALLED IN GAMEDRIVER
public class UserController {
    private UserCommand command;

    public UserController(){}

    public void setCommand(UserCommand c){
        this.command = c;
    }

    public void performCommand(){
        command.execute();
    }
}
