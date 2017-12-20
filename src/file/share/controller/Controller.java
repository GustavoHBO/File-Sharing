package file.share.controller;

/**
 * Class controller of the actions of the user.
 * @author Gustavo Henrique
 */
public class Controller {
    
    private static Controller controller; // Statement controller.
    
    private final int PORTSERVER = 55600;
    private final int LENGTHCODEPROTOCOL = 2;
    
     /* Design Pattern Singleton */
    
    /**
     * The constructor is private for use the singleton
     */
    private Controller(){}
    
    /**
     * Return the instance of controller.
     * @return controller - An instance.
     */
    public static Controller getInstance(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }
    
    /**
     * Reset the controller.
     */
    public static void resetController(){
        controller = null;
    }
                                                /* End Singleton */
    
}
