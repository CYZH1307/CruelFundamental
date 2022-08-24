public class Fork {
    private boolean[] used = {false,false,false,false,false};

    public synchronized void takeFork(){
        String name = Thread.currentThread().getName();
        int i = Integer.parseInt(name.split("-")[1]);
        while(used[i] || used[(i + 1) % 5]){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        used[i] = true;
        used[(i + 1) % 5] = true;
    }

    public synchronized void putFork(){
        String name = Thread.currentThread().getName();
        int i = Integer.parseInt(name.split("-")[1]);
        used[i] = false;
        used[(i + 1) % 5] = false;
        notifyAll();
    }
}


public class Philosopher extends Thread {
    private String name;
    private Fork fork;
    public Philosopher(String name,Fork fork){
        this.name = name;
        this.fork = fork;
    }

    public void run(){
        while(true){
            thinking();
            fork.takeFork();
            eating();
            fork.putFork();
        }
    }

    public void thinking(){
        System.out.println(name + ": I am thinking");
        try{
            sleep(1000);//模拟思考
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void eating(){
        System.out.println(name + ": I am eating");
        try{
            sleep(1000);//模拟吃饭
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
