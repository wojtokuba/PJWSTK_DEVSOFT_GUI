package pl.wojtokuba.proj.Commands;

import pl.wojtokuba.proj.Model.File;
import pl.wojtokuba.proj.Model.Rential;
import pl.wojtokuba.proj.Storage.RentialStorage;
import pl.wojtokuba.proj.Utils.LoggerUtil;
import pl.wojtokuba.proj.Utils.SimpleInjector;

public class CheckRentialDatesCommand extends BaseCommand {
    private static boolean isRunning;

    public CheckRentialDatesCommand() {
        super("Checking for rentials to release...");
    }

    private final RentialStorage rentialStorage = (RentialStorage) SimpleInjector.resolveObject(RentialStorage.class);

    @Override
    public void run() {
        //dont allow to overlapse multiple instances of same command at once
        if(isRunning)
            return;
        isRunning = true;
        assert rentialStorage != null;
        for(Rential rential : rentialStorage.findFinishedNotArchived()){
            if(rential.getPayCall() == null){
                File payCall = new File().setRential(rential);
                rential.setPayCall(payCall);
                LoggerUtil.getLogger().fine("Setting awaiting payment flag for: "+ rential.toString());
            }
        }
        /*
        Content of command
         */
        isRunning = false;
    }
}
