package developer.moein.simpleaacapp.OtherTools;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * TODO (6): Create your executor for room background operations
 *
 */
public class SimpleExecutor {
    //for singleton pattern:
    private static final Object LOCK = new Object();
    private static SimpleExecutor Instance;
    private final Executor diskIo;
    private final Executor mainThread;
    private final Executor networkIo;

    //Constructor:
    public SimpleExecutor(Executor diskIo,Executor mainThread,Executor networkIo){
        this.diskIo = diskIo;
        this.mainThread = mainThread;
        this.networkIo = networkIo;
    }
    //get instance:

    public static SimpleExecutor getInstance() {
        if (Instance == null){
            synchronized (LOCK){
                Instance = new SimpleExecutor(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return Instance;
    }

    //getters:

    public Executor diskIO() {
        return diskIo;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public Executor networkIO() {
        return networkIo;
    }

    //Executor class:
    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
